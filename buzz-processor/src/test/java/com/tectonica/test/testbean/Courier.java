package com.tectonica.test.testbean;

import java.io.Serializable;
import java.util.Date;

import com.tectonica.buzz.Buzzable;
import com.tectonica.test.testbean.CourierProfile.VehicleType;

@Buzzable
public class Courier implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static enum CourierStatus
	{
		Unassigned(true, true), //
		Busy(true, false), //
		OffDuty(false, false), //
		AssumedOffDuty(false, false);

		public final boolean isOnDuty;
		public final boolean isAvailable;

		private CourierStatus(boolean isOnDuty, boolean isAvailable)
		{
			this.isOnDuty = isOnDuty;
			this.isAvailable = isAvailable;
		}
	}

	@Buzzable
	public static class LatLng implements Serializable
	{
		private static final long serialVersionUID = 1L;

		private double lat;
		private double lng;

		public static LatLng create(double lat, double lng)
		{
			LatLng result = new LatLng();
			result.lat = lat;
			result.lng = lng;
			return result;
		}

		public double getLat()
		{
			return lat;
		}

		public void setLat(double lat)
		{
			this.lat = lat;
		}

		public double getLng()
		{
			return lng;
		}

		public void setLng(double lng)
		{
			this.lng = lng;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(lat);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(lng);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LatLng other = (LatLng) obj;
			if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
				return false;
			if (Double.doubleToLongBits(lng) != Double.doubleToLongBits(other.lng))
				return false;
			return true;
		}
	}

	/**
	 * courier-id
	 */
	private String cid;
	private CourierProfile profile;
	private LatLng location;
	private CourierStatus status;
	private String assignedDlvid;
	private Date lastLocationTime;
	private Date lastStatusTime;
	private Date createdTime;

	public static Courier generate()
	{
		CourierProfile prof = new CourierProfile();
		prof.setEmail("zach@tectonica.co.il");
		prof.setRating(3.7);
		prof.setVehicleType(VehicleType.Motorcycle);

		LatLng ll = new LatLng();
		ll.setLat(432.5);
		ll.setLng(765.2);

		Courier c = new Courier();
		c.cid = "234523452345";
		c.profile = prof;
		c.location = ll;
		c.status = CourierStatus.Busy;
		c.assignedDlvid = "g4e5g634g3g4";
		c.lastLocationTime = new Date();
		c.lastStatusTime = new Date();
		c.createdTime = new Date();
		return c;
	}

	public String getCid()
	{
		return cid;
	}

	public void setCid(String cid)
	{
		this.cid = cid;
	}

	public CourierProfile getProfile()
	{
		return profile;
	}

	public void setProfile(CourierProfile profile)
	{
		this.profile = profile;
	}

	public LatLng getLocation()
	{
		return location;
	}

	public void setLocation(LatLng location)
	{
		this.location = location;
	}

	public CourierStatus getStatus()
	{
		return status;
	}

	public void setStatus(CourierStatus status)
	{
		this.status = status;
	}

	public String getAssignedDlvid()
	{
		return assignedDlvid;
	}

	public void setAssignedDlvid(String assignedDlvid)
	{
		this.assignedDlvid = assignedDlvid;
	}

	public Date getLastLocationTime()
	{
		return lastLocationTime;
	}

	public void setLastLocationTime(Date lastLocationTime)
	{
		this.lastLocationTime = lastLocationTime;
	}

	public Date getLastStatusTime()
	{
		return lastStatusTime;
	}

	public void setLastStatusTime(Date lastStatusTime)
	{
		this.lastStatusTime = lastStatusTime;
	}

	public Date getCreatedTime()
	{
		return createdTime;
	}

	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignedDlvid == null) ? 0 : assignedDlvid.hashCode());
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((createdTime == null) ? 0 : createdTime.hashCode());
		result = prime * result + ((lastLocationTime == null) ? 0 : lastLocationTime.hashCode());
		result = prime * result + ((lastStatusTime == null) ? 0 : lastStatusTime.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Courier other = (Courier) obj;
		if (assignedDlvid == null)
		{
			if (other.assignedDlvid != null)
				return false;
		}
		else if (!assignedDlvid.equals(other.assignedDlvid))
			return false;
		if (cid == null)
		{
			if (other.cid != null)
				return false;
		}
		else if (!cid.equals(other.cid))
			return false;
		if (createdTime == null)
		{
			if (other.createdTime != null)
				return false;
		}
		else if (!createdTime.equals(other.createdTime))
			return false;
		if (lastLocationTime == null)
		{
			if (other.lastLocationTime != null)
				return false;
		}
		else if (!lastLocationTime.equals(other.lastLocationTime))
			return false;
		if (lastStatusTime == null)
		{
			if (other.lastStatusTime != null)
				return false;
		}
		else if (!lastStatusTime.equals(other.lastStatusTime))
			return false;
		if (location == null)
		{
			if (other.location != null)
				return false;
		}
		else if (!location.equals(other.location))
			return false;
		if (profile == null)
		{
			if (other.profile != null)
				return false;
		}
		else if (!profile.equals(other.profile))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
}
