package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by b-and on 19/10/2016.
 */

public class FinJuego implements Screen, InputProcessor {
    private MisionKitsune misionKitsune;
    private Texture texturaFondo,texturaFin,texturaReintentar,texturaMenu;
    private AssetManager assetManager;
    private Boton BtnReintentar,BtnMenu;
    private OrthographicCamera camara;
    private Viewport vista;
    private final int ancho=1280,alto=800;
    private SpriteBatch batch;
    public FinJuego(MisionKitsune misionKitsune,Texture texturaFondo){
        this.misionKitsune=misionKitsune;
        this.texturaFondo=texturaFondo;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch=new SpriteBatch();
        assetManager=new AssetManager();
        cargarCamara();
        cargarTexturas();
        cargarBotones();



    }
    public void cargarTexturas(){
        assetManager.load("Pantalla_Perder.png",Texture.class);
        //assetManager.load("fondoNivel1.png",Texture.class);
        assetManager.load("BtnReintentar.png",Texture.class);
        assetManager.load("BtnFinal_Menu.png",Texture.class);
        assetManager.finishLoading();
        texturaFin=assetManager.get("Pantalla_Perder.png");
        //texturaFondo=assetManager.get("fondoNivel1.png");
        texturaMenu=assetManager.get("BtnFinal_Menu.png");
        texturaReintentar=assetManager.get("BtnReintentar.png");

    }
    public void cargarCamara(){
        camara=new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        //Vista
        vista= new StretchViewport(ancho,alto,camara);
    }
    public void cargarBotones(){
        BtnReintentar=new Boton(texturaReintentar);
        BtnReintentar.setPosicion(ancho/2-texturaReintentar.getWidth()/2,alto/4);
        BtnMenu=new Boton(texturaMenu);
        BtnMenu.setPosicion(ancho/2-texturaMenu.getWidth()/2,alto/2.5f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.draw(texturaFin,0,0);
        BtnMenu.render(batch);
        BtnReintentar.render(batch);
        batch.end();
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 v=new Vector3(screenX,screenY,0);
        camara.unproject(v);
        float x=v.x,y=v.y;
        if(BtnMenu.contiene(x,y)){
            misionKitsune.setScreen(new Menu(misionKitsune));
        }
        if(BtnReintentar.contiene(x,y)){
            if(MisionKitsune.nivel==1)
                misionKitsune.setScreen(new Nivel1(misionKitsune));

        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
