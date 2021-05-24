package entity;

import java.util.Date;

public class GameUser {
    private String qqAcc;
    private Integer goldCoin;
    private Integer copperCoin;
    private Date recentDate;

    public Date getRecentDate() {
        return recentDate;
    }

    public Integer getCopperCoin() {
        return copperCoin;
    }

    public Integer getGoldCoin() {
        return goldCoin;
    }

    public String getQqAcc() {
        return qqAcc;
    }

    public void setGoldCoin(Integer goldCoin) {
        this.goldCoin = goldCoin;
    }

    public void setCopperCoin(Integer copperCoin) {
        this.copperCoin = copperCoin;
    }

    public void setQqAcc(String qqAcc) {
        this.qqAcc = qqAcc;
    }

    public void setRecentDate(Date recentDate) {
        this.recentDate = recentDate;
    }
}
