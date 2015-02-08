package com.tectonica.test.testbuzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tectonica.buzz.BUZZ;
import com.tectonica.buzz.BUZZ.Buzzer;
import com.tectonica.test.testbean.AuthProfile;
import com.tectonica.test.testbean.CourierProfile;
import com.tectonica.test.testbean.CourierProfile.VehicleType;

public class CourierProfileBuzzer implements Buzzer<CourierProfile>
{
	@Override
	public void getAndWrite(BUZZ buzz, CourierProfile obj, DataOutput out) throws IOException
	{
		buzz.getAndWrite(out, obj, AuthProfile.class);
		buzz.writeString(out, obj.getPhoneNumber());
		buzz.writeString(out, obj.getPictureUrl());
		buzz.writeDouble(out, obj.getRating());
		buzz.writeInteger(out, obj.getNumOfVotes());
		buzz.writeEnum(out, obj.getVehicleType());
		buzz.writeString(out, obj.getDid());
		buzz.writeDate(out, obj.getUpdatedTime());
	}

	@Override
	public void readAndSet(BUZZ buzz, CourierProfile obj, DataInput in) throws IOException
	{
		buzz.readIntoObject(in, obj, AuthProfile.class);
		obj.setPhoneNumber(buzz.readString(in));
		obj.setPictureUrl(buzz.readString(in));
		obj.setRating(buzz.readDouble(in));
		obj.setNumOfVotes(buzz.readInteger(in));
		obj.setVehicleType(buzz.readEnum(in, VehicleType.values()));
		obj.setDid(buzz.readString(in));
		obj.setUpdatedTime(buzz.readDate(in));
	}
}
