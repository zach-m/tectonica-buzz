package com.tectonica.model.generic;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.tectonica.buzz.Buzzable;
import com.tectonica.serialize.EXTERN;

@Buzzable
public class NestedLists implements Externalizable
{
	private static final long serialVersionUID = 10275539472837495L;

	/* final */boolean special;
	/* final */double[] prices;
	/* final */long[] quantities;
	/* final */String msg;
	/* final */List<NestedLists> children;

	public NestedLists()
	{
		// bean
	}

	public NestedLists(boolean special, double[] prices, long[] quantities, String msg, List<NestedLists> children)
	{
		this.special = special;
		this.prices = prices;
		this.quantities = quantities;
		this.msg = msg;
		this.children = children;
	}

	public static Random rand = new Random();

	public static NestedLists generate(int childrenCount)
	{
		boolean special = rand.nextBoolean();
		double[] prices = new double[] { rand.nextDouble(), rand.nextDouble(), rand.nextDouble() };
		long[] quantities = new long[] { rand.nextLong(), rand.nextLong() };
		String msg = Long.toHexString(rand.nextLong()) + Long.toHexString(rand.nextLong());
		List<NestedLists> children = null;
		if (childrenCount > 0)
		{
			children = new ArrayList<>();
			for (int i = 0; i < childrenCount; i++)
				children.add(generate(childrenCount - 1));
		}
		return new NestedLists(special, prices, quantities, msg, children);
	}

	public static NestedLists generate()
	{
		return generate(2);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NestedLists other = (NestedLists) obj;
		if (children == null)
		{
			if (other.children != null)
				return false;
		}
		else if (!children.equals(other.children))
			return false;
		if (msg == null)
		{
			if (other.msg != null)
				return false;
		}
		else if (!msg.equals(other.msg))
			return false;
		if (!Arrays.equals(prices, other.prices))
			return false;
		if (!Arrays.equals(quantities, other.quantities))
			return false;
		if (special != other.special)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "ObjectToBeSerialised [special=" + special + ", prices=" + Arrays.toString(prices) + ", quantities="
				+ Arrays.toString(quantities) + ", msg=" + msg + ", children=" + children + "]";
	}

	public boolean isSpecial()
	{
		return special;
	}

	public void setSpecial(boolean special)
	{
		this.special = special;
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

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public List<NestedLists> getChildren()
	{
		return children;
	}

	public void setChildren(List<NestedLists> children)
	{
		this.children = children;
	}

	// /////////////////////////////////////////////////////////////////////////

	public void write(final ByteBuffer byteBuffer)
	{
		byteBuffer.put((byte) (special ? 1 : 0));

		byteBuffer.putInt(prices.length);
		for (final double price : prices)
			byteBuffer.putDouble(price);

		byteBuffer.putInt(quantities.length);
		for (final long quantity : quantities)
			byteBuffer.putLong(quantity);

		byte[] msgBytes = msg.getBytes();
		byteBuffer.putInt(msgBytes.length);
		byteBuffer.put(msgBytes);

		byteBuffer.putInt(children == null ? 0 : children.size());
		if (children != null)
		{
			for (NestedLists child : children)
				child.write(byteBuffer);
		}
	}

	public static NestedLists read(final ByteBuffer byteBuffer)
	{
		final boolean special = 0 != byteBuffer.get();

		final int pricesSize = byteBuffer.getInt();
		final double[] prices = new double[pricesSize];
		for (int i = 0; i < pricesSize; i++)
			prices[i] = byteBuffer.getDouble();

		final int quantitiesSize = byteBuffer.getInt();
		final long[] quantities = new long[quantitiesSize];
		for (int i = 0; i < quantitiesSize; i++)
			quantities[i] = byteBuffer.getLong();

		final int msgSize = byteBuffer.getInt();
		byte[] msgBytes = new byte[msgSize];
		byteBuffer.get(msgBytes);
		String msg = new String(msgBytes);

		List<NestedLists> children = null;
		final int childrenSize = byteBuffer.getInt();
		if (childrenSize > 0)
		{
			children = new ArrayList<>(childrenSize);
			for (int i = 0; i < childrenSize; i++)
				children.add(read(byteBuffer));
		}

		return new NestedLists(special, prices, quantities, msg, children);
	}

	// /////////////////////////////////////////////////////////////////////////

	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeBoolean(special);
		EXTERN.writeDoubleArrayPrimitive(out, prices);
		EXTERN.writeLongArrayPrimitive(out, quantities);
		EXTERN.writeString(out, msg);
		EXTERN.writeExternalizableCollection(out, children);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		special = in.readBoolean();
		prices = EXTERN.readDoubleArrayPrimitive(in);
		quantities = EXTERN.readLongArrayPrimitive(in);
		msg = EXTERN.readString(in);
		children = EXTERN.readExternalizableCollection(in, NestedLists.class, ArrayList.class);
	}
}