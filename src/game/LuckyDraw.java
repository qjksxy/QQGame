package game;

import java.util.Random;

public class LuckyDraw {
    private static Random random = null;
    public static String luckyDraw(int num){
        random = new Random();
        String res = "\n";
        for(int i=0; i<num; i++){
            int rand = random.nextInt(100)+1;
            if(rand < 9){
                res += "--S卡碎片*1--\n";
            }else if(rand < 11){
                res += "--S卡碎片*5--\n";
            }else if(rand < 14){
                res += "A1卡碎片*1\n";
            }else if(rand < 21){
                res += "A1卡碎片*4\n";
            }else if(rand < 24){
                res += "A2卡碎片*1\n";
            }else if(rand < 31){
                res += "A2卡碎片*4\n";
            }else if(rand < 35){
                res += "B1卡碎片*1\n";
            }else if(rand < 40){
                res += "B1卡碎片*4\n";
            }else if(rand < 41){
                res += "B1卡整卡\n";
            }else if(rand < 45){
                res += "B2卡碎片*1\n";
            }else if(rand < 50){
                res += "B2卡碎片*4\n";
            }else if(rand < 51){
                res += "B2卡整卡\n";
            }else if(rand < 55){
                res += "B3卡碎片*1\n";
            }else if(rand < 60){
                res += "B3卡碎片*4\n";
            }else if(rand < 61){
                res += "B3卡整卡\n";
            }else if(rand < 71){
                res += "C1卡碎片*1\n";
            }else if(rand < 81){
                res += "C2卡碎片*1\n";
            }else if(rand < 91){
                res += "C3卡碎片*1\n";
            }else if(rand < 101){
                res += "C4卡碎片*1\n";
            }
        }
        return res;
    }


}
