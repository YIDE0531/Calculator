package com.nuu.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.nuu.calculator.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = this;

        initListener();
    }

    private void initListener(){
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edtName.getText().toString();
                if(checkName(name)){
                    Intent intent  = new Intent(context, CalculatorActivity.class);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }else{
                    Toast.makeText(context, getString(R.string.name_hint), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean checkName(String name){
        return name != null && !name.isEmpty();
    }
}