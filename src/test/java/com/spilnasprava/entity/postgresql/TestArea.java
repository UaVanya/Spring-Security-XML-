package com.spilnasprava.entity.postgresql;

import com.spilnasprava.object.AreaType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by VJKL on 15.09.2015.
 * Checks the setter/getter values of this class entity
 */
public class TestArea {

    private Area area = new Area();

    /**
     * Check the correctly established 'id'
     */
    @Test
    public void testGetAndSetId() {
        long id = 1;
        area.setId(id);
        assertThat(area.getId(), is(id));
    }

    /**
     * Check the correctly established 'area'
     */
    @Test
    public void testGetAndSetArea() {
        AreaType areaType = AreaType.AREA1;
        String levelArea = "EmailTest";
        area.setArea(areaType);
        assertThat(area.getArea(), is(areaType));
    }

    /**
     * Check the correctly established 'area_id'
     */
    @Test
    public void testGetAndSetAreaKey() {
        AreaKey areaKey = new AreaKey();
        long id = 1;
        areaKey.setId(id);
        area.setAreaKeys(areaKey);
        assertThat(area.getAreaKeys(), is(areaKey));
    }
}
