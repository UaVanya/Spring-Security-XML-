package com.spilnasprava.business.dao.impl;

import com.spilnasprava.entity.postgresql.Area;
import com.spilnasprava.entity.postgresql.AreaKey;
import com.spilnasprava.object.AreaType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    private static Area area = new Area();
    private static AreaKey areaKey = new AreaKey();

    @Mock
    AreaDaoImpl areaDao;

    @BeforeClass
    public static void init() {
        area.setId(1l);
        area.setArea(AreaType.AREA3);
        areaKey.setId(1l);
        areaKey.setKey("key_area");
        areaKey.setArea(area);
        area.setAreaKeys(areaKey);
    }

    @Test
    public void testGetAllArea() {
        List<Area> areaList = new ArrayList<Area>();
        when(areaDao.getAllAreas()).thenReturn(areaList);
        assertThat(areaList, is(areaDao.getAllAreas()));
    }

    @Test
    public void testAddArea() {
        long areaId = 1l;
        when(areaDao.addArea(area)).thenReturn(areaId);
        assertThat(areaId, is(areaDao.addArea(area)));
    }
}
