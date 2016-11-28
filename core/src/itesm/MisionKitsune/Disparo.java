package itesm.MisionKitsune;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Leslie on 15/11/2016.
 */

public class Disparo {
    private Sprite sprite;
    public estadoD estado = estadoD.ADENTRO;
    public Rectangle colision;
    float vel = 10;

    public Disparo(Texture textura,int x, int y) {
        sprite = new Sprite(textura);
        sprite.setPosition(x,y);
        colision = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void render(SpriteBatch batch) {
        sprite.setX(sprite.getX()-vel);
        colision.setPosition(sprite.getX(),sprite.getY());
        actualizar();
        sprite.draw(batch);
    }

    private void actualizar(){
        if (sprite.getX() < 0){
            estado = estadoD.AFUERA;
        }else estado = estadoD.ADENTRO;
    }
    public Sprite getSprite(){return sprite;}

    public enum estadoD {
        ADENTRO,
        AFUERA
    }
}
