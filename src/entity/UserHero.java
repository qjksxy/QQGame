package entity;

import fight.Move;
import game.Hero;
import game.HiFun;
import res.MyRandom;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.jar.JarEntry;

public class UserHero {
    private List<Move> moveList;
    private int id;
    private int heroId;
    private int types;
    private int prip;
    private int priattdef;
    private int prioth;
    //hp: ((种族值*2+基础值/4+个体值[0~5])*等级/100+等级+基础值-30)*2+50
    //mp: ((种族值*2+基础值/4+个体值[0~5])*等级/100+等级+基础值-30)*2+50
    private int maxhp;
    private int maxmp;
    //((种族值*2+基础点数/4+个体值*5)*等级/100+5)
    private int phyatt;
    private int phydef;
    private int magatt;
    private int magdef;
    private int acc;
    private int miss;
    private int crit;
    private int speed;
    private String userAcc;
    private int level;
    //exp:level*10
    private int exp;

    public List<Move> getMoveList() {
        return moveList;
    }

    public List<Move> getMoveSelected(){
        List<Move> tmoves = new LinkedList<>();
        for(Move move : moveList){
            System.out.println(move.getMoveId()+":"+move.getName()+"::"+move.getIsSelected());
            if(move.getIsSelected()==1){
                tmoves.add(move);
            }
        }
        return tmoves;
    }

