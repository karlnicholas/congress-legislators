package model;

import java.util.*;

/**
 * 
 * @author Karl Nicholas
 * 
 *         Bean to hold all of the other GovTrack beans
 *
 */
public class Record {
    private Id id;
    private Name name;
    private List<OtherName> listGovTrackOtherNames;
    private Bio bio;
    private List<Term> terms;
    private List<LeadershipRole> leadership_roles;
    private List<FamilyMember> family;
    private List<OtherName> other_names;
    public Id getId() {
        return id;
    }
    public void setId(Id id) {
        this.id = id;
    }
    public Name getName() {
        return name;
    }
    public void setName(Name name) {
        this.name = name;
    }
    public List<OtherName> getListGovTrackOtherNames() {
        return listGovTrackOtherNames;
    }
    public void setListGovTrackOtherNames(List<OtherName> listGovTrackOtherNames) {
        this.listGovTrackOtherNames = listGovTrackOtherNames;
    }
    public Bio getBio() {
        return bio;
    }
    public void setBio(Bio bio) {
        this.bio = bio;
    }
    public List<Term> getTerms() {
        return terms;
    }
    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }
    public List<OtherName> getOther_names() {
        return other_names;
    }
    public void setOther_names(List<OtherName> other_names) {
        this.other_names = other_names;
    }
    public List<LeadershipRole> getLeadership_roles() {
        return leadership_roles;
    }
    public void setLeadership_roles(List<LeadershipRole> leadership_roles) {
        this.leadership_roles = leadership_roles;
    }
    public List<FamilyMember> getFamily() {
        return family;
    }
    public void setFamily(List<FamilyMember> family) {
        this.family = family;
    }
}
