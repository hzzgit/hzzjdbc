package com.hzz.hzzjdbc.submeter.vo;

import java.util.List;

/**
 * @author ：hzz
 * @description：版本字段控制，存储版本不同情况下字段的新增修改
 * @date ：2020/8/21 13:57
 */
public class PartVersionFieldVo {

    /**
     * 版本id
     */
    private Double versionId;
    /**
     * 该版本添加的字段
     */
    private List<String> AddField;

    public Double getVersionId() {
        return versionId;
    }

    public void setVersionId(Double versionId) {
        this.versionId = versionId;
    }

    public List<String> getAddField() {
        return AddField;
    }

    public void setAddField(List<String> addField) {
        AddField = addField;
    }
}
