package itesm.MisionKitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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
    private Texture texturaFondo,texturaFin,texturaReintentar,texturaMenu,texturaFondoFin;
    private AssetManager assetManager;
    private Music gameover;
    private Boton BtnReintentar,BtnMenu;
    private OrthographicCamera camara;
    private Viewport vista;
    private final int ancho=1280,alto=800;
    private SpriteBatch batch;
    private int nivel;
    public FinJuego(MisionKitsune misionKitsune, Texture texturaFondo, int nivel){
        this.nivel=nivel;
        this.misionKitsune=misionKitsune;
        this.texturaFondo=texturaFondo;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        batch=new SpriteBatch();
        assetManager=new AssetManager();
        cargarCamara();
        cargarTexturas();
        cargarBotones();
        gameover.play();
    }
    private void cargarTexturas(){
        assetManager.load("gameover.mp3",Music.class);
        assetManager.finishLoading();
        gameover=assetManager.get("gameover.mp3");
        gameover.setVolume(0.5f);
        if(nivel==2) {
            assetManager.load("N2Fin.png",Texture.class);
            assetManager.load("N2FinFondo.png", Texture.class);
            assetManager.load("N2Reintentar.png", Texture.class);
            assetManager.load("N2MenuInicialFin.png", Texture.class);
            assetManager.finishLoading();
            texturaFin = assetManager.get("N2FinFondo.png");
            texturaMenu = assetManager.get("N2MenuInicialFin.png");
            texturaReintentar = assetManager.get("N2Reintentar.png");
            texturaFondoFin=assetManager.get("N2Fin.png");
        }
        else {
            assetManager.load("Pantalla_Perder.png", Texture.class);
            assetManager.load("BtnReintentar.png", Texture.class);
            assetManager.load("BtnFinal_Menu.png", Texture.class);
            assetManager.finishLoading();
            texturaFin = assetManager.get("Pantalla_Perder.png");
            texturaMenu = assetManager.get("BtnFinal_Menu.png");
            texturaReintentar = assetManager.get("BtnReintentar.png");
        }
    }
    private void cargarCamara(){

        if(nivel!=2) {
            camara = new OrthographicCamera(ancho, alto);
            camara.position.set(ancho / 2, alto / 2, 0);
            camara.update();
            vista= new StretchViewport(ancho,alto,camara);
        }
        else{
            camara = new OrthographicCamera(alto, ancho);
            camara.position.set(ancho / 2, alto / 2, 0);
            camara.rotate(90);
            camara.update();
            vista= new StretchViewport(alto,ancho,camara);
        }

    }
    private void cargarBotones(){
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
        if(nivel==2)
            batch.draw(texturaFondoFin,ancho/2-texturaFondoFin.getWidth()/2,alto/1.65f);
        BtnMenu.render(batch);
        BtnReintentar.render(batch);
        batch.end();
        if (misionKitsune.isMudo()){
            mute();
        }else{
            unmute();
        }
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
        if(keycode==Input.Keys.BACK)
            misionKitsune.setScreen(new Menu(misionKitsune));
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
            gameover.stop();
            misionKitsune.getSonidoBotones().play();
            misionKitsune.getMusicaFondo().play();
            misionKitsune.setScreen(new Menu(misionKitsune));
        }
        else if(BtnReintentar.contiene(x,y)){
            gameover.stop();
            misionKitsune.getSonidoBotones().play();
            if(nivel==1)
                misionKitsune.setScreen(new NivelBusqueda(misionKitsune, NivelBusqueda.EstadosJuego.JUGANDO,1));
            else if(nivel==2)
                misionKitsune.setScreen(new NivelPersecucion(misionKitsune, NivelPersecucion.EstadosPersecucion.JUGANDO,2));
            else if (nivel ==3){
                misionKitsune.setScreen(new Nivel3MisionKitsune(misionKitsune, Nivel3MisionKitsune.EstadosJuego.JUGANDO,3));

            }
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
    private void mute(){
        gameover.setVolume(0);
    }
    private void unmute(){
        gameover.setVolume(1);
    }}
