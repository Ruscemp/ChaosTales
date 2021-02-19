package game;

import java.util.ArrayList;
import java.util.Arrays;

public class Character {
    String name;
    String gender;
    String race;
    String role;
    String god;
    int[] stats = new int[9];
    /* 0 = strength; 1 = dexterity; 2 = constitution;
    3 = intelligence; 4 = wisdom; 5 = will;
    6 = charisma; 7 = luck; 8 = magic; */
    int hpMax, mpMax;
    int hpCur, mpCur;
    ArrayList<Abilities> abilitiesUnlocked = new ArrayList<>();
    ArrayList<Items> inventory = new ArrayList<>();

    Character(int Race, int Class, int God, int Gender, String Name){
        do {
            Arrays.fill(stats, 5);
            switch (Race){
                case 1:
                    this.race = "Human";
                    for (int i = 0; i<stats.length;i++) {
                        stats[i] += 1;
                    }
                    addAbility(Abilities.RECOVERY);
                    break;
                case 2:
                    this.race = "Dwarf";
                    stats[0]+=6;
                    stats[2]+=8;
                    stats[5]+=4;
                    break;
                case 3:
                    this.race = "Elf";
                    stats[1]+=2;
                    stats[4]+=2;
                    stats[6]+=2;
                    stats[7]+=1;
                    stats[8]+=2;
                    addAbility(Abilities.EYE_OF_THE_HAWk);
            }
            switch (Class){
                case 1:
                    this.role = "Warrior";
                    stats[0] += 4;
                    stats[1] += 2;
                    stats[2] += 4;
                    stats[3] -= 2;
                    stats[4] -= 2;
                    stats[6] -= 2;
                    stats[8] -= 4;
                    addAbility(Abilities.STRONG_STRIKE);
                    addAbility(Abilities.RECOVERY);
                    inventory.add(Items.SWORD);
                    inventory.add(Items.IRON_ARMOUR);
                    break;
                case 2:
                    this.role = "Archer";
                    stats[0] -= 2;
                    stats[1] += 4;
                    stats[2] -= 4;
                    stats[4] += 4;
                    stats[7] += 2;
                    stats[8] -= 4;
                    addAbility(Abilities.HEADSHOT);
                    addAbility(Abilities.FORAGE);
                    inventory.add(Items.BOW_AND_ARROWS);
                    inventory.add(Items.LEATHER_ARMOUR);
                    break;
                case 3:
                    this.role = "Thief";
                    stats[0] -= 4;
                    stats[1] += 4;
                    stats[2] -= 4;
                    stats[6] += 2;
                    stats[7] += 4;
                    stats[8] -= 2;
                    addAbility(Abilities.STEAL_AND_SNEAK);
                    addAbility(Abilities.TRAP_KNOWLEDGE);
                    inventory.add(Items.DAGGER);
                    inventory.add(Items.COPPER_COIN);
                    break;
                case 4:
                    this.role = "Wizard";
                    stats[1] -= 2;
                    stats[0] -= 4;
                    stats[2] -= 4;
                    stats[3] += 4;
                    stats[4] += 2;
                    stats[8] += 4;
                    addAbility(Abilities.CAST_FIREBALL);
                    addAbility(Abilities.MEDITATE);
                    inventory.add(Items.STAFF);
                    inventory.add(Items.MANA_POTION);
                    break;
                case 5:
                    this.role = "Cleric";
                    stats[0] -= 4;
                    stats[2] += 2;
                    stats[3] -= 2;
                    stats[5] += 4;
                    stats[7] -= 4;
                    stats[8] += 4;
                    addAbility(Abilities.CAST_HEAL);
                    addAbility(Abilities.CAST_HOLY_STRIKE);
                    inventory.add(Items.MACE);
                    inventory.add(Items.HEALTH_POTION);
                    break;
                case 6:
                    this.role = "Warlock";
                    stats[3] += 2;
                    stats[4] -= 2;
                    stats[5] += 4;
                    stats[6] -= 4;
                    stats[7] -= 4;
                    stats[8] += 4;
                    addAbility(Abilities.CAST_FIREBALL);
                    addAbility(Abilities.CAST_ELDRITCH_BLAST);
                    inventory.add(Items.STAFF);
                    inventory.add(Items.POISON);
            }
            switch (God){
                case 1:
                    this.god = "Whartiel of White";
                    addAbility(Abilities.HEAL);
                    break;
                case 2:
                    this.god = "Grind of Grey";
                    addAbility(Abilities.SHADOW_HIDE);
                    break;
                case 3:
                    this.god = "Dio of Dark";
                    addAbility(Abilities.DRAIN_LIFE);
                    break;
                case 4:
                    this.god = "Dgund of Dwarfs";
                    stats[2] +=4;
                    stats[0] +=3;
                    stats[5] +=2;
                    break;
                case 5:
                    this.god = "Han of Humans";
                    for (int i = 0; i<stats.length;i++) {
                        stats[i] += 1;
                    }
                    break;
                case 6:
                    this.god = "Eloiri of Elves";
                    stats[6] += 4;
                    stats[1] += 3;
                    stats[4] += 2;
                    break;
                case 7:
                    this.god = "Firtan of Forgotten";
                    addAbility(Abilities.WIPE_MEMORY);
                    break;
                case 8:
                    this.god = "Maiar of Magic";
                    addAbility(Abilities.MEDITATE);
                    break;
                case 9:
                    this.god = "Lern of Leaders";
                    stats[6] += 4;
                    stats[4] += 2;
                    stats[5] += 2;
                    stats[3] += 1;
                    break;
                case 10:
                    this.god = "Adon of Artisans";
                    stats[1] += 3;
                    stats[6] += 2;
                    stats[3] += 2;
                    stats[7] += 2;
                    break;
                case 11:
                    this.god = "Miltorian of Merchants";
                    stats[6] += 3;
                    stats[3] += 2;
                    stats[4] += 2;
                    stats[5] += 2;
                    break;
                case 12:
                    this.god = "Warlon of Warriors";
                    stats[0] += 4;
                    stats[2] += 3;
                    stats[1] += 1;
                    stats[5] += 1;
                    break;
                case 13:
                    this.god = "Aleana of Archers";
                    stats[1] += 3;
                    stats[6] += 2;
                    stats[7] += 2;
                    stats[0] += 1;
                    stats[4] += 1;
                    break;
                case 14:
                    this.god = "Tifola of Thieves";
                    stats[1] += 3;
                    stats[6] += 3;
                    stats[7] += 3;
                    break;
                case 15:
                    this.god = "Mirl of Mages";
                    stats[8] += 5;
                    stats[3] += 2;
                    stats[4] += 1;
                    stats[5] += 1;
            }
            switch (Gender){
                case 1:
                    gender = "Male";
                    stats[0] += 1;
                    stats[2] += 1;
                    break;
                case 2:
                    gender = "Female";
                    stats[1] += 1;
                    stats[6] += 1;
                    break;
                case 3:
                    gender = "Other";
                    stats[7] += 2;
                    break;
            }
            hpMax = stats[0]+stats[2]*2;
            mpMax = stats[5]+stats[8]*2;
            this.name = Name;
            print();
        } while (createNew());
    }
    public Character(String name, String gender, String race, String role, String god, int[] stats, ArrayList<Abilities> abilities, ArrayList<Items> items) {
        this.name = name;
        this.gender = gender;
        this.race = race;
        this.role = role;
        this.god = god;
        this.stats = stats;
        hpMax = this.stats[0]+this.stats[2]*2;
        mpMax = this.stats[5]+this.stats[8]*2;
        this.abilitiesUnlocked = abilities;
        this.inventory = items;
    }
    private boolean createNew(){
        switch (Message.questionI(Message.message("Do you wish to save this character("+name+") or create a new one?",
                "", "", "", "",
                "1: Save this character("+name+")","2: Create a new Character"))){
            case 1:
                return false;
            case 2:
                return true;
            default:
                Message.questionS(Message.limit(2));
                return createNew();
        }
    }

