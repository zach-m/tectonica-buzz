package com.tectonica.buzz.buzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;

import com.tectonica.buzz.BUZZ;
import com.tectonica.buzz.BUZZ.Buzzer;

public class CollectionBuzzer
{
	public static <T> void writeCollection(BUZZ buzz, DataOutput out, Collection<T> collection) throws IOException
	{
		final int length = (collection == null) ? -1 : collection.size();
		out.writeInt(length);
		if (length > 0)
		{
			Buzzer<T> buzzer = null;
			for (T item : collection)
			{
				if (buzzer == null)
					buzzer = buzz.buzzerFor(item);
				buzz.writeObject(out, item, buzzer);
			}
		}
	}

	public static <T, C extends Collection<T>> C readCollection(BUZZ buzz, DataInput in, Class<T> clz, Class<C> collClz) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		C collection;
		try
		{
			collection = collClz.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		final Buzzer<T> buzzer = buzz.buzzerOf(clz);
		for (int i = 0; i < length; i++)
			collection.add(buzz.readObject(in, clz, buzzer));
		return collection;
	}
}
