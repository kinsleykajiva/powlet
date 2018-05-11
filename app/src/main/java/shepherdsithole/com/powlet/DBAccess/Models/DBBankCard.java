package shepherdsithole.com.powlet.DBAccess.Models;

import io.realm.RealmObject;







public class DBBankCard extends RealmObject {

    private String cardNumber, civ, amount , userNumber;

    private int ID;


    public DBBankCard(){}

    public String getCardNumber () {
        return cardNumber;
    }

    public void setCardNumber (String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCiv () {
        return civ;
    }

    public void setCiv (String civ) {
        this.civ = civ;
    }

    public String getAmount () {
        return amount;
    }

    public void setAmount (String amount) {
        this.amount = amount;
    }

    public String getUserNumber () {
        return userNumber;
    }

    public void setUserNumber (String userNumber) {
        this.userNumber = userNumber;
    }

    public int getID () {
        return ID;
    }

    public void setID (int ID) {
        this.ID = ID;
    }
}