    void upgradeStatType(int i, Game game){
        String str ="", str1 ="", str2 ="", str3 =" | Upgrade cost: ";
        switch (i){
            case 0:
                str = "Strength: ";
                str1 = "Dexterity: ";
                str2 = "Constitution: ";
                break;
            case 1:
                str = "Intelligence: ";
                str1 = "Wisdom: ";
                str2 = "Will: ";
                break;
            case 2:
                str = "Charisma: ";
                str1 = "Luck: ";
                str2 = "Magic: ";
        }
        int cost = (int) (Math.ceil((double)stats[i*3]/5)*5);
        int cost1 = (int) (Math.ceil((double)stats[i*3+1]/5)*5);
        int cost2 = (int) (Math.ceil((double)stats[i*3+2]/5)*5);
        switch (Message.questionI(Message.message("Price for upgrading a stat depends on a level of that stat",
                "Example: lvl 1-5 costs 5, level 6-10 costs 10", "Which stat shall you upgrade? (Current points: "+game.points+")\n",
                "1: "+str+stats[i*3]+str3+cost,
                "2: "+str1+stats[i*3+1]+str3+cost1,
                "3: "+str2+stats[i*3+2]+str3+cost2,
                "4: back"))){
            case 1:
                upgradeStat(i*3, str, cost, game);
                break;
            case 2:
                upgradeStat(i*3+1, str1, cost1, game);
                break;
            case 3:
                upgradeStat(i*3+2, str2, cost2, game);
                break;
            case 4:
                game.upgrade(game.characters.indexOf(this));
                break;
            default:
                Message.questionS(Message.limit(4));
                upgradeStatType(i, game);
        }

    }
    private void upgradeStat(int stat, String statS, int cost, Game game){
        if (game.points >=(Math.ceil((double)stats[stat]/5)*5)){
            stats[stat]++;
            game.points-=cost;
            System.out.println("New "+statS+" level is "+stats[stat]);
            System.out.println("Remaining points: "+game.points);
        }else {
            System.out.println("Failed to upgrade!\nNeed "+(cost-game.points)+" more points!\nTry again after adventure!");
        }
        Message.questionS("Press 'Enter' to continue");
        upgradeStatType((int) Math.floor((double) stat/3), game);
    }

