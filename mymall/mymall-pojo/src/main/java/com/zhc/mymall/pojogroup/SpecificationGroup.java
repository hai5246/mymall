package com.zhc.mymall.pojogroup;

import com.zhc.mymall.pojo.Specification;
import com.zhc.mymall.pojo.SpecificationOption;

import java.io.Serializable;
import java.util.List;
/**
 * 规格的组合实体类
 *
 */
public class SpecificationGroup implements Serializable {
    private Specification specification;
    private List<SpecificationOption> specificationOptionList;

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<SpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }

    @Override
    public String toString() {
        return "SpecificationGroup{" +
                "specification=" + specification +
                ", specificationOptionList=" + specificationOptionList +
                '}';
    }
}
