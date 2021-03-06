package itesm.MisionKitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
 * Created by b-and on 17/10/2016.
 */

public class MenuMapas implements Screen , InputProcessor{
    private MisionKitsune misionKitsune;
    private Texture texturaFondo,texturaNivel1,texturaNivel2,texturaNivel3,texturaCerrar,textHistoria;
    private final int ancho=1280,alto=800;
    private OrthographicCamera camara;
    private Viewport vista;
    private AssetManager assetManager;
    private Boton boton1,boton2,boton3,botonCerrar,btnHistoria;
    private SpriteBatch batch;

    public MenuMapas(MisionKitsune misionKitsune) {
        this.misionKitsune = misionKitsune;
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(this);
        assetManager=new AssetManager();
        cargarCamara();
        cargarTexturas();
        batch=new SpriteBatch();
        //Fondo
        cargarBotones();
    }
    private void cargarBotones(){
        //Nivel 1
        boton1=new Boton(texturaNivel1);
        boton1.setPosicion(ancho/4.2f-texturaNivel1.getWidth()/2, alto/2.9f);
        //Nivel 2
        boton2=new Boton(texturaNivel2);
        boton2.setPosicion(ancho/1.95f-texturaNivel2.getWidth()/2, alto/2.9f);
        boton2.setAlfa(0.2f);
        boton2.setDisabled(true);
        //Nivel 2
        boton3=new Boton(texturaNivel3);
        boton3.setPosicion(ancho/1.3f-texturaNivel3.getWidth()/2, alto/2.9f);
        boton3.setAlfa(0.2f);
        boton3.setDisabled(true);
        botonCerrar=new Boton(texturaCerrar);
        botonCerrar.setPosicion(ancho/1.2f-texturaCerrar.getWidth()/2,alto/1.3f);
        btnHistoria=new Boton(textHistoria);
        btnHistoria.setPosicion(ancho/2-textHistoria.getWidth()/2,textHistoria.getHeight());
    }

    private void cargarCamara() {
        //Camara
        camara=new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        //Vista
        vista= new StretchViewport(ancho,alto,camara);
    }

    
    private void cargarTexturas() {
        //Textura fondo
        assetManager.load("Mision_Nivel3.png", Texture.class);
        //Texturas de Botones
        assetManager.load("Nivel1.png",Texture.class);
        assetManager.load("Nivel_2.png",Texture.class);
        assetManager.load("Nivel_3.png",Texture.class);
        assetManager.load("cerrar.png",Texture.class);
        assetManager.load("BtnHistoria.png",Texture.class);
        //Se bloquea hasta cargar los recursos
        assetManager.finishLoading();
        //Cuando termina, leemos las texturas
        texturaFondo=assetManager.get("Mision_Nivel3.png");
        texturaNivel1=assetManager.get("Nivel1.png");
        texturaNivel2=assetManager.get("Nivel_2.png");
        texturaNivel3=assetManager.get("Nivel_3.png");
        texturaCerrar=assetManager.get("cerrar.png");
        textHistoria=assetManager.get("BtnHistoria.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Dependencia de la camara
        if(misionKitsune.getNivel()==2){
            boton2.setAlfa(1);
            boton2.setDisabled(false);
        }
        else if(misionKitsune.getNivel()>=3){
            boton2.setAlfa(1);
            boton2.setDisabled(false);
            boton3.setAlfa(1);
            boton3.setDisabled(false);
        }
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        boton1.render(batch);
        boton2.render(batch);
        boton3.render(batch);
        botonCerrar.render(batch);
        btnHistoria.render(batch);
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
        texturaNivel1.dispose();
        texturaFondo.dispose();
        texturaNivel2.dispose();
        texturaNivel3.dispose();
        texturaCerrar.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK)
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
        if(boton1.contiene(x,y)){
            misionKitsune.getMusicaFondo().stop();
            misionKitsune.getSonidoBotones().play();
            misionKitsune.setScreen(new Cargando.CargandoBusqueda(misionKitsune));
        }
        if(boton2.contiene(x,y)){
            misionKitsune.getMusicaFondo().stop();
            misionKitsune.getSonidoBotones().play();
            misionKitsune.setScreen(new Cargando.CargandoPersecucion(misionKitsune));
        }
        if (boton3.contiene(x,y)){
            misionKitsune.getMusicaFondo().stop();
            misionKitsune.getSonidoBotones().play();
            dispose();
            misionKitsune.setScreen(new Cargando.CargandoKitsune(misionKitsune));
        }
        if(botonCerrar.contiene(x,y)){
            misionKitsune.getSonidoBotones().play();
            dispose();
            misionKitsune.setScreen(new Menu(misionKitsune));
        }
        if(btnHistoria.contiene(x,y)){
            /*HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
            Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://www.google.de").build();
            Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);*/
            Gdx.net.openURI("https://www.youtube.com/watch?v=gCB73YLPkmo&feature=youtu.be");
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

