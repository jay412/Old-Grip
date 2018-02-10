package jay412.grip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText etTotalMealCost, etTax, etTips, etCustomTips, etPeopleShare;
    private Button btnCalculateTax, btn15Tips, btn20Tips, btnCustomTips, btnCalculateGrandTotal;
    private TextView tvGrandTotal;

    private double mealCost, tax, tips, numPeople, grandTotal;

    /*TO-DO:
    * tax field is editable b/c different places have different tax rates

      */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTotalMealCost = findViewById(R.id.etMealCost);
        etTax = findViewById(R.id.etTax);
        etTips = findViewById(R.id.etTips);
        etCustomTips = findViewById(R.id.etCustomTip);
        etPeopleShare = findViewById(R.id.etPeopleShare);
        tvGrandTotal = findViewById(R.id.tvGrandTotalValue);

        btnCalculateTax = findViewById(R.id.btnCalcTax);
        btnCalculateTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etTotalMealCost.getText().toString()))
                    Toast.makeText(MainActivity.this, "Please specify a value for Total Meal Cost.", Toast.LENGTH_LONG).show();
                else {
                    mealCost = Double.valueOf(etTotalMealCost.getText().toString());
                    calculateTax(mealCost);
                }
            }
        });

        btn15Tips = findViewById(R.id.btn15);
        btn15Tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTips(mealCost, tax, 0.15);
            }
        });

        btn20Tips = findViewById(R.id.btn20);
        btn20Tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTips(mealCost, tax, 0.20);
            }
        });

        btnCustomTips = findViewById(R.id.btnCustomTip);
        btnCustomTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etCustomTips.getText().toString()))
                    Toast.makeText(MainActivity.this, "Please specify a value for Custom Tips.", Toast.LENGTH_LONG).show();
                else {
                    double tipPercent = Double.valueOf(etCustomTips.getText().toString());
                    calculateTips(mealCost, tax, tipPercent);
                }
            }
        });

        btnCalculateGrandTotal = findViewById(R.id.btnCalculateTotal);
        btnCalculateGrandTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numPeople = Double.valueOf(etPeopleShare.getText().toString());
                calculateTotal(mealCost, tax, tips, numPeople);
            }
        });
    }

    private void calculateTax(double mCost){
        tax = mCost * 0.08875;
        roundToMoneyFormat(tax);
        String calcTax = Double.toString(tax);
        etTax.setText(calcTax);
    }

    private void calculateTips(double mCost, double mealTax, double percent){
        if(TextUtils.isEmpty(etTotalMealCost.getText().toString()))
            Toast.makeText(MainActivity.this, "Please specify a value for Total Meal Cost.", Toast.LENGTH_LONG).show();
        else if(TextUtils.isEmpty(etTax.getText().toString()))
            Toast.makeText(MainActivity.this, "Tax has not been calculated yet.", Toast.LENGTH_LONG).show();
        else {
            tips = (mCost + mealTax) * percent;
            roundToMoneyFormat(tips);
            String calcTips = Double.toString(tips);
            etTips.setText(calcTips);
        }
    }

    private void calculateTotal(double mealCost, double mealTax, double tips, double numPeople){
        if(TextUtils.isEmpty(etTotalMealCost.getText().toString()))
            Toast.makeText(MainActivity.this, "Please specify a value for Total Meal Cost.", Toast.LENGTH_LONG).show();
        else if(TextUtils.isEmpty(etTax.getText().toString()))
            Toast.makeText(MainActivity.this, "Tax has not been calculated yet.", Toast.LENGTH_LONG).show();
        else if(TextUtils.isEmpty(etTips.getText().toString()))
            Toast.makeText(MainActivity.this, "Tips has not been entered or calculated yet.", Toast.LENGTH_LONG).show();
        else if (etPeopleShare.getText().toString().equals("0")){
            Toast.makeText(MainActivity.this, "Invalid number of people.", Toast.LENGTH_LONG).show();
        }
        else {
            grandTotal = (mealCost + mealTax + tips) / numPeople;
            roundToMoneyFormat(grandTotal);
            tvGrandTotal.setText("$" + Double.toString(grandTotal));
        }
    }

    public String roundToMoneyFormat(double m){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(m);
    }
}
