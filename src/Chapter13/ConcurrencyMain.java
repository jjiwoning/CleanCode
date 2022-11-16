package Chapter13;

public class ConcurrencyMain {
    public static void main(String[] args) {
        final X x = new X();
        x.setLastIdUsed(42);

        Runnable runnable = x::getNextId;

//        Thread thread1 = new Thread(runnable);
//        Thread thread2 = new Thread(runnable);
//
//        thread1.start();
//        thread2.start();

        for (int i = 0; i < 100; i++) {
            x.setLastIdUsed(42);
            Thread thread1 = new Thread(runnable);
            Thread thread2 = new Thread(runnable);
            System.out.println("================");
            thread1.start();
            thread2.start();
            System.out.println("==============");
        }
    }
}
