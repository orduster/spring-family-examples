package com.ordust.entity;

import java.io.Serializable;

public class Role implements Serializable {

    private static final long serialVersionUID = -1973745100040949051L;

    private Integer id;
    private String name;
    private String memo;

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
