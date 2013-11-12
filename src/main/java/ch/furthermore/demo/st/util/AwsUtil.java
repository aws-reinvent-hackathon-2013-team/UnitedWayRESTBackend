package ch.furthermore.demo.st.util;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.regions.Regions;

public class AwsUtil {
  
  private static Map<String, Regions> regionToEnumMap = new HashMap<String, Regions>();
  static {
    for (Regions r : Regions.values()) {
      regionToEnumMap.put(r.toString(), r);
      regionToEnumMap.put(r.getName(),  r);
    }
  }

  /**
   * look up region in map that has both 'names' and enum constant values so we don't have to figure out the conversion
   * @param name  the region name to lookup and return 
   * @return The <code>Regions</code> AWS enumeration
   */
  public static Regions lookupRegion (String name) {
    return regionToEnumMap.get(name);
  }
}
