import java.util.ArrayList;
import java.util.List;

public class HomeBase {
    private List<PlayerMonster> playerMonsters = new ArrayList<>();
    private List<Item> inventory = new ArrayList<>();
    private List<PlayerMonster> playerTeam = new ArrayList<>();

    public List<PlayerMonster> getPlayerMonsters() {
        return playerMonsters;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public List<PlayerMonster> getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(PlayerMonster monster) {
        this.playerTeam.add(monster);
    }

    public void addPlayerMonster(PlayerMonster monster) {
        playerMonsters.add(monster);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void healMonsters() {
        for (PlayerMonster monster : playerTeam) {
            monster.heal();
        }
    }

    public void evolveMonster(PlayerMonster monster, Element newElement) throws MonsterException {
        monster.evolve(newElement);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public PlayerMonster getMonsterByName(String name) {
        for (PlayerMonster monster : playerMonsters) {
            if (monster.getName().equalsIgnoreCase(name)) {
                return monster;
            }
        }
        return null;
    }
}
