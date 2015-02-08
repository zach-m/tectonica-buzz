package com.tectonica.test.testbuzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tectonica.buzz.BUZZ;
import com.tectonica.buzz.BUZZ.Buzzer;
import com.tectonica.test.testbean.Courier;
import com.tectonica.test.testbean.Courier.CourierStatus;
import com.tectonica.test.testbean.Courier.LatLng;
import com.tectonica.test.testbean.CourierProfile;

public class CourierBuzzer implements Buzzer<Courier>
{
	@Override
	public void getAndWrite(BUZZ buzz, Courier obj, DataOutput out) throws IOException
	{
		buzz.writeString(out, obj.getCid());
		buzz.writeObject(out, obj.getProfile());
		buzz.writeObject(out, obj.getLocation());
		buzz.writeEnum(out, obj.getStatus());
		buzz.writeString(out, obj.getAssignedDlvid());
		buzz.writeDate(out, obj.getLastLocationTime());
		buzz.writeDate(out, obj.getLastStatusTime());
		buzz.writeDate(out, obj.getCreatedTime());
	}

	@Override
	public void readAndSet(BUZZ buzz, Courier obj, DataInput in) throws IOException
	{
		obj.setCid(buzz.readString(in));
		obj.setProfile(buzz.readObject(in, CourierProfile.class));
		obj.setLocation(buzz.readObject(in, LatLng.class));
		obj.setStatus(buzz.readEnum(in, CourierStatus.values()));
		obj.setAssignedDlvid(buzz.readString(in));
		obj.setLastLocationTime(buzz.readDate(in));
		obj.setLastStatusTime(buzz.readDate(in));
		obj.setCreatedTime(buzz.readDate(in));
	}
}
