package com.ordust.entity;

import java.io.Serializable;
import java.util.Date;

public class UserOnline implements Serializable {

    private static final long serialVersionUID = -3047532658814495889L;
    private String id; //Session id
    private String userId; //用户 id
    private String username; //用户名称
    private String host; //用户主机地址
    private String systemHost; //用户登录时系统 IP
    private String status; //状态
    private Date startTimestamp; // session 创建时间
    private Date lastAccessTime; //session 最后访问时间
    private Long timeout; // 超时时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "UserOnline{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", host='" + host + '\'' +
                ", systemHost='" + systemHost + '\'' +
                ", status='" + status + '\'' +
                ", startTimestamp=" + startTimestamp +
                ", lastAccessTime=" + lastAccessTime +
                ", timeout=" + timeout +
                '}';
    }
}
