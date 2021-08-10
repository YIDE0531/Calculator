package com.nuu.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.nuu.calculator.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Context context;
    private SharedPreferences preferences;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = this;

        initListener();
        checkLogin();
    }

    private void initListener(){
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = binding.edtName.getText().toString();
                if(checkName(name)){
                    saveName(name);
                    goCalculatorActivity();
                }else{
                    Toast.makeText(context, getString(R.string.name_hint), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveName(String name){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", name);
        editor.commit();
    }

    private Boolean checkName(String name){
        return name != null && !name.isEmpty();
    }

    private void checkLogin(){
        preferences = context.getSharedPreferences("CalculatorMain", Context.MODE_PRIVATE);
        name = preferences.getString("username", "");
        if (!name.isEmpty()) {
            goCalculatorActivity();
        }
    }

    private void goCalculatorActivity(){
        Intent intent  = new Intent(context, CalculatorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}