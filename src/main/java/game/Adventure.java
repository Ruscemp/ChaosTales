package game;

public class Adventure {
    Game game;
    int attackType = 0;
    Items weaponAttack = null;
    Abilities abilityAttack = null;
    Character character;
    String QuestType;
    String Quest;
    int QuestPlace = 0;
    int QuestTarget = 0;
    int progressCur = 0;
    int QuestProgressStep =0;
    int progressMax;
    int earnedPoints = 0;

    @Override
    public String toString(){
        return QuestType + "/" +
                Quest + "/" +
                QuestPlace + "/" +
                QuestTarget + "/" +
                progressCur + "/" +
                QuestProgressStep + "/" +
                progressMax + "/" +
                earnedPoints + "/" +
                character.toString();
    }
    Adventure(Character character, Game game){
        this.character = character;
        this.game = game;
        startAdventure();
    }
    public Adventure(String QuestType, String Quest, int QuestPlace, int QuestTarget, int progressCur, int QuestProgressStep, int progressMax, int earnedPoints, Character character){
        this.QuestType = QuestType;
        this.Quest = Quest;
        this.QuestPlace = QuestPlace;
        this.QuestTarget = QuestTarget;
        this.progressCur = progressCur;
        this.QuestProgressStep = QuestProgressStep;
        this.progressMax = progressMax;
        this.earnedPoints = earnedPoints;
        this.character = character;
    }

    private void startAdventure(){
        String start = "The Town you start in was built upon very strange structure called The Dungeon. \n" +
                "It periodically spawns in equal measure monsters and treasures which many adventurers hope to get\n" +
                "You start your today's adventure as always in the Hall of Adventure Guild.\n" +
                "You have many options to choose from, but be vary of this dangerous world for Danger can lurk everywhere.\n" +
                "\nPress Enter to continue!";
        Message.questionS(start);
        AdventureGuildHall();
    }

    private void getQuest(){
        int difficulty = Message.questionI(Message.message(
                "Choose quest difficulty", "",
                "1: Wood", "2: Copper",
                "3: Silver", "4: Gold",
                "5: Mithril"));
        progressMax = (int)(Math.random()*(difficulty*difficulty)+1);
        switch (Message.questionI(Message.message(
                        "Choose quest target","", "","",
                        "1: Collect", "2: Eliminate", "3: Deliver"))){
            case 1:
                collect();
                break;
            case 2:
                eliminate();
                break;
            case 3:
                deliver(difficulty);
        }
    }
    private void collect(){
        QuestType = "Collect";
        Quest = getRandomCollectible();
        questStart();
    }
    private void eliminate(){
        QuestType = "Kill";
        Quest = getRandomKill();
        questStart();
    }
    private void deliver(int difficulty){
        QuestType = "Deliver";
        Quest = getRandomPlace();
        switch (difficulty){
            case 1:
                progressMax=1;
                break;
            case 2:
                progressMax=2;
                break;
            case 3:
                progressMax=4;
                break;
            case 4:
                progressMax=6;
                break;
            case 5:
                progressMax=8;
                break;
        }
        questStart();
    }
    private String getRandomCollectible(){
        QuestTarget=getRandom(11);
        switch (QuestTarget){
            case 1:
                return "Monster Tail(s)";
            case 2:
                return "Monster Leg(s)";
            case 3:
                return "Unknown Potion(s)";
            case 4:
                return "Ancient Scroll(s)";
            case 5:
                return "Crystal Tree Sapling(s)";
            case 6:
                return "Wolf Pelt(s)";
            case 7:
                return "Stolen Item(s)";
            case 8:
                return "Crystal(s)";
            case 9:
                return "Gem(s)";
            case 10:
                return "Artifact(s)";
            case 11:
                return "Ancient Artifact(s)";
            default:
                return getRandomCollectible();
        }
    }
    private String getRandomKill(){
        String retur;
        String target;
        switch (getRandom(7)){
            case 1:
                retur = "Dungeon ";
                target = "1";
                break;
            case 2:
                retur = "Town ";
                target = "2";
                break;
            case 3:
                retur = "Forest ";
                target = "3";
                break;
            case 4:
                retur = "Cave ";
                target = "4";
                break;
            case 5:
                retur = "Mountain ";
                target = "5";
                break;
            case 6:
                retur = "Desert ";
                target = "6";
                break;
            case 7:
                retur = "Strange ";
                target = "7";
                break;
            default:
                return getRandomKill();
        }
        switch (getRandom(5)){
            case 1:
                retur += "Monster(s)";
                target +="1";
                break;
            case 2:
                retur += "Bandit(s)";
                target +="2";
                break;
            case 3:
                retur += "Giant Rat(s)";
                target +="3";
                break;
            case 4:
                retur += "Wolf(s)";
                target +="4";
                break;
            case 5:
                retur += "Thief(s)";
                target +="5";
                break;
            default:
                return getRandomKill();
        }
        QuestTarget = Integer.parseInt(target);
        return retur;
    }
    private String getRandomPlace(){
        String retur = "package(s) to ";
        switch (getRandom(3)){
            case 1:
                retur += "a Merchant wandering";
                QuestTarget = 1;
                break;
            case 2:
                retur += "an Officer based";
                QuestTarget = 2;
                break;
            case 3:
                retur += "a Hermit living";
                QuestTarget = 3;
                break;
            default:
                return getRandomPlace();
        }
        retur +=" in the ";
        switch (getRandom(7)){
            case 1:
                retur += "Dungeon";
                QuestPlace = 1;
                break;
            case 2:
                retur += "Town";
                QuestPlace = 2;
                break;
            case 3:
                retur += "Forest";
                QuestPlace = 3;
                break;
            case 4:
                retur += "Mountains";
                QuestPlace = 4;
                break;
            case 5:
                retur += "Desert";
                QuestPlace = 5;
                break;
            case 6:
                retur += "Cave";
                QuestPlace = 6;
                break;
            case 7:
                retur += "Wilderness";
                QuestPlace = 7;
                break;
            default:
                return getRandomPlace();
        }
        return retur;
    }

    private void questStart(){
        QuestProgressStep =0;
        switch (Message.questionI(Message.message(
                "Quest: "+QuestType+" "+progressMax+" "+ Quest,
                "1: Explore Wilderness in hopes of finding "+ Quest,
                "2: Explore Town's Dungeon in hopes of finding "+ Quest,
                "3: Explore Town in hopes of finding "+ Quest,
                "4: Try to get another quest",
                "5: Accept the Quest and go back to the Adventure Guilds Hall",
                ""))){
            case 1:
                Wilderness();
                break;
            case 2:
                Dungeon(getRandom(3));
                break;
            case 3:
                Town();
                break;
            case 4:
                getQuest();
                break;
            case 5:
                AdventureGuildHall();
                break;
            default:
                Message.questionS(Message.limit(5));
                startAdventure();
        }
    }
    private void AdventureGuildHall(){
        switch (Message.questionI(Message.message(
                "You are standing in the Town's Adventure Guild's Hall. What are your intentions?",
                "",
                "1: Go to your room",
                "2: Get random quest from quest board.",
                "3: Explore wilderness.",
                "4: Explore town.",
                "5: Explore town's Dungeon"))){
            case 1:
                room("standing");
                break;
            case 2:
                getQuest();
                break;
            case 3:
                Wilderness();
                break;
            case 4:
                Town();
                break;
            case 5:
                Dungeon(getRandom(3));
                break;
            default:
                Message.questionS(Message.limit(5));
                startAdventure();
        }
    }

