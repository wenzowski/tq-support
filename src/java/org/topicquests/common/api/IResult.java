package org.topicquests.common.api;

public interface IResult {
	boolean hasError();

	String getErrorString();

	void addErrorString(String var1);

	Object getResultObject();

	void setResultObject(Object var1);

	Object getResultObjectA();

	void setResultObjectA(Object var1);
}
