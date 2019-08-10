package com.github.reflectionDemo;

public class TestClass {
    private String MSG = "Original";

    private void privateHeadTail(String head , int tail){
        System.out.print(head + tail);
    }
    public String getMsg(){
        return MSG;
    }
}
