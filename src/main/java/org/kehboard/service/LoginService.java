package org.kehboard.service;

import org.kehboard.entity.db.Session;
import org.kehboard.entity.json.user.DeviceJSON;
import org.kehboard.entity.json.user.DeviceRequestJSON;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface LoginService {
    public String userLogin(String login, String password);
    void userLogout(String token);
    boolean checkToken(String token);
    Integer getUserIdByToken(String token);
}
