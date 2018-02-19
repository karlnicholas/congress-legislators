package model;

import java.util.*;

/**
 * 
 * @author Karl Nicholas
 * 
 *         Bean to hold the legislators Bio information
 *
 */
public class Bio {
    private String birthday;
    private String gender;
    private String religion;

   
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
