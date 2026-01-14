package Modules.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class LoadFont {
    private static AssetManager assetManager;
    private static BitmapFontParameter bmfparams;

    public static void load() {
        assetManager = new AssetManager();



        bmfparams = new BitmapFontParameter();
        bmfparams.minFilter = TextureFilter.MipMapLinearLinear;
        bmfparams.magFilter = TextureFilter.Linear;
        bmfparams.genMipMaps = true;
    }

    public static BitmapFont generateFont(String fontPath, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;

        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        return font;
    }

    public static void bitmapFont(String fontPath) {
        if (!checkAssetManager()){ return; }

        assetManager.load(fontPath, BitmapFont.class, bmfparams);
        assetManager.finishLoading();
    }

    private static boolean checkAssetManager(){
        if (assetManager == null){
            System.out.println("LoadFont has not been loaded!");
            return false;
        }

        return true;
    }
}
