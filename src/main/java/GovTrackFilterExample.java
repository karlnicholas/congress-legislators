import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

import model.*;

import com.esotericsoftware.yamlbeans.*;

import filter.*;

/**
 * 
 * @author Karl Nicholas
 * 
 * This program does the basic minimal processing of the legislators 
 * files. It dumps out all congressional members with the 
 * last name of "brown" There is code at the end that 
 * prints the result in yaml format, but it is not the same
 * as the original legislators yaml format.  
 *
 */
public class GovTrackFilterExample
{
    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

    	// create a filter
    	GovTrackFilter filter = new GovTrackFieldFilter("name.last", "brown", null);

    	// check for command line input
        if (args.length > 0) {
            try {
                GovTrackFilter oldFilter = null;
                for (String aname : args) {
                    filter = (oldFilter = new GovTrackFieldFilter("name.last", aname, oldFilter));
                }
            }
            catch (Exception e) {
                System.out.println("Usage: java -cp legislators.jar GovTrackFilterExample lastname lastname lastname ...");
                return;
           }
        }

        // read the legislators files into memory
        YamlReader reader = new YamlReader(new InputStreamReader(GovTrackFilterExample.class.getResourceAsStream("legislators-current.yaml")));
        ArrayList<Map<String, ?>> list = (ArrayList<Map<String, ?>>)reader.read();
        reader.close();
//        reader = new YamlReader(new InputStreamReader(GovTrackFilterExample.class.getResourceAsStream("legislators-current.yaml")));
//        list.addAll( (ArrayList<Map<String, ?>>)reader.read() );
//        reader.close();

        // create a result list
        ArrayList<GovTrackEntry> listAcceptedEntries= new ArrayList<>();

        // filter the legislators data
        for (Map<String, ?> map : list) {
            filter.doFilter(listAcceptedEntries, new GovTrackEntry(map));
        }

/*        
        // print out each entry separately. 
        // somewhat cleaner than System.out.println(listAcceptedEntries);
        for ( GovTrackEntry govTrackEntry: listAcceptedEntries ) {
        	System.out.println(govTrackEntry);
        }
*/
        /*
        YamlReader reader = new YamlReader(new InputStreamReader(GovTrackFilterExample.class.getResourceAsStream("test.yaml")));
        ArrayList<GovTrackEntry> listAcceptedEntries = (ArrayList<GovTrackEntry>) reader.read();
        reader.close();
*/
        StringWriter w = new StringWriter();
    	YamlWriter writer = new YamlWriter(w);
    	writer.getConfig().setPropertyElementType(GovTrackEntry.class, "terms", GovTrackTerm.class);
    	writer.getConfig().setPropertyElementType(GovTrackEntry.class, "other_names", GovTrackOtherName.class);
    	writer.getConfig().setPropertyElementType(GovTrackEntry.class, "leadership_roles", GovTrackLeadershipRole.class);
    	writer.write( listAcceptedEntries );
    	writer.close();
    	System.out.println(w);
    	
    }
}
