package moves;

import fight.FightEnvironment;
import fight.FightHero;
import fight.Move;
import game.Hero;
import res.MyRandom;

public class Yihewg extends Move {
    public Yihewg() {
    }

    public Yihewg(Move move) {
        super(move);
    }

    @Override
    public String move(FightHero fh1, FightHero fh2, FightEnvironment fe){
        String str = "";
        boolean mpEnough = false;
        if(fh1.nowmp >= this.getConsume()){
            mpEnough = true;
        }
        int hpRestore = fh1.phyatt * 8 /10;
        if(mpEnough){
            fh1.nowmp -= this.getConsume();
            if(fh1.maxhp - fh1.nowhp < hpRestore){
                hpRestore = fh1.maxhp - fh1.nowhp;
            }
            fh1.nowhp += hpRestore;
            str = Hero.getHeroName(fh1.heroId)+"恢复了"+hpRestore+"点体力";
        }else{
            fh1.nowmp = 0;
            hpRestore = hpRestore * fh1.nowmp / this.getConsume();
            if(fh1.maxhp - fh1.nowhp < hpRestore){
                hpRestore = fh1.maxhp - fh1.nowhp;
            }
            fh1.nowhp += hpRestore;
            str = Hero.getHeroName(fh1.heroId)+"没有力气了，恢复了"+hpRestore+"点体力";
        }
        return str;
    }
}
