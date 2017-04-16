package info.androidhive.introslider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by NURAKHMET on 22.03.2017.
 */

public class User extends AppCompatActivity {
    private TextView textView;
    boolean clicked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        textView = (TextView) findViewById(R.id.name);

        SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
        String Uname = userDetails.getString("username", "");
//        SharedPreferences sp = getApplicationContext().getSharedPreferences("UID", 0);
//        String uid = sp.getString("key_name","nothing");
        //textView.setText(intent.getStringExtra(LoginActivity.KEY_USERNAME));
        textView.setText(Uname);
    }
}