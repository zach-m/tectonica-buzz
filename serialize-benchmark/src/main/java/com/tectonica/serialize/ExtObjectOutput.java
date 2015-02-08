package com.tectonica.serialize;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;

import com.tectonica.buzz.io.BuzzDataOutput;
import com.tectonica.buzz.stream.BuzzOutputStream;

public class ExtObjectOutput extends BuzzDataOutput implements ObjectOutput
{
	public ExtObjectOutput(BuzzOutputStream output)
	{
		super(output);
	}

	@Override
	public void writeObject(Object obj) throws IOException
	{
		EXTERN.writeExternalizable(this, (Externalizable) obj);

//		final Class<? extends Object> clz = obj.getClass();
//		if (Externalizable.class.isAssignableFrom(clz))
//		{
//			EXTERN.writeString(this, clz.getName());
//			EXTERN.writeExternalizable(this, (Externalizable) obj);
//		}
//		else if (Serializable.class.isAssignableFrom(clz))
//		{
//			EXTERN.writeString(this, clz.getName());
//			EXTERN.writeSerializable(this, (Serializable) obj);
//		}
//		else
//			throw new IOException("Object must be Externalizable or Serializable");
	}

	@Override
	public void flush() throws IOException
	{
		output.flush();
	}

	@Override
	public void close() throws IOException
	{
		output.close();
	}
}
