package org.javastudy.rpc.fastjson.bean;

import java.io.Serializable;

public class User implements Serializable {
	private int uid;
	private String uname;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}
}
