package com.ordust.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequiresPermissions("deleteOrder")
    @RequestMapping("deleteOrder")
    public String deleteOrder() {
        return "deleteOrder";
    }

    @RequiresRoles("productManager")
    @RequestMapping("deleteProduct")
    public String deleteProduct() {
        return "deleteProduct";
    }

    @RequestMapping("listProduct")
    public String listProduct() {
        return "listProduct";
    }
}
