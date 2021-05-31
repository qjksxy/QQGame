package fight;

import java.util.HashMap;
import java.util.Map;

public class FightEnvironment {
    public Map<String, String> feMap = new HashMap<>();
    public String getValue(String name){
        String str = "false";
        if(feMap.containsKey(name)){
            str = feMap.get(name);
        }
        return str;
    }
}