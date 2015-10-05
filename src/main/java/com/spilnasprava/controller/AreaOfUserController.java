package com.spilnasprava.controller;

import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.spilnasprava.ConfigLogging;
import com.spilnasprava.business.service.AreaService;
import com.spilnasprava.business.service.UserService;
import com.spilnasprava.entity.mysql.User;
import com.spilnasprava.entity.mysql.UserKey;
import com.spilnasprava.entity.postgresql.Area;
import com.spilnasprava.entity.postgresql.AreaKey;
import com.spilnasprava.object.AreaType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Performs in methods in Controller.
 */
@Controller
@RequestMapping(value = "/")
public class AreaOfUserController {
    private final Logger logger = Logger.getLogger(AreaOfUserController.class);
    private String code = null;
    private String accessToken = null;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MessageSource fbConfigSource;

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
            modelAndView.addObject("error", messageSource.getMessage("message.authentication.failure", null, Locale.ENGLISH));
        }
        if (logout != null) {
            modelAndView.addObject("msg", messageSource.getMessage("message.logout.success", null, Locale.ENGLISH));
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
        Map<User, Area> userAreaMap = new HashMap<User, Area>();
        String key = UUID.randomUUID().toString();

        UserKey userKey = new UserKey();
        userKey.setKey(key);
        userKey.setUser(user);
        user.setUserKey(userKey);

        if (userService.addUser(user) != 0) {
            AreaKey areaKey = new AreaKey();
            areaKey.setKey(key);
            areaKey.setArea(area);
            area.setAreaKeys(areaKey);
            areaService.addArea(area);
            userAreaMap.put(user, area);
        } else {
            logger.debug("Failed save User in DB");
            return sendErrorMessageToInterface("registration", messageSource.getMessage("messages.er", null, Locale.ENGLISH));
        }
        return sendUserDataToInterface("user", messageSource.getMessage("message.registered.success", null, Locale.ENGLISH), userAreaMap);
    }

    /**
     * Passing to the page with the access level "ROLE_ADMIN"
     *
     * @return all data users
     * @throws IOException
     */
    @RequestMapping(value = "/admin", produces = "application/json", method = RequestMethod.GET)
    public ModelAndView getUsers() throws IOException {
        Map<User, Area> userAreaMap = getAllUsers();
        if (userAreaMap == null) {
            return sendErrorMessageToInterface("admin", messageSource.getMessage("message.error.get.all.user", null, Locale.ENGLISH));
        }
        return sendUserDataToInterface("admin", messageSource.getMessage("message.protected.page.admin", null, Locale.ENGLISH), userAreaMap);
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
        if (userList.size() == 0 && areaList.size() > 0 || userList.size() > 0 && areaList.size() == 0) {
            logger.debug("Does not match number of lists the user and area obtained from the database");
            userMap = null;
        } else {
            for (User user : userList) {
                for (Area area : areaList) {
                    if (user.getUserKey().getKey().toString().equals(area.getAreaKeys().getKey().toString())) {
                        userMap.put(user, area);
                        break;
                    }
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
        Map<User, Area> userMap = getDataUser(auth.getName());
        return sendUserDataToInterface("user", messageSource.getMessage("message.protected.page.user", null, Locale.ENGLISH), userMap);
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

    /**
     * Gets Access Token. And performed by manipulation of the user
     *
     * @param request
     * @param response
     * @return passing to the page with the access level "ROLE_USER"
     * @throws IOException
     */
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView userFromFacebook(HttpServletRequest request, HttpServletResponse response, ModelAndView view) {
        ConfigLogging configLogging = new ConfigLogging();
        configLogging.init();
        Map<User, Area> userAreaMap = null;
        User user = null;

        code = request.getParameter("code");
        if (code == null) {
            try {
                response.setContentType("text/html");
                logger.info("Run request for get code user and pass this code on '/callback'");
                response.sendRedirect(fbConfigSource.getMessage("url.dialog.oauth", null, Locale.ENGLISH)
                        + "?client_id=" + fbConfigSource.getMessage("app.id", null, Locale.ENGLISH)
                        + "&redirect_uri=" + fbConfigSource.getMessage("redirect.url", null, Locale.ENGLISH)
                        + "&scope=" + fbConfigSource.getMessage("scope", null, Locale.ENGLISH));
            } catch (IOException e) {
                logger.error("Error retrieving code : " + e.getMessage());
                return sendErrorMessageToInterface("login", messageSource.getMessage("message.error.retrieving.from.facebook", null, Locale.ENGLISH));
            }
        }

        if (code == null || code.equals("")) {
            logger.info("Unable to get code user from facebook");
            return sendErrorMessageToInterface("login", messageSource.getMessage("message.error.get.code.from.facebook", null, Locale.ENGLISH));
        } else {
            logger.info("Send request for getting user access_token");
            String urlAccessToken = fbConfigSource.getMessage("url.access.token", null, Locale.ENGLISH)
                    + "?client_id=" + fbConfigSource.getMessage("app.id", null, Locale.ENGLISH)
                    + "&redirect_uri=" + fbConfigSource.getMessage("redirect.url", null, Locale.ENGLISH)
                    + "&client_secret="+ fbConfigSource.getMessage("app.secret", null, Locale.ENGLISH)
                    + "&code=" + code;
            response.setContentType("text/html");
            accessToken = getAccessToken(urlAccessToken);

            if (accessToken == null) {
                logger.error("Unable to get access token user from facebook");
                return sendErrorMessageToInterface("login", messageSource.getMessage("message.error.retrieving.from.facebook", null, Locale.ENGLISH));
            }

            user = getUserFromFacebook(accessToken);

            if (userService.getUserByName(user.getNickname()) == null) {
                logger.info("Save user who first login through facebook. Id user on facebook : " + user.getFacebookId());
                userAreaMap = addUserFromFacebook(user);
                if (userAreaMap == null) {
                    return sendErrorMessageToInterface("login", messageSource.getMessage("message.error.registration", null, Locale.ENGLISH));
                }
            } else {
                logger.info("Run get all data user");
                userAreaMap = getDataUser(user.getNickname());
            }
        }
        return sendUserDataToInterface("user", messageSource.getMessage("message.protected.page.user", null, Locale.ENGLISH), userAreaMap);
    }

    /**
     * Parsing string and pull access token user
     *
     * @param urlAccessToken
     * @return access token user
     * @throws IOException
     */
    public String getAccessToken(String urlAccessToken) {
        String token = null;
        URL url = null;
        URLConnection urlConnection = null;
        try {
            url = new URL(urlAccessToken);
        } catch (MalformedURLException e) {
            logger.error(e);
        }
        try {
            urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(10000);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine + "\n");
            }
            token = stringBuilder.toString().substring(13, stringBuilder.toString().indexOf('&'));
            bufferedReader.close();
        } catch (IOException e) {
            logger.error(e);
        }
//        logger.info("Handles string and return received code");
        return token;
    }

    /**
     * Save user data in DB
     * Unites in the data user in Map<User, Area> and pass the created Map<User, Area>
     *
     * @param user
     * @return data user
     */
    public Map<User, Area> addUserFromFacebook(User user) {
        String key = UUID.randomUUID().toString();
        Map<User, Area> userAreaMap = null;

        UserKey userKey = new UserKey();
        userKey.setKey(key);
        userKey.setUser(user);
        user.setUserKey(userKey);

        if (userService.addUser(user) != 0) {
            Area area = new Area();
            area.setArea(AreaType.AREA1);
            AreaKey areaKey = new AreaKey();
            area.setAreaKeys(areaKey);
            areaKey.setKey(key);
            areaKey.setArea(area);
            area.setAreaKeys(areaKey);
            areaService.addArea(area);
            userAreaMap = new HashMap<User, Area>();
            userAreaMap.put(user, area);
        } else {
            logger.debug("Failed save User from facebook in DB");
        }
        return userAreaMap;
    }

    /**
     * Pulls data user from facebook
     *
     * @param token
     * @return user data from facebook
     */
    public User getUserFromFacebook(String token) {
        DefaultFacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        logger.info("Run get info about user from facebook");
        com.restfb.types.User userRestFB = facebookClient.fetchObject("me", com.restfb.types.User.class);
        logger.info("Run get email user from facebook");
        com.restfb.types.User userEmail = facebookClient.fetchObject("me", com.restfb.types.User.class, Parameter.with("fields", "email"));

        User user = new User();
        logger.info("Establish parameter user and return user");
        user.setNickname(userRestFB.getName());
        user.setPassword(userEmail.getEmail());
        user.setFacebookId(userRestFB.getId().toString());
        return user;
    }

    /**
     * Redirect error message on interface
     *
     * @param error
     * @return error message on interface
     */
    public ModelAndView sendErrorMessageToInterface(String pathToInterface, String error) {
        return new ModelAndView(pathToInterface, "error", error);
    }

    /**
     * Redirect user data and message on interface
     *
     * @param pathToInterface
     * @param message
     * @param userAreaMap
     * @return modelAndView
     */
    public ModelAndView sendUserDataToInterface(String pathToInterface, String message, Map<User, Area> userAreaMap) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", message);
        modelAndView.addObject("result", userAreaMap);
        modelAndView.setViewName(pathToInterface);
        logger.info("Pass data for display on the UI");
        return modelAndView;
    }
}