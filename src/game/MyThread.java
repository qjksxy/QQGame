package game;

public class MyThread extends Thread{
    Integer integer = 1;
    @Override
    public synchronized void start() {
        integer++;
        System.out.println(integer);
    }
}
