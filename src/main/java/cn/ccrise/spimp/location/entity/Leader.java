/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.location.entity;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public class Leader {
	private String name;
	private String location;

	public Leader(String name, String location) {
		this.name = name;
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}
}
