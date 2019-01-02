package com.ordust.controller;

import com.ordust.entity.ResponseBo;
import com.ordust.entity.UserOnline;
import com.ordust.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/online")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @RequestMapping("/index")
    public String online() {
        return "online";
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<UserOnline> list() {
        return sessionService.list();
    }

    @RequestMapping("/forceLogout")
    @ResponseBody
    public ResponseBo forceLogout(String id) {
        try {
            sessionService.forceLogout(id);
            return ResponseBo.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBo.error("踢出用户失败");
        }
    }

}
