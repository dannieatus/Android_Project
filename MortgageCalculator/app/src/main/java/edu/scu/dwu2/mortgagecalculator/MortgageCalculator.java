package edu.scu.dwu2.mortgagecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MortgageCalculator extends AppCompatActivity{

    EditText amount, rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_calculator);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);

        amount = (EditText)findViewById(R.id.amountborrowed);
        rate = (EditText)findViewById(R.id.interestrate);

        //check edittext amount input format
        amount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(12,2)});

        Button buttoncalc = (Button)findViewById(R.id.calculate);
        buttoncalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                RadioGroup term = (RadioGroup)findViewById(R.id.loanterm);
                CheckBox tax = (CheckBox)findViewById(R.id.tax);

                //error check if amount borrowed is empty or interest rate is empty
                if (TextUtils.isEmpty(amount.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Please input amount borrowed", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(rate.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please input interest rate", Toast.LENGTH_SHORT).show();
                }else{
                    //get amount borrowed
                    float P = Float.valueOf(amount.getText().toString());
                    //get interest rate
                    float J = Float.valueOf(rate.getText().toString());
                    if (J < 0 || J > (20f)){
                        Toast.makeText(getApplicationContext(), "Interest Rate Must Be 0% to 20%", Toast.LENGTH_SHORT).show();
                    }else{
                        //get interest rate monthly
                        J = J / 1200;
                        //get term length
                        float N = 0;
                        for(int i=0;i<term.getChildCount();i++){
                            RadioButton radioButton = (RadioButton)term.getChildAt(i);
                            if(radioButton.isChecked()){
                                N = Float.valueOf(radioButton.getText().toString()) * 12;
                            }
                        }
                        //get tax checkbox
                        float T = 0;
                        if (tax.isChecked()) {
                            T = P * 0.1f / 100f;
                        }
                        //get total result
                        float M;
                        if (J == 0){
                            M = (P/N) + T;
                        }else{
                            M = (float) ((P * (J / (1f - Math.pow((1f + J),-N))))+ T);
                        }
                        TextView result = (TextView) findViewById(R.id.textviewresult);
                        result.setText(String.format("%.2f", M));
                    }
                }
            }
        });
    }
}
