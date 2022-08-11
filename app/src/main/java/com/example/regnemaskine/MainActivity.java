package com.example.regnemaskine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity {
    public  boolean ActiveResult = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("SetTextI18n")
    public void numberPad(View v) {
        TextView Display = (TextView) findViewById(R.id.display);

        if(ActiveResult) {
            Display.setText("");
            ActiveResult = false;
        }

        Button numbPad = (Button) v;
        String CurrentDisplayText = Display.getText().toString();
        String CurrentNum = numbPad.getText().toString();

        if (CurrentDisplayText.endsWith(" ") && CurrentNum.contains("+") || CurrentDisplayText.endsWith(" ") && CurrentNum.contains("*") || CurrentDisplayText.endsWith(" ") && CurrentNum.contains("-") || CurrentDisplayText.endsWith(" ") && CurrentNum.contains("/")) {
            return;
        } else {
            if (CurrentNum.contains("+") || CurrentNum.contains("*") || CurrentNum.contains("-") || CurrentNum.contains("/")) {
                Display.setText(CurrentDisplayText + " " + numbPad.getText().toString() + " ");
            } else {
                Display.setText(CurrentDisplayText + numbPad.getText().toString());

            }
        }


    }

    public void ClearDisplay(View v) {
        TextView Display = (TextView) findViewById(R.id.display);
        Display.setText("");
    }

    public void DeleteLastNum(View v) {
        TextView Display = (TextView) findViewById(R.id.display);
        if (Display.getText().toString().length() > 0) {
            if (Display.getText().toString().endsWith(" ")) {
                String NewDisplay = Display.getText().toString().substring(0, Display.getText().length() - 3);

                Display.setText(NewDisplay);
            } else {
                String NewDisplay = Display.getText().toString().substring(0, Display.getText().length() - 1);

                Display.setText(NewDisplay);
            }
        }
    }

    public void GetResult(View v){
        TextView Display = (TextView) findViewById(R.id.display);
        String expr = Display.getText().toString();

        try {
            //  Block of code to try
            if(expr.length() > 0){

                ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
                Object result = engine.eval(expr);
                // Parse the jsResult object to a String
                Display.setText(result.toString());
                ActiveResult = true;

            }
        }
        catch(Exception e) {
            //  Block of code to handle errors
            Display.setText("Invalid Result");
            ActiveResult = true;
            return;
        }

    }
}