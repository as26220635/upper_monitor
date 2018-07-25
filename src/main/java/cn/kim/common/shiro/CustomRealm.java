package cn.kim.common.shiro;

import cn.kim.entity.ActiveUser;
import cn.kim.exception.FrozenException;
import cn.kim.exception.NullRoleFrozenException;
import cn.kim.exception.RoleFrozenException;
import cn.kim.service.ManagerService;
import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.Constants;
import cn.kim.entity.ActiveUser;
import cn.kim.exception.FrozenException;
import cn.kim.exception.NullRoleFrozenException;
import cn.kim.exception.RoleFrozenException;
import cn.kim.service.ManagerService;
import cn.kim.service.MenuService;
import cn.kim.util.*;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

/**
 * Created by 余庚鑫 on 2017/2/21.
 * shiro 域
 */
public class CustomRealm extends AuthorizingRealm {

    private static final String OR_OPERATOR = " OR ";
    private static final String AND_OPERATOR = " AND ";
    private static final String NOT_OPERATOR = "NOT ";

    @Autowired
    private ManagerService managerService;


    // 设置realm的名称
    @Override
    public void setName(String name) {
        super.setName("customRealm");
    }

    // 用于认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        // token是用户输入的
        // 第一步从token中取出身份信息
        CaptchaUsernamePasswordToken cupToken = (CaptchaUsernamePasswordToken) token;

        //查询账号
        Map<String, Object> user = managerService.queryLoginUsername(cupToken.getUsername());

        if (ValidateUtil.isEmpty(user)) {
            return null;
        }

        //用户冻结
        if (TextUtil.toInt(user.get("IS_STATUS")) != Attribute.STATUS_SUCCESS || TextUtil.toInt(user.get("SO_IS_STATUS")) != Attribute.STATUS_SUCCESS) {
            throw new FrozenException();
        }

        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("SO_ID", user.get("ID"));
        List<Map<String, Object>> roles = managerService.queryOperatorRole(paramMap);

        //不能为没有角色
        if (ValidateUtil.isEmpty(roles)) {
            throw new NullRoleFrozenException();
        }
        int forzenSize = 0;
        for (Map<String, Object> role : roles) {
            if (TextUtil.toString(role.get("IS_STATUS")).equals(Attribute.STATUS_ERROR)) {
                forzenSize++;
            }
        }
        //全部角色都被冻结
        if (forzenSize == roles.size()) {
            throw new RoleFrozenException();
        }

        // 从数据库查询到密码
        String password = TextUtil.toString(user.get("SO_PASSWORD"));

        String salt = TextUtil.toString(user.get("SO_SALT"));

        return new SimpleAuthenticationInfo(cupToken.getUsername(), password, new MySimpleByteSource(salt), this.getName());
    }

    // 用于授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        // TODO Auto-generated method stub
        ActiveUser activeUser = (ActiveUser) SessionUtil.get(Constants.SESSION_USERNAME);

        List<String> permissions = new ArrayList<>();

        //添加菜单权限
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("SO_ID", activeUser.getId());
        List<Map<String, Object>> menus = managerService.queryOperatorMenu(paramMap);
        if (!ValidateUtil.isEmpty(menus)) {
            menus.forEach(menu -> {
                permissions.add(TextUtil.toString(menu.get("SM_CODE")));
            });
        }
        //查询按钮权限
        paramMap.clear();
        paramMap.put("SO_ID", activeUser.getId());
        activeUser.setMenuButtons(managerService.queryOperatorMenuButtonPrecode(paramMap));

        //添加按钮权限
        if (!ValidateUtil.isEmpty(activeUser.getMenuButtons())) {
            activeUser.getMenuButtons().forEach(precode -> {
                permissions.add(TextUtil.toString(precode.get("PRECODE")));
            });
        }

        permissions.removeIf(permission -> !permission.contains(":"));

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    /**
     * 支持or and not 关键词  不支持and or混用
     *
     * @param principals
     * @param permission
     * @return
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        try {
            if (permission.contains(OR_OPERATOR)) {
                String[] permissions = permission.split(OR_OPERATOR);
                for (String orPermission : permissions) {
                    if (isPermittedWithNotOperator(principals, orPermission)) {
                        return true;
                    }
                }
                return false;
            } else if (permission.contains(AND_OPERATOR)) {
                String[] permissions = permission.split(AND_OPERATOR);
                for (String orPermission : permissions) {
                    if (!isPermittedWithNotOperator(principals, orPermission)) {
                        return false;
                    }
                }
                return true;
            } else {
                return isPermittedWithNotOperator(principals, permission);
            }
        } catch (Exception e) {
        }
        return super.isPermitted(principals, permission);
    }

    private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
        if (permission.startsWith(NOT_OPERATOR)) {
            return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
        } else {
            return super.isPermitted(principals, permission);
        }
    }

    //模拟登陆
    public void login(AuthenticationToken token) {
        doGetAuthenticationInfo(token);
    }

    //清除全部缓存
    public void clearCached() {
        this.getAuthorizationCache().clear();
        CacheUtil.clear(this.getClass().getName() + ".authorizationCache");
    }

    /**
     * 清除一个缓存的权限信息
     *
     * @param username
     */
    public void clearCachedAuthorizationInfo(String username) {
        try {
            Subject subject = SecurityUtils.getSubject();
            String realmName = subject.getPrincipals().getRealmNames().iterator().next();
            //第一个参数为用户名,第二个参数为realmName
            SimplePrincipalCollection principals = new SimplePrincipalCollection(username, realmName);
            subject.runAs(principals);
            this.getAuthorizationCache().remove(subject.getPrincipals());
            subject.releaseRunAs();
        } catch (Exception e) {
        }
    }
}
