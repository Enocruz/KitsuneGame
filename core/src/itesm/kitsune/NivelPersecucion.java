package itesm.kitsune;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;


/**
 * Created by b-and on 28/10/2016.
 */

public class NivelPersecucion implements Screen,InputProcessor{
    private MisionKitsune misionKitsune;
    private Nave naveEnemiga,naveMiwa;
    private OrthographicCamera camara,camaraHUD;
    private Viewport vista;
    public static final int ANCHO=1280, ALTO=800;
    private SpriteBatch batch;
    private Texture texturaFondo,texturaNaveEnemiga,texturaMiwaCentro,texturaMiwaDerecha,texturaMiwaIzquierda,
            texturaPausa,texturaNaveEnemigaD,texturaNaveEnemigaI,texNebAzul,texNebRoja,texturaMenuInicial,texturaReanudar,texturaPiedra, texturaPiedrita;
    private AssetManager assetManager;
    private int y=0;
    private Piedra piedra,piedrita;
    private Array<Piedra> piedras,piedritas;
    private  float accelX;
    public static EstadosPersecucion estadosJuego;
    private Boton botonPausa,botonMenu,botonReanudar;
    private Random rnd;

    NivelPersecucion(MisionKitsune misionKitsune){
        this.misionKitsune=misionKitsune;
    }

    @Override
    public void show() {
        rnd=new Random();
        Gdx.input.setInputProcessor(this);
        estadosJuego=EstadosPersecucion.JUGANDO;
        assetManager=new AssetManager();
        inicializarCamara();
        cargarTexturas();
        inicializarBotones();
        naveEnemiga=new Nave(texturaNaveEnemiga,texturaNaveEnemigaD,texturaNaveEnemigaI);
        //naveEnemiga.getSprite().setPosition(ANCHO/2-texturaNaveEnemiga.getWidth()/2,ALTO+texturaNaveEnemiga.getHeight()/4);
        naveEnemiga.getSprite().setPosition(ANCHO/2,ALTO-texturaNaveEnemiga.getHeight());
        naveMiwa=new Nave(texturaMiwaCentro,texturaMiwaDerecha,texturaMiwaIzquierda);
        //naveMiwa.getSprite().setPosition(ANCHO/2-texturaMiwaCentro.getWidth()/2,-texturaMiwaCentro.getHeight());
        naveMiwa.getSprite().setPosition(ANCHO/2,0);
        piedras=new Array<Piedra>(3);
        piedritas=new Array<Piedra>(2);
        for(int i=0;i<3;i++) {
            piedra=new Piedra(texturaPiedra,(350*(i+1))-texturaPiedra.getWidth()/2,ALTO+texturaPiedrita.getHeight());
            piedras.add(piedra);
        }
        for(int i=0;i<2;i++){
            piedrita=new Piedra(texturaPiedrita,400*(i+1),ALTO+texturaPiedrita.getHeight());
            piedritas.add(piedrita);
        }
    }
    private void inicializarBotones(){
        botonPausa=new Boton(texturaPausa);
        botonPausa.setPosicion(ANCHO-texturaPausa.getWidth()*1.5f,ALTO-texturaPausa.getHeight()*1.5f);
        botonPausa.setAlfa(0.7f);
        botonMenu=new Boton(texturaMenuInicial);
        botonMenu.setPosicion(ANCHO/2-texturaMenuInicial.getWidth()/2,ALTO/2);
        botonMenu.setDisabled(true);
        botonReanudar=new Boton(texturaReanudar);
        botonReanudar.setPosicion(ANCHO/2-texturaReanudar.getWidth()/2,ALTO/3);
        botonReanudar.setDisabled(true);
    }

    private void cargarTexturas() {
        batch = new SpriteBatch();
        //Texturas Dialogos
        assetManager.load("Reanudar.png", Texture.class);
        assetManager.load("Menu_Inicial.png", Texture.class);
        assetManager.load("pausa.png", Texture.class);
        assetManager.load("FondoEstrellas.png", Texture.class);
        assetManager.load("NaveEnemiga.png", Texture.class);
        assetManager.load("NaveMiwaCentro.png", Texture.class);
        assetManager.load("NaveMiwaDerecha.png", Texture.class);
        assetManager.load("NaveMiwaIzquierda.png", Texture.class);
        assetManager.load("NaveEnemigaIzquierda.png", Texture.class);
        assetManager.load("NaveEnemigaDerecha.png", Texture.class);
        assetManager.load("FondoNebulosaAzul.png", Texture.class);
        assetManager.load("FondoNebulosaRoja.png", Texture.class);
        assetManager.load("Pantalla_Pausa.png", Texture.class);
        assetManager.load("Piedra.png", Texture.class);
        assetManager.load("Piedritas.png", Texture.class);
        assetManager.finishLoading();
        //Textura Vida
        texturaReanudar = assetManager.get("Reanudar.png");
        texturaMenuInicial = assetManager.get("Menu_Inicial.png");
        texturaFondo = assetManager.get("FondoEstrellas.png");
        texturaPausa = assetManager.get("Pantalla_Pausa.png");
        texturaNaveEnemiga = assetManager.get("NaveEnemiga.png");
        texturaMiwaCentro = assetManager.get("NaveMiwaCentro.png");
        texturaMiwaDerecha = assetManager.get("NaveMiwaDerecha.png");
        texturaMiwaIzquierda = assetManager.get("NaveMiwaIzquierda.png");
        texturaPausa = assetManager.get("pausa.png");
        texturaNaveEnemigaD = assetManager.get("NaveEnemigaDerecha.png");
        texturaNaveEnemigaI = assetManager.get("NaveEnemigaIzquierda.png");
        texNebAzul = assetManager.get("FondoNebulosaAzul.png");
        texNebRoja = assetManager.get("FondoNebulosaRoja.png");
        texturaPiedra = assetManager.get("Piedra.png");
        texturaPiedrita=assetManager.get("Piedritas.png");

    }


