package game;

import entity.*;
import fight.*;
import res.MyRandom;
import res.Text;

import java.io.*;
import java.util.*;

public class Core {
    public static Map<String, Fight> fightMap = new HashMap<>();

    public static GameUser getGameUserByQq(String QQAccount) {
        HiFun hf = new HiFun();
        GameUser gu;
        List<GameUser> list = hf.findUserEq("qqAcc", QQAccount);
        if (list.isEmpty()) {
            gu = new GameUser();
            gu.setQqAcc(QQAccount);
            gu.setCopperCoin(1000);
            gu.setGoldCoin(0);
            hf.addUser(gu);
        } else {
            gu = list.get(0);
        }
        return gu;
    }

    public static UserActivity getUserActivityByQq(String QQAccount) {
        HiFun hf = new HiFun();
        UserActivity ua;
        List<UserActivity> userActivityList = hf.findUserActivity("qqAcc", QQAccount);
        if (userActivityList.isEmpty()) {
            ua = new UserActivity();
            ua.setQqAcc(QQAccount);
            ua.setAct2(0);
            ua.setAct1(0);
            ua.setAct3(0);
            ua.setActE("null");
            hf.addUserActivity(ua);
        } else {
            ua = userActivityList.get(0);
        }
        hf.close();
        return ua;
    }

    public static String core(String msg) {
        if (Test.errorCode != 0) {
            return "程序崩溃，快呼叫菜鸡学者\nErrorCode:" + Test.errorCode;
        }
        System.out.println("---" + new Date().toString() + "\nclient:\n" + msg);
        String[] msgs = msg.split(" ");
        String QQAccount = msgs[0];
        String returnMsg = "";
        GameUser gu;
        UserActivity ua;
        HiFun hf = new HiFun();
        gu = getGameUserByQq(QQAccount);
        ua = getUserActivityByQq(QQAccount);
        if (msgs.length > 1) {
            returnMsg += activity(msgs, gu, ua);
        }
        if (msgs.length == 1) {
            return "请选择操作：\n" + Text.help;
        } else if (msgs[1].equals("签到") || msgs[1].equals("sign")) {
            returnMsg += sign(msgs, gu, hf, QQAccount);
        } else if (msgs[1].equals("版本") || msgs[1].equals("version")) {
            returnMsg += Text.version;
        } else if (msgs[1].equals("抽卡") || msgs[1].equals("draw")) {
            returnMsg += draw(msgs, gu);
        } else if (msgs[1].equals("物品") || msgs[1].equals("item")) {
            returnMsg += seeItems(gu);
        } else if (msgs[1].equals("帮助") || msgs[1].equals("help")) {
            returnMsg += getHelp(msgs);
        } else if (msgs[1].equals("碎片") || msgs[1].equals("chip")) {
            returnMsg += seeCards(gu);
        } else if (msgs[1].equals("合成") || msgs[1].equals("composite")) {
            returnMsg += synthetic(gu);
        } else if (msgs[1].equals("角色") || msgs[1].equals("hero") || msgs[1].equals("英雄")) {
            returnMsg += seeHeros(msgs, gu);
        } else if (msgs[1].equals("挑衅") || msgs[1].equals("pve")) {
            returnMsg += fight(msgs, gu);
        } else if (msgs[1].equals("技能") || msgs[1].equals("move")) {
            returnMsg += setmove(msgs, gu);
        } else if (msgs[1].equals("逃跑") || msgs[1].equals("flee")) {
            returnMsg += exitFight(gu);
        } else if (msgs[1].equals("选择技能") || msgs[1].equals("技能选择") || msgs[1].equals("chmove")) {
            returnMsg += chooseMove(msgs, gu);
        } else if (msgs[1].equals("状态") || msgs[1].equals("buff")) {
            returnMsg += seeBuff(gu);
        } else {
            returnMsg += "操作参数错误！\n" + Text.help;
        }
        System.out.println("---" + new Date().toString() + "\nserver:\n" + returnMsg + "\n---");
        hf.close();
        return returnMsg;
    }

