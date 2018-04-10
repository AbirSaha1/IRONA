package com.sandass.irona;

/**
 * Created by abir on 19/3/18.
 */

public class users {

    public String Name;
    public String Status;
    public String image;

    public users(){}

    public users(String name, String status, String image) {
        Name = name;
        Status = status;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}