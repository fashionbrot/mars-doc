
package com.github.fashionbrot.entity;
import lombok.Data;

/**
 * Entity支持类
 * @version 2014-05-16
 */
@Data
public abstract class BaseEntity<T>  {

	/**
	 * 实体编号（唯一标识）
	 */
	protected String id;
	
	/**
	 * 当前用户
	 */
	protected User currentUser;

	/**
	 * 当前实体分页对象
	 */
	protected Page<T> page;


	
}
