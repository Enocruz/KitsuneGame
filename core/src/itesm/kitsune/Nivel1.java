package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by b-and on 08/09/2016.
 */
public class Nivel1 implements Screen, InputProcessor {
    private MisionKitsune misionKitsune;
    //Texturas Pantalla
    private Texture texturaVida,texturaFondo,texturaDer,texturaIzq,texturaSaltar,texturaPausa,texturaMiwa, texturaGema;
    //Botones pantalla
    private Boton botonIzq,botonDer,botonSaltar1,botonSaltar2,botonPausa;
    //AssetManager (Cargar texturas)
    private final AssetManager assetManager=new AssetManager();
    // La cámara principal, la vista y la camara de botones
    private OrthographicCamera camara,camaraHUD;
    private Viewport vista;
    // Objeto para dibujar en la pantalla
    private SpriteBatch batch;
    //Tamaño de pantalla
    public static final int ANCHO=1280,ALTO=960;
    public static int ANCHO_MAPA=2560, ALTO_MAPA=1920;
    //Personaje principal
    private Miwa miwa;
    //Mapa del nivel
    private Mapa mapa;
    //Textura Fondo
    private Fondo fondo;
    //Tamaño celdas TileMap
    public static final int TAM_CELDA = 32;
    //Estados Juego
    private EstadosJuego estadosJuego;
    //Contador para vidas
    private GemaVida gemaVida;
    //Vidas
    private int vidas=3;


    public Nivel1(MisionKitsune misionKitsune) {
        this.misionKitsune = misionKitsune;
    }

    @Override
    public void show() {
        //Inicializamos camara
        inicializarCamara();
        //Cargamos texturas
        cargarTexturas();
        //Cargamos botones
        crearBotones();
        //Creamos mapa
        mapa=new Mapa("MapaN1.tmx");
        //Creamos personaje principal
        miwa=new Miwa(texturaMiwa);
        miwa.getSprite().setPosition(128,256); //Posicion inicial de Miwa
        //Creamos el fondo
        fondo=new Fondo(texturaFondo);
        //Escena con "Listeners"
        Gdx.input.setInputProcessor(this);
        batch=new SpriteBatch();
        //Estado Juego
        estadosJuego=EstadosJuego.JUGANDO;
        //Contador para vida Extra
        gemaVida=new GemaVida(texturaGema);
        gemaVida.getSprite().setPosition(texturaGema.getWidth()/2,ALTO-texturaGema.getHeight()-16);


    }
    //Crear los botones del menú principal
    private void crearBotones(){
        botonIzq=new Boton(texturaIzq);
        botonIzq.setPosicion(botonIzq.getX(),ALTO/3f);
        botonIzq.setAlfa(0.6f);
        botonDer=new Boton(texturaDer);
        botonDer.setPosicion(ANCHO/1.1f,ALTO/3f);
        botonDer.setAlfa(0.6f);
        botonPausa=new Boton(texturaPausa);
        botonPausa.setPosicion(ANCHO-texturaPausa.getWidth()-20,ALTO-texturaPausa.getHeight()-20);
        botonPausa.setAlfa(0.8f);
        botonSaltar1=new Boton(texturaSaltar);
        botonSaltar1.setPosicion(botonSaltar1.getX(),ALTO/2f);
        botonSaltar1.setAlfa(0.6f);
        botonSaltar2=new Boton(texturaSaltar);
        botonSaltar2.setPosicion(ANCHO/1.1f,ALTO/2f);
        botonSaltar2.setAlfa(0.6f);
    }
    private void inicializarCamara() {
        //Creamos la camara principal del nivel
        camara=new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();
        //Creamos la vista
        vista=new StretchViewport(ANCHO,ALTO,camara);
        //Creamos la camara con los botones
        camaraHUD = new OrthographicCamera(ANCHO,ALTO);
        camaraHUD.position.set(ANCHO / 2, ALTO / 2, 0);
        camaraHUD.update();
    }

