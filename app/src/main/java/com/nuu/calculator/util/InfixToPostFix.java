package com.nuu.calculator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InfixToPostFix {

    public static boolean findOperator(char param){
        return (param=='+' || param=='-' || param=='x' || param=='/' || param=='^' || param=='!');
    }

    public static boolean findOperator(String param){
        return (param.contains("+") || param.contains("-") ||
                param.contains("x") || param.contains("/") ||
                param.contains("^") || param.contains("!"));
    }

    public static boolean findOperator(List<String> param){
        return (param.contains("+") || param.contains("-") ||
                param.contains("x") || param.contains("/") ||
                param.contains("^") || param.contains("!"));
    }

    static int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case 'x':
            case '/':
                return 2;
            case '^':
            case '!':
                return 3;
        }
        return -1;
    }

    public static List<String> infixToPostFix(String s){
        Stack<Character> stack = new Stack<>();
        List<String> postFixList = new ArrayList<>();
        boolean flag = false;
        for(int i=0;i<s.length();i++){
            char word = s.charAt(i);
            if(word==' '){
                continue;
            }
            if(word=='('){
                stack.push(word);
                flag = false;
            }else if(word==')'){
                flag = false;
                while(!stack.isEmpty()){
                    if(stack.peek()=='('){
                        stack.pop();
                        break;
                    }else{
                        postFixList.add(stack.pop()+"");
                    }
                }
            }else if(findOperator(word)){
                flag = false;
                if (!stack.isEmpty()) {
                    while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(word)) {
                        postFixList.add(stack.pop() + "");
                    }
                }
                stack.push(word);
            }else{
                if(flag){
                    String lastNumber = postFixList.get(postFixList.size()-1);
                    lastNumber+=word;
                    postFixList.set(postFixList.size()-1, lastNumber);
                }else
                    postFixList.add(word+"");
                flag = true;
            }
        }
        while(!stack.isEmpty()){
            postFixList.add(stack.pop()+"");
        }
        return postFixList;
    }

//    static String infixToPostFix(String expression) {
//
//        String result = "";
//        Stack<Character> stack = new Stack<>();
//        for (int i = 0; i < expression.length(); i++) {
//            char c = expression.charAt(i);
//
//            //check if char is operator
//            if (precedence(c) > 0) {
//                while (stack.isEmpty() == false && precedence(stack.peek()) >= precedence(c)) {
//                    result += stack.pop();
//                }
//                stack.push(c);
//            } else if (c == ')') {
//                char x = stack.pop();
//                while (x != '(') {
//                    result += x;
//                    x = stack.pop();
//                }
//            } else if (c == '(') {
//                stack.push(c);
//            } else {
//                //character is neither operator nor (
//                result += c;
//            }
//        }
//        for (int i = 0; i <= stack.size(); i++) {
//            result += stack.pop();
//        }
//        return result;
//    }
}