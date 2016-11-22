package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by b-and on 21/11/2016.
 */

public class Splash implements Screen{
    private MisionKitsune misionKitsune;
    private float tiempo;
    private Stage escena;
    private Texture textura;
    private Camera camara;
    private Viewport vista;
    private AssetManager assetManager;
    private final int ANCHO=1920,ALTO=1280;

    Splash(MisionKitsune misionKitsune){
        this.misionKitsune=misionKitsune;
    }
    @Override
    public void show() {
        assetManager=new AssetManager();
        cargarTexturas();
        escena=new Stage();
        tiempo=1;
        inicializarCamara();
    }
    private void cargarTexturas(){
        assetManager.load("Splash.jpg",Texture.class);
        assetManager.finishLoading();
        textura=assetManager.get("Splash.jpg");
    }
    private void inicializarCamara() {
        //Creamos la camara principal del nivel
        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO / 2, ALTO / 2, 0);
        camara.update();
        //Creamos la vista
        vista = new FitViewport(ANCHO, ALTO, camara);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiempo-= Gdx.graphics.getDeltaTime();
        escena.setViewport(vista);
        escena.draw();
        escena.getBatch().begin();
        escena.getBatch().draw(textura,0,0);
        escena.getBatch().end();
        if(tiempo<=0)
            misionKitsune.setScreen(new Menu(misionKitsune));
    }


    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
