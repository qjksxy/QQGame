package fight;

import game.Hero;
import moves.*;
import res.MyRandom;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Move {
    private static List<Move> moveList = new LinkedList<>();
    private int moveId = 0;
    private String name = "";
    private int type = 0;
    private int phyPower = 0;
    private int magPower = 0;
    private int consume = 0;
    private String desc = "";
    private int levlimit = 0;
    private int premise = 0;
    private int heroid = 0;
    private int isnear = 1;
    private int status = 0;
    private int isSelected = 0;

    public static Move getMoveInList(int index){
        return moveList.get(index);
    }

    public Move(){}

    public Move(Move move){
        this.setIsSelected(move.getIsSelected());
        this.setIsnear(move.getIsnear());
        this.setHeroid(move.getHeroid());
        this.setPremise(move.getPremise());
        this.setLevlimit(move.getLevlimit());
        this.setDesc(move.getDesc());
        this.setConsume(move.getConsume());
        this.setMagPower(move.getMagPower());
        this.setPhyPower(move.getPhyPower());
        this.setType(move.getType());
        this.setName(move.getName());
        this.setMoveId(move.getMoveId());
        this.setStatus(move.getStatus());
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public static int getMoveNum(){
        return moveList.size();
    }

    public static void addMoveInList(Move move){
        moveList.add(move);
    }
    //需要Override
    public String getExtraValue(String name){
        String value = "false";
        return value;
    }

    public int getMoveId() {
        return moveId;
    }

    //需要Override
    public String move(FightHero fh1, FightHero fh2, FightEnvironment fe){
        String str = "";
        int acc = MyRandom.nextInt(6*fh1.acc);
        if(acc < fh2.miss && acc < fh1.acc*5){
            str = Hero.getHeroName(fh2.heroId) + "躲开了攻击";
        }else{
            int hurt = this.getHpHurt(fh1, fh2, fe);
            if(MyRandom.nextInt(100) < 10){
                hurt  = hurt + hurt * fh1.crit / 100;
                str += Hero.getHeroName(fh1.heroId) + "打出了会心一击！\n";
            }
            fh2.nowhp -= hurt;
            str += Hero.getHeroName(fh2.heroId)+"受到了" + hurt + "点伤害";
        }
        fh1.nowmp -= this.getConsume();
        return str;
    }

    public int getMpConsume(){
        return consume;
    }

    public int getHpHurt(FightHero fh1, FightHero fh2, FightEnvironment fe){
        int hurt = 0;
        //[(攻击侧的LV×0.4＋2)×技巧威力×攻击侧的攻击力÷防御侧的防御力÷50＋2)×各类修正×(220～250之间)÷250
        if(phyPower != 0){
            hurt += (fh1.level*2/5+2)*(phyPower*fh1.phyatt/fh2.phydef/50+2);
        }
        else if(magPower != 0){
            int maghurt = (fh1.level*2/5+2)*(magPower*fh1.magatt/fh2.magdef/50+2);
            if(this.type-fh2.types == 1 || this.type-fh2.types == 4){
                maghurt = maghurt * 12 / 10;
            }else if(this.type-fh2.types == -1 || this.type-fh2.types == -4){
                maghurt = maghurt * 8 /10;
            }
            hurt += maghurt;
        }
        Random random = new Random();
        hurt = hurt*(random.nextInt(30)+220)/250;
        return hurt;
    }

    public String getName(){
        return name;
    }

    public static Move getMoveByName(String moveName){
        switch (moveName){
            case "冲拳":
                return new Chongquan(moveList.get(0));
            case "踢腿":
                return new Titui(moveList.get(1));
            case "重拳":
                return new Zhongquan(moveList.get(2));
            case "膝击":
                return new Xiji(moveList.get(3));
            case "野蛮冲撞":
                return new Yemancz(moveList.get(4));
            case "小旋风":
                return new Xiaoxuanf(moveList.get(5));
            case "大旋风":
                return new Daxuanf(moveList.get(6));
            case "刚力旋风":
                return new Ganglixf(moveList.get(7));
            case "刚力大旋风":
                return new Ganglidxf(moveList.get(8));
            default:
                return null;
        }
    }

    public static Move getMoveByNameNo(String moveName){
        switch (moveName){
            case "冲拳":
                return new Chongquan();
            case "踢腿":
                return new Titui();
            case "重拳":
                return new Zhongquan();
            case "膝击":
                return new Xiji();
            case "野蛮冲撞":
                return new Yemancz();
            case "小旋风":
                return new Xiaoxuanf();
            case "大旋风":
                return new Daxuanf();
            case "刚力旋风":
                return new Ganglixf();
            case "刚力大旋风":
                return new Ganglidxf();
            default:
                return null;
        }
    }

    public static Move getMoveById(int id){
        switch (id){
            case 1:
                return new Chongquan(moveList.get(id-1));
            case 2:
                return new Titui(moveList.get(id-1));
            case 3:
                return new Zhongquan(moveList.get(id-1));
            case 4:
                return new Xiji(moveList.get(id-1));
            case 5:
                return new Yemancz(moveList.get(id-1));
            case 6:
                return new Xiaoxuanf(moveList.get(id-1));
            case 7:
                return new Daxuanf(moveList.get(id-1));
            case 8:
                return new Ganglixf(moveList.get(id-1));
            case 9:
                return new Ganglidxf(moveList.get(id-1));
            default:
                return null;
        }
    }

    public void setMoveId(int moveId) {
        this.moveId = moveId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPhyPower() {
        return phyPower;
    }

    public void setPhyPower(int phyPower) {
        this.phyPower = phyPower;
    }

    public int getMagPower() {
        return magPower;
    }

    public void setMagPower(int magPower) {
        this.magPower = magPower;
    }

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getLevlimit() {
        return levlimit;
    }

    public void setLevlimit(int levlimit) {
        this.levlimit = levlimit;
    }

    public int getPremise() {
        return premise;
    }

    public void setPremise(int premise) {
        this.premise = premise;
    }

    public int getHeroid() {
        return heroid;
    }

    public void setHeroid(int heroid) {
        this.heroid = heroid;
    }

    public int getIsnear() {
        return isnear;
    }

    public void setIsnear(int isnear) {
        this.isnear = isnear;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}