package com.fawns.app.bean;

/**
 * Project Fawns
 * Package com.fawns.app.bean
 * Author Mengjiaxin
 * Date 2016/4/12 15:35
 * Desc 请用一句话来描述作用
 */
public class BaseEntity {

    private String id;
    private String name;

    public BaseEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
