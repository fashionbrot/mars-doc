package com.github.fashionbrot.entity;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Classify extends DataEntity<Classify> {

    private  String classifyId;

    private String name;

    //课程数
    private Integer courseNum;

    //图标
    private String icon;

    //订阅量
    private Integer subscript;

    //更新状态
    private String updateState;

    //课程列表
    private List<Course> courseList;

    //简介
    private String content;
    //状态
    private String state;

    //排序
    private Integer sort;
    //购买价格
    private BigDecimal buyprice;
    //划线价格
    private BigDecimal markprice;

    private Integer isBuy = 0;

    private Integer isRecommend;

    private String detailId;


    private String notState;

    private String orderBy;

    private String desc;

    private Integer isSee;

    private String filePath;

    //完成率
    private BigDecimal process;

    //作业内容
    private String taskInfo;
    //作业要求
    private String taskAsk;
    //截止时间
    private Date endTime;

    private String note;

    private String companyIds;


    private String companyId;

    private String companyName;


    private Integer isBook = 0;

    private Integer isSub = 0;

    private Integer isPublish;

    public Integer isDownLoad;

    private String userId;

    private String domain;


    private String position;

    private Date positonTime;

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    public String getTaskAsk() {
        return taskAsk;
    }

    public void setTaskAsk(String taskAsk) {
        this.taskAsk = taskAsk;
    }

    public void setProcess(BigDecimal process) {
        this.process = process;
    }

    public BigDecimal getProcess() {
        return process;
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

    public String getUpdateState() {
        return updateState;
    }

    public void setUpdateState(String updateState) {
        this.updateState = updateState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public BigDecimal getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    public BigDecimal getMarkprice() {
        return markprice;
    }

    public void setMarkprice(BigDecimal markprice) {
        this.markprice = markprice;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }

    public Integer getIsBuy() {
        return isBuy;
    }


    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }


    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }


    public void setNotState(String notState) {
        this.notState = notState;
    }

    public String getNotState() {
        return notState;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setIsSee(Integer isSee) {
        this.isSee = isSee;
    }

    public Integer getIsSee() {
        return isSee;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }


    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }


    public void setCompanyIds(String companyIds) {
        this.companyIds = companyIds;
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

    public void setIsBook(Integer isBook) {
        this.isBook = isBook;
    }

    public Integer getIsBook() {
        return isBook;
    }

    public void setIsSub(Integer isSub) {
        this.isSub = isSub;
    }

    public Integer getIsSub() {
        return isSub;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }

    public Integer getIsPublish() {
        return isPublish;
    }


    public void setIsDownLoad(Integer isDownLoad) {
        this.isDownLoad = isDownLoad;
    }

    public Integer getIsDownLoad() {
        return isDownLoad;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getPositonTime() {
        return positonTime;
    }

    public void setPositonTime(Date positonTime) {
        this.positonTime = positonTime;
    }
}
