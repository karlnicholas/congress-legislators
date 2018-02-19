package model;

import java.time.LocalDate;

/**
 * 
 * @author Karl Nicholas
 * 
 *         A bean to hold all of the GovTrack other_names fields
 *
 */
public class OtherName {
    private String last;
    private String middle;
    private LocalDate end;
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
    public LocalDate getEnd() {
        return end;
    }
    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
