package com.arsath.Test;

import com.arsath.collections.CustomTable;
import com.arsath.collections.helper.UnableToInitialteTableException;

public class TestTable {
	public static void main(String[] args) throws UnableToInitialteTableException {
		CustomTable table = new CustomTable(50);
		table.visualize();
		table.addRow();
		table.visualize();

	}
}
