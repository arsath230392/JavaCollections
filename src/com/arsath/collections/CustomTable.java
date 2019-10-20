package com.arsath.collections;

import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

@SuppressWarnings("rawtypes")
public class CustomTable implements Iterable<CustomMap> {

	private CustomMap<Integer, CustomArrayList<Object>> tableMap = new CustomMap<Integer, CustomArrayList<Object>>();
	private int mapCounter = 0;

	public CustomTable(int numberOfColumns) {
		if (!(numberOfColumns < 0)) {
			// the first entry is reserved for column headers
			CustomArrayList<Object> headerList = new CustomArrayList<Object>();

			for (int counter = 0; counter < numberOfColumns; counter++) {
				headerList.add("Column" + counter);
			}
			tableMap.put(mapCounter++, headerList);
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

	public void visualize() {
		JFrame frame = new JFrame();
		frame.setTitle("Table Visualization");
		String[] columnNames = new String[tableMap.getValue(0).getSize()];
		int counter = 0;
		for (Object currentHeader : tableMap.getValue(0)) {
			columnNames[counter] = (String) currentHeader;
			counter++;
		}
		Object[][] data = new Object[tableMap.getSize()][tableMap.getValue(0).getSize()];
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

	@Override
	public Iterator<CustomMap> iterator() {
		return null;
	}

}
