package com.arsath.collections;

import java.io.Serializable;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import com.arsath.collections.Enums.TableColumnTypes;
import com.arsath.collections.Exceptions.ColumnExistException;
import com.arsath.collections.Exceptions.UnableToInitialteTableException;
import com.arsath.collections.Exceptions.WrongIndexException;
import com.arsath.collections.Exceptions.WrongTypeException;
import com.arsath.collections.helper.TableColumn;
import com.arsath.collections.helper.TableRow;

@SuppressWarnings("rawtypes")
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
	 * @param numberOfAdditionalRows - the number of news row to be added
	 */
	public void addRows(int numberOfAdditionalRows) {
		for (int counter = 0; counter < numberOfAdditionalRows; counter++) {
			this.addRow();
		}
	}

	/**
	 * Adds a new Column to the table
	 */
	public void addColumn() {
		if (headerRow == null) {
			TableColumn newColumn = new TableColumn();
			newColumn.setValue("Column 1");
			headerRow.addColumn(newColumn);

		} else {
			TableColumn newColumn = new TableColumn();
			newColumn.setValue("Column " + headerRow.getColumnSize());
			headerRow.addColumn(newColumn);

			TableColumn newheaderInfoColumn = new TableColumn();
			newColumn.setValue("Object");
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
			this.addColumn();
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

	/**
	 * The column type is set for type safe operations
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
	public void setObjectAtPosition(int row, int column, Object value) throws WrongIndexException, WrongTypeException {
		if (row > this.getNumberOfRows() | column > this.getNumberOfColumns() | row < 0 | column < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(column).toString().equals(TableColumnTypes.Object.toString())) {
				setAtPosition(row, column, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(column));
			}
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
	public void setStringAtPosition(int row, int column, String value) throws WrongIndexException, WrongTypeException {
		if (row > this.getNumberOfRows() | column > this.getNumberOfColumns() | row < 0 | column < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(column).toString().equals(TableColumnTypes.String.toString())) {
				setAtPosition(row, column, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(column));
			}
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
	public void setintegerAtPosition(int row, int column, Integer value)
			throws WrongIndexException, WrongTypeException {
		if (row > this.getNumberOfRows() | column > this.getNumberOfColumns() | row < 0 | column < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(column).toString().equals(TableColumnTypes.Integer.toString())) {
				setAtPosition(row, column, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(column));
			}
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
	public void setByteAtPosition(int row, int column, Byte value) throws WrongIndexException, WrongTypeException {
		if (row > this.getNumberOfRows() | column > this.getNumberOfColumns() | row < 0 | column < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(column).toString().equals(TableColumnTypes.Byte.toString())) {
				setAtPosition(row, column, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(column));
			}
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
	public void setDoubleAtPosition(int row, int column, Double value) throws WrongIndexException, WrongTypeException {
		if (row > this.getNumberOfRows() | column > this.getNumberOfColumns() | row < 0 | column < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(column).toString().equals(TableColumnTypes.Double.toString())) {
				setAtPosition(row, column, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(column));
			}
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
	public void setShortAtPosition(int row, int column, Short value) throws WrongIndexException, WrongTypeException {
		if (row > this.getNumberOfRows() | column > this.getNumberOfColumns() | row < 0 | column < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(column).toString().equals(TableColumnTypes.Short.toString())) {
				setAtPosition(row, column, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(column));
			}
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
	public void setFloatAtPosition(int row, int column, Float value) throws WrongIndexException, WrongTypeException {
		if (row > this.getNumberOfRows() | column > this.getNumberOfColumns() | row < 0 | column < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(column).toString().equals("Float")) {
				setAtPosition(row, column, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(column));
			}
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
	public void setLongAtPosition(int row, int column, Long value) throws WrongIndexException, WrongTypeException {
		if (row > this.getNumberOfRows() | column > this.getNumberOfColumns() | row < 0 | column < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(column).toString().equals("Float")) {
				setAtPosition(row, column, value);
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(column));
			}
		}
	}

	private void setAtPosition(int row, int column, Object value) throws WrongIndexException {
		arr[row].setColumnValue(column, value);
	}

	private String getStringValueAtPosition(int row, int column) throws WrongIndexException, WrongTypeException {
		String stringValue = "";
		if (row > this.getNumberOfRows() | column > this.getNumberOfColumns() | row < 0 | column < 0) {
			throw new WrongIndexException("The row or column is incorrect");
		} else {
			if (headerTypeinformation.getColumnValue(column).toString().equals(TableColumnTypes.String.toString())) {
				stringValue = getValueAtPosition(row, column).toString();
			} else {
				throw new WrongTypeException("The type of the column is incorrect :: Expected type->"
						+ headerTypeinformation.getColumnValue(column));
			}
		}
		return stringValue;
	}

	private Object getValueAtPosition(int row, int column) throws WrongIndexException {
		if (arr[row] == null) {
			return null;
		}
		return arr[row].getColumnValue(column);
	}

	public void visualize() {
		JFrame frame = new JFrame();
		frame.setTitle("Table Visualization");
		String[] columnNames = new String[this.getNumberOfColumns() + 1];
		columnNames[0] = "Row/ColumnName";
		int counter = 1;
		for (TableColumn currentHeader : headerRow) {
			columnNames[counter] = (String) currentHeader.getValue();
			counter++;
		}
		Object[][] data = new Object[this.getNumberOfRows() + 1][this.getNumberOfColumns() + 1];
		data[0][0] = "";
		int columnCounter = 1;
		// the type information is set after the header
		for (TableColumn currentType : headerTypeinformation) {
			data[0][columnCounter] = "Type = " + currentType.getValue();
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
		return headerRow.getColumnSize();
	}

	// The below methods are for navigating the table
	public boolean hasNextRow() {
		return false;

	}

	public void moveToNextRow() {

	}

	public int getCurrentRowPointer() {
		return 0;
	}

	public void setRowPointer(int positionToSet) {

	}
}
