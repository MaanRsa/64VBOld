package com.maan.menu;

import java.util.List;

import com.maan.common.base.CommonBaseVB;

public class MenuVB extends CommonBaseVB {

	private int registration;
	private int preauthorize;
	private int blocked;
	private int unblocked;
	private int nottransacted;
	private int fulltransacted;
	private int partialtransacted;
	private int rejected;
	private int pending;
	
	private int transacted;
	
	
	private String url;

	private String content;

	private String id;

	private List list;

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public List getList() {
		return list;
	}

	public void setList(final List list) {
		this.list = list;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public int getBlocked() {
		return blocked;
	}

	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}

	public int getFulltransacted() {
		return fulltransacted;
	}

	public void setFulltransacted(int fulltransacted) {
		this.fulltransacted = fulltransacted;
	}

	public int getNottransacted() {
		return nottransacted;
	}

	public void setNottransacted(int nottransacted) {
		this.nottransacted = nottransacted;
	}

	public int getPartialtransacted() {
		return partialtransacted;
	}

	public void setPartialtransacted(int partialtransacted) {
		this.partialtransacted = partialtransacted;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public int getPreauthorize() {
		return preauthorize;
	}

	public void setPreauthorize(int preauthorize) {
		this.preauthorize = preauthorize;
	}

	public int getRegistration() {
		return registration;
	}

	public void setRegistration(int registration) {
		this.registration = registration;
	}

	public int getRejected() {
		return rejected;
	}

	public void setRejected(int rejected) {
		this.rejected = rejected;
	}

	public int getUnblocked() {
		return unblocked;
	}

	public void setUnblocked(int unblocked) {
		this.unblocked = unblocked;
	}

	public int getTransacted() {
		return transacted;
	}

	public void setTransacted(int transacted) {
		this.transacted = transacted;
	}

}