    private void room(String state){
        switch (Message.questionI(Message.message(
                "You are "+state+" in your room. What are your intentions now?",
                "1: Sleep unknown amount (Heal, Save and Exit Adventure)",
                "2: Sleep until next morning (Heal and Save)",
                "3: Rest until healed (Heal)",
                "4: Exit your room",
                "5: Check yourself",
                "6: Check your Quest"))){
            case 1:
                character.hpCur=character.hpMax;
                character.mpCur=character.mpMax;
                game.checkpoint = this;
                game.startGame();
                break;
            case 2:
                character.hpCur=character.hpMax;
                character.mpCur=character.mpMax;
                game.checkpoint = this;
                room("waking up after a night");
                break;
            case 3:
                int r = getRandom(4)+1;
                character.hpCur=character.hpMax;
                character.mpCur=character.mpMax;
                room("standing up after "+r+" hours of relaxing and healing");
                break;
            case 4:
                AdventureGuildHall();
                break;
            case 5:
                character.print();
                room("checking yourself in a mirror");
                break;
            case 6:
                if (Quest == null||Quest.equalsIgnoreCase("null")||Quest.equalsIgnoreCase("")) {
                    Message.questionS("You currently have no active Quest!");
                }else {
                    String str1 = "", str2 = "";
                    switch (QuestType){
                        case "Collect":
                            str1 = "a Collection";
                            str2 = "collect";
                            break;
                        case "Kill":
                            str1 = "an Elimination";
                            str2 = "kill";
                            break;
                        case "Deliver":
                            str1 = "a Delivery";
                            str2 = "deliver a package to";
                            break;
                    }
                    Message.questionS("You currently have "+str1+" Quest active!\n" +
                            "According to your quest you need to "+str2+" "+progressMax+" "+Quest+"\n" +
                            "Currently your progress of this Quest is thus: "+progressCur+"/"+progressMax);
                }
                room("checking you journal for active Quest");
                break;
            default:
                Message.questionS(Message.limit(6));
                startAdventure();
        }
    }
    public void wakeUp(Game game){
        this.game = game;
        room("wake up");
    }

    private void Wilderness(){
        switch (Message.questionI(Message.message(
                "This is wilderness around the Town. Where are you going?",
                "1 Go back into Town",
                "2: Go to the Forest",
                "3: Go to the Mountains",
                "4: Go to the Dessert",
                "5: Go to the Caves",
                "6: Go in random direction"))){
            case 1:
                Town();
                break;
            case 2:
                RandomEncounter(3, getRandom(3));
                break;
            case 3:
                RandomEncounter(4, getRandom(3));
                break;
            case 4:
                RandomEncounter(5, getRandom(3));
                break;
            case 5:
                RandomEncounter(6, getRandom(3));
                break;
            case 6:
                RandomEncounter(getRandom(5)+2, getRandom(3));
                break;
            default:
                Message.questionS(Message.limit(5));
                Town();
        }
    }

