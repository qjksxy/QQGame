package game;

import entity.Card;
import entity.GameUser;
import entity.UserHero;
import res.Text;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Core {
    public static String core(String msg){
        if(Test.status == 1){
            return "程序崩溃，快呼叫菜鸡学者";
        }
        System.out.println("---"+new Date().toString()+"\nclient:\n" + msg);
        String[] msgs = msg.split(" ");
        String QQAccount = msgs[0];
        String returnMsg = "";
        GameUser gu;
        HiFun hf = new HiFun();
        try{
            long QQAcc = Long.parseLong(QQAccount);
        }catch (Exception e){
            returnMsg = "QQ账号错误！\n"+e.toString();
            return returnMsg;
        }
        List<GameUser> list = hf.findUserEq("qqAcc", QQAccount);
        if(list.isEmpty()){
            gu = new GameUser();
            gu.setQqAcc(QQAccount);
            gu.setCopperCoin(1000);
            gu.setGoldCoin(0);
            hf.addUser(gu);
        }else{
            gu = list.get(0);
        }
        if(msgs.length == 1){
            return "请选择操作：\n"+Text.help;
        }else if(msgs[1].equals("签到")){
            returnMsg = sign(msgs, gu, hf, QQAccount);
        }else if(msgs[1].equals("版本")){
            returnMsg = Text.version;
        }else if(msgs[1].equals("抽卡")){
            returnMsg = draw(msgs, gu);
        }else if(msgs[1].equals("物品")){
            returnMsg = seeItems(gu);
        }else if(msgs[1].equals("帮助") || msgs[1].equals("help")){
            returnMsg = Text.help;
        }else if(msgs[1].equals("碎片")){
            returnMsg = seeCards(gu);
        }else if(msgs[1].equals("合成")){
            returnMsg = synthetic(gu);
        }else if(msgs[1].equals("英雄")){
            returnMsg = seeHeros(msgs, gu);
        }
        else {
            returnMsg = "操作参数错误！\n"+Text.help;
        }
        System.out.println("---"+new Date().toString()+"\nserver:\n" + returnMsg+"\n---");
        hf.close();
        return returnMsg;
    }

    private static String seeHeros(String[] msgs, GameUser gu){
        String returnMsg = "";
        HiFun hf = new HiFun();
        if(msgs.length==2){
            List<UserHero> userHeros = hf.findUserHero(gu.getQqAcc(), 0);
            int i = 0;
            for(UserHero userHero : userHeros){
                i++;
                returnMsg += userHero.getHeroId()+":"+Hero.getHeroName(userHero.getHeroId()) + "\n";
                if(i%5==0){
                    i = 0;
                    returnMsg+="&";
                }
            }
        }else{
            for(int i=2; i< msgs.length; i++){
                try{
                    int heroId = Integer.parseInt(msgs[i]);
                    UserHero userHero = hf.findUserHero(gu.getQqAcc(), heroId).get(0);
                    returnMsg += userHero.getHeroId()+":"+Hero.getHeroName(userHero.getHeroId()) + "\n";
                    returnMsg += "等级："+userHero.getLevel() + " 经验："+userHero.getExp() + "\n";
                    returnMsg += "HPMP：" + userHero.getMaxhp() + "/" + userHero.getMaxmp() + "\n";
                    returnMsg += "物攻魔攻：" + userHero.getPhyatt() + "/" + userHero.getMagatt() + "\n";
                    returnMsg += "物抗魔抗：" + userHero.getPhydef() + "/" + userHero.getMagdef() + "\n";
                    returnMsg += "命中闪避：" + userHero.getAcc() + "/" + userHero.getMiss() + "\n";
                    returnMsg += "暴击速度：" + userHero.getCrit() + "/" + userHero.getSpeed() + "&";

                }catch (Exception e){
                    return e.toString();
                }

            }
        }

        return returnMsg;
    }

    private static String synthetic(GameUser gu){
        String returnMsg = "";
        HiFun hf = new HiFun();
        List<Card> cards = hf.findAllCard(gu.getQqAcc());
        int i = 0;
        for(Card card : cards){
            int type = card.getCardId()%10;
            int temp;
            if(type==1){
                temp = 20;
            }else if(type==2 || type==3){
                temp = 15;
            }else if(type==4 || type==5 || type==6){
                temp = 10;
            }else{
                temp = 5;
            }
            if(card.getCount() >= card.getLevel()*temp+temp && card.getLevel()<5){
                int nowCardLevel = card.getCount()/temp;
                if(nowCardLevel>5){
                    nowCardLevel = 5;
                }
                if(card.getLevel()==0){
                    returnMsg += "\n成功获得"+nowCardLevel+"星"+Hero.getHeroName(card.getHeroId());
                    UserHero userHero = new UserHero(Hero.findHeroByID(card.getHeroId()), nowCardLevel, gu.getQqAcc());
                    card.setLevel(nowCardLevel);
                    hf.updateCard(card);
                    hf.saveUserHero(userHero);
                }else{
                    int priIncreate = nowCardLevel - card.getLevel();
                    returnMsg += "\n"+Hero.getHeroName(card.getHeroId())+card.getLevel()+"星->"+nowCardLevel+"星";
                    card.setLevel(nowCardLevel);
                    hf.updateCard(card);
                    UserHero userHero = hf.findUserHero(gu.getQqAcc(), card.getHeroId()).get(0);
                    UserHero.refresh(userHero, userHero.getLevel(), priIncreate);
                    hf.saveUserHero(userHero);
                }
                i++;
                if(i==4){
                    returnMsg+="&";
                    i = 0;
                }
            }


        }
        hf.close();
        return returnMsg;
    }

    private static String seeCards(GameUser gu){
        String returnMsg = "";
        HiFun hf = new HiFun();
        List<Card> cards = hf.findAllCard(gu.getQqAcc());
        int i = 0;
        for(Card card : cards){
            returnMsg += "\n"+Hero.getHeroName(card.getHeroId())+"碎片数量："+card.getCount();
            returnMsg += "\n"+"星级："+card.getLevel();
            i++;
            if(i==4){
                returnMsg+="&";
                i = 0;
            }
        }
        return returnMsg;
    }

    private static String sign(String[] msgs, GameUser gu, HiFun hf, String QQAccount){
        String returnMsg = "";
        if(gu.getRecentDate() == null){
            gu.setRecentDate(new Date());
            gu.setGoldCoin(gu.getGoldCoin()+50);
            hf.updateUser(gu);
            returnMsg = QQAccount+":\n签到成功，金币+50！";
        }else{
            Date nowDate = new Date();
            if(gu.getRecentDate().getDate() == nowDate.getDate()
                    && gu.getRecentDate().getMonth() == nowDate.getMonth()
                    && gu.getRecentDate().getDay() == nowDate.getDay()){
                returnMsg = QQAccount+":\n今天已经签到了哦，请勿重复签到";
            }else{
                gu.setRecentDate(new Date());
                gu.setGoldCoin(gu.getGoldCoin()+50);
                hf.updateUser(gu);
                returnMsg = QQAccount+":\n签到成功，金币+50！";
            }
        }
        try {
            String[] announce = FileOperation.readFile("/home/temp/QQGame/announce", 2);
            for(String ann : announce){
                returnMsg += "&";
                String[] anns = ann.split(" ");
                for(String str : anns){
                    returnMsg += "\n"+str;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            returnMsg += "\n公告获取失败！";
        }
        return returnMsg;
    }

    private static String draw(String[] msgs, GameUser gu){
        HiFun hf = new HiFun();
        String returnMsg = "";
        if(msgs.length == 2){
            if(gu.getGoldCoin() < 100){
                return "当前金币数量不足，每次抽卡需10金币，您当前剩余金币"+gu.getGoldCoin();
            }
            returnMsg = LuckyDraw.luckyDraw(gu, 10);
            gu.setGoldCoin(gu.getGoldCoin() - 100);
            hf.updateUser(gu);
        }else{
            try{
                int luckyNum = Integer.parseInt(msgs[2]);
                if(luckyNum>30){
                    returnMsg = "抽卡无保底，梭哈需谨慎，单次最多连续抽卡30次";
                }else{
                    if(gu.getGoldCoin() < 10*luckyNum){
                        return "当前金币数量不足，每次抽卡需10金币，您当前剩余金币"+gu.getGoldCoin();
                    }
                    returnMsg = LuckyDraw.luckyDraw(gu, luckyNum);
                    gu.setGoldCoin(gu.getGoldCoin() - 10*luckyNum);
                    hf.updateUser(gu);
                }
            }catch (Exception e){
                returnMsg = "抽卡次数错误！\n"+msgs.length+":"+e.toString();
            }
        }
        return returnMsg;
    }

    private static String seeItems(GameUser gu){
        return gu.getQqAcc()+":\n金币数量："+gu.getGoldCoin()+"\n铜币数量："+gu.getCopperCoin();
    }
}
