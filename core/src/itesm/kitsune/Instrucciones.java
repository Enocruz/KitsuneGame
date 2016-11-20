package itesm.kitsune;

/**
 * Created by b-and on 06/09/2016.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Instrucciones implements Screen {
    private final MisionKitsune misionKitsune;
    private Stage escena;
    private Texture texturaFondo,texturaRegresar;
    private AssetManager assetManager=new AssetManager();
    private OrthographicCamera camara;
    private Viewport vista;
    private final int ancho=1280,alto=800;


    public Instrucciones(MisionKitsune misionKitsune){
        this.misionKitsune=misionKitsune;
    }
    @Override
    public void show() {

        assetManager=new AssetManager();
        //Camara
        camara=new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        //Vista
        vista= new StretchViewport(ancho,alto,camara);

        cargarTexturas();
        escena=new Stage();
        Gdx.input.setInputProcessor(escena);

        //Fondo
        Image imgFondo= new Image(texturaFondo);

        escena.addActor(imgFondo);

        TextureRegionDrawable trBtnRegresar=new TextureRegionDrawable(new TextureRegion(texturaRegresar));
        ImageButton btnRegresar=new ImageButton(trBtnRegresar);
        btnRegresar.setPosition(ancho/1.15f-btnRegresar.getWidth()/2, 0.75f*alto);
        escena.addActor(btnRegresar);

        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Regresar a la pantalla del Menu
                misionKitsune.setScreen(new Menu(misionKitsune));
                misionKitsune.getSonidoBotones().play();
                }
            }
        );

    }

    private void cargarTexturas() {
        //Textura fondo
        assetManager.load("Instrucciones.png", Texture.class);
        //Texturas de Boton
        assetManager.load("cerrar.png",Texture.class);
        //Se bloquea hasta cargar los recursos
        assetManager.finishLoading();
        //Cuando termina, leemos las texturas
        texturaFondo=assetManager.get("Instrucciones.png");
        texturaRegresar=assetManager.get("cerrar.png");

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Dependencia de la camara
        escena.setViewport(vista);
        escena.draw();
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
        dispose();
    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        texturaRegresar.dispose();
    }
}

