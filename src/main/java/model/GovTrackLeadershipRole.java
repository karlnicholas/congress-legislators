package model;

import java.util.*;

/**
 * 
 * @author Karl Nicholas
 * 
 * A bean to hold all of the GovTrack leadership_roles
 * fields
 *
 */
public class GovTrackLeadershipRole extends GovTrackBase
{
    public static final String key = "leadership_roles";
    private String title;
    private String chamber;
    private String start;
    private String end;
    private static final String[] values = new String[] { 
    	"title", 
    	"chamber",
    	"start", 
    	"end", 
    };
    
    public GovTrackLeadershipRole() {
        super(GovTrackLeadershipRole.values);
    }

	public GovTrackLeadershipRole(Map<String, ?> map) {
        super(map, GovTrackLeadershipRole.values);
    }
    
    public String getKey() {
        return "leadership_roles";
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}
