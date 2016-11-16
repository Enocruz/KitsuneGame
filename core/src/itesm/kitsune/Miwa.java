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
    private Sprite sprite;
    private TextureRegion[] walkFrames;
    private Animation animacion;
    private float tiempo,tiempoSalto;
    private Estados estados;
    private EstadosSalto estadoSalto= EstadosSalto.EN_PISO;
    public static float VELOCIDAD_Y = -3f, VELOCIDAD_X=7;
    private TextureRegion salto, inicio;
    private float velSalto = 192f;
    private int vidas = 3;
    private boolean right;
    private float yInicial;

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
    }

    public void render(SpriteBatch batch) {
        float x = sprite.getX();
        float y = sprite.getY();
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
        }
    }
    public void miwaSalto(float y){
        switch (estadoSalto){
            case EN_PISO:
                velSalto=64;
                tiempoSalto = 0;
                yInicial = sprite.getY();
                break;
            case SUBIENDO:
                sprite.setRegion(salto);
                y += velSalto*0.2f;
                System.out.println(velSalto);
                velSalto+=VELOCIDAD_Y;
                if (velSalto<=0){
                    estadoSalto=EstadosSalto.BAJANDO;
                }
                sprite.setY(y);
                break;
            case BAJANDO:
                sprite.setRegion(salto);
                sprite.setY(sprite.getY() + 2*VELOCIDAD_Y);
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


    public EstadosSalto getEstadosSalto(){ return this.estadoSalto;}

    // Modificador del estadoMovimiento
    public void setEstadoMovimiento(Estados estadoMovimiento) {
        this.estados = estadoMovimiento;
    }
    public void setEstadoSalto (EstadosSalto estadoSalto){ this.estadoSalto = estadoSalto; }

    public enum Estados {
        IZQUIERDA,
        DERECHA,
        QUIETO,
    }

    public enum EstadosSalto {
        EN_PISO,
        SUBIENDO,
        BAJANDO,
        CAIDA_LIBRE
    }
}