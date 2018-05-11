package shepherdsithole.com.powlet.mServices;

/**
 *
 */

import android.graphics.Bitmap;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class MyJobService extends JobService {


    private static final String TAG = "MyJobService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        /*new Thread( () -> {

        } ).start();*/

        return false;



    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

}
