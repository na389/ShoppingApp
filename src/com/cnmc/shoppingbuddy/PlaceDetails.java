package com.cnmc.shoppingbuddy;

import java.io.Serializable;

import com.google.api.client.util.Key;
 
/** Implement this class from "Serializable"
* So that you can pass this class Object to another using Intents
* Otherwise you can't pass to another actitivy
* */
public class PlaceDetails implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Key
    public String status;
     
    @Key
    public Place result;
 
    @Override
    public String toString() {
        if (result!=null) {
            return result.toString();
        }
        return super.toString();
    }
}