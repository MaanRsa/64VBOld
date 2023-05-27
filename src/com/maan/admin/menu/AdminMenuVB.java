package com.maan.admin.menu;

import com.maan.common.base.CommonBaseVB;

public class AdminMenuVB extends CommonBaseVB {

	private static final long serialVersionUID = 1L;

	private int id;

	private String name;

	private String url;

	private String type;

	private String superMenu;

	private String status;

	private String orderby;

	private String remark;

	private String startDate;

	private String endDate;

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(final String endDate) {
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(final String orderby) {
		this.orderby = orderby;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(final String remark) {
		this.remark = remark;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(final String startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getSuperMenu() {
		return superMenu;
	}

	public void setSuperMenu(final String superMenu) {
		this.superMenu = superMenu;
	}
}
