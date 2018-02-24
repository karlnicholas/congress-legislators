
import static java.util.stream.Collectors.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.IntStream;

import model.Term;

/**
 * 
 * This program will parse the GovTrack legislators file and export a CSV file.
 * The CSV file will contain the count of democrats and republicans for
 * each congressional session starting in 1960. The counts will be 
 * summed for each region of the US, as defined by the US Census Bureau.
 * The CSV file can be imported into Excel or SPSS.
 *
 * @author Karl Nicholas
 * 
 */
public class ExportPartiesByRegion {
    private static String OUTPUT_FILE = "reps.txt";
    // region names
    String[] regions = {"Northeast", "South", "Midwest", "West", "Other" };
    // states in each region must be in alphabetical order
    private static String[][] stateByRegion = {{ "CT", "MA", "ME", "NH", "NJ", "NY", "PA", "RI", "VT" }, 
        { "AL", "AR", "DC", "DE", "FL", "GA", "KY", "LA", "MD", "MS", "NC", "OK", "SC", "TN", "TX", "VA", "WV" }, 
        { "IA", "IL", "IN", "KS", "MI", "MN", "MO", "ND", "NE", "OH", "SD", "WI"}, 
        { "AK", "AZ", "CA", "CO", "HI", "ID", "MT", "NM", "NV", "OR", "UT", "WA", "WY" } };
    
    // create a class to hold information for each congressional seat
    private final class Seat {
        String key, year, state, party;
        Seat(Term term, int iYear) {
            // get all the needed information from the current GovTrackTerm
            state = term.getState();
            party = term.getParty();
            year = Integer.toString(iYear);
            String type = term.getType();
            // create a unique key for the seat
            if (type.equals("sen")) {
                key = new StringBuilder(32).append(year).append(type).append(state).append(term.getClassField()).toString();
            } else {
                key = new StringBuilder(32).append(year).append(term.getDistrict()).append(state).toString();
            }
            // put the seat into the map
        }
    }
    // create a class to count parties
    private final class Counts { 
        int totalDem = 0, totalRep = 0, totalOther = 0;
        public Counts(String party) {
            if ( party != null ) {
                if (party.equals("Democrat")) {
                    ++totalDem;
                } else if (party.equals("Republican")) {
                    ++totalRep;
                } else {
                    ++totalOther;
                }
            } else {
                ++totalOther;
            }
        }
        public void add(Counts c) {
            totalDem += c.totalDem; 
            totalRep += c.totalRep;
            totalOther += c.totalOther;
        } 
    }
    // function to find the region for a seat 
    final Function<Seat, String> getRegion = seat-> {
        for ( int i=0; i < stateByRegion.length; ++i ) {
            if (Arrays.binarySearch(stateByRegion[i], seat.state) >= 0) {
                return regions[i];
            }
        }
        // if not found return "Other" for territories 
        return regions[4];
    };
    // function to start a map of year by counts
    final Function<Seat, Map<Integer, Counts>> mapYearByCounts = seat-> {
        Map<Integer, Counts> map = new TreeMap<>();
        map.put(Integer.valueOf(seat.year), new Counts(seat.party));
        return map;
    };
    // operator to merge two Map<Integer, Counts> maps 
    final BinaryOperator<Map<Integer, Counts>> mergeYearByCountsMaps = (t,u) -> {
        for ( Integer year: t.keySet() ) {
            Counts c = u.get(year);
            if ( c != null ) {
                t.get(year).add(c);
            }
        }
        for ( Integer year: u.keySet() ) {
            Counts c = t.get(year);
            if ( c == null ) {
                t.put(year, u.get(year));
            }
        }
        return t;
    };
    
    public static void main(String[] args) throws Exception {
        new ExportPartiesByRegion().run(1960, 2017);
    }

    private void run(final int from, final int to) throws Exception {
        // session and GovTrackTerm date ranges

        final LocalDate termDateStart = LocalDate.of(from, 1, 1);
        final LocalDate termDateEnd = LocalDate.of(to+1, 1, 1);

        try (GovTrackRecords govTrackRecords = new GovTrackRecords(
                GovTrackFilterExample.class.getResourceAsStream("/legislators-historical.yaml"),
                GovTrackFilterExample.class.getResourceAsStream("/legislators-current.yaml"))) {
            
            List<Term> terms = govTrackRecords.stream().flatMap(r -> r.getTerms().stream())
                .filter(t -> termDateStart.isBefore(t.getEnd()) && t.getStart().isBefore(termDateEnd))
                .collect(toList());

            // use a Map to hold the Seats
            Map<String, Seat> mapSeats = new HashMap<String, Seat>();
            
            // find all terms that fall within date range
            IntStream.range(from+1, to+2)
            .mapToObj(y->LocalDate.of(y,1,1))
            .forEach(sessDate->{
                // run through the list of accepted terms
                terms.stream()
                .filter(term->term.getStart().isBefore(sessDate) && sessDate.isBefore(term.getEnd()))
                .map(term->{ return new Seat(term, sessDate.getYear()-1);})
                .forEach(seat->mapSeats.put(seat.key, seat));
            });

            // create a map that holds counts for each year for each region
            Map<String, Map<Integer, Counts>> groupRegionByYearByCounts = mapSeats.values().stream()
            .collect( toMap(getRegion, mapYearByCounts, mergeYearByCountsMaps, TreeMap::new) );            

            // write the results to a CSV file
            try ( PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ExportPartiesByRegion.OUTPUT_FILE))) ) {
                out.println("Year, NEDems, NEReps, SDems, SReps, MWDems, MWReps, WDems, WReps, ODems, OReps");
    
                for ( int year=from; year<=to; ++year) {
                    // for each year get region totals
                    Map<Integer, Counts> yearMap = groupRegionByYearByCounts.get(regions[0]);
                    Counts CountsNE = 
                        yearMap == null ? new Counts(null) : yearMap.get(year) == null ? new Counts(null) : yearMap.get(year);
                    yearMap = groupRegionByYearByCounts.get(regions[1]);
                    Counts CountsS = 
                        yearMap == null ? new Counts(null) : yearMap.get(year) == null ? new Counts(null) : yearMap.get(year);
                    yearMap = groupRegionByYearByCounts.get(regions[2]);
                    Counts CountsMW = 
                        yearMap == null ? new Counts(null) : yearMap.get(year) == null ? new Counts(null) : yearMap.get(year);
                    yearMap = groupRegionByYearByCounts.get(regions[3]);
                    Counts CountsW = 
                        yearMap == null ? new Counts(null) : yearMap.get(year) == null ? new Counts(null) : yearMap.get(year);
                    yearMap = groupRegionByYearByCounts.get(regions[4]);
                    Counts countsO = 
                        yearMap == null ? new Counts(null) : yearMap.get(year) == null ? new Counts(null) : yearMap.get(year);
    
                    out.println("" + year + "," 
                        + CountsNE.totalDem + "," + CountsNE.totalRep + "," 
                        + CountsS.totalDem + "," + CountsS.totalRep + "," 
                        + CountsMW.totalDem + "," + CountsMW.totalRep + "," 
                        + CountsW.totalDem + "," + CountsW.totalRep + "," 
                        + countsO.totalDem + "," + countsO.totalRep );
                };
                // close file
                out.close();
            }
        }
    }

}
