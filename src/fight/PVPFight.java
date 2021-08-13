package fight;

public class PVPFight extends Fight{
    public String player1Acc;
    public String player2Acc;
    public PVPFight(FightEnvironment fightEnvironment, FightHero fightHero1, FightHero fightHero2) {
        super(fightEnvironment, fightHero1, fightHero2);
    }

}
