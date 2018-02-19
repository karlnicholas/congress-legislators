package model;

import java.time.LocalDate;

/**
 * 
 * @author Karl Nicholas
 * 
 *         A bean to hold all of the GovTrack leadership_roles fields
 *
 */
public class LeadershipRole  {
    private String title;
    private String chamber;
    private LocalDate start;
    private LocalDate end;
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
}
