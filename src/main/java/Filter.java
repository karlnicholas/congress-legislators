import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import model.GovTrackEntry;

import com.esotericsoftware.yamlbeans.YamlReader;

import filter.GovTrackFieldFilter;
import filter.GovTrackFilter;

/**
 * 
 * @author Karl Nicholas
 * 
 * This program will filter the legislators files. The result is 
 * printed to standard out. The GovTrackFieldFilter takes two
 * parameters. The first is the field designator, which is in the 
 * form of GovTrack "key.fieldname" 
 * (e.g., id.govtrack, terms.state, name.last ). The fieldnames
 * start and end are handled specially in that that are only tested
 * against a 4 digit year (2013, 1990, etc). In this program
 * each filter is applied to the results of the last filter
 * giving a AND result. Each filter removes from the list 
 * of GovTrackEntries.   
 * 
 * Nothing more than a test update.
 *
 */
public class Filter {
    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

    	// setup test filters
    	ArrayList<GovTrackFilter> listFilters = new ArrayList<GovTrackFilter>(); 
    	GovTrackFilter filter = new GovTrackFieldFilter("terms.type", "sen", null);
    	listFilters.add(filter);
    	filter = new GovTrackFieldFilter("terms.start", "2013", null);
    	listFilters.add(filter);

    	// see if there is any command line input
        if (args.length > 0) {
        	listFilters.clear();
            try {
                GovTrackFilter oldFilter = null;
                for ( int i=0; i <args.length; i=i+2 ) {
                    filter = new GovTrackFieldFilter(args[i], args[i+1], oldFilter);
                    listFilters.add(filter);
                    oldFilter = filter; 
                }
            }
            catch (Exception e) {
                System.out.println("Usage: java -cp legislators.jar Filter terms.type sen terms.start 2013 [designator value] ...");
                return;
            }
        }

        // read both of the legisltors files into memory
        YamlReader reader = new YamlReader(new InputStreamReader(GovTrackFilterExample.class.getResourceAsStream("legislators-historical.yaml")));
        ArrayList<Map<String, ?>> list = (ArrayList<Map<String, ?>>)reader.read();
        reader.close();
        reader = new YamlReader(new InputStreamReader(GovTrackFilterExample.class.getResourceAsStream("legislators-current.yaml")));
        list.addAll( (ArrayList<Map<String, ?>>)reader.read() );
        reader.close();

        // setup processing arrary
        ArrayList<GovTrackEntry> listAcceptedEntries = new ArrayList<GovTrackEntry>();
        ArrayList<GovTrackEntry> currentEntries = null;

        // filter each filter sequentially 
        for ( GovTrackFilter govTrackFilter: listFilters ) {
        	if ( currentEntries == null ) {
	            for (Map<String, ?> map : list) {
	            	govTrackFilter.doFilter(listAcceptedEntries, new GovTrackEntry(map));
	            }
	            currentEntries = (ArrayList<GovTrackEntry>) listAcceptedEntries.clone();
        	} else {
        		listAcceptedEntries.clear();
	            for (GovTrackEntry govTrackEntry: currentEntries ) {
	            	govTrackFilter.doFilter(listAcceptedEntries, govTrackEntry);
	            }
	            currentEntries = (ArrayList<GovTrackEntry>) listAcceptedEntries.clone();
        	}
        }

        // print out the results
        for (GovTrackEntry govTrackEntry : listAcceptedEntries) {
        	System.out.println(govTrackEntry);
        }
    }

}
