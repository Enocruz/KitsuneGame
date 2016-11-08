package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by b-and on 07/11/2016.
 */

public class Piedra {
    private Sprite sprite;
    private EstadosPiedra estadosPiedra;
    private Rectangle rectColision;
    private float yinicial;
    private float tiempoOculto;

    Piedra(Texture textura){
        sprite=new Sprite(textura);
        rectColision = new Rectangle(sprite.getX(), sprite.getY(),
                sprite.getWidth(), sprite.getHeight());
        estadosPiedra=EstadosPiedra.INICIADO;
    }
    Piedra (Texture textura,float x,float y){
        this(textura);
        sprite.setPosition(x,y);
        yinicial=sprite.getY();
    }
    public void render(SpriteBatch batch, int vel, int rot) {
        sprite.draw(batch);
        actualizar(vel,rot);
    }
    private void actualizar(int vel, int rot){
        float x=sprite.getX();
        float y=sprite.getY();
        rectColision.setPosition(x,y);
        switch(estadosPiedra) {
            case INICIADO:
            case NUEVA:
                tiempoOculto=(float)(Math.random()*5+3);
                estadosPiedra=EstadosPiedra.BAJANDO;
                break;
            case BAJANDO:
                tiempoOculto-=Gdx.graphics.getDeltaTime();
                if(tiempoOculto<=0) {
                    if (y >= -sprite.getHeight()) {
                        y -= vel;
                        sprite.setY(y);
                        sprite.setRotation(sprite.getRotation() + rot);
                    } else
                        estadosPiedra = EstadosPiedra.DESAPARECER;
                }
                break;
            case DESAPARECER:
                sprite.setY(yinicial);
                estadosPiedra=EstadosPiedra.NUEVA;
                break;
        }

    }
    public Sprite getSprite(){
        return  this.sprite;
    }
    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public Rectangle getRectangle(){return rectColision;}

    public void setEstado(EstadosPiedra estado) {
        this.estadosPiedra = estado;
    }

    public Object getEstadosPiedra() {
        return estadosPiedra;
    }

    public enum EstadosPiedra{
        NUEVA,BAJANDO,DESAPARECER,INICIADO
    }

}
