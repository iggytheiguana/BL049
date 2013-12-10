package com.craftbeer.modal;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SearchBeerModel  implements  Serializable {
	/**
	 * {"searchBeer":[{"searchBeer":{"aroma":"","sweet":"","bitter":"","malt":""
	 * ,"yeast":"","mouthFeel":"","sour":"","additive":"","booziness":"",
	 * "brewery"
	 * :"55","beerName":"1","beerStyle":"1","abv":"1","ibu":"1","mood":"1"
	 * ,"venue":"1","event":"1","hype":"1"}}]}
	 */
	
	private String aroma="",sweet="",bitter="",malt="",yeast="",mouthfeel="",sour="",additive="",booziness="",brewery="",beerName="",beerStyle="",abv="",ibu="",mood="",
	
	venue="",event="",hype="",tastePercentage="",beerId="";
	
	String facebookHandle="" , twitterHandle="" , facebookurl="";

	public String getFacebookurl() {
		return facebookurl;
	}

	public void setFacebookurl(String facebookurl) {
		this.facebookurl = facebookurl;
	}

	public String getFacebookHandle() {
		return facebookHandle;
	}

	public void setFacebookHandle(String facebookHandle) {
		this.facebookHandle = facebookHandle;
	}

	public String getTwitterHandle() {
		return twitterHandle;
	}

	public void setTwitterHandle(String twitterHandle) {
		this.twitterHandle = twitterHandle;
	}

	public String getBeerId() {
		return beerId;
	}

	public void setBeerId(String beerId) {
		this.beerId = beerId;
	}

	public String getTastePercentage() {
		return tastePercentage;
	}

	public void setTastePercentage(String tastePercentage) {
		this.tastePercentage = tastePercentage;
	}

	public String getSweet() {
		return sweet;
	}

	public void setSweet(String sweet) {
		this.sweet = sweet;
	}

	public String getBitter() {
		return bitter;
	}

	public void setBitter(String bitter) {
		this.bitter = bitter;
	}

	public String getMalt() {
		return malt;
	}

	public void setMalt(String malt) {
		this.malt = malt;
	}

	public String getYeast() {
		return yeast;
	}

	public void setYeast(String yeast) {
		this.yeast = yeast;
	}

	public String getMouthfeel() {
		return mouthfeel;
	}

	public void setMouthfeel(String mouthfeel) {
		this.mouthfeel = mouthfeel;
	}

	public String getSour() {
		return sour;
	}

	public void setSour(String sour) {
		this.sour = sour;
	}

	public String getAdditive() {
		return additive;
	}

	public void setAdditive(String additive) {
		this.additive = additive;
	}

	public String getBooziness() {
		return booziness;
	}

	public void setBooziness(String booziness) {
		this.booziness = booziness;
	}

	public String getBrewery() {
		return brewery;
	}

	public void setBrewery(String brewery) {
		this.brewery = brewery;
	}

	public String getBeerName() {
		return beerName;
	}

	public void setBeerName(String beerName) {
		this.beerName = beerName;
	}

	public String getBeerStyle() {
		return beerStyle;
	}

	public void setBeerStyle(String beerStyle) {
		this.beerStyle = beerStyle;
	}

	public String getAbv() {
		return abv;
	}

	public void setAbv(String abv) {
		this.abv = abv;
	}

	public String getIbu() {
		return ibu;
	}

	public void setIbu(String ibu) {
		this.ibu = ibu;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getHype() {
		return hype;
	}

	public void setHype(String hype) {
		this.hype = hype;
	}

	public String getAroma() {
		return aroma;
	}

	public void setAroma(String aroma) {
		this.aroma = aroma;
	}
	
	
	
}
