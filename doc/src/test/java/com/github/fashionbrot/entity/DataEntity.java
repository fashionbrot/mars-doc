
package com.github.fashionbrot.entity;



import lombok.Data;

import java.util.Date;

/**
 * 数据Entity类
 */
@Data
public abstract class DataEntity<T> extends BaseEntity<T> {

	protected Date createDate;	// 创建日期
	protected User updateBy;	// 更新者

}
