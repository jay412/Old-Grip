package jay412.grip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText etTotalMealCost, etTax, etTips, etCustomTips;
    private Button btnCalculateTax, btn15Tips, btn20Tips, btnCustomTips;

    private double mealCost, tax, tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTotalMealCost = findViewById(R.id.etMealCost);
        etTax = findViewById(R.id.etTax);
        etTips = findViewById(R.id.etTips);
        etCustomTips = findViewById(R.id.etCustomTip);

        btnCalculateTax = findViewById(R.id.btnCalcTax);
        btnCalculateTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealCost = Double.valueOf(etTotalMealCost.getText().toString());
                calculateTax(mealCost);
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
                double tipPercent = Double.valueOf(etCustomTips.getText().toString());
                calculateTips(mealCost, tax, tipPercent);
            }
        });
    }

    private void calculateTax(double mCost){
        tax = mCost * 0.08875;
        String calcTax = Double.toString(tax);
        etTax.setText(calcTax);
    }

    private void calculateTips(double mCost, double mealTax, double percent){
        tips = (mCost + mealTax) * percent;
        String calcTips = Double.toString(tips);
        etTips.setText(calcTips);
    }
}
