package com.nuu.calculator;

import android.content.Context;
import android.util.Log;
import androidx.databinding.ObservableField;

import java.math.BigInteger;
import java.util.List;
import static com.nuu.calculator.util.InfixToPostFix.findOperator;
import static com.nuu.calculator.util.InfixToPostFix.infixToPostFix;

public class CalculatorViewModel{
    public final ObservableField<String> resultText = new ObservableField<>();   //textView畫面顯示
    public final ObservableField<String> nameText = new ObservableField<>();   //textView畫面顯示
    private String nowNumber;  //要進行運算的整數
    private String nowText;
    private boolean isDeleteChar = false;

    CalculatorViewModel(){
        clearData();
    }

    //刪除前一個字元
    public void backspace() {
        if (!nowText.isEmpty()) {
            if (nowText.length() > 1) {
                char nowDeleteChar = nowText.charAt(nowText.length() - 2);
                isDeleteChar = !findOperator(nowDeleteChar); //判斷刪除的前一字如果不是運算子，讓他下次可直接輸入運算子
            }
            nowText = nowText.substring(0, nowText.length() - 1);
            resultText.set(nowText);
            if (!nowNumber.isEmpty()) {
                nowNumber.substring(0, nowNumber.length() - 1);
            }
        }
    }

    //c清除資料
    public void clearData() {
        nowNumber = "";
        nowText = "";
        resultText.set(nowText);
    }

    //加法
    public void addition() {
        operatorMethod("+");
    }

    //減法
    public void subtraction() {
        operatorMethod("-");
    }

    //乘法
    public void multiplication() {
        operatorMethod("x");
    }

    //除法
    public void division() {
        operatorMethod("/");
    }

    //!階
    public void factorial() {
        if (!nowText.endsWith("!"))
            operatorMethod("!");
    }

    //左括號
    public void leftParentheses() {
        if (!nowNumber.contains(".")) {
            nowText += "(";
            resultText.set(nowText);
            nowNumber = "";
        }
    }

    //右括號
    public void rightParentheses() {
        if (!nowNumber.isEmpty() && !nowNumber.endsWith(".") && !nowNumber.endsWith(")")) {
            nowText += ")";
            resultText.set(nowText);
        }
    }

    //=顯示運算結果
    public void calculationResult() {
        try {
            if (!nowNumber.isEmpty() || nowText.endsWith("!")) {  //判斷輸入的最後一項是數字
                List<String> postfixList = infixToPostFix(nowText); //中序轉後序
                Log.d("myPostfix", postfixList.toString());
                int nowPosition = 0;
                String result = "";
                while (findOperator(postfixList)) {  //一直抓直到沒運算子
                    String nowChar = postfixList.get(nowPosition);
                    if (findOperator(nowChar)) {    //判斷是不是運算子
                        result = "";
                        Double b = Double.valueOf(postfixList.get(nowPosition - 1));
                        Double a = 0.0;
                        if (nowPosition > 1)
                            a = Double.valueOf(postfixList.get(nowPosition - 2));
                        switch (nowChar) {
                            case "+":
                                result = String.valueOf(a + b);
                                nowPosition -= 2;
                                postfixList.remove(nowPosition);
                                break;
                            case "-":
                                result = String.valueOf(a - b);
                                nowPosition -= 2;
                                postfixList.remove(nowPosition);
                                break;
                            case "x":
                                result = String.valueOf(a * b);
                                nowPosition -= 2;
                                postfixList.remove(nowPosition);
                                break;
                            case "/":
                                result = String.valueOf(a / b);
                                nowPosition -= 2;
                                postfixList.remove(nowPosition);
                                break;
                            case "!":
//                                Long factorialNum = 1L;
                                BigInteger factorialNum = BigInteger.ONE;   //原long超過長度值會有錯誤
                                for(int i=1;i<=b;i++){
                                    factorialNum = factorialNum.multiply(new BigInteger(Integer.toString(i)));
//                                    factorialNum *= i;
                                }
                                result = factorialNum.toString();
                                nowPosition -= 1;
                                break;
                        }
                        postfixList.remove(nowPosition);
                        postfixList.remove(nowPosition);
                        postfixList.add(nowPosition, result);
                    }
                    nowPosition++;
                }
                nowText = postfixList.get(0);
                resultText.set(nowText);
                nowNumber = nowText;
            }
        }catch (Exception e){
            nowText = "Operation is invalid";
            resultText.set(nowText);
            nowNumber = "";
        }
    }

    private void operatorMethod(String operator){
        if (!nowNumber.isEmpty() && !nowNumber.endsWith(".") || nowText.endsWith("!") || isDeleteChar) {
            nowText += operator;
            resultText.set(nowText);
            nowNumber = "";
            isDeleteChar = false;
        }
    }

    // 數字
    public void numberValue(int value){
        if (nowText.equals("Operation is invalid"))
            nowText = "";
        nowNumber += value;
        nowText += value;
        resultText.set(nowText);
    }

    // 小數點
    public void decimalPoint(){
        if (!nowNumber.isEmpty() && !nowNumber.contains(".")) {
            nowNumber += ".";
            nowText += ".";
            resultText.set(nowText);
        }
    }
}