    private void Town(){
        switch (Message.questionI(Message.message(
                "This town is very large and it is easy to get lost. Where are you heading?",
                "",
                "1: Go to Dungeon's Gate",
                "2: Go to gate to Wilderness",
                "3: Go to Adventure Hall",
                "4: Go to Random Adventurer's Shop",
                "5: Explore town's streets in hopes of something happening"))){
            case 1:
                Dungeon(getRandom(3));
                break;
            case 2:
                Wilderness();
                break;
            case 3:
                AdventureGuildHall();
                break;
            case 4:
                Shop();
                break;
            case 5:
                RandomEncounter(2,1);
                Town();
                break;
            default:
                Message.questionS(Message.limit(5));
                Town();
        }
    }
    private void Shop(){
        String shopType = "";
        switch (getRandom(5)){
            case 1:
                shopType = "Weapon";
                break;
            case 2:
                shopType = "Armour";
                break;
            case 3:
                shopType = "Utility";
                break;
            case 4:
                shopType = "Support";
                break;
            case 5:
                shopType = "Strange";
                break;
            default:
                Shop();
                break;
        }
        String str1="", str2="";
        int limit= 2;
        if (character.has(Abilities.STEAL_AND_SNEAK)){
            limit++;
            str1 = limit+": 'I shall explore what this shop offers on my own if you permit it' you say while you check what you can steal.";
        }
        if (character.has(Abilities.WIPE_MEMORY)){
            limit++;
            str2 = limit+": You take one random item and say 'Forget about me and this item' as you use your ability Wipe Memory to leave no traces";
        }
        switch (Message.questionI(Message.message(
                "'Welcome to "+shopType+" Paradise! How may I help you today?' says shopkeeper.",
                "1: You say: 'I would like to see what you sell'",
                "2: 'I am only exploring this town for now. Bye!' you say as you walk away.",
                ""+str1,
                ""+str2,
                "",
                ""))){
            case 1:
                buyShop(shopType);
                break;
            case 2:
                Town();
                break;
            case 3:
                if (character.has(Abilities.STEAL_AND_SNEAK)){
                    stealShop(shopType);
                }
                break;
            case 4:
                if (character.has(Abilities.WIPE_MEMORY)){
                    stealShop(shopType);
                }
                break;
            default:
                Message.questionS(Message.limit(limit));
                startAdventure();
        }
    }
    private void buyShop(String shopType){
        String str1 = "", str2 = "", str3 = "", str4 = "", str5 = "", str6 = "";
        int type = 0;
        switch (shopType){
            case "Weapon":
                str1 = "Sword | Copper Coin";
                str2 = "Dagger | Copper Coin";
                str3 = "Staff | Copper Coin";
                str4 = "Bow and Arrows | Silver Coin";
                str5 = "Mace | Silver Coin";
                str6 = "Poison | Silver Coin";
                type = 1;
                break;
            case "Armour":
                str1 = "Iron Armour | Silver Coin";
                str2 = "Shield | Silver Coin";
                str3 = "Robes | Copper Coin";
                str4 = "Leather Armour | Copper Coin";
                str5 = "Amulet of Protection | Gold Coin";
                str6 = "Health Potion | Silver Coin";
                type = 2;
                break;
            case "Utility":
                str1 = "Rope | Silver Coin";
                str2 = "Tent | Copper Coin";
                str3 = "Bedroll | Copper Coin";
                str4 = "Meal | Copper Coin";
                str5 = "Backpack | Copper Coin";
                str6 = "Torch | Silver Coin";
                type = 3;
                break;
            case "Support":
                str1 = "Health Potion | Silver Coin";
                str2 = "Mana Potion | Silver Coin";
                str3 = "Meal | Copper Coin";
                str4 = "Scroll of return | Gold Coin";
                str5 = "Scroll of healing | Silver Coin";
                str6 = "Scroll of camping | Gold Coin";
                type = 4;
                break;
            case "Strange":
                str1 = "Ring of Night Vision | Silver Coin";
                str2 = "Ring of Greater Strength | Gold Coin";
                str3 = "Ring of Greater Dexterity | Gold Coin";
                str4 = "Ring of Greater Constitution | Gold Coin";
                str5 = "Potion of invisibility | Silver Coin";
                str6 = "Poison | Silver Coin";
                type = 5;
        }
        switch (Message.questionI(Message.message(
                "'Of course! Here are our catalog. On the left is the Item, on the right is the Price!' cheerily says shopkeeper.",
                "1: "+str1,
                "2: "+str2,
                "3: "+str3,
                "4: "+str4,
                "5: "+str5,
                "6: "+str6))){
            case 1:
                switch (type){
                    case 1:
                        character.buy(Items.SWORD,Items.COPPER_COIN);
                        break;
                    case 2:
                        character.buy(Items.IRON_ARMOUR,Items.GOLD_COIN);
                        break;
                    case 3:
                        character.buy(Items.ROPE,Items.SILVER_COIN);
                        break;
                    case 4:
                        character.buy(Items.HEALTH_POTION,Items.SILVER_COIN);
                        break;
                    case 5:
                        character.buy(Items.RING_OF_NIGTH_VISION,Items.SILVER_COIN);
                        break;
                    default:
                }
                break;
            case 2:
                switch (type){
                    case 1:
                        character.buy(Items.DAGGER,Items.COPPER_COIN);
                        break;
                    case 2:
                        character.buy(Items.SHIELD,Items.SILVER_COIN);
                        break;
                    case 3:
                        character.buy(Items.TENT,Items.SILVER_COIN);
                        break;
                    case 4:
                        character.buy(Items.MANA_POTION,Items.SILVER_COIN);
                        break;
                    case 5:
                        character.buy(Items.RING_OF_GREATER_STRENGTH,Items.GOLD_COIN);
                        break;
                    default:
                }
                break;
            case 3:
                switch (type){
                    case 1:
                        character.buy(Items.STAFF,Items.COPPER_COIN);
                        break;
                    case 2:
                        character.buy(Items.ROBES,Items.COPPER_COIN);
                        break;
                    case 3:
                        character.buy(Items.BEDROLL,Items.COPPER_COIN);
                        break;
                    case 4:
                        character.buy(Items.MEAL,Items.COPPER_COIN);
                        break;
                    case 5:
                        character.buy(Items.RING_OF_GREATER_DEXTERITY,Items.GOLD_COIN);
                        break;
                    default:
                }
                break;
            case 4:
                switch (type){
                    case 1:
                        character.buy(Items.BOW_AND_ARROWS,Items.SILVER_COIN);
                        break;
                    case 2:
                        character.buy(Items.LEATHER_ARMOUR,Items.SILVER_COIN);
                        break;
                    case 3:
                        character.buy(Items.MEAL,Items.COPPER_COIN);
                        break;
                    case 4:
                        character.buy(Items.SCROLL_OF_RETURN,Items.GOLD_COIN);
                        break;
                    case 5:
                        character.buy(Items.RING_OF_GREATER_CONSTITUTION,Items.GOLD_COIN);
                        break;
                    default:
                }
                break;
            case 5:
                switch (type){
                    case 1:
                        character.buy(Items.POISON,Items.SILVER_COIN);
                        break;
                    case 2:
                        character.buy(Items.AMULET_OF_PROTECTION,Items.GOLD_COIN);
                        break;
                    case 3:
                        character.buy(Items.BACKPACK,Items.COPPER_COIN);
                        break;
                    case 4:
                        character.buy(Items.SCROLL_OF_HEALING,Items.SILVER_COIN);
                        break;
                    case 5:
                        character.buy(Items.POTION_OF_INVISIBILITY,Items.SILVER_COIN);
                        break;
                    default:
                }
                break;
            case 6:
                switch (type){
                    case 1:
                        character.buy(Items.MACE,Items.SILVER_COIN);
                        break;
                    case 2:
                        character.buy(Items.HEALTH_POTION,Items.SILVER_COIN);
                        break;
                    case 3:
                        character.buy(Items.TORCH,Items.SILVER_COIN);
                        break;
                    case 4:
                        character.buy(Items.SCROLL_OF_CAMPING,Items.GOLD_COIN);
                        break;
                    case 5:
                        character.buy(Items.POISON,Items.SILVER_COIN);
                        break;
                    default:
                }
                break;
            default:
                Message.questionS(Message.limit(6));
                startAdventure();
        }
        Shop();
    }
    private void stealShop(String shopType){
        int item = getRandom(25);
        switch (shopType){
            case "Weapon":
                switch (item){
                    case 1: case 2: case 3:
                        character.inventory.add(Items.SWORD);
                        break;
                    case 4: case 5: case 6:
                        character.inventory.add(Items.DAGGER);
                        break;
                    case 7: case 8: case 9:
                        character.inventory.add(Items.STAFF);
                        break;
                    case 10: case 11: case 12:
                        character.inventory.add(Items.BOW_AND_ARROWS);
                        break;
                    case 13: case 14: case 15:
                        character.inventory.add(Items.POISON);
                        break;
                    case 16: case 17: case 18: case 19: case 20: case 21:
                        character.inventory.add(Items.COPPER_COIN);
                        break;
                    case 22: case 23: case 24:
                        character.inventory.add(Items.SILVER_COIN);
                        break;
                    case 25:
                        character.inventory.add(Items.GOLD_COIN);
                        break;
                }
                break;
            case "Armour":
                switch (item){
                    case 1: case 2: case 3:
                        character.inventory.add(Items.IRON_ARMOUR);
                        break;
                    case 4: case 5: case 6:
                        character.inventory.add(Items.SHIELD);
                        break;
                    case 7: case 8: case 9:
                        character.inventory.add(Items.ROBES);
                        break;
                    case 10: case 11: case 12:
                        character.inventory.add(Items.LEATHER_ARMOUR);
                        break;
                    case 13: case 14: case 15:
                        character.inventory.add(Items.AMULET_OF_PROTECTION);
                        break;
                    case 16: case 17: case 18:
                        character.inventory.add(Items.HEALTH_POTION);
                        break;
                    case 19: case 20: case 21: case 22:
                        character.inventory.add(Items.COPPER_COIN);
                        break;
                    case 23: case 24:
                        character.inventory.add(Items.SILVER_COIN);
                        break;
                    case 25:
                        character.inventory.add(Items.GOLD_COIN);
                        break;
                }
                break;
            case "Utility":
                switch (item){
                    case 1: case 2: case 3:
                        character.inventory.add(Items.ROPE);
                        break;
                    case 4: case 5: case 6:
                        character.inventory.add(Items.TENT);
                        break;
                    case 7: case 8: case 9:
                        character.inventory.add(Items.BEDROLL);
                        break;
                    case 10: case 11: case 12:
                        character.inventory.add(Items.MEAL);
                        break;
                    case 13: case 14: case 15:
                        character.inventory.add(Items.BACKPACK);
                        break;
                    case 16: case 17: case 18:
                        character.inventory.add(Items.TORCH);
                        break;
                    case 19: case 20: case 21: case 22:
                        character.inventory.add(Items.COPPER_COIN);
                        break;
                    case 23: case 24:
                        character.inventory.add(Items.SILVER_COIN);
                        break;
                    case 25:
                        character.inventory.add(Items.GOLD_COIN);
                        break;
                }
                break;
            case "Support":
                switch (item){
                    case 1: case 2: case 3:
                        character.inventory.add(Items.HEALTH_POTION);
                        break;
                    case 4: case 5: case 6:
                        character.inventory.add(Items.MANA_POTION);
                        break;
                    case 7: case 8: case 9:
                        character.inventory.add(Items.MEAL);
                        break;
                    case 10: case 11: case 12:
                        character.inventory.add(Items.SCROLL_OF_HEALING);
                        break;
                    case 13: case 14: case 15:
                        character.inventory.add(Items.SCROLL_OF_RETURN);
                        break;
                    case 16: case 17: case 18:
                        character.inventory.add(Items.SCROLL_OF_CAMPING);
                        break;
                    case 19: case 20: case 21: case 22:
                        character.inventory.add(Items.COPPER_COIN);
                        break;
                    case 23: case 24:
                        character.inventory.add(Items.SILVER_COIN);
                        break;
                    case 25:
                        character.inventory.add(Items.GOLD_COIN);
                        break;
                }
                break;
            case "Strange":
                switch (item){
                    case 1: case 2: case 3:
                        character.inventory.add(Items.RING_OF_NIGTH_VISION);
                        break;
                    case 4: case 5: case 6:
                        character.inventory.add(Items.RING_OF_GREATER_STRENGTH);
                        break;
                    case 7: case 8: case 9:
                        character.inventory.add(Items.RING_OF_GREATER_DEXTERITY);
                        break;
                    case 10: case 11: case 12:
                        character.inventory.add(Items.RING_OF_GREATER_CONSTITUTION);
                        break;
                    case 13: case 14: case 15:
                        character.inventory.add(Items.POTION_OF_INVISIBILITY);
                        break;
                    case 16: case 17: case 18:
                        character.inventory.add(Items.POISON);
                        break;
                    case 19: case 20: case 21: case 22:
                        character.inventory.add(Items.COPPER_COIN);
                        break;
                    case 23: case 24:
                        character.inventory.add(Items.SILVER_COIN);
                        break;
                    case 25:
                        character.inventory.add(Items.GOLD_COIN);
                        break;
                }
        }
        Message.questionS("You have stolen "+character.inventory.get(character.inventory.size()-1)+"!\nPress Enter to continue!");
        Town();
    }