    public void addMoveList(Move move) {
        this.moveList.add(move);
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public int getMaxhp() {
        return maxhp;
    }

    public void setMaxhp(int maxhp) {
        this.maxhp = maxhp;
    }

    public int getMaxmp() {
        return maxmp;
    }

    public void setMaxmp(int maxmp) {
        this.maxmp = maxmp;
    }

    public int getPhyatt() {
        return phyatt;
    }

    public void setPhyatt(int phyatt) {
        this.phyatt = phyatt;
    }

    public int getPhydef() {
        return phydef;
    }

    public void setPhydef(int phydef) {
        this.phydef = phydef;
    }

    public int getMagatt() {
        return magatt;
    }

    public void setMagatt(int magatt) {
        this.magatt = magatt;
    }

    public int getMagdef() {
        return magdef;
    }

    public void setMagdef(int magdef) {
        this.magdef = magdef;
    }

    public int getAcc() {
        return acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public int getMiss() {
        return miss;
    }

    public void setMiss(int miss) {
        this.miss = miss;
    }

    public int getCrit() {
        return crit;
    }

    public void setCrit(int crit) {
        this.crit = crit;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getUserAcc() {
        return userAcc;
    }

    public void setUserAcc(String userAcc) {
        this.userAcc = userAcc;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getPrip() {
        return prip;
    }

    public void setPrip(int prip) {
        this.prip = prip;
    }

    public int getPriattdef() {
        return priattdef;
    }

    public void setPriattdef(int priattdef) {
        this.priattdef = priattdef;
    }

    public int getPrioth() {
        return prioth;
    }

    public void setPrioth(int prioth) {
        this.prioth = prioth;
    }

    public UserHero(){
        super();
    }

    public UserHero(Hero hero, int _level, String userAcc){
        int grade = hero.id%10;
        this.heroId = hero.id;
        if(grade == 3){
            grade = 2;
        }else if(grade > 3 && grade < 7){
            grade = 3;
        }else if(grade > 6 && grade < 10){
            grade = 4;
        }
        if(grade==0){
            grade = 4;
        }
        this.level = (5-grade)*5;
        this.types = Integer.parseInt(hero.type);
        this.exp = 0;
        Random random = new Random();
        this.prip = random.nextInt(5);
        this.priattdef = random.nextInt(5);
        this.prioth = random.nextInt(5);
        this.userAcc = userAcc;
        refresh(this, this.level, _level);
    }

    //升级升星要调用
    public static UserHero refresh(UserHero userHero, int level, int priIncreate){
        //hp: ((种族值*2+基础值/4+个体值[0~5])*等级/100+等级+基础值-30)*2+50
        //mp: ((种族值*2+基础值/4+个体值[0~5])*等级/100+等级+基础值-30)*2+50
        Hero hero = Hero.findHeroByID(userHero.heroId);
        userHero.level = level;
        userHero.prip += priIncreate;
        userHero.prioth += priIncreate;
        userHero.priattdef += priIncreate;
        userHero.maxhp = ((hero.stuhp*2+hero.bashp/4+userHero.prip)*userHero.level/100+userHero.level+hero.bashp-30)*2+50;
        userHero.maxmp = ((hero.stump*2+hero.basmp/4+userHero.prip)*userHero.level/100+userHero.level+hero.basmp-30)*2+50;
        //((种族值*2+基础点数/4+个体值*5)*等级/100+5)
        userHero.phyatt = ((hero.stuphyatt*2+hero.basphyatt/4+userHero.priattdef*5)*userHero.level/100+5);
        userHero.phydef = ((hero.stuphydef*2+hero.basphydef/4+userHero.priattdef*5)*userHero.level/100+5);
        userHero.magatt = ((hero.stumagatt*2+hero.basmagatt/4+userHero.priattdef*5)*userHero.level/100+5);
        userHero.magdef = ((hero.stumagdef*2+hero.basmagdef/4+userHero.priattdef*5)*userHero.level/100+5);
        userHero.acc = ((hero.stuacc*2+hero.basacc/4+userHero.prioth*5)*userHero.level/100+5);
        userHero.miss = ((hero.stumiss*2+hero.basmiss/4+userHero.prioth*5)*userHero.level/100+5);
        userHero.crit = hero.stucrit+hero.bascrit+userHero.prioth+userHero.prip+userHero.priattdef+ userHero.level/4;
        userHero.speed = ((hero.stuspeed*2+hero.basspeed/4+userHero.prioth*5)*userHero.level/100+5);
        return userHero;
    }

    //对战时首先调用该方法
    public void getMove(){
        HiFun hiFun = new HiFun();
        List<HeroMove> heroMoveList = hiFun.findHeroMove(this.id, false);
        List<Move> tmoves = new LinkedList<>();
        for(HeroMove heroMove : heroMoveList){
            System.out.println("HeromoveID:"+heroMove.getId());
            Move move = Move.getMoveById(heroMove.getMoveId());
            move.setIsSelected(heroMove.getIsSelected());
            tmoves.add(move);
        }
        moveList = tmoves;
    }


    //
    public String studyMove(){
        String str = "什么也没学到";
        if(MyRandom.nextInt(100) < 60){
            return str;
        }
        int type1 = this.types/10;
        int type2 = this.types%10;
        Random random = new Random();
        int moveNum = Move.getMoveNum();
        this.getMove();
        int i = 0;
        outer:
        while(i < 5){
            Move move = Move.getMoveById(random.nextInt(moveNum));
            if(move == null){
                break;
            }
            if(move.getType()!=0  && move.getType() != type1 && move.getType() != type2){
                continue;
            } else {
                boolean movePremise = false;
                if(move.getPremise()==0){
                    movePremise = true;
                }
                if(move.getLevlimit() > this.level){
                    i++;
                    continue;
                }
                for(Move m : this.moveList){
                    if(m.getMoveId() == move.getMoveId()){
                        i++;
                        continue outer;
                    }
                    if(move.getPremise() == m.getMoveId()){
                        movePremise = true;
                    }
                }
                if(movePremise){
                    str = "学到了新技能:"+move.getName();
                    HeroMove heroMove = new HeroMove();
                    heroMove.setMoveId(move.getMoveId());
                    heroMove.setUserHeroId(this.id);
                    heroMove.setExtraInfo("");
                    heroMove.setIsSelected(0);
                    HiFun hiFun = new HiFun();
                    hiFun.saveHeroMove(heroMove);
                    hiFun.close();
                    break;
                } else {
                    i++;
                }
            }
        }
        return str;
    }
}
