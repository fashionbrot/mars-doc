
package com.github.fashionbrot.entity;



import java.util.List;

/**
 * 菜单Entity
 * @version 2013-05-15
 */
public class Menu extends DataEntity<Menu> {

	private static final long serialVersionUID = 1L;
	public static String getRootId(){
		return "1";
	}
	public static void sortList(List<Menu> list, List<Menu> sourcelist, String parentId, boolean cascade){
		for (int i=0; i<sourcelist.size(); i++){
			Menu e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						Menu child = sourcelist.get(j);
						if (child.getParent()!=null && child.getParent().getId()!=null
								&& child.getParent().getId().equals(e.getId())){
							sortList(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}
	private Menu parent;	// 父级菜单
	private String parentIds; // 所有父级编号
	private String name; 	// 名称
	private String href; 	// 链接
	private String target; 	// 目标（ mainFrame、_blank、_self、_parent、_top）
	private String icon; 	// 图标
	private Integer sort; 	// 排序
	
	private String isShow; 	// 是否在菜单中显示（1：显示；0：不显示）
	
	private String permission; // 权限标识
	
	private String userId;
	
	public Menu(){
		super();
		this.sort = 30;
		this.isShow = "1";
	}

	public Menu(String id){
		super(id);
	}

	public String getHref() {
		return href;
	}

	public String getIcon() {
		return icon;
	}
	
	public String getIsShow() {
		return isShow;
	}

	public String getName() {
		return name;
	}

	public Menu getParent() {
		return parent;
	}

	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	public String getParentIds() {
		return parentIds;
	}

	public String getPermission() {
		return permission;
	}
	
	public Integer getSort() {
		return sort;
	}

	public String getTarget() {
		return target;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return name;
	}
}