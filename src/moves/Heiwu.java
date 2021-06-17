package moves;

import fight.Buff;
import fight.FightEnvironment;
import fight.FightHero;
import fight.Move;
import game.Hero;

import java.util.List;

public class Heiwu extends Move {

    public Heiwu() {
    }

    public Heiwu(Move move) {
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
            List<Buff> buffList = fh2.buffs;
            for(Buff buff : buffList){
                if(buff.name.equals("黑雾")){
                    buff.con = 3;
                    return str;
                }
            }
            Buff buff = new Buff("黑雾", 1, 3, 0, 0, 0, true);
            buff.desc = "降低命中30%";
            fh2.buffs.add(buff);
            fh1.nowmp -= this.getConsume();
        }
        return str;
    }
}
