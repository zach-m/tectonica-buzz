package com.tectonica.model.generic;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.tectonica.buzz.Buzzable;
import com.tectonica.serialize.EXTERN;

@Buzzable
public class Primitives implements Externalizable
{
	private static final long serialVersionUID = 10275539472837495L;

	private long sourceId;
	private boolean special;
	private int orderCode;
	private int priority;
	private double[] prices;
	private long[] quantities;

	public Primitives()
	{
		// bean
	}

	public Primitives(final long sourceId, final boolean special, final int orderCode, final int priority, final double[] prices,
			final long[] quantities)
	{
		this.sourceId = sourceId;
		this.special = special;
		this.orderCode = orderCode;
		this.priority = priority;
		this.prices = prices;
		this.quantities = quantities;
	}

	public static Primitives generate()
	{
		return new Primitives(1010L, true, 777, 99, new double[] { 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0 }, new long[] { 1, 2,
				3, 4, 5, 6, 7, 8, 9, 10 });
	}

	public void write(final ByteBuffer byteBuffer)
	{
		byteBuffer.putLong(sourceId);
		byteBuffer.put((byte) (special ? 1 : 0));
		byteBuffer.putInt(orderCode);
		byteBuffer.putInt(priority);

		byteBuffer.putInt(prices.length);
		for (final double price : prices)
		{
			byteBuffer.putDouble(price);
		}

		byteBuffer.putInt(quantities.length);
		for (final long quantity : quantities)
		{
			byteBuffer.putLong(quantity);
		}
	}

	public static Primitives read(final ByteBuffer byteBuffer)
	{
		final long sourceId = byteBuffer.getLong();
		final boolean special = 0 != byteBuffer.get();
		final int orderCode = byteBuffer.getInt();
		final int priority = byteBuffer.getInt();

		final int pricesSize = byteBuffer.getInt();
		final double[] prices = new double[pricesSize];
		for (int i = 0; i < pricesSize; i++)
		{
			prices[i] = byteBuffer.getDouble();
		}

		final int quantitiesSize = byteBuffer.getInt();
		final long[] quantities = new long[quantitiesSize];
		for (int i = 0; i < quantitiesSize; i++)
		{
			quantities[i] = byteBuffer.getLong();
		}

		return new Primitives(sourceId, special, orderCode, priority, prices, quantities);
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final Primitives that = (Primitives) o;

		if (orderCode != that.orderCode)
			return false;
		if (priority != that.priority)
			return false;
		if (sourceId != that.sourceId)
			return false;
		if (special != that.special)
			return false;
		if (!Arrays.equals(prices, that.prices))
			return false;
		if (!Arrays.equals(quantities, that.quantities))
			return false;

		return true;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////

	public long getSourceId()
	{
		return sourceId;
	}

	public void setSourceId(long sourceId)
	{
		this.sourceId = sourceId;
	}

	public boolean isSpecial()
	{
		return special;
	}

	public void setSpecial(boolean special)
	{
		this.special = special;
	}

	public int getOrderCode()
	{
		return orderCode;
	}

	public void setOrderCode(int orderCode)
	{
		this.orderCode = orderCode;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public double[] getPrices()
	{
		return prices;
	}

	public void setPrices(double[] prices)
	{
		this.prices = prices;
	}

	public long[] getQuantities()
	{
		return quantities;
	}

	public void setQuantities(long[] quantities)
	{
		this.quantities = quantities;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeLong(sourceId);
		out.writeBoolean(special);
		out.writeInt(orderCode);
		out.writeInt(priority);
		EXTERN.writeDoubleArrayPrimitive(out, prices);
		EXTERN.writeLongArrayPrimitive(out, quantities);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		sourceId = in.readLong();
		special = in.readBoolean();
		orderCode = in.readInt();
		priority = in.readInt();
		prices = EXTERN.readDoubleArrayPrimitive(in);
		quantities = EXTERN.readLongArrayPrimitive(in);
	}

}
