package moves;

import fight.FightEnvironment;
import fight.FightHero;
import fight.Move;
import game.Hero;

public class Chongquan extends Move {
    @Override
    public String move(FightHero fh1, FightHero fh2, FightEnvironment fe){
        int hurt = this.getHpHurt(fh1, fh2, fe);
        fh2.nowhp -= hurt;
        String str = Hero.getHeroName(fh2.heroId)+"受到了"+hurt + "点伤害";
        return str;
    }

    public Chongquan(Move move){
        super(move);
    }

    public Chongquan() {}
}
