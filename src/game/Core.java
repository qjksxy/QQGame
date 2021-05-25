package game;

import entity.GameUser;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
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
            return "请选择操作：\n签到\n抽卡 [次数]";
        }
        if(msgs[1].equals("签到")){
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
        }else if(msgs[1].equals("抽卡")){
            if(msgs.length == 2){
                returnMsg = LuckyDraw.luckyDraw(gu, 10);
            }else{
                try{
                    int luckyNum = Integer.parseInt(msgs[2]);
                    if(luckyNum>30){
                        returnMsg = "抽卡无保底，梭哈需谨慎，单次最多连续抽卡30次";
                    }else{
                        returnMsg = LuckyDraw.luckyDraw(gu, luckyNum);
                    }
                }catch (Exception e){
                    returnMsg = "抽卡次数错误！\n"+msgs.length+":"+e.toString();
                }
            }
        }else if(msgs[1].equals("物品")) {
            returnMsg = gu.getQqAcc()+":\n金币数量："+gu.getGoldCoin()+"\n铜币数量："+gu.getCopperCoin();
        }else if(msgs[1].equals("帮助") || msgs[1].equals("help")){
            returnMsg = "签到\n抽卡 [次数]\n物品\n帮助";
        }else if(msgs[1].equals("测试")){
            if(msgs.length>2){
                try{
                    int len = Integer.parseInt(msgs[2]);
                    for(int i=0; i<len; i++){
                        returnMsg+="口";
                    }
                }catch (Exception e){
                    return e.toString();
                }


            }
        } else if(msgs[1].equals("测试2")){
            if(msgs.length>2){
                String[] teststr = {"1", "\n", "<", "口", "A", "a", " "};
                Random random = new Random();
                try{
                    int len = Integer.parseInt(msgs[2]);
                    for(int i=0; i<len; i++){
                        returnMsg+=teststr[random.nextInt(teststr.length)];
                    }
                }catch (Exception e){
                    return e.toString();
                }
            }
        }else if(msgs[1].equals("测试3")){
            if(msgs.length>2){
                String[] teststr = {"\n", "A", "a"};
                Random random = new Random();
                try{
                    int len = Integer.parseInt(msgs[2]);
                    for(int i=0; i<len; i++){
                        returnMsg+=teststr[random.nextInt(teststr.length)];
                    }
                }catch (Exception e){
                    return e.toString();
                }
            }
        }
        else {
            returnMsg = "操作参数错误！\n请选择操作：\n签到\n抽卡 [次数]\n物品";
        }
        System.out.println("---"+new Date().toString()+"\nserver:\n" + returnMsg+"\n---");
        hf.close();
        return returnMsg;
    }
}
