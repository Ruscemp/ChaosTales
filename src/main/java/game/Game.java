package game;

import java.util.ArrayList;

public class Game {
    ArrayList<Character> characters = new ArrayList<>();
    int points;
    Adventure checkpoint;

    void startGame() {
        switch (Message.questionI(Message.message("What are your intentions now?",
                "",
                "1: Adventure (New and Old)",
                "2: Characters (Create, Appraise, Delete, Upgrade)",
                "3: Save/Load",
                "4: Main Menu",
                "5: Exit the App"))) {
            case 1:
                adventureMenu();
                break;
            case 2:
                charactersMenu();
                break;
            case 3:
                saveLoad();
                break;
            case 4:
                break;
            case 5:
                System.exit(0);
            default:
                Message.questionS(Message.limit(5));
                startGame();
        }
    }

    void adventureMenu() {
        switch (Message.questionI(Message.message("Shall we start a New or Old Adventure?", "", "", "",
                "1: Start New Adventure",
                "2: Start Old Adventure",
                "3: back"))) {
            case 1:
                adventure(true);
                break;
            case 2:
                adventure(false);
                break;
            case 3:
                startGame();
                break;
            default:
                Message.questionS(Message.limit(3));
                adventureMenu();
        }
    }

    void adventure(boolean New) {
        int pages = (int) Math.floor((double) (characters.size()) / 5);
        int curPage = 0;
        if (New) {
            loop:
            while (true) {
                switch (Message.getCharactersListMessage(characters, curPage, pages, 4)) {
                    case 1:
                        new Adventure(characters.get(curPage * 5), this);
                        break loop;
                    case 2:
                        new Adventure(characters.get(1 + curPage * 5), this);
                        break;
                    case 3:
                        new Adventure(characters.get(2 + curPage * 5), this);
                        break;
                    case 4:
                        new Adventure(characters.get(3 + curPage * 5), this);
                        break;
                    case 5:
                        if (curPage == pages) {
                            curPage = 0;
                        } else {
                            new Adventure(characters.get(4 + curPage * 5), this);
                        }
                        break;
                    case 6:
                        if (curPage == pages) {
                            break loop;
                        } else {
                            curPage++;
                        }
                }
            }
        } else {
            checkpoint.wakeUp(this);
        }
    }

    void newGame() {
        Message.questionS(Message.message("Hello player!", "Welcome to Chaos Tales",
                "Here you are going to take charge of various adventurers in their stories and tales.",
                "You shall see how they explore the world, fight the enemies, maybe even create a legacy for themselves.",
                "You would have the ultimate control of theirs fate.", "But first lets choose who you are going to lead first.",
                "Press 'Enter' to continue!").toString());
        newCharacter();
        startGame();
    }

    static Game loadGame() {
        Game game = new Game();
        game.load();
        return game;
    }

    void charactersMenu() {
        switch (Message.questionI(Message.message("What are your intentions now?",
                "",
                "1: Create new Character",
                "2: Appraise your Characters info",
                "3: Delete your Character",
                "4: Upgrade your Character",
                "5: Back"))) {
            case 1:
                newCharacter();
                charactersMenu();
                break;
            case 2:
                seeCharacters();
                charactersMenu();
                break;
            case 3:
                deleteCharacter();
                charactersMenu();
                break;
            case 4:
                upgradeCharacters();
                charactersMenu();
                break;
            case 5:
                startGame();
                break;
            default:
                Message.questionS(Message.limit(5));
                charactersMenu();
        }
    }

