package shepherdsithole.com.powlet.DBAccess.Models;

import io.realm.RealmObject;

public class DBCrediits extends RealmObject {

    private String creditsBalance, reedemed; 

    private int ID;

    public DBCrediits() {
    }

    public String getCreditsBalance () {
        return creditsBalance;
    }

    public void setCreditsBalance (String creditsBalance) {
        this.creditsBalance = creditsBalance;
    }

    public String getReedemed () {
        return reedemed;
    }

    public void setReedemed (String reedemed) {
        this.reedemed = reedemed;
    }

    public int getID () {
        return ID;
    }

    public void setID (int ID) {
        this.ID = ID;
    }
}
