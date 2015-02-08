package com.tectonica.buzz.buzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PrimitiveArrayBuzzer
{
	public static void writeByteArrayPrimitive(DataOutput out, byte[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeByte(array[i]);
	}

	public static byte[] readByteArrayPrimitive(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final byte[] array = new byte[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readByte();
		return array;
	}

	public static void writeShortArrayPrimitive(DataOutput out, short[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeShort(array[i]);
	}

	public static short[] readShortArrayPrimitive(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final short[] array = new short[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readShort();
		return array;
	}

	public static void writeIntegerArrayPrimitive(DataOutput out, int[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeInt(array[i]);
	}

	public static int[] readIntegerArrayPrimitive(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final int[] array = new int[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readInt();
		return array;
	}

	public static void writeLongArrayPrimitive(DataOutput out, long[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeLong(array[i]);
	}

	public static long[] readLongArrayPrimitive(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final long[] array = new long[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readLong();
		return array;
	}

	public static void writeFloatArrayPrimitive(DataOutput out, float[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeFloat(array[i]);
	}

	public static float[] readFloatArrayPrimitive(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final float[] array = new float[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readFloat();
		return array;
	}

	public static void writeDoubleArrayPrimitive(DataOutput out, double[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeDouble(array[i]);
	}

	public static double[] readDoubleArrayPrimitive(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final double[] array = new double[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readDouble();
		return array;
	}

	public static void writeCharacterArrayPrimitive(DataOutput out, char[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeChar(array[i]);
	}

	public static char[] readCharacterArrayPrimitive(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final char[] array = new char[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readChar();
		return array;
	}

	public static void writeBooleanArrayPrimitive(DataOutput out, boolean[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeBoolean(array[i]);
	}

	public static boolean[] readBooleanArrayPrimitive(DataInput in) throws IOException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final boolean[] array = new boolean[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readBoolean();
		return array;
	}
}
