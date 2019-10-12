package Singleton;

public class LazyUnsafe {
    private static LazyUnsafe instance;
    private LazyUnsafe() {}

    public static synchronized LazyUnsafe getInstance() {
        if (instance==null) {
            instance = new LazyUnsafe();
        }
        return instance;
    }
}
