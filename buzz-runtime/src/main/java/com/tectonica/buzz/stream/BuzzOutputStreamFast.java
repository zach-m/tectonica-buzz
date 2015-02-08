package com.tectonica.buzz.stream;

import java.io.OutputStream;

import com.tectonica.buzz.BuzzException;

/**
 * Same as Output, but does not use variable length encoding for integer types.
 * 
 * @author Roman Levenstein <romxilev@gmail.com>
 */
public final class BuzzOutputStreamFast extends BuzzOutputStream
{

	/** Creates an uninitialized Output. {@link #setBuffer(byte[], int)} must be called before the Output is used. */
	public BuzzOutputStreamFast()
	{}

	/**
	 * Creates a new Output for writing to a byte array.
	 * 
	 * @param bufferSize
	 *            The initial and maximum size of the buffer. An exception is thrown if this size is exceeded.
	 */
	public BuzzOutputStreamFast(int bufferSize)
	{
		this(bufferSize, bufferSize);
	}

	/**
	 * Creates a new Output for writing to a byte array.
	 * 
	 * @param bufferSize
	 *            The initial size of the buffer.
	 * @param maxBufferSize
	 *            The buffer is doubled as needed until it exceeds maxBufferSize and an exception is thrown.
	 */
	public BuzzOutputStreamFast(int bufferSize, int maxBufferSize)
	{
		super(bufferSize, maxBufferSize);
	}

	/**
	 * Creates a new Output for writing to a byte array.
	 * 
	 * @see #setBuffer(byte[])
	 */
	public BuzzOutputStreamFast(byte[] buffer)
	{
		this(buffer, buffer.length);
	}

	/**
	 * Creates a new Output for writing to a byte array.
	 * 
	 * @see #setBuffer(byte[], int)
	 */
	public BuzzOutputStreamFast(byte[] buffer, int maxBufferSize)
	{
		super(buffer, maxBufferSize);
	}

	/** Creates a new Output for writing to an OutputStream. A buffer size of 4096 is used. */
	public BuzzOutputStreamFast(OutputStream outputStream)
	{
		super(outputStream);
	}

	/** Creates a new Output for writing to an OutputStream. */
	public BuzzOutputStreamFast(OutputStream outputStream, int bufferSize)
	{
		super(outputStream, bufferSize);
	}

	public int writeInt(int value, boolean optimizePositive) throws BuzzException
	{
		writeInt(value);
		return 4;
	}

	public int writeLong(long value, boolean optimizePositive) throws BuzzException
	{
		writeLong(value);
		return 8;
	}
}
