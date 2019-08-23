import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bt = loadClassData(name);
        return this.defineClass(name, bt, 0, bt.length);
    }

    private byte[] loadClassData(String className) {
        //read class
        InputStream is = getClass().getClassLoader()
                .getResourceAsStream(className.replace(".","/")+".class");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //write into byte buffer
        int len = 0;
        try {
            while((len=is.read())!=-1) {
                baos.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //convert into byte array
        return baos.toByteArray();
    }
}
