package Modules.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SpriteMethods {
    public static void onClicked(Sprite sprite, Runnable runnable) {
        Vector2 mouseVector = new Vector2();
        mouseVector.set(Gdx.input.getX(), Gdx.input.getY());

        if (sprite.getBoundingRectangle().contains(mouseVector)) {
            System.out.println("E");
            runnable.run();
        }
    }
}
