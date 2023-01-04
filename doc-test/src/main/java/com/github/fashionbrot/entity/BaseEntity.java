
package com.github.fashionbrot.entity;



import jdk.nashorn.internal.objects.Global;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity支持类
 * @version 2014-05-16
 */
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	
	public static final String DEL_FLAG_DELETE = "1";
	
	public static final String DEL_FLAG_AUDIT = "2";


	/**
	 * 课程类型标记（0：视频；1：音频；2：图文；）
	 */
	public static final String COURSE_VODEO_TYPE = "0";

	public static final String COURSE_AUDIO_TYPE = "1";

	public static final String COURSE_IMAGETEXT_TYPE = "2";
	
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
	
	/**
	 * 自定义SQL（SQL标识，SQL内容）
	 */
	protected Map<String, String> sqlMap;

	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 */
	protected boolean isNewRecord = false;

	public BaseEntity() {
		
	}
	
	public BaseEntity(String id) {
		this();
		this.id = id;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        BaseEntity<?> that = (BaseEntity<?>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

	@XmlTransient
	public User getCurrentUser() {
		if(currentUser == null){
		}
		return currentUser;
	}
	
	/**
	 * 获取数据库名称
	 */
	public String getDbName(){
		return "";
	}

	/**
	 * 全局变量对象
	 */
	public Global getGlobal() {
		return null;
	}

	public String getId() {
		return id;
	}
	
	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     * @return
     */
	public boolean getIsNewRecord() {
        return isNewRecord;
    }
	
	@XmlTransient
	public Page<T> getPage() {
		if (page == null){
			page = new Page<T>();
		}
		return page;
	}
	
	@XmlTransient
	public Map<String, String> getSqlMap() {
		if (sqlMap == null){
			sqlMap = new HashMap<>();
		}
		return sqlMap;
	}

	/**
	 * 插入之前执行方法，子类实现
	 */
	public abstract void preInsert();

	/**
	 * 更新之前执行方法，子类实现
	 */
	public abstract void preUpdate();
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
    public void setId(String id) {
		this.id = id;
	}
    
    /**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 */
	public void setIsNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
    
	public Page<T> setPage(Page<T> page) {
		this.page = page;
		return page;
	}
	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	
}
