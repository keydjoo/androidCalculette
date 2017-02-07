package com.example.samuel.calculette;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private String currentValue;
    private String currentOperator;
    private String allExpressions;
    private Vector<Integer> values;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.currentValue = "";
        this.currentOperator = "";
        this.allExpressions = "";
        this.values = new Vector<>();
        this.display = (TextView)findViewById(R.id.displayer);
    }

    public void numberButton(View view) {
        stabiliseOperation();
        Button b = (Button)view;
        this.currentValue += b.getText().toString();
        displayExpression();
    }

    public void plusButton(View view) {plusAction();}
    private void plusAction() {
        addValue();
        this.currentOperator = this.values.isEmpty() ? "" : "+";
        displayExpression();
    }

    public void minusButton(View view) {minusAction();}
    private void minusAction() {
        addValue();
        this.currentOperator = "-";
        displayExpression();
    }

    public void equalsButton(View view) {equalsAction();}
    private void equalsAction() {
        addValue();
        Integer total = 0;
        for (Integer value : values)
            total += value;
        this.values.clear();
        this.currentOperator = "";
        this.values.add(total);
        this.allExpressions = total.toString();
        displayExpression();
    }

    private void addValue() {
        if(!this.currentValue.equals("")) {
            this.allExpressions += this.currentOperator + this.currentValue;
            this.values.add(Integer.parseInt(this.currentOperator + this.currentValue));
            this.currentValue = "";
        }
    }

    private void displayExpression() {
        this.display.setText(allExpressions + currentOperator + currentValue);
    }

    private void stabiliseOperation() {
        if(this.currentOperator.equals("") && !this.values.isEmpty())
            this.currentOperator = "+";
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN)
            switch(event.getKeyCode()) {
                case KeyEvent.KEYCODE_0: case KeyEvent.KEYCODE_1:
                case KeyEvent.KEYCODE_2: case KeyEvent.KEYCODE_3:
                case KeyEvent.KEYCODE_4: case KeyEvent.KEYCODE_5:
                case KeyEvent.KEYCODE_6: case KeyEvent.KEYCODE_7:
                case KeyEvent.KEYCODE_8: case KeyEvent.KEYCODE_9:
                    stabiliseOperation();
                    this.currentValue += String.valueOf(event.getNumber());
                    displayExpression();
                    break;
                case KeyEvent.KEYCODE_EQUALS:
                case KeyEvent.KEYCODE_ENTER:
                    equalsAction();
                    break;
                case KeyEvent.KEYCODE_MINUS:
                    minusAction();
                    break;
                case KeyEvent.KEYCODE_PLUS:
                    plusAction();
                    break;
            }
        displayExpression();
        return true;
    }
}
