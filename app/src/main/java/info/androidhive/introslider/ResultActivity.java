package info.androidhive.introslider;

/**
 * Created by NURAKHMET on 29.01.2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //get rating bar object
//        RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1);
//        bar.setNumStars(5);
//        bar.setStepSize(0.5f);
        //get text view
        TextView t=(TextView)findViewById(R.id.tex_result);
        //get score
        Bundle b = getIntent().getExtras();
        int score= b.getInt("score");
        t.setText("Теперь вы знаете "+(Integer.toString((score*100)/5))+"%");

        Button btn = (Button)findViewById(R.id.go_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, Registration.class));
            }
        });






        //display score
        //bar.setRating(score);
//        switch (score)
//        {
//            case 1:
//            case 2: t.setText(score);
//                break;
//            case 3:
//            case 4:t.setText("?");
//                break;
//            case 5:t.setText(".");
//                break;
//        }

    }
    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.activity_result, menu);
      //  return true;
    //}
}