    void newCharacter() {
        switch (Message.questionI(Message.message("Which shall you choose for your new character?\n",
                "1: Erik the Bold (Human Warrior of 'Han of Humans')",
                "2: Rose the Sharp (Human Archer of 'Aleana of Archers')",
                "3: Jamik the White (Human Cleric of 'Whartiel of White')",
                "4: Vion the Mysterious (Elf Wizard of 'Maiar of Magic')",
                "5: Gron the Stoneskin (Dwarf Warrior of 'Dgund of Dwarfs')",
                "6: Custom new character"))) {
            case 1:
                characters.add(new Character(1, 1, 5, 1, "Erik the Bold"));
                break;
            case 2:
                characters.add(new Character(1, 2, 13, 2, "Rose the Sharp"));
                break;
            case 3:
                characters.add(new Character(1, 5, 1, 1, "Jamik the White"));
                break;
            case 4:
                characters.add(new Character(3, 4, 8, 3, "Vion the Mysterious"));
                break;
            case 5:
                characters.add(new Character(2, 1, 4, 1, "Gron the Stoneskin"));
                break;
            case 6:
                characters.add(new Character(chooseRace(), chooseClass(), chooseGod1(), chooseGender(), Message.questionS("What is this characters name?")));
        }
    }

    private int chooseRace() {
        return Message.questionI(Message.message("Of which race this character shall be?",
                "", "", "",
                "1: Human", "2: Dwarf", "3: Elf"));
    }

    private int chooseClass() {
        return Message.questionI(Message.message("Of which class this character shall be?\n",
                "1: Warrior", "2: Archer", "3: Thief",
                "4: Wizard", "5: Cleric", "6: Warlock"));
    }

    private int chooseGod1() {
        int choice = Message.questionI(Message.message("Which God does this character follow?\n",
                "1: Whartiel of White", "2: Grind of Grey", "3: Dio of Dark",
                "4: Dgund of Dwarfs", "5: Han of Humans", "6: next page"));
        if (choice == 6) {
            return chooseGod2();
        } else {
            return choice;
        }
    }

    private int chooseGod2() {
        int choice = Message.questionI(Message.message("Which God does this character follow?\n",
                "1: Eloiri of Elves", "2: Firtan of Forgotten", "3: Maiar of Magic",
                "4: Lern of Leaders", "5: Adon of Artisans", "6: next page"));
        if (choice == 6) {
            return chooseGod3();
        } else {
            return choice + 5;
        }
    }

    private int chooseGod3() {
        int choice = Message.questionI(Message.message("Which God does this character follow?\n",
                "1: Miltorian of Merchants", "2: Warlon of Warriors", "3: Aleana of Archers",
                "4: Tifola of Thieves", "5: Mirl of Mages", "6: first page"));
        if (choice == 6) {
            return chooseGod1();
        } else {
            return choice + 10;
        }
    }

    private int chooseGender() {
        return Message.questionI(Message.message("What is your characters gender?",
                "", "", "",
                "1: Male", "2: Female", "3: Other"));
    }

    void seeCharacters() {
        int pages = (int) Math.floor((double) (characters.size()) / 5);
        int curPage = 0;
        loop:
        while (true) {
            switch (Message.getCharactersListMessage(characters, curPage, pages, 1)) {
                case 1:
                    characters.get(curPage * 5).print();
                    break;
                case 2:
                    characters.get(1 + curPage * 5).print();
                    break;
                case 3:
                    characters.get(2 + curPage * 5).print();
                    break;
                case 4:
                    characters.get(3 + curPage * 5).print();
                    break;
                case 5:
                    if (curPage == pages) {
                        curPage = 0;
                    } else {
                        characters.get(4 + curPage * 5).print();
                    }
                    break;
                case 6:
                    if (curPage == pages) {
                        break loop;
                    } else {
                        curPage++;
                    }
            }
        }
    }

    void deleteCharacter() {
        int pages = (int) Math.floor((double) (characters.size()) / 5);
        int curPage = 0;
        loop:
        while (true) {
            switch (Message.getCharactersListMessage(characters, curPage, pages, 2)) {
                case 1:
                    characters.remove(curPage * 5);
                    pages = (int) Math.floor((double) (characters.size()) / 5);
                    break;
                case 2:
                    characters.remove(1 + curPage * 5);
                    pages = (int) Math.floor((double) (characters.size()) / 5);
                    break;
                case 3:
                    characters.remove(2 + curPage * 5);
                    pages = (int) Math.floor((double) (characters.size()) / 5);
                    break;
                case 4:
                    characters.remove(3 + curPage * 5);
                    pages = (int) Math.floor((double) (characters.size()) / 5);
                    break;
                case 5:
                    if (curPage == pages) {
                        curPage = 0;
                    } else {
                        characters.remove(4 + curPage * 5);
                        pages = (int) Math.floor((double) (characters.size()) / 5);
                    }
                    break;
                case 6:
                    if (curPage == pages) {
                        break loop;
                    } else {
                        curPage++;
                    }
            }
        }
    }

