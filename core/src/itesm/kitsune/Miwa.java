package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by b-and on 23/09/2016.
 */
public class Miwa {
    private Sprite sprite;
    private TextureRegion[] walkFrames;
    private Animation animacion;
    private float tiempo,tiempoSalto;
    private Estados estados;
    private EstadosSalto estadoSalto= EstadosSalto.EN_PISO;
    public static float VELOCIDAD_Y = -3f, VELOCIDAD_X=7;
    private TextureRegion salto, inicio;
    private Texture texturaDisparo;
    private float velSalto = 195f;
    private int vidas = 3;
    private boolean right;
    private Rectangle colision;
    public boolean enpiso;


    Miwa(Texture textura) {
        TextureRegion texturaCompleta = new TextureRegion(textura);
        TextureRegion[][] texturaMiwa = texturaCompleta.split(textura.getWidth() / 10, textura.getHeight());
        walkFrames = new TextureRegion[8];
        for (int i = 1; i < 9; i++)
            walkFrames[i - 1] = texturaMiwa[0][i];
        salto = texturaMiwa[0][9];
        inicio = texturaMiwa[0][0];
        animacion = new Animation(0.09f, walkFrames);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        tiempo = 0;
        sprite = new Sprite(texturaMiwa[0][0]);
        estados = Estados.QUIETO;
        right=true;
        colision = new Rectangle(sprite.getX(),sprite.getY(),sprite.getX()+sprite.getWidth(),sprite.getY()+sprite.getHeight());
        enpiso=false;
    }

    public void render(SpriteBatch batch) {
        float x = sprite.getX();
        float y = sprite.getY();
        colision.setPosition(x,y);
        miwaSalto(y);
        miwaMovimiento(x,estadoSalto);
        sprite.draw(batch);
    }

    public void miwaMovimiento(float x,EstadosSalto estadosSalto) {
        switch (estados) {
            case DERECHA:
            case IZQUIERDA:
                tiempo += Gdx.graphics.getDeltaTime();
                sprite.setRegion(animacion.getKeyFrame(tiempo));
                if (estadosSalto != EstadosSalto.EN_PISO)
                    sprite.setRegion(salto);
                if (estados == Estados.DERECHA) {
                    right=true;
                    x += VELOCIDAD_X;
                    if (sprite.isFlipX()) {
                        sprite.flip(true, false);
                    }
                    if (x <= NivelBusqueda.ANCHO_MAPA - sprite.getWidth())
                        sprite.setX(x);
                } else {
                    right=false;
                    x -= VELOCIDAD_X;
                    if (!sprite.isFlipX()) {
                        sprite.flip(true, false);
                    }
                    if (x >= 0)
                        sprite.setX(x);
                }
                break;
            case QUIETO:
                if (estadosSalto!=EstadosSalto.EN_PISO)
                    sprite.setRegion(salto);
                else{
                    sprite.setRegion(inicio);
                }
                if(right==false)
                    if(!sprite.isFlipX())
                        sprite.flip(true, false);
                break;
            case DISPARANDO:
                x += VELOCIDAD_X;
                sprite.setX(x);
                break;
            case N3:
                if (sprite.getX() <  NivelBusqueda.ANCHO-sprite.getWidth()){
                    VELOCIDAD_X =4;
                }else if (sprite.getX()+sprite.getWidth() >  NivelBusqueda.ANCHO){
                    VELOCIDAD_X=-1;
                }else VELOCIDAD_X=0;
                sprite.setX(sprite.getX()+VELOCIDAD_X);
                tiempo += Gdx.graphics.getDeltaTime();
                sprite.setRegion(animacion.getKeyFrame(tiempo));
                break;
        }
    }
    public void miwaSalto(float y){
        switch (estadoSalto){
            case EN_PISO:
                velSalto=64;
                tiempoSalto = 0;
                break;
            case SUBIENDO:
                sprite.setRegion(salto);
                y += velSalto*0.2f;
                velSalto+=VELOCIDAD_Y;
                if (velSalto<=0){
                    estadoSalto=EstadosSalto.BAJANDO;
                }
                sprite.setY(y);
                break;
            case BAJANDO:
                sprite.setRegion(salto);
                sprite.setY(sprite.getY() + 9.81f*VELOCIDAD_Y*0.3f);
                break;
        }
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
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

    public void setVelocidadX(float x) {
        VELOCIDAD_X = x;
    }
    public Rectangle getRectangle(){
        return this.colision;
    }

    public EstadosSalto getEstadosSalto(){ return this.estadoSalto;}

    public Estados getEstadoMovimiento (){ return this.estados;}
    // Modificador del estadoMovimiento
    public void setEstadoMovimiento(Estados estadoMovimiento) {
        this.estados = estadoMovimiento;
    }
    public void setEstadoSalto (EstadosSalto estadoSalto){ this.estadoSalto = estadoSalto; }

    public enum Estados {
        IZQUIERDA,
        DERECHA,
        QUIETO,
        DISPARANDO,
        N3
    }

    public enum EstadosSalto {
        EN_PISO,
        SUBIENDO,
        BAJANDO
    }
}