package itesm.kitsune;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by b-and on 10/10/2016.
 */
public class GemaVida {
    private TextureRegion[] gemaVidas;
    private Sprite spriteGemas;
    private int gemasContador;
    GemaVida(Texture textura){
        TextureRegion texturaCompleta = new TextureRegion(textura);
        TextureRegion[][] texturaMiwa = texturaCompleta.split(textura.getWidth()/4,textura.getHeight());
        gemaVidas= new TextureRegion[4];
        for(int i=0;i<=3;i++)
            gemaVidas[i]=texturaMiwa[0][i];
        spriteGemas=new Sprite(gemaVidas[0]);
        gemasContador=0;
    }
    public void render(SpriteBatch batch){
        if(getGemas()==1)
            gemasContador=1;
        else if(this.getGemas()==2)
            gemasContador=2;
        else if(this.getGemas()==3)
            gemasContador=3;
        else
            gemasContador=0;
        batch.draw(gemaVidas[gemasContador],spriteGemas.getX(),spriteGemas.getY());
    }
    public int getGemas(){
        return this.gemasContador;
    }
    public void setGemas(int cont){
        this.gemasContador=cont;
    }
    public Sprite getSprite() {
        return spriteGemas;
    }


    // Accesores para la posición
    public float getX() {
        return spriteGemas.getX();
    }

    public float getY() {
        return spriteGemas.getY();
    }

}
