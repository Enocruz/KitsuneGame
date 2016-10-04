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
    private final float velocidad=2;
    private Sprite sprite,spriteSalto;
    private TextureRegion[] walkFrames;
    private Animation animacion;
    private float tiempo;
    private Estados estados;
    Miwa(Texture textura){
        TextureRegion texturaCompleta = new TextureRegion(textura);
        TextureRegion[][] texturaMiwa = texturaCompleta.split(textura.getWidth()/10,textura.getHeight());
        walkFrames= new TextureRegion[8];
        for(int i=1;i<9;i++)
            walkFrames[i-1]=texturaMiwa[0][i];
        animacion=new Animation(2f,walkFrames);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        tiempo=0;
        sprite=new Sprite(texturaMiwa[0][0]);
        spriteSalto=new Sprite(texturaMiwa[0][9]);
        estados=Estados.QUIETO;
    }
    public void render(SpriteBatch batch){
        float x=sprite.getX();
        switch (estados) {
            case DERECHA:
            case IZQUIERDA:
                tiempo += Gdx.graphics.getDeltaTime();
                //sprite.setTexture(animacion.getKeyFrame(tiempo).getTexture());
                TextureRegion region = animacion.getKeyFrame(tiempo);
                //sprite.setTexture(region.getTexture());
                if (estados == Estados.DERECHA) {
                    x += velocidad;
                    if (region.isFlipX()) {
                        region.flip(true, false);
                    }
                    if (x <= Nivel1.ANCHO - sprite.getWidth())
                        sprite.setX(x);
                } else {
                    x -= velocidad;
                    if (!region.isFlipX()) {
                        region.flip(true, false);
                    }
                    if (x >= 0)
                        sprite.setX(x);
                }
                batch.draw(region, sprite.getX(), sprite.getY());
                break;
            case QUIETO:
                sprite.draw(batch);
                break;
        }
    }
    public Sprite getSprite() {
        return sprite;
    }

    // Accesores para la posición
    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public void setPosicion(float x, int y) {
        sprite.setPosition(x,y);
    }

    // Accesor del estadoMovimiento
    public Estados getEstadoMovimiento() {
        return estados;
    }

    // Modificador del estadoMovimiento
    public void setEstadoMovimiento(Estados estadoMovimiento) {
        this.estados = estadoMovimiento;
    }
    public enum Estados{
        IZQUIERDA,
        DERECHA,
        QUIETO
    }
}
