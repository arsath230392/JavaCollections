package com.arsath.Test;

import com.arsath.collections.CustomMap;
import com.arsath.collections.MapEntrySet;

public class TestMap {
	public static void main(String[] args) {
		CustomMap<String, String> map = new CustomMap<String, String>();
		map.put("1", "v");
		map.put("2", "dsgsd");
		map.put("3", "dsafg");
		map.put("1", "change");

		System.out.println(map);
	}
}
