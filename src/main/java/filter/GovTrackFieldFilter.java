package filter;

import java.util.ArrayList;

import model.GovTrackEntry;

/**
 * 
 * @author Karl Nicholas
 * 
 * A filter that implements a generic field filtering
 * logic. the first parameter is a field designator in the
 * form of key.fieldname. The field is found and then compared 
 * against the value of the field.  
 *
 */
public class GovTrackFieldFilter implements GovTrackFilter 
{
	private final GovTrackFilter chain;
	private final String search;
	private final String value;
    
    public GovTrackFieldFilter(String search, String value, GovTrackFilter chain) {
        this.search = search;
        this.value = value;
        this.chain = chain;
    }
    
    public void doFilter(ArrayList<GovTrackEntry> listAcceptedEntries, GovTrackEntry currentEntry) {
        if ( currentEntry.compareField(search, value )) {
            listAcceptedEntries.add(currentEntry);
        }
        if (chain != null) {
            chain.doFilter(listAcceptedEntries, currentEntry);
        }
    }

}
