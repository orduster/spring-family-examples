package com.orduster.domain;

public class Role {
    private Long id;

    private String name;

    private String desc_;

    public Role(Long id, String name, String desc_) {
        this.id = id;
        this.name = name;
        this.desc_ = desc_;
    }

    public Role() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDesc_() {
        return desc_;
    }

    public void setDesc_(String desc_) {
        this.desc_ = desc_ == null ? null : desc_.trim();
    }
}