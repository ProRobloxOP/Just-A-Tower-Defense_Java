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
        private UUID UUID;
        private Map<String, Float> spriteScales;
        private HashMap<String, Float> stats;
        private Sprite sprite;
        private String texturePath;
        private String name;

        private float x = 0, y = 0, scale = 1;
        boolean notLoaded = true;

        public Tower(String name, float x, float y, float scale) {
            TowerInfo towerInfo = TowerInfos.get(name, x, y, scale);

            this.name = towerInfo.getName();
            this.UUID = java.util.UUID.randomUUID();
            this.sprite = towerInfo.getSprite();
            this.texturePath = towerInfo.getTexturePath();
            this.spriteScales = towerInfo.getSpriteScales();

            this.x = x;
            this.y = y;
            this.scale = scale;
        }

        public Tower(String name, float x,  float y, float scale, String texturePath){
            Tower tower = new Tower(name, x, y, scale, texturePath);
            this.texturePath = texturePath;
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
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
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

        public void setPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public Towers(){}

    public static HashMap<UUID, Tower> getTowers() {
        return towers;
    }

    public static Tower create(String name, float x, float y, float scale) {
        return new Tower(name, x, y, scale);
    }

    public static Tower create(String name, float x, float y, float scale, String texturePath) {
        Tower tower = create(name, x, y, scale);
        tower.texturePath = texturePath;

        return tower;
    }

    public static void addTower(Tower tower){
        tower.loadSprite();
        towers.put(tower.UUID, tower);
    }
}
