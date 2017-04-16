package info.androidhive.introslider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NURAKHMET on 11.04.2017.
 */

public class Logarithm  extends AppCompatActivity {
    List<Logarithm_question> quesList;
    int score=0;
    int qid=0;
    Logarithm_question currentQ;
    TextView txtQuestion;
    Button rda, rdb, rdc,rdd ,butNext;
    boolean checked = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelperLogarithm db=new DBHelperLogarithm(this);
        quesList=db.getAllQuestions();
        currentQ=quesList.get(qid);
        txtQuestion=(TextView)findViewById(R.id.textView1);

        rda=(Button)findViewById(R.id.radio0);
        rdb=(Button)findViewById(R.id.radio1);
        rdc=(Button)findViewById(R.id.radio2);
        rdd=(Button)findViewById(R.id.radio3);
        butNext=(Button)findViewById(R.id.button1);
        setQuestionView();

        rda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("yourans", currentQ.getANSWER()+" "+rda.getText());
                if (checked) {
                    checked = false;
                    butNext.setEnabled(true);
                    butNext.setBackgroundResource(R.drawable. next_btn_bg);
                    if(currentQ.getANSWER().equals(rda.getText())) {
                        score++;
                        Log.d("score", "Your score"+score);

//                        rda.setTextColor(Color.GREEN);
//                        t.setText("Correct!!! " + "Your point:"+Integer.toString(score));
//                        t.setTextColor(Color.GREEN);
                        rda.setBackgroundResource(R.drawable.bg_correct);
                    } else {

//                        t.setText("Incorrect!!!Never give up!!!"  + "Your point:"+Integer.toString(score));
//                        t.setTextColor(Color.RED);
//                        rda.setTextColor(Color.RED);
                        rda.setBackgroundResource(R.drawable.bg_incorrect);
                    }
                }
            }
        });
        rdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("yourans", currentQ.getANSWER()+" "+rdb.getText());
                butNext.setEnabled(true);
                butNext.setBackgroundResource(R.drawable. next_btn_bg);
                if (checked) {
                    checked = false;
                    if(currentQ.getANSWER().equals(rdb.getText())) {
                        score++;
                        Log.d("score", "Your score"+score);

//                    t.setText("Correct!!!" + "Your point:"+Integer.toString(score));
//                    rdb.setTextColor(Color.GREEN);
//                    t.setTextColor(Color.GREEN);
                        rdb.setBackgroundResource(R.drawable.bg_correct);
                    } else {

//                        t.setText("Incorrect!!!Never give up!!!"  + "Your point:"+Integer.toString(score));
//                        t.setTextColor(Color.RED);
//                        rdb.setTextColor(Color.RED);
                        rdb.setBackgroundResource(R.drawable.bg_incorrect);
                    }
                }
            }
        });
        rdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("yourans", currentQ.getANSWER()+" "+rdc.getText());
                butNext.setEnabled(true);
                butNext.setBackgroundResource(R.drawable. next_btn_bg);
                if (checked) {
                    checked = false;
                    if(currentQ.getANSWER().equals(rdc.getText())) {
                        score++;
                        Log.d("score", "Your score"+score);

//                    t.setText("Correct!!!" + "Your point:"+Integer.toString(score));
//                    rdc.setTextColor(Color.GREEN);
//                    t.setTextColor(Color.GREEN);
                        rdc.setBackgroundResource(R.drawable.bg_correct);
                    } else {

//                        t.setText("Incorrect!!!Never give up!!!"  + "Your point:"+Integer.toString(score));
//                        t.setTextColor(Color.RED);
//                        rdc.setTextColor(Color.RED);
                        rdc.setBackgroundResource(R.drawable.bg_incorrect);
                    }
                }
            }
        });
        rdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("yourans", currentQ.getANSWER()+" "+rdd.getText());
                butNext.setEnabled(true);
                butNext.setBackgroundResource(R.drawable. next_btn_bg);
                if (checked) {
                    checked = false;
                    if(currentQ.getANSWER().equals(rdd.getText())) {
                        score++;
                        Log.d("score", "Your score"+score);

//                    t.setText("Correct!!!" + "Your point:"+Integer.toString(score));
//                    rdc.setTextColor(Color.GREEN);
//                    t.setTextColor(Color.GREEN);
                        rdd.setBackgroundResource(R.drawable.bg_correct);
                    } else {

//                        t.setText("Incorrect!!!Never give up!!!"  + "Your point:"+Integer.toString(score));
//                        t.setTextColor(Color.RED);
//                        rdc.setTextColor(Color.RED);
                        rdd.setBackgroundResource(R.drawable.bg_incorrect);
                    }
                }
            }
        });

        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butNext.setEnabled(false);
                butNext.setBackgroundResource(R.drawable.unclicked);
//                  t.setText("");
//                rda.setTextColor(Color.BLACK);
//                rdb.setTextColor(Color.BLACK);
//                rdc.setTextColor(Color.BLACK);
                rda.setBackgroundResource(R.drawable.layout_bg);
                rdb.setBackgroundResource(R.drawable.layout_bg);
                rdc.setBackgroundResource(R.drawable.layout_bg);
                rdd.setBackgroundResource(R.drawable.layout_bg);
                checked = true;
                if(qid<10){
                    currentQ=quesList.get(qid);
                    setQuestionView();
                }
                else{
                    Intent intent = new Intent(Logarithm.this, ResultActivity.class);
                    Bundle b =  new Bundle();
                    b.putInt("score", score); //Your score
                    intent.putExtras(b); //Put your score to your next Intent
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
    //   getMenuInflater().inflate(R.menu.activity_quiz, menu);
    //  return true;
    //}
    private void setQuestionView()
    {
        txtQuestion.setText(Html.fromHtml(currentQ.getQUESTION()));
        rda.setText(currentQ.getOPTA());
        rdb.setText(currentQ.getOPTB());
        rdc.setText(currentQ.getOPTC());
        rdd.setText(currentQ.getOPTD());
        qid++;
    }
}