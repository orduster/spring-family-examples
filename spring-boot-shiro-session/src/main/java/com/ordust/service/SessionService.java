package com.ordust.service;

import com.ordust.entity.UserOnline;

import java.util.List;

public interface SessionService {
    List<UserOnline> list();

    boolean forceLogout(String sessionId);
}
