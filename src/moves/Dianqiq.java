package moves;

import fight.Buff;
import fight.FightEnvironment;
import fight.FightHero;
import fight.Move;
import game.Hero;

import java.util.List;

public class Dianqiq extends Move {
    public Dianqiq() {
    }

    public Dianqiq(Move move) {
        super(move);
    }

    @Override
    public String move(FightHero fh1, FightHero fh2, FightEnvironment fe) {
        String str = "";
        boolean mpEnough = false;
        if (fh1.nowmp >= this.getConsume()) {
            mpEnough = true;
        }
        if (!mpEnough) {
            str = Hero.getHeroName(fh1.heroId) + "已经没有力气了!\n" + str;
            fh1.nowmp = 0;
        } else {
            List<Buff> buffList = fh2.buffs;
            for (Buff buff : buffList) {
                if (buff.name.equals("电气球")) {
                    buff.con = 5;
                    return str;
                }
            }
            Buff buff = new Buff("电气球", 1, 5, 0, 0, 0, true);
            buff.desc = "特攻增加";
            fh1.buffs.add(buff);
            fh1.nowmp -= this.getConsume();
        }
        return str;
    }
}
