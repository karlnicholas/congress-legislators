package model;

import java.util.*;

/**
 * 
 * @author Karl Nicholas
 * 
 * A bean to hold all of the GovTrack terms fields
 *
 */
public class GovTrackTerm extends GovTrackBase
{
    static final String key = "terms";
    private String type;
    private String start;
    private String end;
    private String state;
    private String district;
    private String gclass;
    private String state_rank;
    private String party;
    private String url;
    private String address;
    private String phone;
    private String fax;
    private String contact_form;
    private String office;
    private String rss_url;
    private static final String[] values = new String[] { 
    	"type", 
    	"start", 
    	"end", 
    	"state", 
    	"district", 
    	"class", 
    	"state_rank", 
    	"party", 
    	"url", 
    	"address", 
    	"phone", 
    	"fax", 
    	"contact_form", 
    	"office", 
    	"rss_url", 
	};
    
    public GovTrackTerm() {
        super(GovTrackTerm.values);
    }
    
    public GovTrackTerm(Map<String, ?> map) {
        super(map, GovTrackTerm.values);
    }
    
    public String getKey() {
        return "terms";
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getGclass() {
		return gclass;
	}

	public void setGclass(String gclass) {
		this.gclass = gclass;
	}

	public String getState_rank() {
		return state_rank;
	}

	public void setState_rank(String state_rank) {
		this.state_rank = state_rank;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContact_form() {
		return contact_form;
	}

	public void setContact_form(String contact_form) {
		this.contact_form = contact_form;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getRss_url() {
		return rss_url;
	}

	public void setRss_url(String rss_url) {
		this.rss_url = rss_url;
	}

}
