package cn.kim.service.impl;

import cn.kim.common.attr.MagicValue;
import cn.kim.common.attr.ParamTypeResolve;
import cn.kim.common.attr.TableName;
import cn.kim.common.eu.NameSpace;
import cn.kim.dao.BaseDao;
import cn.kim.entity.DictInfo;
import cn.kim.entity.Tree;
import cn.kim.entity.TreeState;
import cn.kim.exception.CustomException;
import cn.kim.service.FormatService;
import cn.kim.util.DateUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 余庚鑫 on 2018/5/22
 */
@Service
public class FormatServiceImpl extends BaseServiceImpl implements FormatService {

    @Override
    public Map<String, Object> selectFormat(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put("ID", mapParam.get("ID"));
        paramMap.put("SF_CODE", mapParam.get("SF_CODE"));
        return baseDao.selectOne(NameSpace.FormatMapper, "selectFormat", paramMap);
    }

    @Override
    public Map<String, Object> selectFormat(String formatCode) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        if (isEmpty(formatCode)) {
            return null;
        }

        paramMap.put("SF_CODE", formatCode);

        return this.selectFormat(paramMap);
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateFormat(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(5);
            String id = toString(mapParam.get("ID"));
            //验证唯一标示是否被使用
            paramMap.put("NOT_ID", id);
            paramMap.put("SF_CODE", mapParam.get("SF_CODE"));
            int count = baseDao.selectOne(NameSpace.FormatMapper, "selectFormatCount", paramMap);
            if (count > 0) {
                throw new CustomException("格式唯一标示重复,请检查!");
            }

            paramMap.clear();
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_FORMAT);

