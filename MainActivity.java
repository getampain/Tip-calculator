package uk.com.widget;


import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button button;
    EditText textt,otherper;
    CheckBox check10,check20,others;
    TextView tipvalue,totalvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        check10 = (CheckBox) findViewById(R.id.per10);
        check20 = (CheckBox) findViewById(R.id.per20);
        others = (CheckBox) findViewById(R.id.others);

        button.setOnClickListener(this);
        check10.setOnClickListener(this);
        check20.setOnClickListener(this);
        others.setOnClickListener(this);
        otherper = (EditText) findViewById(R.id.otherPer);
        tipvalue =  (TextView) findViewById(R.id.tipvalue);
        totalvalue = (TextView) findViewById(R.id.totalvalue);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {//function for save data using life cycle
    // Save the user's current values

        //check total and tipvalue are null and input correct values
        if(tipvalue.getText().toString() != null)
            savedInstanceState.putString("tipV", tipvalue.getText().toString());
        else
            savedInstanceState.putString("tipV",null);
        if(totalvalue.getText().toString() != null)
            savedInstanceState.putString("totalV", totalvalue.getText().toString());
        else
            savedInstanceState.putString("totalV", null);


// Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {//function for use saved data usig life cycle

        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {//if save data is not null
            if (savedInstanceState.getString("tipV") != null)
                tipvalue.setText(savedInstanceState.getString("tipV"));
            if (savedInstanceState.getString("totalV") != null)
                totalvalue.setText(savedInstanceState.getString("totalV"));
        }
    }

    public double check(String money)//check function for input value is num or not
    {
        int count,count2,i,judge;
        char change[];

        judge=0;
        count2=0;//check for '.' if count2 >= 2  it is not number

        change=money.toCharArray();
        count=money.length();
        if(count == 0)
            judge++;

        for(i=0;i<count;i++){
            if((change[i] < 48 || change[i] > 57)  ) {//if char is not digit number
                if(change[i] == '.') //if is '.'
                    count2++;
                else
                    judge++;
            }
        }
        if(judge == 0 && count2 < 2){
            return Double.valueOf(money);
        }
        else
            return -1.0;
    }
    public void onClick(View view){

        textt = (EditText) findViewById(R.id.resultvalue);
        double doubleMoney,temp;

        if(view.getId() == button.getId()){

            String total;
            total = textt.getText().toString();
            doubleMoney=check(total);
            temp=check(otherper.getText().toString());

            if(doubleMoney == -1 && temp == -1 ) {//if input value and tipvalue is not number toast
                Toast.makeText(getApplicationContext(), "You entert ths wrong input!!", Toast.LENGTH_LONG).show();
                textt.setText("");
                otherper.setText("");
            }
            else if(doubleMoney == -1){//if input value is not number
                Toast.makeText(getApplicationContext(), "You entert ths wrong input!!", Toast.LENGTH_LONG).show();
                textt.setText("");
            }
            else {
                if(check10.isChecked()){//if checkbox 10 is checked
                    totalvalue.setText("Total: "+Double.toString(doubleMoney + doubleMoney * 0.1));
                    tipvalue.setText("Tip: " + Double.toString(doubleMoney*0.1));

                }
                else if(check20.isChecked()){//if checkbox 20 is checked
                    totalvalue.setText("Total: "+Double.toString(doubleMoney+doubleMoney*0.2));
                    tipvalue.setText("Tip: " +Double.toString(doubleMoney*0.2));

                }
                else if(others.isChecked()){//if checkbox others is checked
                    if(temp == -1) {//if other tip is not number toast
                        otherper.setText("");
                        Toast.makeText(getApplicationContext(), "You entert ths wrong tip input!!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        totalvalue.setText("Total: "+Double.toString(doubleMoney+doubleMoney*temp*0.01));
                        tipvalue.setText("Tip: " +Double.toString(doubleMoney*temp*0.01));


                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"You entert ths wrong input!!", Toast.LENGTH_LONG).show();
                    textt.setText("");
                }

            }

        }
        //if one check box is clicked other is unchecked
        else if(view.getId() == check10.getId()){
            check20.setChecked(false);
            others.setChecked(false);
            otherper.setVisibility(otherper.INVISIBLE);
            otherper.setText("");
            otherper.setHint("enter_persent");
        }
        else if(view.getId() == check20.getId()){
            check10.setChecked(false);
            others.setChecked(false);
            otherper.setVisibility(otherper.INVISIBLE);
            otherper.setText("");
            otherper.setHint("enter_persent");
        }
        else if(view.getId() == others.getId()){// if others is checked set tiptextbox's visibility true
            check20.setChecked(false);
            check10.setChecked(false);
            if(!others.isChecked())
                otherper.setVisibility(otherper.INVISIBLE);
            else
                otherper.setVisibility(otherper.VISIBLE);
            otherper.setText("");
            otherper.setHint("enter_persent");
        }



    }

}
