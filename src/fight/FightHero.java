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
    private int id;
    private int types;
    private int maxhp;
    private int maxmp;
    private int nowhp;
    private int nowmp;
    private int phyatt;
    private int phydef;
    private int magatt;
    private int magdef;
    private int acc;
    private int miss;
    private int crit;
    private int speed;
    private String userAcc;
    public FightHero(UserHero uh){
        this.id = uh.getId();
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
    }

    //随机从可用技能中产生一个技能使用, 或指定技能
    public Move getMove(int i){
        if(i==AI_GET_MOVE){
            LinkedList<Move> movesCanUse = new LinkedList<>();
            for(Move move : moves){
                if(move.getMpConsume() < nowmp){
                    movesCanUse.add(move);
                }
            }
            Random random = new Random();
            random.nextInt(movesCanUse.size());
            return movesCanUse.get(random.nextInt());
        }else{
            return moves.get(i);
        }
    }
}