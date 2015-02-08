package com.tectonica.model.bringoz;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import com.tectonica.buzz.Buzzable;
import com.tectonica.serialize.EXTERN;

@Buzzable
public class CourierProfile extends AuthProfile
{
	private static final long serialVersionUID = 1L;

	@Buzzable
	public static enum VehicleType
	{
		Bicycle, Motorcycle, PrivateCar, Van, Truck;
	}

	/**
	 * mandatory at sign-up
	 */
	private String phoneNumber;

	private String pictureUrl;

	private Double rating;
	private Integer numOfVotes;
	// TODO: store the users' comments separately

	private VehicleType vehicleType;

	/**
	 * dispatcher-id that the courier works for, or null if none
	 */
	private String did;

	/**
	 * last time the courier's profile was updated (read-only)
	 */
	private Date updatedTime;

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getPictureUrl()
	{
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl)
	{
		this.pictureUrl = pictureUrl;
	}

	public Double getRating()
	{
		return rating;
	}

	public void setRating(Double rating)
	{
		this.rating = rating;
	}

	public Integer getNumOfVotes()
	{
		return numOfVotes;
	}

	public void setNumOfVotes(Integer numOfVotes)
	{
		this.numOfVotes = numOfVotes;
	}

	public VehicleType getVehicleType()
	{
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType)
	{
		this.vehicleType = vehicleType;
	}

	public String getDid()
	{
		return did;
	}

	public void setDid(String did)
	{
		this.did = did;
	}

	public Date getUpdatedTime()
	{
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime)
	{
		this.updatedTime = updatedTime;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((did == null) ? 0 : did.hashCode());
		result = prime * result + ((numOfVotes == null) ? 0 : numOfVotes.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((pictureUrl == null) ? 0 : pictureUrl.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((updatedTime == null) ? 0 : updatedTime.hashCode());
		result = prime * result + ((vehicleType == null) ? 0 : vehicleType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourierProfile other = (CourierProfile) obj;
		if (did == null)
		{
			if (other.did != null)
				return false;
		}
		else if (!did.equals(other.did))
			return false;
		if (numOfVotes == null)
		{
			if (other.numOfVotes != null)
				return false;
		}
		else if (!numOfVotes.equals(other.numOfVotes))
			return false;
		if (phoneNumber == null)
		{
			if (other.phoneNumber != null)
				return false;
		}
		else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (pictureUrl == null)
		{
			if (other.pictureUrl != null)
				return false;
		}
		else if (!pictureUrl.equals(other.pictureUrl))
			return false;
		if (rating == null)
		{
			if (other.rating != null)
				return false;
		}
		else if (!rating.equals(other.rating))
			return false;
		if (updatedTime == null)
		{
			if (other.updatedTime != null)
				return false;
		}
		else if (!updatedTime.equals(other.updatedTime))
			return false;
		if (vehicleType != other.vehicleType)
			return false;
		return true;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		super.writeExternal(out);
		EXTERN.writeString(out, phoneNumber);
		EXTERN.writeString(out, pictureUrl);
		EXTERN.writeDouble(out, rating);
		EXTERN.writeInteger(out, numOfVotes);
		EXTERN.writeEnum(out, vehicleType);
		EXTERN.writeString(out, did);
		EXTERN.writeDate(out, updatedTime);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		super.readExternal(in);
		phoneNumber = EXTERN.readString(in);
		pictureUrl = EXTERN.readString(in);
		rating = EXTERN.readDouble(in);
		numOfVotes = EXTERN.readInteger(in);
		vehicleType = EXTERN.readEnum(in, VehicleType.values());
		did = EXTERN.readString(in);
		updatedTime = EXTERN.readDate(in);
	}
}
