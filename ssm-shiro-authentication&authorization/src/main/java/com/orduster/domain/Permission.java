package com.orduster.domain;

import java.io.Serializable;

/**
 * 权限类
 */
public class Permission implements Serializable {

    private static final long serialVersionUID = -5364721707986686368L;

    private Integer id; //id
    private String name;//权限名

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

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
