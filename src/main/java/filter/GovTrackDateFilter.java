package filter;

import java.text.*;
import model.*;
import java.util.*;

/**
 * 
 * @author Karl Nicholas
 * 
 * A filter that checks to see if the defined
 * date range overlaps with a GovTrackEntry's term start and
 * end date range.
 *
 */
public class GovTrackDateFilter implements GovTrackFilter
{
    private final Date startDate;
    private final Date endDate;
    private final GovTrackFilter chain;
    private static final SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");;
    
    public GovTrackDateFilter(final Date startDate, final Date endDate, final GovTrackFilter chain) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.chain = chain;
    }
    
    public void doFilter(final ArrayList<GovTrackEntry> listAcceptedEntries, final GovTrackEntry currentEntry) {
        for (GovTrackTerm term : currentEntry.getTerms()) {
            try {
                Date termStart = GovTrackDateFilter.dformat.parse(term.getStart());
                Date termEnd = GovTrackDateFilter.dformat.parse(term.getEnd());
                if (startDate.compareTo(termEnd) <= 0 && termStart.compareTo(endDate) <= 0) {
                    listAcceptedEntries.add(currentEntry);
                    break;
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (chain != null) {
            chain.doFilter(listAcceptedEntries, currentEntry);
        }
    }
    
}
