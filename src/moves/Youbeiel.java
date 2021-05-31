package moves;

import fight.Buff;
import fight.FightEnvironment;
import fight.FightHero;
import fight.Move;
import game.Hero;

public class Youbeiel extends Move {
    public Youbeiel() {
    }

    public Youbeiel(Move move) {
        super(move);
    }

    @Override
    public String move(FightHero fh1, FightHero fh2, FightEnvironment fe){
        String str = "";
        boolean mpEnough = false;
        if(fh1.nowmp >= this.getConsume()){
            mpEnough = true;
        }
        if(!mpEnough){
            str = Hero.getHeroName(fh1.heroId)+"已经没有力气了!\n" + str;
            fh1.nowmp = 0;
        }else{
            boolean hasThisBuff = false;
            for(Buff b : fh1.buffs){
                if(b.name.equals("有备而来")){
                    hasThisBuff = true;
                    break;
                }
            }
            if(hasThisBuff == false){
                Buff buff = new Buff("有备而来", 1, 0, 0, 0, 0, true);
                fh1.buffs.add(buff);
                str += Hero.getHeroName(fh1.heroId)+"的命中和闪避都提升了！";
            }else{
                str += Hero.getHeroName(fh1.heroId)+"尝试再次提升命中和闪避，但是失败了";
            }
            fh1.nowmp -= this.getConsume();
        }
        return str;
    }
}