    private void Dungeon(int diff){
        String difficulty="";
        switch (diff){
            case 1:
                difficulty = "easy";
                break;
            case 2:
                difficulty = "average";
                break;
            case 3:
                difficulty = "hard";
                break;
        }
        switch (Message.questionI(Message.message(
                "As you approach the Dungeon Gate its guards greets you with 'Right now dungeon is "+difficulty+" on adventurers!",
                "",
                "",
                "",
                "1: Dive into the Dungeon",
                "2: Go back into Town",
                "3: Ask guards about Dungeon"))){
            case 1:
                RandomEncounter(3, diff);
                break;
            case 2:
                Town();
                break;
            case 3:
                Message.questionS("This is Town's Dungeon! The longer one is inside the more difficult it becomes but also more rewards one can get.\n" +
                        "On average rookie adventurers dive into The Dungeon for two or three encounters before the retread.\n" +
                        "That is, it is about living rookie adventurers. Those who die there are never heard again.");
                Dungeon(diff);
                break;
            default:
                Message.questionS(Message.limit(3));
                Dungeon(diff);
        }
    }

    private void RandomEncounter(int place, int difficulty){
        switch (place){
            case 1:
                switch (getRandom(10)){
                    case 1: case 2: case 3:case 4:case 5:case 6:
                        battle(place, difficulty);
                        break;
                    case 7:
                        treasure(place, difficulty);
                        break;
                    case 8:
                        encounter(place);
                        break;
                    case 9:
                        Message.questionS("As you wander in the dungeon you see that you approach stairs to lower levels.\nPress Enter to continue!");
                        RandomEncounter(place, difficulty+5);
                        break;
                    case 10:
                        Message.questionS("As you wander in the dungeon you see that you approach the exit!");
                        Dungeon(getRandom(3));
                }
                break;
            case 2:
                switch (getRandom(7)){
                    case 1:
                        battle(place, difficulty);
                        break;
                    case 2:
                        encounter(place);
                        break;
                    case 3: case 4: case 5: case 6: case 7:
                        Message.questionS("Nothing happens as you wander the streets.\nPress Enter to continue.");
                        Town();
                }
                break;
            case 3: case 4: case 5: case 6:
                switch (getRandom(11)){
                    case 1: case 2: case 3:case 4:case 5:case 6:
                        battle(place, difficulty);
                        break;
                    case 7:
                        treasure(place, difficulty);
                        break;
                    case 8:case 9:
                        encounter(place);
                        break;
                    case 10:case 11:
                        Message.questionS("As you wander in wilderness you see that you approach the Town.\nPress Enter to continue.");
                        Wilderness();
                }
                break;
            case 7:
                switch (getRandom(7)){
                    case 1:case 2:
                        battle(place, difficulty);
                        break;
                    case 3:
                        treasure(place, difficulty);
                        break;
                    case 4:case 5:
                        encounter(place);
                        break;
                    case 6:case 7:
                        Message.questionS("As you wander in wilderness you see that you approach the Town.\nPress Enter to continue.");
                        Wilderness();
                }
        }
        RandomEncounter(place,difficulty+1);
    }
    private void battle(int place, int difficulty){
        String str1 = getPlace(place), str3 = "", str4 = "",str5 = "",str6 = "";
        if (character.has(Abilities.SHADOW_HIDE)){
            str3 = "3: Use ability 'Shadow Hide' to attack the enemy from behind";
        }
        if (character.has(Abilities.TRAP_KNOWLEDGE)){
            str4 = "4: Construct a trap and lure enemy to it.";
        }
        if (character.has(Abilities.STEAL_AND_SNEAK)){
            str5 = "5: Try to sneak and steal from the enemy.";
        }
        if (character.stats[6]>100){
            str6 = "6: Try to persuade the enemy to leave you alone";
        }
        if ( ((character.stats[1]+character.stats[4]+character.stats[7])/2) > difficulty*5){
            switch (Message.questionI(Message.message("You are about to meet an "+str1+" enemy. How would you like to proceed?",
                    "1: Attack the enemy!",
                    "2: Avoid the enemy.",
                    str3,
                    str4,
                    str5,
                    str6))){
                case 1:
                    battleAttack(place, difficulty, 0);
                    break;
                case 2:
                    RandomEncounter(place,difficulty-1);
                    break;
                case 3:
                    if (character.has(Abilities.SHADOW_HIDE)){
                        battleAttack(place, difficulty, 1);
                    }
                    break;
                case 4:
                    if (character.has(Abilities.TRAP_KNOWLEDGE)){
                        battleAttack(place, difficulty, 2);
                    }
                    break;
                case 5:
                    if (character.has(Abilities.STEAL_AND_SNEAK)){
                        Message.questionS("You successfully stole "+Items.getRandomPrice()+" from enemy!");
                        RandomEncounter(place,difficulty+2);
                    }
                    break;
                case 6:
                    if (character.stats[6]>100){
                        Message.questionS("You successfully convince your enemy to leave you! Your discussion also increased some of your stats!");
                        int x = getRandom(5);
                        for (int i = 0; i <= x;i++){
                            character.stats[getRandom(9)-1]+=getRandom(3-1);
                        }
                        RandomEncounter(place,difficulty+2);
                    }
                    break;
                default:
                    Message.limit(6);
                    battle(place, difficulty);
            }
        }
    }
    private void battleAttack(int place, int difficulty, int ability){
        int enemyMaxHp = difficulty*2;
        int enemyCurHp = enemyMaxHp;
        String eN = "";
        int enemy = 0;
        switch (getRandom(5)){
            case 1:
                eN = "Zombie";
                enemy = 1;
                break;
            case 2:
                eN = "Bandit";
                enemy = 2;
                break;
            case 3:
                eN = "Giant Rat";
                enemy = 3;
                break;
            case 4:
                eN = "Mad Wolf";
                enemy = 4;
                break;
            case 5:
                eN = "Thief";
                enemy = 5;
                break;
            default:
                battleAttack(place, difficulty, ability);
        }
        String enemyName = getPlace(place)+" "+eN;
        if (ability == 1){
            enemyCurHp = (int)Math.round(enemyMaxHp*(1-(character.stats[0]+character.stats[1]+character.stats[8])/100.0));
        }else if (ability == 2){
            enemyCurHp = (int)Math.round(enemyMaxHp*(1-(character.stats[1]+character.stats[3]+character.stats[4])/100.0));
        }
        while (enemyCurHp>0&&character.hpCur>0) {
            switch (Message.questionI(Message.message(
                    "You begin battle with "+enemyName+"("+enemyCurHp+"/"+enemyMaxHp+")",
                    "1: Attack with weapon",
                    "2: Use ability",
                    "3: Defend yourself",
                    "4: Flee",
                    "5: Repeat last Action",
                    ""))){
                case 1:
                    enemyCurHp = battleExchange(difficulty, enemyCurHp, enemyName, getWeaponDamage(chooseWeapon()));
                    break;
                case 2:
                    enemyCurHp = battleExchange(difficulty, enemyCurHp, enemyName, getAbilityUsage(chooseAbility()));
                    break;
                case 3:
                    defend();
                    attackType = 3;
                    break;
                case 4:
                    flee(place,difficulty,enemyName);
                    attackType = 4;
                    break;
                case 5:
                    switch (attackType){
                        case 1:
                            enemyCurHp-=getWeaponDamage(weaponAttack);
                            break;
                        case 2:
                            enemyCurHp -= getAbilityUsage(abilityAttack);
                            break;
                        case 3:
                            defend();
                            break;
                        case 4:
                            flee(place,difficulty,enemyName);
                            break;
                        default:
                            Message.questionS("You do not have last Action!");
                            break;
                    }
            }
        }
        if (character.hpCur<=0){
            Message.questionS("You have died! This adventure is over! You have earned "+earnedPoints+" total points to use between adventures!");
            game.startGame();
        }else {
            Message.questionS("You have defeated "+enemyName+"!");
            if (QuestType.equalsIgnoreCase("kill")&&(Math.floor(QuestTarget/10.0))==place&&((QuestTarget-place*10)==enemy)){
                completeQuest(false,1);
            }else if (QuestType.equalsIgnoreCase("collect")){
                battleCollect(enemy);
            }
            RandomEncounter(place,difficulty+1);
        }
    }

