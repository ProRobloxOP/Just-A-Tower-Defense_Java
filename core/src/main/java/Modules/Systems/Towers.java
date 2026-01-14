package Modules.Systems;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

import Modules.Infos.Towers.TowerInfos;
import Modules.Infos.Towers.TowerInfos.TowerInfo;

public class Towers {
    public abstract static class Tower implements TowerInfo {
        private UUID UUID;

        public Tower() {}
    }

    public Towers(){}

    public TowerInfo getTowerInfo(String name, int x, int y, float scale) {
        TowerInfos towerInfos = new TowerInfos();
        return towerInfos.getTower(name, x, y, scale);
    }

    public Tower getTower(String name, int x, int y, float scale) {
        Tower tower = (Tower) getTowerInfo(name, x, y, scale);
        tower.UUID = UUID.randomUUID();

        return tower;
    }
}
