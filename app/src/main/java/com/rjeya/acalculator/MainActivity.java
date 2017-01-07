package com.rjeya.acalculator;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    // Graphic component
    private AutoResizeTextView lblNumberPrinter;
    private AutoResizeTextView lblOperationHistory;
    private Button btnC;
    private Button btnCE;
    private ImageButton btnErase;
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnMultiply;
    private Button btnSoustract;
    private Button btnAdd;
    private Button btnDivide;
    private Button btnNegate;
    private Button btnEqual;

    // Property

    private char opChar;
    private double resultNb;
    private boolean isOpExecuted;
    private boolean isOpButtonWasPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        // Récuperation des boutons dans l'activity
        this.lblNumberPrinter = (AutoResizeTextView)findViewById(R.id.lblNumberPrinter);
        this.lblOperationHistory = (AutoResizeTextView) findViewById(R.id.lblOperationHistory);
        this.btnC = (Button) findViewById(R.id.btnC);
        this.btnCE = (Button) findViewById(R.id.btnCE);
        this.btnErase = (ImageButton) findViewById(R.id.btnDel);
        this.btn0 = (Button) findViewById(R.id.btn0);
        this.btn1 = (Button) findViewById(R.id.btn1);
        this.btn2 = (Button) findViewById(R.id.btn2);
        this.btn3 = (Button) findViewById(R.id.btn3);
        this.btn4 = (Button) findViewById(R.id.btn4);
        this.btn5 = (Button) findViewById(R.id.btn5);
        this.btn6 = (Button) findViewById(R.id.btn6);
        this.btn7 = (Button) findViewById(R.id.btn7);
        this.btn8 = (Button) findViewById(R.id.btn8);
        this.btn9 = (Button) findViewById(R.id.btn9);
        this.btnMultiply = (Button) findViewById(R.id.btnMultiply);
        this.btnSoustract = (Button) findViewById(R.id.btnSoustract);
        this.btnAdd = (Button) findViewById(R.id.btnAdd);
        this.btnDivide = (Button) findViewById(R.id.btnDivide);
        this.btnNegate = (Button) findViewById(R.id.btnNegate);
        this.btnEqual = (Button) findViewById(R.id.btnEqual);

        this.opChar = ' ';
        this.lblNumberPrinter.setText("0");
        this.isOpExecuted = true;
    }

    // Method
    public void printInScreenLabel(int nb)
    {
        this.resetDisplayLabelTextSize();
        if (this.lblNumberPrinter.getText().toString() == "0" || this.isOpButtonWasPressed)
        {
            this.lblNumberPrinter.setText(Integer.toString(nb));
            this.isOpButtonWasPressed = false;
        }
        else
        {
            this.lblNumberPrinter.setText(this.lblNumberPrinter.getText().toString() + Integer.toString(nb));
        }
    }

    public void printInScreenLabel(double nb)
    {
        this.resetDisplayLabelTextSize();
        if (this.lblNumberPrinter.getText() == "0" || this.isOpButtonWasPressed || this.isOpExecuted || this.isOpButtonWasPressed)
        {
            this.lblNumberPrinter.setText(this.doubleToString(nb));
            this.isOpButtonWasPressed = false;
            this.isOpExecuted = false;
        }
        else
        {
            this.lblNumberPrinter.setText(this.lblNumberPrinter.getText().toString() + this.doubleToString(nb));
        }
    }

    public void printInScreenLabel(char car)
    {
        this.resetDisplayLabelTextSize();
        if (car == '.' && this.isCharactereIsPresentIn(this.lblNumberPrinter.getText().toString(), car) != true)
        {
            this.lblNumberPrinter.setText(this.lblNumberPrinter.getText().toString() + Character.toString(car));
        }
        else
        {
            if ((this.lblNumberPrinter.getText() != "0" && car != '.'))
            {
                this.lblNumberPrinter.setText(this.lblNumberPrinter.getText().toString() + Character.toString(car));
                this.isOpButtonWasPressed = false;
                this.isOpExecuted = false;
            }
        }
    }

    public void eraseACharacterInLblPrinter()
    {
        if(this.lblNumberPrinter.getText().length() > 1)
        {
            this.lblNumberPrinter.setText(this.lblNumberPrinter.getText().toString().substring(0, this.lblNumberPrinter.getText().length() - 1));
        }
        else
        {
            this.lblNumberPrinter.setText("0");
        }
    }

    public boolean isCharactereIsPresentIn(String str, char car)
    {
        return str.contains(String.valueOf(car));
    }

    private void printCurrentNumberInHistoryLabel(char op)
    {
        this.lblOperationHistory.setText(this.lblOperationHistory.getText().toString() + this.lblNumberPrinter.getText().toString() + op);
    }

    public void preExecuteOperation(char op)
    {
        this.printCurrentNumberInHistoryLabel(op);
        if(this.opChar != ' ' && this.lblNumberPrinter.getText().toString() != "0")
        {
            this.executeOperation();
        }
        else
        {
            this.resultNb = Double.parseDouble(this.lblNumberPrinter.getText().toString());
        }
        if(this.lblNumberPrinter.getText().toString() != "0")
        {
            this.opChar = op;
        }
        this.isOpButtonWasPressed = true;
    }


    public void executeOperation()
    {
        switch (this.opChar)
        {
            case '+':
                this.resultNb = this.resultNb + Double.parseDouble(this.lblNumberPrinter.getText().toString());
                break;
            case '-':
                this.resultNb = this.resultNb - Double.parseDouble(this.lblNumberPrinter.getText().toString());
                break;
            case (char)247: // Symbole division
                this.resultNb = this.resultNb / Double.parseDouble(this.lblNumberPrinter.getText().toString());
                break;
            case (char)215: // Symbole multiplier
                this.resultNb = this.resultNb * Double.parseDouble(this.lblNumberPrinter.getText().toString());
                break;
            default:

                break;

        }
        if (this.opChar != ' ' || this.isOpExecuted)
        {
            this.printInScreenLabel(this.resultNb);
        }
        this.opChar = ' ';
        this.isOpExecuted = true;
    }

    public String doubleToString(double nb)
    {
        if(nb == (long) nb)
            return String.format("%d",(long)nb);
        else
            return String.format("%s",nb);
    }

    public void resetDisplayLabelTextSize(){
        this.lblNumberPrinter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
        this.lblOperationHistory.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        this.lblNumberPrinter.setText(this.lblNumberPrinter.getText());
    }

    public void btnC_Click(View sender){
        this.lblNumberPrinter.setText("0");
        this.lblOperationHistory.setText("");
        // this.resetDisplayLabelTextSize();
        this.resultNb = 0;
        this.opChar = ' ';
        this.isOpExecuted = true;
        this.resetDisplayLabelTextSize();
    }

    public void btnCE_Click(View sender){
        this.lblNumberPrinter.setText("0");
        this.resetDisplayLabelTextSize();
    }

    public void btnErase_Click(View sender){
        this.eraseACharacterInLblPrinter();
        this.resetDisplayLabelTextSize();
    }

    public void btn0_Click(View sender){
        this.printInScreenLabel(0);
    }

    public void btn1_Click(View sender){
        this.printInScreenLabel(1);
    }

    public void btn2_Click(View sender){
        this.printInScreenLabel(2);
    }

    public void btn3_Click(View sender){
        this.printInScreenLabel(3);
    }

    public void btn4_Click(View sender){
        this.printInScreenLabel(4);
    }

    public void btn5_Click(View sender){
        this.printInScreenLabel(5);
    }

    public void btn6_Click(View sender){
        this.printInScreenLabel(6);
    }

    public void btn7_Click(View sender){
        this.printInScreenLabel(7);
    }

    public void btn8_Click(View sender){
        this.printInScreenLabel(8);
    }

    public void btn9_Click(View sender){
        this.printInScreenLabel(9);
    }

    public void btnMultiply_Click(View sender){
        this.preExecuteOperation((char)215);
    }

    public void btnSoustract_Click(View sender){
        this.preExecuteOperation('-');
    }

    public void btnAdd_Click(View sender){
        this.preExecuteOperation('+');
    }

    public void btnDivide_Click(View sender){
        this.preExecuteOperation((char)247);
    }

    public void btnNegate_Click(View sender){
        if(this.lblNumberPrinter.getText().toString() != "0")
        {
            if(this.isCharactereIsPresentIn(this.lblNumberPrinter.getText().toString(), '-'))
            {
                this.lblNumberPrinter.setText(this.lblNumberPrinter.getText().toString().substring(1));
            }
            else
            {
                this.lblNumberPrinter.setText("-" + this.lblNumberPrinter.getText().toString());
            }
        }
    }

    public void btnEqual_Click(View sender){
        this.resetDisplayLabelTextSize();
        this.executeOperation();
        this.lblOperationHistory.setText("");
        this.opChar = ' ';
    }

    public void btnComma_Click(View sender){
        this.printInScreenLabel('.');
    }
}
