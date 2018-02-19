
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
import java.util.function.Function;
import java.util.stream.IntStream;

import model.Term;

public class ExportPartiesByRegion {
    private static String OUTPUT_FILE = "reps.txt";
    String[] regions = {"Northeast", "South", "Midwest", "West" };
    
    private static String[][] stateByRegion = {{ "CT", "MA", "ME", "NH", "NJ", "NY", "PA", "RI", "VT" }, 
        { "AL", "AR", "DC", "DE", "FL", "GA", "KY", "LA", "MD", "MS", "NC", "OK", "SC", "TN", "TX", "VA", "WV" }, 
        { "AK", "AZ", "CA", "CO", "HI", "ID", "MT", "NM", "NV", "OR", "UT", "WA", "WY" }, 
        { "IA", "IL", "IN", "KS", "MI", "MN", "MO", "ND", "NE", "OH", "SD", "WI"} };
    
    public static void main(String[] args) throws Exception {
        new ExportPartiesByRegion().run(1960);
    }
    // create a class to hold information for each congressional seat

    private void run(final int from) throws Exception {
        // create a class to hold legislative seats
        class Seat {
            String key;
            String year;
            String state;
            String party;
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
        class Counts { int totalDem = 0; int totalRep = 0;

            public void add(Counts c) {totalDem += c.totalDem; totalRep+=c.totalRep;} 
        }

        // session and GovTrackTerm date ranges

        final LocalDate termDateStart = LocalDate.of(from, 1, 1);
        final LocalDate now = LocalDate.of(2014, 1, 1);

        try (GovTrackRecords govTrackRecords = new GovTrackRecords(
                GovTrackFilterExample.class.getResourceAsStream("/legislators-historical.yaml"),
                GovTrackFilterExample.class.getResourceAsStream("/legislators-current.yaml"))) {
            
            List<Term> terms = govTrackRecords.stream().flatMap(r -> r.getTerms().stream())
                .filter(t -> termDateStart.isBefore(t.getEnd()) && t.getStart().isBefore(now))
                .collect(toList());

            // use a Map to hold the Seats
            Map<String, Seat> mapSeats = new HashMap<String, Seat>();
            
            IntStream.range(from+1, 2014)
            .mapToObj(y->LocalDate.of(y,1,1))
            .forEach(sessDate->{
                // run through the list of accepted terms
                terms.parallelStream()
                .filter(term->term.getStart().isBefore(sessDate) && sessDate.isBefore(term.getEnd()))
                .map(term->{ return new Seat(term, sessDate.getYear()-1);})
                .forEach(seat->mapSeats.put(seat.key, seat));
            });

            final Function<String, String> getRegion = state-> {
                for ( int i=0; i < stateByRegion.length; ++i ) {
                    if (Arrays.binarySearch(stateByRegion[i], state) >= 0) {
                        return regions[i];
                    }
                }
                return "";
            };

            Map<String, Map<Integer, Counts>> regionByYearByCount = mapSeats.values().stream()
            .collect(toMap(
                    seat->{
                        return getRegion.apply(seat.state);                        
                    }, seat->{
                        // start a map
                        Map<Integer, Counts> map = new TreeMap<>();
                        Counts counts = new Counts();
                        if (seat.party.equals("Democrat")) {
                            ++counts.totalDem;
                        } else {
                            ++counts.totalRep;
                        }
                        map.put(Integer.valueOf(seat.year), counts );
                        return map;
                    }, (t, u)->{
                        // merge two Map<Integer, Counts> maps
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
                    },
                    // supply new TreeMap
                    TreeMap::new
                    ));            

            // write the results to a CSV file
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ExportPartiesByRegion.OUTPUT_FILE)));
            out.println("Year, NEDems, NEReps, SDems, SReps, MWDems, MWReps, WDems, WReps, ODems, OReps");

            IntStream.range(from, 2013).forEach(year->{
                Counts CountsNE = regionByYearByCount.get(regions[0]).get(year);
                Counts CountsS = regionByYearByCount.get(regions[1]).get(year);
                Counts CountsMW = regionByYearByCount.get(regions[2]).get(year);
                Counts CountsW = regionByYearByCount.get(regions[3]).get(year);
                Map<Integer, Counts> map = regionByYearByCount.get("");                

                out.print("" + year + "," + CountsNE.totalDem + "," + CountsNE.totalRep + "," + CountsS.totalDem + ","
                        + CountsS.totalRep + "," + CountsMW.totalDem + "," + CountsMW.totalRep + "," + CountsW.totalDem
                        + "," + CountsW.totalRep + ",");

                if (map != null) {
                    Counts countsO = map.get(year);
                    if (countsO != null ) {
                        out.println(countsO.totalDem + "," + countsO.totalRep);
                    } else {
                        out.println(",");
                    }
                }
            });
            out.close();
        }

    }

}
