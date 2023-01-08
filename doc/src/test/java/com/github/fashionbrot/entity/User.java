
package com.github.fashionbrot.entity;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户Entity
 * @version 2013-12-05
 */
@Data
public class User extends DataEntity<User> {

	private String loginName;// 登录名
	private String password;// 密码

	private List<Role> roleList = new ArrayList<>(); // 拥有角色列表

}