import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameGUI extends JFrame {
    private HomeBase homeBase = new HomeBase();
    private HomeBaseGUI homeBaseGui = new HomeBaseGUI();
    private DungeonGUI dungeon = new DungeonGUI();
    public static List<String> allMonsters = new ArrayList<>();
    private boolean dungeonInBattle = false;

    private ImageIcon mainBackgroundImage;
    private JLabel backgroundLabel;
    private ImageIcon battleBackgroundImage;
    private JLabel battleBackgroundLabel;

    public GameGUI() {
        allMonsters.add("PokemonApi");
        allMonsters.add("PokemonAir");
        allMonsters.add("PokemonTanah");
        allMonsters.add("PokemonAngin");
        allMonsters.add("PokemonEs");

        initGUI();
    }
    private ImageIcon currentBackgroundImage;

    private void initGUI() {
        setTitle("POKEMON PBO");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon("PokemonGemes.jpg");

        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new GridBagLayout());
        setContentPane(backgroundLabel);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton newGameButton = new JButton("START");
        JButton loadGameButton = new JButton("Load Game");
        JButton exitButton = new JButton("Exit Game");

        newGameButton.addActionListener(e -> chooseStarterMonster());
        loadGameButton.addActionListener(e -> {
            try {
                loadProgress("game_progress.txt");
                showMainMenu();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading progress: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        exitButton.addActionListener(e -> System.exit(0));

        mainPanel.add(newGameButton, gbc);
        gbc.gridy++;
        mainPanel.add(loadGameButton, gbc);
        gbc.gridy++;
        mainPanel.add(exitButton, gbc);

        backgroundLabel.add(mainPanel);

        backgroundLabel.setOpaque(true);

        Image img = backgroundImage.getImage();
        Image scaledImg = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImg);
        backgroundLabel.setIcon(scaledBackgroundImage);
    }

    private void chooseStarterMonster() {
        String[] monsters = {"PokemonApi", "PokemonTanah", "PokemonAir", "PokemonAngin", "PokemonEs"};
        String chosenMonster = (String) JOptionPane.showInputDialog(this, "Choose your starter Monster:", "Starter Monster", JOptionPane.QUESTION_MESSAGE, null, monsters, monsters[0]);

        if (chosenMonster != null) {
            PlayerMonster playerMonster = new PlayerMonster(chosenMonster, 1, homeBase);
            homeBase.setPlayerTeam(playerMonster);
            JOptionPane.showMessageDialog(this, "You have chosen " + playerMonster.getName() + " as your starter Monster!", "Starter Chosen", JOptionPane.INFORMATION_MESSAGE);
            showMainMenu();
        }
    }

    private void showMainMenu() {
        ImageIcon backgroundImage = new ImageIcon("PokemonBattle.gif");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new GridBagLayout());

        setContentPane(backgroundLabel);
        revalidate();
        repaint();

        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(3, 1));

        JButton exploreDungeonButton = new JButton("Battle With Random Player");
        JButton manageHomeBaseButton = new JButton("Home Base");
        JButton saveGameButton = new JButton("Save");

        exploreDungeonButton.addActionListener(e -> {
            ImageIcon battleBackgroundImage = new ImageIcon("PokemonBattleV2.gif");
            backgroundLabel.setIcon(battleBackgroundImage);
            goToDungeon();
        });
        manageHomeBaseButton.addActionListener(e -> manageHomeBase());
        saveGameButton.addActionListener(e -> {
            try {
                saveProgress("game_progress.txt");
                JOptionPane.showMessageDialog(this, "Saved!", "Save Game", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving progress: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainMenuPanel.add(exploreDungeonButton);
        mainMenuPanel.add(manageHomeBaseButton);
        mainMenuPanel.add(saveGameButton);

        getContentPane().removeAll();
        getContentPane().add(mainMenuPanel);
        revalidate();
        repaint();
    }




    private void goToDungeon() {
        ImageIcon currentBackgroundImage = (ImageIcon) backgroundLabel.getIcon();

        ImageIcon newBackgroundImage = new ImageIcon("PokemonBattleV2.gif");
        backgroundLabel.setIcon(newBackgroundImage);

        if (homeBase.getPlayerTeam().isEmpty()) {
            JOptionPane.showMessageDialog(this, "You don't have any monsters. Please add monsters to your team first.", "No Monsters", JOptionPane.WARNING_MESSAGE);
            backgroundLabel.setIcon(currentBackgroundImage);
            return;
        }

        dungeonInBattle = true;

        WildMonster wildMonster = dungeon.generateWildMonster(homeBase.getPlayerTeam().get(0));
        JOptionPane.showMessageDialog(this, "Musuh ditemukan! " + wildMonster.getName() + " (" + wildMonster.getElement() + ")!", "Wild Encounter", JOptionPane.INFORMATION_MESSAGE);

        String[] teamOptions = new String[homeBase.getPlayerTeam().size()];
        for (int i = 0; i < homeBase.getPlayerTeam().size(); i++) {
            PlayerMonster monster = homeBase.getPlayerTeam().get(i);
            teamOptions[i] = monster.getName() + " Lv." + monster.getLevel() + " HP: " + monster.getHP() + "/" + monster.getMaxHP();
        }
        String chosenMonster = (String) JOptionPane.showInputDialog(this, "Pilih Pokemon Anda" + ":", "Pilih", JOptionPane.QUESTION_MESSAGE, null, teamOptions, teamOptions[0]);

        if (chosenMonster != null) {
            int monsterIndex = java.util.Arrays.asList(teamOptions).indexOf(chosenMonster);
            PlayerMonster playerMonster = homeBase.getPlayerTeam().get(monsterIndex);

            try {
                dungeon.startBattle(playerMonster, wildMonster);
                JOptionPane.showMessageDialog(this, "Game Over", "Battle", JOptionPane.INFORMATION_MESSAGE);
                dungeonInBattle = false;

                backgroundLabel.setIcon(currentBackgroundImage);
                showMainMenu();
            } catch (MonsterException e) {
                JOptionPane.showMessageDialog(this, "Error during battle: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {

            backgroundLabel.setIcon(currentBackgroundImage);
            showMainMenu();
        }
    }
    private void resizeBackgroundImage(String imagePath) {
        ImageIcon backgroundImage = new ImageIcon(imagePath);
        Image img = backgroundImage.getImage();

        double scale = 2.0;
        int newWidth = (int) (img.getWidth(null) * 10);
        int newHeight = (int) (img.getHeight(null) * 10);

        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImg);
        backgroundLabel.setIcon(scaledBackgroundImage);
    }
    private void manageHomeBase() {
        ImageIcon homeBaseBackgroundImage = new ImageIcon("HomeBaseBackground.jpg");
        JLabel backgroundLabel = new JLabel(homeBaseBackgroundImage);
        backgroundLabel.setLayout(new GridBagLayout());

        setContentPane(backgroundLabel);
        revalidate();
        repaint();

        resizeBackgroundImage("HomeBaseBackground.jpg");

        while (true) {
            String[] options = {"Trained Monsters", "Potions", "Train Monster", "Heal Monsters", "Evolve Monster", "Back"};
            int choice = JOptionPane.showOptionDialog(null, "", "Home Base Management", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    StringBuilder monstersList = new StringBuilder("Monster Ready to Evolve:\n");
                    List<PlayerMonster> playerMonsters = homeBase.getPlayerMonsters();
                    for (PlayerMonster monster : playerMonsters) {
                        monstersList.append(monster.getName()).append(" (").append(monster.getElement()).append(") Lv.").append(monster.getLevel()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, monstersList.toString());
                    break;
                case 1:
                    homeBase.addItem(ItemDex.getItemData("HP Potion"));
                    homeBase.addItem(ItemDex.getItemData("Small HP Potion"));
                    homeBase.addItem(ItemDex.getItemData("Fire Bomb"));
                    homeBase.addItem(ItemDex.getItemData("Water Bomb"));
                    homeBase.addItem(ItemDex.getItemData("Wind Bomb"));
                    homeBase.addItem(ItemDex.getItemData("Ice Bomb"));

                    StringBuilder itemsList = new StringBuilder("Potions Storage:\n");
                    List<Item> items = homeBase.getInventory();
                    for (Item item : items) {
                        itemsList.append(item.getName()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, itemsList.toString());
                    break;

                case 2:
                    storeMonster();
                    break;
                case 3:
                    homeBase.healMonsters();
                    JOptionPane.showMessageDialog(null, "Monster anda sudah sehat :)");
                    break;
                case 4:
                    evolveMonster();
                    break;
                case 5:
                    showMainMenu();
                    return;
                default:
                    showMainMenu();
                    return;

            }
        }
    }




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
                        System.out.println("Item " + name + " not found!");
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

    private void setTeam() {
        StringBuilder message = new StringBuilder("Set Team: Choose up to 3 monsters.\n");
        for (int i = 0; i < homeBase.getPlayerMonsters().size(); i++) {
            PlayerMonster monster = homeBase.getPlayerMonsters().get(i);
            message.append((i + 1)).append(". ").append(monster.getName()).append(" Lv.").append(monster.getLevel()).append(" (").append(monster.getElement()).append(")\n");
        }
        message.append("Enter the numbers separated by commas:");

        String input = JOptionPane.showInputDialog(null, message.toString());
        if (input != null && !input.isEmpty()) {
            String[] chosenIndexes = input.split(",");
            List<PlayerMonster> chosenTeam = new ArrayList<>();

            for (String indexString : chosenIndexes) {
                int monsterIndex = Integer.parseInt(indexString.trim()) - 1;
                if (monsterIndex >= 0 && monsterIndex < homeBase.getPlayerMonsters().size()) {
                    PlayerMonster monsterToAdd = homeBase.getPlayerMonsters().get(monsterIndex);
                    if (!chosenTeam.contains(monsterToAdd)) {
                        chosenTeam.add(monsterToAdd);
                        homeBase.setPlayerTeam(monsterToAdd);
                        homeBase.getPlayerMonsters().remove(monsterToAdd);
                        if (homeBase.getPlayerTeam().size() >= 3) {
                            break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, monsterToAdd.getName() + " Lv." + monsterToAdd.getLevel() + " is already in the team.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid monster index: " + (monsterIndex + 1));
                }
            }
            JOptionPane.showMessageDialog(null, "Team set successfully.");
        }
    }

    private void storeMonster() {
        StringBuilder message = new StringBuilder("Choose a monster to Train:\n");
        for (int i = 0; i < homeBase.getPlayerTeam().size(); i++) {
            PlayerMonster monster = homeBase.getPlayerTeam().get(i);
            message.append((i + 1)).append(". ").append(monster.getName()).append(" Lv.").append(monster.getLevel()).append(" (").append(monster.getElement()).append(")\n");
        }
        message.append("Enter the number of the training monster:");

        String input = JOptionPane.showInputDialog(null, message.toString());
        if (input != null && !input.isEmpty()) {
            int monsterIndex = Integer.parseInt(input.trim());
            if (monsterIndex > 0 && monsterIndex <= homeBase.getPlayerTeam().size()) {
                PlayerMonster monster = homeBase.getPlayerTeam().get(monsterIndex - 1);
                homeBase.addPlayerMonster(monster);
                homeBase.getPlayerTeam().remove(monsterIndex - 1);
                JOptionPane.showMessageDialog(null, monster.getName() + " Lv." + monster.getLevel() + " is Training right now.");

                homeBase.setPlayerTeam(monster);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid monster index.");
            }
        }
    }

    private void evolveMonster() {
        StringBuilder monsterSelection = new StringBuilder("Select a Trained Monster to evolve:\n");
        List<PlayerMonster> playerMonsters = homeBase.getPlayerMonsters();
        for (int i = 0; i < playerMonsters.size(); i++) {
            PlayerMonster monster = playerMonsters.get(i);
            StringBuilder append = monsterSelection.append((i + 1)).append(". ").append(monster.getName()).append(" Energy: ").append(monster.getEvoP()).append("\n");
        }

        String input = JOptionPane.showInputDialog(null, monsterSelection.toString(), "Evolve Monster", JOptionPane.PLAIN_MESSAGE);
        if (input != null && !input.isEmpty()) {
            int monsterIndex = Integer.parseInt(input.trim()) - 1;
            if (monsterIndex >= 0 && monsterIndex < playerMonsters.size()) {
                PlayerMonster playerMonster = playerMonsters.get(monsterIndex);

                StringBuilder elementSelection = new StringBuilder("Select an element to evolve to:\n");
                List<Element> possibleElements = playerMonster.getElement().getEvolutionElements();
                for (int i = 0; i < possibleElements.size(); i++) {
                    elementSelection.append((i + 1)).append(". ").append(possibleElements.get(i)).append("\n");
                }

                input = JOptionPane.showInputDialog(null, elementSelection.toString(), "Choose Evolution Element", JOptionPane.PLAIN_MESSAGE);
                if (input != null && !input.isEmpty()) {
                    int elementIndex = Integer.parseInt(input.trim()) - 1;
                    if (elementIndex >= 0 && elementIndex < possibleElements.size()) {
                        Element newElement = possibleElements.get(elementIndex);

                        try {
                            homeBase.evolveMonster(playerMonster, newElement);
                            JOptionPane.showMessageDialog(null, playerMonster.getName() + " has evolved to " + newElement + " element!", "Evolution Success", JOptionPane.INFORMATION_MESSAGE);

                            homeBase.setPlayerTeam(playerMonster);
                        } catch (MonsterException e) {
                            JOptionPane.showMessageDialog(null, "Error evolving monster: " + e.getMessage(), "Evolution Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid choice.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid choice.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameGUI gameGui = new GameGUI();
            gameGui.setVisible(true);
        });
    }
}