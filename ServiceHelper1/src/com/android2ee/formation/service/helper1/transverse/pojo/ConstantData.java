/**
 * <ul>
 * <li>ServiceHelper1</li>
 * <li>com.android2ee.formation.service.helper1.transverse.pojo</li>
 * <li>26 janv. 2013</li>
 * <li>======================================================</li>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li> /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> Produced by <strong>Dr. Mathias
 * SEGUY</strong>.</br> Delivered by <strong>http://android2ee.com/</strong></br> Belongs to
 * <strong>Mathias Seguy</strong></br> </br> This code is free for any usage except training and
 * can't be distribute.</br> The distribution is reserved to the site
 * <strong>http://android2ee.com</strong>.</br> The intelectual property belongs to <strong>Mathias
 * Seguy</strong>.</br> <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * ********************
 * ******************************************************************************
 * ***************</br> Ce code est libre de toute utilisation mais n'est pas distribuable.</br> Sa
 * distribution est reservée au site <strong>http://android2ee.com</strong>.</br> Sa propriété
 * intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************
 * ************************************************************************</br>
 */
package com.android2ee.formation.service.helper1.transverse.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 * This class aims to show how to implement its POJO to be parcelable and to be pass from within an Intent
 */
public class ConstantData implements Parcelable {
	/******************************************************************************************/
	/** Some attributes **************************************************************************/
	/******************************************************************************************/

	public String message;
	public String message2;
	public int entier;
	public boolean boleen;

	/**
	 * A constructor
	 * @param message
	 * @param entier
	 * @param boleen
	 */
	public ConstantData(String message, int entier, boolean boleen) {
		this.message = message;
		this.message2 = message + "2";
		this.entier = entier;
		this.boleen = boleen;
	}

	/******************************************************************************************/
	/** Implementation of Parcelable **************************************************************************/
	/******************************************************************************************/

	/*
	 * (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(message);
		dest.writeString(message2);
		dest.writeInt(entier);
		boolean[] toto = new boolean[] { boleen };
		dest.writeBooleanArray(toto);
	}

	
	/**
	 * Needed creator
	 */
	public static final Parcelable.Creator<ConstantData> CREATOR = new Parcelable.Creator<ConstantData>() {
		public ConstantData createFromParcel(Parcel in) {
			return new ConstantData(in);
		}

		public ConstantData[] newArray(int size) {
			return new ConstantData[size];
		}
	};

	
	/**
	 * Needed constructor
	 * @param in
	 */
	private ConstantData(Parcel in) {
		message = in.readString();
		message = in.readString();
		entier = in.readInt();
		boolean[] toto = new boolean[] { boleen };
		in.readBooleanArray(toto);
		boleen = toto[0];
	}
}
