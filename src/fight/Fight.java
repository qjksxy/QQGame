package fight;

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

    public void dealBuffs(FightHero fightHero){
        for(Buff buff : fightHero.buffs){
            switch (buff.name){
                case "烧伤":

            }
        }
    }
    public String fight(){
        String str = "";
        if(hero1HasSelectedMove && hero2HasSelectedMove){
            hero1HasSelectedMove = false;
            hero2HasSelectedMove = false;

            move1.move(fightHero1, fightHero2, fightEnvironment);
            move2.move(fightHero2, fightHero1, fightEnvironment);
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