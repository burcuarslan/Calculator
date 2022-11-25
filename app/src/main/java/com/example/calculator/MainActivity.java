package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private EditText operation;
    private TextView displayOperation;
    private String pendingOperation = "+";
    private Double operand1 = null, operand2 = null;
    private final String STATE_PENDING_OPERATION = "PENDING_OPERATION";
    private final String OPERATION = "OPERATION";
    private final String OPERAND1 = "OPERAND1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView) findViewById(R.id.result);
        operation = (EditText) findViewById(R.id.operation);
        displayOperation = (TextView) findViewById(R.id.displayOperation);

        Button one = (Button) findViewById(R.id.one);
        Button two = (Button) findViewById(R.id.two);
        Button three = (Button) findViewById(R.id.three);
        Button four = (Button) findViewById(R.id.four);
        Button five = (Button) findViewById(R.id.five);
        Button six = (Button) findViewById(R.id.six);
        Button seven = (Button) findViewById(R.id.seven);
        Button eight = (Button) findViewById(R.id.eight);
        Button nine = (Button) findViewById(R.id.nine);
        Button zero = (Button) findViewById(R.id.zero);
        Button add = (Button) findViewById(R.id.add);
        Button sub = (Button) findViewById(R.id.sub);
        Button mul = (Button) findViewById(R.id.mul);
        Button div = (Button) findViewById(R.id.div);
        Button equal = (Button) findViewById(R.id.equal);
        Button dot = (Button) findViewById(R.id.dot);
        Button clear = (Button) findViewById(R.id.clear);
        Button pow = (Button) findViewById(R.id.pow);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        ArrayList<Button> numberButtons = new ArrayList<Button>() {{
            add(one);
            add(two);
            add(three);
            add(four);
            add(five);
            add(six);
            add(seven);
            add(eight);
            add(nine);
            add(zero);
            add(dot);
        }};

        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                operation.append(b.getText().toString());
            }
        };

        for (Button b : numberButtons) {
            b.setOnClickListener(numberListener);
        }

        ArrayList<Button> operatorButtons = new ArrayList<Button>() {{
            add(add);
            add(sub);
            add(mul);
            add(div);
            add(equal);
            add(pow);

        }};

        View.OnClickListener operatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = operation.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    calculate(doubleValue, op);
                } catch (NumberFormatException e) {
                    Log.d("Calculator", "Number Format Exception");
                    operation.setText("");
                }

                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };

        for (Button b : operatorButtons) {
            b.setOnClickListener(operatorListener);
        }


    }


    private void calculate(Double value, String op) {
        if (operand1 == null) {
            operand1 = value;

        } else {
            operand2 = value;
            if (pendingOperation.equals("=")) {
                pendingOperation = op;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = operand2;
                    break;
                case "+":
                    operand1 += operand2;
                    break;
                case "-":
                    operand1 -= operand2;
                    break;
                case "*":
                    operand1 *= operand2;
                    break;
                case "/":
                    if (operand2 == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= operand2;
                    }
                    break;
                case "^":
                    operand1 = Math.pow(operand1, operand2);
                    break;
            }
        }
        result.setText(String.valueOf(operand1));
        operation.setText("");
        displayOperation.setText(op);

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(OPERATION, operation.getText().toString());
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(OPERAND1, operand1);
            super.onSaveInstanceState(outState);
        }


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //operation.setText(savedInstanceState.getString(OPERATION));
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(OPERAND1);
        result.setText(String.valueOf(operand1));
        displayOperation.setText(pendingOperation);
    }

    private void clear(){
        operand1=null;
        operand2=null;
        operation.setText("");
        result.setText("");
        displayOperation.setText("");
    }
}