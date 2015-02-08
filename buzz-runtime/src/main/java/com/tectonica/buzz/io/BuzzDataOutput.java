package com.tectonica.buzz.io;

import java.io.DataOutput;
import java.io.IOException;

import com.tectonica.buzz.stream.BuzzOutputStream;

public class BuzzDataOutput implements DataOutput
{
	protected BuzzOutputStream output;

	public BuzzDataOutput(BuzzOutputStream output)
	{
		this.output = output;
	}

	@Override
	public void write(int b) throws IOException
	{
		output.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException
	{
		output.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException
	{
		output.write(b, off, len);
	}

	@Override
	public void writeBoolean(boolean v) throws IOException
	{
		output.writeBoolean(v);
	}

	@Override
	public void writeByte(int v) throws IOException
	{
		output.writeByte(v);
	}

	@Override
	public void writeShort(int v) throws IOException
	{
		output.writeShort(v);
	}

	@Override
	public void writeChar(int v) throws IOException
	{
		output.writeChar((char) v);
	}

	@Override
	public void writeInt(int v) throws IOException
	{
		output.writeInt(v);
	}

	@Override
	public void writeLong(long v) throws IOException
	{
		output.writeLong(v);
	}

	@Override
	public void writeFloat(float v) throws IOException
	{
		output.writeFloat(v);
	}

	@Override
	public void writeDouble(double v) throws IOException
	{
		output.writeDouble(v);
	}

	@Override
	public void writeBytes(String s) throws IOException
	{
		int len = s.length();
		for (int i = 0; i < len; i++)
		{
			output.write((byte) s.charAt(i));
		}
	}

	@Override
	public void writeChars(String s) throws IOException
	{
		int len = s.length();
		for (int i = 0; i < len; i++)
		{
			int v = s.charAt(i);
			output.write((v >>> 8) & 0xFF);
			output.write((v >>> 0) & 0xFF);
		}
	}

	@Override
	public void writeUTF(String s) throws IOException
	{
		output.writeString(s);
	}
}
