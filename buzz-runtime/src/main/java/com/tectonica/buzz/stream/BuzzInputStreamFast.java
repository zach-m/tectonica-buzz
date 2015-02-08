package com.tectonica.buzz.stream;

import java.io.InputStream;

import com.tectonica.buzz.BuzzException;

/**
 * Same as Input, but does not use variable length encoding for integer types.
 * 
 * @author Roman Levenstein <romxilev@gmail.com>
 */
public final class BuzzInputStreamFast extends BuzzInputStream
{

	/** Creates an uninitialized Output. {@link #setBuffer(byte[], int, int)} must be called before the Output is used. */
	public BuzzInputStreamFast()
	{}

	/**
	 * Creates a new Output for writing to a byte array.
	 * 
	 * @param bufferSize
	 *            The initial and maximum size of the buffer. An exception is thrown if this size is exceeded.
	 */
	public BuzzInputStreamFast(int bufferSize)
	{
		super(bufferSize);
	}

	/**
	 * Creates a new Output for writing to a byte array.
	 * 
	 * @see #setBuffer(byte[])
	 */
	public BuzzInputStreamFast(byte[] buffer)
	{
		super(buffer);
	}

	/**
	 * Creates a new Output for writing to a byte array.
	 * 
	 * @see #setBuffer(byte[], int, int)
	 */
	public BuzzInputStreamFast(byte[] buffer, int offset, int count)
	{
		super(buffer, offset, count);
	}

	/** Creates a new Output for writing to an OutputStream. A buffer size of 4096 is used. */
	public BuzzInputStreamFast(InputStream outputStream)
	{
		super(outputStream);
	}

	/** Creates a new Output for writing to an OutputStream. */
	public BuzzInputStreamFast(InputStream outputStream, int bufferSize)
	{
		super(outputStream, bufferSize);
	}

	public int readInt(boolean optimizePositive) throws BuzzException
	{
		return readInt();
	}

	public long readLong(boolean optimizePositive) throws BuzzException
	{
		return readLong();
	}
}
