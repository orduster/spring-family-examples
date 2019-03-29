package com.orduster.domain;

import java.io.Serializable;

/**
 * 角色类
 */
public class Role implements Serializable {

    private static final long serialVersionUID = -9172990250853416154L;

    private Integer id; //id
    private String name;//角色名
    private String description;//描述

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
