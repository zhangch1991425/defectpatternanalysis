package org.incava.ijdk.util;

import java.util.List;

public class ListExt extends CollectionExt {
	/**
	 * Returns the last element in the list.
	 */
	public static <T> T last(List<T> list) {
		return list == null ? null : get(list, -1);
	}

	/**
	 * Returns the nth element in the list, where n can be negative, to index
	 * from the end of the list. Returns null if out of bounds.
	 */
	public static <T> T get(List<T> list, int index) {
		if (list == null) {
			return null;
		}
		Integer idx = getIndex(list.size(), index);
		return idx == null ? null : list.get(idx);
	}

	/**
	 * Converts the index, which can be positive or negative, to one within the
	 * given size, representing the length of a list. A negative index will
	 * result in the distance from the end of the list, with index -1 meaning
	 * the last element in the list. Returns null if the resulting index is out
	 * of range.
	 */
	public static Integer getIndex(Integer size, Integer index) {
		int idx = index < 0 ? size + index : index;
		return idx < 0 || idx >= size ? null : idx;
	}

	/**
	 * Removes all occurrances of <code>element</code> from <code>list</code>,
	 * returning whether any were found. Returns null if <code>list</code> is
	 * false.
	 */
	public static <T> boolean removeAll(List<T> list, T element) {
		if (list == null) {
			return false;
		}
		boolean found = list.remove(element);
		boolean origFound = found;
		while (found) {
			found = list.remove(element);
		}
		return origFound;
	}
}
