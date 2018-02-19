
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.Record;

/**
 * 
 * Read the legislative historical and current files and save them.
 * Convert to streams for processing.
 * 
 * @author Karl Nicholas
 *
 */
public class GovTrackRecords implements AutoCloseable {
    private List<Record> records;
    private ObjectMapper objectMapper;
    private TypeReference<List<Record>> typeReference; 
    InputStream[] legislatorStreams;

    public GovTrackRecords(InputStream... legislatorStreams ) throws JsonParseException, JsonMappingException, IOException {
        this.legislatorStreams = legislatorStreams;
        records = new ArrayList<>();
        objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.registerModule(new JavaTimeModule());
        typeReference = new TypeReference<List<Record>>(){};
        for ( InputStream inputStream: legislatorStreams ) {
            records.addAll( objectMapper.readValue(inputStream, typeReference) );
        }
    }
    public void setFailOnUnknownProperties(boolean fail) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, fail);
    }
    
    public Stream<Record> stream() {
        return records.stream();
    }
    
    public Stream<Record> parallelStream() {
        return records.parallelStream();
    }
    @Override
    public void close() throws Exception {
        for ( InputStream inputStream: legislatorStreams ) {
            inputStream.close();
        }
    }
    ObjectMapper getObjectMapper( ) {
        return objectMapper;
    }
}
