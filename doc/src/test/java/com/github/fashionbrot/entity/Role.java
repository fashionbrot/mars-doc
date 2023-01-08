
package com.github.fashionbrot.entity;



import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色Entity
 * @version 2013-12-05
 */
@Data
public class Role extends DataEntity<Role> {

	private String name; 	// 角色名称

	private User user;		// 根据用户ID查询角色列表
	//	private List<User> userList = Lists.newArrayList(); // 拥有用户列表
	private List<Menu> menuList = new ArrayList<>(); // 拥有菜单列表

}
