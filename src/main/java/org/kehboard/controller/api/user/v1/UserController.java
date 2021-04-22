package org.kehboard.controller.api.user.v1;

import org.kehboard.entity.json.user.ChangePassword;
import org.kehboard.entity.json.user.DeviceJSON;
import org.kehboard.entity.json.user.DeviceRequestJSON;
import org.kehboard.entity.json.user.MeasureNameJSON;
import org.kehboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("device/create")
    public ResponseEntity<?> createDevice(@RequestBody DeviceRequestJSON deviceRequestJSON, @RequestParam String apikey) {
        return userService.createDevice(deviceRequestJSON,apikey);
    }

    @PostMapping("device/edit/{id}")
    public ResponseEntity<?> editDevice(@PathVariable(required = true) Integer id, @RequestBody DeviceJSON deviceJSON,
                                        @RequestParam String apikey) {
        return userService.editDevice(id, deviceJSON,apikey);
    }

    @GetMapping("device/resetApiKey/{id}")
    public ResponseEntity<?> resetApiKeyDevice(@PathVariable(required = true) Integer id, @RequestParam String apikey) {
        return userService.resetApiKeyDevice(id, apikey);
    }

    @GetMapping("device/remove/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable(required = true) Integer id, @RequestParam String apikey) {
        return userService.deleteDevice(id,apikey);
    }

    @GetMapping("device/list")
    public ResponseEntity<?> getDevices(@RequestParam String apikey) {
        return userService.getDevices(apikey);
    }

    @GetMapping("device/{id}")
    public ResponseEntity<?> getDeviceById(@PathVariable(required = true) Integer id, @RequestParam(required = false) String apikey) {
        return userService.getDeviceById(apikey, id);
    }

    @GetMapping("device/{id}/getMeasuredData")
    public ResponseEntity<?> getMeasuredDataById(@PathVariable(required = true) Integer id,@RequestParam String apikey) {
        return userService.getMeasuredDataById(id,apikey);
    }

    @GetMapping("device/{id}/getLatestData")
    public ResponseEntity<?> getLatestDataByDevId(@PathVariable(required = true) Integer id,@RequestParam(required = false) String apikey, @RequestParam Integer amount) {
        return userService.getLatestDataByDevId(id,apikey,amount);
    }

    @PostMapping("device/{id}/addMeasurements")
    public ResponseEntity<?> addMeasurement(@PathVariable(required = true) Integer id, @RequestParam String apikey, @RequestBody MeasureNameJSON measureNameJSON) {
        return userService.addMeasurement(id,apikey,measureNameJSON);
    }
    @PostMapping("device/{id}/editMeasurement/{mid}")
    public ResponseEntity<?> editMeasurement(@PathVariable(required = true) Integer id, @RequestParam String apikey,@PathVariable(required = true) Integer mid, @RequestBody MeasureNameJSON measureNameJSON) {
        return userService.editMeasurement(id,apikey,mid,measureNameJSON);
    }
    @GetMapping("device/{id}/removeMeasurement/{mid}")
    public ResponseEntity<?> removeMeasurement(@PathVariable(required = true) Integer id, @RequestParam String apikey,@PathVariable(required = true) Integer mid) {
        return userService.removeMeasurement(id,apikey,mid);
    }

    @GetMapping("device/{id}/getMeasurement/{mid}")
    public ResponseEntity<?> getMeasurement(@PathVariable(required = true) Integer id,@PathVariable(required = true) Integer mid, @RequestParam String apikey) {
        return userService.getMeasurement(id,apikey,mid);
    }


    @PostMapping("user/login")
    public ResponseEntity<?> userLogin(@RequestParam String login, @RequestParam String password) {
        return userService.userLogin(login, password);
    }

    @PostMapping("user/changePassword")
    public ResponseEntity<?> userChangePassword(@RequestParam String apikey, @RequestBody ChangePassword changePassword) {
        return userService.userChangePassword(apikey, changePassword);
    }

    @GetMapping("user/logout")
    public ResponseEntity<?> userLogout(@RequestParam String token) {
        return userService.userLogout(token);
    }
}
