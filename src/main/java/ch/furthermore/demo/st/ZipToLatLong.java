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
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("US.txt")));

            for (String line = in.readLine(); line != null; line = in.readLine()) {
                String [] fields = line.split("\t");
                String longitutine = fields[fields.length-1]  ;
                String latitutine = fields[fields.length-2] ;
                zipLatLongMap.put(Long.parseLong(fields[1]), new LatLong(Float.parseFloat(latitutine), Float.parseFloat(longitutine) ));
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
}
