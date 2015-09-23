import com.spilnasprava.business.dao.impl.UserDaoImpl;
import com.spilnasprava.entity.mysql.User;
import com.spilnasprava.entity.mysql.UserKey;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VJKL on 15.09.2015.
 * Tests methods class UserDaoImpl
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUserDaoImpl {
    private static long id = 1l;
    private static User user = new User();
    private static UserKey userKey = new UserKey();
    private static List<User> userList = new ArrayList<User>();

    @Mock
    private Session currentSession;

    @Mock
    private Criteria criteria;

    @Mock
    private Criteria add;

    @Mock
    private SessionFactory sessionFactoryMySQL;

    @InjectMocks
    private UserDaoImpl userDao;

    /**
     * Initializes the necessary objects for testing
     */
    @BeforeClass
    public static void init() {
        user.setId(id);
        user.setNickname("NickTest");
        user.setName("NameTest");
        user.setEmail("EmailTest");
        userKey.setId(id);
        userKey.setKey("key-test");
        userKey.setUser(user);
        user.setUserKey(userKey);

        userList.add(user);
    }

    @Test
    public void testGetUserByName() {
        when(sessionFactoryMySQL.getCurrentSession()).thenReturn(currentSession);
        when(currentSession.createCriteria(User.class)).thenReturn(criteria);
        when(criteria.add((Criterion) anyObject())).thenReturn(add);
        when(add.list()).thenReturn(userList);

        assertThat(user, is(userDao.getUserByName(user.getNickname())));
    }

    @Test
    public void testGetAllUsers() {
        when(sessionFactoryMySQL.getCurrentSession()).thenReturn(currentSession);
        when(currentSession.createCriteria(User.class)).thenReturn(criteria);
        when(criteria.list()).thenReturn(userList);

        assertThat(userDao.getAllUsers(), is(userList));
    }

    @Test
    public void testAddUser() {
        when(sessionFactoryMySQL.getCurrentSession()).thenReturn(currentSession);
        when(userDao.getSessionMySQL().save(user)).thenReturn(1l);
        assertThat(id, is(userDao.addUser(user)));
    }
}