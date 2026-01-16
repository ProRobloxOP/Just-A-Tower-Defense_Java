package Modules.Utilities;

import Modules.Systems.Towers;
import Modules.Systems.Towers.Tower;
import Modules.Tools.Tuples.Pair;

import java.util.*;


public class UserData {
    private static class UserInventory {
        public static HashMap<UUID, Tower> towers = new HashMap<>();
        public static HashMap<String, Float> currencies = new HashMap<>();
        public static Vector<Pair<String, Tower>> loadout = new Vector<>();
    }

    public static void load() {
        Tower test1 = Towers.create("Test", 0, 0, 1);
        UserInventory.towers.put(test1.getUUID(), test1);
        UserInventory.loadout.add(new Pair<>("1", test1));
    }

    // Inventory Methods

    public static HashMap<UUID, Tower> getTowers() {
        return UserInventory.towers;
    }

    public static Vector<Pair<String, Tower>> getLoadout() {
        return UserInventory.loadout;
    }

    public HashMap<String, Float> getCurrencies() {
        return UserInventory.currencies;
    };
}
