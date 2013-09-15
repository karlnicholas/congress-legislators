import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import model.GovTrackEntry;
import model.GovTrackTerm;

import com.esotericsoftware.yamlbeans.YamlReader;

import filter.GovTrackDateFilter;
import filter.GovTrackFilter;

/**
 * 
 * @author Karl Nicholas
 * 
 * This program will parse the GovTrack legislators file and export a CSV file.
 * The CSV file will contain the count of democrats and republicans for
 * each congressional session starting in 1960. The counts will be 
 * summed for each region of the US, as defined by the US Census Bureau.
 * The CSV file can be imported into Excel or SPSS.
 *   
 *
 */
public class ExportPartiesByRegion
{
    private static SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
    private static int START_YEAR = 1960;
    private static int END_YEAR = 2013;
    private static String OUTPUT_FILE = "reps.txt";
    private static String[] west = new String[] { "AK", "AZ", "CA", "CO", "HI", "ID", "MT", "NM", "NV", "OR", "UT", "WA", "WY" };;
    private static String[] south = new String[] { "AL", "AR", "DC", "DE", "FL", "GA", "KY", "LA", "MD", "MS", "NC", "OK", "SC", "TN", "TX", "VA", "WV" };;
    private static String[] ne = new String[] { "CT", "MA", "ME", "NH", "NJ", "NY", "PA", "RI", "VT" };;
    private static String[] mw = new String[] { "IA", "IL", "IN", "KS", "MI", "MN", "MO", "ND", "NE", "OH", "SD", "WI" };;
    
    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
    	// see if any commandline args. not tested.
        if (args.length == 3) {
            try {
                ExportPartiesByRegion.START_YEAR = Integer.parseInt(args[0]);
                ExportPartiesByRegion.END_YEAR = Integer.parseInt(args[1]);
                ExportPartiesByRegion.OUTPUT_FILE = args[2];
            }
            catch (Exception e) {
                System.out.println("Usage: java ExportPartiesByRegion START_YEAR END_YEAR OUTPUT_FILE");
                return;
            }
        }
        // create dates for the a GovTrackDateFilter
        Date filterStartDate = new GregorianCalendar(ExportPartiesByRegion.START_YEAR, 0, 3).getTime();
        Date filterEndDate = new GregorianCalendar(ExportPartiesByRegion.END_YEAR, 11, 31).getTime();
      
        // the both of the legislators files
        YamlReader reader = new YamlReader(new InputStreamReader(ExportPartiesByRegion.class.getResourceAsStream("legislators-historical.yaml")));
        ArrayList<Map<String, ?>> list = (ArrayList<Map<String, ?>>)reader.read();
        reader.close();
        reader = new YamlReader(new InputStreamReader(ExportPartiesByRegion.class.getResourceAsStream("legislators-current.yaml")));
        list.addAll( (ArrayList<Map<String, ?>>)reader.read() );
        reader.close();

        // create a filter from the start and end dates
        GovTrackFilter filter = new GovTrackDateFilter(filterStartDate, filterEndDate, null);
        
        // filter the original GovTrack files. This just gives less data to process later
        ArrayList<GovTrackEntry> listAcceptedEntries = new ArrayList<GovTrackEntry>();
        for (Map<String, ?> map : list) {
            filter.doFilter(listAcceptedEntries, new GovTrackEntry(map));
        }

        // strip out the GovTrackTerms from the filtered results. They have all that is needed
        ArrayList<GovTrackTerm> listTerms = new ArrayList<GovTrackTerm>();
        for (GovTrackEntry entry : listAcceptedEntries) {
            for (GovTrackTerm term : entry.getTerms()) {
                listTerms.add(term);
            }
        }

        // create a class to hold information for each congressional seat
        class Seat
        {
            String key;
            String year;
            String state;
            String party;
        }

        // use a TreeMap to hold the seats and eleminate duplicates
        TreeMap<String, Seat> mapSeats = new TreeMap<String, Seat>();
        
        // session and GovTrackTerm date ranges
        GregorianCalendar sessDateStart = new GregorianCalendar(ExportPartiesByRegion.START_YEAR, 0, 4);
        GregorianCalendar sessDateEnd = new GregorianCalendar();
        GregorianCalendar termStart = new GregorianCalendar();
        GregorianCalendar termEnd = new GregorianCalendar();

