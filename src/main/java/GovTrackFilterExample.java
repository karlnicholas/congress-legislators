
/**
 * 
 * This program does the basic minimal processing of the legislators 
 * files. It prints the count or all congressional members with the 
 * last name of "brown" There is code at the end that 
 * prints the result in yaml format, but it is not the same
 * as the original legislators yaml format.  
 *
 * @author Karl Nicholas
 * 
 */
import static java.util.stream.Collectors.*;

import java.util.List;

import model.Record;

public class GovTrackFilterExample {
    public static void main(String[] args) throws Exception {
        new GovTrackFilterExample().run();
    }

    private void run() throws Exception {
        try (GovTrackRecords govTrackRecords = new GovTrackRecords(
                GovTrackFilterExample.class.getResourceAsStream("/legislators-historical.yaml"),
                GovTrackFilterExample.class.getResourceAsStream("/legislators-current.yaml"))
        ) {
            List<Record> browns = govTrackRecords.stream()
                .filter(r -> r.getName().getLast().equalsIgnoreCase("brown"))
                .collect(toList());
            System.out.println("Count: " + browns.size());
            // optional print records to console if wanted
            // govTrackRecords.getObjectMapper().writer().writeValue(System.out, browns);
        }
    }
}
