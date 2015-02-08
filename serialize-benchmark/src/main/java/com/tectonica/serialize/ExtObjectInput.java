package com.tectonica.serialize;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;

import com.tectonica.buzz.io.BuzzDataInput;
import com.tectonica.buzz.stream.BuzzInputStream;

public class ExtObjectInput<T extends Externalizable> extends BuzzDataInput implements ObjectInput
{
	protected final Class<T> clz;

	public ExtObjectInput(BuzzInputStream input, Class<T> clz)
	{
		super(input);
		this.clz = clz;
	}

	@Override
	public Object readObject() throws ClassNotFoundException, IOException
	{
		return EXTERN.readExternalizable(this, clz);

//		final String className = EXTERN.readString(this);
//		final Class<?> clz = Class.forName(className);
//		if (Externalizable.class.isAssignableFrom(clz))
//			return EXTERN.readExternalizable(this, (Class<? extends Externalizable>) clz);
//		return EXTERN.readSerializable(this, (Class<? extends Serializable>) clz);
	}

	@Override
	public int read() throws IOException
	{
		return input.read();
	}

	@Override
	public int read(byte[] b) throws IOException
	{
		return input.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException
	{
		return input.read(b, off, len);
	}

	@Override
	public long skip(long n) throws IOException
	{
		return input.skip(n);
	}

	@Override
	public int available() throws IOException
	{
		return 0;
	}

	@Override
	public void close() throws IOException
	{
		input.close();
	}
}