    private void cargarTexturas() {
        //Textura Miwa
        assetManager.load("miwa.png",Texture.class);
        //Textura Vida
        assetManager.load("Vida.png",Texture.class);
        assetManager.load("GemaContador.png",Texture.class);
        //Textura fondo
        assetManager.load("fondo.png", Texture.class);
        //Texturas de Boton
        assetManager.load("pausa.png",Texture.class);
        assetManager.load("izquierda.png",Texture.class);
        assetManager.load("derecha.png",Texture.class);
        assetManager.load("salto.png",Texture.class);
        //Se bloquea hasta cargar los recursos
        assetManager.finishLoading();
        //Textura Vida
        texturaVida=assetManager.get("Vida.png");
        texturaGema=assetManager.get("GemaContador.png");
        //Cuando termina, leemos las texturas
        texturaFondo=assetManager.get("fondo.png");
        //Texturas botones
        texturaPausa=assetManager.get("pausa.png");
        texturaDer=assetManager.get("derecha.png");
        texturaIzq=assetManager.get("izquierda.png");
        texturaSaltar=assetManager.get("salto.png");
        //Textura Miwa
        texturaMiwa=assetManager.get("miwa.png");
    }

    private void actualizarCamara() {
        float posY=miwa.getY();
        float posX = miwa.getX();
        // Si está en la parte 'media'
        if (posX>=ANCHO/2 && posX<=ANCHO_MAPA-ANCHO/2) {
            // El personaje define el centro de la cámara
            camara.position.set((int)posX, camara.position.y, 0);
        } else if (posX>=ANCHO_MAPA-ANCHO/2) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camara.position.set(ANCHO_MAPA-ANCHO/2, camara.position.y, 0);
        }
        camara.update();

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        actualizarCamara();
        batch.begin();
        fondo.draw(batch);
        batch.end();
        //////
        //Camara principal
        batch.setProjectionMatrix(camara.combined);
        mapa.render(batch,camara);
        batch.begin();
        miwa.render(batch);
        batch.end();
        //Dependencia de la camara con todos los botones
        batch.setProjectionMatrix(camaraHUD.combined);
        batch.begin();
        gemaVida.render(batch);
        botonDer.render(batch);
        botonIzq.render(batch);
        batch.draw(texturaVida,texturaVida.getWidth()/8,ALTO-texturaVida.getHeight()-16);
        botonSaltar1.render(batch);
        botonSaltar2.render(batch);
        botonPausa.render(batch);
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
        texturaVida.dispose();
        texturaIzq.dispose();
        texturaDer.dispose();
        texturaPausa.dispose();
        texturaSaltar.dispose();
        texturaFondo.dispose();
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
        camaraHUD.unproject(v);
        float x=v.x,y=v.y;
        if(botonIzq.contiene(x,y)){
            miwa.setEstadoMovimiento(Miwa.Estados.IZQUIERDA);


        }
        else if(botonDer.contiene(x,y)){
            miwa.setEstadoMovimiento(Miwa.Estados.DERECHA);
        }
        else if(botonSaltar1.contiene(x,y)||botonSaltar2.contiene(x,y)){
            miwa.setEstadoSalto(Miwa.Salto.SALTO);
        }
        else if(botonPausa.contiene(x,y)){
            Gdx.app.log("clicked","Pausa");
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 v=new Vector3(screenX,screenY,0);
        camaraHUD.unproject(v);
        float x=v.x,y=v.y;
        if(botonIzq.contiene(x,y)){
            miwa.setEstadoMovimiento(Miwa.Estados.QUIETOI);
        }
        else if(botonDer.contiene(x,y)){
            miwa.setEstadoMovimiento(Miwa.Estados.QUIETOD);
        }
        else if(botonSaltar1.contiene(x,y)|| botonSaltar2.contiene(x,y)){
            miwa.setEstadoSalto(Miwa.Salto.SALTO);
        }
        else if(botonPausa.contiene(x,y)){
            Gdx.app.log("clicked","Pausa");
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
    public enum EstadosJuego {
        GANO,
        JUGANDO,
        PAUSADO,
        PERDIO
    }
}
