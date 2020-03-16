/**
 * 
 */
package org.yelong.test.core.model;

import org.yelong.core.model.annotation.Table;
import org.yelong.core.model.sql.SqlModel;

/**
 * @author PengFei
 * @date 2020年3月2日下午4:37:44
 * @since 1.0
 */
@Table(value = "CO_USER",alias = "u")
public class User extends SqlModel{

	private static final long serialVersionUID = -7671853499151551862L;

	private String id;
	
	private String username;
	
	private String password;
	
	private String realName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
