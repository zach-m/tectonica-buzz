package com.tectonica.buzz;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tectonica.buzz.buzzers.CollectionBuzzer;
import com.tectonica.buzz.buzzers.DateBuzzer;
import com.tectonica.buzz.buzzers.EnumBuzzer;
import com.tectonica.buzz.buzzers.MapBuzzer;
import com.tectonica.buzz.buzzers.ObjectArrayBuzzer;
import com.tectonica.buzz.buzzers.PrimitiveArrayBuzzer;
import com.tectonica.buzz.buzzers.PrimitiveWrapperBuzzer;
import com.tectonica.buzz.buzzers.StringBuzzer;

public class BUZZ
{
	public static interface Buzzer<T>
	{
		public void getAndWrite(BUZZ buzz, T obj, DataOutput out) throws IOException;

		public void readAndSet(BUZZ buzz, T obj, DataInput in) throws IOException;
	}

	private final List<Buzzer<?>> buzzers = new ArrayList<>();
	private final IdentityObjectIntMap<Class<?>> buzzedClasses = new IdentityObjectIntMap<Class<?>>();

//	private IntMap<Class> nameIdToClass = new IntMap();;
//	private ObjectMap<String, Class> nameToClass = new ObjectMap();;

	@SuppressWarnings("unchecked")
	public <T> Buzzer<T> buzzerFor(T obj)
	{
		return buzzerOf((Class<T>) obj.getClass());
	}

	@SuppressWarnings("unchecked")
	public <T> Buzzer<T> buzzerOf(Class<T> clz)
	{
		final int index = buzzedClasses.get(clz, -1);
		if (index >= 0)
			return (Buzzer<T>) buzzers.get(index);
		throw new RuntimeException("no buzzer found for " + clz.getName());
	}

