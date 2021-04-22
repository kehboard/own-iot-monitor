package org.kehboard.service.impl;

import com.google.common.hash.Hashing;
import org.kehboard.entity.db.Session;
import org.kehboard.entity.db.User;
import org.kehboard.entity.json.Error;
import org.kehboard.entity.json.user.ApiKeyJSON;
import org.kehboard.repository.SessionJPA;
import org.kehboard.repository.UserJPA;
import org.kehboard.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    private static final int sessionLifetime = 3600;
    @Autowired
    private UserJPA userJPA;
    @Autowired
    private SessionJPA sessionJPA;
    @Override
    @Transactional
    public String userLogin(String login, String password) {
        User user = userJPA.selectUserByLoginAndPassword(login,genHashFromString(password));
        if (user != null) {
            String sessionUUID = UUID.randomUUID().toString();
            sessionJPA.save(Session.builder().id(-1).token(genHashFromString(sessionUUID))
                    .expiredAt(Instant.now().getEpochSecond() + sessionLifetime)
                    .userId(user.getId()).build());
            return sessionUUID;
        } else {
            return null;
        }
    }

    @Override
    public void userLogout(String token) {
        Session session = sessionJPA.selectSessionByToken(genHashFromString(token), Instant.now().getEpochSecond());
        session.setExpiredAt((long) 0);
        sessionJPA.save(session);
    }

    @Transactional
    @Override
    public boolean checkToken(String token){
        return sessionJPA.selectSessionByToken(genHashFromString(token), Instant.now().getEpochSecond())!=null;
    }

    @Override
    public Integer getUserIdByToken(String token){
        return sessionJPA.selectSessionByToken(genHashFromString(token), Instant.now().getEpochSecond()).getUserId();
    }
    private String genHashFromString(String stringToHash){
        return Hashing.sha256()
                .hashString(stringToHash, StandardCharsets.UTF_8)
                .toString();
    }
}
