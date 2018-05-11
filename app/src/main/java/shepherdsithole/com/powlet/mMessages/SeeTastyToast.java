package shepherdsithole.com.powlet.mMessages;

import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

/**
 *
 */
public class SeeTastyToast  {
    private Context context;


    public SeeTastyToast(Context context) {
        this.context = context;
    }


  public   void ToastWarning(String msg){
      TastyToast.makeText(context, msg, TastyToast.LENGTH_LONG, TastyToast.WARNING);
    }
    public   void ToastError(String msg){
        TastyToast.makeText(context, msg, TastyToast.LENGTH_LONG, TastyToast.ERROR);
    }
    public   void ToastSuccess(String msg){
        TastyToast.makeText(context,msg, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
    }
    public   void ToastDafault(String msg){
        TastyToast.makeText(context, msg, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
    }
}
