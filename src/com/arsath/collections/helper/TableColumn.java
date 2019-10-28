package com.arsath.collections.helper;

import java.io.Serializable;

public class TableColumn implements Serializable {
	private static final long serialVersionUID = 2280383007510178389L;

	Object content = null;

	public Object getValue() {
		return this.content;
	}

	public void setValue(Object value) {
		this.content = value;
	}

	public String toString() {
		return this.content.toString();
	}

}
