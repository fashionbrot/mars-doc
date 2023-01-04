package com.github.fashionbrot.entity;

import com.github.fashionbrot.doc.annotation.ApiModelProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course extends DataEntity<Course> {

    private static final long serialVersionUID = 1446192936111767211L;
    //用户id
    private String userId;
    //课程标题
    private String title;

    //讲师
    private String lecturer;

    //课程类型
    private Integer courseType;

    //课程概要
    private String outline;

    //附件地址
    private String filePath;

   //试看地址
    private String seePath;

    //地址
    private String path;

    //划线价格
    private BigDecimal scribPrice;

    private BigDecimal buyPrice;


    private String detailId;

    @ApiModelProperty("状态：0：正常状态， 1:下架  ")
    private Integer status;
    //更新时间
    private Date updateDate;

    private String icon;


    private Integer hot;


    private Integer buyNum = 0;

    private Integer favorite = 0; //0 未收藏   1收藏

    private Integer isSub = 0;

    private Integer isForm = 0;


    private List<Label> labels = new ArrayList<>();

    private List<String> labelIds = new ArrayList<>();

    private List<Classify> classifyes = new ArrayList<>();

    private List<String> classifyIds = new ArrayList<>();

    private String content;
    private String classifyId;

    //推荐
    private String recommend;
    //是否试看 0 是不试看，1是试看
    private Integer trySeeState;

    private String videoId;

    private String videoId2;

    private Integer isBuy = 0;

    private Integer isFile = 0;

    private BigDecimal score;

    private Integer isRecommend;

    private Integer aloneBuy ;

    private String videoPath;

    private Integer sort =0;

    private Integer time = 300;

    private Integer timeTotal = 0;

    private Date upTime;

    private String courseId;

    private String redType;

    private Long processTime;

    private BigDecimal process;

    private Integer isMaxBuy = 0; //是否已经购买最大值

    private Integer tryTimeTotal = 0;

    private String videoIcon;


    private String companyIds;

    private List<String> companyIdsList = new ArrayList<>();

    private String companyId;

    private String companyName;


    private Integer laud;

    private String classifyName; //文章分类

    private String parentId;

    //阅读
    private Integer hits = 0;

    //评论数
    private Integer commentCount = 0;

    //收藏数
    private Integer favoriteCount = 0;

    private User user;

    private String domain;

    private String position;

    private Date positonTime;


    public void setVideoIcon(String videoIcon) {
        this.videoIcon = videoIcon;
    }

    public String getVideoIcon() {
        return videoIcon;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * 是否开启考试星后台联系，0表示不开启，1表示开启
     */
    private Integer examstar;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getRedType() {
        return redType;
    }

    public void setRedType(String redType) {
        this.redType = redType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setScribPrice(BigDecimal scribPrice) {
        this.scribPrice = scribPrice;
    }

    public BigDecimal getBuyPrice() {
        buyPrice= buyPrice == null ? new BigDecimal(0) : buyPrice;
        return buyPrice;
    }

    public BigDecimal getScribPrice() {
        scribPrice= scribPrice == null ? new BigDecimal(0) : scribPrice;
        return scribPrice;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public String getDetailId() {
        return detailId;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getOutline() {
        return outline;
    }

    public String getPath() {
        return path;
    }


    public void setSeePath(String seePath) {
        this.seePath = seePath;
    }

    public String getSeePath() {
        return seePath;
    }


    public void setClassifyes(List<Classify> classifyes) {
        this.classifyes = classifyes;
    }

    public List<Classify> getClassifyes() {
        return classifyes;
    }


    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }



    public List<Label> getLabels() {
        return labels;
    }

    @Override
    public Date getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }




    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Integer getTrySeeState() {
        return trySeeState;
    }

    public void setTrySeeState(Integer trySeeState) {
        this.trySeeState = trySeeState;
    }

    public void setClassifyIds(List<String> classifyIds) {
        this.classifyIds = classifyIds;
    }

    public List<String> getLabelIds() {

        for (Label label : getLabels()) {
            labelIds.add(label.getId());
        }
        return labelIds;
    }

    public void setLabelIds(List<String> labelIds) {
        this.labelIds = labelIds;
    }

    public List<String> getClassifyIds() {
        for (Classify classify : getClassifyes()) {
            classifyIds.add(classify.getId());
        }
        return classifyIds;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setVideoId2(String videoId2) {
        this.videoId2 = videoId2;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getVideoId2() {
        return videoId2;
    }


    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }

    public Integer getIsBuy() {
        return isBuy;
    }

    public void setIsFile(Integer isFile) {
        this.isFile = isFile;
    }

    public Integer getIsFile() {
        return isFile;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setAloneBuy(Integer aloneBuy) {
        this.aloneBuy = aloneBuy;
    }

    public Integer getAloneBuy() {
        return aloneBuy;
    }


    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return sort;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getTime() {
        return time;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public Date getUpTime() {
        return upTime;
    }

    public Integer getExamstar() {
        return examstar;
    }

    public void setExamstar(Integer examstar) {
        this.examstar = examstar;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setTimeTotal(Integer timeTotal) {
        this.timeTotal = timeTotal;
    }

    public Integer getTimeTotal() {
        return timeTotal;
    }


    public void setProcessTime(Long processTime) {
        this.processTime = processTime;
    }

    public Long getProcessTime() {
        return processTime;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public Integer getFavorite() {
        return favorite == null ? 0 : favorite;
    }

    public void setProcess(BigDecimal process) {
        this.process = process;
    }

    public BigDecimal getProcess() {
        return process;
    }

    public void setIsMaxBuy(Integer isMaxBuy) {
        this.isMaxBuy = isMaxBuy;
    }

    public Integer getIsMaxBuy() {
        return isMaxBuy;
    }

    public void setTryTimeTotal(Integer tryTimeTotal) {
        this.tryTimeTotal = tryTimeTotal;
    }

    public Integer getTryTimeTotal() {
        return tryTimeTotal;
    }

    public void setIsSub(Integer isSub) {
        this.isSub = isSub;
    }

    public Integer getIsSub() {
        return isSub;
    }

    public void setIsForm(Integer isForm) {
        this.isForm = isForm;
    }

    public Integer getIsForm() {
        return isForm;
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

    public void setLaud(Integer laud) {
        this.laud = laud;
    }

    public Integer getLaud() {
        return laud;
    }


    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getHits() {
        return hits;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPositonTime(Date positonTime) {
        this.positonTime = positonTime;
    }

    public Date getPositonTime() {
        return positonTime;
    }

}

