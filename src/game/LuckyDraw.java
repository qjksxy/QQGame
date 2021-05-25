package game;

import java.util.Random;

public class LuckyDraw {
    private static Random random = null;
    public static String luckyDraw(int num){
        random = new Random();
        String res = "";
        for(int i=0; i<num; i++){
            if(i==10 || i==20){
                res += "&";
            }
            int rand = random.nextInt(100)+1;
            if(rand < 9){
                res += "\n--S卡碎片*1--";
            }else if(rand < 11){
                res += "\n--S卡碎片*5--";
            }else if(rand < 14){
                res += "\nA1卡碎片*1";
            }else if(rand < 21){
                res += "\nA1卡碎片*4";
            }else if(rand < 24){
                res += "\nA2卡碎片*1";
            }else if(rand < 31){
                res += "\nA2卡碎片*4";
            }else if(rand < 35){
                res += "\nB1卡碎片*1";
            }else if(rand < 40){
                res += "\nB1卡碎片*4";
            }else if(rand < 41){
                res += "\nB1卡整卡";
            }else if(rand < 45){
                res += "\nB2卡碎片*1";
            }else if(rand < 50){
                res += "\nB2卡碎片*4";
            }else if(rand < 51){
                res += "\nB2卡整卡";
            }else if(rand < 55){
                res += "\nB3卡碎片*1";
            }else if(rand < 60){
                res += "\nB3卡碎片*4";
            }else if(rand < 61){
                res += "\nB3卡整卡";
            }else if(rand < 71){
                res += "\nC1卡碎片*1";
            }else if(rand < 81){
                res += "\nC2卡碎片*1";
            }else if(rand < 91){
                res += "\nC3卡碎片*1";
            }else if(rand < 101){
                res += "\nC4卡碎片*1";
            }
        }
        return res;
    }


}
