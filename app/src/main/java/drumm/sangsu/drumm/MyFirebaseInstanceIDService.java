package drumm.sangsu.drumm;

/**
 * Created by Duedapi on 2016-09-08.
 */
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + token);

        // TODO: Implement this method to send any registration to your app's servers.
      //  sendRegistrationToServer(token);
        NetworkManager.getInstance().send_token(MyApplication.getContext(), token, new NetworkManager.OnResultListener<SendTokenResult>() {
            @Override
            public void onSuccess(Request request, SendTokenResult result) {

            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
/*

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .build();

        //request
        Request request = new Request.Builder()
                .url("210.118.74.151:8902")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

    }
}
