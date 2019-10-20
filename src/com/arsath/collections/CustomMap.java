package com.arsath.collections;

import java.io.Serializable;
import java.util.Iterator;

public class CustomMap<K, V> implements Iterable<MapEntrySet>, Serializable {

	private static final long serialVersionUID = 2725506582821619105L;
	MapEntrySet[] arr = null;

	/**
	 * Default constructor
	 */
	public CustomMap() {

	}

	/**
	 * Initializes the internal array with the specified size
	 * 
	 * @param the initial size of the array
	 */
	public CustomMap(int size) {
		arr = new MapEntrySet[size];
	}

	/**
	 * Enters the value corresponding to the existing key. If the key is not present
	 * then a new k,v entry is added
	 * 
	 * @param key
	 * @param value
	 */
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
	 * The corresponding value of the key is returned
	 * 
	 * @param key- this key searched in the map
	 * @return V - the value corresponding to the key
	 */
	@SuppressWarnings("unchecked")
	public V getValue(K key) {
		if (arr == null | !this.containsKey(key)) {
			return null;
		}
		for (MapEntrySet currentEntrySet : arr) {
			if (key == currentEntrySet.getKey()) {
				return (V) currentEntrySet.getValue();
			}
		}
		return null;
	}

	/**
	 * The key value pair in the index is replaced with the new value in the
	 * parameter
	 * 
	 * @param index - the index to replace
	 * @param key
	 * @param value
	 * @throws WrongIndexException
	 */
	public void setAtindex(int index, K key, V value) throws WrongIndexException {
		if (arr == null | index < 0 | index >= arr.length) {
			throw new WrongIndexException("The index is either out of bound or the array is empty");
		}
		MapEntrySet entrySet = new MapEntrySet();
		entrySet.setKey(key);
		entrySet.setValue(value);

		arr[index] = entrySet;
	}

	/**
	 * The value at the index is removed
	 * 
	 * @param index - the position to remove from the map
	 * @throws WrongIndexException
	 */
	public void removeAtindex(int index) throws WrongIndexException {
		if (arr == null | index < 0 | index >= arr.length) {
			throw new WrongIndexException("The index is either out of bound or the array is empty");
		}
		MapEntrySet[] tempArr = arr;
		int counter = 0;
		arr = new MapEntrySet[arr.length - 1];
		for (MapEntrySet currentEntrySet : tempArr) {
			if (!(counter == index)) {
				arr[counter] = currentEntrySet;
			}
			counter++;
		}
	}

	/**
	 * The Entry set whose key match will be removed from the Map
	 * 
	 * @param key - the element will be removed where the key matches
	 */
	public void remove(K key) {
		if (arr == null) {
			return;
		} else if (this.containsKey(key)) {
			MapEntrySet[] tempArr = arr;
			arr = new MapEntrySet[arr.length - 1];
			int counter = 0;
			for (MapEntrySet currentEntrySet : tempArr) {
				if (!(currentEntrySet.getKey() == key)) {
					arr[counter] = currentEntrySet;
				}
				counter++;
			}
		}
	}

	/**
	 * To check if the key is contained in the map
	 * 
	 * @param key - the key that needs to be checked
	 * @return if the key is present in the map, true is returned or false is
	 *         returned
	 */
	public boolean containsKey(K key) {
		for (MapEntrySet currentEntrySet : arr) {
			if (currentEntrySet.getKey() == key) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the values of the Map in a readable format
	 */
	public String toString() {
		if (arr == null) {
			return "[]";
		}
		String toString = "";
		for (MapEntrySet currentEntrySet : arr) {
			toString += "[Key-> " + currentEntrySet.getKey() + " :: Value-> " + currentEntrySet.getValue() + "]\n";
		}
		return toString;
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
