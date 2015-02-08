package com.tectonica.buzz.buzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;

import com.tectonica.buzz.BUZZ;
import com.tectonica.buzz.BUZZ.Buzzer;

public class ObjectArrayBuzzer
{
	public static <T> void writeArray(BUZZ buzz, DataOutput out, T[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		Buzzer<T> buzzer = null;
		for (int i = 0; i < length; i++)
		{
			final T item = array[i];
			if (buzzer == null)
				buzzer = buzz.buzzerFor(item);
			buzz.writeObject(out, item, buzzer);
		}
	}

	public static <T> T[] readArray(BUZZ buzz, DataInput in, Class<T> clz) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		@SuppressWarnings("unchecked")
		final T[] array = (T[]) Array.newInstance(clz, length);
		final Buzzer<T> buzzer = buzz.buzzerOf(clz);
		for (int i = 0; i < length; i++)
			array[i] = buzz.readObject(in, clz, buzzer);
		return array;
	}

	public static void writeByteArray(DataOutput out, Byte[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			PrimitiveWrapperBuzzer.writeByte(out, array[i]);
	}

	public static Byte[] readByteArray(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Byte[] array = new Byte[length];
		for (int i = 0; i < length; i++)
			array[i] = PrimitiveWrapperBuzzer.readByte(in);
		return array;
	}

	public static void writeShortArray(DataOutput out, Short[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			PrimitiveWrapperBuzzer.writeShort(out, array[i]);
	}

	public static Short[] readShortArray(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Short[] array = new Short[length];
		for (int i = 0; i < length; i++)
			array[i] = PrimitiveWrapperBuzzer.readShort(in);
		return array;
	}

	public static void writeIntegerArray(DataOutput out, Integer[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			PrimitiveWrapperBuzzer.writeInteger(out, array[i]);
	}

	public static Integer[] readIntegerArray(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Integer[] array = new Integer[length];
		for (int i = 0; i < length; i++)
			array[i] = PrimitiveWrapperBuzzer.readInteger(in);
		return array;
	}

	public static void writeLongArray(DataOutput out, Long[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			PrimitiveWrapperBuzzer.writeLong(out, array[i]);
	}

	public static Long[] readLongArray(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Long[] array = new Long[length];
		for (int i = 0; i < length; i++)
			array[i] = PrimitiveWrapperBuzzer.readLong(in);
		return array;
	}

	public static void writeFloatArray(DataOutput out, Float[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			PrimitiveWrapperBuzzer.writeFloat(out, array[i]);
	}

	public static Float[] readFloatArray(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Float[] array = new Float[length];
		for (int i = 0; i < length; i++)
			array[i] = PrimitiveWrapperBuzzer.readFloat(in);
		return array;
	}

	public static void writeDoubleArray(DataOutput out, Double[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			PrimitiveWrapperBuzzer.writeDouble(out, array[i]);
	}

	public static Double[] readDoubleArray(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Double[] array = new Double[length];
		for (int i = 0; i < length; i++)
			array[i] = PrimitiveWrapperBuzzer.readDouble(in);
		return array;
	}

	public static void writeCharacterArray(DataOutput out, Character[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			PrimitiveWrapperBuzzer.writeCharacter(out, array[i]);
	}

	public static Character[] readCharacterArray(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Character[] array = new Character[length];
		for (int i = 0; i < length; i++)
			array[i] = PrimitiveWrapperBuzzer.readCharacter(in);
		return array;
	}

	public static void writeBooleanArray(DataOutput out, Boolean[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			PrimitiveWrapperBuzzer.writeBoolean(out, array[i]);
	}

	public static Boolean[] readBooleanArray(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Boolean[] array = new Boolean[length];
		for (int i = 0; i < length; i++)
			array[i] = PrimitiveWrapperBuzzer.readBoolean(in);
		return array;
	}
}
