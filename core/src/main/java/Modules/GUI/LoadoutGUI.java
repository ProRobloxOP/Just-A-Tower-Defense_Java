package Modules.GUI;

import Modules.Systems.Towers.Tower;
import Modules.Tools.LoadFont;
import Modules.Tools.Tuples.Pair;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
            Sprite[] towerGUI = loadTowerGUI();
            loadoutSprites.add(towerGUI);

            loadHotkeyFont();
        }
    }

    public void render() {
        loadoutViewport.apply();
        loadoutBatch.setProjectionMatrix(loadoutViewport.getCamera().combined);
        loadoutBatch.enableBlending();
        loadoutBatch.begin();

        for (int loadoutNum = 0; loadoutNum < loadoutSprites.size(); loadoutNum++) {
            Pair<Float, Float> fontPos = renderLoadout(loadoutNum);
            Pair<String, Tower> hotkey;
            if (loadoutNum >= loadout.size()) { continue; }

            hotkey = loadout.get(loadoutNum);
            if (hotkey == null) {
                continue;
            }
            renderHotkey(loadoutNum, hotkey.first, fontPos.first, fontPos.second);
        }

        loadoutBatch.end();
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
        BitmapFont hotkeyFont = LoadFont.generateFont("ThaleahFat.ttf", 20);
        hotkeyFont.setColor(Color.BLACK);

        hotkeyFonts.add(hotkeyFont);
    }

    private void loadTowerSprite(Tower tower) {

    }

    private void loadTowerSprites() {

    }

    private Sprite[] loadTowerGUI(){
        Texture loadoutTexture = new Texture("Images/GUI/Loadout.png");
        Sprite loadoutBackground = new Sprite(loadoutTexture);
        Sprite[] towerGUI = new Sprite[3];

        int loadoutNum = loadoutSprites.size();
        float scale = 0.55f;
        float width = loadoutTexture.getWidth()*scale;

        loadoutBackground.setSize(width, loadoutTexture.getHeight()*scale);
        loadoutBackground.setCenter( mainViewport.getWorldWidth() / 4f + width*loadoutNum*1.5f, 70);
        loadoutBackground.setOriginCenter();

        towerGUI[0] = loadoutBackground;

        return towerGUI;
    }

    private Pair<Float, Float> renderLoadout(int loadoutNum) {
        Sprite[] loadoutGUI = loadoutSprites.elementAt(loadoutNum);
        Sprite backgroundGUI = loadoutGUI[0];

        float fontXPos = backgroundGUI.getOriginX();
        float fontYPos = backgroundGUI.getOriginY();

        for (Sprite sprite : loadoutGUI) {
            if (sprite == null) { break; }
            sprite.draw(loadoutBatch);
        }

        return new Pair<>(fontXPos, fontYPos);
    }

    private void renderHotkey(int loadoutNum, String hotkey, float x, float y){
        if (hotkey == null) { return; }
        BitmapFont hotkeyFont = hotkeyFonts.get(loadoutNum);
        hotkeyFont.draw(loadoutBatch, hotkey, x, y);
    }
}