    private static String activity(String[] msgs, GameUser gu, UserActivity ua) {
        String str = "";
        Date signDate = gu.getRecentDate();
        Date nowDate = new Date();
        if (signDate == null &&
                (msgs[1].equals("签到") || msgs[1].equals("sign"))) {
            if (nowDate.getMonth() == 6 &&
                    nowDate.getDate() > 9 &&
                    nowDate.getDate() < 18) {
                str = "招募返利活动赠送50金币\n";
                gu.setGoldCoin(gu.getGoldCoin() + 50);
                HiFun hiFun = new HiFun();
                hiFun.updateUser(gu);
                hiFun.close();
            }
        } else if (signDate != null &&
                (msgs[1].equals("签到") || msgs[1].equals("sign"))) {
            if (signDate.getMonth() != 6 || signDate.getDate() <= 9 || signDate.getDate() >= 18) {
                if (nowDate.getMonth() == 6 &&
                        nowDate.getDate() > 9 &&
                        nowDate.getDate() < 18) {
                    str = "招募返利活动赠送50金币\n";
                    gu.setGoldCoin(gu.getGoldCoin() + 50);
                    HiFun hiFun = new HiFun();
                    hiFun.updateUser(gu);
                    hiFun.close();
                }
            }
        }
        return str;
    }

    private static String seeBuff(GameUser gu) {
        String str = "";
        if (fightMap.containsKey(gu.getQqAcc())) {
            Fight fight = fightMap.get(gu.getQqAcc());
            str = Hero.getHeroName(fight.fightHero1.heroId) + ":\n";
            for (Buff buff : fight.fightHero1.buffs) {
                str += buff.name + ":" + buff.desc + "\n";
            }
            str += "&" + Hero.getHeroName(fight.fightHero2.heroId) + ":\n";
            for (Buff buff : fight.fightHero2.buffs) {
                str += buff.name + ":" + buff.desc + "\n";
            }
        } else {
            str = "您不在战斗状态哦";
        }
        return str;
    }

    private static String getHelp(String[] msgs) {
        if (msgs.length == 2) {
            return Text.help;
        } else {
            return Text.getHelp(msgs[2]);
        }
    }

    private static String chooseMove(String[] msgs, GameUser gu) {
        String str = "";
        if (msgs.length == 2) {
            str = "请选择角色";
        } else if (msgs.length == 3) {
            try {
                int heroId = Integer.parseInt(msgs[2]);
                HiFun hiFun = new HiFun();
                UserHero userHero = hiFun.findUserHero(gu.getQqAcc(), heroId).get(0);
                userHero.getMove();
                for (Move m : userHero.getMoveList()) {
                    str += m.getMoveId() + "：" + m.getName();
                    if (m.getIsSelected() == 1) {
                        str += "  (已选择)\n";
                    } else {
                        str += "  (未选择)\n";
                    }
                    str += "物理威力：" + m.getPhyPower() + "  魔法威力：" + m.getMagPower() +
                            "  耗蓝：" + m.getConsume() + "\n" + m.getDesc() + "\n";
                }
                str += "技能选择 角色序号 技能序号 [被替换技能序号]";
                hiFun.close();
            } catch (Exception e) {
                str = e.toString();
            }
        } else if (msgs.length == 4) {
            int heroId = Integer.parseInt(msgs[2]);
            HiFun hiFun = new HiFun();
            UserHero userHero = hiFun.findUserHero(gu.getQqAcc(), heroId).get(0);
            userHero.getMove();
            int i = 0;
            for (Move m : userHero.getMoveList()) {
                if (m.getIsSelected() == 1) {
                    i++;
                }
            }
            if (i >= 4) {
                str = "角色携带技能已满，请附加被替换技能参数";
            } else {
                str = "失败";
                try {
                    int moveId = Integer.parseInt(msgs[3]);
                    HiFun hiFun1 = new HiFun();
                    List<HeroMove> heroMoveList = hiFun1.findHeroMove(userHero.getId(), false);
                    for (HeroMove hm : heroMoveList) {
                        if (hm.getMoveId() == moveId) {
                            hm.setIsSelected(1);
                            hiFun1.saveHeroMove(hm);
                            hiFun1.close();
                            str = "成功！";
                            break;
                        }
                    }
                } catch (Exception e) {
                    str += e.toString();
                }
                hiFun.close();
            }
        } else {
            int heroId = Integer.parseInt(msgs[2]);
            HiFun hiFun = new HiFun();
            UserHero userHero = hiFun.findUserHero(gu.getQqAcc(), heroId).get(0);
            userHero.getMove();

            str = "失败";
            try {
                int moveId = Integer.parseInt(msgs[3]);
                int moveId2 = Integer.parseInt(msgs[4]);
                HiFun hiFun1 = new HiFun();
                List<HeroMove> heroMoveList = hiFun1.findHeroMove(userHero.getId(), false);
                for (HeroMove hm : heroMoveList) {
                    if (hm.getMoveId() == moveId) {
                        hm.setIsSelected(1);
                        hiFun1.saveHeroMove(hm);
                    }
                    if (hm.getMoveId() == moveId2) {
                        hm.setIsSelected(0);
                        hiFun1.saveHeroMove(hm);
                    }
                }
                hiFun1.close();
                str = "成功！";
            } catch (Exception e) {
                str += e.toString();
            }

        }
        return str;
    }

