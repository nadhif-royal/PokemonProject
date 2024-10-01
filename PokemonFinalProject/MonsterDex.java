import java.util.HashMap;
import java.util.Map;

public class MonsterDex {
    private static Map<String, MonsterData> monsterDataMap = new HashMap<>();

    static {
        monsterDataMap.put("PokemonApi", new MonsterData("PokemonApi", 180, 20, Element.FIRE));
        monsterDataMap.put("PokemonAir", new MonsterData("PokemonAir", 150, 25, Element.WATER));
        monsterDataMap.put("PokemonTanah", new MonsterData("PokemonTanah", 240, 10, Element.EARTH));
        monsterDataMap.put("PokemonAngin", new MonsterData("PokemonAngin", 120, 35, Element.WIND));
        monsterDataMap.put("PokemonEs", new MonsterData("PokemonEs", 200, 15, Element.ICE));

    }

    public static MonsterData getMonsterData(String name) {
        return monsterDataMap.get(name);
    }
}

class MonsterData {
    private String name;
    private int baseHP;
    private int baseAP;
    private Element element;

    public MonsterData(String name, int baseHP, int baseAP, Element element) {
        this.name = name;
        this.baseHP = baseHP;
        this.baseAP = baseAP;
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public int getBaseAP() {
        return baseAP;
    }

    public Element getElement() {
        return element;
    }
}
