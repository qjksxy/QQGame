package game;

import entity.Card;
import entity.GameUser;

import java.util.Random;

public class LuckyDraw {
    private static Random random = null;
    public static String luckyDraw(GameUser gu, int num){
        int[] card = new int[10];
        int[] heroPot = {1, 1, 1, 2, 2, 2, 3, 3, 3, 3};
        String[] heroName = new String[10];
        for(int i=0; i<10; i++){
            Hero hero = Hero.findHeroByID(heroPot[i]);
            if(hero!=null){
                heroName[i] = hero.name;
                if(!hero.title.equals("")){
                    heroName[i] += "["+hero.title+"]";
                }
            }

        }
        random = new Random();
        String res = "";
        for(int i=0; i<num; i++){
            if(i==10 || i==20){
                res += "&";
            }
            int rand = random.nextInt(100)+1;
            if(rand < 9){
                card[0]++;
                res += "\n"+heroName[0]+"碎片*1";
            }else if(rand < 11){
                card[0]+=5;
                res += "\n"+heroName[0]+"碎片*5";
            }else if(rand < 14){
                card[1]++;
                res += "\n"+heroName[1]+"碎片*1";
            }else if(rand < 21){
                card[1]+=4;
                res += "\n"+heroName[1]+"碎片*4";
            }else if(rand < 24){
                card[2]++;
                res += "\n"+heroName[2]+"碎片*1";
            }else if(rand < 31){
                card[2]+=4;
                res += "\n"+heroName[2]+"碎片*4";
            }else if(rand < 35){
                card[3]++;
                res += "\n"+heroName[3]+"碎片*1";
            }else if(rand < 40){
                card[3]+=4;
                res += "\n"+heroName[3]+"碎片*4";
            }else if(rand < 41){
                card[3]+=10;
                res += "\n"+heroName[3]+"碎片*10";
            }else if(rand < 45){
                card[4]++;
                res += "\n"+heroName[4]+"碎片*1";
            }else if(rand < 50){
                card[4]+=4;
                res += "\n"+heroName[4]+"碎片*4";
            }else if(rand < 51){
                card[4]+=10;
                res += "\n"+heroName[4]+"碎片*10";
            }else if(rand < 55){
                card[5]++;
                res += "\n"+heroName[5]+"碎片*1";
            }else if(rand < 60){
                card[5]+=4;
                res += "\n"+heroName[5]+"碎片*4";
            }else if(rand < 61){
                card[5]+=10;
                res += "\n"+heroName[5]+"碎片*10";
            }else if(rand < 71){
                card[6]++;
                res += "\n"+heroName[6]+"碎片*1";
            }else if(rand < 81){
                card[7]++;
                res += "\n"+heroName[7]+"碎片*1";
            }else if(rand < 91){
                card[8]++;
                res += "\n"+heroName[8]+"碎片*1";
            }else if(rand < 101){
                card[9]++;
                res += "\n"+heroName[9]+"碎片*1";
            }
        }
        for(int i=0; i<10; i++){
            if(card[i]!=0){
                HiFun hiFun = new HiFun();
                Card card1 = hiFun.findCard(gu.getQqAcc(), heroPot[i]);
                if(card1!=null){
                    card1.setCount(card1.getCount()+card[i]);
                    hiFun.updateCard(card1);
                    hiFun.close();
                }else{
                    card1 = new Card();
                    card1.setCount(card[i]);
                    card1.setHeroId(heroPot[i]);
                    card1.setLevel(0);
                    card1.setUserAcc(gu.getQqAcc());
                    hiFun.saveCard(card1);
                    hiFun.close();
                }

            }
        }
        return res;
    }


}
