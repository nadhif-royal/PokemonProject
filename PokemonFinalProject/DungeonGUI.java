import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class DungeonGUI implements BattleArena {
    private Random random = new Random();

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
        JOptionPane.showMessageDialog(null, "Battle Start!! " +  playerMonster.getName() +
                " VS " + wildMonster.getName());

        while (playerMonster.getHP() > 0 && wildMonster.getHP() > 0) {
            String message = "(Enemy) " + wildMonster.getName() + " HP: " + wildMonster.getHP() + "\n" +
                    "(You) " + playerMonster.getName() + " HP: " + playerMonster.getHP();

            String[] options = {"Attack", "Use Item", "Run"};
            int choice = JOptionPane.showOptionDialog(null, message, "Choose your action",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    chooseAttack(playerMonster, wildMonster);
                    break;
                case 1:
                    chooseItem(playerMonster, wildMonster);
                    break;
                case 2:
                    if (runAttempt()) {
                        JOptionPane.showMessageDialog(null, "Yah anda kabur, Game Over deh!");
                        return;
                    } else {
                        JOptionPane.showMessageDialog(null, "Gagal kabur :v");
                        wildMonster.attack(playerMonster);
                    }
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Try again.");
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
        }

        if (playerMonster.getHP() <= 0) {
            JOptionPane.showMessageDialog(null, playerMonster.getName() + " has been defeated!");
        } else {
            JOptionPane.showMessageDialog(null, wildMonster.getName() + " has been defeated!");
            if (playerMonster.getLevel() >= wildMonster.getLevel()) {
                int exp = 200;
                playerMonster.addExp(exp);
                JOptionPane.showMessageDialog(null, playerMonster.getName() + " earned " + exp + " experience.");
            } else {
                int diff = wildMonster.getLevel() - playerMonster.getLevel();
                int exp = 200 + (diff * 40);
                playerMonster.addExp(exp);
                JOptionPane.showMessageDialog(null, playerMonster.getName() + " earned " + exp + " experience.");
            }
            dropItem(playerMonster);

            int a = JOptionPane.showConfirmDialog(null, "Try to catch the monster?");
            if (a == JOptionPane.YES_OPTION) {
                attemptCapture(wildMonster, playerMonster.getHomeBase());
            }
        }
    }

    @Override
    public void useItem(PlayerMonster playerMonster, Item item, WildMonster wildMonster, HomeBase homeBase) throws MonsterException {
        JOptionPane.showMessageDialog(null, playerMonster.getName() + " uses " + item.getName() + ".");
        switch (item.getEffect().toLowerCase()) {
            case "heal":
                playerMonster.heal(item.getValue());
                break;
            case "elemental":
                playerMonster.elementalAttack(wildMonster, item.getValue(), item.getElement());
                break;
            default:
                JOptionPane.showMessageDialog(null, "Unknown item effect.");
        }
        homeBase.removeItem(item);
    }

    @Override
    public boolean runAttempt() {
        return random.nextInt(100) < 50;
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
            JOptionPane.showMessageDialog(null, "Successfully captured " + wildMonster.getName() + "!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Capture failed!");
            return false;
        }
    }

    private void chooseAttack(PlayerMonster playerMonster, WildMonster wildMonster) throws MonsterException {
        String[] options = {"Basic Attack", "Special Attack", "Elemental Attack"};
        int choice = JOptionPane.showOptionDialog(null, "Choose your attack:", "Attack Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                playerMonster.attack(wildMonster);
                break;
            case 1:
                playerMonster.specialAttack(wildMonster);
                break;
            case 2:
                playerMonster.elementalAttack(wildMonster);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid choice. Try again.");
                chooseAttack(playerMonster, wildMonster);
        }
    }

    private void chooseItem(PlayerMonster playerMonster, WildMonster wildMonster) throws MonsterException {
        HomeBase homeBase = playerMonster.getHomeBase();
        List<Item> items = homeBase.getInventory();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have no items.");
            return;
        }

        String[] itemNames = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemNames[i] = items.get(i).getName();
        }

        String selectedItem = (String) JOptionPane.showInputDialog(null, "Choose an item:",
                "Choose Item", JOptionPane.QUESTION_MESSAGE, null, itemNames, itemNames[0]);

        if (selectedItem != null) {
            for (Item item : items) {
                if (item.getName().equals(selectedItem)) {
                    useItem(playerMonster, item, wildMonster, homeBase);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid choice.");
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
            JOptionPane.showMessageDialog(null, "You received: " + newItem.getName());
        }
    }
}
