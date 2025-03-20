# Pokémon-Style Monster Battle Game

## Introduction
This is a Pokémon-inspired game built using Java with Object-Oriented Programming (OOP) principles and a Graphical User Interface (GUI). Players can choose their starting monster, explore dungeons, battle wild monsters, and manage their home base.

## Features
- **Starter Monster Selection**: Choose from different elemental monsters at the beginning.
- **Dungeon Exploration**: Encounter wild monsters and engage in battles.
- **Turn-Based Battles**: Attack, use items, or attempt to capture monsters.
- **Elemental System**: Monsters have different elements with strengths and weaknesses.
- **Monster Evolution**: Train and evolve monsters into stronger forms.
- **Home Base Management**: Store, train, heal, and manage your monsters.
- **Save and Load Game Progress**.

## Technologies Used
- **Java**: Core programming language.
- **Swing (GUI)**: Used for graphical user interface elements.
- **OOP Principles**: Classes, interfaces, and inheritance are used to structure the game logic.

## How to Play
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

## Installation
1. Ensure you have Java installed on your system.
2. Clone or download this repository.
3. Compile and run `Main.java` using any Java IDE or terminal:
   ```sh
   javac Main.java
   java Main
   ```

## File Structure
- `Main.java` - Entry point of the game.
- `GameGUI.java` - Handles the graphical interface.
- `DungeonGUI.java` - Manages dungeon exploration and battles.
- `HomeBase.java` - Manages the player's stored monsters and items.
- `HomeBaseGUI.java` - Provides GUI for home base interactions.
- `Element.java` - Defines monster elements and their strengths/weaknesses.
- `BattleArena.java` - Interface for battle-related functionalities.

## Future Improvements
- Implement multiplayer battle mode.
- Add more monster evolutions and abilities.
- Improve battle animations and sound effects.

