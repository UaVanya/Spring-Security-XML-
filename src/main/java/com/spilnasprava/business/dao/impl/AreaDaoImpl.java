package com.spilnasprava.business.dao.impl;

import com.spilnasprava.business.dao.AreaDAO;
import com.spilnasprava.business.dao.utils.SessionBaseInit;
import com.spilnasprava.entity.postgresql.Area;
import com.spilnasprava.entity.postgresql.AreaKey;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements interface DAO @see com.spilnasprava.business.dao.AreaDAO
 */
public class AreaDaoImpl extends SessionBaseInit implements AreaDAO {

    /**z
     * Get Area from database.
     *
     * @return list Area
     */
    public Area getArea(String keyId) {
        List listAreasKey = getSessionPostgreSQL().createCriteria(AreaKey.class).add(Restrictions.eq("key", keyId)).list();
        Area area = null;
        if (!listAreasKey.isEmpty()) {
            AreaKey areaKey = (AreaKey) listAreasKey.get(0);
            area = areaKey.getArea();
        }
        return area;
    }

    /**
     * Get list Areas from database.
     *
     * @return list Area
     */
    public List<Area> getAllAreas() {
        Session session = getSessionPostgreSQL();
        List<Area> areaList = new ArrayList<Area>();
        List<Area> areas = (List<Area>) session.createCriteria(Area.class).list();
        List<AreaKey> areaKeys = (List<AreaKey>) session.createCriteria(AreaKey.class).list();
        for (AreaKey areaKey : areaKeys) {
            Area area = areaKey.getArea();
            area.setAreaKeys(areaKey);
            areaList.add(area);
        }
        return areaList;
    }

    /**
     * Save User in DB.
     *
     * @return list User
     */
    public long addArea(Area area) {
        return (Long) getSessionPostgreSQL().save(area);
    }
}
