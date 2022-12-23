
package com.github.fashionbrot.entity;



import java.util.ArrayList;
import java.util.List;

/**
 * 角色Entity
 * @author luoka
 * @version 2013-12-05
 */
public class Role extends DataEntity<Role> {
	
	private static final long serialVersionUID = 1L;
	// 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
	public static final String DATA_SCOPE_ALL = "1";
	public static final String DATA_SCOPE_COMPANY_AND_CHILD = "2";
	public static final String DATA_SCOPE_COMPANY = "3";
	public static final String DATA_SCOPE_OFFICE_AND_CHILD = "4";
	
	public static final String DATA_SCOPE_OFFICE = "5";
	public static final String DATA_SCOPE_SELF = "8";
	public static final String DATA_SCOPE_CUSTOM = "9";
	private String name; 	// 角色名称
	
	private String enname;	// 英文名称

	private String roleType;// 权限类型

	private String dataScope;// 数据范围
	private String oldName; 	// 原角色名称
	private String oldEnname;	// 原英文名称
	private String sysData; 		//是否是系统数据
	private String useable; 		//是否是可用
	private User user;		// 根据用户ID查询角色列表
	//	private List<User> userList = Lists.newArrayList(); // 拥有用户列表
	private List<Menu> menuList = new ArrayList<>(); // 拥有菜单列表
	
	public Role() {
		super();
		this.dataScope = DATA_SCOPE_SELF;
	}
	
	public Role(String id){
		super(id);
	}
	
	public Role(User user) {
		this();
		this.user = user;
	}

	public String getDataScope() {
		return dataScope;
	}

	public String getEnname() {
		return enname;
	}

	public List<String> getMenuIdList() {
		List<String> menuIdList = new ArrayList<>();
		for (Menu menu : menuList) {
			menuIdList.add(menu.getId());
		}
		return menuIdList;
	}

	public String getMenuIds() {
		return "";
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public String getName() {
		return name;
	}

	public String getOldEnname() {
		return oldEnname;
	}

	public String getOldName() {
		return oldName;
	}
	
	/**
	 * 获取权限字符串列表
	 */
	public List<String> getPermissions() {
		List<String> permissions = new ArrayList<>();
		for (Menu menu : menuList) {
			if (menu.getPermission()!=null && !"".equals(menu.getPermission())){
				permissions.add(menu.getPermission());
			}
		}
		return permissions;
	}

	public String getRoleType() {
		return roleType;
	}

	public String getSysData() {
		return sysData;
	}

	public String getUseable() {
		return useable;
	}

	public User getUser() {
		return user;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public void setMenuIdList(List<String> menuIdList) {
		menuList = new ArrayList<>();
		for (String menuId : menuIdList) {
			Menu menu = new Menu();
			menu.setId(menuId);
			menuList.add(menu);
		}
	}


	public void setMenuIds(String menuIds) {
		menuList = new ArrayList<>();
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOldEnname(String oldEnname) {
		this.oldEnname = oldEnname;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public void setSysData(String sysData) {
		this.sysData = sysData;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
