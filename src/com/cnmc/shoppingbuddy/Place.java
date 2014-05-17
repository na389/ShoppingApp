package com.cnmc.shoppingbuddy;

import java.io.Serializable;

import com.google.api.client.util.Key;
 
/** Implement this class from "Serializable"
* So that you can pass this class Object to another using Intents
* Otherwise you can't pass to another actitivy
* */
public class Place implements Serializable {
 
    @Key
    public String id;
     
    @Key
    public String name;
     
    @Key
    public String reference;
     
    @Key
    public String icon;
     
    @Key
    public String vicinity;
     
    @Key
    public Geometry geometry;
     
    @Key
    public String formatted_address;
     
    @Key
    public String formatted_phone_number;
    
    
    @Override
    public String toString() {
        return name + " - " + id + " - " + reference;
    }
     
    public static class Geometry implements Serializable
    {
        @Key
        public Location location;
    }
     
    public static class Location implements Serializable
    {
        @Key
        public double lat;
         
        @Key
        public double lng;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
     
}
