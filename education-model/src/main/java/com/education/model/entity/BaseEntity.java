package com.education.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类父类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/12 19:21
 */
public abstract class BaseEntity<T extends BaseEntity<T>> implements Serializable {

    private int id;
    Date createDate;
    Date updateDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
