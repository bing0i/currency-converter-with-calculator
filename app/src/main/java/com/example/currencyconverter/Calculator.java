package com.example.currencyconverter;

public class Calculator {
    private String text;
    private double op1 = 0;
    private double op2 = 0;

    public Calculator(String t) {
        text = t;
        if (text.length() != 0)
            setOperands();
    }

    private void setOperands() {
        if (indexOfOperator() > 0) {
            op1 = Double.parseDouble(text.substring(0, indexOfOperator()));
            op2 = Double.parseDouble(text.substring(indexOfOperator() + 1));
        }
        else
            op1 = Double.parseDouble(text);
    }

    private int indexOfOperator() {
        if (text.indexOf("+") >= 0)
            return text.indexOf("+");
        else if (text.indexOf("-") >= 0)
            return text.indexOf("-");
        else if (text.indexOf("*") >= 0)
            return text.indexOf("*");
        else if (text.indexOf("/") >= 0)
            return text.indexOf("/");
        return -1;
    }

    public double calculate() {
        if (indexOfOperator() == -1)
            return op1;
        char operator = text.charAt(indexOfOperator());
        if (operator == '+')
            return op1 + op2;
        else if (operator == '-')
            return op1 - op2;
        else if (operator == '*')
            return op1 * op2;
        else
            return op1 / op2;
    }
}
