package com.nuu.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nuu.calculator.databinding.ActivityCalculatorBinding;

public class CalculatorActivity extends AppCompatActivity {
    private CalculatorViewModel calculatorViewModel = new CalculatorViewModel();
    private ActivityCalculatorBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalculatorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.setViewModel(calculatorViewModel);
    }
}