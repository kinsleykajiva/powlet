package shepherdsithole.com.powlet.DBAccess;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import shepherdsithole.com.powlet.DBAccess.Models.DBBankCard;
import shepherdsithole.com.powlet.DBAccess.Models.DBCrediits;

import shepherdsithole.com.powlet.DBAccess.Models.DBMobilePurchases;
import shepherdsithole.com.powlet.DBAccess.Models.DBUsers;

/**
 *
 */

public class CRUD {

    public static void saveUser(String name, String Surname, String password,String metterNuymber , String PhoneNumber) {
        new Thread ( () -> {
            Realm realm = Realm.getDefaultInstance ();
            realm.beginTransaction ();
            RealmResults < DBUsers > results = realm.where ( DBUsers.class ).findAll ();
            DBUsers user = realm.createObject ( DBUsers.class );
            user.setName ( name );
            user.setSurname ( Surname );
            user.setPassword ( password );
            user.setMetterNuymber ( metterNuymber );
            user.setPhoneNumber ( PhoneNumber );
            user.setID (
                    (results.isEmpty () && results.size () < 1) ? 0 : (results.max ( "ID" ).intValue () + 1)
            );
            realm.commitTransaction ();
        realm.close ();
        } ).start ();
    }

    public static void saveTransactionMobileNumber (String loogedUser , String userNumber, String transNumber, String amount_,String token) {


            Realm realm = Realm.getDefaultInstance ();
            RealmResults < DBMobilePurchases > results = realm.where ( DBMobilePurchases.class ).findAll ();
            String date = new SimpleDateFormat ( "yyyy/MM/dd" ).format ( new Date () );
            realm.beginTransaction ();
            DBMobilePurchases row = realm.createObject ( DBMobilePurchases.class );
            row.setAmount_ ( amount_ );
            row.setDateofTrans ( date  );
            row.setLoggedInUser (  loogedUser);
            row.setTransNumber ( transNumber );
            row.setToken ( token );
            row.setUserNumber (userNumber  );
            row.setID (
                    (results.isEmpty () && results.size () < 1) ? 0 : (results.max ( "ID" ).intValue () + 1)
            );

            realm.commitTransaction ();
            realm.close ();

    }
    public static int getTranctionID(){
        Realm realm = Realm.getDefaultInstance ();
        RealmResults < DBMobilePurchases > results = realm.where ( DBMobilePurchases.class ).findAll ();
       int id= results.max ( "ID" ).intValue ();
        realm.close ();
       return id;
    }
    public static void saveBankCard(String cardNumber,String civ, String amount ,String  userNumber) {
         new Thread ( () -> {
        Realm realm = Realm.getDefaultInstance ();
            RealmResults < DBBankCard > results = realm.where ( DBBankCard.class ).findAll ();
            String date = new SimpleDateFormat ( "yyyy/MM/dd" ).format ( new Date () );
            realm.beginTransaction ();
            DBBankCard row = realm.createObject ( DBBankCard.class );
            row.setCardNumber (cardNumber);
            row.setCiv (civ);
            row.setAmount (amount);
            row.setUserNumber (userNumber);
            row.setID (
                    (results.isEmpty () && results.size () < 1) ? 0 : (results.max ( "ID" ).intValue () + 1)
            );

            realm.commitTransaction ();
            realm.close ();
            } ).start ();
        
    }

    public static void saveRedeeemCredit(String setreedemed , String setcreditsBalance) {
        new Thread(() -> {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<DBCrediits > results = realm.where(DBCrediits.class).findAll();
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            realm.beginTransaction();
            DBCrediits row = realm.createObject(DBCrediits.class);
            row.setReedemed (setreedemed);
           row.setCreditsBalance (setcreditsBalance);
            row.setID((results.isEmpty() && results.size() < 1) ? 0 : (results.max("ID").intValue() + 1));

            realm.commitTransaction();
            realm.close();
        }).start();
    }
    public static String getLastCreditBalance(){
         Realm realm = Realm.getDefaultInstance();
         RealmResults<DBCrediits> results = realm.where(DBCrediits.class).findAll();
         if(results.isEmpty ()){
             realm.close();
             return "0";
         }
        int id = results.max("ID").intValue();
        DBCrediits results2 = realm.where(DBCrediits.class).equalTo ("ID" , id).findFirst ();
        String bal = results2.getCreditsBalance ();
         realm.close();
         return bal;
    }
}
