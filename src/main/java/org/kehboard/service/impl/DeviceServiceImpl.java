package org.kehboard.service.impl;

import org.kehboard.entity.db.Device;
import org.kehboard.entity.db.Measure;
import org.kehboard.entity.json.Error;
import org.kehboard.entity.json.device.*;
import org.kehboard.repository.DeviceJPA;
import org.kehboard.repository.MeasureJPA;
import org.kehboard.repository.MeasureNameJPA;
import org.kehboard.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceJPA deviceJPA;
    @Autowired
    private MeasureNameJPA measureNameJPA;
    @Autowired
    private MeasureJPA measureJPA;

    // Успею ли я это реалзовать не знаю но пусть будет тут. Этот метод нужен
    // для простого добавления устройства. Устройсво должно передать название, какие
    // параметы оно измеряет, его местоположение (если есть возможность это определить)
    // Сценарий использования: Разработчик собрал устройтсво которое умеет отпралвять данные в это
    // приложение и хочет поделится со всем миром схемой устройства и прошивкой к нему. И чтобы другому
    // самодельщику который провторил проект не вбивать руками имена переменных в админке этого приложения
    // устройство автоматически добавит эти данные в админку (если разработчик сделал эту фичу).
    // Пока что этот метод возвращает 418 ошибку "Я чайник".
    public ResponseEntity<?> registerDeviceToAccount(@RequestBody RegisterToAccount registerToAccount) {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new Error("Not implemented now"));
    }

    // Метод для добавления измерения в бд.
    // Коды ошибок которые он возвращает
    // 403 неправильный api ключ
    // 406 в следующих случаях
    // * имена переменных в запросе одиннаковые
    // * не скофигурированы переменные в админ панели
    // * переменные не совпадают в базе данных и запросе
    // Если запрос прошел успешно возвращаем 204 код.
    @Transactional
    public ResponseEntity<?> addMeasure(@RequestBody ReceiveMeasurement receiveMeasurement) {
        Device device = deviceJPA.getDeviceByApiKey(receiveMeasurement.getKey());
        if (device != null) {
            if (isVarsUnique(receiveMeasurement.getMeasure())) {
                Integer devId = device.getId();
                Integer userId = device.getUserId();
                List<Map<String, Object>> iotNamesAndIds = measureNameJPA.getIotNamesAndIdByDevIdAndUserId(devId, userId);
                if (iotNamesAndIds != null) {
                    HashMap<String, Integer> iotNames = makeIdVarHashMap(iotNamesAndIds);
                    if (isEquals(iotNames, receiveMeasurement.getMeasure())) {
                        List<Measure> measures = new LinkedList<>();
                        for (MeasureJSON measureJSON : receiveMeasurement.getMeasure()) {
                            measures.add(Measure.builder()
                                    .measureNameId(iotNames.get(measureJSON.getVariable()))
                                    .devId(devId)
                                    .data(measureJSON.getData())
                                    .unixTime(System.currentTimeMillis() / 1000L)
                                    .build());
                        }
                        //System.out.println(measures);
                        measureJPA.saveAll(measures);
                        //measureJPA.flush();
                        return ResponseEntity.status(HttpStatus.OK).body(new SuccessJSON());
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Error("measurements in request and in admin panel not equal"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Error("please configure measurements in admin panel"));
                }

            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Error("variable names must be unique in request"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("invalid key"));
        }
    }

    // Метод для проверки апи ключа. В случае отсутсвия ключа возвращает 403 ошибку.
    // В случае успешной проверки возвращает 200.
    public ResponseEntity<?> checkKey(@RequestBody CheckKeyJSON checkKeyJSON) {
        Device device = deviceJPA.getDeviceByApiKey(checkKeyJSON.getKey());
        if (device == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("api key not valid"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessJSON());
    }


    private boolean isVarsUnique(List<MeasureJSON> measures) {
        Set<String> store = new HashSet<>();
        for (MeasureJSON measure : measures) {
            if (!store.add(measure.getVariable())) {
                return false;
            }
        }
        return true;
    }

    private HashMap<String, Integer> makeIdVarHashMap(List<Map<String, Object>> iotNames) {
        HashMap<String, Integer> map = new HashMap<>();
        for (Map<String, Object> iotName :
                iotNames) {
            System.out.println(iotName.get("id"));
            map.put((String) iotName.get("iotName"), (Integer) iotName.get("id"));
        }
        return map;
    }

    private boolean isEquals(HashMap<String, Integer> iotNames, List<MeasureJSON> measures) {
        for (MeasureJSON measure : measures) {
            if (!iotNames.containsKey(measure.getVariable())) {
                return false;
            }
        }
        return true;
    }
}
