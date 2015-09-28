package com.spilnasprava.controller;

import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.spilnasprava.business.service.AreaService;
import com.spilnasprava.business.service.UserService;
import com.spilnasprava.entity.mysql.User;
import com.spilnasprava.entity.mysql.UserKey;
import com.spilnasprava.entity.postgresql.Area;
import com.spilnasprava.entity.postgresql.AreaKey;
import com.spilnasprava.object.AreaType;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.net.URL;
import java.net.URLConnection;
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

    private String accessToken;
    private static final String SCOPE = "email,publish_actions,user_about_me,user_birthday,user_posts,manage_pages";
    private static final String REDIRECT_URI = "http://localhost:8080/callback";
    private static final String APP_ID = "1045832752134158";
    private static final String APP_SECRET = "d228b4a2a074dffdc211fcf3870eb578";
    private static final String URL_DIALOG_OAUTH = "https://www.facebook.com/dialog/oauth";
    private static final String URL_ACCESS_TOKEN = "https://graph.facebook.com/oauth/access_token";

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
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "This is protected page!");
        modelAndView.addObject("result", userAreaMap);
        modelAndView.setViewName("user");
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

    /**
     * Performs a redirect to the login page on Facebook
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public void getSignin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        response.sendRedirect(URL_DIALOG_OAUTH + "?client_id=" + APP_ID
                + "&redirect_uri=" + REDIRECT_URI + "&scope=" + SCOPE);
    }

    /**
     * Gets Access Token. And performed by manipulation of the user
     *
     * @param request
     * @param response
     * @return passing to the page with the access level "ROLE_USER"
     * @throws IOException
     * @throws FacebookException
     */
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public ModelAndView userFromFacebook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        String code = request.getParameter("code");
        User user = null;
        if (code == null || code.equals("")) {
            response.sendRedirect("http://localhost:8080/login.jsp?error");
        } else {
            String urlAccessToken = URL_ACCESS_TOKEN + "?client_id=" + APP_ID
                    + "&redirect_uri=" + REDIRECT_URI + "&client_secret="
                    + APP_SECRET + "&code=" + code;
            response.setContentType("text/html");

            accessToken = getAccessToken(urlAccessToken);

            user = getUserFromFacebook(accessToken);
            User userCheck = userService.getUserByName(user.getNickname());
            if (userCheck == null) {
                Map<User, Area> userAreaMap = addUserFromFacebook(user);
                modelAndView.addObject("result", userAreaMap);
            } else modelAndView.addObject("result", getDataUser(user.getNickname()));
        }
        modelAndView.addObject("message", messagesProtectedPageUser);
        modelAndView.setViewName("user");
        return modelAndView;
    }

    /**
     * Parsing string and pull access token user
     *
     * @param urlAccessToken
     * @return access token user
     * @throws IOException
     */
    public String getAccessToken(String urlAccessToken) throws IOException {
        URL url = new URL(urlAccessToken);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setConnectTimeout(10000);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine + "\n");
        }

        bufferedReader.close();
        return stringBuilder.toString().substring(13, stringBuilder.toString().indexOf('&'));
    }

    /**
     * Save user data in DB
     * Unites in the data user in Map<User, Area> and pass the created Map<User, Area>
     *
     * @param user
     * @return data user
     * @throws FacebookException
     */
    public Map<User, Area> addUserFromFacebook(User user) {
        String key = UUID.randomUUID().toString();
        Map<User, Area> userAreaMap = new HashMap<User, Area>();

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
            userAreaMap.put(user, area);
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
        com.restfb.types.User userRestFB = facebookClient.fetchObject("me", com.restfb.types.User.class);
        com.restfb.types.User userEmail = facebookClient.fetchObject("me", com.restfb.types.User.class, Parameter.with("fields", "email"));

        User user = new User();
        user.setNickname(userRestFB.getName());
        user.setPassword(userEmail.getEmail());
        user.setFacebookId(userRestFB.getId().toString());
        return user;
    }
}