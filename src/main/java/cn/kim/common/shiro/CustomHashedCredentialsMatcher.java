package cn.kim.common.shiro;

import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.Tips;
import cn.kim.common.eu.UseType;
import cn.kim.entity.ActiveUser;
import cn.kim.service.ManagerService;
import cn.kim.service.MenuService;
import cn.kim.common.attr.CacheName;
import cn.kim.common.attr.Constants;
import cn.kim.util.*;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 余庚鑫 on 2017/4/22.
 * 如果是自动登录就直接判断md5，密码错误5次直接等待10分钟
 */
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private MenuService menuService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
        if (token.isAuto()) {
            Object accountCredentials = getCredentials(info);
            String pwd = String.valueOf(token.getPassword());
            return ((SimpleHash) accountCredentials).toString().equals(pwd);
        } else {
            String username = token.getUsername();
            Cache cache = CacheUtil.getCache(CacheName.PASSWORD_RETRY_CACHE);

            AtomicInteger retryCount = ValidateUtil.isEmpty(cache.get(username)) ? null : (AtomicInteger) cache.get(username);
            if (retryCount == null) {
                retryCount = new AtomicInteger();
            }
            //连续登录5次失败
            if (retryCount.get() >= 5) {
                throw new ExcessiveAttemptsException();
            }
            //查询账号
            Map<String, Object> user = managerService.queryLoginUsername(token.getUsername());

            //成功失败都记录日志
            ActiveUser activeUser = new ActiveUser();
            //设置类型
            activeUser.setType(TextUtil.toString(user.get("SOS_USERTYPE")));
            activeUser.setId(TextUtil.toString(user.get("ID")));
            activeUser.setUsername(TextUtil.toString(user.get("SOS_USERNAME")));
            activeUser.setUsercode(TextUtil.toString(user.get("SAI_NAME")));
            activeUser.setRole(TextUtil.toString(user.get("SR_NAME")));
            activeUser.setRoleIds(TextUtil.toString(user.get("SR_ID")));

//            Map<String, String> ipMap = HttpUtil.getIpAddressName(HttpUtil.getIpAddr(HttpUtil.getRequest()));
            //获得登录地址
//            String loginAddress = ipMap.get("code").equals("0") ? ipMap.get("country") + ipMap.get("region") + ipMap.get("city") + ":" + ipMap.get("isp") : "未知";
            String loginAddress = "未知";
            //清除验证码SESSION
            SessionUtil.remove("validateCode");
            //密码是否输入正确
            boolean matchs = super.doCredentialsMatch(token, info);
            if (matchs) {
                //移除输入失败记录
                cache.remove(username);
                //登录成功，查询信息放入SESSION中
                //添加菜单树
//                List<Map<String, Object>> menus = managerService.queryOperatorMenuTree(activeUser.getId());
//                activeUser.setMenus(menus);
                //查询按钮权限
//                Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
//                paramMap.put("SO_ID", activeUser.getId());
//                activeUser.setMenuButtons(managerService.queryOperatorMenuButtonPrecode(paramMap));

                //设置到session中
                SessionUtil.set(Constants.SESSION_USERNAME, activeUser);

                //记录日志
                LogUtil.recordLog(HttpUtil.getRequest(), "登录", UseType.PERSONAL.getType(), activeUser.getType(), "登录成功!登录地址:" + loginAddress, Attribute.STATUS_SUCCESS);
            } else {
                retryCount.getAndIncrement();
                cache.put(username, retryCount);
                //记录日志
                SessionUtil.set(Constants.SESSION_USERNAME, activeUser);
                LogUtil.recordLog(HttpUtil.getRequest(), "登录", UseType.PERSONAL.getType(), activeUser.getType(), "登录失败!第" + retryCount.get() + "次!登录地址:" + loginAddress, Attribute.STATUS_ERROR);
                SessionUtil.remove(Constants.SESSION_USERNAME);
            }
            return matchs;
        }
    }
}