package itesm.kitsune;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by b-and on 13/09/2016.
 */
public class Fondo {
    private Sprite sprite;

    public Fondo(Texture texturaFondo) {
        sprite=new Sprite(texturaFondo);
    }
    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
}
