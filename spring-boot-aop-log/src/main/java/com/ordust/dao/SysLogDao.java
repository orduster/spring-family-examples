package com.ordust.dao;

import com.ordust.domain.SysLog;

public interface SysLogDao {
    /*保存日志*/
    void saveLog(SysLog sysLog);
}
