package com.github.reflectionDemo;

public class Son extends Father {
    private String sonName;
    protected int sonAge;
    public String sonBirthday;

    public void printSonMsg() {
        System.out.print("Son msg - name: "+sonName+" age: "+sonAge);
    }

    private void setSonName(String sonName) {
        this.sonName = sonName;
    }

    private void setSonAge(int sonAge) {
        this.sonAge = sonAge;
    }

    public String getSonName() {
        return sonName;
    }

    public int getSonAge() {
        return sonAge;
    }
}
