import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Dungeon implements BattleArena{
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

    public WildMonster generateWildMonster(PlayerMonster playerMonster) {
        int playerLevel = playerMonster.getLevel();
        int minLevel = Math.max(1, playerLevel - 2);
        int maxLevel = playerLevel + 2;

        if (maxLevel < minLevel) {
            maxLevel = minLevel + 1;
        }

        int levelDifference = maxLevel - minLevel;
        int wildMonsterLevel = minLevel + random.nextInt(levelDifference + 1);

        String wildMonsterName = GameGUI.allMonsters.get(random.nextInt(GameGUI.allMonsters.size()));
        Element wildMonsterElement = Element.values()[random.nextInt(Element.values().length)];

        return new WildMonster(wildMonsterName, wildMonsterLevel);
    }

    @Override
    public void startBattle(PlayerMonster playerMonster, WildMonster wildMonster) throws MonsterException {
        System.out.println("Battle starts between " + "Lv." + playerMonster.getLevel() + " " + playerMonster.getName() + " and " + "Lv." + wildMonster.getLevel() + " " + wildMonster.getName() + " (" + wildMonster.getElement() + ")");
        while (playerMonster.getHP() > 0 && wildMonster.getHP() > 0) {
            System.out.println("(Enemy) " + wildMonster.getName() + " HP: " + wildMonster.getHP());
            System.out.println("(You) " + playerMonster.getName() + " HP: " + playerMonster.getHP());
            System.out.println("Battle Arena");
            System.out.println("Random Attack");
            System.out.println("Random Item");
            System.out.println("Run");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    chooseAttack(playerMonster, wildMonster);
                    break;
                case 2:
                    chooseItem(playerMonster, wildMonster);
                    break;
                case 3:
                    if (runAttempt()) {
                        System.out.println("Kabur!!!");
                        return;
                    } else {
                        System.out.println("Yah Gagal");
                        wildMonster.attack(playerMonster);
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

            if (wildMonster.getHP() > 0) {
                int randomChance = random.nextInt(100);
                if (randomChance < 60) {
                    wildMonster.attack(playerMonster);
                } else if (randomChance < 85) {
                    wildMonster.elementalAttack(playerMonster);
                } else {
                    wildMonster.specialAttack(playerMonster);
                }
            }
            System.out.println();
        }

        if (playerMonster.getHP() <= 0) {
            System.out.println(playerMonster.getName() + " has been defeated!");
        } else {
            System.out.println(wildMonster.getName() + " has been defeated!");
            if(playerMonster.getLevel() >= wildMonster.getLevel()){
                int exp = 200;
                playerMonster.addExp(200);
                System.out.println(playerMonster.getName() + "earned " + exp + " experience.");
            }else{
                int diff = wildMonster.getLevel() - playerMonster.getLevel();
                int exp = 200 + (diff * 40);
                playerMonster.addExp(exp);
                System.out.println(playerMonster.getName() + "earned " + exp + " experience.");
            }
            dropItem(playerMonster);
            
            System.out.println();
            System.out.println("Try to catch the monster?");
            System.out.println("1. yes");
          //  System.out.println("2. no");

            int a = scanner.nextInt();
            switch (a) {
                case 1:
                    attemptCapture(wildMonster, playerMonster.getHomeBase());
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println();
        }
    }

    @Override
    public void useItem(PlayerMonster playerMonster, Item item, WildMonster wildMonster, HomeBase homeBase) throws MonsterException {
        System.out.println(playerMonster.getName() + " uses " + item.getName() + ".");
        switch (item.getEffect().toLowerCase()) {
            case "heal":
                playerMonster.heal(item.getValue());
                break;
            case "elemental":
                playerMonster.elementalAttack(wildMonster, item.getValue(), item.getElement());
                break;
            default:
                System.out.println("Unknown item effect.");
        }
        homeBase.removeItem(item);
    }

    @Override
    public boolean runAttempt() {
        return random.nextInt(100) < 88;
    }

    @Override
    public boolean attemptCapture(WildMonster wildMonster, HomeBase homeBase) {
        Random random = new Random();
        if (random.nextInt(100) < 13) {

            PlayerMonster newMonster = new PlayerMonster(
                wildMonster.getName(),
                wildMonster.getLevel(),
                wildMonster.getBaseHP(),
                wildMonster.getBaseAP(),
                wildMonster.getElement(),
                homeBase
            );

            homeBase.addPlayerMonster(newMonster);
            System.out.println("New Pokemon!!! " + wildMonster.getName() + "!");
            return true;
        } else {
            System.out.println("Monsternya kabur :v");
            return false;
        }
    }

    private void chooseAttack(PlayerMonster playerMonster, WildMonster wildMonster) throws MonsterException {
        System.out.println("ATTACK");
        System.out.println("1. Normal Attack");
        System.out.println("2. Special Attack");
        System.out.println("3. Elemental Attack");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                playerMonster.attack(wildMonster);
                break;
            case 2:
                playerMonster.specialAttack(wildMonster);
                break;
            case 3:
                playerMonster.elementalAttack(wildMonster);
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                chooseAttack(playerMonster, wildMonster);
        }
    }

    private void chooseItem(PlayerMonster playerMonster, WildMonster wildMonster) throws MonsterException {
        HomeBase homeBase = playerMonster.getHomeBase();
        List<Item> items = homeBase.getInventory();
        if (items.isEmpty()) {
            System.out.println("Nothing to see");
            return;
        }

        System.out.println("Select an item to use:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }
        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < items.size()) {
            useItem(playerMonster, items.get(choice), wildMonster, homeBase);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void dropItem(PlayerMonster playerMonster) {
        Random random = new Random();
        if (random.nextDouble() < 0.5) {
            double chance = random.nextDouble();
            Item newItem;
            if (chance < 0.7) {
                newItem = ItemDex.getItemData("Small HP Potion");
            } else {
                newItem = random.nextBoolean() ? ItemDex.getItemData("HP Potion") : ItemDex.getRandomElementStone();
            }
            playerMonster.getHomeBase().addItem(newItem);
            System.out.println("You received: " + newItem.getName());
        } else {
            
        }
    }
    
}
