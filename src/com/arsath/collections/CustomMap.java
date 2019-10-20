package com.arsath.collections;

import java.io.Serializable;
import java.util.Iterator;

public class CustomMap<K, V> implements Iterable<MapEntrySet>, Serializable {

	private static final long serialVersionUID = 2725506582821619105L;
	MapEntrySet[] arr = null;

	public void put(K key, V value) {
		if (arr == null) {
			arr = new MapEntrySet[1];
			MapEntrySet entrySet = new MapEntrySet();
			entrySet.setKey(key);
			entrySet.setValue(value);
			arr[0] = entrySet;
		} else {
			// Existing key is searched to replace the value with the new value
			int counter = 0;
			for (MapEntrySet currentEntrySet : arr) {
				if (currentEntrySet.getKey() == key.toString()) {
					arr[counter].setValue(value);
					return;
				}
				counter++;
			}
			// Existing key does not exist, so adding new Entry
			counter = 0;
			MapEntrySet[] tempArray = arr;
			arr = new MapEntrySet[arr.length + 1];
			for (MapEntrySet currentEntrySet : tempArray) {
				arr[counter] = currentEntrySet;
				counter++;
			}
			MapEntrySet entrySet = new MapEntrySet();
			entrySet.setKey(key);
			entrySet.setValue(value);
			arr[counter] = entrySet;
		}
	}

	/**
	 * The iterable interface is implemented so that this Map can be used with
	 * foreach. The implementation of the iterator for the iterable is done in
	 * iterator class
	 * 
	 */
	public Iterator<MapEntrySet> iterator() {
		return new iteratorClass();
	}

	class iteratorClass implements Iterator<MapEntrySet> {
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

		@SuppressWarnings("unchecked")
		@Override
		public MapEntrySet next() {
			if (!hasNext()) {
				try {
					throw new Exception();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			MapEntrySet entrySet = new MapEntrySet();
			entrySet.setKey((K) arr[currentIteratorPosition].getKey());
			entrySet.setValue((V) arr[currentIteratorPosition].getValue());
			currentIteratorPosition++;
			return entrySet;
		}
	}
}