    private int battleExchange(int difficulty, int enemyCurHp, String enemyName, int pDamage) {
        StringBuilder sbf = new StringBuilder();
        switch (getRandom(3)){
            case 1:
                int eDamage = (int)Math.max(1,Math.round(difficulty/4.0));
                if (pDamage>0){
                    enemyCurHp -= pDamage;
                    sbf.append("You and your enemy attack at the same time!").append("\n").append("You deal ").append(pDamage);
                    if (enemyCurHp>0){
                        sbf.append(" damage leaving your enemy with ").append(enemyCurHp).append(" HP!\n");
                    }else {
                        sbf.append(" damage which kills your enemy!");
                    }
                } else {
                    sbf.append(enemyName).append(" attacks at the same time as you use your Ability!\n");
                }
                if (enemyCurHp>0){
                    character.hpCur-=eDamage;
                    sbf.append(enemyName).append(" deals ").append(eDamage);
                    if (character.hpCur>0){
                        sbf.append(" damage leaving you with ").append(character.hpCur).append(" HP!");
                    }else {
                        sbf.append(" damage which kills you!");
                    }
                }
                break;
            case 2:
                if (pDamage>0){
                    pDamage = (int) Math.round(pDamage/5.0);
                    enemyCurHp -= pDamage;
                    sbf.append(enemyName).append(" defends as you attack!\n");
                    sbf.append("You deal ").append(pDamage);
                    if (enemyCurHp>0){
                        sbf.append(" damage leaving your enemy with ").append(enemyCurHp).append(" HP!");
                    }else {
                        sbf.append(" damage which kills ").append(enemyName).append("!");
                    }
                }else {
                    sbf.append(enemyName).append(" defends as you use your ability!");
                }
                break;
            case 3:
                if (pDamage>0){
                    enemyCurHp -= pDamage;
                    sbf.append(enemyName).append(" does nothing as you attack!\n");
                    sbf.append("You deal ").append(pDamage);
                    if (enemyCurHp>0){
                       sbf.append(" damage leaving your enemy with ").append(enemyCurHp).append(" HP!");
                    }else {
                        sbf.append(" damage which kills ").append(enemyName).append("!");
                    }
                }else {
                    sbf.append(enemyName).append(" does nothing as you use your ability!");
                }
                break;
        }
        Message.questionS(sbf.toString());
        return enemyCurHp;
    }

