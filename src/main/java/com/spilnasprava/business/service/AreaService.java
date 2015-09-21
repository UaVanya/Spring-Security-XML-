package com.spilnasprava.business.service;

import com.spilnasprava.entity.postgresql.Area;
import com.spilnasprava.entity.postgresql.AreaKey;

import java.util.List;

/**
 * Create Service interface to work with the Area
 */
public interface AreaService {

    /**
     * Get Area from database.
     *
     * @return list Area
     */
    Area getArea(String keyId);

    /**
     * Get list Areas from database.
     *
     * @return list Area
     */
    List<Area> getAllAreas();

    /**
     * Save User in DB.
     *
     * @return list User
     */
    long addArea(Area area);
}
