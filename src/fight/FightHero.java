package fight;

import entity.UserHero;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class FightHero {
    public static final int AI_GET_MOVE = -1;
    public LinkedList<Move> moves;
    public LinkedList<Buff> buffs;
    public ArrayList<Integer> typesList;
    public int id;
    public int heroId;
    public int types;
    public int level;
    public int maxhp;
    public int maxmp;
    public int nowhp;
    public int nowmp;
    public int phyatt;
    public int phydef;
    public int magatt;
    public int magdef;
    public int acc;
    public int miss;
    public int crit;
    public int speed;
    public String userAcc;
    public FightHero(UserHero uh){
        this.id = uh.getId();
        this.heroId = uh.getHeroId();
        this.level = uh.getLevel();
        this.types = uh.getTypes();
        this.maxhp = uh.getMaxhp();
        this.maxmp = uh.getMaxmp();
        this.nowhp = uh.getMaxhp();
        this.nowmp = uh.getMaxmp();
        this.phyatt = uh.getPhyatt();
        this.phydef = uh.getPhydef();
        this.magatt = uh.getMagatt();
        this.magdef = uh.getMagdef();
        this.acc = uh.getAcc();
        this.miss = uh.getMiss();
        this.crit = uh.getCrit();
        this.speed = uh.getSpeed();
        moves = new LinkedList<>();
        buffs = new LinkedList<>();
        typesList = new ArrayList<>();
        if(types/10!=0){
            typesList.add(types/10);
        }
        typesList.add(types%10);
        moves.addAll(uh.getMoveSelected());
        System.out.println("--------------");
        for(Move move : moves){
            System.out.println(move.getName()+"::"+move.getMoveId());
        }
    }

    //随机从可用技能中产生一个技能使用, 或指定技能
    public Move getMove(int i){
        if(i==AI_GET_MOVE){
            LinkedList<Move> movesCanUse = new LinkedList<>();
            for(Move move : moves){

                if(move.getMpConsume() <= nowmp){
                    movesCanUse.add(move);
                }
            }
            Random random = new Random();
            int x = random.nextInt(movesCanUse.size());
            return movesCanUse.get(x);
        }else{
            return moves.get(i);
        }
    }
}