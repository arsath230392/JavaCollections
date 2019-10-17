package com.arsath.Test;

import com.arsath.collections.CustomArrayList;

public class TestCustomArrayList {
	public static void main(String[] args) {
		CustomArrayList<String> list = new CustomArrayList<String>();

		list.add("aa");
		list.add("bb");
		System.out.println(list);
		list.addAll(list);

		System.out.println("****************************************");
		System.out.println(list);

		System.out.println("****************************************");
		list.remove("bb");
		System.out.println(list);
		System.out.println("****************************************");
		list.add("cc");
		list.add("dd");
		list.add("ee");
		list.add("EE");
		System.out.println(list);
		list.remove("ee");
		System.out.println(list);
		System.out.println("****************************************");
		list.removeAll(list);
		System.out.println(list);
		System.out.println(list.getSize());

	}
}
