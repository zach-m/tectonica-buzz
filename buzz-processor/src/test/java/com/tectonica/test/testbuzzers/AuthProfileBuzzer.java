package com.tectonica.test.testbuzzers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tectonica.buzz.BUZZ;
import com.tectonica.buzz.BUZZ.Buzzer;
import com.tectonica.test.testbean.AuthProfile;

public class AuthProfileBuzzer implements Buzzer<AuthProfile>
{
	@Override
	public void getAndWrite(BUZZ buzz, AuthProfile obj, DataOutput out) throws IOException
	{
		buzz.writeString(out, obj.getFirstName());
		buzz.writeString(out, obj.getLastName());
		buzz.writeString(out, obj.getEmail());
		buzz.writeString(out, obj.getPassword());
		buzz.writeString(out, obj.getPasswordHash());
		buzz.writeString(out, obj.getResetPasswordToken());
		buzz.writeDate(out, obj.getResetPasswordExpiration());
	}

	@Override
	public void readAndSet(BUZZ buzz, AuthProfile obj, DataInput in) throws IOException
	{
		obj.setFirstName(buzz.readString(in));
		obj.setLastName(buzz.readString(in));
		obj.setEmail(buzz.readString(in));
		obj.setPassword(buzz.readString(in));
		obj.setPasswordHash(buzz.readString(in));
		obj.setResetPasswordToken(buzz.readString(in));
		obj.setResetPasswordExpiration(buzz.readDate(in));
	}
}
