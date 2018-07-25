package cn.kim.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 余庚鑫 on 2018/3/26
 * 字典详细信息
 */
public class DictInfo implements Serializable {
    private String id;
    private String sdtId;
    private String sdtCode;
    private String sdiName;
    private String sdiCode;
    private String sdiInnercode;
    private Integer sdiOrder;
    private String sdiParentid;
    private String sdiRemark;
    private Integer sdiRequired;
    private Integer isStatus;
    /**
     * 子类
     */
    private List<DictInfo> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSdtId() {
        return sdtId;
    }

    public void setSdtId(String sdtId) {
        this.sdtId = sdtId;
    }

    public String getSdtCode() {
        return sdtCode;
    }

    public void setSdtCode(String sdtCode) {
        this.sdtCode = sdtCode;
    }

    public String getSdiName() {
        return sdiName;
    }

    public void setSdiName(String sdiName) {
        this.sdiName = sdiName;
    }

    public String getSdiCode() {
        return sdiCode;
    }

    public void setSdiCode(String sdiCode) {
        this.sdiCode = sdiCode;
    }

    public String getSdiInnercode() {
        return sdiInnercode;
    }

    public void setSdiInnercode(String sdiInnercode) {
        this.sdiInnercode = sdiInnercode;
    }

    public Integer getSdiOrder() {
        return sdiOrder;
    }

    public void setSdiOrder(Integer sdiOrder) {
        this.sdiOrder = sdiOrder;
    }

    public String getSdiParentid() {
        return sdiParentid;
    }

    public void setSdiParentid(String sdiParentid) {
        this.sdiParentid = sdiParentid;
    }

    public String getSdiRemark() {
        return sdiRemark;
    }

    public void setSdiRemark(String sdiRemark) {
        this.sdiRemark = sdiRemark;
    }

    public Integer getSdiRequired() {
        return sdiRequired;
    }

    public void setSdiRequired(Integer sdiRequired) {
        this.sdiRequired = sdiRequired;
    }

    public Integer getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Integer isStatus) {
        this.isStatus = isStatus;
    }

    public List<DictInfo> getChildren() {
        return children;
    }

    public void setChildren(List<DictInfo> children) {
        this.children = children;
    }
}
