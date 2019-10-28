package com.arsath.collections.helper;

import java.io.Serializable;
import java.util.Iterator;

import com.arsath.collections.Exceptions.WrongIndexException;

public class TableRow implements Iterable<TableColumn>, Serializable {

	private static final long serialVersionUID = -303344631636989311L;

	private TableColumn[] arr = null;

	public TableRow(int numberOfColumns) {
		arr = new TableColumn[numberOfColumns];
	}

	/**
	 * Default constructor is not enabled. The number of column is mandatory
	 */
	@SuppressWarnings("unused")
	private TableRow() {
	}

	public void setColumnValue(int columnIndex, Object value) throws WrongIndexException {
		if (arr == null) {
			throw new NullPointerException("The Table row is null");
		}
		if (columnIndex > arr.length | columnIndex < 0) {
			throw new WrongIndexException("");
		}
		TableColumn currentColumn = new TableColumn();
		currentColumn.setValue(value);
		arr[columnIndex] = currentColumn;
	}

	public Object getColumnValue(int columnIndex) throws WrongIndexException {
		if (arr == null | columnIndex < 0 | columnIndex >= this.getColumnSize()) {
			throw new WrongIndexException("The column index is wrong");
		}
		if (arr[columnIndex] == null) {
			return null;
		}
		return arr[columnIndex].getValue();
	}

	public int getColumnSize() {
		return arr.length;
	}

	@Override
	public Iterator<TableColumn> iterator() {
		return new iteratorClass();
	}

	class iteratorClass implements Iterator<TableColumn> {
		private int currentIteratorPosition = 0;

		@Override
		public boolean hasNext() {
			if (arr == null) {
				return false;
			}
			if (currentIteratorPosition < arr.length) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public TableColumn next() {
			if (!hasNext()) {
				try {
					throw new Exception();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return arr[currentIteratorPosition++];
		}
	}

	public void addColumn(TableColumn column) {
		if (arr == null) {
			arr = new TableColumn[1];
			arr[0] = column;

		} else {
			TableColumn[] temp = arr;
			arr = new TableColumn[arr.length + 1];
			System.arraycopy(temp, 0, arr, 0, temp.length);
			arr[arr.length - 1] = column;
		}
	}

	public void insertColumnAtIndex(int columnindex, TableColumn column) {
		if (arr == null & columnindex == 0) {
			arr = new TableColumn[1];
			arr[0] = column;
		} else {
			TableColumn[] temp = arr;
			arr = new TableColumn[arr.length + 1];
			int columnCounter = 0;
			for (TableColumn currentTempColumn : temp) {
				if (columnCounter == columnindex) {
					arr[columnCounter] = column;
					columnCounter++;
				}
				arr[columnCounter] = currentTempColumn;
				columnCounter++;
			}
		}
	}

	public void removeColumnAtIndex(int columnindex) {
		if (arr == null) {
			throw new NullPointerException();
		}
		if (columnindex == 0 && this.getColumnSize() == 1) {
			arr = null;
		}
	}
}
