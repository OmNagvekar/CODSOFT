package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView result,operation;
    MaterialButton buttonC,buttonOpen,buttonClose,buttonDivide;
    MaterialButton button7,button8,button9,buttonMultiply,button4,button5,button6,buttonAdd,button1,button2,button3;
    MaterialButton buttonMinus,buttonAC,button0,buttonDot,buttonEquals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        operation = findViewById(R.id.operation);
        assignId(buttonC,R.id.button_c);
        assignId(buttonOpen,R.id.button_open);
        assignId(buttonClose,R.id.button_close);
        assignId(buttonDivide,R.id.button_divide);
        assignId(button7,R.id.button_seven);
        assignId(button8,R.id.button_eight);
        assignId(button9,R.id.button_nine);
        assignId(buttonMultiply,R.id.button_multiply);
        assignId(button4,R.id.button_four);
        assignId(button5,R.id.button_five);
        assignId(button6,R.id.button_six);
        assignId(buttonAdd,R.id.button_add);
        assignId(button1,R.id.button_one);
        assignId(button2,R.id.button_two);
        assignId(button3,R.id.button_three);
        assignId(button0,R.id.button_zero);
        assignId(buttonMinus,R.id.button_substract);
        assignId(buttonAC,R.id.button_allclear);
        assignId(buttonDot,R.id.button_dot);
        assignId(buttonEquals,R.id.button_equal);
    }

    private void assignId(MaterialButton button, int button_id) {
        button = findViewById(button_id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button =(MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataToCalculate = operation.getText().toString();
        if(buttonText.equals("AC")){
            operation.setText("");
            result.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            operation.setText(result.getText());
            return;
        }
        if(buttonText.equals("C")){
            if(dataToCalculate.isEmpty()){
                dataToCalculate ="";
            }else {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        }else {
            dataToCalculate = dataToCalculate+buttonText;
        }

        operation.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);
        if(!finalResult.equals("Error")){
            result.setText(finalResult);
        }
    }
    String getResult(String data){
        try {
            if(data.isEmpty()){
                return "";
            }
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable =  context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e){
            return "Error";
        }
    }
}