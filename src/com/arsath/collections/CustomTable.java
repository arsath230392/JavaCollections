package com.arsath.collections;

import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import com.arsath.collections.Enums.TableColumnTypes;
import com.arsath.collections.Exceptions.CannotMoveRowPointerException;
import com.arsath.collections.Exceptions.ColumnExistException;
import com.arsath.collections.Exceptions.ColumnNotExistsException;
import com.arsath.collections.Exceptions.UnableToInitialteTableException;
import com.arsath.collections.Exceptions.WrongIndexException;
import com.arsath.collections.Exceptions.WrongTypeException;
import com.arsath.collections.helper.TableColumn;
import com.arsath.collections.helper.TableRow;

public class CustomTable implements Serializable {

	private static final long serialVersionUID = 3895666524658697673L;

	private TableRow[] arr = null;
	private TableRow headerRow = null;
	private TableRow headerTypeinformation = null;

	private int rowPointer = 0;

	/**
	 * Constructor with number of initial columns
	 * 
	 * @param numberOfColumns - the number of columns to initiate
	 * @throws UnableToInitialteTableException
	 */
	public CustomTable(int numberOfColumns) throws UnableToInitialteTableException {
		if (!(numberOfColumns < 1)) {
			headerRow = new TableRow(numberOfColumns);
			headerTypeinformation = new TableRow(numberOfColumns);
			// initial column name is set to the header
			for (int counter = 0; counter < numberOfColumns; counter++) {
				try {
					headerRow.setColumnValue(counter, ("Column " + (counter + 1)));
					headerTypeinformation.setColumnValue(counter, "Object");
				} catch (WrongIndexException e) {
					e.printStackTrace();
				}
			}
		} else {
			throw new UnableToInitialteTableException(
					"Unable to intitate the table. The number of columns should be greater than 0");
		}
	}

	/**
	 * Default constructor
	 */
	public CustomTable() {
	}

	/**
	 * Adds a new Row to the table
	 */
	public void addRow() {
		if (arr == null) {
			arr = new TableRow[1];
			arr[0] = new TableRow(this.getNumberOfColumns());
		} else {
			TableRow[] temp = arr;
			arr = new TableRow[arr.length + 1];
			System.arraycopy(temp, 0, arr, 0, arr.length - 1);
			arr[arr.length - 1] = new TableRow(this.getNumberOfColumns());
		}
	}

	/**
	 * Total number of new row to add
	 * 
	 * @param numberOfAdditionalRows - the number of new row to be added
	 */
	public void addRows(int numberOfAdditionalRows) {
		for (int counter = 0; counter < numberOfAdditionalRows; counter++) {
			this.addRow();
		}
	}

	/**
	 * inserts new row at index. Can insert one row at the end of the table
	 * 
	 * @param rowIndex - the index at which the row is inserted
	 * @throws WrongIndexException The index should be >=0 and <= last row+1
	 */
	public void insertRowAtIndex(int rowIndex) throws WrongIndexException {
		if (rowIndex < 0 | rowIndex > this.getNumberOfRows()) {
			throw new WrongIndexException("The index is wrong, the index has to be >=0 and the last row+1");
		}
		if (arr == null) {
			arr = new TableRow[1];
			arr[0] = null;
		} else {
			TableRow[] temp = arr;
			arr = new TableRow[arr.length + 1];
			int rowCounter = 0;
			for (TableRow currentTempRow : temp) {
				if (rowCounter == rowIndex) {
					arr[rowCounter] = null;
				} else {
					arr[rowCounter] = currentTempRow;
				}
				rowCounter++;
			}
			arr[rowCounter] = temp[arr.length - 1];
		}
	}

	/**
	 * Removes the row which is currently pointed
	 * 
	 * @throws WrongIndexException
	 */
	public void removeCurrentRow() throws WrongIndexException {
		this.removeRowAtIndex(this.getCurrentRowPointer());
	}

