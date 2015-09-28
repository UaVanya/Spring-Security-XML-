import com.spilnasprava.business.dao.impl.AreaDaoImpl;
import com.spilnasprava.entity.postgresql.Area;
import com.spilnasprava.entity.postgresql.AreaKey;
import com.spilnasprava.object.AreaType;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Created by VJKL on 15.09.2015.
 * Tests methods class AreaDaoImpl
 */
@RunWith(MockitoJUnitRunner.class)
public class TestAreaDaoImpl {
    private static long id = 1l;
    private static Area area = new Area();
    private static AreaKey areaKey = new AreaKey();
    private static List<Area> areaList = new ArrayList<Area>();
    private static List<AreaKey> areaKeys = new ArrayList<AreaKey>();

    @Mock
    private Session currentSession;

    @Mock
    private Criteria createCriteria;

    @Mock
    private Criteria add;

    @Mock
    private SessionFactory sessionFactoryMySQL;

    @Mock
    private SessionFactory sessionFactoryPostgreSQL;

    @InjectMocks
    private AreaDaoImpl areaDao;

    /**
     * Initializes the necessary objects for testing
     */
    @BeforeClass
    public static void init() {
        area.setId(id);
        area.setArea(AreaType.AREA3);
        areaKey.setId(id);
        areaKey.setKey("key_area");

        areaKey.setArea(area);
        area.setAreaKeys(areaKey);

        areaList.add(area);
        areaKeys.add(areaKey);
    }

    @Test
    public void testGetArea() {
        when(sessionFactoryPostgreSQL.getCurrentSession()).thenReturn(currentSession);
        when(currentSession.createCriteria(AreaKey.class)).thenReturn(createCriteria);
        when(createCriteria.add((Criterion) anyObject())).thenReturn(add);
        when(add.list()).thenReturn(areaKeys);

        assertThat(areaDao.getArea(area.getAreaKeys().getKey()), is(area));
    }

    @Test
    public void testGetAllArea() {
        when(sessionFactoryPostgreSQL.getCurrentSession()).thenReturn(currentSession);
        when(currentSession.createCriteria(AreaKey.class)).thenReturn(createCriteria);
        when(createCriteria.list()).thenReturn(areaKeys);

        assertThat(areaDao.getAllAreas(), is(areaList));
    }

    @Test
    public void testAddArea() {
        when(sessionFactoryPostgreSQL.getCurrentSession()).thenReturn(currentSession);
        when(currentSession.save(area)).thenReturn(1l);
        assertThat(id, is(areaDao.addArea(area)));
    }
}