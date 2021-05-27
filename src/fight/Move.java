package fight;

import moves.Lieyand;

public class Move {
    private int moveId = 0;

    public int getMoveId() {
        return moveId;
    }

    public String move(FightHero fh1, FightHero fh2, FightEnvironment fe){
        return null;
    }
    public int getMpConsume(){
        return 0;
    }
    public String getName(){return null;}
    public Move getMove(String moveName){
        switch (moveName){
            case "裂炎弹":
                return new Lieyand();
            default:
                return null;
        }
    }
}