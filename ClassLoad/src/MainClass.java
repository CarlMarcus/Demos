import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainClass {
    public static void main(String[] args) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException {
        MyClassLoader cl = new MyClassLoader();
        Class<?> c = cl.findClass("Test");
        Object obj = c.newInstance();
        System.out.println("new object instance: "+obj);
        System.out.println("object's class loader: "+obj.getClass().getClassLoader());
        Method md = c.getMethod("show");
        try {
            md.invoke(obj);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
