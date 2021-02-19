package game;

import java.util.Random;

public enum Items {
    ROPE,
    BEDROLL,
    TENT,
    MEAL,
    BACKPACK,
    TORCH,
    SWORD,
    MACE,
    SHIELD,
    ROBES,
    DAGGER,
    IRON_ARMOUR,
    LEATHER_ARMOUR,
    BOW_AND_ARROWS,
    STAFF,
    MANA_POTION,
    HEALTH_POTION,
    POISON,
    COPPER_COIN,
    SILVER_COIN,
    GOLD_COIN,
    AMULET_OF_PROTECTION,
    SCROLL_OF_RETURN,
    SCROLL_OF_HEALING,
    SCROLL_OF_CAMPING,
    RING_OF_NIGTH_VISION,
    RING_OF_GREATER_STRENGTH,
    RING_OF_GREATER_DEXTERITY,
    RING_OF_GREATER_CONSTITUTION,
    POTION_OF_INVISIBILITY;


    private static Random random = new Random();

    public static Items getRandomItem() {
        return values()[random.nextInt(values().length)];
    }
    public static Items getRandomPrice(){
        switch ((int)Math.ceil(random.nextInt()*3+1)){
            case 1:
                return COPPER_COIN;
            case 2:
                return SILVER_COIN;
            default:
                return GOLD_COIN;
        }
    }
}