    private void inicializarCamara() {
        //Creamos la camara principal del nivel
        camara=new OrthographicCamera(ALTO,ANCHO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.rotate(90);
        camara.update();
        //Creamos la vista
        vista=new StretchViewport(ALTO,ANCHO,camara);
        //Creamos la camara con los botones
        camaraHUD = new OrthographicCamera(ALTO,ANCHO);
        camaraHUD.position.set(ANCHO/2, ALTO/2, 0);
        camaraHUD.rotate(90);
        camaraHUD.update();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        texturaFondo.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo, 0, 0, 0, y, ANCHO, ALTO);
        batch.end();
        if(estadosJuego==EstadosPersecucion.PAUSADO){
            batch.setProjectionMatrix(camaraHUD.combined);
            botonMenu.setDisabled(false);
            botonReanudar.setDisabled(false);
            botonPausa.setDisabled(true);
            batch.begin();
            botonMenu.render(batch);
            botonReanudar.render(batch);
            batch.end();

        }
        else{
            for(Piedra p:piedritas)
                if(p.getEstadosPiedra()== Piedra.EstadosPiedra.NUEVA) {
                    if(rnd.nextInt(10)>=5)
                        p.getSprite().setX(naveMiwa.getX());

                }
            //}System.out.println(naveEnemiga.getX()+" "+piedrita.getY());
            botonMenu.setDisabled(true);
            botonReanudar.setDisabled(true);
            botonPausa.setDisabled(false);
            batch.setProjectionMatrix(camaraHUD.combined);
            batch.begin();
            naveEnemiga.render(batch);
            naveMiwa.render(batch);
            botonPausa.render(batch);
            for(Piedra x: piedras)
                x.render(batch,7,1);
            for(Piedra x: piedritas)
                x.render(batch,3,0);
            batch.end();
            accelX = Gdx.input.getAccelerometerX();
            if (accelX < -0.5f) {
                naveEnemiga.setMovimiento(Nave.MOVIMIENTO.DERECHA);
                naveMiwa.setMovimiento(Nave.MOVIMIENTO.DERECHA);
            } else if (accelX > 0.5f) {
                naveEnemiga.setMovimiento(Nave.MOVIMIENTO.IZQUIERDA);
                naveMiwa.setMovimiento(Nave.MOVIMIENTO.IZQUIERDA);
            } else {
                naveEnemiga.setMovimiento(Nave.MOVIMIENTO.QUIETO);
                naveMiwa.setMovimiento(Nave.MOVIMIENTO.QUIETO);
            }
            y -= 1;
            for(Piedra p:piedras)
                if(naveMiwa.getRectangle().overlaps(p.getRectangle()))
                    p.setEstado(Piedra.EstadosPiedra.DESAPARECER);
            for(Piedra p:piedritas)
                if(naveMiwa.getRectangle().overlaps(p.getRectangle()))
                    p.setEstado(Piedra.EstadosPiedra.DESAPARECER);

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
       return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 v=new Vector3(screenX,screenY,0);
        camaraHUD.unproject(v);
        float x=v.x,y=v.y;
        if(botonPausa.contiene(x,y)) {
            estadosJuego=EstadosPersecucion.PAUSADO;
        }
        if(botonReanudar.contiene(x,y)){
            estadosJuego=EstadosPersecucion.JUGANDO;
            System.out.println("Me estas tocando");
        }
        if(botonMenu.contiene(x,y)){
            MisionKitsune.musicaFondo.play();
            Menu.sonidoBotones.play();
            misionKitsune.setScreen(new Menu(misionKitsune));
        }
        return true;

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

    public enum EstadosPersecucion{
        JUGANDO,
        PAUSADO,
        PERDIO,
        GANO
    }

}
