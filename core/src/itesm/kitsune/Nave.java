package itesm.kitsune;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by b-and on 28/10/2016.
 */

public class Nave {
    private int vidas=3;
    private Sprite sprite;
    private Texture texturaDerecha,texturaIzquierda,texturaCentro;
    private MOVIMIENTO movimiento=MOVIMIENTO.QUIETO;
    private Rectangle rectColision;

    Nave(Texture texturaCentro, Texture texturaDerecha, Texture texturaIzquierda){
        sprite=new Sprite(texturaCentro);
        this.texturaCentro=texturaCentro;
        this.texturaIzquierda=texturaIzquierda;
        this.texturaDerecha=texturaDerecha;
        rectColision = new Rectangle(sprite.getX(), sprite.getY(),
                sprite.getWidth(), sprite.getHeight());
    }
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
    public void render(SpriteBatch batch) {
        float x=sprite.getX();
        float y=sprite.getY();
        rectColision.setPosition(x,y);
        switch (movimiento) {
            case DERECHA:
            case IZQUIERDA:
                if(movimiento==MOVIMIENTO.DERECHA) {
                    x += 10;
                    if (x <= NivelPersecucion.ANCHO-sprite.getWidth())
                        sprite.setX(x);
                    sprite.setTexture(texturaDerecha);
                }
                 else if(movimiento==MOVIMIENTO.IZQUIERDA){
                    x -= 10;
                    if (x >=0)
                        sprite.setX(x);
                    sprite.setTexture(texturaIzquierda);
                }
                batch.draw(sprite, sprite.getX(), sprite.getY());
                break;
            case QUIETO:
                sprite.setTexture(texturaCentro);
                batch.draw(sprite,sprite.getX(),sprite.getY());
        }

    }
    public void setMovimiento(MOVIMIENTO movimiento){
        this.movimiento=movimiento;
    }

    public int getVidas() {
        return this.vidas;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }
    public boolean contiene(float x, float y) {
        return rectColision.contains(x, y);
    }

    public enum MOVIMIENTO{
        DERECHA,
        IZQUIERDA,
        QUIETO
    }
}