            paramMap.put("ID", mapParam.get("ID"));
            paramMap.put("SF_NAME", mapParam.get("SF_NAME"));
            paramMap.put("SF_CODE", mapParam.get("SF_CODE"));
            paramMap.put("SF_YEAR", mapParam.get("SF_YEAR"));

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);
                paramMap.put("SF_ENTRY_TIME", getDate());

                baseDao.insert(NameSpace.FormatMapper, "insertFormat", paramMap);
                resultMap.put(MagicValue.LOG, "添加格式:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectFormat(oldMap);

                baseDao.update(NameSpace.FormatMapper, "updateFormat", paramMap);
                resultMap.put(MagicValue.LOG, "更新格式,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
            }

            status = STATUS_SUCCESS;
            desc = SAVE_SUCCESS;

            resultMap.put("ID", id);
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }


    @Override
    @Transactional
    public Map<String, Object> deleteFormat(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            String id = toString(mapParam.get("ID"));
            if (isEmpty(id)) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
            //删除格式详细
            paramMap.put("SF_ID", id);
            baseDao.delete(NameSpace.FormatMapper, "deleteFormatDetail", paramMap);
            //删除格式
            paramMap.clear();
            paramMap.put("ID", id);
            Map<String, Object> oldMap = selectFormat(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_FORMAT);
            baseDao.delete(NameSpace.FormatMapper, "deleteFormat", paramMap);

            resultMap.put(MagicValue.LOG, "删除格式,信息:" + toString(oldMap));

            status = STATUS_SUCCESS;
            desc = DELETE_SUCCESS;
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    @Override
    public Map<String, Object> selectFormatDetail(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
        paramMap.put("ID", mapParam.get("ID"));
        paramMap.put("SF_ID", mapParam.get("SF_ID"));
        return baseDao.selectOne(NameSpace.FormatMapper, "selectFormatDetail", paramMap);
    }

    @Override
    public List<Map<String, Object>> selectFormatDetailList(Map<String, Object> mapParam) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("SF_ID", mapParam.get("SF_ID"));
        return baseDao.selectList(NameSpace.FormatMapper, "selectFormatDetail", paramMap);
    }

    @Override
    public List<Tree> selectFormatDetailTree(Map<String, Object> mapParam) {
        List<Map<String, Object>> formatDetails = setFormatDetailChildrenList(baseDao, toString(mapParam.get("SF_ID")), "0", toString(mapParam.get("NOT_ID")));

        return getFormatDetailTree(formatDetails, toString(mapParam.get("SFD_PARENT_ID")));
    }

    @Override
    @Transactional
    public Map<String, Object> insertAndUpdateFormatDetail(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(7);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(7);
            String id = toString(mapParam.get("ID"));
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_FORMAT_DETAIL);

            paramMap.put("ID", mapParam.get("ID"));
            paramMap.put("SF_ID", mapParam.get("SF_ID"));
            paramMap.put("SM_ID", mapParam.get("SM_ID"));
            paramMap.put("SFD_NAME", mapParam.get("SFD_NAME"));
            paramMap.put("SFD_ORDER", mapParam.get("SFD_ORDER"));
            paramMap.put("SFD_PARENT_ID", isEmpty(mapParam.get("SFD_PARENT_ID")) ? "0" : mapParam.get("SFD_PARENT_ID"));

            if (isEmpty(id)) {
                id = getId();
                paramMap.put("ID", id);
                paramMap.put("IS_STATUS", STATUS_SUCCESS);

                baseDao.insert(NameSpace.FormatMapper, "insertFormatDetail", paramMap);
                resultMap.put(MagicValue.LOG, "添加格式详细:" + toString(paramMap));
            } else {
                Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
                oldMap.put("ID", id);
                oldMap = selectFormatDetail(oldMap);

                baseDao.update(NameSpace.FormatMapper, "updateFormatDetail", paramMap);
                resultMap.put(MagicValue.LOG, "更新格式详细,更新前:" + toString(oldMap) + ",更新后:" + toString(paramMap));
            }

            status = STATUS_SUCCESS;
            desc = SAVE_SUCCESS;

            resultMap.put("ID", id);
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> changeFormatDetailStatus(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = SAVE_ERROR;
        try {
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(2);
            String id = toString(mapParam.get("ID"));

            paramMap.put("ID", id);
            paramMap.put("IS_STATUS", mapParam.get("IS_STATUS"));

            Map<String, Object> oldMap = Maps.newHashMapWithExpectedSize(1);
            oldMap.put("ID", id);
            oldMap = selectFormatDetail(oldMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_FORMAT_DETAIL);
            baseDao.update(NameSpace.FormatMapper, "updateFormatDetail", paramMap);
            resultMap.put(MagicValue.LOG, "更新格式详细状态,格式详细名称:" + toString(oldMap.get("SFD_NAME")) + ",状态更新为:" + ParamTypeResolve.statusExplain(mapParam.get("IS_STATUS")));

            status = STATUS_SUCCESS;
            desc = SAVE_SUCCESS;

            resultMap.put("ID", id);
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }


    @Override
    @Transactional
    public Map<String, Object> deleteFormatDetail(Map<String, Object> mapParam) {
        Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(5);
        int status = STATUS_ERROR;
        String desc = DELETE_ERROR;
        try {
            String id = toString(mapParam.get("ID"));
            if (isEmpty(id)) {
                throw new CustomException("ID不能为空!");
            }
            Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);

            paramMap.clear();
            paramMap.put("ID", id);
            Map<String, Object> oldMap = selectFormatDetail(paramMap);
            //记录日志
            paramMap.put("SVR_TABLE_NAME", TableName.SYS_FORMAT_DETAIL);
            baseDao.delete(NameSpace.FormatMapper, "deleteFormatDetail", paramMap);

            resultMap.put(MagicValue.LOG, "删除格式详细,信息:" + toString(oldMap));

            status = STATUS_SUCCESS;
            desc = DELETE_SUCCESS;
        } catch (Exception e) {
            desc = catchException(e, baseDao, resultMap);
        }
        resultMap.put(MagicValue.STATUS, status);
        resultMap.put(MagicValue.DESC, desc);
        return resultMap;
    }

    /**
     * 获取格式详细子类
     *
     * @param baseDao
     * @param formatId
     * @param formatDetailParentId
     * @param notId
     * @return
     */
    private List<Map<String, Object>> setFormatDetailChildrenList(BaseDao baseDao, String formatId, String formatDetailParentId, String notId) {
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(3);
        paramMap.put("SF_ID", formatId);
        paramMap.put("SFD_PARENT_ID", formatDetailParentId);
        paramMap.put("NOT_ID", notId);

        List<Map<String, Object>> formatDetail = baseDao.selectList(NameSpace.FormatMapper, "selectFormatDetail", paramMap);

        if (!isEmpty(formatDetail)) {
            formatDetail.stream().forEach(map -> {
                List<Map<String, Object>> children = setFormatDetailChildrenList(baseDao, formatId, toString(map.get("ID")), notId);
                if (!isEmpty(children)) {
                    map.put("CHILDREN", children);
                }
            });
        }
        return formatDetail;
    }

    /**
     * 吧格式详细map转为tree
     *
     * @return
     */
    private List<Tree> getFormatDetailTree(List<Map<String, Object>> formatDetail, String formatDetailParentId) {
        List<Tree> formatDetailTrees = new ArrayList<>();
        if (!isEmpty(formatDetail)) {
            formatDetail.forEach(detail -> {
                String id = toString(detail.get("ID"));

                Tree tree = new Tree();
                tree.setId(id);
                tree.setText(toString(detail.get("SFD_NAME")));
                tree.setTags(new String[]{"菜单:" + toHtmlBColor(detail.get("SM_NAME"), "yellow")});

                TreeState state = new TreeState();
                //是否选中
                if (id.equals(formatDetailParentId)) {
                    state.setChecked(true);
                    //选中的设置打开
                    state.setExpanded(true);
                }

                //设置状态
                tree.setState(state);

                //递归
                if (!isEmpty(detail.get("CHILDREN"))) {
                    tree.setNodes(getFormatDetailTree((List<Map<String, Object>>) detail.get("CHILDREN"), formatDetailParentId));
                }

                formatDetailTrees.add(tree);
            });
        }

        return formatDetailTrees;
    }
}
