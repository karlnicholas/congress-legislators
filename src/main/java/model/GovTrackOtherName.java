package model;

import java.util.*;

/**
 * 
 * @author Karl Nicholas
 * 
 * A bean to hold all of the GovTrack other_names fields
 *
 */
public class GovTrackOtherName extends GovTrackBase
{
    static final String key = "other_names";
    private String last;
    private String middle;
    private String end;
    private static final String[] values = new String[] { 
    	"last", 
    	"middle", 
    	"end",
    };

    public GovTrackOtherName() {
        super(GovTrackOtherName.values);
    }
    
    public GovTrackOtherName(Map<String, ?> map) {
        super(map, GovTrackOtherName.values);
    }
    
    public String getKey() {
        return "other_names";
    }
    
    public String getLast() {
        return this.last;
    }
    
    public void setLast(final String last) {
        this.last = last;
    }
    
    public String getMiddle() {
        return this.middle;
    }
    
    public void setMiddle(final String middle) {
        this.middle = middle;
    }
    
    public String getEnd() {
        return this.end;
    }
    
    public void setEnd(final String end) {
        this.end = end;
    }
    
}
