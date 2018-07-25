package cn.kim.service;

import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/5/22
 * 系统配置
 */
public interface FileService extends BaseService {
    /**
     * 根据ID查询文件
     *
     * @param id
     * @return
     */
    Map<String, Object> selectFile(String id);

    /**
     * 根据map查询文件
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> selectFile(Map<String, Object> mapParam);

    /**
     * 根据map查询符合条件的文件
     *
     * @param mapParam
     * @return
     */
    List<Map<String, Object>> selectFileList(Map<String, Object> mapParam);

    /**
     * 插入文件
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> insertFile(Map<String, Object> mapParam);

    /**
     * 删除文件
     *
     * @param id
     * @return
     */
    Map<String, Object> deleteFile(String id);

    /**
     * 删除文件
     *
     * @param mapParam
     * @return
     */
    Map<String, Object> deleteFile(Map<String, Object> mapParam);


}
