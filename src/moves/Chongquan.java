package moves;

import fight.FightEnvironment;
import fight.FightHero;
import fight.Move;
import game.Hero;
import res.MyRandom;

public class Chongquan extends Move {
    @Override
    public String move(FightHero fh1, FightHero fh2, FightEnvironment fe){
        String str = "";
        int acc = MyRandom.nextInt(6*fh1.acc);
        boolean mpEnough = false;
        if(fh1.nowmp >= this.getConsume()){
            mpEnough = true;
        }
        if(acc < fh2.miss && acc < fh1.acc*5){
            str = Hero.getHeroName(fh2.heroId) + "躲开了攻击";
        }else{
            int hurt = this.getHpHurt(fh1, fh2, fe);
            if(MyRandom.nextInt(100) < 10){
                hurt  = hurt + hurt * fh1.crit / 100;
                str += Hero.getHeroName(fh1.heroId) + "打出了会心一击！\n";
            }
            if(!mpEnough){
                hurt = hurt * fh1.nowmp / this.getConsume();
                str = Hero.getHeroName(fh1.heroId)+"已经没有力气了!\n" + str;
            }
            str += Hero.getHeroName(fh2.heroId)+"受到了" + hurt + "点伤害";
            fh2.nowhp -= hurt;
        }
        if(mpEnough){
            fh1.nowmp -= this.getConsume();
        }else{
            fh1.nowmp = 0;
        }

        return str;
    }

    public Chongquan(Move move){
        super(move);
    }

    public Chongquan() {}
}
