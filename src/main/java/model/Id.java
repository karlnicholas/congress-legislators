package model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Karl Nicholas
 * 
 *         Bean to hold the GovTrack id record fields. 
 *         
 *         id={house_history=,
 *         icpsr=, bioguide_previous=, opensecrets=, cspan=, maplight=, fec=,
 *         house_history_alternate=, google_entity_id=, lis=, bioguide=,
 *         wikipedia=, thomas=, govtrack=, wikidata=, votesmart=, ballotpedia=}
 *
 */
public class Id {
    private String bioguide;
    private String thomas;
    private String lis;
    private ArrayList<String> fec;
    private String govtrack;
    private String opensecrets;
    private String votesmart;
    private String icpsr;
    private String cspan;
    @JsonProperty(value="class")
    private String classField;
    private String wikipedia;
    private String wikidata;
    private String ballotpedia;
    private String washington_post;
    private String house_history;
    private String house_history_alternate;
    private String google_entity_id;
    private String maplight;
    private ArrayList<String> bioguide_previous;

    public String getBioguide() {
        return bioguide;
    }

    public void setBioguide(String bioguide) {
        this.bioguide = bioguide;
    }

    public String getThomas() {
        return thomas;
    }

    public void setThomas(String thomas) {
        this.thomas = thomas;
    }

    public String getLis() {
        return lis;
    }

    public void setLis(String lis) {
        this.lis = lis;
    }

    public ArrayList<String> getFec() {
        return fec;
    }

    public void setFec(ArrayList<String> fec) {
        this.fec = fec;
    }

    public String getGovtrack() {
        return govtrack;
    }

    public void setGovtrack(String govtrack) {
        this.govtrack = govtrack;
    }

    public String getOpensecrets() {
        return opensecrets;
    }

    public void setOpensecrets(String opensecrets) {
        this.opensecrets = opensecrets;
    }

    public String getVotesmart() {
        return votesmart;
    }

    public void setVotesmart(String votesmart) {
        this.votesmart = votesmart;
    }

    public String getIcpsr() {
        return icpsr;
    }

    public void setIcpsr(String icpsr) {
        this.icpsr = icpsr;
    }

    public String getCspan() {
        return cspan;
    }

    public void setCspan(String cspan) {
        this.cspan = cspan;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getBallotpedia() {
        return ballotpedia;
    }

    public void setBallotpedia(String ballotpedia) {
        this.ballotpedia = ballotpedia;
    }

    public String getWashington_post() {
        return washington_post;
    }

    public void setWashington_post(String washington_post) {
        this.washington_post = washington_post;
    }

    public String getHouse_history() {
        return house_history;
    }

    public void setHouse_history(String house_history) {
        this.house_history = house_history;
    }

    public String getMaplight() {
        return maplight;
    }

    public void setMaplight(String maplight) {
        this.maplight = maplight;
    }

    public ArrayList<String> getBioguide_previous() {
        return bioguide_previous;
    }

    public void setBioguide_previous(ArrayList<String> bioguide_previous) {
        this.bioguide_previous = bioguide_previous;
    }

    public String getWikidata() {
        return wikidata;
    }

    public void setWikidata(String wikidata) {
        this.wikidata = wikidata;
    }

    public String getHouse_history_alternate() {
        return house_history_alternate;
    }

    public void setHouse_history_alternate(String house_history_alternate) {
        this.house_history_alternate = house_history_alternate;
    }

    public String getGoogle_entity_id() {
        return google_entity_id;
    }

    public void setGoogle_entity_id(String google_entity_id) {
        this.google_entity_id = google_entity_id;
    }

    public String getClassField() {
        return classField;
    }

    public void setClassField(String classField) {
        this.classField = classField;
    }

}
