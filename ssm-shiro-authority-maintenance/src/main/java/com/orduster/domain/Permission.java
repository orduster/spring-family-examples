package com.orduster.domain;

public class Permission {
    private Long id;

    private String name;

    private String desc_;

    private String url;

    public Permission(Long id, String name, String desc_, String url) {
        this.id = id;
        this.name = name;
        this.desc_ = desc_;
        this.url = url;
    }

    public Permission() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}