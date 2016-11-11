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
    static class NaveEnemiga extends Nave{
        NaveEnemiga(Texture texturaC,Texture texturaD,Texture texturaI){
            super(texturaC,texturaD,texturaI);
        }
        public void render(SpriteBatch batch) {
            float x=super.getSprite().getX();
            float y=super.getSprite().getY();
            super.getRectangle().setPosition(x,y);
            switch (super.movimiento) {
                case DERECHA:
                case IZQUIERDA:
                    batch.draw(super.sprite, super.sprite.getX(), super.sprite.getY());
                    break;
                case QUIETO:
                    //sprite.setTexture(texturaCentro);
                    batch.draw(super.sprite,super.sprite.getX(),super.sprite.getY());
            }

        }
    }

    static class NaveMiwa extends Nave{
        NaveMiwa(Texture texturaC,Texture texturaD,Texture texturaI){
            super(texturaC,texturaD,texturaI);
        }
        public void render(SpriteBatch batch) {
            float x=super.getSprite().getX();
            float y=super.getSprite().getY();
            super.getRectangle().setPosition(x,y);
            switch (super.movimiento) {
                case DERECHA:
                case IZQUIERDA:
                    if(super.movimiento==MOVIMIENTO.DERECHA) {
                        x += 10;
                        if (x <= NivelPersecucion.ANCHO-super.sprite.getWidth())
                            super.sprite.setX(x);
                        super.sprite.setTexture(super.texturaDerecha);
                    }
                    else if(super.movimiento==MOVIMIENTO.IZQUIERDA){
                        x -= 10;
                        if (x >=0)
                            super.sprite.setX(x);
                        super.sprite.setTexture(super.texturaIzquierda);
                    }
                    batch.draw(super.sprite, super.sprite.getX(), super.sprite.getY());
                    break;
                case QUIETO:
                    super.sprite.setTexture(super.texturaCentro);
                    batch.draw(super.sprite,super.sprite.getX(),super.sprite.getY());
            }
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

    public Rectangle getRectangle(){return rectColision;}

    public enum MOVIMIENTO{
        DERECHA,
        IZQUIERDA,
        QUIETO
    }
}
