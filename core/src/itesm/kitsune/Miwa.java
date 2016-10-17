package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by b-and on 23/09/2016.
 */
public class Miwa {

    private Sprite sprite,spriteSalto;
    private TextureRegion[] walkFrames;
    private Animation animacion;
    private float tiempo;
    private Estados estados;
    public static float VELOCIDAD_Y=-4f,VELOCIDAD_X;
    private TextureRegion salto,inicio;
    private float velSalto = 30f;
    private boolean libre =false;


    Miwa(Texture textura){
        TextureRegion texturaCompleta = new TextureRegion(textura);
        TextureRegion[][] texturaMiwa = texturaCompleta.split(textura.getWidth()/10,textura.getHeight());
        walkFrames= new TextureRegion[8];
        for(int i=1;i<9;i++)
            walkFrames[i-1]=texturaMiwa[0][i];
        salto = texturaMiwa[0][9];
        inicio = texturaMiwa[0][0];
        animacion=new Animation(0.09f,walkFrames);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        tiempo=0;
        sprite=new Sprite(texturaMiwa[0][0]);
        spriteSalto=new Sprite(texturaMiwa[0][9]);
        estados=Estados.QUIETOD;
    }
    public void render(SpriteBatch batch){
        float x=sprite.getX();
        float y = sprite.getY();

        miwaMovimiento(x,y,batch);
    }
    public void miwaMovimiento(float x,float y,SpriteBatch batch){
        switch (estados) {
            case DERECHA:
            case IZQUIERDA:
                tiempo += Gdx.graphics.getDeltaTime();
                //sprite.setTexture(animacion.getKeyFrame(tiempo).getTexture());
                TextureRegion region = animacion.getKeyFrame(tiempo);
                //sprite.setTexture(region.getTexture());
                if (estados == Estados.DERECHA) {
                    x += VELOCIDAD_X;
                    if (region.isFlipX()) {
                        region.flip(true, false);
                    }
                    if (x <= Nivel1.ANCHO_MAPA - sprite.getWidth())
                        sprite.setX(x);
                }
                else {
                    x -= VELOCIDAD_X;
                    if (!region.isFlipX()) {
                        region.flip(true, false);
                    }
                    if (x >= 0)
                        sprite.setX(x);
                }
                batch.draw(region, sprite.getX(), sprite.getY());
                break;
            case QUIETOD:
                sprite.setRegion(inicio);
                if(sprite.isFlipX())
                    sprite.flip(true,false);
                sprite.draw(batch);
                break;
            case QUIETOI:
                sprite.setRegion(inicio);
                if (!sprite.isFlipX())
                    sprite.flip(true, false);
                sprite.draw(batch);
                break;
            case SALTOD:
            case SALTOI:
                break;
            case SUBIENDO:
                if (sprite.isFlipX())
                    sprite.flip(true, false);
                sprite.setRegion(salto);
                y += velSalto;
                sprite.setY(y);
                velSalto+=VELOCIDAD_Y;
                System.out.println(velSalto);
                sprite.draw(batch);
                if (velSalto<=0){
                    estados=Estados.BAJANDO;
                }libre=true;
                break;
            case BAJANDO:
                velSalto=30;
                sprite.setRegion(salto);
                y += VELOCIDAD_Y;
                sprite.setY(y);
                sprite.draw(batch);
                if (libre ==false){
                    if(!sprite.isFlipX()){estados=Estados.QUIETOD;}
                    else{estados=Estados.QUIETOI;}
                }libre = true;

                break;
        }
    }
    public Sprite getSprite() {
        return sprite;
    }
    public void setLibre(boolean libre){
        this.libre=libre;
    }
    // Accesores para la posición
    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }


    public void setVelocidadX(float x){
        VELOCIDAD_X=x;
    }
    public void caer(){
        sprite.setRegion(salto);
        sprite.setY(sprite.getY()+VELOCIDAD_Y);
    }
    public Estados getEstados(){
        return this.estados;
    }
    // Modificador del estadoMovimiento
    public void setEstadoMovimiento(Estados estadoMovimiento) {
        this.estados = estadoMovimiento;
    }

    public enum Estados{
        IZQUIERDA,
        DERECHA,
        QUIETOD,
        QUIETOI,
        SUBIENDO,
        BAJANDO,
        SALTOD,
        SALTOI,
    }

}
