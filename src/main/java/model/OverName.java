package model;

import java.time.LocalDate;

public class OverName {
    private String middle;
    private String last;
    private LocalDate end;
    public String getMiddle() {
        return middle;
    }
    public void setMiddle(String middle) {
        this.middle = middle;
    }
    public String getLast() {
        return last;
    }
    public void setLast(String last) {
        this.last = last;
    }
    public LocalDate getEnd() {
        return end;
    }
    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
