package tieorange.com.pjabuffet.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by root on 1/5/17.
 */

public class MapToJsonConverter {

  /*
   * @Description: Method to convert Map to JSON String
   * @param: map Map<String, String>
   * @return: json String
   */
  public static String convert(Map<String, String> map) {
    Gson gson = new Gson();
    String json = gson.toJson(map);
    return json;
  }

  /*
   * @Description: Method to convert JSON String to Map
   * @param: json String
   * @return: map Map<String, String>
   */
  public static Map<String, String> revert(String json) {
    Gson gson = new Gson();
    Type type = new TypeToken<Map<String, String>>() {
    }.getType();
    Map<String, String> map = gson.fromJson(json, type);
    return map;
  }

  /*
   * @Description: Method to print elements in the Map
   * @param: map Map<String, String>
   * @return: void
   */
  public static void printMap(Map<String, String> map) {
    for (String key : map.keySet()) {
      System.out.println("map.get(\"" + key + "\") = " + map.get(key));
    }
  }

  /*
   * @Description: Method to print the JSON String
   * @param: json String
   * @return: void
   */
  public static void printJson(String json) {
    System.out.println("json = " + json);
  }
}