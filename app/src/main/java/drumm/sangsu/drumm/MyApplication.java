package drumm.sangsu.drumm;

import android.app.Application;
import android.content.Context;

/**
 * Created by Duedapi on 2016-09-08.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }
    public  static Context getContext(){
        return  context;
    }
}