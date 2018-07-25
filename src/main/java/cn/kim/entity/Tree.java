package cn.kim.entity;

import cn.kim.util.TextUtil;

import java.util.List;

/**
 * Created by 余庚鑫 on 2018/3/20
 * 树节点参数
 */
public class Tree {
    /**
     * String(自定义)	列的ID
     */
    private String id;
    /**
     * String(必选项)	列表树节点上的文本，通常是节点右边的小图标。
     */
    private String text;
    /**
     * String(可选项)	列表树节点上的图标，通常是节点左边的图标。
     */
    private String icon;
    /**
     * String(可选项)	当某个节点被选择后显示的图标，通常是节点左边的图标。
     */
    private String selectedIcon;
    /**
     * String(可选项)	结合全局enableLinks选项为列表树节点指定URL。
     */
    private String href;
    /**
     * Boolean. Default: true	指定列表树的节点是否可选择。设置为false将使节点展开，并且不能被选择。
     */
    private boolean selectable = true;
    /**
     * Object(可选项)	一个节点的初始状态。
     */
    private TreeState state;
    /**
     * String. Optional	节点的前景色，覆盖全局的前景色选项。
     */
    private String color;
    /**
     * String. Optional	节点的背景色，覆盖全局的背景色选项。
     */
    private String backColor;
    /**
     * Array of Strings. Optional	通过结合全局showTags选项来在列表树节点的右边添加额外的信息。
     */
    private String[] tags;
    /**
     * 子节点
     */
    private List<Tree> nodes;

    public Tree() {
    }

    /**
     * 初始化
     *
     * @param id
     * @param text
     */
    public Tree(Object id, Object text) {
        this.id = TextUtil.toString(id);
        this.text = TextUtil.toString(text);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(String selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public TreeState getState() {
        return state;
    }

    public void setState(TreeState state) {
        this.state = state;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public List<Tree> getNodes() {
        return nodes;
    }

    public void setNodes(List<Tree> nodes) {
        this.nodes = nodes;
    }
}
