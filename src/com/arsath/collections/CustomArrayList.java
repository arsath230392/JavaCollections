package com.arsath.collections;

import java.io.Serializable;
import java.util.Iterator;

public class CustomArrayList<E> implements Iterable<E>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3978994619048637774L;
	private Object arr[] = null;

	/**
	 * Adds an element to the list
	 * 
	 * @param the object that needs to be added
	 * @return if the operation is successful
	 */
	public boolean add(E entry) {
		if (arr == null) {
			arr = new Object[1];
			arr[0] = entry;
		} else {
			Object tempArray[] = arr;
			arr = new Object[arr.length + 1];

			int counter = 0;
			for (Object currentTempObj : tempArray) {
				arr[counter] = currentTempObj;
				counter++;
			}
			arr[counter] = entry;
		}
		return true;
	}

	/**
	 * Appends the value of the parameter array into this arrayList
	 * 
	 * @param another Custom
	 */
	public boolean addAll(CustomArrayList<E> collection) {
		if (arr == null) {
			arr = collection.getPrimitiveArray();
		} else {
			Object tempArray[] = arr;
			Object tempCollectionArray[] = collection.getPrimitiveArray();
			arr = new Object[arr.length + collection.getSize()];

			int counter = 0;
			for (Object currentTempObj : tempArray) {
				arr[counter] = currentTempObj;
				counter++;
			}
			for (Object currentCollectionObject : tempCollectionArray) {
				arr[counter] = currentCollectionObject;
				counter++;
			}
		}
		return true;
	}

	/**
	 * @param entry is removed from the list
	 * @return if the removal is successful, true is returned
	 */
	@SuppressWarnings("unchecked")
	public boolean remove(E entry) {
		boolean removalDone = false;
		if (arr == null) {
			return removalDone;
		} else {

			CustomArrayList<E> tempList = new CustomArrayList<E>();
			for (Object currentObject : arr) {
				if (!(currentObject == entry)) {
					tempList.add((E) currentObject);
				} else {
					// at least one element should be removed so that 'true' is returned to this
					// function.
					removalDone = true;
				}
			}
			arr = tempList.getPrimitiveArray();

		}
		return removalDone;

	}

	/**
	 * @param each entry of the parameter list is removed from the current list
	 * @return if the removal is successful, true is returned
	 */
	@SuppressWarnings("unchecked")
	public boolean removeAll(CustomArrayList<E> list) {
		boolean removalDone = false;
		if (arr == null) {
			return removalDone;
		} else {
			CustomArrayList<E> tempList = new CustomArrayList<E>();
			for (Object currentObject : arr) {
				if (!(list.contains(currentObject))) {
					tempList.add((E) currentObject);
				} else {
					removalDone = true;
				}
			}
			arr = tempList.getPrimitiveArray();
		}
		return removalDone;
	}

	/**
	 * @param the elements in the list will be maintained in the original list
	 */
	public void retainAll(CustomArrayList<E> list) {

	}

	public boolean contains(Object entry) {
		for (Object currentObject : arr) {
			if (entry == currentObject) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return The size of the list
	 */
	public int getSize() {
		if (arr == null) {
			return 0;
		}
		return arr.length;
	}

	@SuppressWarnings("unchecked")
	public E[] getPrimitiveArray() {
		return (E[]) arr;
	}

	/**
	 * @return the values of the array list in a readable format
	 */
	public String toString() {
		if (arr == null) {
			return "[]";
		} else {
			String toString = "";
			for (Object currentObject : arr) {
				toString += "[";
				toString += currentObject;
				toString += "]\n";
			}
			return toString;
		}
	}

	/**
	 * The iterable interface is implemented so that this arraylist can be used with
	 * foreach. The implementation of the iterator for the iterable is done in
	 * iterator class
	 * 
	 */
	public Iterator<E> iterator() {
		return new iteratorClass();
	}

	class iteratorClass implements Iterator<E> {
		private int currentIteratorPosition = 0;

		@Override
		public boolean hasNext() {
			if (currentIteratorPosition < arr.length) {
				return true;
			} else {
				return false;
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			if (!hasNext()) {
				try {
					throw new Exception();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return (E) arr[currentIteratorPosition++];
		}
	}
}