    private static String exitFight(GameUser gu) {
        String str = "";
        Fight fight = fightMap.get(gu.getQqAcc());
        if (fight == null) {
            str = "您当前不在战斗状态";
        } else {
            str = "成功退出！";
            fightMap.remove(gu.getQqAcc());
        }
        return str;
    }

    private static String setmove(String[] msgs, GameUser gu) {
        String str = "";
        FightHero fightHero;
        Fight fight = fightMap.get(gu.getQqAcc());
        if (fight == null) {
            str = "您当前不在战斗状态，无法选择技能";
        } else {
            fightHero = fight.fightHero1;
            if (msgs.length == 2) {
                str = "请选择技能！\n";
                for (Move move : fightHero.moves) {
                    str += move.getMoveId() + ":" + move.getName() + "\n";
                }
            } else if (msgs.length == 3) {
                try {
                    int moveId = Integer.parseInt(msgs[2]);
                    for (Move m : fightHero.moves) {
                        if (m.getMoveId() == moveId) {
                            fight.setHero1Move(m);
                            fight.setHero2Move(fight.fightHero2.getMove(FightHero.AI_GET_MOVE));
                            str += fight.fight();
                            if (fight.fightHero1.nowhp <= 0) {
                                str += Hero.getHeroName(fight.fightHero2.heroId) + "取得了胜利！\n";
                                if (fight.fightHero1.heroId == 12) {
                                    str += Text.maBaoguo[MyRandom.nextInt(Text.maBaoguo.length)] + "\n";
                                } else if (fight.fightHero2.heroId == 12) {
                                    str += Text.maBaoguoV[MyRandom.nextInt(Text.maBaoguoV.length)] + "\n";
                                }
                                fightMap.remove(gu.getQqAcc());
                            } else if (fight.fightHero2.nowhp <= 0) {
                                str += Hero.getHeroName(fight.fightHero1.heroId) + "取得了胜利！\n";
                                if (fight.fightHero2.heroId == 12) {
                                    str += Text.maBaoguo[MyRandom.nextInt(Text.maBaoguo.length)] + "\n";
                                } else if (fight.fightHero1.heroId == 12) {
                                    str += Text.maBaoguoV[MyRandom.nextInt(Text.maBaoguoV.length)] + "\n";
                                }
                                HiFun hiFun = new HiFun();
                                UserHero userHero = hiFun.findUserHeroById(fight.fightHero1.id);
                                if (userHero.getLevel() < 50) {
                                    if (MyRandom.nextInt(100) < fight.fightHero2.level * 160 / fight.fightHero1.level) {
                                        UserHero.refresh(userHero, userHero.getLevel() + 1, 0);
                                        str += "等级提升了,金币+10!\n";
                                        gu.setGoldCoin(gu.getGoldCoin() + 10);
                                        hiFun.updateUserHero(userHero);
                                        hiFun.updateUser(gu);
                                        hiFun.close();
                                    }
                                }
                                str += userHero.studyMove();
                                fightMap.remove(gu.getQqAcc());
                            } else {
                                str += Hero.getHeroName(fight.fightHero1.heroId) + "\n";
                                str += "hp:" + fight.fightHero1.nowhp + "/" + fight.fightHero1.maxhp + "\n";
                                str += "mp:" + fight.fightHero1.nowmp + "/" + fight.fightHero1.maxmp + "\n";
                                str += Hero.getHeroName(fight.fightHero2.heroId) + "\n";
                                str += "hp:" + fight.fightHero2.nowhp + "/" + fight.fightHero2.maxhp + "\n";
                                str += "mp:" + fight.fightHero2.nowmp + "/" + fight.fightHero2.maxmp + "\n";
                                for (Move move : fight.fightHero1.moves) {
                                    str += move.getMoveId() + ":" + move.getName() + "\n";
                                }
                            }

                        }
                    }
                    if (fight.move1 == null) {
                        return "技能选择失败，请重新选择！";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return e.toString();
                }
            }
        }
        return str;
    }

    private static String fight(String[] msgs, GameUser gu) {
        String str = "";
        if (fightMap.containsKey(gu.getQqAcc())) {
            return "您已经在战斗状态了，请先解决当前战斗或逃跑";
        }
        if (msgs.length == 2) {
            return "请选择参战英雄";
        } else {
            HiFun hiFun = new HiFun();
            try {
                str = "已为您匹配旗鼓相当的对手\n";
                int heroId = Integer.parseInt(msgs[2]);
                UserHero userHero1 = hiFun.findUserHero(gu.getQqAcc(), heroId).get(0);
                userHero1.getMove();
                UserHero userHero2 = hiFun.findRandomUserHero();
                userHero2.getMove();
                FightHero fightHero1 = new FightHero(userHero1);
                FightHero fightHero2 = new FightHero(userHero2);
                Fight fight = new Fight(new FightEnvironment(), fightHero1, fightHero2);
                fightMap.put(gu.getQqAcc(), fight);
                str += Hero.getHeroName(fightHero1.heroId) + "\n" + "HP/MP:" + fightHero1.nowhp + "/" + fightHero1.nowmp + "\n";
                str += Hero.getHeroName(fightHero2.heroId) + "\n" + "HP/MP:" + fightHero2.nowhp + "/" + fightHero2.nowmp + "\n";
                str += "请选择技能:技能 技能编号\n";
                for (Move move : fightHero1.moves) {
                    str += move.getMoveId() + ":" + move.getName() + "\n";
                }
            } catch (Exception e) {
                return e.toString();
            }
            hiFun.close();

        }
        return str;
    }

    private static String seeHeros(String[] msgs, GameUser gu) {
        String returnMsg = "";
        HiFun hf = new HiFun();
        if (msgs.length == 2) {
            List<UserHero> userHeros = hf.findUserHero(gu.getQqAcc(), 0);
            int i = 0;
            for (UserHero userHero : userHeros) {
                i++;
                returnMsg += userHero.getHeroId() + ":" + Hero.getHeroName(userHero.getHeroId()) +
                        "  level:" + userHero.getLevel() + "\n";
                if (i % 5 == 0) {
                    i = 0;
                }
            }
        } else {
            for (int i = 2; i < msgs.length; i++) {
                try {
                    int heroId = Integer.parseInt(msgs[i]);
                    UserHero userHero = hf.findUserHero(gu.getQqAcc(), heroId).get(0);
                    returnMsg += userHero.getHeroId() + ":" + Hero.getHeroName(userHero.getHeroId()) + "\n";
                    returnMsg += "等级：" + userHero.getLevel() + "\n";
                    returnMsg += "生命魔法：" + userHero.getMaxhp() + "/" + userHero.getMaxmp() + "\n";
                    returnMsg += "物攻魔攻：" + userHero.getPhyatt() + "/" + userHero.getMagatt() + "\n";
                    returnMsg += "物抗魔抗：" + userHero.getPhydef() + "/" + userHero.getMagdef() + "\n";
                    returnMsg += "命中闪避：" + userHero.getAcc() + "/" + userHero.getMiss() + "\n";
                    returnMsg += "暴击速度：" + userHero.getCrit() + "/" + userHero.getSpeed() + "&";

                } catch (Exception e) {
                    return "您尚未获得该英雄";
                }

            }
        }
        hf.close();
        return returnMsg;
    }

    private static String synthetic(GameUser gu) {
        String returnMsg = "";
        HiFun hf = new HiFun();
        //获取用户全部碎片
        List<Card> cards = hf.findAllCard(gu.getQqAcc());
        for (Card card : cards) {
            int type = card.getHeroId() % 10;
            int temp;
            if (type == 1) {
                temp = 20;
            } else if (type == 2 || type == 3) {
                temp = 15;
            } else if (type == 4 || type == 5 || type == 6) {
                temp = 10;
            } else {
                temp = 5;
            }
            if (card.getCount() >= card.getLevel() * temp + temp && card.getLevel() < 5) {
                int nowCardLevel = card.getCount() / temp;
                if (nowCardLevel > 5) {
                    nowCardLevel = 5;
                }
                //获得新卡
                if (card.getLevel() == 0) {
                    returnMsg += "\n成功获得" + nowCardLevel + "星" + Hero.getHeroName(card.getHeroId());
                    UserHero userHero = new UserHero(Hero.findHeroByID(card.getHeroId()), nowCardLevel, gu.getQqAcc());
                    card.setLevel(nowCardLevel);
                    hf.updateCard(card);
                    hf.saveUserHero(userHero);
                    HeroMove heroMove = new HeroMove();
                    heroMove.setMoveId(1);
                    heroMove.setUserHeroId(userHero.getId());
                    heroMove.setIsSelected(1);
                    heroMove.setExtraInfo("");
                    hf.saveHeroMove(heroMove);
                } else {
                    //已有卡升星
                    int priIncreate = nowCardLevel - card.getLevel();
                    returnMsg += "\n" + Hero.getHeroName(card.getHeroId()) + card.getLevel() + "星->" + nowCardLevel + "星";
                    card.setLevel(nowCardLevel);
                    hf.updateCard(card);
                    UserHero userHero = hf.findUserHero(gu.getQqAcc(), card.getHeroId()).get(0);
                    UserHero.refresh(userHero, userHero.getLevel(), priIncreate);
                    hf.saveUserHero(userHero);
                }
            }


        }
        hf.close();
        return returnMsg;
    }

    private static String seeCards(GameUser gu) {
        String returnMsg = "";
        HiFun hf = new HiFun();
        List<Card> cards = hf.findAllCard(gu.getQqAcc());
        hf.close();
        for (Card card : cards) {
            returnMsg += "\n" + Hero.getHeroName(card.getHeroId()) + "碎片数量：" + card.getCount();
            returnMsg += "\n" + "星级：" + card.getLevel();
        }
        return returnMsg;
    }

    private static String sign(String[] msgs, GameUser gu, HiFun hf, String QQAccount) {
        String returnMsg = "";
        if (gu.getRecentDate() == null) {
            gu.setRecentDate(new Date());
            int coppercoin = MyRandom.nextInt(100) + 50;
            gu.setCopperCoin(gu.getCopperCoin() + coppercoin);
            gu.setGoldCoin(gu.getGoldCoin() + 50);
            hf.updateUser(gu);
            returnMsg = QQAccount + ":\n签到成功，获得金币50，铜币" + coppercoin;
        } else {
            Date nowDate = new Date();
            if (gu.getRecentDate().getDate() == nowDate.getDate()
                    && gu.getRecentDate().getMonth() == nowDate.getMonth()
                    && gu.getRecentDate().getDay() == nowDate.getDay()) {
                returnMsg = QQAccount + ":\n今天已经签到了哦，请勿重复签到";
            } else {
                int coppercoin = MyRandom.nextInt(100) + 50;
                gu.setRecentDate(new Date());
                gu.setCopperCoin(gu.getCopperCoin() + coppercoin);
                gu.setGoldCoin(gu.getGoldCoin() + 50);
                hf.updateUser(gu);
                returnMsg = QQAccount + ":\n签到成功，获得金币50,铜币" + coppercoin;
            }
        }
        try {
            String[] announce = FileOperation.readFile("/home/temp/QQGame/announce", 2);
            for (String ann : announce) {
                returnMsg += "\n";
                String[] anns = ann.split(" ");
                for (String str : anns) {
                    returnMsg += "\n" + str;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            returnMsg += "\n公告获取失败！";
        }
        return returnMsg;
    }

    private static String draw(String[] msgs, GameUser gu) {
        HiFun hf = new HiFun();
        String returnMsg = "";
        Calendar calendar = Calendar.getInstance();
        boolean inActivity = calendar.get(Calendar.MONTH) <= 8 && calendar.get(Calendar.DATE) <= 20;
        if (msgs.length == 2) {
            if (gu.getGoldCoin() < 100) {
                return "当前金币数量不足，每次抽卡需10金币，您当前剩余金币" + gu.getGoldCoin();
            }
            returnMsg = LuckyDraw.luckyDraw(gu, 10 + (inActivity ? 2 : 0));
            gu.setGoldCoin(gu.getGoldCoin() - 100);
            hf.updateUser(gu);
            hf.close();
        } else {
            try {
                int luckyNum = Integer.parseInt(msgs[2]);
                if (luckyNum > 40) {
                    returnMsg = "抽卡无保底，梭哈需谨慎，单次最多连续抽卡40次";
                } else {
                    if (gu.getGoldCoin() < 10 * luckyNum) {
                        return "金币数量不足，每次抽卡需10金币，您当前剩余金币" + gu.getGoldCoin();
                    }
                    if (inActivity) {
                        luckyNum = luckyNum * 12 / 10;
                    }
                    returnMsg = LuckyDraw.luckyDraw(gu, luckyNum);
                    gu.setGoldCoin(gu.getGoldCoin() - 10 * luckyNum);
                    hf.updateUser(gu);
                    hf.close();
                }
            } catch (Exception e) {
                returnMsg = "抽卡次数错误！\n" + msgs.length + ":" + e.toString();
            }
        }
        return returnMsg;
    }

    private static String seeItems(GameUser gu) {
        return gu.getQqAcc() + ":\n金币数量：" + gu.getGoldCoin() + "\n铜币数量：" + gu.getCopperCoin();
    }
}
