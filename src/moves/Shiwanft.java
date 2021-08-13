package moves;

import fight.Buff;
import fight.FightEnvironment;
import fight.FightHero;
import fight.Move;
import game.Hero;
import res.MyRandom;

public class Shiwanft extends Move {
    public Shiwanft() {
    }

    public Shiwanft(Move move) {
        super(move);
    }

    @Override
    public String move(FightHero fh1, FightHero fh2, FightEnvironment fe) {
        String str = "";
        int acc = MyRandom.nextInt(6 * fh1.acc);
        int randExt = MyRandom.nextInt(100);
        boolean mpEnough = false;
        if (fh1.nowmp >= this.getConsume()) {
            mpEnough = true;
        }
        if (acc < fh2.miss && acc < fh1.acc * 5) {
            str = Hero.getHeroName(fh2.heroId) + "躲开了攻击";
        } else {
            int hurt = this.getHpHurt(fh1, fh2, fe);
            if (MyRandom.nextInt(100) < 10) {
                hurt = hurt + hurt * fh1.crit / 100;
                str += Hero.getHeroName(fh1.heroId) + "打出了会心一击！\n";
            }
            if (!mpEnough) {
                hurt = hurt * fh1.nowmp / this.getConsume();
                str = Hero.getHeroName(fh1.heroId) + "已经没有力气了!\n" + str;
            }
            //
            for (Buff buff : fh2.buffs) {
                if (buff.name.equals("护甲")) {
                    int power = buff.power;
                    if (power >= hurt) {
                        buff.power -= hurt;
                        hurt = 0;
                    } else {
                        hurt -= power;
                        buff.power = 0;
                    }
                }
            }
            str += Hero.getHeroName(fh2.heroId) + "受到了" + hurt + "点伤害";
            fh2.nowhp -= hurt;

            if (mpEnough && randExt <= 30) {
                Buff buff = new Buff("麻痹", 1, 1, 0, 0, 0, false);
                buff.desc = "无法释放招式";
                fh2.buffs.add(buff);
            }
        }
        fh1.nowmp -= this.getConsume();
        if(fh1.nowmp < 0) {
            fh1.nowmp = 0;
        }
        return str;
    }
}
