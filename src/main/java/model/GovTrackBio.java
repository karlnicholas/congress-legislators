package model;

import java.util.*;

/**
 * 
 * @author Karl Nicholas
 * 
 * Bean to hold the legislators Bio information
 *
 */
public class GovTrackBio extends GovTrackBase
{
    public static final String key = "bio";
    private String birthday;
    private String gender;
    private String religion;
    private static final String[] values = new String[] { 
    	"birthday", 
    	"gender",
    	"religion", 
    };
    
    public GovTrackBio() {
        super(GovTrackBio.values);
    }

	public GovTrackBio(Map<String, ?> map) {
        super(map, GovTrackBio.values);
    }
    
    public String getKey() {
        return "bio";
    }

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

}