        // loop over congressional sessions
        while (sessDateStart.get(Calendar.YEAR) <= ExportPartiesByRegion.END_YEAR) {

        	// set the current session end date
        	sessDateEnd.setTime(sessDateStart.getTime());
            sessDateEnd.set(Calendar.MONTH, Calendar.DECEMBER);
            sessDateEnd.set(Calendar.DAY_OF_MONTH, 31);

            // loop over the list of accepted terms
            for (GovTrackTerm term2 : listTerms) {

            	// parse the term dates into java dates
            	termStart.setTime(ExportPartiesByRegion.dformat.parse(term2.getStart()));
                termEnd.setTime(ExportPartiesByRegion.dformat.parse(term2.getEnd()));

                // test to see if the congressional session and GovTrack term dates overlap
                if (termStart.compareTo(sessDateEnd) <= 0 && sessDateStart.compareTo(termEnd) <= 0) {
                	// get all the needed information from the current GovTrackTerm
                    String state = term2.getState();
                    String party = term2.getParty();
                    String type = term2.getType();
                    String district = term2.getDistrict();
                    String gClass = term2.getGclass();
                    String sYear = Integer.toString(sessDateStart.get(1));
                    Seat seat = new Seat();
                    // create a unique key for the seeat
                    if (term2.getType().equals("sen")) {
                        seat.key = "" + sYear + type + state + gClass;
                    }
                    else {
                        seat.key = "" + sYear + district + state;
                    }
                    // put the seat into the map
                    seat.year = sYear;
                    seat.state = state;
                    seat.party = party;
                    // overwriting any duplicates that may occur
                    mapSeats.put(seat.key, seat);
                }
            }
            // increments session date to the next year.
            sessDateStart.set(Calendar.YEAR, sessDateStart.get(Calendar.YEAR) + 1);
        }

        // create a class to count parties
        class Counts
        {
            int totalDem = 0;
            int totalRep = 0;
        }
        
        // create maps to hold the counts for each region by year
        TreeMap<Integer, Counts> mapW = new TreeMap<Integer, Counts>();
        TreeMap<Integer, Counts> mapS = new TreeMap<Integer, Counts>();
        TreeMap<Integer, Counts> mapNE = new TreeMap<Integer, Counts>();
        TreeMap<Integer, Counts> mapMW = new TreeMap<Integer, Counts>();
        TreeMap<Integer, Counts> mapOther = new TreeMap<Integer, Counts>();
        
        // loop over the list of seats
        for (String key : mapSeats.keySet()) {
            Seat seat2 = mapSeats.get(key);
            // determine region for this seat
            String region = getRegion(seat2.state);
            // assign the correct map
            TreeMap<Integer, Counts> mapRef;
            if (region.equals("West")) {
                mapRef = mapW;
            }
            else if (region.equals("South")) {
                mapRef = mapS;
            }
            else if (region.equals("Midwest")) {
                mapRef = mapMW;
            }
            else if (region.equals("Northeast")) {
                mapRef = mapNE;
            }
            else {
                mapRef = mapOther;
            }
            // create a session year key for map
            Integer iYear = Integer.valueOf(seat2.year);
            // get the count object if it exists or create a new one
            Counts Counts;
            if (mapRef.containsKey(iYear)) {
                Counts = mapRef.get(iYear);
            }
            else {
                Counts = new Counts();
            }
            // count dem's or republicans
            if (seat2.party.equals("Democrat")) {
                Counts counts = Counts;
                ++counts.totalDem;
            }
            else {
                Counts counts2 = Counts;
                ++counts2.totalRep;
            }
            // add/update count in map
            mapRef.put(iYear, Counts);
        }
        
        // write the results to a CSV file
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ExportPartiesByRegion.OUTPUT_FILE)));
        out.println("Year, NEDems, NEReps, SDems, SReps, MWDems, MWReps, WDems, WReps, ODems, OReps");
        
        for (Integer iYear2 : mapW.keySet()) {
            Counts CountsNE = mapNE.get(iYear2);
            Counts CountsS = mapS.get(iYear2);
            Counts CountsMW = mapMW.get(iYear2);
            Counts CountsW = mapW.get(iYear2);
            Counts CountsO = mapOther.get(iYear2);
            
            out.print("" + iYear2 + "," + CountsNE.totalDem + "," + CountsNE.totalRep + "," + CountsS.totalDem + "," + CountsS.totalRep + "," + CountsMW.totalDem + "," + CountsMW.totalRep + "," + CountsW.totalDem + "," + CountsW.totalRep + ",");
            
            if (CountsO != null) {
                out.println(CountsO.totalDem + "," + CountsO.totalRep);
            }
            else {
                out.println(",");
            }
        }
        out.close();
    }

    // utility routine to lookup region
    private static String getRegion(String state) {
        if (Arrays.binarySearch(ExportPartiesByRegion.west, state) >= 0) {
            return "West";
        }
        if (Arrays.binarySearch(ExportPartiesByRegion.south, state) >= 0) {
            return "South";
        }
        if (Arrays.binarySearch(ExportPartiesByRegion.ne, state) >= 0) {
            return "Northeast";
        }
        if (Arrays.binarySearch(ExportPartiesByRegion.mw, state) >= 0) {
            return "Midwest";
        }
        return "";
    }
    
}
