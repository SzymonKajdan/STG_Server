package com.shareThegame.STG.Model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {


    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private long id;

    public long getId( ) {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    private  String role;

    public String getRole ( ) {
        return role;
    }

    public void setRole ( String role ) {
        this.role = role;
    }
}
