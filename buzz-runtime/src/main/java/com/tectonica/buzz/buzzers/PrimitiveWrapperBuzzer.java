package com.tectonica.buzz.buzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PrimitiveWrapperBuzzer
{
	private static final byte NULL_OBJECT = 0;
	private static final byte NON_NULL_OBJECT = 1;

	public static void writeByte(DataOutput out, Byte num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeByte(num.byteValue());
		}
	}

	public static Byte readByte(DataInput in) throws IOException
	{
		return in.readByte() == NULL_OBJECT ? null : Byte.valueOf(in.readByte());
	}

	public static void writeShort(DataOutput out, Short num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeShort(num.shortValue());
		}
	}

	public static Short readShort(DataInput in) throws IOException
	{
		return in.readByte() == NULL_OBJECT ? null : Short.valueOf(in.readShort());
	}

	public static void writeInteger(DataOutput out, Integer num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeInt(num.intValue());
		}
	}

	public static Integer readInteger(DataInput in) throws IOException
	{
		return in.readByte() == NULL_OBJECT ? null : Integer.valueOf(in.readInt());
	}

	public static void writeLong(DataOutput out, Long num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeLong(num.longValue());
		}
	}

	public static Long readLong(DataInput in) throws IOException
	{
		return in.readByte() == NULL_OBJECT ? null : Long.valueOf(in.readLong());
	}

	public static void writeFloat(DataOutput out, Float num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeFloat(num.floatValue());
		}
	}

	public static Float readFloat(DataInput in) throws IOException
	{
		return in.readByte() == NULL_OBJECT ? null : Float.valueOf(in.readFloat());
	}

	public static void writeDouble(DataOutput out, Double num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeDouble(num.doubleValue());
		}
	}

	public static Double readDouble(DataInput in) throws IOException
	{
		return in.readByte() == NULL_OBJECT ? null : Double.valueOf(in.readDouble());
	}

	public static void writeCharacter(DataOutput out, Character num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeChar(num.charValue());
		}
	}

	public static Character readCharacter(DataInput in) throws IOException
	{
		return in.readByte() == NULL_OBJECT ? null : Character.valueOf(in.readChar());
	}

	public static void writeBoolean(DataOutput out, Boolean num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeBoolean(num.booleanValue());
		}
	}

	public static Boolean readBoolean(DataInput in) throws IOException
	{
		return in.readByte() == NULL_OBJECT ? null : Boolean.valueOf(in.readBoolean());
	}
}
