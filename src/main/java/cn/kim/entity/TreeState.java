package cn.kim.entity;

/**
 * Created by 余庚鑫 on 2018/3/20
 * 树节点参数
 */
public class TreeState {
    /**
     * 指示一个节点是否处于checked状态，用一个checkbox图标表示。
     */
    private boolean checked;
    /**
     * 指示一个节点是否处于disabled状态。（不是selectable，expandable或checkable）
     */
    private boolean disabled;
    /**
     * 指示一个节点是否处于展开状态。
     */
    private boolean expanded;
    /**
     * 指示一个节点是否可以被选择。
     */
    private boolean selected;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
