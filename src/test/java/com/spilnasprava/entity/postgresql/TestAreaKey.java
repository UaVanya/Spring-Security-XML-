package com.spilnasprava.entity.postgresql;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by VJKL on 15.09.2015.
 * Checks the setter/getter values of this class entity
 */
public class TestAreaKey {
    private AreaKey areaKey = new AreaKey();

    /**
     * Check the correctly established 'id'
     */
    @Test
    public void testGetAndSetId() {
        long id = 1;
        areaKey.setId(id);
        assertThat(areaKey.getId(), is(id));
    }

    /**
     * Check the correctly established 'key'
     */
    @Test
    public void testGetAndSetKey() {
        String key = "KeyTest";
        areaKey.setKey(key);
        assertThat(areaKey.getKey(), is(key));
    }

    /**
     * Check the correctly established 'area_id'
     */
    @Test
    public void testGetAndSetArea() {
        Area area = new Area();
        long id = 1;
        area.setId(1);
        areaKey.setArea(area);
        assertThat(areaKey.getArea(), is(area));
    }
}
