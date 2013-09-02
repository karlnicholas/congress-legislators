import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlReader;

/**
 * 
 * @author Karl Nicholas
 * 
 * This program traverses the congress-legislators files and finds all unique fields
 * and dumps them to Stdout. It doesn't handle fec and bioguide_previous very well, 
 * but the are ArrayList's of Strings. I had a version that did so but accidently
 * deleted it. Oh well. The output was used to create the Java Beans. 
 *
 */
public class ShowGovTrackHierarchy
{
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {
        YamlReader reader = new YamlReader(new InputStreamReader(ShowGovTrackHierarchy.class.getResourceAsStream("/legislators-historical.yaml")));
        ArrayList<Map<String, ?>> list = (ArrayList<Map<String, ?>>)reader.read();
        reader.close();
        reader = new YamlReader(new InputStreamReader(ShowGovTrackHierarchy.class.getResourceAsStream("/legislators-current.yaml")));
        list.addAll(  (ArrayList<Map<String, ?>>)reader.read() );
        reader.close();

        System.out.println("Size of 1st level ArrayList of Maps:" + list.size());
        Map<String, Object>mapEntry = new HashMap();

        for (Map<String, ?> map : list) {
            for (String key : map.keySet()) {
                Object o1 = map.get(key);

                if (o1 instanceof List) {
                    ArrayList<Map<String, String>> list2 = (ArrayList<Map<String, String>>)o1;
                    Map<String, String> mapOfKeys = new HashMap<String, String>();
                    for (Map<String, String> mapOfList : list2) {
                        for (String mapKey : mapOfList.keySet()) {
                            if (!mapOfKeys.containsKey(mapKey)) {
                                mapOfKeys.put(mapKey, "");
                            }
                        }
                    }
                    if (!mapEntry.containsKey(key)) {
                        ArrayList<Map<String, String>> listChild = new ArrayList<Map<String, String>>();
                        listChild.add(mapOfKeys);
                        mapEntry.put(key, (Object)listChild);
                    }
                    else {
                        ArrayList<Map<String, String>> listChild = (ArrayList<Map<String, String>>)mapEntry.get(key);
                        Map<String, String> mapOfOldKeys = listChild.get(0);
                        for (String oldKey : mapOfOldKeys.keySet()) {
                            if (mapOfKeys.containsKey(oldKey)) {
                                mapOfKeys.remove(oldKey);
                            }
                        }
                        for (String newKey : mapOfKeys.keySet()) {
                            mapOfOldKeys.put(newKey, "");
                        }
                    }
                } else if (o1 instanceof Map) {
                	
                    Map<String, String> map2 = (Map<String, String>)o1;
                    Map<String, String> mapOfKeys = new HashMap<String, String>();
                    for (String mapKey2 : map2.keySet()) {
                        if (!mapOfKeys.containsKey(mapKey2)) {
                            mapOfKeys.put(mapKey2, "");
                        }
                    }
                    if (!mapEntry.containsKey(key)) {
                        mapEntry.put(key, mapOfKeys);
                    }
                    else {
                        Map<String, String> mapOldChild = (Map<String, String>)mapEntry.get(key);
                        for (String oldKey2 : mapOldChild.keySet()) {
                            if (mapOfKeys.containsKey(oldKey2)) {
                                mapOfKeys.remove(oldKey2);
                            }
                        }
                        for (String newKey2 : mapOfKeys.keySet()) {
                            mapOldChild.put(newKey2, "");
                        }
                    }
                }
            }
        }
        for (String key2 : mapEntry.keySet()) {
            Object o2 = mapEntry.get(key2);
            System.out.println(key2 + "=" + o2.toString());
        }
    }
}
