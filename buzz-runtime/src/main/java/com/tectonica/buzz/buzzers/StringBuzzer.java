package com.tectonica.buzz.buzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StringBuzzer
{
	public static void writeString(DataOutput out, String s) throws IOException
	{
		final int length = (s == null) ? -1 : s.length();
		out.writeInt(length);
		if (length > 0)
			out.writeChars(s);
	}

	public static String readString(DataInput in) throws IOException
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
}
