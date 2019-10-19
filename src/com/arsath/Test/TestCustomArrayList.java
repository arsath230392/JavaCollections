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
		System.out.println("****************************************");
		list.add("cc");
		list.add("dd");
		list.add("ee");
		list.add("EE");
		System.out.println(list);

		CustomArrayList<String> list2 = new CustomArrayList<String>();
		list2.add("cc");
		list2.add("EE");
		list.retainAll(list2);
		System.out.println(list);

		System.out.println("****************************************");
		list.add("asf");
		list.add("dsgagd");
		list.add("easdghae");
		list.add("EasgaE");
		System.out.println(list);
		list.sort();
		System.out.println(list);

		System.out.println("****************************************");
		CustomArrayList<Integer> list3 = new CustomArrayList<Integer>();
		list3.add(4);
		list3.add(35735);
		list3.add(547);
		list3.add(457);
		System.out.println(list3);
		list3.sort();
		System.out.println(list3);

		System.out.println("*****************************************");
		CustomArrayList<Integer> integerList = new CustomArrayList<Integer>();
		integerList.add(1);
		integerList.add(16);
		integerList.add(1);
		integerList.add(1643);
		integerList.add(1346);
		integerList.add(1346);
		integerList.add(12345);
		integerList.add(125);
		integerList.add(3461);
		integerList.add(14);
		integerList.add(1235);
		integerList.add(15);
		integerList.add(1235);
		integerList.add(41);
		integerList.add(12352);
		integerList.add(1235235);
		integerList.add(8);
		integerList.add(6);
		integerList.sort();
		System.out.println(integerList);

		System.out.println("*****************************************");
		CustomArrayList<String> StringList = new CustomArrayList<String>();
		StringList.add("dsg");
		StringList.add("sdgsdh");
		StringList.add("t");
		StringList.add("TRE");
		StringList.add("fh");
		StringList.add("TREHE");
		StringList.add("fdbndf");
		StringList.add("f");
		StringList.add("RBHRB");
		StringList.add("dsTg");
		StringList.add("TbdfD");
		StringList.add("bfsd");
		StringList.add("dsbfsg");
		StringList.add("dsg");
		StringList.sort();
		System.out.println(StringList);
		StringList.reverseSort();
		System.out.println(StringList);

		System.out.println("*****************************************");
		CustomArrayList<Float> floatList = new CustomArrayList<Float>();
		floatList.add(1.00989f);
		floatList.add(16.546346f);
		floatList.add(134634f);
		floatList.add(1643.3463f);
		floatList.add(1346.55f);
		floatList.add(1346.555555f);
		floatList.add(12345.35635f);
		floatList.add(125.35635f);
		floatList.add(3461.353635f);
		floatList.add(14.357f);
		floatList.add(357357.7357f);
		floatList.add(3573.35735f);
		floatList.add(357.35735f);
		floatList.add(357.35f);
		floatList.add(3f);
		floatList.add(5f);
		floatList.add(8.35635f);
		floatList.add(6f);
		floatList.sort();
		System.out.println(floatList);
		floatList.reverseSort();
		System.out.println(floatList);
	}
}
