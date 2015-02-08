package com.tectonica.buzz.io;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;

import com.tectonica.buzz.BuzzException;
import com.tectonica.buzz.stream.BuzzInputStream;

public class BuzzDataInput implements DataInput
{
	protected final BuzzInputStream input;

	public BuzzDataInput(BuzzInputStream input)
	{
		this.input = input;
	}

	@Override
	public void readFully(byte[] b) throws IOException
	{
		readFully(b, 0, b.length);
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException
	{
		try
		{
			input.readBytes(b, off, len);
		}
		catch (BuzzException e)
		{
			throw new EOFException(e.getMessage());
		}
	}

	@Override
	public int skipBytes(int n) throws IOException
	{
		return (int) input.skip((long) n);
	}

	@Override
	public boolean readBoolean() throws IOException
	{
		return input.readBoolean();
	}

	@Override
	public byte readByte() throws IOException
	{
		return input.readByte();
	}

	@Override
	public int readUnsignedByte() throws IOException
	{
		return input.readByteUnsigned();
	}

	@Override
	public short readShort() throws IOException
	{
		return input.readShort();
	}

	@Override
	public int readUnsignedShort() throws IOException
	{
		return input.readShortUnsigned();
	}

	@Override
	public char readChar() throws IOException
	{
		return input.readChar();
	}

	@Override
	public int readInt() throws IOException
	{
		return input.readInt();
	}

	@Override
	public long readLong() throws IOException
	{
		return input.readLong();
	}

	@Override
	public float readFloat() throws IOException
	{
		return input.readFloat();
	}

	@Override
	public double readDouble() throws IOException
	{
		return input.readDouble();
	}

	/**
	 * This is not currently implemented. The method will currently throw an {@link java.lang.UnsupportedOperationException} whenever it is
	 * called.
	 * 
	 * @throws UnsupportedOperationException
	 *             when called.
	 * @deprecated this method is not supported in this implementation.
	 */
	@Override
	public String readLine() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Reads the length and string of UTF8 characters, or null. This can read strings written by {@link #writeUTF(String)},
	 * {@link com.esotericsoftware.kryo.io.Output#writeString(String)},
	 * {@link com.esotericsoftware.kryo.io.Output#writeString(CharSequence)}, and
	 * {@link com.esotericsoftware.kryo.io.Output#writeAscii(String)}.
	 * 
	 * @return May be null.
	 */
	@Override
	public String readUTF() throws IOException
	{
		return input.readString();
	}
}
