package org.kehboard.controller.api.user.v1;

import org.kehboard.entity.json.Error;
import org.kehboard.entity.json.user.DeviceJSON;
import org.kehboard.repository.DeviceJPA;
import org.kehboard.repository.MeasureJPA;
import org.kehboard.repository.MeasureNameJPA;
import org.kehboard.repository.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/v1")
public class UserController {
    @Autowired
    private UserJPA userJPA;
    @Autowired
    private DeviceJPA deviceJPA;
    @Autowired
    private MeasureNameJPA measureNameJPA;
    @Autowired
    private MeasureJPA measureJPA;

    @PostMapping("device/create")
    public ResponseEntity<?> createDevice(@RequestBody DeviceJSON deviceJSON){
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new Error("Not implemented now"));
    }
    @PutMapping("device/edit/{id}")
    public ResponseEntity<?> editDevice(@PathVariable(required = true) Integer id, @RequestBody DeviceJSON deviceJSON){
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new Error("Not implemented now"));
    }
    @GetMapping("device/resetApiKey/{id}")
    public ResponseEntity<?> resetApiKeyDevice(@PathVariable(required = true) Integer id){
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new Error("Not implemented now"));
    }
    @DeleteMapping("device/remove/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable(required = true) Integer id){
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new Error("Not implemented now"));
    }
    @GetMapping("device/list")
    public ResponseEntity<?> getDevices(@RequestParam Integer offset){
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new Error("Not implemented now"));
    }

    @GetMapping("device/{id}")
    public ResponseEntity<?> getDeviceById(@PathVariable(required = true) Integer id){
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new Error("Not implemented now"));
    }

    @GetMapping("device/{id}/getMeasuredData")
    public ResponseEntity<?> getMeasuredDataById(@PathVariable(required = true) Integer id){
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new Error("Not implemented now"));
    }


}
