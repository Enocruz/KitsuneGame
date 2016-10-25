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
    private float tiempo, tiempoSalto, tiempoVuelo;
    private Estados estados;
    private EstadosSalto estadoSalto= EstadosSalto.EN_PISO;
    public static float VELOCIDAD_Y = -3f, VELOCIDAD_X=7;
    private TextureRegion salto, inicio;
    private float velSalto = 192f;
    private int vidas = 3;
    private boolean libre = false;
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
        estados = Estados.QUIETOD;
    }

    public void render(SpriteBatch batch) {
        float x = sprite.getX();
        float y = sprite.getY();
        miwaSalto(x,y,batch);
        miwaMovimiento(x, y, batch, estadoSalto);
        System.out.println(estados +"y"+estadoSalto);

    }

    public void miwaMovimiento(float x, float y, SpriteBatch batch,EstadosSalto estadosSalto) {
        switch (estados) {
            case DERECHA:
            case IZQUIERDA:
                tiempo += Gdx.graphics.getDeltaTime();
                TextureRegion region = animacion.getKeyFrame(tiempo);
                if (estadosSalto != EstadosSalto.EN_PISO)
                    sprite.setRegion(salto);
                if (estados == Estados.DERECHA) {
                    x += VELOCIDAD_X;
                    if (region.isFlipX()) {
                        region.flip(true, false);
                    }
                    if (x <= NivelBusqueda.ANCHO_MAPA - sprite.getWidth())
                        sprite.setX(x);
                } else {
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
                if (estadosSalto==EstadosSalto.EN_PISO)
                    sprite.setRegion(inicio);
                if (sprite.isFlipX())
                    sprite.flip(true, false);
                batch.draw(sprite , sprite.getX(), sprite.getY());
                break;
            case QUIETOI:
                if (estadosSalto==EstadosSalto.EN_PISO){
                    sprite.setRegion(inicio);}
                if (!sprite.isFlipX())
                    sprite.flip(true, false);
                batch.draw(sprite , sprite.getX(), sprite.getY());
                break;
        }
    }

    public void miwaSalto(float x, float y,SpriteBatch batch){
        switch (estadoSalto){
            case EN_PISO:
                tiempoSalto = 0;
                yInicial = sprite.getY();
                break;
            case SUBIENDO:
                System.out.println("aaaariba");
                sprite.setRegion(salto);
                y += velSalto*.75;
                sprite.setY(y);
                velSalto+=VELOCIDAD_Y*0.75f;
                sprite.draw(batch);
                if (velSalto<=0){
                    estadoSalto=EstadosSalto.BAJANDO;
                }
                /*sprite.setRegion(salto);
                tiempoSalto += 10 * Gdx.graphics.getDeltaTime();
                sprite.setY(sprite.getY()-VELOCIDAD_Y);
                tiempoVuelo = 2 * velSalto / 9.81f;
                if (tiempoSalto>tiempoVuelo/2)
                    System.out.println("lolololo");
                    estadoSalto = EstadosSalto.BAJANDO;*/
                break;
            case BAJANDO:
                velSalto=192f;
                sprite.setRegion(salto);
                sprite.setY(sprite.getY()+VELOCIDAD_Y);
                break;
            case CAIDA_LIBRE:
                velSalto=192f;
                sprite.setRegion(salto);
                sprite.setY(sprite.getY()+VELOCIDAD_Y);
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
        QUIETOD,
        QUIETOI,
    }

    public enum EstadosSalto {
        EN_PISO,
        SUBIENDO,
        BAJANDO,
        CAIDA_LIBRE
    }
}