package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by b-and on 07/10/2016.
 */
public class Mapa{
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer rendererMapa;

    Mapa(String nombre){
        this.mapa= new TmxMapLoader().load(nombre);

    }
    public void render(SpriteBatch batch, OrthographicCamera camara) {
        rendererMapa=new OrthogonalTiledMapRenderer(mapa,batch);
        rendererMapa.setView(camara);

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        rendererMapa.render();
    }
    public TiledMap getMapa(){
        return this.mapa;
    }



}
