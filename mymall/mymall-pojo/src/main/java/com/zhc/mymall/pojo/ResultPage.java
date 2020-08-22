package com.zhc.mymall.pojo;

import java.util.List;

/**
 * 返回到angular前端的封装类
 */
public class ResultPage {
    private List rows; //查询分页区间数
    private Long total; //记录总数

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