    void upgradeCharacters() {
        int pages = (int) Math.floor((double) (characters.size()) / 5);
        int curPage = 0;
        loop:
        while (true) {
            switch (Message.getCharactersListMessage(characters, curPage, pages, 3)) {
                case 1:
                    upgrade(curPage * 5);
                    break;
                case 2:
                    upgrade(1 + curPage * 5);
                    break;
                case 3:
                    upgrade(2 + curPage * 5);
                    break;
                case 4:
                    upgrade(3 + curPage * 5);
                    break;
                case 5:
                    if (curPage == pages) {
                        curPage = 0;
                    } else {
                        upgrade(4 + curPage * 5);
                    }
                    break;
                case 6:
                    if (curPage == pages) {
                        break loop;
                    } else {
                        curPage++;
                    }
            }
        }
    }

    void upgrade(int index) {
        switch (Message.questionI(Message.message("Price for upgrading a stat depends on a level of that stat",
                "(Current points: " + points + ")", "What part of this character do you wish to Upgrade?\n",
                "1: Body (str,dex,con)",
                "2: Mind (int,wis,will)",
                "3: Soul (cha,luc,mag)",
                "4: Exit Upgrading " + characters.get(index).name))) {
            case 1:
                characters.get(index).upgradeStatType(0, this);
                break;
            case 2:
                characters.get(index).upgradeStatType(1, this);
                break;
            case 3:
                characters.get(index).upgradeStatType(2, this);
                break;
            case 4:
                break;
            case 0:                                                     //FOR TESTING PURPOSES
                System.out.println("CHEAT!!!\n+10 points!\nCHEAT!!!");  //FOR TESTING PURPOSES
                points += 10;                                             //FOR TESTING PURPOSES
                upgrade(index);                                         //FOR TESTING PURPOSES
                break;
            default:
                Message.questionS(Message.limit(4));
                upgrade(index);
        }
    }


    void saveLoad() {
        switch (Message.questionI(Message.message("What do you want?",
                "1: Save current Game",
                "2: Load another Game",
                "3: Back",
                "",
                "",
                ""))) {
            case 1:
                save();
                saveLoad();
                break;
            case 2:
                load();
                break;
            case 3:
                startGame();
                break;
            default:
                Message.questionS(Message.limit(3));
                saveLoad();
        }
    }


    static int Menu(String str) {
        return Message.questionI(Message.message("Welcome to the ChaosTales!",
                str, "2: Load old Game", "3: Create and start new Game",
                "4: Options", "5: Info", "6: Exit"));
    }

    static void options() {
        System.out.println(Message.message("There",
                "are", "no", "options",
                "at", "this", "time"));
        Message.questionS("Press 'Enter' to continue!");
    }

    static void info() {
        Message.questionS(Message.message("Info",
                "Made by: Jaanek Kerma", "Made year: 2021", "Made for: School project",
                "Made with: Java", "Made in IntelliJ IDEA", "press Enter to continue").toString());
    }

    public void save() {
        SaveLoadEngine.save(this, Message.questionI(Message.message("Type of save", "1: Local File", "2: Database", "", "", "", "")) == 1);
        System.out.println("Game saved!");
    }

    public void load() {
        SaveLoadEngine.load(this, Message.questionI(Message.message("Type of load", "1: Local File", "2: Database", "", "", "", "")) == 1);
        System.out.println("Loading game!");
        startGame();
    }
}
