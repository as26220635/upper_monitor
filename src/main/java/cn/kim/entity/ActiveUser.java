package cn.kim.entity;


import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/1/1.
 * 用户身份信息，存入session 由于tomcat将session会序列化在本地硬盘上，所以使用Serializable接口
 */
public class ActiveUser implements java.io.Serializable {
    /**
     * 用户id（主键）
     */
    private String id;
    /**
     * 用户账号
     */
    private String usercode;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 用户elixir
     */
    private String type;
    /**
     * 角色名
     */
    private String role;
    /**
     * 角色ids
     */
    private String roleIds;

    /**
     * 菜单
     */
    private List<Map<String, Object>> menus;
    /**
     * 菜单按钮权限
     */
    private List<Map<String, Object>> menuButtons;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Map<String, Object>> getMenus() {
        return menus;
    }

    public void setMenus(List<Map<String, Object>> menus) {
        this.menus = menus;
    }

    public List<Map<String, Object>> getMenuButtons() {
        return menuButtons;
    }

    public void setMenuButtons(List<Map<String, Object>> menuButtons) {
        this.menuButtons = menuButtons;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
