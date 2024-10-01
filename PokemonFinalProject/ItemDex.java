import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ItemDex {
    private static Map<String, Item> itemDataMap = new HashMap<>();

    static {
        itemDataMap.put("Small HP Potion", new Item("Small HP Potion", "Heal", 80));
        itemDataMap.put("HP Potion", new Item("HP Potion", "Heal", 150));
        itemDataMap.put("Fire Bomb", new ElementStone("Fire Bomb", Element.FIRE, 50));
        itemDataMap.put("Water Bomb", new ElementStone("Water Bomb", Element.WATER, 50));
        itemDataMap.put("Ground Bomb", new ElementStone("Ground Bomb", Element.EARTH, 50));
        itemDataMap.put("Wind Bomb", new ElementStone("Wind Bomb", Element.WIND, 50));
        itemDataMap.put("Ice Bomb", new ElementStone("Ice Bomb", Element.ICE, 50));
    }

    public static Item getItemData(String name) {
        return itemDataMap.get(name);
    }

    public static Item getRandomElementStone() {
        String[] elements = {"Fire Bomb", "Water Bomb", "Ground Bomb", "Wind Bomb", "Lightning Bomb", "Ice Bomb"};
        Random random = new Random();
        return itemDataMap.get(elements[random.nextInt(elements.length)]);
    }
}