    private Items chooseWeapon(){
        StringBuilder str=new StringBuilder();
        boolean again;
        if (character.has(Items.SWORD)){
            str.append("| Sword |");
        }
        if (character.has(Items.BOW_AND_ARROWS)){
            str.append("| Bow_and_Arrows |");
        }
        if (character.has(Items.MACE)){
            str.append("| Mace |");
        }
        if (character.has(Items.DAGGER)){
            str.append("| Dagger |");
        }
        if (character.has(Items.STAFF)){
            str.append("| Staff |");
        }
        again = true;
        do {
            try{
                weaponAttack = Items.valueOf(Message.questionS("With which weapon do you wish to attack? You have following weapons: \n"+str.toString()));
                if (!character.has(weaponAttack)){
                    Message.questionS("You do not have this weapon in your inventory! Try again with one of these: \n"+str.toString());
                }else {
                    again=false;
                }
            }catch (Exception e){
                Message.questionS("Unknown weapon! Try again with one of these: \n"+str.toString());
            }
        }while (again);
        return weaponAttack;
    }
    private int getWeaponDamage(Items weapon){
        attackType=1;
        switch (weapon){
            case MACE:
                return character.stats[0]*2;
            case SWORD:
                return (int)Math.round(character.stats[0]*1.8+character.stats[1]*1.2);
            case BOW_AND_ARROWS:
                return (int)Math.round(character.stats[1]*1.8+character.stats[4]*1.2);
            case DAGGER:
                return character.stats[1]*2;
            case STAFF:
                return (int)Math.round(character.stats[0]*1.5);
            default:
                return 0;
        }
    }
    private Abilities chooseAbility(){
        StringBuilder str=new StringBuilder();
        if (character.has(Abilities.CAST_ELDRITCH_BLAST)){
            str.append("| Cast_Eldritch_Blast |");
        }
        if (character.has(Abilities.CAST_HOLY_STRIKE)){
            str.append("| Cast_Holy_Strike |");
        }
        if (character.has(Abilities.CAST_FIREBALL)){
            str.append("| Cast_Fireball |");
        }
        if (character.has(Abilities.CAST_HEAL)){
            str.append("| Cast_Heal |");
        }
        if (character.has(Abilities.DRAIN_LIFE)){
            str.append("| Drain_Life |");
        }
        if (character.has(Abilities.HEADSHOT)&&character.has(Items.BOW_AND_ARROWS)){
            str.append("| Headshot |");
        }
        if (character.has(Abilities.STRONG_STRIKE)&&(character.has(Items.SWORD)||character.has(Items.MACE)||character.has(Items.DAGGER))){
            str.append("| Strong_Strike |");
        }
        if (character.has(Abilities.HEAL)){
            str.append("| Heal |");
        }
        if (character.has(Abilities.MEDITATE)){
            str.append("| Meditate |");
        }
        if (character.has(Abilities.RECOVERY)){
            str.append("| Recovery |");
        }
        boolean again = true;
        do {
            try{
                abilityAttack = Abilities.valueOf(Message.questionS("Which ability do you want to use? You have following abilities: \n"+str.toString()));
                if (!character.has(abilityAttack)){
                    Message.questionS("You do not have this ability! Try again with one of these: \n"+str.toString());
                }else {
                    again=false;
                }
            }catch (Exception e){
                Message.questionS("Unknown ability! Try again with one of these: \n"+str.toString());
            }
        }while (again);
        return abilityAttack;
    }
    private int getAbilityUsage(Abilities ability){
        attackType=2;
        switch (ability){
            case CAST_ELDRITCH_BLAST:
                if (character.mpCur>=3){
                    character.mpCur-=3;
                    Message.questionS("You cast Eldritch Blast on your enemy!");
                    return (int)Math.round(character.stats[8]*1.5+character.stats[5]*1.35+character.stats[4]*1.15);
                }else {
                    Message.questionS("You do not have enough mana to cast Eldritch Blast!");
                }
                break;
            case CAST_HOLY_STRIKE:
                if (character.mpCur>=2) {
                    character.mpCur -= 2;
                    Message.questionS("You cast Holy Strike on your enemy!");
                    return (int)Math.round(character.stats[8]*1.5+character.stats[5]*1.35+character.stats[6]*1.15);
                }else {
                    Message.questionS("You do not have enough mana to cast Holy Strike!");
                }
                break;
            case CAST_FIREBALL:
                if (character.mpCur>=1) {
                    character.mpCur -= 1;
                    Message.questionS("You cast Fireball on your enemy!");
                    return (int)Math.round(character.stats[8]*1.5+character.stats[5]*1.25+character.stats[4]*1.125+character.stats[3]*1.125);
                }else {
                    Message.questionS("You do not have enough mana to cast Fireball!");
                }
                break;
            case CAST_HEAL:
                if (character.mpCur>=4) {
                    character.mpCur -= 4;
                    Message.questionS("You cast Heal on yourself!");
                    character.heal((int)Math.round(character.stats[8]*1.5+character.stats[5]*1.25+character.stats[4]*1.125+character.stats[3]*1.125));
                }else {
                    Message.questionS("You do not have enough mana to cast Heal!");
                }
                break;
            case DRAIN_LIFE:
                if (character.mpCur>=5) {
                    character.mpCur -= 5;
                    Message.questionS("You use Drain Life on your enemy and yourself!");
                    int drain = (int)Math.round(character.stats[8]*1.5+character.stats[7]*1.25+character.stats[6]+character.stats[5]*1.125+character.stats[4]*1.125+character.stats[3]*1.125);
                    character.heal(drain);
                    return drain;
                }else {
                    Message.questionS("You do not have enough mana to use Drain Life!");
                }
                break;
            case HEADSHOT:
                if (character.mpCur>=2) {
                    character.mpCur -= 2;
                    weaponAttack = Items.BOW_AND_ARROWS;
                    Message.questionS("You use Headshot on your enemy!");
                    return (int)Math.round(getWeaponDamage(weaponAttack)*(1+(character.stats[1]+character.stats[7])/100.0));
                }else {
                    Message.questionS("You do not have enough mana to use Headshot!");
                }
                break;
            case STRONG_STRIKE:
                if (character.mpCur>=2){
                    character.mpCur-=2;
                    weaponAttack = chooseWeapon();
                    Message.questionS("You use Strong Strike on your enemy!");
                    return (int)Math.round(getWeaponDamage(weaponAttack)*(1+(character.stats[1]+character.stats[7])/100.0));
                }else {
                    Message.questionS("You do not have enough mana to cast Eldritch Blast!");
                }
                break;
            case HEAL:
                if (character.mpCur>=2){
                    character.mpCur-=2;
                    Message.questionS("You use Heal on yourself!");
                    character.heal((int)Math.round(character.stats[8]*2+character.stats[5]*1.5+character.stats[4]*1.25+character.stats[3]*1.25));
                }else {
                    Message.questionS("You do not have enough mana to cast Eldritch Blast!");
                }
                break;
            case MEDITATE:
                Message.questionS("You use Meditate on yourself!");
                character.mpRest((int)Math.round(character.stats[5]*1.125+character.stats[4]*1.125+character.stats[3]*1.125));
                break;
            case RECOVERY:
                Message.questionS("You use Recovery on yourself!");
                character.heal((int)Math.round(character.stats[2]*1.125+character.stats[1]*1.125+character.stats[0]*1.125));
                character.mpRest((int)Math.round(character.stats[5]*1.125+character.stats[4]*1.125+character.stats[3]*1.125));
                break;
        }
        return 0;
    }
    private void defend(){
        switch (getRandom(2)){
            case 1:
                Message.questionS("You successfully defend from your foes attack!");
                break;
            case 2:
                Message.questionS("You defend yourself at the time when your enemy does nothing!");
                break;
        }
    }
    private void flee(int place,int difficulty, String enemyName){
        if (character.stats[1]+getRandom(20)>difficulty+getRandom(20)){
            Message.questionS("You successfully flee from battle!");
            RandomEncounter(place,difficulty+1);
        }else {
            Message.questionS(enemyName+" attacks you as you try to flee which prevents you from running away!");
            character.hpCur -= (int)Math.round(difficulty/4.0);
        }
    }

