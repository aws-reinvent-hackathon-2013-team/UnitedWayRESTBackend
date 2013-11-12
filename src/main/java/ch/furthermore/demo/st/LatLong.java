package ch.furthermore.demo.st;

/**
 * Created with IntelliJ IDEA.
 * User: antoniopellizzaro
 * Date: 11/12/13
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */

public class LatLong {
    private float Latitude;
    private float Longitude;

    public LatLong (float latitude, float longitude) {
        Latitude=latitude;
        Longitude=longitude;
    }


    public float getLatitude(){ return Latitude;}
    public float getLongitude(){ return Longitude;}

    public void setLatitude (float l)  { this.Latitude=l;}
    public void setLongitude (float l)  { this.Longitude=l;}
}