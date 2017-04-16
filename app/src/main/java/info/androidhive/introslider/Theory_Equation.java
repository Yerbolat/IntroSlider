package info.androidhive.introslider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by NURAKHMET on 12.04.2017.
 */

public class Theory_Equation extends AppCompatActivity{
    String cnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Theory", 0);
        cnt = sp.getString("Logarithm", "nothing");
        TextView t=(TextView)findViewById(R.id.cnt);
        t.setText(Html.fromHtml(cnt));
        TextView topic =(TextView)findViewById(R.id.topic);
        topic.setText("Logarithm");
    }
}
