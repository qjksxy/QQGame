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
        boolean mpEnough = false;
        if(fh1.nowmp >= this.getConsume()){
            mpEnough = true;
        }
        if(acc < fh2.miss && acc < fh1.acc*5){
            str = Hero.getHeroName(fh2.heroId) + "躲开了攻击";
        }else{
            int hurt = this.getHpHurt(fh1, fh2, fe);
            if(MyRandom.nextInt(100) < 10){
                hurt  = hurt + hurt * fh1.crit / 100;
                str += Hero.getHeroName(fh1.heroId) + "打出了会心一击！\n";
            }
            if(!mpEnough){
                hurt = hurt * fh1.nowmp / this.getConsume();
                str = Hero.getHeroName(fh1.heroId)+"已经没有力气了!\n" + str;
            }
            //
            for(Buff buff : fh2.buffs){
                if(buff.name.equals("护甲")){
                    int power = buff.power;
                    if(power >= hurt){
                        buff.power -= hurt;
                        hurt = 0;
                    }else{
                        hurt -= power;
                        buff.power = 0;
                    }
                }
            }
            str += Hero.getHeroName(fh2.heroId)+"受到了" + hurt + "点伤害";
            fh2.nowhp -= hurt;
        }
        if(mpEnough){
            fh1.nowmp -= this.getConsume();
        }else{
            fh1.nowmp = 0;
        }

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
            if(this.type != 0 && (this.type-fh2.types == -1 || this.type-fh2.types == 4)){
                maghurt = maghurt * 12 / 10;
            }else if(this.type != 0 && (this.type-fh2.types == 1 || this.type-fh2.types == -4)){
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
            case "左正蹬":
                return new Zuozhengd(moveList.get(9));
            case "右鞭腿":
                return new Youbiant(moveList.get(10));
            case "左刺拳":
                return new Zuociq(moveList.get(11));
            case "武德":
                return new Wude(moveList.get(12));
            case "混元功法":
                return new Hunyuangf(moveList.get(13));
            case "有备而来":
                return new Youbeiel(moveList.get(14));
            case "以和为贵":
                return new Yihewg(moveList.get(15));
            case "耗子尾汁":
                return new Haoziwz(moveList.get(16));
            case "闪电五连鞭":
                return new Shandianwlb(moveList.get(17));
            case "大突破":
                return new Datup(moveList.get(18));
            case "真空波":
                return new Zhenkongb(moveList.get(19));
            case "大玉真空波":
                return new Zhenkongb(moveList.get(19));
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
            case "左正蹬":
                return new Zuozhengd();
            case "右鞭腿":
                return new Youbiant();
            case "左刺拳":
                return new Zuociq();
            case "武德":
                return new Wude();
            case "混元功法":
                return new Hunyuangf();
            case "有备而来":
                return new Youbeiel();
            case "以和为贵":
                return new Yihewg();
            case "耗子尾汁":
                return new Haoziwz();
            case "闪电五连鞭":
                return new Shandianwlb();
            case "风·大突破":
                return new Datup();
            case "风·真空波":
                return new Zhenkongb();
            case "风·大玉真空波":
                return new Dayuzkb();
            case "风·风切":
                return new Fengqie();
            case "风·风压":
                return new Fengya();
            case "风·压害":
                return new Yahai();
            case "雷·黑豹":
                return new Heibao();
            case "雷·雷切":
                return new Leiqie();
            case "雷·雷铠":
                return new Leikai();
            case "雷·伪暗":
                return new Weian();
            case "土·土流壁":
                return new Tuliub();
            case "土·碎石连段":
                return new Suishild();
            case "土·黄泉沼":
                return new Huangquanz();
            case "水·水龙弹":
                return new Shuilongd();
            case "水·水龙卷":
                return new Shuilongj();
            case "水·水阵壁":
                return new Shuizhenb();
            case "水·水冲波":
                return new Shuichongb();
            case "火·裂炎弹":
                return new Lieyand();
            case "火·火炎弹":
                return new Huoyand();
            case "火·火龙炎弹":
                return new Huolongyd();
            case "火·炎龙放歌":
                return new Yanlongfg();
            case "火·凤仙花爪红":
                return new Fengxianhzh();
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
            case 10:
                return new Zuozhengd(moveList.get(id-1));
            case 11:
                return new Youbiant(moveList.get(id-1));
            case 12:
                return new Zuociq(moveList.get(id-1));
            case 13:
                return new Wude(moveList.get(id-1));
            case 14:
                return new Hunyuangf(moveList.get(id-1));
            case 15:
                return new Youbeiel(moveList.get(id-1));
            case 16:
                return new Yihewg(moveList.get(id-1));
            case 17:
                return new Haoziwz(moveList.get(id-1));
            case 18:
                return new Shandianwlb(moveList.get(id-1));
            case 19:
                return new Datup(moveList.get(id-1));
            case 20:
                return new Zhenkongb(moveList.get(id-1));
            case 21:
                return new Dayuzkb(moveList.get(id-1));
            case 22:
                return new Fengqie(moveList.get(id-1));
            case 23:
                return new Fengya(moveList.get(id-1));
            case 24:
                return new Yahai(moveList.get(id-1));
            case 25:
                return new Heibao(moveList.get(id-1));
            case 26:
                return new Leiqie(moveList.get(id-1));
            case 27:
                return new Leikai(moveList.get(id-1));
            case 28:
                return new Weian(moveList.get(id-1));
            case 29:
                return new Tuliub(moveList.get(id-1));
            case 30:
                return new Suishild(moveList.get(id-1));
            case 31:
                return new Huangquanz(moveList.get(id-1));
            case 32:
                return new Shuilongd(moveList.get(id-1));
            case 33:
                return new Shuilongj(moveList.get(id-1));
            case 34:
                return new Shuizhenb(moveList.get(id-1));
            case 35:
                return new Shuichongb(moveList.get(id-1));
            case 36:
                return new Lieyand(moveList.get(id-1));
            case 37:
                return new Huoyand(moveList.get(id-1));
            case 38:
                return new Huolongyd(moveList.get(id-1));
            case 39:
                return new Yanlongfg(moveList.get(id-1));
            case 40:
                return new Fengxianhzh(moveList.get(id-1));
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