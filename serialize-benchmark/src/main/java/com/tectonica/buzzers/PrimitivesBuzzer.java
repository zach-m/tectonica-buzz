package com.tectonica.buzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tectonica.buzz.BUZZ;
import com.tectonica.buzz.BUZZ.Buzzer;
import com.tectonica.model.generic.Primitives;

public class PrimitivesBuzzer implements Buzzer<Primitives>
{
	@Override
	public void getAndWrite(BUZZ buzz, Primitives obj, DataOutput out) throws IOException
	{
		out.writeLong(obj.getSourceId());
		out.writeBoolean(obj.isSpecial());
		out.writeInt(obj.getOrderCode());
		out.writeInt(obj.getPriority());
		buzz.writeDoubleArrayPrimitive(out, obj.getPrices());
		buzz.writeLongArrayPrimitive(out, obj.getQuantities());
	}

	@Override
	public void readAndSet(BUZZ buzz, Primitives obj, DataInput in) throws IOException
	{
		obj.setSourceId(in.readLong());
		obj.setSpecial(in.readBoolean());
		obj.setOrderCode(in.readInt());
		obj.setPriority(in.readInt());
		obj.setPrices(buzz.readDoubleArrayPrimitive(in));
		obj.setQuantities(buzz.readLongArrayPrimitive(in));
	}
}
