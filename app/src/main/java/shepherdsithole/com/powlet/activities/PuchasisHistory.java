package shepherdsithole.com.powlet.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import shepherdsithole.com.powlet.DBAccess.Models.DBMobilePurchases;
import shepherdsithole.com.powlet.R;
import shepherdsithole.com.powlet.cwidgets.VerticalSpaceItemDecoration;
import shepherdsithole.com.powlet.mAdapaters.AdapterPurchaseshistory;

public class PuchasisHistory extends AppCompatActivity {
    private RecyclerView recycler_view;
    private AdapterPurchaseshistory mAdapter;
    private Realm realm;
    private TextView recycViewStatus;
    private RealmResults<DBMobilePurchases > results;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_puchasis_history );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realm = Realm.getDefaultInstance ();
        recycler_view = findViewById(R.id.recyclerview);

         recycViewStatus = findViewById ( R.id.recycViewStatus );
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager (this));
        recycler_view.addItemDecoration(new VerticalSpaceItemDecoration (14));

        results = realm.where(DBMobilePurchases.class).sort("ID", Sort.DESCENDING).findAll();
        mAdapter = new AdapterPurchaseshistory(results);
        if (results.size() == 0) {

            recycViewStatus.setText("No History to show yet.");
        }
        recycler_view.setAdapter(mAdapter);

    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
           
            realm.close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
