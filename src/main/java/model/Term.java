package model;

import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Karl Nicholas
 * 
 *         A bean to hold all of the GovTrack terms fields
 *
 */
public class Term {
    private String type;
    private LocalDate start;
    private LocalDate end;
    private String state;
    private String district;
    @JsonProperty(value="class")
    private String classField;
    private String state_rank;
    private String party;
    private String url;
    private String address;
    private String phone;
    private String fax;
    private String contact_form;
    private String office;
    private String rss_url;
    private String caucus;
    private List<PartyAffiliation> party_affiliations;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
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

    public String getClassField() {
        return classField;
    }

    public void setClassField(String classField) {
        this.classField = classField;
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

    public List<PartyAffiliation> getParty_affiliations() {
        return party_affiliations;
    }

    public void setParty_affiliations(List<PartyAffiliation> party_affiliations) {
        this.party_affiliations = party_affiliations;
    }

    public String getCaucus() {
        return caucus;
    }

    public void setCaucus(String caucus) {
        this.caucus = caucus;
    }

}
