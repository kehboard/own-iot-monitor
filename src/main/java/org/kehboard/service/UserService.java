package org.kehboard.service;

import org.kehboard.entity.json.Error;
import org.kehboard.entity.json.user.ChangePassword;
import org.kehboard.entity.json.user.DeviceJSON;
import org.kehboard.entity.json.user.DeviceRequestJSON;
import org.kehboard.entity.json.user.MeasureNameJSON;
import org.kehboard.repository.DeviceJPA;
import org.kehboard.repository.MeasureJPA;
import org.kehboard.repository.MeasureNameJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public interface UserService {

    public ResponseEntity<?> createDevice(DeviceRequestJSON deviceRequestJSON,String apikey);

    public ResponseEntity<?> editDevice(@PathVariable(required = true) Integer id, @RequestBody DeviceJSON deviceJSON,String apikey);

    public ResponseEntity<?> resetApiKeyDevice(@PathVariable(required = true) Integer id, String apikey);

    public ResponseEntity<?> deleteDevice(@PathVariable(required = true) Integer id, String apikey);

    public ResponseEntity<?> getDevices(String apikey);


    public ResponseEntity<?> getDeviceById(String apikey, Integer id);


    public ResponseEntity<?> getMeasuredDataById(@PathVariable(required = true) Integer id,String apikey);

    public ResponseEntity<?> userLogin(String login, String password);

    ResponseEntity<?> userLogout(String token);

    ResponseEntity<?> getLatestDataByDevId(Integer id, String apikey, Integer amount);

    ResponseEntity<?> addMeasurement(Integer id, String apikey, MeasureNameJSON measureNameJSON);

    ResponseEntity<?> getMeasurement(Integer id, String apikey, Integer mid);

    ResponseEntity<?> editMeasurement(Integer id, String apikey, Integer mid, MeasureNameJSON measureNameJSON);
    ResponseEntity<?> removeMeasurement(Integer id, String apikey, Integer mid);

    ResponseEntity<?> userChangePassword(String apikey, ChangePassword changePassword);
}
