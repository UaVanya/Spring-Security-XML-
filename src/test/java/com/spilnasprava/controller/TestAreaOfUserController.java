import com.spilnasprava.business.service.AreaService;
import com.spilnasprava.business.service.UserService;
import com.spilnasprava.controller.AreaOfUserController;
import com.spilnasprava.entity.mysql.User;
import com.spilnasprava.entity.mysql.UserKey;
import com.spilnasprava.entity.postgresql.Area;
import com.spilnasprava.entity.postgresql.AreaKey;
import com.spilnasprava.object.AreaType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Created by VJKL on 15.09.2015.
 * Checks the methods of AreaOfUserController.class
 */
@RunWith(MockitoJUnitRunner.class)
public class TestAreaOfUserController {
    private static Map<User, Area> userMap = new HashMap<User, Area>();
    private static User user = new User();
    private static UserKey userKey = new UserKey();
    private static Area area = new Area();
    private static AreaKey areaKey = new AreaKey();
    private static List<User> userList = new ArrayList<User>();
    private static List<Area> areaList = new ArrayList<Area>();

    @Mock
    private UserService userService;

    @Mock
    private AreaService areaService;

    @InjectMocks
    private AreaOfUserController controller;

    /**
     * Initializes the necessary objects for testing
     */
    @BeforeClass
    public static void init() {
        String keyId = "key-test";

        user.setId(1l);
        user.setName("NameTest");
        user.setEmail("EmailTest");
        userKey.setId(1l);
        userKey.setKey(keyId);
        userKey.setUser(user);
        user.setUserKey(userKey);

        area.setId(1l);
        area.setArea(AreaType.AREA1);
        areaKey.setId(1l);
        areaKey.setKey(keyId);
        areaKey.setArea(area);
        area.setAreaKeys(areaKey);

        userList.add(user);
        areaList.add(area);

        userMap.put(user, area);
    }

    @Test
    public void testLogin() {
        String messagesAuthenticationFailure = "Invalid username and password!";
        String messagesLogoutSuccess = "You've been logged out successfully.";

        ModelAndView model = controller.login("error", "logout");
        String messageError = (String) model.getModel().get("error");
        String messageLogout = (String) model.getModel().get("msg");

        assertThat(messagesAuthenticationFailure, is(messageError));
        assertThat(messagesLogoutSuccess, is(messageLogout));
    }

    @Test
    public void testAddUser() throws IOException {
        when(userService.addUser(user)).thenReturn(user.getId());
        when(areaService.addArea(area)).thenReturn(area.getId());
        when(userService.getAllUsers()).thenReturn(userList);
        when(areaService.getAllAreas()).thenReturn(areaList);

        Map<User, Area> userAreaMap = (Map<User, Area>) controller.addUser(user, area).getModel().get("result");
        assertThat(userMap, is(userAreaMap));
    }

    @Test
    public void testGetUsers() throws IOException {
        when(userService.getAllUsers()).thenReturn(userList);
        when(areaService.getAllAreas()).thenReturn(areaList);
        Map<User, Area> userAreaMap = (Map<User, Area>) controller.getUsers().getModel().get("result");
        assertThat(userMap, is(userAreaMap));
    }

    @Test
    public void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(userList);
        when(areaService.getAllAreas()).thenReturn(areaList);
        Map<User, Area> userAreaMap = controller.getAllUsers();

        assertThat(userMap, is(userAreaMap));
    }

    @Test
    public void testGetUser() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getName(), ""));
        when(userService.getUserByName(user.getName())).thenReturn(user);
        when(areaService.getArea(area.getAreaKeys().getKey())).thenReturn(area);
        Map<User, Area> userAreaMap = (Map<User, Area>) controller.getUser().getModel().get("result");

        assertThat(userMap, is(userAreaMap));
    }

    @Test
    public void testGetDataUser() {
        when(userService.getUserByName(user.getNickname())).thenReturn(user);
        when(areaService.getArea(area.getAreaKeys().getKey())).thenReturn(area);
        Map<User, Area> userAreaMap = controller.getDataUser(user.getNickname());

        assertThat(userMap, is(userAreaMap));
    }

    @Test
    public void testAddUserFromFacebook() {
        when(userService.addUser(user)).thenReturn(user.getId());
        Map<User, Area> userAreaMap = controller.addUserFromFacebook(user);
        User userFromFacebook = new User();
        if (!userAreaMap.isEmpty()) {
            Set<User> userSet = (Set<User>) userAreaMap.keySet();
            for (User iterUser : userSet) {
                userFromFacebook = iterUser;
            }
        }
        assertThat(user, is(userFromFacebook));
    }
}