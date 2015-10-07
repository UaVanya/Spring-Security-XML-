package com.spilnasprava.object;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by VJKL on 15.09.2015.
 * Checks the getter values of this enum
 */
public class TestAreaType {

    /**
     * Checks the set value in Enum as String @see package.com.spilnasprava.object.AreaType.class
     */
    @Test
    public void testGetValue() {
        AreaType areaType = AreaType.AREA1;
        assertThat(areaType.getValue(), is(0));
    }

    /**
     * Compares different values in Enum as String @see package.com.spilnasprava.object.AreaType.class
     */
    @Test(expected = AssertionError.class)
    public void testExceptionGetValue() {
        AreaType areaType = AreaType.AREA1;
        assertThat(areaType.getValue(), is(2));
    }

    /**
     * Compares value in Enum @see package.com.spilnasprava.object.AreaType.class
     */
    @Test
    public void testParse() {
        AreaType areaType = AreaType.parse(1);
        assertThat(AreaType.AREA2, is(areaType));
    }

    /**
     * Compares the value of a non-existent
     */
    @Test(expected = AssertionError.class)
    public void testExceptionParse() {
        AreaType areaType = AreaType.parse(3);
        assertThat(AreaType.AREA2, is(areaType));
    }
}
