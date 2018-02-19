package model;

/**
 * 
 * @author Karl Nicholas
 * 
 *         A bean to hold all of the GovTrack Name fields
 *
 */
public class Name {
    private String first;
    private String middle;
    private String last;
    private String suffix;
    private String nickname;
    private String official_full;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

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

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOfficial_full() {
        return official_full;
    }

    public void setOfficial_full(String official_full) {
        this.official_full = official_full;
    }

}
