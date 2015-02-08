package com.tectonica.buzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tectonica.buzz.BUZZ;
import com.tectonica.buzz.BUZZ.Buzzer;
import com.tectonica.model.bringoz.LatLng;

public class LatLngBuzzer implements Buzzer<LatLng>
{
	@Override
	public void getAndWrite(BUZZ buzz, LatLng obj, DataOutput out) throws IOException
	{
		out.writeDouble(obj.getLat());
		out.writeDouble(obj.getLng());
	}

	@Override
	public void readAndSet(BUZZ buzz, LatLng obj, DataInput in) throws IOException
	{
		obj.setLat(in.readDouble());
		obj.setLng(in.readDouble());
	}
}
