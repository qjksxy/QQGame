package fight;

public class Buff {
    public String name;
    public int wait;
    public int con;
    public int status;
    public int power;
    public int level;
    public boolean isBeneficial;

    public Buff(String name, int wait, int con, int status, int power, int level, boolean isBeneficial) {
        this.name = name;
        this.wait = wait;
        this.con = con;
        this.status = status;
        this.power = power;
        this.level = level;
        this.isBeneficial = isBeneficial;
    }
}