public class TestCglibProxy {
    public static void main(String[] args) {
        CglibProxy cglib = new CglibProxy();
        SayHello proxy = (SayHello) cglib.getProxy(SayHello.class);
        proxy.say("James");
    }
}