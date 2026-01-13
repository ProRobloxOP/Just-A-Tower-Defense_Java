package Modules.Systems;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

import Modules.Infos.Towers.TowerInfos;
import Modules.Infos.Towers.TowerInfos.TowerInfo;

public class Towers {
    public static class Tower implements TowerInfo {
        private UUID UUID;

        public Tower() {}

        public void setUUID(UUID UUID) {
            this.UUID = UUID;
        }

        public UUID getUUID() {
            return UUID;
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public HashMap<String, Double> getStats() {
            return null;
        }

        @Override
        public Runnable getGuiCreator(Graphics2D g2d) {
            return null;
        }
    }

    public Towers(){}

    public TowerInfo getTowerInfo(String name, int x, int y, int width, int height) {
        TowerInfos towerInfos = new TowerInfos();
        return towerInfos.getTower(name, x, y, width, height);
    }

    public Tower getTower(String name, int x, int y, int width, int height) {
        Tower tower = (Tower) getTowerInfo(name, x, y, width, height);
        tower.UUID = UUID.randomUUID();

        return tower;
    }
}
