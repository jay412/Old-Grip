package jay412.grip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText etTotalMealCost, etTax, etTips, etCustomTips, etPeopleShare;
    private Button btnCalculateTax, btn15Tips, btn20Tips, btnCustomTips, btnCalculateGrandTotal, btnCalculateTotalMeal;
    private TextView tvGrandTotal;

    private double mealCost, tax, tips, numPeople, grandTotal;

    /*TODO:
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
        btn15Tips = findViewById(R.id.btn15);
        btn20Tips = findViewById(R.id.btn20);
        btnCustomTips = findViewById(R.id.btnCustomTip);
        btnCalculateGrandTotal = findViewById(R.id.btnCalculateTotal);
        btnCalculateTotalMeal = findViewById(R.id.btn_calc_total_meal);

        setUpButtons();
    }

    private void calculateTax(double mCost){
        tax = mCost * 0.08875;
        String calcTax = roundToMoneyFormat(tax);
        //String calcTax = Double.toString(tax);
        tax = Double.valueOf(calcTax);
        etTax.setText(calcTax);
    }

    private void calculateTips(double mCost, double mealTax, double percent){
        if(TextUtils.isEmpty(etTotalMealCost.getText().toString()))
            Toast.makeText(MainActivity.this, "Please specify a value for Total Meal Cost.", Toast.LENGTH_LONG).show();
        else if(TextUtils.isEmpty(etTax.getText().toString()))
            Toast.makeText(MainActivity.this, "Tax has not been calculated yet.", Toast.LENGTH_LONG).show();
        else {
            tips = (mCost + mealTax) * percent;
            String calcTips = roundToMoneyFormat(tips);
            //String calcTips = Double.toString(tips);
            tips = Double.valueOf(calcTips);
            etTips.setText(calcTips);
        }
    }

    private void calculateTotal(double mCost, double mealTax, double tips, double numPeople){
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
            grandTotal = (mCost + mealTax + tips) / numPeople;

            String total = roundToMoneyFormat(grandTotal);
            grandTotal = Double.valueOf(total);
            tvGrandTotal.setText("$" + total);
        }
    }

    public String roundToMoneyFormat(double m){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(m);
    }

    public void resetFields(){
        etTotalMealCost.getText().clear();
        etTax.getText().clear();
        etTips.getText().clear();
        etCustomTips.getText().clear();
        etPeopleShare.setText("1");
        tvGrandTotal.setText(getString(R.string.initial_grand_total));
    }

    private void setUpButtons(){
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

        btn15Tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTips(mealCost, tax, 0.15);
            }
        });

        btn20Tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTips(mealCost, tax, 0.20);
            }
        });

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

        btnCalculateGrandTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numPeople = Double.valueOf(etPeopleShare.getText().toString());
                calculateTotal(mealCost, tax, tips, numPeople);
            }
        });

        btnCalculateTotalMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TotalMealActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear_setting) {
            resetFields();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
