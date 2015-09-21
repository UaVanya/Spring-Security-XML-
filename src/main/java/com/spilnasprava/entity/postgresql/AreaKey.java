package com.spilnasprava.entity.postgresql;

import com.spilnasprava.entity.mysql.UserKey;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Create entity for table area_key with PostgreSQL DB.
 */
@Entity
@Table(name = "area_key")
public class AreaKey implements Serializable {
    private long id;
    private String key;
    @Column(name = "area_id")
    private Area area;

    /**
     * @return current id the areaKey
     */
    @Id
    @Column(name="id_area_key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    /**
     * Sets the id the areaKey
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return current key the areaKey
     */
    @Column(name = "key_id")
    public String getKey() {
        return key;
    }

    /**
     * Sets the key the areaKey
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return current area_id the areaKey
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id")
    public Area getArea() {
        return area;
    }

    /**
     * Sets the area_id the areaKey
     *
     * @param area
     */
    public void setArea(Area area) {
        this.area = area;
    }
}
