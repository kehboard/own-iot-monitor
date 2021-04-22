package org.kehboard.controller;

import org.kehboard.entity.db.Device;
import org.kehboard.entity.db.Measure;
import org.kehboard.repository.DeviceJPA;
import org.kehboard.repository.MeasureNameJPA;
import org.kehboard.repository.UserJPA;
import org.kehboard.service.LoginService;
import org.kehboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class HomeController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private DeviceJPA deviceJPA;
    @Autowired
    private MeasureNameJPA measureNameJPA;

    @GetMapping
    public String index(@CookieValue(name = "authToken", required = false) Cookie authToken, Model model) {
        if (authToken != null) {
            if (loginService.checkToken(authToken.getValue())) {
                return "redirect:/dashboard";
            } else {
                return "login";
            }
        } else {
            return "login";
        }
    }

    @PostMapping("/login")
    public String login(HttpServletResponse response, HttpServletRequest request, @RequestParam("login") String login,
                        @RequestParam("password") String password) {
        String sessionUUID = loginService.userLogin(login, password);
        if (sessionUUID != null) {
            Cookie cookie = new Cookie("authToken", sessionUUID);
            cookie.setMaxAge(1000000);
            cookie.setHttpOnly(false);
            response.addCookie(cookie);
            return "redirect:/dashboard";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, HttpServletRequest request,
                         @CookieValue(name = "authToken", required = false) Cookie authToken) {
        if (authToken != null) {
            if (loginService.checkToken(authToken.getValue())) {
                Cookie cookie = new Cookie("authToken", authToken.getValue());
                cookie.setMaxAge(-1);
                cookie.setHttpOnly(false);
                response.addCookie(cookie);
                loginService.userLogout(authToken.getValue());
                return "redirect:/";
            }
            else{
                return "redirect:/";
            }
        }else{
            return "redirect:/";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(@CookieValue(name = "authToken", required = false) Cookie authToken, Model model) {
        if (authToken != null) {
            if (loginService.checkToken(authToken.getValue())) {
                return "index";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/dashboard/addNewDevice")
    public String addNewDevice(@CookieValue(name = "authToken", required = false) Cookie authToken, Model model) {
        if (authToken != null) {
            if (loginService.checkToken(authToken.getValue())) {
                return "addNewDevice";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/dashboard/viewDevice/{id}")
    public String viewDevice(@CookieValue(name = "authToken", required = false) Cookie authToken, Model model, @PathVariable Integer id) {
        if (authToken != null) {
            if (loginService.checkToken(authToken.getValue())) {
                model.addAttribute("id", id);
                Device d = deviceJPA.getDeviceByIdAndUserId(loginService.getUserIdByToken(authToken.getValue()), id);
                if (d != null) {
                    model.addAttribute("device", d);
                    model.addAttribute("measurements", measureNameJPA.getMeasureNamesByDevId(d.getId()));
                } else {
                    return "redirect:/dashboard";
                }
                return "viewDevice";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/share/{id}")
    public String sharedDevice(@PathVariable Integer id, Model model) {
        Device device = deviceJPA.getDeviceById(id);
        if (device != null) {
            if (device.getIsPublic()) {
                model.addAttribute("device", device);
                return "showPublicDevice";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/dashboard/accountSettings")
    public String accountSettings(@CookieValue(name = "authToken", required = false) Cookie authToken) {
        if (authToken != null) {
            if (loginService.checkToken(authToken.getValue())) {
                return "accountSettings";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }
}
