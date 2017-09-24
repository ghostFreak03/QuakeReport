/**
 * Created by Ghost Freak on 18-02-2017.
 */

/**
 * Class for earthquake object.Arraylist sholud contain objects of this class
 */
package com.example.android.quakereport;
public class Earthquake {
    //declare variables
    /**
     * @param magnitude-magnitude of the earth quake
     * @param place-place of the earthquake
     * @param date-date on which earthquake occured
     */
    private double magnitude;
    private String place;
    private long timeinMilliseconds;
    private String url;
    //constructor
    public Earthquake(double mag,String p,long d,String urlLink){
        magnitude=mag;
        place=p;
        timeinMilliseconds=d;
        url=urlLink;
    }
    //getters
    public double getMagnitude(){
        return magnitude;
    }
    public String getPlace(){
        return place;
    }
    public long getDate(){
        return timeinMilliseconds;
    }
    public String getUrl() {return url; }
}
