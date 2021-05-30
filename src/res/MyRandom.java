package res;

import java.util.Random;

public class MyRandom {
    public static Random random;
    static{
        random = new Random();
    }
    public static int nextInt(int bound){
        return random.nextInt(bound);
    }
}

