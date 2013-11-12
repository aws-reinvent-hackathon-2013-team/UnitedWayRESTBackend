package ch.furthermore.demo.st;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

public class ZipToLatLong {
    private static ZipToLatLong ourInstance = new ZipToLatLong();

    public static ZipToLatLong getInstance() {
        return ourInstance;
    }

    private HashMap <Long, LatLong> zipLatLongMap = new HashMap <Long, LatLong>();

    private ZipToLatLong() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("zipcode.csv")));

            for (String line = in.readLine(); line != null; line = in.readLine()) {
                String [] fields = line.split(",");
                String latitutine  = fields[3].replace('\"',' ').trim();
                String longitutine = fields[4].replace('\"',' ').trim();

                zipLatLongMap.put(Long.parseLong(fields[0].replace('\"',' ').trim()), new LatLong(Float.parseFloat(latitutine), Float.parseFloat(longitutine) ));
            }
        }

        catch (IOException e) {
        	throw new RuntimeException(e);
        }

    }

    public void printTable () {
        Set<Long> keys =  zipLatLongMap.keySet() ;

        for (Long key : keys) {
            System.out.print(key);
            System.out.print(zipLatLongMap.get(key).getLatitude());
            System.out.print(zipLatLongMap.get(key).getLongitude());
        }
    }

    public LatLong getLatLongForZip(Long zipCode) {
        return zipLatLongMap.get(zipCode);
    }


    public float distance (LatLong p1, LatLong p2) {
        double p1LatRad = p1.getLatitude() * 0.0174532925;
        double p1LongRad = p1.getLongitude() * 0.0174532925;
        double p2LatRad = p2.getLatitude() * 0.0174532925;
        double p2LongRad = p2.getLongitude() * 0.0174532925;

        double R = 6371; // km
        double d = Math.acos(Math.sin(p1LatRad)*Math.sin(p2LatRad) +
                Math.cos(p1LatRad)*Math.cos(p2LatRad) *
                Math.cos(p2LongRad-p1LongRad)) * R;

        return (float)d;
    }
}
