
package com.github.fashionbrot.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户Entity
 * @version 2013-12-05
 */
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;
	public static boolean isAdmin(String id){
		return id != null && "1".equals(id);
	}
	private String loginName;// 登录名
	private String password;// 密码
	private String no;		// 工号
	private String name;	// 姓名
	private String email;	// 邮箱

	private String phone;	// 电话
	private String mobile;	// 手机
	private String userType;// 用户类型
	private String loginIp;	// 最后登陆IP
	private Date loginDate;	// 最后登陆日期
	private String loginFlag;	// 是否允许登陆
	private int payStatus;//是否付费 1否 2是
	private String photo;	// 头像
	private String oldLoginName;// 原登录名
	private String oldMobile;//原手机号
	private Date payDate;//付费时间
	private String newPassword;	// 新密码
	private String oldLoginIp;	// 上次登陆IP
	private String province;
	private String city;
	private int sex;
	private String sexDesc;
	private Date oldLoginDate;	// 上次登陆日期
	private Date birthday;
	private Role role;	// 根据角色查询用户条件
	private float lat;//经度
	private float lng;//纬度
	private String wxId;
	private String company;
	private String application;
	private String jobTitle;
	private String zipCode;
	private List<Role> roleList = new ArrayList<>(); // 拥有角色列表

	private String age;
	private String label;

	//微信openId
	private String openId;

	//微信unionid（唯一）
	private String unionid;


	private Integer checked = 0 ;

	private Integer buyCourse;
	private Long studyTime;

	private String address;

	//数量
	private Integer number;
	//级别
	private String level="1";

	List<User> children;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<User> getChildren() {
		return children;
	}

	public void setChildren(List<User> children) {
		this.children = children;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public User() {
		super();
	}

	public User(Role role){
		super();
		this.role = role;
	}

	public User(String id){
		super(id);
	}

	public User(String id, String loginName){
		super(id);
		this.loginName = loginName;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public String getName() {
		return name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getNo() {
		return no;
	}

	public Date getOldLoginDate() {
		if (oldLoginDate == null){
			return loginDate;
		}
		return oldLoginDate;
	}

	public String getOldLoginIp() {
		if (oldLoginIp == null){
			return loginIp;
		}
		return oldLoginIp;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public String getOldLoginName() {
		return oldLoginName;
	}
	
	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String getPhoto() {
		return photo;
	}

	public String getRemarks() {
		return remarks;
	}

	public Role getRole() {
		return role;
	}
	
	public List<String> getRoleIdList() {
		List<String> roleIdList = new ArrayList<>();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return "";
	}

	public String getUserType() {
		return userType;
	}

	public boolean isAdmin(){
		return isAdmin(this.id);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setOldLoginDate(Date oldLoginDate) {
		this.oldLoginDate = oldLoginDate;
	}

	public void setOldLoginIp(String oldLoginIp) {
		this.oldLoginIp = oldLoginIp;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = new ArrayList<>();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}
	
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return id;
	}

	public String getOldMobile() {
		return oldMobile;
	}

	public void setOldMobile(String oldMobile) {
		this.oldMobile = oldMobile;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAge() {
		return age;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getSexDesc() {
		return sexDesc;
	}

	public void setSexDesc(String sexDesc) {
		this.sexDesc = sexDesc;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setBuyCourse(Integer buyCourse) {
		this.buyCourse = buyCourse;
	}

	public Integer getBuyCourse() {
		return buyCourse;
	}

	public void setStudyTime(Long studyTime) {
		this.studyTime = studyTime;
	}

	public Long getStudyTime() {
		return studyTime;
	}
}