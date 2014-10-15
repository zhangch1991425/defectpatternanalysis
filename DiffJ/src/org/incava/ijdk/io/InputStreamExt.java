package org.incava.ijdk.io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumSet;
import java.util.List;

public class InputStreamExt {
	/**
	 * Reads the file into a string array, without end-of-line characters
	 * (sequences). Returns empty array on error.
	 */
	public static List<String> readLines(InputStream stream)
			throws IORuntimeException {
		return ReaderExt.readLines(new InputStreamReader(stream), null);
	}

	/**
	 * Reads the file into a string array, without end-of-line characters
	 * (sequences). Returns empty array on error.
	 */
	public static List<String> readLines(InputStream stream,
			EnumSet<ReadOptionType> options) throws IORuntimeException {
		return ReaderExt.readLines(new InputStreamReader(stream), options);
	}
}
