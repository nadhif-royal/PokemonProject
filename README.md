# 🎮 Pokémon-Style Monster Battle Game

## 🔥 Introduction
This is a Pokémon-inspired game built using Java with Object-Oriented Programming (OOP) principles and a Graphical User Interface (GUI). Players can choose their starting monster, explore dungeons, battle wild monsters, and manage their home base.

## ⭐ Features
- **Starter Monster Selection**: Choose from different elemental monsters at the beginning.
- **Dungeon Exploration**: Encounter wild monsters and engage in battles.
- **Turn-Based Battles**: Attack, use items, or attempt to capture monsters.
- **Elemental System**: Monsters have different elements with strengths and weaknesses.
- **Monster Evolution**: Train and evolve monsters into stronger forms.
- **Home Base Management**: Store, train, heal, and manage your monsters.
- **Save and Load Game Progress**.

## 🛠️ Technologies Used
- **Java**: Core programming language.
- **Swing (GUI)**: Used for graphical user interface elements.
- **OOP Principles**: Classes, interfaces, and inheritance are used to structure the game logic.

## 🎮 How to Play
1. **Start the Game**: Run the `Main.java` file.
2. **Choose a Starter Monster**: Select from fire, water, wind, ice, or earth-type monsters.
3. **Explore Dungeons**: Battle wild monsters and gain experience.
4. **Battle Mechanics**:
   - Choose between normal, elemental, or special attacks.
   - Use items to heal or strengthen your monster.
   - Attempt to capture defeated wild monsters.
5. **Manage Your Home Base**:
   - Train, evolve, and store monsters.
   - Use potions and other items.
   - Heal monsters before battle.
6. **Save and Load Progress**: Save your game before exiting and reload later.

## 🏗️ Installation
1. Ensure you have Java installed on your system.
2. Clone or download this repository.
3. Compile and run `Main.java` using any Java IDE or terminal:
   ```sh
   javac Main.java
   java Main
   ```

## 📂 File Structure
```
📦 PokemonProject
 ┣ 📂 out/production/Data          # Game production data
 ┣ 📜 BattleArena.java             # Interface for battle functionalities
 ┣ 📜 Data.iml                     # Project configuration file
 ┣ 📜 Dungeon.java                  # Dungeon mechanics
 ┣ 📜 DungeonGUI.java               # GUI for dungeon exploration
 ┣ 📜 Element.java                  # Defines monster elements
 ┣ 📜 GameGUI.java                  # Main game interface
 ┣ 📜 HomeBase.java                 # Manages stored monsters and items
 ┣ 📜 HomeBaseGUI.java              # GUI for home base management
 ┣ 📜 Item.java                     # Defines usable items in the game
 ┣ 📜 ItemDex.java                  # Item database
 ┣ 📜 Main.java                     # Entry point of the game
 ┣ 📜 Monster.java                  # Base class for all monsters
 ┣ 📜 MonsterDex.java               # Monster database
 ┣ 📜 MonsterException.java         # Custom exception handling for monsters
 ┣ 📜 PlayerMonster.java            # Player’s monster class
 ┣ 📜 PokeBall.java                 # Capturing mechanism
 ┣ 🖼️ HomeBaseBackground.jpg        # Background image for home base
 ┣ 🖼️ HomeBaseBackground2.png       # Additional background image
```

## 🚀 Future Improvements
- Implement multiplayer battle mode.
- Add more monster evolutions and abilities.
- Improve battle animations and sound effects.

## 👨‍💻 Credits
Developed by **Nadhif, Wilson, and Arry** as part of a Java OOP project. 🎉
