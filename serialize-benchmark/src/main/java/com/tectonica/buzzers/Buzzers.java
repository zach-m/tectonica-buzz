package com.tectonica.buzzers;

import com.tectonica.buzz.BUZZ;
import com.tectonica.model.bringoz.AuthProfile;
import com.tectonica.model.bringoz.Courier;
import com.tectonica.model.bringoz.CourierProfile;
import com.tectonica.model.bringoz.LatLng;
import com.tectonica.model.generic.NestedLists;
import com.tectonica.model.generic.Primitives;

public class Buzzers
{
	public static void registerBuzzers(BUZZ buzz)
	{
		buzz.registerBuzzer(AuthProfile.class, new AuthProfileBuzzer());
		buzz.registerBuzzer(Courier.class, new CourierBuzzer());
		buzz.registerBuzzer(CourierProfile.class, new CourierProfileBuzzer());
		buzz.registerBuzzer(LatLng.class, new LatLngBuzzer());
		buzz.registerBuzzer(NestedLists.class, new NestedListsBuzzer());
		buzz.registerBuzzer(Primitives.class, new PrimitivesBuzzer());
	}
}