	public <T> boolean registerBuzzer(Class<T> clz, Buzzer<T> buzzer)
	{
		if (clz == null)
			throw new NullPointerException("null clz was passed");
		if (buzzer == null)
			throw new NullPointerException("null buzzer was passed");

		int index = buzzedClasses.get(clz, -1);
		if (index < 0)
		{
			buzzedClasses.put(clz, buzzers.size());
			buzzers.add(buzzer);
			return true; // i.e. new buzzer was added
		}

		buzzers.set(index, buzzer);
		return false; // i.e. existing buzzer was replaced
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static final byte NULL_OBJECT = 0;
	private static final byte NON_NULL_OBJECT = 1;
	private static final long NULL_DATE = Long.MIN_VALUE;

	@SuppressWarnings("unchecked")
	public <T> void writeObject(DataOutput out, T obj) throws IOException
	{
		getAndWrite(out, obj, (Class<T>) obj.getClass(), null);
	}

	@SuppressWarnings("unchecked")
	public <T> void writeObject(DataOutput out, T obj, Buzzer<T> buzzerHint) throws IOException
	{
		getAndWrite(out, obj, (Class<T>) obj.getClass(), buzzerHint);
	}

	public <T> void getAndWrite(DataOutput out, T obj, Class<T> clz) throws IOException
	{
		getAndWrite(out, obj, clz, null);
	}

	private <T> void getAndWrite(DataOutput out, T obj, Class<T> buzzerClz, Buzzer<T> buzzerHint) throws IOException
	{
		if (obj == null)
			out.writeByte(NULL_OBJECT);
		else
		{
			out.writeByte(NON_NULL_OBJECT);
			final Buzzer<T> buzzer = (buzzerHint != null) ? buzzerHint : buzzerOf(buzzerClz);
			buzzer.getAndWrite(this, obj, out);
		}
	}

	public <T> void readIntoObject(DataInput in, T obj, Class<T> buzzerClz) throws IOException
	{
		if (in.readByte() != NULL_OBJECT)
			buzzerOf(buzzerClz).readAndSet(this, obj, in);
	}

	public <T> T readObject(DataInput in, Class<T> clz) throws IOException
	{
		return readObject(in, clz, null);
	}

	public <T> T readObject(DataInput in, Class<T> clz, Buzzer<T> buzzerHint) throws IOException
	{
		if (in.readByte() == NULL_OBJECT)
			return null;
		T obj;
		try
		{
			obj = clz.newInstance(); // TODO: Objenesis?
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		final Buzzer<T> buzzer = (buzzerHint != null) ? buzzerHint : buzzerOf(clz);
		buzzer.readAndSet(this, obj, in);
		return obj;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void writeString(DataOutput out, String s) throws IOException
	{
		StringBuzzer.writeString(out, s);
	}

	public String readString(DataInput in) throws IOException
	{
		return StringBuzzer.readString(in);
	}

	public void writeDate(DataOutput out, Date d) throws IOException
	{
		DateBuzzer.writeDate(out, d);
	}

	public Date readDate(DataInput in) throws IOException
	{
		return DateBuzzer.readDate(in);
	}

	public void writeEnum(DataOutput out, Enum<?> e) throws IOException
	{
		EnumBuzzer.writeEnum(out, e);
	}

	public <E extends Enum<E>> E readEnum(DataInput in, E[] values) throws IOException
	{
		return EnumBuzzer.readEnum(in, values);
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public <T> void writeCollection(DataOutput out, Collection<T> collection) throws IOException
	{
		CollectionBuzzer.writeCollection(this, out, collection);
	}

	public <T, C extends Collection<T>> C readCollection(DataInput in, Class<T> clz, Class<C> collClz) throws IOException
	{
		return CollectionBuzzer.readCollection(this, in, clz, collClz);
	}

	public <K, V> void writeMap(DataOutput out, Map<K, V> map) throws IOException
	{
		MapBuzzer.writeMap(this, out, map);
	}

	public <K, V, M extends Map<K, V>> M readMap(DataInput in, Class<K> keyClz, Class<V> valueClz, Class<M> mapClz) throws IOException
	{
		return MapBuzzer.readMap(this, in, keyClz, valueClz, mapClz);
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void writeByte(DataOutput out, Byte num) throws IOException
	{
		PrimitiveWrapperBuzzer.writeByte(out, num);
	}

	public Byte readByte(DataInput in) throws IOException
	{
		return PrimitiveWrapperBuzzer.readByte(in);
	}

	public void writeShort(DataOutput out, Short num) throws IOException
	{
		PrimitiveWrapperBuzzer.writeShort(out, num);
	}

	public Short readShort(DataInput in) throws IOException
	{
		return PrimitiveWrapperBuzzer.readShort(in);
	}

	public void writeInteger(DataOutput out, Integer num) throws IOException
	{
		PrimitiveWrapperBuzzer.writeInteger(out, num);
	}

	public Integer readInteger(DataInput in) throws IOException
	{
		return PrimitiveWrapperBuzzer.readInteger(in);
	}

	public void writeLong(DataOutput out, Long num) throws IOException
	{
		PrimitiveWrapperBuzzer.writeLong(out, num);
	}

	public Long readLong(DataInput in) throws IOException
	{
		return PrimitiveWrapperBuzzer.readLong(in);
	}

	public void writeFloat(DataOutput out, Float num) throws IOException
	{
		PrimitiveWrapperBuzzer.writeFloat(out, num);
	}

	public Float readFloat(DataInput in) throws IOException
	{
		return PrimitiveWrapperBuzzer.readFloat(in);
	}

	public void writeDouble(DataOutput out, Double num) throws IOException
	{
		PrimitiveWrapperBuzzer.writeDouble(out, num);
	}

	public Double readDouble(DataInput in) throws IOException
	{
		return PrimitiveWrapperBuzzer.readDouble(in);
	}

	public void writeCharacter(DataOutput out, Character num) throws IOException
	{
		PrimitiveWrapperBuzzer.writeCharacter(out, num);
	}

	public Character readCharacter(DataInput in) throws IOException
	{
		return PrimitiveWrapperBuzzer.readCharacter(in);
	}

	public void writeBoolean(DataOutput out, Boolean num) throws IOException
	{
		PrimitiveWrapperBuzzer.writeBoolean(out, num);
	}

	public Boolean readBoolean(DataInput in) throws IOException
	{
		return PrimitiveWrapperBuzzer.readBoolean(in);
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public <T> void writeArray(DataOutput out, T[] array) throws IOException
	{
		ObjectArrayBuzzer.writeArray(this, out, array);
	}

	public <T> T[] readArray(DataInput in, Class<T> clz) throws IOException
	{
		return ObjectArrayBuzzer.readArray(this, in, clz);
	}

	public void writeByteArray(DataOutput out, Byte[] array) throws IOException
	{
		ObjectArrayBuzzer.writeByteArray(out, array);
	}

	public Byte[] readByteArray(DataInput in) throws IOException
	{
		return ObjectArrayBuzzer.readByteArray(in);
	}

	public void writeShortArray(DataOutput out, Short[] array) throws IOException
	{
		ObjectArrayBuzzer.writeShortArray(out, array);
	}

	public Short[] readShortArray(DataInput in) throws IOException
	{
		return ObjectArrayBuzzer.readShortArray(in);
	}

	public void writeIntegerArray(DataOutput out, Integer[] array) throws IOException
	{
		ObjectArrayBuzzer.writeIntegerArray(out, array);
	}

	public Integer[] readIntegerArray(DataInput in) throws IOException
	{
		return ObjectArrayBuzzer.readIntegerArray(in);
	}

	public void writeLongArray(DataOutput out, Long[] array) throws IOException
	{
		ObjectArrayBuzzer.writeLongArray(out, array);
	}

	public Long[] readLongArray(DataInput in) throws IOException
	{
		return ObjectArrayBuzzer.readLongArray(in);
	}

	public void writeFloatArray(DataOutput out, Float[] array) throws IOException
	{
		ObjectArrayBuzzer.writeFloatArray(out, array);
	}

	public Float[] readFloatArray(DataInput in) throws IOException
	{
		return ObjectArrayBuzzer.readFloatArray(in);
	}

	public void writeDoubleArray(DataOutput out, Double[] array) throws IOException
	{
		ObjectArrayBuzzer.writeDoubleArray(out, array);
	}

	public Double[] readDoubleArray(DataInput in) throws IOException
	{
		return ObjectArrayBuzzer.readDoubleArray(in);
	}

	public void writeCharacterArray(DataOutput out, Character[] array) throws IOException
	{
		ObjectArrayBuzzer.writeCharacterArray(out, array);
	}

	public Character[] readCharacterArray(DataInput in) throws IOException
	{
		return ObjectArrayBuzzer.readCharacterArray(in);
	}

	public void writeBooleanArray(DataOutput out, Boolean[] array) throws IOException
	{
		ObjectArrayBuzzer.writeBooleanArray(out, array);
	}

	public Boolean[] readBooleanArray(DataInput in) throws IOException
	{
		return ObjectArrayBuzzer.readBooleanArray(in);
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void writeByteArrayPrimitive(DataOutput out, byte[] array) throws IOException
	{
		PrimitiveArrayBuzzer.writeByteArrayPrimitive(out, array);
	}

	public byte[] readByteArrayPrimitive(DataInput in) throws IOException
	{
		return PrimitiveArrayBuzzer.readByteArrayPrimitive(in);
	}

	public void writeShortArrayPrimitive(DataOutput out, short[] array) throws IOException
	{
		PrimitiveArrayBuzzer.writeShortArrayPrimitive(out, array);
	}

	public short[] readShortArrayPrimitive(DataInput in) throws IOException
	{
		return PrimitiveArrayBuzzer.readShortArrayPrimitive(in);
	}

	public void writeIntegerArrayPrimitive(DataOutput out, int[] array) throws IOException
	{
		PrimitiveArrayBuzzer.writeIntegerArrayPrimitive(out, array);
	}

	public int[] readIntegerArrayPrimitive(DataInput in) throws IOException
	{
		return PrimitiveArrayBuzzer.readIntegerArrayPrimitive(in);
	}

	public void writeLongArrayPrimitive(DataOutput out, long[] array) throws IOException
	{
		PrimitiveArrayBuzzer.writeLongArrayPrimitive(out, array);
	}

	public long[] readLongArrayPrimitive(DataInput in) throws IOException
	{
		return PrimitiveArrayBuzzer.readLongArrayPrimitive(in);
	}

	public void writeFloatArrayPrimitive(DataOutput out, float[] array) throws IOException
	{
		PrimitiveArrayBuzzer.writeFloatArrayPrimitive(out, array);
	}

	public float[] readFloatArrayPrimitive(DataInput in) throws IOException
	{
		return PrimitiveArrayBuzzer.readFloatArrayPrimitive(in);
	}

	public void writeDoubleArrayPrimitive(DataOutput out, double[] array) throws IOException
	{
		PrimitiveArrayBuzzer.writeDoubleArrayPrimitive(out, array);
	}

	public double[] readDoubleArrayPrimitive(DataInput in) throws IOException
	{
		return PrimitiveArrayBuzzer.readDoubleArrayPrimitive(in);
	}

	public void writeCharacterArrayPrimitive(DataOutput out, char[] array) throws IOException
	{
		PrimitiveArrayBuzzer.writeCharacterArrayPrimitive(out, array);
	}

	public char[] readCharacterArrayPrimitive(DataInput in) throws IOException
	{
		return PrimitiveArrayBuzzer.readCharacterArrayPrimitive(in);
	}

	public void writeBooleanArrayPrimitive(DataOutput out, boolean[] array) throws IOException
	{
		PrimitiveArrayBuzzer.writeBooleanArrayPrimitive(out, array);
	}

	public boolean[] readBooleanArrayPrimitive(DataInput in) throws IOException
	{
		return PrimitiveArrayBuzzer.readBooleanArrayPrimitive(in);
	}
}
