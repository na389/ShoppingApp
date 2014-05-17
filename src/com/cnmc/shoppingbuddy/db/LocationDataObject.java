package com.cnmc.shoppingbuddy.db;

import java.io.Serializable;

public class LocationDataObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mLocId;
	private String mAddress;
	private String mDataType;
	private String mImageLocation;
	private String mLocLat;
	private String mLocLong;
	private String mReview;
	
	public String getmLocLat() {
		return mLocLat;
	}
	public void setmLocLat(String mLocLat) {
		this.mLocLat = mLocLat;
	}
	public String getmLocLong() {
		return mLocLong;
	}
	public void setmLocLong(String mLocLong) {
		this.mLocLong = mLocLong;
	}
	private boolean mFavorite;
	public String getmAddress() {
		return mAddress;
	}
	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
	}
	public String getmDataType() {
		return mDataType;
	}
	public void setmDataType(String mDataType) {
		this.mDataType = mDataType;
	}
	public String getmImageLocation() {
		return mImageLocation;
	}
	public void setmImageLocation(String mImageLocation) {
		this.mImageLocation = mImageLocation;
	}
	public String getmReview() {
		return mReview;
	}
	public void setmReview(String mReview) {
		this.mReview = mReview;
	}
	public boolean ismFavorite() {
		return mFavorite;
	}
	public void setmFavorite(boolean mFavorite) {
		this.mFavorite = mFavorite;
	}
	
	public String getmLocId() {
		return mLocId;
	}
	public void setmLocId(String mLocId) {
		this.mLocId = mLocId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mAddress == null) ? 0 : mAddress.hashCode());
		result = prime * result
				+ ((mDataType == null) ? 0 : mDataType.hashCode());
		result = prime * result + (mFavorite ? 1231 : 1237);
		result = prime * result
				+ ((mImageLocation == null) ? 0 : mImageLocation.hashCode());
		result = prime * result + ((mLocId == null) ? 0 : mLocId.hashCode());
		result = prime * result + ((mLocLat == null) ? 0 : mLocLat.hashCode());
		result = prime * result
				+ ((mLocLong == null) ? 0 : mLocLong.hashCode());
		result = prime * result + ((mReview == null) ? 0 : mReview.hashCode());
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
		LocationDataObject other = (LocationDataObject) obj;
		if (mAddress == null) {
			if (other.mAddress != null)
				return false;
		} else if (!mAddress.equals(other.mAddress))
			return false;
		if (mDataType == null) {
			if (other.mDataType != null)
				return false;
		} else if (!mDataType.equals(other.mDataType))
			return false;
		if (mFavorite != other.mFavorite)
			return false;
		if (mImageLocation == null) {
			if (other.mImageLocation != null)
				return false;
		} else if (!mImageLocation.equals(other.mImageLocation))
			return false;
		if (mLocId == null) {
			if (other.mLocId != null)
				return false;
		} else if (!mLocId.equals(other.mLocId))
			return false;
		if (mLocLat == null) {
			if (other.mLocLat != null)
				return false;
		} else if (!mLocLat.equals(other.mLocLat))
			return false;
		if (mLocLong == null) {
			if (other.mLocLong != null)
				return false;
		} else if (!mLocLong.equals(other.mLocLong))
			return false;
		if (mReview == null) {
			if (other.mReview != null)
				return false;
		} else if (!mReview.equals(other.mReview))
			return false;
		return true;
	}
		

}
