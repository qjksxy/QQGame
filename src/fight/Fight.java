package fight;

import java.nio.charset.StandardCharsets;

public class Fight {
    public FightEnvironment fightEnvironment;
    public FightHero fightHero1;
    public FightHero fightHero2;
    public Move move1;
    public Move move2;
    public boolean hero1HasSelectedMove;
    public boolean hero2HasSelectedMove;
    public Fight(FightEnvironment fightEnvironment, FightHero fightHero1, FightHero fightHero2){
        this.fightEnvironment = fightEnvironment;
        this.fightHero1 = fightHero1;
        this.fightHero2 = fightHero2;
    }

    public String dealBuffsBefore(FightHero fightHero){
        String str = "";
        for(Buff buff : fightHero.buffs){
            switch (buff.name){
                case "烧伤":
                    str = "烧伤";
            }
        }
        return str;
    }

    public String dealBuffsAfter(FightHero fightHero){
        String str = "";
        for(Buff buff : fightHero.buffs){
            switch (buff.name){
                case "烧伤":
                    str = "烧伤";
            }
        }
        return str;
    }

    //判断英雄1是否先手
    public boolean isHero1First(){
        if(move1.getExtraValue("强先手").equals("true")){
            if(move2.getExtraValue("强先手").equals("true")){
                return fightHero1.speed >= fightHero2.speed;
            }else{
                return true;
            }
        }else if(move1.getExtraValue("先手").equals("true")){
            if(move2.getExtraValue("强先手").equals("true")){
                return false;
            }else if(move2.getExtraValue("先手").equals("true")){
                return fightHero1.speed >= fightHero2.speed;
            }else{
                return true;
            }
        }else if(move1.getExtraValue("后手").equals("true")){
            if(move2.getExtraValue("后手").equals("true")){
                return fightHero1.speed >= fightHero2.speed;
            }else if(move2.getExtraValue("强后手").equals("true")){
                return true;
            }else {
                return false;
            }
        }else if(move1.getExtraValue("强后手").equals("true")){
            if(move2.getExtraValue("强后手").equals("true")){
                return fightHero1.speed >= fightHero2.speed;
            }else{
                return false;
            }
        }else if(move2.getExtraValue("后手").equals("true")
                || move2.getExtraValue("强后手").equals("true")){
            return true;
        }else if(move2.getExtraValue("先手").equals("true")
                || move2.getExtraValue("强先手").equals("true")){
            return false;
        }
        else {
            return fightHero1.speed >= fightHero2.speed;
        }
    }

    public String fight(){
        String str = "";


        if(hero1HasSelectedMove && hero2HasSelectedMove){
            //双方选择技能完毕，回合开始
            dealBuffsBefore(fightHero1);
            dealBuffsBefore(fightHero2);
            hero1HasSelectedMove = false;
            hero2HasSelectedMove = false;
            if(isHero1First()){
                move1.move(fightHero1, fightHero2, fightEnvironment);
                move2.move(fightHero2, fightHero1, fightEnvironment);
            }else{
                move2.move(fightHero2, fightHero1, fightEnvironment);
                move1.move(fightHero1, fightHero2, fightEnvironment);
            }

            dealBuffsAfter(fightHero1);
            dealBuffsAfter(fightHero2);
        }
        return null;
    }

    public void setHero1Move(Move move){
        move1 = move;
        hero1HasSelectedMove = true;
    }
    public void setHero2Move(Move move){
        move2 = move;
        hero2HasSelectedMove = true;
    }
}