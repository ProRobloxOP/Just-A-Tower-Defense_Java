package Modules.Utilities.Screens;

import Modules.GUI.LoadoutGUI;
import Modules.Tools.Tuples.Quartet;
import Modules.Utilities.UserData;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.HashSet;

public class BattleScreen implements Screen {
    private Quartet<FitViewport, SpriteBatch, BitmapFont[], HashSet<Sprite[]>> loadoutConfig;
    private LoadoutGUI loadoutGui;

    private final Loader loader;

    public BattleScreen(Loader loader) {
        this.loader = loader;
    }

    @Override
    public void show() {
        loadoutGui = new LoadoutGUI(loader.getMainViewport(), UserData.getLoadout(), 5);
        loader.addLoadingTask("Loadout", loadoutGui::load);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);

        loadoutGui.render();
    }

    @Override
    public void resize(int width, int height) {
        loadoutGui.resize(width, height);
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
        loadoutGui.dispose();
    }
}
