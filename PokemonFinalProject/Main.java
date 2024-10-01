import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private HomeBase homeBase = new HomeBase();
    private Dungeon dungeon = new Dungeon();
    public static List<String> allMonsters = new ArrayList<>();

    public Main() {
        // Initialize your monster names
        allMonsters.add("FireDragon");
        allMonsters.add("WaterSerpent");
        allMonsters.add("EarthGolem");
        allMonsters.add("AirSprite");
    }

    // Save game
    public void saveProgress(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (PlayerMonster monster : homeBase.getPlayerTeam()) {
                writer.write("TEAM," + monster.getName() + "," + monster.getLevel() + "," + monster.getExp() + "," + monster.getHP() + "," 
                + monster.getAP() + "," +  monster.getElement() + "," + monster.getMaxHP() + "," + monster.getBaseHP() + "," + monster.getBaseAP() + "," + monster.getEvoP());
                writer.newLine();
            }
            for (PlayerMonster monster : homeBase.getPlayerMonsters()) {
                writer.write(monster.getName() + "," + monster.getLevel() + "," + monster.getExp() + "," + monster.getHP() + "," 
                + monster.getAP() + "," +  monster.getElement() + "," + monster.getMaxHP() + "," + monster.getBaseHP() + "," + monster.getBaseAP() + "," + monster.getEvoP());
                writer.newLine();
            }
            for (Item item : homeBase.getInventory()) {
                writer.write("ITEM," + item.getName());
                writer.newLine();
            }
        }
    }

    // Load game
    public void loadProgress(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("ITEM")) {
                    String name = parts[1];
                    Item newItem = ItemDex.getItemData(name);
                    if (newItem != null) {
                        homeBase.addItem(newItem);
                    } else {
                        System.out.println("Item " + name + " not found in ItemDex. Skipping...");
                    }
                } else if (parts[0].equals("TEAM")){
                    String name = parts[1];
                    int level = Integer.parseInt(parts[2]);
                    int exp = Integer.parseInt(parts[3]);
                    int HP = Integer.parseInt(parts[4]);
                    int AP = Integer.parseInt(parts[5]);
                    Element element = Element.valueOf(parts[6]);
                    int maxHP = Integer.parseInt(parts[7]);
                    int baseHP = Integer.parseInt(parts[8]);
                    int baseAP = Integer.parseInt(parts[9]);
                    int evoP = Integer.parseInt(parts[10]); 

                    PlayerMonster monster = new PlayerMonster(name, level, exp, HP, AP, element, maxHP, baseHP, baseAP, evoP, homeBase);
                    homeBase.setPlayerTeam(monster);
                } else {
                    String name = parts[0];
                    int level = Integer.parseInt(parts[1]);
                    int exp = Integer.parseInt(parts[2]);
                    int HP = Integer.parseInt(parts[3]);
                    int AP = Integer.parseInt(parts[4]);
                    Element element = Element.valueOf(parts[5]);
                    int maxHP = Integer.parseInt(parts[6]);
                    int baseHP = Integer.parseInt(parts[7]);
                    int baseAP = Integer.parseInt(parts[8]);
                    int evoP = Integer.parseInt(parts[9]);

                    PlayerMonster monster = new PlayerMonster(name, level, exp, HP, AP, element, maxHP, baseHP, baseAP, evoP, homeBase);
                    homeBase.addPlayerMonster(monster);
                }
            }
        }
    }

    // Main Menu
    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Monster Game!");

        while (true) {
            System.out.println("1. New Game");
            System.out.println("2. Load Game");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    chooseStarterMonster(scanner);
                    mainMenu(scanner);
                    break;
                case 2:
                    try {
                        loadProgress("game_progress.txt");
                        mainMenu(scanner);
                    } catch (IOException e) {
                        System.out.println("Error loading progress: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Main Menu2
    private void mainMenu(Scanner scanner) {
        while (true) {
            System.out.println("1. Explore Dungeon");
            System.out.println("2. Manage HomeBase");
            System.out.println("3. Save Game");
            System.out.println("4. Load Game");
            System.out.println("5. Main Menu");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    goToDungeon();
                    break;
                case 2:
                    manageHomeBase(scanner);
                    break;
                case 3:
                    try {
                        saveProgress("game_progress.txt");
                        System.out.println("Game saved successfully.");
                    } catch (IOException e) {
                        System.out.println("Error saving progress: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        loadProgress("game_progress.txt");
                        System.out.println("Game loaded successfully.");
                    } catch (IOException e) {
                        System.out.println("Error loading progress: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Returning to main menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    // Starter
    private void chooseStarterMonster(Scanner scanner) {
        System.out.println("Choose your starter Monster:");
        System.out.println("1. FireDragon (Fire)");
        System.out.println("2. EarthGolem (Ground)");
        System.out.println("3. WaterSerpent (Water)");
        System.out.println("4. AirSprite (Wind)");

        int choice = scanner.nextInt();
        String monsterName;
        switch (choice) {
            case 1:
                monsterName = "FireDragon";
                break;
            case 2:
                monsterName = "EarthGolem";
                break;
            case 3:
                monsterName = "WaterSerpent";
                break;
            case 4:
                monsterName = "AirSprite";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to FireDragon.");
                monsterName = "FireDragon";
                break;
        }
        PlayerMonster playerMonster = new PlayerMonster(monsterName, 1, homeBase);
        homeBase.setPlayerTeam(playerMonster);
        System.out.println("You have chosen " + playerMonster.getName() + " as your starter Monster!");
    }


    // Dungeon
    private void goToDungeon() {
        if (homeBase.getPlayerTeam().isEmpty()) {
            System.out.println("You don't have any monsters. Please add monsters to your team first.");
            return;
        }
        WildMonster wildMonster = dungeon.generateWildMonster(homeBase.getPlayerTeam().get(0));
        System.out.println("ENEMY SPOTTED = " + wildMonster.getLevel() + " " + wildMonster.getName() + " (" + wildMonster.getElement() + ")!");

        System.out.println("USE YOUR TEAM " + wildMonster.getName() + ":");
        for (int i = 0; i < homeBase.getPlayerTeam().size(); i++) {
            System.out.println((i + 1) + ". " + homeBase.getPlayerTeam().get(i).getName() + " Lv." + homeBase.getPlayerTeam().get(i).getLevel() + " HP: " + homeBase.getPlayerTeam().get(i).getHP() + "/" + homeBase.getPlayerTeam().get(i).getMaxHP());
        }
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        int monsterIndex = scanner.nextInt() - 1;
        if (monsterIndex < 0 || monsterIndex >= homeBase.getPlayerTeam().size()) {
            System.out.println("Invalid choice.");
            return;
        }
        PlayerMonster playerMonster = homeBase.getPlayerTeam().get(monsterIndex);

        try {
            dungeon.startBattle(playerMonster, wildMonster);
        } catch (MonsterException e) {
            System.out.println("Error during battle: " + e.getMessage());
        }
    }


    // Homebase
    private void manageHomeBase(Scanner scanner) {
        while (true) {
            System.out.println("Home Base Management:");
            System.out.println("1. View Monsters");
            System.out.println("2. View Items");
            System.out.println("3. Set team");
            System.out.println("4. Store monster");
            System.out.println("5. Heal Monsters");
            System.out.println("6. Evolve Monster");
            System.out.println("7. Back");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Monsters in Home Base:");
                    for (PlayerMonster monster : homeBase.getPlayerMonsters()) {
                        System.out.println(monster.getName() + " (" + monster.getElement() + ") Lv." + monster.getLevel());
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Inventory:");
                    for (Item item : homeBase.getInventory()) {
                        System.out.println(item.getName());
                    }
                    System.out.println();
                    break;
                case 3:
                    setTeam();
                    System.out.println();
                    break;
                case 4:
                    storeMonster();
                    System.out.println();
                    break;
                case 5:
                    homeBase.healMonsters();
                    System.out.println("All monsters have been healed.");
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Select a monster to evolve:");
                    for (int i = 0; i < homeBase.getPlayerMonsters().size(); i++) {
                        System.out.println((i + 1) + ". " + homeBase.getPlayerMonsters().get(i).getName() + " Energy " + homeBase.getPlayerMonsters().get(i).getEvoP());
                    }
                    int monsterIndex = scanner.nextInt() - 1;
                    if (monsterIndex < 0 || monsterIndex >= homeBase.getPlayerMonsters().size()) {
                        System.out.println("Invalid choice.");
                        break;
                    }
                    PlayerMonster playerMonster = homeBase.getPlayerMonsters().get(monsterIndex);

                    System.out.println("Evolve to?");
                    List<Element> possibleElements = playerMonster.getElement().getEvolutionElements();
                    for (int i = 0; i < possibleElements.size(); i++) {
                        System.out.println((i + 1) + ". " + possibleElements.get(i));
                    }
                    int elementIndex = scanner.nextInt() - 1;
                    if (elementIndex < 0 || elementIndex >= possibleElements.size()) {
                        System.out.println("Invalid choice.");
                        break;
                    }
                    Element newElement = possibleElements.get(elementIndex);
                    
                    try {
                        playerMonster.evolve(newElement);
                    } catch (MonsterException e) {
                        System.out.println("Error evolving monster: " + e.getMessage());
                    }
                    System.out.println();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void setTeam() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Set Team: Choose up to 3 monsters.");
        int index = 1;
        for (PlayerMonster monster : homeBase.getPlayerMonsters()) {
            System.out.println(index + ". " + monster.getName() + " Lv." + monster.getLevel() + " (" + monster.getElement() + ")");
            index++;
        }
        System.out.println("Enter the numbers separated by commas:");

        String input = scanner.nextLine();
        String[] chosenIndexes = input.split(",");
        List<PlayerMonster> chosenTeam = new ArrayList<>();

        for (String indexString : chosenIndexes) {
            int monsterIndex = Integer.parseInt(indexString.trim()) - 1;
            if (monsterIndex >= 0 && monsterIndex < homeBase.getPlayerMonsters().size()) {
                PlayerMonster monsterToAdd = homeBase.getPlayerMonsters().get(monsterIndex);
                if (!chosenTeam.contains(monsterToAdd)) {
                    chosenTeam.add(monsterToAdd);
                    homeBase.setPlayerTeam(monsterToAdd);
                    homeBase.getPlayerMonsters().remove(monsterToAdd); // Remove from playerMonsters
                    if (homeBase.getPlayerTeam().size() >= 3) {
                        break; // Limit to 3 monsters
                    }
                } else {
                    System.out.println(monsterToAdd.getName() + "Lv." + monsterToAdd.getLevel() + " is already in the team.");
                }
            } else {
                System.out.println("Invalid monster index: " + (monsterIndex + 1));
            }
        }
        System.out.println("Team set successfully.");
    }

    private void storeMonster() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Store Monster: Choose a monster to store in Home Base:");
        int index = 1;
        for (PlayerMonster monster : homeBase.getPlayerTeam()) {
            System.out.println(index + ". " + monster.getName() + " Lv." + monster.getLevel() + " (" + monster.getElement() + ")");
            index++;
        }
        System.out.println("Enter the number of the monster to store:");
    
        int monsterIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (monsterIndex > 0 && monsterIndex <= homeBase.getPlayerTeam().size()) {
            PlayerMonster monster = homeBase.getPlayerTeam().get(monsterIndex - 1);
            homeBase.addPlayerMonster(monster);
            System.out.println(monster.getName() + "Lv." + monster.getLevel() + " is back to home");
        } else {
            System.out.println("Invalid monster index.");
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.play();
    }
}