    private String getPlace(int place){
        switch (place){
            case 1:
                return "dungeon";
            case 2:
                return "town";
            case 3:
                return "forest";
            case 4:
                return "mountain";
            case 5:
                return "desert";
            case 6:
                return "cave";
            case 7:
                return "strange";
            default:
                return "unknown";
        }
    }
    private void treasure(int place, int difficulty){
        String str1 = "", str2 = "", str3 = "";
        int reward = getRandom(3);
        String rew = "";
        switch (reward){
            case 1:
                rew = "wooden";
                break;
            case 2:
                rew = "iron";
                break;
            case 3:
                rew = "strange";
                break;
        }
        switch (place){
            case 1://Dungeon
                str1 = "dungeon";
                str2 = "inside a small branch room.";
                break;
            case 2://town
                str1 = "town";
                str2 = "inside a dead end street.";
                break;
            case 3://Forest
                str1 = "forest";
                str2 = "upon a small clearing.";
                break;
            case 4://Mountain
                str1 = "mountain";
                str2 = "inside a small valley.";
                break;
            case 5://Desert
                str1 = "desert";
                str2 = "under a small sand hill.";
                break;
            case 6://Cave
                str1 = "caves";
                str2 = "inside a small branch cave.";
                break;
            case 7://Mystery Wilderness
                str1 = "wilderness";
                str2 = "upon a clearing with strange flowers.";
                reward *= 2;
        }
        reward += Math.floor((difficulty / 10.0) + (character.stats[7]/25.0));
        if (character.has(Abilities.TRAP_KNOWLEDGE)||(getRandom(20)+character.stats[4]>40&&getRandom(20)+character.stats[1]>40)){
            str3 = "You check for traps and if found disarm them. After that you open the chest";
        }
        switch (Message.questionI(Message.message(
                "As you wander around the "+str1+" you stumble upon "+rew+" chest "+str2,
                "You open the chest",
                "You leave without opening the chest",
                str3,
                "",
                "",
                ""))){
            case 1:
                if (getRandom(100)>75){
                    chestReward(reward);
                }else {
                    int dam = getRandom(5)+getRandom(5)+getRandom(5);
                    character.hpCur -= dam;
                    if (character.hpCur>0){
                        Message.questionS("As you try to open the chest you find that it was trapped.\n" +
                                "When the trap is triggered it destroys the chest with its content and \n" +
                                "damages you for "+dam+" points of damage leaving you with "+character.hpCur+" HP!");
                    }else {
                        Message.questionS("As you try to open the chest you find that it was trapped.\n" +
                                "When the trap is triggered it destroys the chest with its content and \n" +
                                "damages you for "+dam+" points of damage which kills you!");
                        game.startGame();
                    }
                }
                break;
            case 2:
                break;
            case 3:
                if (getRandom(100)>75){
                    chestReward(reward);
                }else {
                    Message.questionS("You quickly find a trap and disarm it. After which you open the chest.");
                    chestReward((int) (reward+Math.max(1, Math.floor(reward/2.0))));
                }
            default:
        }

    }
    private void chestReward(int reward){
        if (reward == 1){
            Items itemReward = Items.getRandomPrice();
            Message.questionS("Inside the chest you have found "+itemReward+" and take that with you as you leave this place");
            character.inventory.add(itemReward);
        }else {
            Items[] itemReward = new Items[(int) Math.max(2,Math.floor(reward/3.0))];
            StringBuilder sbf = new StringBuilder();
            sbf.append("Inside the chest you have found ");
            for (int i = 0; i<itemReward.length;i++){
                if (reward>5){
                    itemReward[i] = Items.getRandomItem();
                    reward *= 0.75;
                }else {
                    itemReward[i] = Items.getRandomPrice();
                }
                character.inventory.add(itemReward[i]);
                sbf.append(itemReward[i]);
                if (i!=itemReward.length-1){
                    sbf.append(", ");
                }
            }
            sbf.append("!");// and take all of that with you as you leave this place
            if (QuestType.equalsIgnoreCase("collect")){
                sbf.append(" You also have found items for your Collect Quest!");
            }
            Message.questionS(sbf.toString());
            if (QuestType.equalsIgnoreCase("collect")){
                getDrop();
            }
        }
    }
    private void encounter(int place){
        switch (getRandom(3)){
            case 1:
                wanderingMerchant(place,false);
                break;
            case 2:
                baseOfficer(place,false);
                break;
            case 3:
                reclusiveHermit(place);
        }
    }

    private int getRandom(int max){
        return (int)(Math.random()*max+1);
    }

