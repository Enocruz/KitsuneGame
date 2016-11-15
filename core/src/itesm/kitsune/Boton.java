package itesm.kitsune;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;

/**
 * Created by b-and on 23/09/2016.
 */
public class Boton implements Disableable{

    private Sprite sprite;
    private Rectangle rectColision;
    private boolean disabled = false;
    public Boton(Texture textura) {
        sprite = new Sprite(textura);
        rectColision = new Rectangle(sprite.getX(), sprite.getY(),
                sprite.getWidth(), sprite.getHeight());
    }

    public void setPosicion(float x, float y) {
        sprite.setPosition(x, y);
        rectColision.setPosition(x,y);
    }

    public Rectangle getRectColision() {
        return rectColision;
    }

    // Dibuja el botón
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }



    public float getX() {
        return sprite.getX();
    }

    public void setAlfa(float alfa) {
        sprite.setAlpha(alfa);
    }

    public boolean contiene(float x, float y) {
        if (disabled == false) {
            return rectColision.contains(x, y);
        }
        return false;
    }

    @Override
    public void setDisabled(boolean isDisabled) {
        this.disabled = isDisabled;

    }

    @Override
    public boolean isDisabled() {
        if (disabled= true){return true;}
        return false;
    }

    public float getY() {
        return sprite.getY();
    }
}
