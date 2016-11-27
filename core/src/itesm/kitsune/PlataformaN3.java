package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Leslie on 06/11/2016.
 */

public class PlataformaN3 {

    private Sprite sprite;
    private float xInicial, tiempo;
    public estadosP estado;
    private Rectangle colision;

    public PlataformaN3(Texture textura, float x, float y) {
        sprite = new Sprite(textura);
        sprite.setPosition(x, y);
        colision = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getRegionHeight());
        estado = estadosP.NUEVA;
        xInicial = sprite.getX();
    }
    private void actualizar(float vel){
        float x= sprite.getX();
        float y = sprite.getY();
        colision.setPosition(x,y);
        switch (estado){
            case NUEVA:
                tiempo = (float)(Math.random()*2);
                estado = estadosP.DENTRO;
                break;
            case DENTRO:
                tiempo -= Gdx.graphics.getDeltaTime();
                if (tiempo<=0){
                    if (x >= sprite.getWidth()/2){
                        x+=vel;
                        sprite.setX(x);
                    }else{
                        estado=estadosP.FUERA;
                    }
                }
                break;
            case FUERA:
                sprite.setX(xInicial);
                estado=estadosP.NUEVA;

        }
    }

    public void render(SpriteBatch batch,float vel){
        sprite.draw(batch);
        actualizar(vel);
    }

    public float getX(){
        return sprite.getX();
    }

    public float getY(){
        return sprite.getY();
    }
    public void setEstado (estadosP estado) {this.estado = estado;}
    public void setTiempo(float tiempo) {this.tiempo = tiempo;}
    public Sprite getSprite (){return sprite;}
    public void setPosicion(float x,float y){this.getSprite().setPosition(x,y);}
    public Rectangle getRectangle(){return colision;}

    enum estadosP{
        DENTRO,
        FUERA,
        NUEVA
    }
}