    private void wanderingMerchant(int place, boolean delivered){
        String str1 = "";
        boolean quest =false;
        if (QuestType.equalsIgnoreCase("deliver")&& QuestPlace ==place&&QuestTarget==3){
            str1 = "3: 'Here is your delivery from Adventure Guild!' you say as you deliver to your Quests target("+Quest+") delivery!";
            quest = true;
        }
        switch (Message.questionI(Message.message(
                "'Welcome to my wandering shop. Would you like to buy something?' asks wandering Merchant",
                "1: 'What have you for sell?' you ask",
                "2: 'I do not have time nor coins to buy something I would like right now. Be Safe' you say as walk away",
                str1,
                "",
                "",
                ""))){
            case 1:
                Items[] items = new Items[5];
                for (int i = 0; i < items.length;i++){
                    items[i] = Items.getRandomItem();
                }
                Items[] price = new Items[5];
                for (int i = 0; i < price.length;i++){
                    price[i] = Items.getRandomPrice();
                }
                merchantBuy(items, price);
                break;
            case 3:
                if (quest&&!delivered){
                    completeDelivery();
                    wanderingMerchant(place,true);
                }
        }
    }
    private void merchantBuy(Items[] items, Items[] price){
        int i = Message.questionI(Message.message(
                "'Here is my catalog. On the left is Items, on the right is theirs Price' says Merchant",
                "1: "+items[0]+" | "+price[0],
                "2: "+items[1]+" | "+price[1],
                "3: "+items[2]+" | "+price[2],
                "4: "+items[3]+" | "+price[3],
                "5: "+items[4]+" | "+price[4],
                "6: Leave without buying anything"));
        if (0<i && i<6){
            character.buy(items[i-1],price[i-1]);
        }else if (i!=6){
            Message.limit(6);
            merchantBuy(items, price);
        }
    }
    private void baseOfficer(int place, boolean delivered){
        String str1 = "";
        boolean quest =false;
        if (QuestType.equalsIgnoreCase("deliver")&& QuestPlace ==place&&QuestTarget==3){
            str1 = "3: 'Here is your delivery from Adventure Guild!' you say as you deliver to your Quests target("+Quest+") his delivery!";
            quest = true;
        }
        switch (Message.questionI(Message.message(
                "'Welcome to this guards base. Why have you sought me?' asks base Officer",
                "1: 'I wish to train my self. Could you allow me to use training hall of this base?'",
                "2: 'I was just exploring. I will be on my way. Till we meet again!'",
                str1,
                "",
                "",
                ""))){
            case 1:
                int ranLuck = getRandom(20)+character.stats[7];
                if ((getRandom(20)+character.stats[6])>=30 && ranLuck>=30){
                    if (ranLuck>=60){
                        Message.questionS("You are very lucky that nobody uses training hall in this base and that it even has enchantments that increase training benefits.");
                        trainingHall(true);
                    }else {
                        Message.questionS("You are lucky that nobody uses training hall in this base.");
                        trainingHall(false);
                    }
                }else if (ranLuck>=20 && character.has(Items.SILVER_COIN)&&character.has(Items.GOLD_COIN)){
                    switch (Message.questionI(Message.message(
                            "Unfortunately for you I cannot allow free access to it. I can however grant it for 1 Silver or 1 Gold Coin\n" +
                                    "Paying with Gold Coin will even grant you access to training hall's enchantments that will increase your training benefits!",
                            "1: Pay with Silver Coin",
                            "2: Pay with Gold Coin",
                            "3: Refuse this exchange",
                            "",
                            "",
                            ""))) {
                        case 1:
                            character.inventory.remove(Items.SILVER_COIN);
                            trainingHall(false);
                            break;
                        case 2:
                            character.inventory.remove(Items.GOLD_COIN);
                            trainingHall(true);
                            break;
                    }
                }else if (ranLuck>=20 && character.has(Items.SILVER_COIN)){
                    if (Message.questionI(Message.message(
                            "Unfortunately for you I cannot allow free access to it. I can however grant it for 1 Silver Coin",
                            "1: Pay with Silver Coin",
                            "2: Refuse this exchange",
                            "",
                            "",
                            "",
                            "")) == 1) {
                        character.inventory.remove(Items.SILVER_COIN);
                        trainingHall(false);
                    }
                }else if (ranLuck>=20 && character.has(Items.GOLD_COIN)){
                    if (Message.questionI(Message.message(
                            "Unfortunately for you I cannot allow free access to it. I can however grant it for 1 Gold Coin\n" +
                                    "It will even grant you access to training hall's enchantments that will increase your training benefits!",
                            "1: Pay with Gold Coin",
                            "2: Refuse this exchange",
                            "",
                            "",
                            "",
                            "")) == 1) {
                        character.inventory.remove(Items.GOLD_COIN);
                        trainingHall(true);
                    }
                }else {
                    Message.questionS("Unfortunately for you I cannot allow it.");
                }
                break;
            case 3:
                if (quest&&!delivered){
                    completeDelivery();
                    baseOfficer(place,true);
                }
        }
    }
    private void trainingHall(boolean enchantments){
        switch (Message.questionI(Message.message("Which stat of your body will you train?",
                "1: Strength (current: "+character.stats[0]+")",
                "2: Dexterity (current: "+character.stats[1]+")",
                "3: Constitution (current: "+character.stats[2]+")",
                "4: Leave",
                "",
                ""))){
            case 1:
                if (enchantments){
                    character.stats[0]+=4;
                }else {
                    character.stats[0]+=1;
                }
                Message.questionS("You increased your Strength to "+character.stats[0]+"!");
                break;
            case 2:
                if (enchantments){
                    character.stats[1]+=4;
                }else {
                    character.stats[1]+=1;
                }
                Message.questionS("You increased your Dexterity to "+character.stats[1]+"!");
                break;
            case 3:
                if (enchantments){
                    character.stats[2]+=4;
                }else {
                    character.stats[2]+=1;
                }
                Message.questionS("You increased your Constitution to "+character.stats[2]+"!");
                break;
            case 4:
                Message.questionS("You do nothing and leave this base.");
                break;
        }
    }
    private void reclusiveHermit(int place){
        String str1 = "";
        boolean quest =false;
        if (QuestType.equalsIgnoreCase("deliver")&& QuestPlace ==place&&QuestTarget==3){
            str1 = "3: 'Here is your delivery from Adventure Guild!' you say as you deliver to your Quests target("+Quest+") his delivery!";
            quest = true;
        }
        switch (Message.questionI(Message.message(
                "'Why did you come to me?' asks you the reclusive Hermit",
                "1: 'I seek the knowledge that you have! May I get even a drop of it?' you ask the hermit.",
                "2: 'I was only exploring when i came upon this place. I shall be on my way, so that I do not disturb you hermit. Goodbye!'",
                str1,
                "",
                "",
                ""))){
            case 1:
                if ((getRandom(20)+character.stats[6])<=50){
                    Message.questionS("'Be gone!' says The Hermit as he casts some spell and now you find yourself in different place!\nPress Enter to continue");
                    randomTeleport();
                }else {
                    String str2="";
                    switch (getRandom(3)){
                        case 1:
                            str2="Mind";
                            character.stats[3]+=1;
                            character.stats[4]+=1;
                            break;
                        case 2:
                            str2="Will";
                            character.stats[5]+=2;
                            break;
                        case 3:
                            str2="Magic";
                            character.stats[8]+=2;
                    }
                    Message.questionS("'Take this and be gone!' says Hermit as he throws at you a spell that even as it transports you to a different place " +
                            "you feel as your "+str2+" increases!");
                    Town();
                }
                break;
            case 3:
                Message.questionS("Hermit takes the package and says 'Thanks for that, but now be gone!'. After that he throws at you a teleportation spell");
                if (quest){
                    completeDelivery();
                }
                randomTeleport();
        }
    }
    private void randomTeleport() {
        switch (getRandom(4)){
            case 1:
                Dungeon(getRandom(3));
            case 2:
                Town();
            case 3:
                Wilderness();
            case 4:
                RandomEncounter(getRandom(7),getRandom(10));
        }
    }

    private void completeDelivery(){
        progressCur++;
        if (progressMax == progressCur) {
            int points=(2*progressMax);
            Message.questionS("You have completed Delivery Quest! You gain "+points+" points to use between adventures!");
            earnedPoints+=points;
        }else {
            earnedPoints+=2;
            Message.questionS("You have progressed in your Delivery Quest! ["+progressCur+"/"+progressMax+"] You have gained 2 points to use between adventures!");
        }
    }
    private void battleCollect(int enemy){
        switch (enemy){
            case 1:
                switch (QuestTarget){
                    case 2:
                        completeQuest(true,2);
                        break;
                    case 4: case 7: case 9:
                        getDrop();
                }
                break;
            case 2:
                switch (QuestTarget){
                    case 3: case 7: case 8: case 9: case 10:
                        getDrop();
                }
                break;
            case 3:
                switch (QuestTarget){
                    case 1:
                        completeQuest(true,1);
                        break;
                    case 2:
                        completeQuest(true,2);
                        break;
                    case 7: case 9:
                        getDrop();
                }
                break;
            case 4:
                switch (QuestTarget){
                    case 1: case 6:
                        completeQuest(true,1);
                        break;
                    case 2:
                        completeQuest(true,2);
                        break;
                    case 7: case 8:
                        getDrop();
                }
                break;
            case 5:
                switch (QuestTarget){
                    case 1: case 3: case 4: case 6: case 7: case 8: case 9: case 10:
                        getDrop();
                }
                break;
        }
    }
    private void getDrop() {
        int x = 0;
        if (character.stats[7] + getRandom(20) > 99) {
            x += (int)Math.floor(character.stats[7] / 5.0);
        } else if (character.stats[7] + getRandom(20) > 74) {
            x += (int)Math.floor(character.stats[7] / 10.0);
        } else if (character.stats[7] + getRandom(20) > 49) {
            x += (int)Math.floor(character.stats[7] / 15.0);
        } else if (character.stats[7] + getRandom(20) > 34) {
            x += (int)Math.floor(character.stats[7] / 20.0);
        } else if (character.stats[7] + getRandom(20) > 19) {
            x = 1;
        }
        completeQuest(true,x);
    }
    private void completeQuest(boolean collect, int n){
        progressCur+=n;
        if (progressCur>=progressMax){
            int points;
            if (collect){
                points=progressMax/2;
            }else {
                points=progressMax;
            }
            Message.questionS("You have completed "+QuestType+" Quest! You gain "+points+" points to use between adventures!");
            earnedPoints+=points;
        }else if (QuestProgressStep ==2&&(progressCur==Math.floor(progressMax*0.75))||
                (QuestProgressStep ==1&&progressCur==Math.floor(progressMax*0.5))||
                (QuestProgressStep ==0&&progressCur==Math.floor(progressMax*0.25))){
            QuestProgressStep++;
            earnedPoints+=2;
            Message.questionS("You have progressed in your "+QuestType+" Quest! ["+progressCur+"/"+progressMax+"] You have gained 2 point to use between adventures!");
        }else {
            Message.questionS("You have progressed in your "+QuestType+" Quest! ["+progressCur+"/"+progressMax+"]!");
        }
    }
}