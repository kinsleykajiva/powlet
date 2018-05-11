package shepherdsithole.com.powlet.activities;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 *
 */

public class App extends Application {
    @Override
    public void onCreate () {
        super.onCreate ();
        Realm.init ( this );
        Realm.setDefaultConfiguration (
                new RealmConfiguration.Builder ()
                        .deleteRealmIfMigrationNeeded ()
                        .build ()
        );

    }
}
