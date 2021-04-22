package org.kehboard.controller.api.device.v1;

import org.kehboard.entity.json.device.*;
import org.kehboard.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device/v1")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;


    // Успею ли я это реалзовать не знаю но пусть будет тут. Этот метод нужен
    // для простого добавления устройства. Устройсво должно передать название, какие
    // параметы оно измеряет, его местоположение (если есть возможность это определить)
    // Сценарий использования: Разработчик собрал устройтсво которое умеет отпралвять данные в это
    // приложение и хочет поделится со всем миром схемой устройства и прошивкой к нему. И чтобы другому
    // самодельщику который провторил проект не вбивать руками имена переменных в админке этого приложения
    // устройство автоматически добавит эти данные в админку (если разработчик сделал эту фичу).
    // Пока что этот метод возвращает 418 ошибку "Я чайник".
    @PostMapping("registerToAccount")
    public ResponseEntity<?> registerDeviceToAccount(@RequestBody RegisterToAccount registerToAccount) {
        return deviceService.registerDeviceToAccount(registerToAccount);
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
    @PostMapping("addMeasure")
    public ResponseEntity<?> addMeasure(@RequestBody ReceiveMeasurement receiveMeasurement) {
        return deviceService.addMeasure(receiveMeasurement);
    }

    // Метод для проверки апи ключа. В случае отсутсвия ключа возвращает 403 ошибку.
    // В случае успешной проверки возвращает 200.
    @PostMapping("checkKey")
    public ResponseEntity<?> checkKey(@RequestBody CheckKeyJSON checkKeyJSON) {
        return deviceService.checkKey(checkKeyJSON);
    }
}
