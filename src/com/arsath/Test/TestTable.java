package com.arsath.Test;

import com.arsath.collections.CustomTable;

public class TestTable {
	public static void main(String[] args) {
		CustomTable table = new CustomTable(50);
		System.out.println(table);
		table.visualize();
	}
}
