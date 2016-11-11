package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by b-and on 11/11/2016.
 */

public class Gemas {
    private Sprite sprite;
    private EstadoGema estadoGema;
    private Rectangle rectColision;
    private float tiempoOculto,yinicial;


    Gemas(Texture textura,float x,float y){
        sprite=new Sprite(textura);
        estadoGema=EstadoGema.NUEVA;
        sprite.setPosition(x,y);
        rectColision = new Rectangle(sprite.getX(), sprite.getY(),
                sprite.getWidth(), sprite.getHeight());
        yinicial=sprite.getY();
    }
    public void render(SpriteBatch batch,float x){
        sprite.draw(batch);
        actualizar(x);
    }
    private void actualizar(float xinicial){
        sprite.setX(xinicial);
        float x=sprite.getX();
        float y=sprite.getY();
        rectColision.setPosition(x,y);
        switch (estadoGema){
            case NUEVA:
            case INICIADO:
                tiempoOculto=(float)(Math.random()*5+2);
                estadoGema= EstadoGema.BAJANDO;
                break;
            case BAJANDO:
                tiempoOculto-= Gdx.graphics.getDeltaTime();
                if(tiempoOculto<=0) {
                    if (y >= -sprite.getHeight()) {
                        y -= 2;
                        sprite.setY(y);
                    } else
                        estadoGema = EstadoGema.DESAPARECER;
                }
                break;
            case DESAPARECER:
                sprite.setY(yinicial);
                estadoGema=EstadoGema.NUEVA;
                break;
        }
    }
    public Sprite getSprite(){
        return this.sprite;
    }

    public EstadoGema getEstadoGemas() {
        return estadoGema;
    }
    public Rectangle getRectangle(){return rectColision;}

    public void setEstadoGema(EstadoGema estado) {
        estadoGema=estado;
    }

    public enum EstadoGema{
        INICIADO,NUEVA, BAJANDO, DESAPARECER;
    }
}
