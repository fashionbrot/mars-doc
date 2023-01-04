package com.github.fashionbrot.entity;

import java.util.ArrayList;
import java.util.List;

public class Label extends DataEntity<Label> {

    private String name;

    //课程数
    private Integer courseNum;

    //图标
    private String icon;

    //订阅量
    private Integer subscript;

    //更新内容
    private String  updateContent;

    //更新状态
    private char updateState;

    //课程列表
    private List<Course> courseList;

    //详情ID
    private String detailId;

    private String content;

    //简介
    private String outline;
    //状态
    private char state;


    private String companyIds;

    private List<String> companyIdsList = new ArrayList<>();

    private String companyId;

    private String companyName;

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSubscript() {
        return subscript;
    }

    public void setSubscript(Integer subscript) {
        this.subscript = subscript;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public char getUpdateState() {
        return updateState;
    }

    public void setUpdateState(char updateState) {
        this.updateState = updateState;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }


    public void setCompanyIds(String companyIds) {
        this.companyIds = companyIds;
    }

    public void setCompanyIdsList(List<String> companyIdsList) {
        this.companyIdsList = companyIdsList;
    }

    public List<String> getCompanyIdsList() {

        return companyIdsList;
    }

    public String getCompanyIds() {
        return  "";
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
