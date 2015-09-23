package com.spilnasprava.controller;

import com.spilnasprava.business.service.AreaService;
import com.spilnasprava.business.service.UserService;
import com.spilnasprava.entity.mysql.User;
import com.spilnasprava.entity.mysql.UserKey;
import com.spilnasprava.entity.postgresql.Area;
import com.spilnasprava.entity.postgresql.AreaKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

/**
 * Performs in methods in Controller.
 */
@Controller
@RequestMapping(value = "/")
public class AreaOfUserController {
    private final String messagesAuthenticationFailure = "Invalid username and password!";
    private final String messagesLogoutSuccess = "You've been logged out successfully.";
    private final String messagesProtectedPageAdmin = "This is protected page with the access level \"ROLE_ADMIN\"!";
    private final String messagesProtectedPageUser = "This is protected page with the access level \"ROLE_USER\"!";

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    /**
     * Passing to page 'login' if the user entered the incorrect data or logout from the system
     * Also sends a message informing the user the of result.
     *
     * @param error  if user could not login
     * @param logout if user the logout
     * @return message of result
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView modelAndView = new ModelAndView();
        if (error != null) {
            modelAndView.addObject("error", messagesAuthenticationFailure);
        }

        if (logout != null) {
            modelAndView.addObject("msg", messagesLogoutSuccess);
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     * Save user data in DB
     *
     * @param user
     * @param area
     * @return all data users
     * @throws IOException
     */
    @RequestMapping(value = "registration", produces = "application/json", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute User user, @ModelAttribute Area area) throws IOException {
        String key = UUID.randomUUID().toString();

        UserKey userKey = new UserKey();
        userKey.setKey(key);
        userKey.setUser(user);
        user.setUserKey(userKey);
        userService.addUser(user);

        AreaKey areaKey = new AreaKey();
        areaKey.setKey(key);
        areaKey.setArea(area);
        area.setAreaKeys(areaKey);
        areaService.addArea(area);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "This is protected page!");
        modelAndView.addObject("result", user);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     * Passing to the page with the access level "ROLE_ADMIN"
     *
     * @return all data users
     * @throws IOException
     */
    @RequestMapping(value = "/admin", produces = "application/json", method = RequestMethod.GET)
    public ModelAndView getUsers() throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", messagesProtectedPageAdmin);
        modelAndView.addObject("result", getAllUsers());
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    /**
     * Pulls all data users with MySQL DB and PostgreSQL DB
     * Then compare them and unites in the Map<User, Area>
     * Pass the created Map<User, Area>
     *
     * @return userMap
     */
    public Map<User, Area> getAllUsers() {
        Map<User, Area> userMap = new HashMap<User, Area>();
        List<User> userList = userService.getAllUsers();
        List<Area> areaList = areaService.getAllAreas();
        for (User user : userList) {
            for (Area area : areaList) {
                if (user.getUserKey().getKey().toString().equals(area.getAreaKeys().getKey().toString())) {
                    userMap.put(user, area);
                    break;
                }
            }
        }
        return userMap;
    }

    /**
     * Passing to the page with the access level "ROLE_ADMIN"
     *
     * @return data user
     */
    @RequestMapping(value = "/user", produces = "application/json", method = RequestMethod.GET)
    public ModelAndView getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", messagesProtectedPageUser);
        modelAndView.addObject("result", getDataUser(auth.getName()));
        modelAndView.setViewName("user");
        return modelAndView;
    }

    /**
     * Pulls all data user with MySQL DB and PostgreSQL DB
     * Unites in the Map<User, Area>
     * Pass the created Map<User, Area>
     *
     * @return userMap
     */
    public Map<User, Area> getDataUser(String nickname) {
        Map<User, Area> userMap = new HashMap<User, Area>();
        User user = userService.getUserByName(nickname);
        Area area = null;
        if (user != null) {
            area = areaService.getArea(user.getUserKey().getKey());
            userMap.put(user, area);
        }

        return userMap;
    }
}