package com.arsath.Test;

import com.arsath.collections.CustomTable;
import com.arsath.collections.Exceptions.UnableToInitialteTableException;
import com.arsath.collections.Exceptions.WrongIndexException;
import com.arsath.collections.Exceptions.WrongTypeException;

public class TestTable {
	public static void main(String[] args)
			throws UnableToInitialteTableException, WrongIndexException, WrongTypeException {
		CustomTable table = new CustomTable(50);
		table.visualize();
		table.addRow();
		table.visualize();
		table.setObjectAtPosition(0, 0, "Hi");
		table.visualize();

	}
}
