package ch.furthermore.demo.st;


import org.junit.Test;

public class ZipToLatLongTest {

    @Test
    public void testTableRead() {

       //ZipToLatLong.getInstance().printTable() ;
        float lat = ZipToLatLong.getInstance().getLatLongForZip((long)94107).getLatitude();
        float longitude = ZipToLatLong.getInstance().getLatLongForZip((long)94107).getLongitude();

        System.out.println(lat);
        System.out.println(longitude);
        assert(Math.abs(lat-37.766529)<0.1 );
    }

    @Test
    public void testCalcDistance() {

        LatLong p1 = ZipToLatLong.getInstance().getLatLongForZip((long)94107);
        LatLong p2 = ZipToLatLong.getInstance().getLatLongForZip((long)94066);

        double d = ZipToLatLong.getInstance().distance(p1,p2);
        System.out.println(d);
        assert(d<100);
    }


}
