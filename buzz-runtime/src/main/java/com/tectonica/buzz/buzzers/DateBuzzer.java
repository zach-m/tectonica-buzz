package com.tectonica.buzz.buzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;

public class DateBuzzer
{
	private static final long NULL_DATE = Long.MIN_VALUE;

	public static void writeDate(DataOutput out, Date d) throws IOException
	{
		if (d == null)
			out.writeLong(NULL_DATE);
		else
			out.writeLong(d.getTime());
	}

	public static Date readDate(DataInput in) throws IOException
	{
		long time = in.readLong();
		return (time == NULL_DATE) ? null : new Date(time);
	}
}
