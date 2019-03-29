package com.orduster.shiro;


import com.orduster.service.PermissionService;
import com.orduster.service.RoleService;
import com.orduster.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;


    /**
     * 获取用户的角色和权限：权限控制
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //能进入到这里，说明已经通过验证
        String userName = (String) principals.getPrimaryPrincipal();
        //获取用户的角色和权限列表
        Set<String> roles = roleService.listRoles(userName);
        Set<String> permissions = permissionService.listPermissions(userName);

        //授权对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //把通过 service 获取到的角色和权限放进去
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 验证用户的合法性：登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //从 token 流中获取用户输入的账号密码
        String userName = token.getPrincipal().toString();//获取用户名
        String password = new String((char[]) token.getCredentials());//获取密码

        String passwordInDB = userService.getPassword(userName);//获取数据库中的密码

        //如果为空为账号不存在，不相同就是密码错误，但是都抛出相同的错误信息，免得给破解者提供帮助信息
        if (null == passwordInDB) {
            throw new UnknownAccountException("用户名或者密码错误！");
        }
        if (!passwordInDB.equals(password)) {
            throw new IncorrectCredentialsException("用户名或者密码错误！");
        }

        //认证信息里存放账号密码，getName() 是当前 Realm 的继承方法，通常返回当前类名: ShiroRealm
        return new SimpleAuthenticationInfo(userName, password, getName());
    }
}
