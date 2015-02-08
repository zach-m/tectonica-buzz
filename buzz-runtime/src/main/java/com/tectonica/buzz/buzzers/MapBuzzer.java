package com.tectonica.buzz.buzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.tectonica.buzz.BUZZ;
import com.tectonica.buzz.BUZZ.Buzzer;

public class MapBuzzer
{
	public static <K, V> void writeMap(BUZZ buzz, DataOutput out, Map<K, V> map) throws IOException
	{
		final int length = (map == null) ? -1 : map.size();
		out.writeInt(length);
		if (length > 0)
		{
			Buzzer<K> keyBuzzer = null;
			Buzzer<V> valueBuzzer = null;
			for (Entry<K, V> entry : map.entrySet())
			{
				final K key = entry.getKey();
				final V value = entry.getValue();
				if (keyBuzzer == null)
					keyBuzzer = buzz.buzzerFor(key);
				if (valueBuzzer == null)
					valueBuzzer = buzz.buzzerFor(value);
				buzz.writeObject(out, key, keyBuzzer);
				buzz.writeObject(out, value, valueBuzzer);
			}
		}
	}

	public static <K, V, M extends Map<K, V>> M readMap(BUZZ buzz, DataInput in, Class<K> keyClz, Class<V> valueClz, Class<M> mapClz)
			throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		M map;
		try
		{
			map = mapClz.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		final Buzzer<K> keyBuzzer = buzz.buzzerOf(keyClz);
		final Buzzer<V> valueBuzzer = buzz.buzzerOf(valueClz);
		for (int i = 0; i < length; i++)
		{
			K key = buzz.readObject(in, keyClz, keyBuzzer);
			V value = buzz.readObject(in, valueClz, valueBuzzer);
			map.put(key, value);
		}
		return map;
	}
}
