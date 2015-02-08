package com.tectonica.model.bringoz;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import com.tectonica.buzz.Buzzable;
import com.tectonica.serialize.EXTERN;

@Buzzable
public class AuthProfile implements Externalizable
{
	private static final long serialVersionUID = 1L;

	/**
	 * mandatory at sign-up
	 */
	private String firstName;

	/**
	 * mandatory at sign-up
	 */
	private String lastName;

	/**
	 * mandatory at sign-up
	 */
	private String email;

	/**
	 * mandatory at sign-up
	 */
	private String password;
	private String passwordHash;
	private String resetPasswordToken;
	private Date resetPasswordExpiration;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPasswordHash()
	{
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash)
	{
		this.passwordHash = passwordHash;
	}

	public String getResetPasswordToken()
	{
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken)
	{
		this.resetPasswordToken = resetPasswordToken;
	}

	public Date getResetPasswordExpiration()
	{
		return resetPasswordExpiration;
	}

	public void setResetPasswordExpiration(Date resetPasswordExpiration)
	{
		this.resetPasswordExpiration = resetPasswordExpiration;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordHash == null) ? 0 : passwordHash.hashCode());
		result = prime * result + ((resetPasswordExpiration == null) ? 0 : resetPasswordExpiration.hashCode());
		result = prime * result + ((resetPasswordToken == null) ? 0 : resetPasswordToken.hashCode());
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
		AuthProfile other = (AuthProfile) obj;
		if (email == null)
		{
			if (other.email != null)
				return false;
		}
		else if (!email.equals(other.email))
			return false;
		if (firstName == null)
		{
			if (other.firstName != null)
				return false;
		}
		else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null)
		{
			if (other.lastName != null)
				return false;
		}
		else if (!lastName.equals(other.lastName))
			return false;
		if (password == null)
		{
			if (other.password != null)
				return false;
		}
		else if (!password.equals(other.password))
			return false;
		if (passwordHash == null)
		{
			if (other.passwordHash != null)
				return false;
		}
		else if (!passwordHash.equals(other.passwordHash))
			return false;
		if (resetPasswordExpiration == null)
		{
			if (other.resetPasswordExpiration != null)
				return false;
		}
		else if (!resetPasswordExpiration.equals(other.resetPasswordExpiration))
			return false;
		if (resetPasswordToken == null)
		{
			if (other.resetPasswordToken != null)
				return false;
		}
		else if (!resetPasswordToken.equals(other.resetPasswordToken))
			return false;
		return true;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		EXTERN.writeString(out, firstName);
		EXTERN.writeString(out, lastName);
		EXTERN.writeString(out, email);
		EXTERN.writeString(out, password);
		EXTERN.writeString(out, passwordHash);
		EXTERN.writeString(out, resetPasswordToken);
		EXTERN.writeDate(out, resetPasswordExpiration);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		firstName = EXTERN.readString(in);
		lastName = EXTERN.readString(in);
		email = EXTERN.readString(in);
		password = EXTERN.readString(in);
		passwordHash = EXTERN.readString(in);
		resetPasswordToken = EXTERN.readString(in);
		resetPasswordExpiration = EXTERN.readDate(in);
	}
}
