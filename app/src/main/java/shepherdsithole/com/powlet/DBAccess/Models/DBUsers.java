package shepherdsithole.com.powlet.DBAccess.Models;

import io.realm.RealmObject;

/**
 *
 */

public class DBUsers extends RealmObject {

    public DBUsers () { }

    private String name , Surname ,password ,metterNuymber ,PhoneNumber;

    private int ID;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getSurname () {
        return Surname;
    }

    public void setSurname (String surname) {
        Surname = surname;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getMetterNuymber () {
        return metterNuymber;
    }

    public void setMetterNuymber (String metterNuymber) {
        this.metterNuymber = metterNuymber;
    }

    public String getPhoneNumber () {
        return PhoneNumber;
    }

    public void setPhoneNumber (String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getID () {
        return ID;
    }

    public void setID (int ID) {
        this.ID = ID;
    }
}
