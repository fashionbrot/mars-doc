package com.github.fashionbrot.entity;


public class CourseVO extends Course {

    private String labelId;
    private String classifyId;
    private String companyId;
    private String orderBy;
    private String keywords;
    private String desc = "DESC";
    private Integer aloneBuy ;


    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getLabelId() {
        return labelId;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setAloneBuy(Integer aloneBuy) {
        this.aloneBuy = aloneBuy;
    }

    public Integer getAloneBuy() {
        return aloneBuy;
    }

}
