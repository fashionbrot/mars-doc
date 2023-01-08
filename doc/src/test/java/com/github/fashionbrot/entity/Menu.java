
package com.github.fashionbrot.entity;



import lombok.Data;


/**
 * 菜单Entity
 * @version 2013-05-15
 */
@Data
public class Menu extends DataEntity<Menu> {

	private String name; 	// 名称
	private String href; 	// 链接
}