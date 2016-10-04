package itesm.kitsune;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by b-and on 23/09/2016.
 */
public class Boton {

    private Sprite sprite;
    private Rectangle rectColision;
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

    public float getY() {
        return sprite.getY();
    }

    public float getX() {
        return sprite.getX();
    }

    public void setAlfa(float alfa) {
        sprite.setAlpha(alfa);
    }

    public boolean contiene(float x, float y) {
        return rectColision.contains(x,y);
    }

}
