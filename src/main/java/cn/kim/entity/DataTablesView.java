package cn.kim.entity;

import cn.kim.util.CommonUtil;
import cn.kim.util.CommonUtil;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.util.List;

/**
 * Created by 余庚鑫 on 2017/3/9.
 */
public class DataTablesView<T> implements Serializable {
    /**
     * data 与datatales 加载的“dataSrc"对应
     */
    private List<T> data;
    /**
     * 记录总数
     */
    private long recordsTotal;

    private long recordsFiltered;
    /**
     * 第几页
     */
    private int draw;
    /**
     * 总页数
     */
    private long totalPages;
    /**
     * 是否加密
     */
    @JSONField(serialize = false)
    private boolean isEncrypt = true;

    public DataTablesView() {

    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public List<T> getData() throws InvalidKeyException {
//        if (isEncrypt) {
//            CommonUtil.idEncrypt(data);
//        }
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public void setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
    }
}