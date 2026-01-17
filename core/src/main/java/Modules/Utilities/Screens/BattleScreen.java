package Modules.Utilities.Screens;

import Modules.GUI.LoadoutGUI;
import Modules.Systems.Towers;
import Modules.Systems.Towers.Tower;
import Modules.Tools.SpriteMethods.InteractableSprites;
import Modules.Utilities.UserData;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.UUID;

public class BattleScreen implements Screen {
    private Viewport mainViewport;
    private SpriteBatch spriteBatch;
    private FillViewport viewport;
    private LoadoutGUI loadoutGui;

    private final Loader loader;

    public BattleScreen(Loader loader) {
        this.loader = loader;
    }

    @Override
    public void show(){
        mainViewport = loader.getMainViewport();
        loadoutGui = new LoadoutGUI(mainViewport, UserData.getLoadout(), 5);
        loader.addLoadingTask("Loadout", loadoutGui::load);

        viewport = new FillViewport(mainViewport.getWorldWidth(), mainViewport.getWorldHeight());
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        renderMain();
        loadoutGui.render();
    }

    @Override
    public void resize(int width, int height) {
        loadoutGui.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        loadoutGui.dispose();
    }

    private void renderTowers(){
        HashMap<UUID, Tower> towers = Towers.getTowers();

        for (Tower tower : towers.values()) {
            InteractableSprites.add(tower.getSprite());
            tower.drawSprite(spriteBatch);
        }
    }

    private void renderMain() {
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        renderTowers();

        spriteBatch.end();
    }
}
