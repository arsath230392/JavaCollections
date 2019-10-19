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
	@SuppressWarnings("unchecked")
	public void retainAll(CustomArrayList<E> list) {
		if (arr == null) {
			return;
		} else {
			CustomArrayList<E> tempList = new CustomArrayList<E>();
			for (Object currentObject : arr) {
				if (list.contains(currentObject)) {
					tempList.add((E) currentObject);
				}
			}
			arr = tempList.getPrimitiveArray();
		}

	}

	/**
	 * Sorts the elements in ascending order. This sort is a generic sort that works
	 * for strings and numerical data types
	 */
	@SuppressWarnings("unchecked")
	public void sort() {
		if (arr == null | arr.length == 1) {
			return;
		} else {
			// If the array is of the type Numeric, we sort the array using numeric Sort
			if (((E) arr[0]).getClass().getSimpleName().equals("Byte")
					| ((E) arr[0]).getClass().getSimpleName().equals("Integer")
					| ((E) arr[0]).getClass().getSimpleName().equals("Double")
					| ((E) arr[0]).getClass().getSimpleName().equals("Short")
					| ((E) arr[0]).getClass().getSimpleName().equals("Float")
					| ((E) arr[0]).getClass().getSimpleName().equals("Long")) {
				// array is sorted using Quick sort
				QuickSort(0, arr.length - 1);

			} else {
				// array is sorted using CompareTo() method
				Object temp = "";
				for (int i = 0; i < arr.length - 1; i++) {
					for (int j = i + 1; j < arr.length; j++) {
						if ((arr[i].toString().toLowerCase()).compareTo(arr[j].toString().toLowerCase()) > 0) {
							temp = arr[i];
							arr[i] = arr[j];
							arr[j] = temp;
						}
					}
				}
			}
		}
	}

	/**
	 * Sorts the elements in descending order. This sort is a generic sort that
	 * works for strings and numerical data types
	 */
	public void reverseSort() {
		sort();
		Object[] temp = arr;
		int currentIteration = arr.length;
		for (Object currentObject : temp) {
			currentIteration--;
			arr[currentIteration] = currentObject;
		}

	}

	private void QuickSort(int low, int high) {
		if (low < high) {

			int pi = partition(low, high);

			// Recursively sort elements before
			// partition and after partition
			QuickSort(low, pi - 1);
			QuickSort(pi + 1, high);
		}
	}

	private int partition(int low, int high) {
		Double pivot = Double.valueOf(arr[high].toString());
		int i = (low - 1); // index of smaller element
		for (int j = low; j < high; j++) {
			// If current element is smaller than the pivot
			if (Double.valueOf(arr[j].toString()) < pivot) {
				i++;

				// swap arr[i] and arr[j]
				Object temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		// swap arr[i+1] and arr[high] (or pivot)
		Object temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;

		return i + 1;

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
