package com.github.createThread;


import java.lang.instrument.Instrumentation;

public class PreAgent
{
    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("=========premain方法执行1========");
        System.out.println(agentOps);
    }

    public static void premain(String agentOps) {
        System.out.println("=========premain方法执行2========");
        System.out.println(agentOps);
    }
}
