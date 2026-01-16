package Modules.GUI;

import Modules.Infos.Towers.TowerInfos;
import Modules.Systems.Towers;
import Modules.Systems.Towers.Tower;
import Modules.Tools.LoadFont;
import Modules.Tools.SpriteMethods;
import Modules.Tools.Tuples.Pair;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class LoadoutGUI {
    private HashMap<String, HashMap<String, Sprite>> towerSprites;
    private FitViewport loadoutViewport;
    private SpriteBatch loadoutBatch;
    private Vector<Sprite[]> loadoutSprites;

    private Vector<BitmapFont> hotkeyFonts;
    private Vector<Pair<String, Tower>> loadout;
    private int maxTowers;

    private Viewport mainViewport;

    public LoadoutGUI(Viewport mainViewport, Vector<Pair<String, Tower>> loadout, Integer maxTowers){
        if (maxTowers == null) { maxTowers = loadout.size(); }

        this.loadout = loadout;
        this.maxTowers = maxTowers;
        this.mainViewport = mainViewport;
    }

    public void load() {
        loadoutViewport = new FitViewport(mainViewport.getWorldWidth(), mainViewport.getWorldHeight());
        loadoutBatch = new SpriteBatch();
        loadoutSprites = new Vector<>();
        hotkeyFonts = new Vector<>();

        for (int i = 0; i < maxTowers; i++){
            Pair<String, Tower> hotkey = (i < loadout.size())? loadout.get(i) : new Pair<>("", null);
            Sprite[] towerGUI = loadTowerGUI(hotkey);
            loadoutSprites.add(towerGUI);

            loadHotkeyFont();
        }
    }

    public void render() {
        checkClickDetection();
        renderLoadout();
    }

    public void resize(int screenWidth, int screenHeight) {
        loadoutViewport.update(screenWidth, screenHeight, true);
    }

    public void dispose(){
        loadoutBatch.dispose();
        disposeFonts();
    }

    private void disposeFonts() {
        for (BitmapFont font : hotkeyFonts) {
            font.dispose();
        }
    }

    private void loadHotkeyFont() {
        BitmapFont hotkeyFont = LoadFont.generateFont("ThaleahFat.ttf", 40);
        hotkeyFont.setColor(Color.BLACK);

        hotkeyFonts.add(hotkeyFont);
    }

    private Sprite loadTowerSprite(Tower tower, float x, float y) {
        if (tower == null || tower.getSpriteScales().get("Loadout") == null) { return null; }
        return TowerInfos.loadTowerSprite(tower.getTexturePath(), x, y, tower.getSpriteScales().get("Loadout"));
    }

    private Sprite[] loadTowerGUI(Pair<String, Tower> hotkey){
        Texture loadoutTexture = new Texture("Images/GUI/Loadout.png");
        Sprite loadoutBackground = new Sprite(loadoutTexture);
        Sprite[] towerGUI = new Sprite[2];
        float towerX, towerY;

        int loadoutNum = loadoutSprites.size();
        float scale = 0.55f;
        float width = loadoutTexture.getWidth()*scale;

        loadoutBackground.setSize(width, loadoutTexture.getHeight()*scale);
        loadoutBackground.setCenter( mainViewport.getWorldWidth() / 4f + width*loadoutNum*1.5f, 70);
        loadoutBackground.setOriginCenter();

        towerX = loadoutBackground.getX() + loadoutBackground.getWidth()/2;
        towerY = loadoutBackground.getY() + loadoutBackground.getHeight()/2;

        towerGUI[0] = loadoutBackground;
        towerGUI[1] = loadTowerSprite(hotkey.second, towerX, towerY);

        return towerGUI;
    }

    private void renderTowerGUI(int loadoutNum, String hotkey) {
        Sprite[] loadoutGUI = loadoutSprites.elementAt(loadoutNum);
        Sprite backgroundGUI = loadoutGUI[0];

        float fontXPos = backgroundGUI.getX() + backgroundGUI.getWidth() / 1.25f;
        float fontYPos = backgroundGUI.getY() + backgroundGUI.getHeight() / 5.5f;

        for (Sprite sprite : loadoutGUI) {
            if (sprite == null) { break; }
            sprite.draw(loadoutBatch);
        }

        renderHotkey(loadoutNum, hotkey, fontXPos, fontYPos);
    }

    private void renderHotkey(int loadoutNum, String hotkey, float x, float y){
        if (hotkey == null) { return; }
        BitmapFont hotkeyFont = hotkeyFonts.get(loadoutNum);
        hotkeyFont.draw(loadoutBatch, hotkey, x, y);
    }

    private void renderLoadout(){
        loadoutViewport.apply();
        loadoutBatch.setProjectionMatrix(loadoutViewport.getCamera().combined);
        loadoutBatch.enableBlending();
        loadoutBatch.begin();

        for (int loadoutNum = 0; loadoutNum < loadoutSprites.size(); loadoutNum++) {
            Pair<String, Tower> hotkey = (loadoutNum < loadout.size())? loadout.get(loadoutNum) : new Pair<>("", null);
            renderTowerGUI(loadoutNum, hotkey.first);
        }

        loadoutBatch.end();
    }

    private void checkClickDetection(){
        for (Sprite[] loadoutSprite : loadoutSprites) {
            Sprite loadoutBackground = loadoutSprite[0];
            SpriteMethods.onClicked(loadoutBackground, () -> {
                System.out.println("YESS");
            });
        }
    }
}
