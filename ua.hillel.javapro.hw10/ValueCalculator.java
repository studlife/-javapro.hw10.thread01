import java.util.Arrays;

public class ValueCalculator {

    private final int count = 1_000_000;
    private final int countTwo = count / 2;
    private float[] array = new float[count];   // все значение в массиве = 0



    public void fillArray () {

        long start = System.currentTimeMillis();

        Arrays.fill(array, (float) 1.1);    // заполняем весь массив одинаковими значениями

        float[] a = new float[countTwo];
        float[] b = new float[count - countTwo];


        System.arraycopy(array, 0, a, 0, countTwo);
        System.arraycopy(array, countTwo, b, 0, countTwo);


//        Thread thread1 = new Thread(() -> {
//            for (int i = 0; i < a.length; i++) {
//                a[i] = (float)(a[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//            }
//        });
//
//        Thread thread2 = new Thread(() -> {
//            for (int i = 0; i < b.length; i++) {
//                b[i] = (float)(b[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//            }
//        });


        // Альтернативное решение
        Thread thread1 = new Thread(new MyThread(a));
        Thread thread2 = new Thread(new MyThread(b));
        // - - - - - - - - - - -

        thread1.start();
        thread2.start();

        try {
            thread2.join();
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.arraycopy(a, 0, array, 0, countTwo);
        System.arraycopy(b, 0, array, countTwo, countTwo);

        System.out.println("Time: " + (System.currentTimeMillis() - start) + " ms");
    }
}