package info.androidhive.introslider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by NURAKHMET on 14.03.2017.
 */

public class Guide extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPref = this.getSharedPreferences("MyPREFERENCES",Context.MODE_PRIVATE);

        boolean loggedIn = sharedPref.getBoolean("loggedIn", false);

        if (loggedIn){
            Intent intent = new Intent(Guide.this, ActivityUserProfile.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(Guide.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
    }


}