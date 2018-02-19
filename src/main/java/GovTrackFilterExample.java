
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
        }
    }
}
