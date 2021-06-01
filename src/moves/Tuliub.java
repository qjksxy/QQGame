package moves;

import fight.Buff;
import fight.FightEnvironment;
import fight.FightHero;
import fight.Move;
import game.Hero;

public class Tuliub extends Move {
    public Tuliub() {
    }

    public Tuliub(Move move) {
        super(move);
    }

    @Override
    public String move(FightHero fh1, FightHero fh2, FightEnvironment fe) {
        String str = "";
        boolean mpEnough = false;
        if(fh1.nowmp >= this.getConsume()){
            mpEnough = true;
        }
        if(!mpEnough){
            str = Hero.getHeroName(fh1.heroId)+"已经没有力气了!\n" + str;
            fh1.nowmp = 0;
        }else{
            Buff buff = new Buff("护甲", 1, 3, 0, fh1.magatt, 0, true);
            buff.desc = "减免伤害";
            fh1.buffs.add(buff);
            fh1.nowmp -= this.getConsume();
        }
        return str;
    }
}
