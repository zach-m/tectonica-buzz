package com.tectonica.buzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

import com.tectonica.buzz.BUZZ;
import com.tectonica.buzz.BUZZ.Buzzer;
import com.tectonica.model.generic.NestedLists;

public class NestedListsBuzzer implements Buzzer<NestedLists>
{
	@Override
	public void getAndWrite(BUZZ buzz, NestedLists obj, DataOutput out) throws IOException
	{
		out.writeBoolean(obj.isSpecial());
		buzz.writeDoubleArrayPrimitive(out, obj.getPrices());
		buzz.writeLongArrayPrimitive(out, obj.getQuantities());
		buzz.writeString(out, obj.getMsg());
		buzz.writeCollection(out, obj.getChildren());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readAndSet(BUZZ buzz, NestedLists obj, DataInput in) throws IOException
	{
		obj.setSpecial(in.readBoolean());
		obj.setPrices(buzz.readDoubleArrayPrimitive(in));
		obj.setQuantities(buzz.readLongArrayPrimitive(in));
		obj.setMsg(buzz.readString(in));
		obj.setChildren(buzz.readCollection(in, NestedLists.class, ArrayList.class));
	}
}