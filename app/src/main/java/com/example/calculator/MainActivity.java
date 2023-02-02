package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;
    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";
    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);
        Button buttonEqual = findViewById(R.id.buttonEqual);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonMul = findViewById(R.id.buttonMul);
        Button buttonDiv = findViewById(R.id.buttonDiv);
        Button buttonNeg = findViewById(R.id.buttonNeg);
        Button buttonClr=findViewById(R.id.buttonClr);
        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                newNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);
        //        buttonDot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if((newNumber.getText().toString()).length()==0)
//                {
//                    newNumber.setText("0.");
//                }
//                else newNumber.append(buttonDot.getText().toString());
//            }
//        });
        buttonClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.getText().clear();
                newNumber.getText().clear();
                operand1 = null;
                operand2 = null;
                displayOperation.setText("=");
            }
        });
        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((newNumber.getText().toString().length()) == 0) {
                    newNumber.setText("-");
                } else {
                    try {
                        Double value = Double.valueOf(newNumber.getText().toString());
                        value = value * -1;
                        newNumber.setText(value.toString());
                    } catch (NumberFormatException e) {
                        newNumber.setText("");
                    }
                }
            }
        });

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(value, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };
        buttonEqual.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonMul.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonDiv.setOnClickListener(opListener);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(String value, String op) {
        if (operand1 == null) {
            operand1 = Double.valueOf(value);
        } else {
            operand2 = Double.valueOf(value);

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
            }

        }
        result.setText(operand1.toString());
        newNumber.setText("");


    }
}