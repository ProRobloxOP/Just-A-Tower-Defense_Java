package Modules.Systems;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import Modules.Infos.Towers.TowerInfos;
import Modules.Infos.Towers.TowerInfos.TowerInfo;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Towers {
    private static final HashMap<UUID, Tower> towers = new HashMap<>();

    public static class Tower implements TowerInfo {
        private final UUID UUID;
        private Map<String, Float> spriteScales;
        private HashMap<String, Float> stats;
        private Sprite sprite;
        private String texturePath;
        private String name;

        float x = 0, y = 0, scale = 1;
        boolean notLoaded = true;

        public Tower(String name, float x, float y, float scale) {
            TowerInfo towerInfo = TowerInfos.get(name, x, y, scale);

            this.name = towerInfo.getName();
            this.UUID = new UUID(Long.MAX_VALUE, Long.MIN_VALUE);
            this.sprite = towerInfo.getSprite();
            this.texturePath = towerInfo.getTexturePath();
            this.spriteScales = towerInfo.getSpriteScales();

            this.x = x;
            this.y = y;
            this.scale = scale;
        }

        public UUID getUUID() {
            return UUID;
        }

        @Override
        public String getTexturePath() {
            return texturePath;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Map<String, Float> getSpriteScales() {
            return spriteScales;
        }

        @Override
        public HashMap<String, Float> getStats() {
            return stats;
        }

        @Override
        public Sprite getSprite() {
            return sprite;
        }

        @Override
        public Runnable loadSprite() {
            sprite = TowerInfos.loadTowerSprite(texturePath, x, y, scale);
            notLoaded = false;
            return null;
        }

        @Override
        public Runnable drawSprite(Batch batch) {
            TowerInfos.drawTowerSprite(name, sprite, batch, notLoaded);
            return null;
        }
    }

    public Towers(){}

    public HashMap<UUID, Tower> getTowers() {
        return towers;
    }

    public static Tower create(String name, int x, int y, float scale) {
       Tower tower = new Tower(name, x, y, scale);

        towers.put(tower.getUUID(), tower);
        return tower;
    }
}
