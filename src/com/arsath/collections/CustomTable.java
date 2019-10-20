package com.arsath.collections;

import java.util.Iterator;

@SuppressWarnings("rawtypes")
public class CustomTable implements Iterable<CustomMap> {

	private CustomMap<Integer, CustomArrayList<Object>> tableMap = new CustomMap<Integer, CustomArrayList<Object>>();

	public CustomTable(int numberOfColumns) {
		if (!(numberOfColumns < 0)) {
			// the first entry is reserved for column headers
			CustomArrayList<Object> headerList = new CustomArrayList<Object>();

			for (int counter = 0; counter < numberOfColumns; counter++) {
				headerList.add("Column" + counter);
			}
			tableMap.put(1, headerList);
		}
	}

	@SuppressWarnings("unchecked")
	public String toString() {
		String toString = "";
		for (MapEntrySet currentRow : tableMap) {
			toString += "|";
			for (Object currentColumn : (CustomArrayList<Object>) currentRow.getValue()) {
				toString += currentColumn + "|";
			}
			toString += "\n";
		}
		return toString;

	}

	@Override
	public Iterator<CustomMap> iterator() {
		return null;
	}

}
