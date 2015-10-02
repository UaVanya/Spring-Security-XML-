package com.spilnasprava.business.dao.impl;

import com.spilnasprava.business.dao.AreaDAO;
import com.spilnasprava.business.dao.utils.SessionBaseInit;
import com.spilnasprava.entity.postgresql.Area;
import com.spilnasprava.entity.postgresql.AreaKey;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements interface DAO @see com.spilnasprava.business.dao.AreaDAO
 */
public class AreaDaoImpl extends SessionBaseInit implements AreaDAO {
    private final static Logger logger = Logger.getLogger(AreaDaoImpl.class);

    /**
     * Get Area from database.
     *
     * @return list Area
     */
    public Area getArea(String keyId) {
        logger.info("Run get list entity AreaKey by 'key' from DB");
        List listAreasKey = getSessionPostgreSQL().createCriteria(AreaKey.class).add(Restrictions.eq("key", keyId)).list();
        Area area = null;
        if (listAreasKey != null && !listAreasKey.isEmpty()) {
            logger.info("Run pull AreaKey with list AreaKey from DB");
            AreaKey areaKey = (AreaKey) listAreasKey.get(0);
            area = areaKey.getArea();
        } else {
            logger.debug("Failed to list AreaKey. That indicates an error  when saved data user");
        }
        return area;
    }

    /**
     * Get list Areas from database.
     *
     * @return list Area
     */
    public List<Area> getAllAreas() {
        List<Area> areaList = new ArrayList<Area>();
        logger.info("Run get list entity AreaKey");
        List<AreaKey> areaKeys = (List<AreaKey>) getSessionPostgreSQL().createCriteria(AreaKey.class).list();
        for (AreaKey areaKey : areaKeys) {
            logger.info("Run pull AreaKey from list AreaKey");
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
        logger.info("Run save Area entity in DB");
        return (Long) getSessionPostgreSQL().save(area);
    }
}
