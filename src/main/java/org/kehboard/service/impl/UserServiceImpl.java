package org.kehboard.service.impl;

import com.google.common.hash.Hashing;
import org.kehboard.entity.db.*;
import org.kehboard.entity.json.Error;
import org.kehboard.entity.json.user.*;
import org.kehboard.repository.*;
import org.kehboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final int sessionLifetime = 3600;
    @Autowired
    private DeviceJPA deviceJPA;
    @Autowired
    private MeasureNameJPA measureNameJPA;
    @Autowired
    private MeasureJPA measureJPA;
    @Autowired
    private UserJPA userJPA;
    @Autowired
    private SessionJPA sessionJPA;

    @Transactional
    @Override
    public ResponseEntity<?> createDevice(DeviceRequestJSON deviceRequestJSON, String apikey) {
        Session session = getSession(apikey);
        if (session != null) {
            extendSession(apikey);
            DeviceJSON deviceJSON = deviceRequestJSON.getDevice();
            if (deviceJSON.getIsPublic() != null && deviceJSON.getDeviceName() != null) {
                Device device = deviceJPA.save(Device.builder().deviceName(deviceJSON.getDeviceName())
                        .isPublic(deviceJSON.getIsPublic())
                        .altitude(deviceJSON.getAltitude())
                        .latitude(deviceJSON.getLatitude())
                        .longitude(deviceJSON.getLongitude())
                        .userId(session.getUserId())
                        .apiKey(UUID.randomUUID().toString())
                        .description(deviceJSON.getDescription())
                        .build());
                return ResponseEntity.status(HttpStatus.OK).body(device);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Error("isPublic, altitude, latitude, longitude, deviceName not empty"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new Error("Invalid access token"));
        }
    }

    @Override
    public ResponseEntity<?> editDevice(@PathVariable(required = true) Integer id, @RequestBody DeviceJSON deviceJSON,
                                        String apikey) {
        Session session = getSession(apikey);
        if (session != null) {
            System.out.println(deviceJSON);
            Device device = deviceJPA.getDeviceById(id);
            if (device != null) {
                device = deviceJPA.save(Device.builder()
                        .id(id)
                        .deviceName(deviceJSON.getDeviceName())
                        .isPublic(deviceJSON.getIsPublic())
                        .altitude(deviceJSON.getAltitude())
                        .latitude(deviceJSON.getLatitude())
                        .longitude(deviceJSON.getLongitude())
                        .userId(session.getUserId())
                        .apiKey(device.getApiKey())
                        .description(deviceJSON.getDescription())
                        .build());
                return ResponseEntity.status(HttpStatus.OK).body(device);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("device not found"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Invalid access token"));
        }
    }

    @Override
    public ResponseEntity<?> resetApiKeyDevice(Integer id, String apikey) {
        Session session = getSession(apikey);
        if (session != null) {
            extendSession(apikey);
            String newApiKey = UUID.randomUUID().toString();
            Device device = deviceJPA.getDeviceById(id);
            if (device.getUserId().equals(session.getUserId())) {
                device.setApiKey(newApiKey);
                deviceJPA.save(device);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiKeyJSON(newApiKey));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Not your device"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Invalid access token"));
        }
    }

    @Override
    public ResponseEntity<?> deleteDevice(Integer id, String apikey) {
        Session session = getSession(apikey);
        if (session != null) {
            extendSession(apikey);
            Device device = deviceJPA.getDeviceByIdAndUserId(session.getUserId(), id);
            if (device != null) {
                deviceJPA.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("device not found"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("invalid access token"));
        }
    }

    @Override
    public ResponseEntity<?> getDevices(String apikey) {
        Session session = getSession(apikey);
        if (session != null) {
            extendSession(apikey);
            List<Device> devices = deviceJPA.getDevicesByUserId(session.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(devices);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Invalid access token"));
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> getDeviceById(String apikey, Integer id) {
        Device device = deviceJPA.getDeviceById(id);
        if (device != null) {
            if (device.getIsPublic()) {
                return ResponseEntity.status(HttpStatus.OK).body(device);
            } else {
                Session session = getSession(apikey);
                if (session != null) {
                    extendSession(apikey);
                    return ResponseEntity.status(HttpStatus.OK).body(device);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Not implemented now"));
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("Device not exist"));
        }
    }

    @Override
    public ResponseEntity<?> getMeasuredDataById(@PathVariable(required = true) Integer id, String apikey) {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new Error("Not implemented now"));
    }

    @Transactional
    @Override
    public ResponseEntity<?> userLogin(String login, String password) {
        User user = userJPA.selectUserByLoginAndPassword(login, genHashFromString(password));
        if (user != null) {
            String sessionUUID = UUID.randomUUID().toString();
            sessionJPA.save(Session.builder().id(-1).token(genHashFromString(sessionUUID))
                    .expiredAt(Instant.now().getEpochSecond() + sessionLifetime)
                    .userId(user.getId()).build());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiKeyJSON(sessionUUID));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Incorrect login or password"));
        }
    }

    @Override
    public ResponseEntity<?> userLogout(String token) {
        return null;
    }

    @Override
    public ResponseEntity<?> getLatestDataByDevId(Integer id, String apikey, Integer amount) {
        if (apikey == null){
            return getLatestDataByDevId(id,amount);
        }
        Session session = getSession(apikey);
        if (session != null) {
            extendSession(apikey);
            return getLatestDataByDevId(id,amount);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Invalid access token"));
        }
    }

    private ResponseEntity<?> getLatestDataByDevId(Integer id, Integer amount) {
        Device device = deviceJPA.getDeviceById(id);
        if (device != null) {
            List<MeasureName> measureName = measureNameJPA.getMeasureNamesByDevId(id);
            List<MeasureNameJSON> measureNameJSONS = new LinkedList<>();
            Map<Integer, List<GetMeasureJSON>> measureJSONS = new HashMap<>();
            for (MeasureName mn : measureName) {
                measureNameJSONS.add(MeasureNameJSON.builder()
                        .id(mn.getId())
                        .iotName(mn.getIotName())
                        .measureName(mn.getMeasureName())
                        .description(mn.getDescription())
                        .measureSymbol(mn.getMeasureSymbol()).build());
            }
            for (MeasureName m :
                    measureName) {
                PageRequest pageRequest = PageRequest.of(0, 1000);
                List<Measure> page = measureJPA.getMeasureByMeasureNameId(m.getId(), pageRequest).getContent();
                List<GetMeasureJSON> tmpList = new LinkedList<>();
                for (Measure ms :
                        page) {
                    tmpList.add(GetMeasureJSON.builder()
                            .unixtime(ms.getUnixTime())
                            .value(ms.getData())
                            .id(ms.getId()).build());
                }
                measureJSONS.put(m.getId(), tmpList);
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    MeasureRequestJSON.builder().measureNames(measureNameJSONS).measures(measureJSONS).build());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Device not exists"));
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> addMeasurement(Integer id, String apikey, MeasureNameJSON measureNameJSON) {
        Session session = getSession(apikey);
        if (session != null) {
            extendSession(apikey);
            Device device = deviceJPA.getDeviceByIdAndUserId(session.getUserId(), id);
            if (device != null) {
                MeasureName measureName = measureNameJPA.save(MeasureName.builder().id(-1)
                        .devId(id)
                        .description(measureNameJSON.getDescription())
                        .iotName(measureNameJSON.getIotName())
                        .measureName(measureNameJSON.getMeasureName())
                        .measureSymbol(measureNameJSON.getMeasureSymbol()).
                                userId(session.getUserId()).build());
                return ResponseEntity.status(HttpStatus.OK).body(measureName);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("device not found"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("invalid access token"));
        }

    }

    @Override
    public ResponseEntity<?> getMeasurement(Integer id, String apikey, Integer mid) {
        Session session = getSession(apikey);
        if (session != null) {
            extendSession(apikey);
            Device device = deviceJPA.getDeviceByIdAndUserId(session.getUserId(), id);
            if (device != null) {
                MeasureName measureName = measureNameJPA.getMeasureNamesByDevIdAndId(id, mid);
                if (measureName != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(measureName);
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("measure not found"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("device not found"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("invalid access token"));
        }
    }

    @Override
    public ResponseEntity<?> editMeasurement(Integer id, String apikey, Integer mid, MeasureNameJSON measureNameJSON) {
        Session session = getSession(apikey);
        if (session != null) {
            extendSession(apikey);
            Device device = deviceJPA.getDeviceByIdAndUserId(session.getUserId(), id);
            if (device != null) {
                MeasureName measureName = measureNameJPA.getMeasureNamesByDevIdAndId(id, mid);
                if (measureName != null) {
                    measureName = measureNameJPA.save(MeasureName.builder().id(mid)
                            .devId(id)
                            .description(measureNameJSON.getDescription())
                            .iotName(measureNameJSON.getIotName())
                            .measureName(measureNameJSON.getMeasureName())
                            .measureSymbol(measureNameJSON.getMeasureSymbol()).
                                    userId(session.getUserId()).build());
                    return ResponseEntity.status(HttpStatus.OK).body(measureName);
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("measure not found"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("device not found"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("invalid access token"));
        }
    }

    @Override
    public ResponseEntity<?> removeMeasurement(Integer id, String apikey, Integer mid) {
        Session session = getSession(apikey);
        if (session != null) {
            extendSession(apikey);
            Device device = deviceJPA.getDeviceByIdAndUserId(session.getUserId(), id);
            if (device != null) {
                MeasureName measureName = measureNameJPA.getMeasureNamesByDevIdAndId(id, mid);
                if (measureName != null) {
                    measureNameJPA.deleteById(mid);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("measure not found"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("device not found"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("invalid access token"));
        }
    }

    @Override
    public ResponseEntity<?> userChangePassword(String apikey, ChangePassword changePassword) {
        Session session = getSession(apikey);
        if (session != null) {
            extendSession(apikey);
            User u = userJPA.selectUserById(session.getUserId());
            if(genHashFromString(changePassword.getOldPassword()).equals(u.getPassword())){
                u.setPassword(genHashFromString(changePassword.getNewPassword()));
                userJPA.save(u);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("old password mismatch"));
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("invalid access token"));
        }
    }

    private void extendSession(String sessionToken) {
        Session session = sessionJPA.selectSessionByToken(genHashFromString(sessionToken), Instant.now().getEpochSecond());
        session.setExpiredAt(Instant.now().getEpochSecond() + sessionLifetime);
        sessionJPA.save(session);
    }

    private Session getSession(String sessionToken) {
        Session session = sessionJPA.selectSessionByToken(genHashFromString(sessionToken), Instant.now().getEpochSecond());
        return session;
    }

    private String genHashFromString(String stringToHash){
        return Hashing.sha256()
                .hashString(stringToHash, StandardCharsets.UTF_8)
                .toString();
    }
}
