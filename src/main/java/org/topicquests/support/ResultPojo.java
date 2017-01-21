package org.topicquests.support;

import org.topicquests.support.api.IResult;

public class ResultPojo implements IResult {
	private String errorString = "";
	private boolean isError = false;
	private Object returnObject = null;
	private Object returnObjectA = null;

	public ResultPojo() {
	}

	public ResultPojo(Object v) {
		this.setResultObject(v);
	}

	public boolean hasError() {
		return this.isError;
	}

	public String getErrorString() {
		return this.errorString;
	}

	public void addErrorString(String e) {
		this.errorString = this.errorString + "; " + e;
		this.isError = true;
	}

	public Object getResultObject() {
		return this.returnObject;
	}

	public void setResultObject(Object v) {
		this.returnObject = v;
	}

	public Object getResultObjectA() {
		return this.returnObjectA;
	}

	public void setResultObjectA(Object v) {
		this.returnObjectA = v;
	}
}
