package model;

import java.util.*;

/**
 * 
 * @author Karl Nicholas
 * 
 * Bean to hold all of the other GovTrack beans
 *
 */
public class GovTrackEntry
{
    private GovTrackId govTrackId;
    private GovTrackName govTrackName;
    private ArrayList<GovTrackOtherName> listGovTrackOtherNames;
    private GovTrackBio govTrackBio;
    private ArrayList<GovTrackTerm> listGovTrackTerms;
    private ArrayList<GovTrackLeadershipRole> listGovTrackLeadershipRoles;
    
    public GovTrackEntry() {}
    
    @SuppressWarnings("unchecked")
	public GovTrackEntry(Map<String, ?> map) {
        govTrackId = new GovTrackId((Map<String, ?>)map.get(GovTrackId.key));
        govTrackName = new GovTrackName((Map<String, ?>)map.get("name"));
        ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>)map.get("other_names");
        if (list != null) {
            listGovTrackOtherNames = new ArrayList<GovTrackOtherName>();
            for (Map<String, String> mapChild : list) {
                listGovTrackOtherNames.add(new GovTrackOtherName(mapChild));
            }
        }
        govTrackBio = new GovTrackBio((Map<String, ?>)map.get(GovTrackBio.key));
        list = (ArrayList<Map<String, String>>)map.get("terms");
        if (list != null) {
            listGovTrackTerms = new ArrayList<GovTrackTerm>();
            for (Map<String, String> mapChild : list) {
                listGovTrackTerms.add(new GovTrackTerm(mapChild));
            }
        }
        list = (ArrayList<Map<String, String>>)map.get("leadership_roles");
        if (list != null) {
            listGovTrackLeadershipRoles = new ArrayList<GovTrackLeadershipRole>();
            for (Map<String, String> mapChild : list) {
                listGovTrackLeadershipRoles.add(new GovTrackLeadershipRole(mapChild));
            }
        }
    }
    
    public String toString() {
        String ret = new String();
        ret = ret + govTrackId.toString() + "\n";
        ret = ret + govTrackName.toString() + "\n";
        if ( listGovTrackOtherNames != null ) {
	        for (GovTrackOtherName govTrackOtherName : listGovTrackOtherNames) {
	            ret = ret + govTrackOtherName.toString() + "\n";
	        }
        }
        ret = ret + govTrackBio.toString() + "\n";
        if ( listGovTrackTerms != null ) {
	        for (GovTrackTerm govTrackTerm : listGovTrackTerms) {
	            ret = ret + govTrackTerm.toString() + "\n";
	        }
        }
        if ( listGovTrackLeadershipRoles != null ) {
	        for (GovTrackLeadershipRole govTrackLeadershipRole : listGovTrackLeadershipRoles) {
	            ret = ret + govTrackLeadershipRole.toString() + "\n";
	        }
        }
        return ret;
    }

    public boolean compareField(String search, String value) {
    	String[] split = search.split("\\.");
    	if ( split[0].equalsIgnoreCase(GovTrackId.key)) {
    		return govTrackId.compareField(split[1], value);
    	} else if (split[0].equalsIgnoreCase(GovTrackName.key) ) {
    		return govTrackName.compareField(split[1], value);
    	} else if (split[0].equalsIgnoreCase(GovTrackOtherName.key) ) {
    		if ( listGovTrackOtherNames != null ) {
	    		for ( GovTrackOtherName govTrackOtherName: listGovTrackOtherNames ) {
	    			if ( govTrackOtherName.compareField(split[1], value )) return true;
	    		}
    		}
    	} else if (split[0].equalsIgnoreCase(GovTrackBio.key) ) {
    		return govTrackBio.compareField(split[1], value);
    	} else if (split[0].equalsIgnoreCase(GovTrackTerm.key) ) {
    		if ( listGovTrackTerms != null ) {
	    		for ( GovTrackTerm govTrackTerm: listGovTrackTerms ) {
	    			if ( govTrackTerm.compareField(split[1], value )) return true;
	    		}
    		}
    	} else if (split[0].equalsIgnoreCase(GovTrackLeadershipRole.key) ) {
    		if ( listGovTrackLeadershipRoles != null ) {
	    		for ( GovTrackLeadershipRole govTrackLeadershipRole: listGovTrackLeadershipRoles ) {
	    			if ( govTrackLeadershipRole.compareField(split[1], value )) return true;
	    		}
    		}
    	}
        return false;
    }

    public GovTrackId getGovTrackId() {
		return govTrackId;
	}

	public void setGovTrackId(GovTrackId govTrackId) {
		this.govTrackId = govTrackId;
	}

	public GovTrackName getGovTrackName() {
		return govTrackName;
	}

	public void setGovTrackName(GovTrackName govTrackName) {
		this.govTrackName = govTrackName;
	}

	public ArrayList<GovTrackOtherName> getListGovTrackOtherNames() {
		return listGovTrackOtherNames;
	}

	public void setListGovTrackOtherNames(ArrayList<GovTrackOtherName> listGovTrackOtherNames) {
		this.listGovTrackOtherNames = listGovTrackOtherNames;
	}

	public GovTrackBio getGovTrackBio() {
		return govTrackBio;
	}

	public void setGovTrackBio(GovTrackBio govTrackBio) {
		this.govTrackBio = govTrackBio;
	}

	public ArrayList<GovTrackTerm> getListGovTrackTerms() {
		return listGovTrackTerms;
	}

	public void setListGovTrackTerms(ArrayList<GovTrackTerm> listGovTrackTerms) {
		this.listGovTrackTerms = listGovTrackTerms;
	}

	public ArrayList<GovTrackLeadershipRole> getListGovTrackLeadershipRoles() {
		return listGovTrackLeadershipRoles;
	}

	public void setListGovTrackLeadershipRoles(ArrayList<GovTrackLeadershipRole> listGovTrackLeadershipRoles) {
		this.listGovTrackLeadershipRoles = listGovTrackLeadershipRoles;
	}

}
