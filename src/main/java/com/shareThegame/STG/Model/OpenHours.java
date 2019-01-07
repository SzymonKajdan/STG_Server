package com.shareThegame.STG.Model;

import org.json.JSONPropertyIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public
class OpenHours {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;
    long sportobjectid;
    boolean openInBankHolidays;
    String  mondayHours;
    String  tusedayHours;
    String  wensdayHours;
    String  thrusdayHours;
    String  fridayHours;
    String  saturdayHours;
    String  sundayHours;

    public
    Long getId ( ) {
        return id;
    }

    public
    void setId ( Long id ) {
        this.id = id;
    }

    @JSONPropertyIgnore
    public
    long getSportobjectid ( ) {
        return sportobjectid;
    }

    public
    void setSportobjectid ( long sportobjectid ) {
        this.sportobjectid = sportobjectid;
    }

    public
    boolean isOpenInBankHolidays ( ) {
        return openInBankHolidays;
    }

    public
    void setOpenInBankHolidays ( boolean openInBankHolidays ) {
        this.openInBankHolidays = openInBankHolidays;
    }

    public
    String getMondayHours ( ) {
        return mondayHours;
    }

    public
    void setMondayHours ( String mondayHours ) {
        this.mondayHours = mondayHours;
    }

    public
    String getTusedayHours ( ) {
        return tusedayHours;
    }

    public
    void setTusedayHours ( String tusedayHours ) {
        this.tusedayHours = tusedayHours;
    }

    public
    String getWensdayHours ( ) {
        return wensdayHours;
    }

    public
    void setWensdayHours ( String wensdayHours ) {
        this.wensdayHours = wensdayHours;
    }

    public
    String getThrusdayHours ( ) {
        return thrusdayHours;
    }

    public
    void setThrusdayHours ( String thrusdayHours ) {
        this.thrusdayHours = thrusdayHours;
    }

    public
    String getFridayHours ( ) {
        return fridayHours;
    }

    public
    void setFridayHours ( String fridayHours ) {
        this.fridayHours = fridayHours;
    }

    public
    String getSaturdayHours ( ) {
        return saturdayHours;
    }

    public
    void setSaturdayHours ( String saturdayHours ) {
        this.saturdayHours = saturdayHours;
    }

    public
    String getSundayHours ( ) {
        return sundayHours;
    }

    public
    void setSundayHours ( String sundayHours ) {
        this.sundayHours = sundayHours;
    }
}
