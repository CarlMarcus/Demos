package com.github.reflectionDemo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class Test {

    public static void printFields() {
        //1.获取并输出类的名称
        Class mClass = Son.class;
        System.out.println("类的名称"+mClass.getName());

        //2.1 获取所有 public 访问权限的属性（包括父类）
        //Field[] fields = mclass.getFields();
        //2.2 获取所有本类声明的变量（不问访问权限，但只包括本类，不包括父类）
        Field[] fields = mClass.getDeclaredFields();
        for (Field field: fields) {
            //获取该属性字段的访问权限
            int modifiers = field.getModifiers();
            System.out.print("权限修饰符："+Modifier.toString(modifiers) + " ");
            //获取该属性字段的类型和名称
            System.out.println("属性类型："+field.getType()+" 属性名："+field.getName());
        }
    }

    public static void printMethods() {
        Class mClass = Son.class;
        System.out.println("类的名称："+mClass.getName());

        //2.1 获取所有 public 访问权限的方法 (包括自己声明和从父类继承的)
        //Method[] methods = mClass.getMethods();
        //2.2 获取所有本类的的方法（不问访问权限，只包含自己不包含父类）
        Method[] methods = mClass.getDeclaredMethods();
        for(Method method: methods) {
            //获取并输出方法的访问权限（Modifiers：修饰符）
            int modifiers = method.getModifiers();
            System.out.print(Modifier.toString(modifiers)+" ");
            //获取方法的返回值类型和方法名称
            Class returnType = method.getReturnType();
            System.out.print(returnType.getName() + " " + method.getName() + "( ");
            //获取方法的参数列表
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter: parameters) {
                System.out.print(parameter.getType().getName()+" "+parameter.getName()+", ");
            }
            //获取并抛出方法的异常
            Class[] exceptionTypes = method.getExceptionTypes();
            if (exceptionTypes.length == 0){
                System.out.print(" )");
            } else if (exceptionTypes.length == 1) {
                System.out.print(") throws "+exceptionTypes[0].getName());
            } else {
                System.out.print(") throws "+exceptionTypes[0].getName());
                for (int i=1; i< exceptionTypes.length; i++) {
                    System.out.print(", "+exceptionTypes[i].getName());
                }
            }
            System.out.println();
        }
    }

    /**
     * 访问对象的私有方法
     * 为简洁代码，在方法上抛出总的异常，实际开发别这样
     */
    private static void getPrivateMethod() throws Exception{
        //1. 获取 Class 类实例
        TestClass testClass = new TestClass();
        Class mClass = testClass.getClass();
        //2. 获取私有方法
        //第一个参数为要获取的私有方法的名称
        //第二个为要获取方法的参数的类型，参数为 Class...，没有参数就是null
        //方法参数也可这么写 ：new Class[]{String.class , int.class}
        Method privateMethod =
                mClass.getDeclaredMethod("privateHeadTail", String.class, int.class);
        //3. 开始操作方法
        if (privateMethod != null) {
            //获取私有方法的访问权
            //只是获取访问权，并不是修改实际权限
            privateMethod.setAccessible(true);
            //使用 invoke 反射调用私有方法
            //privateMethod 是获取到的私有方法
            //testClass 要操作的对象
            //后面两个参数传实参
            privateMethod.invoke(testClass, "Java Reflect ", 666);
        }
    }

    private static void modifyPrivateFiled() throws Exception {
        //1. 获取 Class 类实例
        TestClass testClass = new TestClass();
        Class mClass = testClass.getClass();
        //2. 获取私有变量
        Field privateField = mClass.getDeclaredField("MSG");
        //3. 操作私有变量
        if (privateField != null) {
            //获取私有变量的访问权
            privateField.setAccessible(true);
            //修改私有变量，并输出以测试
            System.out.println("Before Modify：MSG = " + testClass.getMsg());
            //调用 set(object , value) 修改变量的值
            //privateField 是获取到的私有变量
            //testClass 要操作的对象
            //"Modified" 为要修改成的值
            privateField.set(testClass, "Modified");
            System.out.println("After Modify：MSG = " + testClass.getMsg());
        }
    }

    /*
    Java 虚拟机（JVM）在编译 .java 文件得到 .class 文件时，会优化我们的代码以提升效率。其中一个优化就是：
    JVM 在编译阶段会把引用常量的代码替换成具体的常量值。但是，并不是所有常量都会优化。经测试对于 int、long、
    boolean 以及 String 这些基本类型 JVM 会优化，而对于 Integer、Long、Boolean 这种包装类型，或者其他
    诸如 Date 、Object 类型则不会被优化。
    总结来说：对于基本类型的静态常量，JVM 在编译阶段会把引用此常量的代码替换成具体的常量值。这么说来，
    在实际开发中，如果我们想修改某个类的常量值，恰好那个常量是基本类型的，岂不是无能为力了？反正我个人
    认为除非修改源码，否则真没办法！这里所谓的无能为力是指：我们在程序运行时刻依然可以使用反射修改常量的值（
    后面会代码验证），但是 JVM 在编译阶段得到的 .class 文件已经将常量优化为具体的值，在运行阶段就直接使用具
    体的值了，所以即使修改了常量的值也已经毫无意义了。
     */
    public static void main(String[] args) throws Exception {
        modifyPrivateFiled();
    }
}
