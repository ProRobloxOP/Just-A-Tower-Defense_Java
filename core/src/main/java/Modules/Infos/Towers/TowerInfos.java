package Modules.Infos.Towers;

import Modules.Tools.SpriteMethods;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class TowerInfos {
    public interface TowerInfo {
        Map<String, Float> spriteScales = new HashMap<>();
        HashMap<String, Float> stats = new HashMap<>();
        Sprite sprite = new Sprite();
        String texturePath = "";
        String name = "";

        float x = 0, y = 0, scale = 1;

        Map<String, Float> getSpriteScales();
        HashMap<String, Float> getStats();
        String getTexturePath();
        Sprite getSprite();
        String getName();

        float getX();
        float getY();

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
        SpriteMethods.setPosition(sprite, x, y);

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
        private final Map<String, Float> spriteScales;
        private final HashMap<String, Float> stats;
        private final String name;

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
            spriteScales = Map.ofEntries(
                Map.entry("Loadout", 0.05f),
                Map.entry("Regular", 0.1f)
            );

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
        public float getX() {
            return 0;
        }

        @Override
        public float getY() {
            return 0;
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

        @Override
        public Map<String, Float> getSpriteScales() {
            return spriteScales;
        }

        private void setStats() {
            stats.put("Damage", 5.0f);
            stats.put("Cooldown", 0.3f);
            stats.put("Range", 10.0f);
        }
    }
}
