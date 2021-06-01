package res;

public class Text {
    public static final String version = "0.4.1 测试对战";
    public static final String help = "版本(version)\n"+
            "帮助(help) [指令]\n"+
            "签到(sign)\n"+
            "物品(item)\n"+
            "抽卡(draw) [次数，默认为10]\n"+
            "碎片\n"+
            "合成\n"+
            "&"+
            "角色(hero) [角色序号...]\n"+
            "挑衅 出战角色序号\n"+
            "逃跑\n"+
            "技能 技能序号\n"+
            "技能选择 角色序号 技能 [被替换技能]\n"+
            "状态(buff)";
    public static String getHelp(String helpKey){
        String str = "请选择正确的指令：\n"+help;
        switch (helpKey){
            case "版本": case "version":
                return "查看当前程序版本信息";
            case "帮助": case "help":
                return "查看可使用的指令列表，附加具体指令名可查看该指令详细信息";
            case "签到": case "sign":
                return "签到，每日可签到一次，同时查看最新公告并领取签到奖励";
            case "物品": case "item":
                return "查看当前获得的金币铜币数量，以及可使用的物品";
            case "抽卡": case "draw":
                return "抽卡，附加数量可指定抽卡次数，不附加时默认为10，每次抽卡消耗10金币";
            case "碎片":
                return "查看抽卡累计获得到的碎片总数和角色对应星级";
            case "合成":
                return "将可以合成的角色碎片进行合成，同时将可以升星的角色升星";
            case "角色": case "hero":
                return "查看已有角色列表，附加角色序号时可查看该角色详细信息";
            case "挑衅":
                return "选择一名角色，随机挑衅另一名角色，并进入对战状态";
            case "逃跑":
                return "怂，不打了";
            case "技能":
                return "在对战状态下选择技能";
            case "技能选择":
                return "查看角色已掌握技能，并携带指定技能进行战斗，每名角色能携带的最大技能数量为4，超过时需要附加被替换技能";
            case "状态": case "buff":
                return "查看战斗中的双方状态和效果";
        }
        return str;
    }
}
