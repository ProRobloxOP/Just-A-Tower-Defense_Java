package Modules.GUI;

import Modules.Infos.Towers.TowerInfos;
import Modules.Systems.Towers;
import Modules.Systems.Towers.Tower;
import Modules.Tools.LoadFont;
import Modules.Tools.SpriteMethods;
import Modules.Tools.SpriteMethods.InteractableSprites;
import Modules.Tools.Tuples.Pair;
import Modules.Utilities.Screens.Loader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class LoadoutGUI {
    private HashMap<Sprite, Tower> towerLoadouts;
    private FillViewport loadoutViewport;
    private SpriteBatch loadoutBatch;
    private Vector<Sprite[]> loadoutSprites;

    private Vector<BitmapFont> hotkeyFonts;
    private Vector<Pair<String, Tower>> loadout;
    private int maxTowers;

    private Sprite selectedTowerSprite;
    private SpriteBatch selectedTowerBatch;
    private Tower selectedTower;

    private Loader mainLoader;
    private Viewport mainViewport;

    public LoadoutGUI(Loader mainloader, Vector<Pair<String, Tower>> loadout, Integer maxTowers){
        if (maxTowers == null) { maxTowers = loadout.size(); }

        this.loadout = loadout;
        this.maxTowers = maxTowers;
        this.mainLoader = mainloader;
        this.mainViewport = mainloader.getMainViewport();
    }

    public void load() {
        loadoutViewport = new FillViewport(mainViewport.getWorldWidth(), mainViewport.getWorldHeight());
        selectedTowerBatch = new SpriteBatch();
        loadoutBatch = new SpriteBatch();
        loadoutSprites = new Vector<>();
        towerLoadouts = new HashMap<>();
        hotkeyFonts = new Vector<>();

        mainLoader.addLoadingTask("LoadoutGUI", () -> {
            for (int i = 0; i < maxTowers; i++){
                Tower tower = (i < loadout.size())? loadout.get(i).second : null;
                Sprite[] towerGUI = loadTowerGUI(tower);
                loadoutSprites.add(towerGUI);

                loadHotkeyFont();
            }
        });
    }

    public void render() {
        placeSelectedTower();
        checkSelect();
        renderLoadout();
    }

    public void resize(int screenWidth, int screenHeight) {
        loadoutViewport.update(screenWidth, screenHeight, true);
    }

    public void dispose(){
        selectedTowerBatch.dispose();
        loadoutBatch.dispose();
        disposeFonts();
    }

    private void disposeFonts() {
        for (BitmapFont font : hotkeyFonts) {
            font.dispose();
        }
    }

    private void resetSelectedTower(){
        selectedTower = null;
        selectedTowerSprite = null;
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

    private Sprite[] loadTowerGUI(Tower tower){
        Texture loadoutTexture = new Texture("Images/GUI/Loadout.png");
        Sprite loadoutBackground = new Sprite(loadoutTexture);
        Sprite[] towerGUI = new Sprite[2];
        float towerX, towerY;

        int loadoutNum = loadoutSprites.size();
        float scale = 0.55f;
        float width = loadoutTexture.getWidth()*scale;

        loadoutBackground.setSize(width, loadoutTexture.getHeight()*scale);
        loadoutBackground.setPosition( mainViewport.getWorldWidth() / 4.5f + width*loadoutNum*1.5f, 30);

        towerX = loadoutBackground.getX() + loadoutBackground.getWidth()/2;
        towerY = loadoutBackground.getY() + loadoutBackground.getHeight()/2;

        towerGUI[0] = loadoutBackground;
        towerGUI[1] = loadTowerSprite(tower, towerX, towerY);

        InteractableSprites.add(loadoutBackground);

        towerLoadouts.put(loadoutBackground, tower);

        return towerGUI;
    }

    private void placeSelectedTower(){
        if (!Gdx.input.isTouched() || selectedTower == null) { return; }
        if (InteractableSprites.hoverOnInteractable(selectedTowerSprite)) { return; }
        Tower tower;
        float scale = selectedTower.getSpriteScales().get("Regular");

        tower = Towers.create(selectedTower.getName(), selectedTower.getX(), selectedTower.getY(), scale, selectedTower.getTexturePath());
        Towers.addTower(tower);

        resetSelectedTower();
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

    private void renderSelectedTower() {
        if (selectedTower == null) { return; }
        Vector3 cameraVector = loadoutViewport.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        float selectedTowerX, selectedTowerY, selectedTowerWidth, selectedTowerHeight;

        selectedTowerBatch.setProjectionMatrix(loadoutViewport.getCamera().combined);
        selectedTowerBatch.begin();
        selectedTowerBatch.setColor(1, 1, 1, 0.25f);

        SpriteMethods.setPosition(selectedTowerSprite, cameraVector.x, cameraVector.y);
        selectedTower.setPosition(SpriteMethods.getX(selectedTowerSprite), SpriteMethods.getY(selectedTowerSprite));
        selectedTowerX = selectedTowerSprite.getX();
        selectedTowerY = selectedTowerSprite.getY();
        selectedTowerWidth = selectedTowerSprite.getWidth();
        selectedTowerHeight = selectedTowerSprite.getHeight();

        selectedTowerBatch.draw(selectedTowerSprite, selectedTowerX, selectedTowerY, selectedTowerWidth, selectedTowerHeight);

        selectedTowerBatch.end();
        selectedTowerBatch.setColor(1, 1, 1, 1);
    }

    private void renderLoadout(){
        loadoutViewport.apply();
        renderSelectedTower();

        loadoutBatch.setProjectionMatrix(loadoutViewport.getCamera().combined);
        loadoutBatch.enableBlending();
        loadoutBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        loadoutBatch.begin();

        for (int loadoutNum = 0; loadoutNum < loadoutSprites.size(); loadoutNum++) {
            Pair<String, Tower> hotkey = (loadoutNum < loadout.size())? loadout.get(loadoutNum) : new Pair<>("", null);
            renderTowerGUI(loadoutNum, hotkey.first);
        }

        loadoutBatch.end();
    }

    private void checkSelect(){
        for (Sprite[] loadoutSprite : loadoutSprites) {
            Sprite loadoutBackground = loadoutSprite[0];
            float clickedScale = 1.1f, hoverScale = 0.95f;

            SpriteMethods.onClicked(loadoutBackground, loadoutViewport.getCamera(), () -> {
                for (Sprite sprite : loadoutSprite) {
                    float scale;

                    if (sprite == null || towerLoadouts.get(sprite) == null) {
                        return;
                    }

                    SpriteMethods.scaleInterpolate(sprite, clickedScale, 1.3f);
                    selectedTower = towerLoadouts.get(sprite);
                    scale = selectedTower.getSpriteScales().get("Regular");

                    selectedTowerSprite = TowerInfos.loadTowerSprite(selectedTower.getTexturePath(), Gdx.input.getX(), Gdx.input.getY(), scale);
                }
            });

            SpriteMethods.onHover(loadoutBackground, loadoutViewport.getCamera(), () -> {
                for (Sprite sprite : loadoutSprite) {
                    if (sprite == null) { return; }
                    SpriteMethods.scaleInterpolate(sprite, hoverScale, 1.3f);
                }
            });
        }
    }
}
