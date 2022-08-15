package com.kaibutsusama.stock.system.entity.user;

import com.kaibutsusama.stock.system.entity.base.BaseEntity;
import lombok.Data;

/**
 * 
 * 机构信息表
 *
 */
@Data
public class Institution extends BaseEntity {

    /**i
     * 机构类型id
     */
    private String institutionTypeId;

    /**
     * 机构关联id
     */
    private Long detailInstitutionId;

    /**
     * 机构关联名称
     */
    private String detailInstitutionName;

    /**
     */
    private static final long serialVersionUID = 1L;


    /**
     * 机构类型id
     */
    public String getInstitutionTypeId() {
        return institutionTypeId;
    }

    public void setInstitutionTypeId(String institutionTypeId) {
        this.institutionTypeId = institutionTypeId == null ? null : institutionTypeId.trim();
    }

    /**
     * 机构关联id
     */
    public Long getDetailInstitutionId() {
        return detailInstitutionId;
    }

    public void setDetailInstitutionId(Long detailInstitutionId) {
        this.detailInstitutionId = detailInstitutionId;
    }

    /**
     * 机构关联名称
     */
    public String getDetailInstitutionName() {
        return detailInstitutionName;
    }

    public void setDetailInstitutionName(String detailInstitutionName) {
        this.detailInstitutionName = detailInstitutionName == null ? null : detailInstitutionName.trim();
    }
}