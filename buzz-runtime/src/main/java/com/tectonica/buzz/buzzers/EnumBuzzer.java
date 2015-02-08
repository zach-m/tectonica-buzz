package com.tectonica.buzz.buzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EnumBuzzer
{
	public static void writeEnum(DataOutput out, Enum<?> e) throws IOException
	{
		if (e == null)
			out.writeInt(-1);
		else
			out.writeInt(e.ordinal());
	}

	public static <E extends Enum<E>> E readEnum(DataInput in, E[] values) throws IOException
	{
		int ordinal = in.readInt();
		return ordinal < 0L ? null : values[ordinal];
	}
}
