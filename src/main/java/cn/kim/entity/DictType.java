package cn.kim.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 余庚鑫 on 2018/3/26
 * 字典类型
 */
public class DictType implements Serializable {
    private String id;
    private String sdtName;
    private String sdtCode;
    private Integer isStatus;
    /**
     * 详细信息
     */
    private List<DictInfo> infos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSdtName() {
        return sdtName;
    }

    public void setSdtName(String sdtName) {
        this.sdtName = sdtName;
    }

    public String getSdtCode() {
        return sdtCode;
    }

    public void setSdtCode(String sdtCode) {
        this.sdtCode = sdtCode;
    }

    public Integer getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Integer isStatus) {
        this.isStatus = isStatus;
    }

    public List<DictInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<DictInfo> infos) {
        this.infos = infos;
    }
}
