package shepherdsithole.com.powlet.DBAccess.Models;

import io.realm.RealmObject;

/**
 * Created by Kajiva Kinsley on 10-May-18.
 */

public class DBMobilePurchases  extends RealmObject{

    private String loggedInUser , userNumber, TransNumber ,  amount_ ,dateofTrans ,token;
    private int ID;

    public DBMobilePurchases () {
    }

    public String getToken () {
        return token;
    }

    public void setToken (String token) {
        this.token = token;
    }

    public String getLoggedInUser () {
        return loggedInUser;
    }

    public void setLoggedInUser (String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getUserNumber () {
        return userNumber;
    }

    public void setUserNumber (String userNumber) {
        this.userNumber = userNumber;
    }

    public String getTransNumber () {
        return TransNumber;
    }

    public void setTransNumber (String transNumber) {
        TransNumber = transNumber;
    }

    public String getAmount_ () {
        return amount_;
    }

    public void setAmount_ (String amount_) {
        this.amount_ = amount_;
    }

    public String getDateofTrans () {
        return dateofTrans;
    }

    public void setDateofTrans (String dateofTrans) {
        this.dateofTrans = dateofTrans;
    }

    public int getID () {
        return ID;
    }

    public void setID (int ID) {
        this.ID = ID;
    }
}