	/**
	 * Removes the row at the given index
	 * 
	 * @param rowIndex
	 * @throws WrongIndexException
	 */
	public void removeRowAtIndex(int rowIndex) throws WrongIndexException {
		if (arr == null) {
			throw new NullPointerException();
		} else if (rowIndex < 0 | rowIndex >= arr.length) {
			throw new WrongIndexException("The column is incorrect");
		} else if (arr.length == 1) {
			arr = null;
		} else {
			TableRow[] temp = arr;
			arr = new TableRow[arr.length - 1];
			int counter = 0;
			for (TableRow currentTableRow : temp) {
				if (rowIndex != counter) {
					arr[counter] = currentTableRow;
					counter++;
				}

			}
			// if the pointer is in last row, it needs to be move to previous row as one row
			// will be removed
			if (this.getCurrentRowPointer() >= this.getNumberOfRows()) {
				try {
					this.moveToPreviousRow();
				} catch (CannotMoveRowPointerException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Adds a new Column to the table
	 */
	public void addColumn(TableColumnTypes type) {
		if (headerRow == null) {
			TableColumn newColumn = new TableColumn();
			newColumn.setValue("Column 1");
			headerRow.addColumn(newColumn);

			TableColumn newheaderInfoColumn = new TableColumn();
			newColumn.setValue(type);
			headerTypeinformation.addColumn(newheaderInfoColumn);

		} else {
			TableColumn newColumn = new TableColumn();
			newColumn.setValue("Column " + headerRow.getColumnSize());
			headerRow.addColumn(newColumn);

			TableColumn newheaderInfoColumn = new TableColumn();
			newColumn.setValue(type);
			headerTypeinformation.addColumn(newheaderInfoColumn);

			// need to create a new column entry for all the existing row
			if (!(arr == null)) {
				for (TableRow currentRow : arr) {
					currentRow.addColumn(null);
				}
			}
		}
	}

	public void addColumn(String columnName, TableColumnTypes type) throws ColumnExistException {
		if (headerRow == null) {
			headerRow = new TableRow(1);
			TableColumn newColumn = new TableColumn();
			newColumn.setValue(columnName);
			try {
				headerRow.setColumnValue(0, newColumn);
			} catch (WrongIndexException e) {
				e.printStackTrace();
			}

			headerTypeinformation = new TableRow(1);
			TableColumn newheaderInfoColumn = new TableColumn();
			newheaderInfoColumn.setValue(type.toString());
			try {
				headerTypeinformation.setColumnValue(0, newheaderInfoColumn);
			} catch (WrongIndexException e) {
				e.printStackTrace();
			}
			return;

		}
		if (columnExist(columnName)) {
			throw new ColumnExistException("This column already Exists");
		} else {
			TableColumn newColumn = new TableColumn();
			newColumn.setValue(columnName);
			headerRow.addColumn(newColumn);

			TableColumn newheaderInfoColumn = new TableColumn();
			newheaderInfoColumn.setValue(type);
			headerTypeinformation.addColumn(newheaderInfoColumn);

			// need to create a new column entry for all the existing row
			if (!(arr == null)) {
				for (TableRow currentRow : arr) {
					currentRow.addColumn(null);
				}
			}
		}
	}

	/**
	 * Adds n number of new columns
	 * 
	 * @param numberOfColumns
	 */
	public void addColumns(int numberOfColumns) {
		for (int counter = 0; counter < numberOfColumns; counter++) {
			this.addColumn(TableColumnTypes.Object);
		}
	}

	/**
	 * Inserts a new column at the specified index
	 * 
	 * @param columnIndex - the position to insert the column
	 * @param type        the tablecolumn type of the new row
	 * @throws WrongIndexException will be thrown if the index is not correct
	 */
	public void insertColumnAtIndex(String columnName, int columnIndex, TableColumnTypes type)
			throws WrongIndexException {
		if (headerRow == null & columnIndex == 0) {
			TableColumn newColumn = new TableColumn();
			newColumn.setValue(columnName);
			headerRow.addColumn(newColumn);

			TableColumn newheaderInfoColumn = new TableColumn();
			newColumn.setValue(type);
			headerTypeinformation.addColumn(newheaderInfoColumn);
			return;
		}
		if (columnIndex < 0 | columnIndex > this.getNumberOfRows()) {
			throw new WrongIndexException("The index is wrong, the index has to be >=0 and the last Column+1");
		} else {
			// insert new Columns in header and header info tables
			TableColumn newHeaderColumn = new TableColumn();
			newHeaderColumn.setValue(columnName);
			headerRow.insertColumnAtIndex(columnIndex, newHeaderColumn);

			TableColumn newHeaderInfoColumn = new TableColumn();
			newHeaderInfoColumn.setValue(type);
			headerTypeinformation.insertColumnAtIndex(columnIndex, newHeaderInfoColumn);
		}

		TableRow[] temp = arr;
		// now we need to create an new column for each table rows
		if (!(arr == null)) {
			for (TableRow currentTempRow : temp) {
				currentTempRow.insertColumnAtIndex(columnIndex, null);
			}
		}
	}

	/**
	 * removes the column at the given index
	 * 
	 * @param columnIndex
	 * @throws WrongIndexException - if the index is incorrect
	 */
	public void removeColumnAtIndex(int columnIndex) throws WrongIndexException {
		if (headerRow == null) {
			throw new NullPointerException("There are no columns present in the table");
		}
		if (columnIndex < 0 | columnIndex >= this.getNumberOfColumns()) {
			throw new WrongIndexException("The index is wrong. It should be >=0 and < currentColumn length");
		}
		headerRow.removeColumnAtIndex(columnIndex);
		headerTypeinformation.removeColumnAtIndex(columnIndex);

		// Now need to remove columns from each Table rows
		TableRow[] tempArr = arr;
		for (TableRow currentTempRow : tempArr) {
			currentTempRow.removeColumnAtIndex(columnIndex);
		}
	}

	/**
	 * Removes the index by column name
	 * 
	 * @param columnName
	 * @throws ColumnNotExistsException - if the column does not exists
	 * @throws WrongIndexException
	 */
	public void removeColumnByName(String columnName) throws ColumnNotExistsException {
		if (!this.columnExist(columnName)) {
			throw new ColumnNotExistsException("This column does not exists");
		}
		try {
			this.removeColumnAtIndex(this.getColumnIndex(columnName));
		} catch (WrongIndexException e) {
			// This exception will not be thrown
			e.printStackTrace();
		}
	}

	/**
	 * Sets the name of the column
	 * 
	 * @param index - the index of the header row
	 * @param name  - the name that has to be set
	 * @throws WrongIndexException
	 * @throws ColumnExistException
	 */
	public void setColumnName(int index, String name) throws WrongIndexException, ColumnExistException {
		if (arr == null) {
			throw new NullPointerException();
		} else if (index < 0 | index >= arr.length) {
			throw new WrongIndexException("The column is incorrect");
		} else if (columnExist(name)) {
			throw new ColumnExistException("The column name already exisits");
		} else {
			headerRow.setColumnValue(index, name);
		}

	}

	public String getColumnName(int index) throws WrongIndexException {
		if (headerRow == null) {
			throw new NullPointerException();
		}
		if (index < 0 | index >= this.getCurrentRowPointer()) {
			throw new WrongIndexException("The index is not correct");
		}
		return headerRow.getColumnValue(index).toString();
	}

	public int getColumnIndex(String columnName) throws ColumnNotExistsException {
		if (!this.columnExist(columnName)) {
			throw new ColumnNotExistsException("This column does not exists");
		}
		int columnCounter = 0;
		for (TableColumn currentColumn : headerRow) {
			if (currentColumn.getValue().toString().equals(columnName)) {
				return columnCounter;
			}
			columnCounter++;
		}
		return columnCounter;
	}

	/**
	 * The column type is set for type safe operations Use caution while using this
	 * method. Try to set the column type at initialization
	 * 
	 * @param index
	 * @param type
	 * @throws WrongIndexException
	 */
	public void setColumnType(int index, TableColumnTypes type) throws WrongIndexException {
		if (arr == null) {
			throw new NullPointerException();
		} else if (index < 0 | index >= arr.length) {
			throw new WrongIndexException("The column is incorrect");
		} else {
			headerTypeinformation.setColumnValue(index, type);
		}
	}

	/**
	 * returns the column type at index
	 * 
	 * @param index
	 * @return the type of the column is returned
	 * @throws WrongIndexException
	 */
	public TableColumnTypes getColumnType(int index) throws WrongIndexException {
		if (arr == null) {
			throw new NullPointerException();
		} else if (index < 0 | index >= arr.length) {
			throw new WrongIndexException("The column is incorrect");
		} else {
			return (TableColumnTypes) headerTypeinformation.getColumnValue(index);
		}
	}

	public boolean columnExist(String name) {
		for (TableColumn currentColumn : headerRow) {
			if (currentColumn.getValue().toString().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets the Object value at the row and column mentioned in the parameter
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	public void setObjectAtPosition(int columnPosition, Object value) throws WrongIndexException, WrongTypeException {
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnPosition > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnPosition < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnPosition).toString()
					.equals(TableColumnTypes.Object.toString())) {
				setAtPosition(this.getCurrentRowPointer(), columnPosition, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnPosition));
			}
		}
	}

	/**
	 * Sets the Object value at the row and column mentioned in the parameter
	 * 
	 * @param columnName
	 * @param value
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 */
	public void setObjectAtColumn(String columnName, Object value) throws WrongTypeException, ColumnNotExistsException {
		try {
			this.setObjectAtPosition(this.getColumnIndex(columnName), value);
		} catch (WrongIndexException e) {
			// This exception not likely to be thrown
			e.printStackTrace();
		}

	}

	/**
	 * Sets the String value at the row and column mentioned in the parameter
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	public void setStringAtPosition(int columnPosition, String value) throws WrongIndexException, WrongTypeException {
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnPosition > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnPosition < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnPosition).toString()
					.equals(TableColumnTypes.String.toString())) {
				setAtPosition(this.getCurrentRowPointer(), columnPosition, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnPosition));
			}
		}
	}

	/**
	 * Sets the String value at the row and column mentioned in the parameter
	 * 
	 * @param columnName
	 * @param value
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 */
	public void setStringAtColumn(String columnName, String value) throws WrongTypeException, ColumnNotExistsException {
		try {
			this.setStringAtPosition(this.getColumnIndex(columnName), value);
		} catch (WrongIndexException e) {
			// This exception not likely to be thrown
			e.printStackTrace();
		}

	}

	/**
	 * Sets the Integer value at the row and column mentioned in the parameter
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	public void setIntegerAtPosition(int columnPosition, Integer value) throws WrongIndexException, WrongTypeException {
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnPosition > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnPosition < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnPosition).toString()
					.equals(TableColumnTypes.Integer.toString())) {
				setAtPosition(this.getCurrentRowPointer(), columnPosition, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnPosition));
			}
		}
	}

	/**
	 * Sets the Integer value at the row and column mentioned in the parameter
	 * 
	 * @param columnName
	 * @param value
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 */
	public void setIntegerAtColumn(String columnName, Integer value)
			throws WrongTypeException, ColumnNotExistsException {
		try {
			this.setIntegerAtPosition(this.getColumnIndex(columnName), value);
		} catch (WrongIndexException e) {
			// This exception not likely to be thrown
			e.printStackTrace();
		}

	}

	/**
	 * Sets the Byte value at the row and column mentioned in the parameter
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	public void setByteAtPosition(int columnPosition, Byte value) throws WrongIndexException, WrongTypeException {
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnPosition > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnPosition < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnPosition).toString()
					.equals(TableColumnTypes.Byte.toString())) {
				setAtPosition(this.getCurrentRowPointer(), columnPosition, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnPosition));
			}
		}
	}

	/**
	 * Sets the Byte value at the row and column mentioned in the parameter
	 * 
	 * @param columnName
	 * @param value
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 */
	public void setByteAtColumn(String columnName, Byte value) throws WrongTypeException, ColumnNotExistsException {
		try {
			this.setByteAtPosition(this.getColumnIndex(columnName), value);
		} catch (WrongIndexException e) {
			// This exception not likely to be thrown
			e.printStackTrace();
		}

	}

	/**
	 * Sets the Double value at the row and column mentioned in the parameter
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	public void setDoubleAtPosition(int columnPosition, Double value) throws WrongIndexException, WrongTypeException {
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnPosition > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnPosition < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnPosition).toString()
					.equals(TableColumnTypes.Double.toString())) {
				setAtPosition(this.getCurrentRowPointer(), columnPosition, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnPosition));
			}
		}
	}

	/**
	 * Sets the Double value at the row and column mentioned in the parameter
	 * 
	 * @param columnName
	 * @param value
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 */
	public void setDoubleAtColumn(String columnName, Double value) throws WrongTypeException, ColumnNotExistsException {
		try {
			this.setDoubleAtPosition(this.getColumnIndex(columnName), value);
		} catch (WrongIndexException e) {
			// This exception not likely to be thrown
			e.printStackTrace();
		}

	}

	/**
	 * Sets the Short value at the row and column mentioned in the parameter
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	public void setShortAtPosition(int columnPosition, Short value) throws WrongIndexException, WrongTypeException {
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnPosition > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnPosition < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnPosition).toString()
					.equals(TableColumnTypes.Short.toString())) {
				setAtPosition(this.getCurrentRowPointer(), columnPosition, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnPosition));
			}
		}
	}

	/**
	 * Sets the Short value at the row and column mentioned in the parameter
	 * 
	 * @param columnName
	 * @param value
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 */
	public void setShortAtColumn(String columnName, Short value) throws WrongTypeException, ColumnNotExistsException {
		try {
			this.setShortAtPosition(this.getColumnIndex(columnName), value);
		} catch (WrongIndexException e) {
			// This exception not likely to be thrown
			e.printStackTrace();
		}

	}

	/**
	 * Sets the Float value at the row and column mentioned in the parameter
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	public void setFloatAtPosition(int columnPosition, Float value) throws WrongIndexException, WrongTypeException {
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnPosition > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnPosition < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnPosition).toString().equals("Float")) {
				setAtPosition(this.getCurrentRowPointer(), columnPosition, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnPosition));
			}
		}
	}

	/**
	 * Sets the Float value at the row and column mentioned in the parameter
	 * 
	 * @param columnName
	 * @param value
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 */
	public void setFloatAtColumn(String columnName, Float value) throws WrongTypeException, ColumnNotExistsException {
		try {
			this.setFloatAtPosition(this.getColumnIndex(columnName), value);
		} catch (WrongIndexException e) {
			// This exception not likely to be thrown
			e.printStackTrace();
		}

	}

	/**
	 * Sets the Long value at the row and column mentioned in the parameter
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	public void setLongAtPosition(int columnPosition, Long value) throws WrongIndexException, WrongTypeException {
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnPosition > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnPosition < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnPosition).toString().equals("Float")) {
				setAtPosition(this.getCurrentRowPointer(), columnPosition, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnPosition));
			}
		}
	}

	/**
	 * Sets the Long value at the row and column mentioned in the parameter
	 * 
	 * @param columnName
	 * @param value
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 */
	public void setLongAtColumn(String columnName, Long value) throws WrongTypeException, ColumnNotExistsException {
		try {
			this.setLongAtPosition(this.getColumnIndex(columnName), value);
		} catch (WrongIndexException e) {
			// This exception not likely to be thrown
			e.printStackTrace();
		}

	}

	private void setAtPosition(int row, int column, Object value) throws WrongIndexException {
		if (arr == null) {
			throw new NullPointerException("There are no rows present in the table");
		}
		arr[row].setColumnValue(column, value);
	}

	/**
	 * gets the value at the columnIndex, if the type matches defined type
	 * 
	 * @param columnIndex
	 * @return
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	public String getStringValueAtPosition(int columnIndex) throws WrongIndexException, WrongTypeException {
		String stringValue = "";
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnIndex > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnIndex < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnIndex).toString()
					.equals(TableColumnTypes.String.toString())) {
				stringValue = getValueAtPosition(this.getCurrentRowPointer(), columnIndex).toString();
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnIndex));
			}
		}
		return stringValue;
	}

	/**
	 * gets the value at the column name , if the type matches defined type
	 * 
	 * @param columnName
	 * @return
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 * @throws WrongIndexException
	 */
	@SuppressWarnings("unused")
	public String getStringValueAtColumn(String columnName)
			throws WrongTypeException, ColumnNotExistsException, WrongIndexException {
		if (arr == null) {
			throw new NullPointerException("There are no rows present in the table");
		}
		return this.getStringValueAtPosition(this.getColumnIndex(columnName));
	}

	/**
	 * gets the value at the columnIndex, if the type matches defined type
	 * 
	 * @param columnIndex
	 * @return
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	@SuppressWarnings("unused")
	public Object getObjectValueAtPosition(int columnIndex) throws WrongIndexException, WrongTypeException {
		Object ObjectValue = "";
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnIndex > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnIndex < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnIndex).toString()
					.equals(TableColumnTypes.Object.toString())) {
				ObjectValue = getValueAtPosition(this.getCurrentRowPointer(), columnIndex);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnIndex));
			}
		}
		return ObjectValue;
	}

	/**
	 * gets the value at the column name , if the type matches defined type
	 * 
	 * @param columnName
	 * @return
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 * @throws WrongIndexException
	 */
	@SuppressWarnings("unused")
	public Object getObjectValueAtColumn(String columnName)
			throws WrongTypeException, ColumnNotExistsException, WrongIndexException {
		if (arr == null) {
			throw new NullPointerException("There are no rows present in the table");
		}
		return this.getObjectValueAtPosition(this.getColumnIndex(columnName));
	}

	/**
	 * gets the value at the columnIndex, if the type matches defined type
	 * 
	 * @param columnIndex
	 * @return
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	@SuppressWarnings("unused")
	public Byte getByteValueAtPosition(int columnIndex) throws WrongIndexException, WrongTypeException {
		Byte byteValue;
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnIndex > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnIndex < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnIndex).toString().equals(TableColumnTypes.Byte.toString())) {
				byteValue = (Byte) getValueAtPosition(this.getCurrentRowPointer(), columnIndex);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnIndex));
			}
		}
		return byteValue;
	}

	/**
	 * gets the value at the column name , if the type matches defined type
	 * 
	 * @param columnName
	 * @return
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 * @throws WrongIndexException
	 */
	@SuppressWarnings("unused")
	public Byte getByteValueAtColumn(String columnName)
			throws WrongTypeException, ColumnNotExistsException, WrongIndexException {
		if (arr == null) {
			throw new NullPointerException("There are no rows present in the table");
		}
		return this.getByteValueAtPosition(this.getColumnIndex(columnName));
	}

	/**
	 * gets the value at the columnIndex, if the type matches defined type
	 * 
	 * @param columnIndex
	 * @return
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	@SuppressWarnings("unused")
	private Short getShortValueAtPosition(int columnIndex) throws WrongIndexException, WrongTypeException {
		Short shortValue;
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnIndex > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnIndex < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnIndex).toString().equals(TableColumnTypes.Byte.toString())) {
				shortValue = (Short) getValueAtPosition(this.getCurrentRowPointer(), columnIndex);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnIndex));
			}
		}
		return shortValue;
	}

	/**
	 * gets the value at the column name , if the type matches defined type
	 * 
	 * @param columnName
	 * @return
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 * @throws WrongIndexException
	 */
	public Short getShortValueAtColumn(String columnName)
			throws WrongTypeException, ColumnNotExistsException, WrongIndexException {
		if (arr == null) {
			throw new NullPointerException("There are no rows present in the table");
		}
		return this.getShortValueAtPosition(this.getColumnIndex(columnName));
	}

	/**
	 * gets the value at the columnIndex, if the type matches defined type
	 * 
	 * @param columnIndex
	 * @return
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	private Integer getIntegerValueAtPosition(int columnIndex) throws WrongIndexException, WrongTypeException {
		Integer integerValue;
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnIndex > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnIndex < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnIndex).toString().equals(TableColumnTypes.Byte.toString())) {
				integerValue = (Integer) getValueAtPosition(this.getCurrentRowPointer(), columnIndex);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnIndex));
			}
		}
		return integerValue;
	}

	/**
	 * gets the value at the column name , if the type matches defined type
	 * 
	 * @param columnName
	 * @return
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 * @throws WrongIndexException
	 */
	public Integer getIntegerValueAtColumn(String columnName)
			throws WrongTypeException, ColumnNotExistsException, WrongIndexException {
		if (arr == null) {
			throw new NullPointerException("There are no rows present in the table");
		}
		return this.getIntegerValueAtPosition(this.getColumnIndex(columnName));
	}

	/**
	 * gets the value at the columnIndex, if the type matches defined type
	 * 
	 * @param columnIndex
	 * @return
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	private Long getLongValueAtPosition(int columnIndex) throws WrongIndexException, WrongTypeException {
		Long longValue;
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnIndex > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnIndex < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnIndex).toString().equals(TableColumnTypes.Byte.toString())) {
				longValue = (Long) getValueAtPosition(this.getCurrentRowPointer(), columnIndex);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnIndex));
			}
		}
		return longValue;
	}

	/**
	 * gets the value at the column name , if the type matches defined type
	 * 
	 * @param columnName
	 * @return
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 * @throws WrongIndexException
	 */
	public Long getLongValueAtColumn(String columnName)
			throws WrongTypeException, ColumnNotExistsException, WrongIndexException {
		if (arr == null) {
			throw new NullPointerException("There are no rows present in the table");
		}
		return this.getLongValueAtPosition(this.getColumnIndex(columnName));
	}

	/**
	 * gets the value at the columnIndex, if the type matches defined type
	 * 
	 * @param columnIndex
	 * @return
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	@SuppressWarnings("unused")
	private Float getFloatValueAtPosition(int columnIndex) throws WrongIndexException, WrongTypeException {
		Float floatValue;
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnIndex > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnIndex < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnIndex).toString().equals(TableColumnTypes.Byte.toString())) {
				floatValue = (Float) getValueAtPosition(this.getCurrentRowPointer(), columnIndex);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnIndex));
			}
		}
		return floatValue;
	}

	/**
	 * gets the value at the column name , if the type matches defined type
	 * 
	 * @param columnName
	 * @return
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 * @throws WrongIndexException
	 */
	public Float getFloatValueAtColumn(String columnName)
			throws WrongTypeException, ColumnNotExistsException, WrongIndexException {
		if (arr == null) {
			throw new NullPointerException("There are no rows present in the table");
		}
		return this.getFloatValueAtPosition(this.getColumnIndex(columnName));
	}

	/**
	 * gets the value at the columnIndex, if the type matches defined type
	 * 
	 * @param columnIndex
	 * @return
	 * @throws WrongIndexException
	 * @throws WrongTypeException
	 */
	@SuppressWarnings("unused")
	private Double getDoubleValueAtPosition(int columnIndex) throws WrongIndexException, WrongTypeException {
		Double floatValue;
		if (this.getCurrentRowPointer() > this.getNumberOfRows() | columnIndex > this.getNumberOfColumns()
				| this.getCurrentRowPointer() < 0 | columnIndex < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(columnIndex).toString().equals(TableColumnTypes.Byte.toString())) {
				floatValue = (Double) getValueAtPosition(this.getCurrentRowPointer(), columnIndex);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(columnIndex));
			}
		}
		return floatValue;
	}

	/**
	 * gets the value at the column name , if the type matches defined type
	 * 
	 * @param columnName
	 * @return
	 * @throws WrongTypeException
	 * @throws ColumnNotExistsException
	 * @throws WrongIndexException
	 */
	public Double getDoubleValueAtColumn(String columnName)
			throws WrongTypeException, ColumnNotExistsException, WrongIndexException {
		if (arr == null) {
			throw new NullPointerException("There are no rows present in the table");
		}
		return this.getDoubleValueAtPosition(this.getColumnIndex(columnName));
	}

	private Object getValueAtPosition(int row, int column) throws WrongIndexException {
		if (arr == null) {
			throw new NullPointerException("There are no rows present in the table");
		}
		if (arr[row] == null) {
			return null;
		}
		return arr[row].getColumnValue(column);
	}

	/**
	 * This methods sorts the table by the value present in the column index
	 * provided
	 * 
	 * @param columnIndex
	 * @throws WrongIndexException
	 */
	public void sortByColumnPosition(int columnIndex) throws WrongIndexException {
		if (arr == null) {
			throw new NullPointerException();
		}
		if (columnIndex < 0 | columnIndex >= this.getNumberOfColumns()) {
			throw new WrongIndexException("The index of the column is wrong");
		}
		performQuickSort(columnIndex, 0, arr.length - 1);
	}

	/**
	 * This method sorts the table by the value present in the column name
	 * 
	 * @param columnName
	 * @throws ColumnNotExistsException
	 */
	public void sortByColumnName(String columnName) throws ColumnNotExistsException {
		if (!(this.columnExist(columnName))) {
			throw new ColumnNotExistsException("The column does not exist");
		}
		try {
			this.sortByColumnPosition(this.getColumnIndex(columnName));
		} catch (WrongIndexException e) {
			// This error should not occur for this method
			e.printStackTrace();
		}
	}

	public void reverseSortByColumnPosition(int columnIndex) throws WrongIndexException {
		this.sortByColumnPosition(columnIndex);
		TableRow[] tempArr = arr;
		int rowCounter = arr.length - 1;
		for (TableRow currentTempArray : tempArr) {
			arr[rowCounter] = currentTempArray;
			rowCounter--;
		}
	}

	public void reverseSortByColumnName(String columnName) throws WrongIndexException, ColumnNotExistsException {
		this.reverseSortByColumnPosition(this.getColumnIndex(columnName));
	}

	private void performQuickSort(int columnIndex, int low, int high) {
		if (low < high) {
			/*
			 * pi is partitioning index, arr[pi] is now at right place
			 */
			int pi = 0;
			try {
				pi = partition(columnIndex, low, high);
			} catch (WrongIndexException e) {
				e.printStackTrace();
			}

			// Recursively sort elements before
			// partition and after partition
			performQuickSort(columnIndex, low, pi - 1);
			performQuickSort(columnIndex, pi + 1, high);
		}
	}

	private int partition(int columnIndex, int low, int high) throws WrongIndexException {
		Object pivot = this.arr[high].getColumnValue(columnIndex);
		int i = (low - 1); // index of smaller element
		for (int j = low; j < high; j++) {
			// If current element is smaller than the pivot
			if (arr[j].getColumnValue(columnIndex).toString().toLowerCase()
					.compareTo(pivot.toString().toLowerCase()) > 0) {
				i++;
				// swap arr[i] and arr[j]
				TableRow temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		// swap arr[i+1] and arr[high] (or pivot)
		TableRow temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;

		return i + 1;
	}

	/**
	 * This method shows the content of the table in UI
	 */
	JFrame frame;

	public void visualize() {
		if (frame != null) {
			frame.dispose();
		}
		frame = new JFrame();
		frame.setTitle("Table Visualization");
		String[] columnNames = new String[this.getNumberOfColumns() + 1];
		columnNames[0] = "Row/ColumnName";
		int counter = 1;
		for (TableColumn currentHeader : headerRow) {
			columnNames[counter] = currentHeader.getValue().toString();
			counter++;
		}
		Object[][] data = new Object[this.getNumberOfRows() + 1][this.getNumberOfColumns() + 1];
		data[0][0] = "";
		int columnCounter = 1;
		// the type information is set after the header
		for (TableColumn currentType : headerTypeinformation) {
			data[0][columnCounter] = "Type = " + currentType.getValue().toString();
			columnCounter++;
		}
		// the actual values are set
		for (int rowCounter = 0; rowCounter < this.getNumberOfRows(); rowCounter++) {
			if (rowCounter == rowPointer) {
				data[rowCounter + 1][0] = "=> " + rowCounter;
			} else {
				data[rowCounter + 1][0] = rowCounter;
			}
			for (int columnCounterdata = 0; columnCounterdata < this.getNumberOfColumns(); columnCounterdata++) {
				try {
					if (arr != null) {
						data[rowCounter + 1][columnCounterdata + 1] = this.getValueAtPosition(rowCounter,
								columnCounterdata);
					}
				} catch (WrongIndexException e) {
					e.printStackTrace();
				}
			}
		}
		JTable table = new JTable(data, columnNames);
		table.setBounds(30, 40, 200, 300);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(sp);
		// Frame Size
		frame.setSize(500, 200);
		// Frame Visible = true
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.repaint();
	}

	/**
	 * 
	 * @return the total number of rows
	 */
	public int getNumberOfRows() {
		if (arr == null) {
			return 0;
		}
		return arr.length;
	}

	/**
	 * The total number of columns in the table
	 * 
	 * @return the number of column
	 */
	public int getNumberOfColumns() {
		if (headerRow == null) {
			return 0;
		}
		return headerRow.getColumnSize();
	}

	// The below methods are for navigating the table
	public boolean hasNextRow() {
		if (arr == null) {
			return false;
		}
		if (this.getCurrentRowPointer() == this.getNumberOfRows() - 1) {
			return false;
		}
		return true;
	}

	public void moveToNextRow() throws CannotMoveRowPointerException {
		if (this.hasNextRow()) {
			this.rowPointer++;
		} else {
			throw new CannotMoveRowPointerException("Cannot move the row pointer");
		}
	}

	public boolean hasPreviousRow() {
		if (arr == null) {
			return false;
		}
		if (this.getCurrentRowPointer() == 0) {
			return false;
		}
		return true;
	}

	public void moveToPreviousRow() throws CannotMoveRowPointerException {
		if (hasPreviousRow()) {
			this.rowPointer--;
		} else {
			throw new CannotMoveRowPointerException("Cannot move the row pointer");
		}
	}

	public int getCurrentRowPointer() {
		return this.rowPointer;
	}

	public void setRowPointer(int positionToSet) throws WrongIndexException {
		if (arr == null) {
			throw new NullPointerException();
		}
		if (positionToSet < 0 | positionToSet >= arr.length) {
			throw new WrongIndexException("The index is wrong");
		}
		this.rowPointer = positionToSet;
	}
}
