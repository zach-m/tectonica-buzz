package com.tectonica.serialize;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class EXTERN
{
	private static final byte NULL_OBJECT = 0;
	private static final byte NON_NULL_OBJECT = 1;
	private static final long NULL_DATE = Long.MIN_VALUE;
	private static final byte SERIALIZABLE_OBJECT = 0;
	private static final byte EXTERNALIZABLE_OBJECT = 1;

	public static <T extends Externalizable> void writeExternalizable(ObjectOutput out, T object) throws IOException
	{
		if (object == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			object.writeExternal(out);
		}
	}

	public static <T extends Externalizable> T readExternalizable(ObjectInput in, Class<T> clz) throws IOException, ClassNotFoundException
	{
		if (in.readByte() == NULL_OBJECT)
			return null;
		T object;
		try
		{
			object = clz.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		object.readExternal(in);
		return object;
	}

	public static <T extends Serializable> void writeSerializable(ObjectOutput out, T object) throws IOException
	{
		if (object == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			if (object instanceof Externalizable)
			{
				out.writeByte(EXTERNALIZABLE_OBJECT);
				((Externalizable) object).writeExternal(out);
			}
			else
			{
				out.writeByte(SERIALIZABLE_OBJECT);
				out.writeObject(object);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T readSerializable(ObjectInput in, Class<T> clz) throws IOException, ClassNotFoundException
	{
		if (in.readByte() == NULL_OBJECT)
			return null;
		boolean isExternaqlizable = in.readByte() == EXTERNALIZABLE_OBJECT;
		if (isExternaqlizable)
		{
			Externalizable object;
			try
			{
				object = (Externalizable) clz.newInstance();
			}
			catch (InstantiationException | IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
			object.readExternal(in);
			return (T) object;
		}

		return (T) in.readObject();
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void writeString(ObjectOutput out, String s) throws IOException
	{
		final int length = (s == null) ? -1 : s.length();
		out.writeInt(length);
		if (length > 0)
			out.writeChars(s);
	}

	public static String readString(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		if (length == 0)
			return "";
		char[] chars = new char[length];
		for (int i = 0; i < length; i++)
			chars[i] = in.readChar();
		return new String(chars);
	}

	public static void writeDate(ObjectOutput out, Date d) throws IOException
	{
		if (d == null)
			out.writeLong(NULL_DATE);
		else
			out.writeLong(d.getTime());
	}

	public static Date readDate(ObjectInput in) throws IOException, ClassNotFoundException
	{
		long time = in.readLong();
		return (time == NULL_DATE) ? null : new Date(time);
	}

	public static void writeEnum(ObjectOutput out, Enum<?> e) throws IOException
	{
		if (e == null)
			out.writeInt(-1);
		else
			out.writeInt(e.ordinal());
	}

	public static <E extends Enum<E>> E readEnum(ObjectInput in, E[] values) throws IOException, ClassNotFoundException
	{
		int ordinal = in.readInt();
		return ordinal < 0L ? null : values[ordinal];
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static <T extends Externalizable> void writeExternalizableCollection(ObjectOutput out, Collection<T> collection)
			throws IOException
	{
		final int length = (collection == null) ? -1 : collection.size();
		out.writeInt(length);
		if (length > 0)
			for (T item : collection)
				writeExternalizable(out, item);
	}

	public static <T extends Externalizable, C extends Collection<T>> C readExternalizableCollection(ObjectInput in, Class<T> clz,
			Class<C> collClz) throws IOException, ClassNotFoundException
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
		for (int i = 0; i < length; i++)
			collection.add(readExternalizable(in, clz));
		return collection;
	}

	public static <K extends Externalizable, V extends Externalizable> void writeExternalizableMap(ObjectOutput out, Map<K, V> map)
			throws IOException
	{
		final int length = (map == null) ? -1 : map.size();
		out.writeInt(length);
		if (length > 0)
		{
			for (Entry<K, V> entry : map.entrySet())
			{
				writeExternalizable(out, entry.getKey());
				writeExternalizable(out, entry.getValue());
			}
		}
	}

	public static <K extends Externalizable, V extends Externalizable, M extends Map<K, V>> M readExternalizableMap(ObjectInput in,
			Class<K> keyClz, Class<V> valueClz, Class<M> mapClz) throws IOException, ClassNotFoundException
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
		for (int i = 0; i < length; i++)
		{
			K key = readExternalizable(in, keyClz);
			V value = readExternalizable(in, valueClz);
			map.put(key, value);
		}
		return map;
	}

//	public static <K extends Serializable, V extends Externalizable> void writeSemiExternalizableMap(ObjectOutput out, Map<K, V> map)
//			throws IOException
//	{
//		final int length = (map == null) ? -1 : map.size();
//		out.writeInt(length);
//		if (length > 0)
//		{
//			for (Entry<K, V> entry : map.entrySet())
//			{
//				K key = entry.getKey();
//				if (key == null)
//					out.writeByte(NULL_OBJECT);
//				else
//				{
//					out.writeByte(NON_NULL_OBJECT);
//					out.writeObject(key); // FIXME: since when are we using writeObject ?!?
//				}
//				writeExternalizable(out, entry.getValue());
//			}
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public static <K extends Serializable, V extends Externalizable, M extends Map<K, V>> M readSemiExternalizableMap(ObjectInput in,
//			Class<K> keyClz, Class<V> valueClz, Class<M> mapClz) throws IOException, ClassNotFoundException
//	{
//		final int length = in.readInt();
//		if (length < 0)
//			return null;
//		M map;
//		try
//		{
//			map = mapClz.newInstance();
//		}
//		catch (InstantiationException | IllegalAccessException e)
//		{
//			throw new RuntimeException(e);
//		}
//		for (int i = 0; i < length; i++)
//		{
//			K key = (K) (in.readByte() == NULL_OBJECT ? null : in.readObject());
//			V value = readExternalizable(in, valueClz);
//			map.put(key, value);
//		}
//		return map;
//	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void writeByte(ObjectOutput out, Byte num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeByte(num.byteValue());
		}
	}

	public static Byte readByte(ObjectInput in) throws IOException, ClassNotFoundException
	{
		return in.readByte() == NULL_OBJECT ? null : Byte.valueOf(in.readByte());
	}

	public static void writeShort(ObjectOutput out, Short num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeShort(num.shortValue());
		}
	}

	public static Short readShort(ObjectInput in) throws IOException, ClassNotFoundException
	{
		return in.readByte() == NULL_OBJECT ? null : Short.valueOf(in.readShort());
	}

	public static void writeInteger(ObjectOutput out, Integer num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeInt(num.intValue());
		}
	}

	public static Integer readInteger(ObjectInput in) throws IOException, ClassNotFoundException
	{
		return in.readByte() == NULL_OBJECT ? null : Integer.valueOf(in.readInt());
	}

	public static void writeLong(ObjectOutput out, Long num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeLong(num.longValue());
		}
	}

	public static Long readLong(ObjectInput in) throws IOException, ClassNotFoundException
	{
		return in.readByte() == NULL_OBJECT ? null : Long.valueOf(in.readLong());
	}

	public static void writeFloat(ObjectOutput out, Float num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeFloat(num.floatValue());
		}
	}

	public static Float readFloat(ObjectInput in) throws IOException, ClassNotFoundException
	{
		return in.readByte() == NULL_OBJECT ? null : Float.valueOf(in.readFloat());
	}

	public static void writeDouble(ObjectOutput out, Double num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeDouble(num.doubleValue());
		}
	}

	public static Double readDouble(ObjectInput in) throws IOException, ClassNotFoundException
	{
		return in.readByte() == NULL_OBJECT ? null : Double.valueOf(in.readDouble());
	}

	public static void writeCharacter(ObjectOutput out, Character num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeChar(num.charValue());
		}
	}

	public static Character readCharacter(ObjectInput in) throws IOException, ClassNotFoundException
	{
		return in.readByte() == NULL_OBJECT ? null : Character.valueOf(in.readChar());
	}

	public static void writeBoolean(ObjectOutput out, Boolean num) throws IOException
	{
		if (num == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			out.writeBoolean(num.booleanValue());
		}
	}

	public static Boolean readBoolean(ObjectInput in) throws IOException, ClassNotFoundException
	{
		return in.readByte() == NULL_OBJECT ? null : Boolean.valueOf(in.readBoolean());
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static <T extends Externalizable> void writeExternalizableArray(ObjectOutput out, T[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			writeExternalizable(out, array[i]);
	}

	public static <T extends Externalizable> T[] readExternalizableArray(ObjectInput in, Class<T> clz) throws IOException,
			ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		@SuppressWarnings("unchecked")
		final T[] array = (T[]) Array.newInstance(clz, length);
		for (int i = 0; i < length; i++)
			array[i] = readExternalizable(in, clz);
		return array;
	}

	public static void writeByteArray(ObjectOutput out, Byte[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			writeByte(out, array[i]);
	}

	public static Byte[] readByteArray(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Byte[] array = new Byte[length];
		for (int i = 0; i < length; i++)
			array[i] = readByte(in);
		return array;
	}

	public static void writeShortArray(ObjectOutput out, Short[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			writeShort(out, array[i]);
	}

	public static Short[] readShortArray(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Short[] array = new Short[length];
		for (int i = 0; i < length; i++)
			array[i] = readShort(in);
		return array;
	}

	public static void writeIntegerArray(ObjectOutput out, Integer[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			writeInteger(out, array[i]);
	}

	public static Integer[] readIntegerArray(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Integer[] array = new Integer[length];
		for (int i = 0; i < length; i++)
			array[i] = readInteger(in);
		return array;
	}

	public static void writeLongArray(ObjectOutput out, Long[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			writeLong(out, array[i]);
	}

	public static Long[] readLongArray(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Long[] array = new Long[length];
		for (int i = 0; i < length; i++)
			array[i] = readLong(in);
		return array;
	}

	public static void writeFloatArray(ObjectOutput out, Float[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			writeFloat(out, array[i]);
	}

	public static Float[] readFloatArray(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Float[] array = new Float[length];
		for (int i = 0; i < length; i++)
			array[i] = readFloat(in);
		return array;
	}

	public static void writeDoubleArray(ObjectOutput out, Double[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			writeDouble(out, array[i]);
	}

	public static Double[] readDoubleArray(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Double[] array = new Double[length];
		for (int i = 0; i < length; i++)
			array[i] = readDouble(in);
		return array;
	}

	public static void writeCharacterArray(ObjectOutput out, Character[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			writeCharacter(out, array[i]);
	}

	public static Character[] readCharacterArray(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Character[] array = new Character[length];
		for (int i = 0; i < length; i++)
			array[i] = readCharacter(in);
		return array;
	}

	public static void writeBooleanArray(ObjectOutput out, Boolean[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			writeBoolean(out, array[i]);
	}

	public static Boolean[] readBooleanArray(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final Boolean[] array = new Boolean[length];
		for (int i = 0; i < length; i++)
			array[i] = readBoolean(in);
		return array;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void writeByteArrayPrimitive(ObjectOutput out, byte[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeByte(array[i]);
	}

	public static byte[] readByteArrayPrimitive(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final byte[] array = new byte[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readByte();
		return array;
	}

	public static void writeShortArrayPrimitive(ObjectOutput out, short[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeShort(array[i]);
	}

	public static short[] readShortArrayPrimitive(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final short[] array = new short[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readShort();
		return array;
	}

	public static void writeIntegerArrayPrimitive(ObjectOutput out, int[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeInt(array[i]);
	}

	public static int[] readIntegerArrayPrimitive(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final int[] array = new int[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readInt();
		return array;
	}

	public static void writeLongArrayPrimitive(ObjectOutput out, long[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeLong(array[i]);
	}

	public static long[] readLongArrayPrimitive(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final long[] array = new long[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readLong();
		return array;
	}

	public static void writeFloatArrayPrimitive(ObjectOutput out, float[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeFloat(array[i]);
	}

	public static float[] readFloatArrayPrimitive(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final float[] array = new float[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readFloat();
		return array;
	}

	public static void writeDoubleArrayPrimitive(ObjectOutput out, double[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeDouble(array[i]);
	}

	public static double[] readDoubleArrayPrimitive(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final double[] array = new double[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readDouble();
		return array;
	}

	public static void writeCharacterArrayPrimitive(ObjectOutput out, char[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeChar(array[i]);
	}

	public static char[] readCharacterArrayPrimitive(ObjectInput in) throws IOException, ClassNotFoundException
	{
		final int length = in.readInt();
		if (length < 0)
			return null;
		final char[] array = new char[length];
		for (int i = 0; i < length; i++)
			array[i] = in.readChar();
		return array;
	}

	public static void writeBooleanArrayPrimitive(ObjectOutput out, boolean[] array) throws IOException
	{
		final int length = (array == null) ? -1 : array.length;
		out.writeInt(length);
		for (int i = 0; i < length; i++)
			out.writeBoolean(array[i]);
	}

	public static boolean[] readBooleanArrayPrimitive(ObjectInput in) throws IOException, ClassNotFoundException
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
