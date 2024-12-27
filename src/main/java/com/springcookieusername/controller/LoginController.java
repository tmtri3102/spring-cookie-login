package com.springcookieusername.controller;

import com.springcookieusername.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes("user")
public class LoginController {
    @ModelAttribute("user")
    public User setUpUserForm() {
        return new User();
    }
    @GetMapping("/login")
    public String index(@CookieValue(value = "setUser", defaultValue = "") String setUser, Model model) {
//        Cookie cookie = new Cookie("setUser", setUser);

//        model.addAttribute("cookieValue", cookie);
        model.addAttribute("cookieValue", setUser);
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@ModelAttribute("user") User user, Model model,
                          @CookieValue(value = "setUser", defaultValue = "") String setUser,
                          HttpServletResponse response, HttpServletRequest request) {
        // First check for null before using getEmail()
        if(user.getEmail() != null && user.getPassword() != null &&
                user.getEmail().equals("admin@mail.com") && user.getPassword().equals("123456")) {

            setUser = user.getEmail();

            Cookie cookie = new Cookie("setUser", setUser);
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);

            // Get all cookies
            Cookie[] cookies = request.getCookies();
            // Only proceed if cookies exist
            if (cookies != null) {
                // Iterate each cookie
                for(Cookie c : cookies){
                    // Find cookie named "setUser" then add to model, if found then use its value
                    if(c.getName().equals("setUser")){
                        model.addAttribute("cookievalue", c);
                    }
                }
            }
            model.addAttribute("message", "Login successful");
        }
        else {
            user.setEmail("");
            Cookie cookie = new Cookie("setUser", setUser);
            model.addAttribute("cookie", cookie);
            model.addAttribute("message", "Login failed");
        }
        return "login";
    }

//    @PostMapping("/doLogin")
//    public String doLogin(@ModelAttribute("user") User user, Model model,
//                          @CookieValue(value = "setUser", defaultValue = "") String setUser,
//                          HttpServletResponse response, HttpServletRequest request) {
//        if(user.getEmail().equals("admin@mail.com") && user.getPassword().equals("123456")) {
//            if(user.getEmail() != null){
//                setUser = user.getEmail();
//            }
//            // create cookie to save user email and set time
//            Cookie cookie = new Cookie("setUser", setUser);
//            cookie.setMaxAge(24 * 60 * 60);
//            // set it in response
//            response.addCookie(cookie);
//            // get all cookies
//            Cookie[] cookies = request.getCookies();
//            // iterate each cookie
//            for(Cookie c : cookies){
//                // find only cookie named "setUser" then add to model, if not then set value empty
//                if(!c.getName().equals("setUser")){
//                    c.setValue("");
//                }
//                model.addAttribute("cookieValue", c);
//            }
//            model.addAttribute("message", "Login successful");
//        }
//        else {
//            user.setEmail("");
//            Cookie cookie = new Cookie("setUser", setUser);
//            model.addAttribute("cookie", cookie);
//            model.addAttribute("message", "Login failed");
//        }
//        return "login";
//    }
}
