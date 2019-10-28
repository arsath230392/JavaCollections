package com.arsath.Test;

import com.arsath.collections.CustomTable;
import com.arsath.collections.Enums.TableColumnTypes;
import com.arsath.collections.Exceptions.CannotMoveRowPointerException;
import com.arsath.collections.Exceptions.ColumnExistException;
import com.arsath.collections.Exceptions.ColumnNotExistsException;
import com.arsath.collections.Exceptions.UnableToInitialteTableException;
import com.arsath.collections.Exceptions.WrongIndexException;
import com.arsath.collections.Exceptions.WrongTypeException;

public class TestTable {
	public static void main(String[] args) throws UnableToInitialteTableException, WrongIndexException,
			WrongTypeException, ColumnExistException, ColumnNotExistsException, CannotMoveRowPointerException {
		CustomTable table = new CustomTable();

		// 1) Adding new columns
		table.addColumn("Mobile Name", TableColumnTypes.String);
		table.visualize();
		table.addColumn("RAM Memory in GB", TableColumnTypes.Integer);
		table.visualize();
		table.addColumn("Cost in Rs", TableColumnTypes.Integer);
		table.visualize();
		table.addColumn("Screen size in inch", TableColumnTypes.Float);
		table.visualize();

		// 2) Adding new Rows
		table.addRows(4);
		table.visualize();

		// 3) setting values in rows
		table.setStringAtColumn("Mobile Name", "Redmi Note 7S");
		table.setIntegerAtColumn("RAM Memory in GB", 4);
		table.setIntegerAtColumn("Cost in Rs", 9999);
		table.setFloatAtColumn("Screen size in inch", 6.30F);
		table.visualize();

		// 4) Moving the row pointer to next row
		if (table.hasNextRow()) {
			table.moveToNextRow();
		}
		table.visualize();

		// 5) Adding rest of the
		table.setStringAtColumn("Mobile Name", "Realme 5");
		table.setIntegerAtColumn("RAM Memory in GB", 4);
		table.setIntegerAtColumn("Cost in Rs", 10999);
		table.setFloatAtColumn("Screen size in inch", 6.50F);
		if (table.hasNextRow()) {
			table.moveToNextRow();
		}

		table.setStringAtColumn("Mobile Name", "Realme 5");
		table.setIntegerAtColumn("RAM Memory in GB", 3);
		table.setIntegerAtColumn("Cost in Rs", 8999);
		table.setFloatAtColumn("Screen size in inch", 6.50F);
		if (table.hasNextRow()) {
			table.moveToNextRow();
		}

		table.setStringAtColumn("Mobile Name", "Google Pixel 3a XL ");
		table.setIntegerAtColumn("RAM Memory in GB", 4);
		table.setIntegerAtColumn("Cost in Rs", 34999);
		table.setFloatAtColumn("Screen size in inch", 6.00F);

		table.visualize();

		// 6. reset the row pointer to the top
		table.setRowPointer(0);
		table.visualize();

		// 7. insert new column before 'Cost in Rs' column
		table.insertColumnAtIndex("Storage Memory in GB", table.getColumnIndex("Cost in Rs"), TableColumnTypes.Integer);
		table.visualize();

	}
}