    public void print(){
        StringBuilder sbf = new StringBuilder();
        sbf.append("Your characters name is ").append(name);
        switch (gender){
            case "Male":
                sbf.append(". He is ");
                break;
            case "Female":
                sbf.append(". She is ");
                break;
            case "Other":
                sbf.append(". They are ");
        }
        sbf.append(race).append(" ").append(role);
        sbf.append(" and follow");
        if (!gender.equalsIgnoreCase("Other")){
            sbf.append("s");
        }
        sbf.append(" ").append(god).append(".").append('\n');
        switch (gender){
            case "Male":
                sbf.append("His");
                break;
            case "Female":
                sbf.append("Her");
                break;
            case "Other":
                sbf.append("Theirs");
        }
        sbf.append(" stats are:");
        sbf.append('\n');
        sbf.append("Strength: ").append(stats[0]).append(" Dexterity: ").append(stats[1]).append(" Constitution: ").append(stats[2]).append('\n');
        sbf.append("Intelligence: ").append(stats[3]).append(" Wisdom: ").append(stats[4]).append(" Will: ").append(stats[5]).append('\n');
        sbf.append("Charisma: ").append(stats[6]).append(" Luck: ").append(stats[7]).append(" Magic: ").append(stats[8]).append('\n');
        sbf.append("Abilities: ").append(getAbilities());
        sbf.append("Items: ").append(getItems());
        System.out.println(sbf.toString());
        sbf.setLength(0);
        Message.questionS("press Enter to continue");
    }
    private String getAbilities(){
        StringBuilder sbf = new StringBuilder();
        int limit = abilitiesUnlocked.size();
        if (limit == 0){
            sbf.append("0 unlocked");
        } else {
            for (int i = 0; i < limit; i++){
                sbf.append(abilitiesUnlocked.get(i));
                if (i != (limit-1)){
                    sbf.append(", ");
                } else {
                    sbf.append('\n');
                }
            }
        }
        return sbf.toString();
    }
    private String getItems(){
        StringBuilder sbf = new StringBuilder();
        int limit = inventory.size();
        if (limit == 0){
            sbf.append("0 in inventory");
        } else {
            for (int i = 0; i < limit; i++){
                sbf.append(inventory.get(i));
                if (i != (limit-1)){
                    sbf.append(", ");
                } else {
                    sbf.append('\n');
                }
            }
        }
        return sbf.toString();
    }
    public String toString(){
        StringBuilder sbf = new StringBuilder();
        sbf.append(name).append("|");
        sbf.append(gender).append("|");
        sbf.append(race).append("|");
        sbf.append(role).append("|");
        sbf.append(god).append("|");
        for (int i:stats){
            sbf.append(i).append("#");
        }
        sbf.append("|");
        if (abilitiesUnlocked.size()==0){
            sbf.append("$");
        }else {
            for (Abilities a:abilitiesUnlocked){
                sbf.append(a).append("$");
            }
        }
        sbf.append("|");
        if (inventory.size()==0){
            sbf.append("%");
        }else {
            for (Items a: inventory){
                sbf.append(a).append("%");
            }
        }
        return sbf.toString();
    }
    public String shortString(){
        return name+" ("+race+" "+role+" of '"+god+"')";
    }
    public void buy(Items item, Items price){
        if (inventory.contains(price)){
            inventory.remove(price);
            inventory.add(item);
            Message.questionS("You have bought "+item+"!\nPress Enter to continue!");
        } else {
            Message.questionS("You tried to buy an Item without having coins to pay for it! Try again later when you have coins to pay!");
        }
    }
    public boolean has(Items item){
        return inventory.contains(item);
    }
    public boolean has(Abilities ability){
        return abilitiesUnlocked.contains(ability);
    }
    public void heal(int heal){
        if (hpCur + heal > hpMax) {
            hpCur = hpMax;
        }else {
            hpCur+=heal;
        }
    }
    public void mpRest(int mpRestore){
        if (mpCur + mpRestore > mpMax) {
            mpCur = mpMax;
        }else {
            mpCur+=mpRestore;
        }
    }
    private void addAbility(Abilities ability){
        if (!abilitiesUnlocked.contains(ability)){
            abilitiesUnlocked.add(ability);
        }
    }
}
