package shepherdsithole.com.powlet.mAdapaters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;
import shepherdsithole.com.powlet.DBAccess.Models.DBMobilePurchases;
import shepherdsithole.com.powlet.R;

/**
 *
 */

public class AdapterPurchaseshistory extends RecyclerView.Adapter<AdapterPurchaseshistory.CustomViewHolder> {

    private RealmResults<DBMobilePurchases > results;

    public AdapterPurchaseshistory (RealmResults < DBMobilePurchases > results) {
       
        this.results = results;
    }
    static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView descrp, txtModel;

        CustomViewHolder (View view) {
            super(view);
            this.descrp = view.findViewById ( R.id.description );

            this.txtModel =  view.findViewById( R.id.txtModel);

        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, null);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder (CustomViewHolder holder, int position) {
        DBMobilePurchases feeditem = results.get ( position );

        holder.txtModel.setText ( "Data: "+feeditem.getDateofTrans () + ": Token " + feeditem.getToken());
        

        holder.descrp.setText ("Amont: "+feeditem.getAmount_ () );

    }
   
    @Override
    public int getItemCount() {

        return (null != results ? results.size() : 0);

    }
}
