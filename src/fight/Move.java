package fight;
//技能抽象类
public abstract class Move {
    public abstract String move(FightHero fh1, FightHero fh2, FightEnvironment fe);
    public abstract int getMpConsume();
}
