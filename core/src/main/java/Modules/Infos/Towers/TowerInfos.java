package Modules.Infos.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class TowerInfos {
    public interface TowerInfo {
        HashMap<String, Float> stats = new HashMap<>();
        Sprite sprite = new Sprite();
        String texturePath = "";
        String name = "";

        float x = 0, y = 0, scale = 1;

        String getTexturePath();
        String getName();
        HashMap<String, Float> getStats();
        Sprite getSprite();

        Runnable loadSprite();
        Runnable drawSprite(Batch batch);
    }

    public static TowerInfo get(String name, float x, float y, float scale){
        Map<String, TowerInfo> towerClasses = Map.ofEntries(
                Map.entry("Test", new Test(x, y, scale))
        );

        return towerClasses.get(name);
    }

    public static Sprite loadTowerSprite(String texturePath, float x, float y, float scale){
        Texture texture = new Texture(texturePath);
        Sprite sprite = new Sprite(texture);

        sprite.setSize(texture.getWidth()*scale, texture.getHeight()*scale);
        sprite.setCenter(x, y);
        sprite.setOriginCenter();

        return sprite;
    }

    public static boolean drawTowerSprite(String name, Sprite sprite, Batch batch, boolean notLoaded) {
        if (sprite == null) {
            if (notLoaded) {
                return false;
            }

            System.out.println("Tower " + name + " sprite has not been loaded!");
            return true;
        }
        sprite.draw(batch);

        return false;
    }

    // Tower Info Classes (All Tower Infos)
    private final static class Test implements TowerInfo {
        private final String name;
        private final HashMap<String, Float> stats;

        private final float x, y, scale;

        private final String texturePath = "Images/Towers/Cowboy_Tower.png";
        private Sprite sprite;
        private boolean notLoaded;

        public Test(float x, float y, float scale){
            name = "Test";
            this.x = x;
            this.y = y;
            this.scale = scale;

            stats = new HashMap<>();
            setStats();
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
        public HashMap<String, Float> getStats() {
            return stats;
        }

        @Override
        public Sprite getSprite() {
            return sprite;
        }

        @Override
        public Runnable loadSprite() {
            sprite = loadTowerSprite(texturePath, x, y, scale);
            return null;
        }

        @Override
        public Runnable drawSprite(Batch batch) {
            notLoaded = drawTowerSprite(name, sprite, batch, notLoaded);
            return null;
        }

        private void setStats() {
            stats.put("Damage", 5.0f);
            stats.put("Cooldown", 0.3f);
            stats.put("Range", 10.0f);
        }
    }
}
