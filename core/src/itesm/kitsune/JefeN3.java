package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Leslie on 09/11/2016.
 */

public class JefeN3 {
    public int vida = 100;
    private TextureRegion[] mordida;
    private Animation animacion;
    private Sprite sprite;
    private float tiempo;
    private Estado estado = Estado.VIVO;
    private Rectangle colision;
    private Timer timer =new Timer();

    public JefeN3(Texture textura,float x){
        TextureRegion completa = new TextureRegion(textura);
        TextureRegion[][] texturaLobo = completa.split(textura.getWidth()/3, textura.getHeight());
        mordida = new TextureRegion[3];
        for (int i = 0; i<3;i++){
            mordida[i] = texturaLobo[0][i];
        }
        animacion = new Animation(0.25f, mordida);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        sprite = new Sprite(texturaLobo[0][0]);
        sprite.setX(x);
        tiempo = 0;
        colision = new Rectangle(sprite.getX(),sprite.getY(),sprite.getX()+sprite.getWidth(),sprite.getHeight());
    }
    public void render(SpriteBatch batch){
        //NO FUNCIONA :(
        tiempo += Gdx.graphics.getDeltaTime();
        sprite.setRegion(animacion.getKeyFrame(tiempo));
        sprite.draw(batch);
        actualizarVida();
        System.out.println("ENEMIGO "+colision.toString());

    }

    private void actualizarVida (){
        switch(estado) {
            case VIVO:
                if (vida < 50) {
                    timer.start();
                    timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            vida += 1;
                        }
                    }, 0, 50);
                }
                else if (vida>=100){
                    timer.stop();
                }

                break;
            case MUERTO:
                break;
            case ATACADO:
                vida -= 5;
                estado = Estado.VIVO;
                break;
        }if (vida<=0){
            estado=Estado.MUERTO;
        }
    }

    public float getX(){
        return sprite.getX();
    }
    public float getY(){
        return sprite.getY();
    }
    public Sprite getSprite(){
        return sprite;
    }
    public void setEstado(Estado estado){
        this.estado = estado;
    }
    public Estado getEstado (){return estado;}
    public Rectangle getRectangle(){
        return this.colision;
    }
    public enum Estado{
        VIVO,
        MUERTO,
        ATACADO
    }
}
