package itesm.kitsune;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by b-and on 11/11/2016.
 */

public class Hoyo {
    private Sprite sprite;
    private Texture texture;
    private Rectangle rectangle;
    private EstadosHoyo estadosHoyo;
    Hoyo(Texture texture){
        sprite=new Sprite(texture);
        rectangle=new Rectangle(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        estadosHoyo=EstadosHoyo.NUEVO;
    }
    public void render(SpriteBatch batch){
        sprite.draw(batch);
        actualizar();
    }
    private void actualizar(){

    }
    public Rectangle getRectangle(){
        return rectangle;
    }
    public Sprite getSprite(){
        return sprite;
    }
    public enum EstadosHoyo{
        NUEVO,BAJANDO,DESAPARECER;

    }
}
