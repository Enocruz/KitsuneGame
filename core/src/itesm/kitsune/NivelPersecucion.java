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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;


/**
 * Created by b-and on 28/10/2016.
 */

public class NivelPersecucion implements Screen,InputProcessor{
    private MisionKitsune misionKitsune;
    private Nave.NaveEnemiga naveEnemiga;
    private Nave.NaveMiwa naveMiwa;
    private OrthographicCamera camara,camaraHUD;
    private Viewport vista;
    public static final int ANCHO=1280, ALTO=800;
    private SpriteBatch batch;
    private Texture texturaFondo,texturaNaveEnemiga,texturaMiwaCentro,texturaMiwaDerecha,texturaMiwaIzquierda,
            texturaPausa,texturaNaveEnemigaD,texturaNaveEnemigaI,texNebAzul,texNebRoja,texturaMenuInicial,texturaReanudar,
            texturaPiedra, texturaPiedrita,texturaVida,texturaBarra,texturaMiniNave,texturaGemaVida;
    private AssetManager assetManager;
    private int y=0,yNave;
    private Piedra piedra,piedrita;
    private Array<Piedra> piedras,piedritas;
    private  float accelX;
    public static EstadosPersecucion estadosJuego;
    private Boton botonPausa,botonMenu,botonReanudar;
    private Random rnd;
    private GemaVida gemaVida;
    private Texto texto;
    private float tiempoInvencible,tiempoNivel,tiempoFinal;

    NivelPersecucion(MisionKitsune misionKitsune){
        this.misionKitsune=misionKitsune;
    }

    @Override
    public void show() {
        tiempoInvencible=3;
        rnd=new Random();
        Gdx.input.setInputProcessor(this);
        estadosJuego=EstadosPersecucion.JUGANDO;
        assetManager=new AssetManager();
        inicializarCamara();
        cargarTexturas();
        inicializarBotones();
        naveEnemiga=new Nave.NaveEnemiga(texturaNaveEnemiga,texturaNaveEnemigaD,texturaNaveEnemigaI);
        naveEnemiga.getSprite().setPosition(ANCHO/2,ALTO-texturaNaveEnemiga.getHeight());
        naveMiwa=new Nave.NaveMiwa(texturaMiwaCentro,texturaMiwaDerecha,texturaMiwaIzquierda);
        naveMiwa.getSprite().setPosition(ANCHO/2,0);
        piedras=new Array<Piedra>(3);
        piedritas=new Array<Piedra>(2);
        gemaVida=new GemaVida(texturaGemaVida);
        texto=new Texto("DominoFont.fnt");
        gemaVida.getSprite().setPosition(texturaGemaVida.getWidth()/2,ALTO-texturaGemaVida.getHeight()-ANCHO/80);
        for(int i=0;i<3;i++) {
            piedra=new Piedra(texturaPiedra,(350*(i+1))-texturaPiedra.getWidth()/2,ALTO+texturaPiedrita.getHeight());
            piedras.add(piedra);
        }
        for(int i=0;i<2;i++){
            piedrita=new Piedra(texturaPiedrita,400*(i+1),ALTO+texturaPiedrita.getHeight());
            piedritas.add(piedrita);
        }
        yNave=ALTO/2-texturaBarra.getHeight()/2-texturaMiniNave.getHeight()/2;
        tiempoNivel=1;
        tiempoFinal=0;
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
        assetManager.load("N2Vida.png",Texture.class);
        assetManager.load("N2Reanudar.png", Texture.class);
        assetManager.load("N2MenuInicial.png", Texture.class);
        assetManager.load("pausa.png", Texture.class);
        assetManager.load("FondoEstrellas.png", Texture.class);
        assetManager.load("N2NaveEnemiga.png", Texture.class);
        assetManager.load("N2NaveMiwa.png", Texture.class);
        assetManager.load("N2NaveMiwaDerecha.png", Texture.class);
        assetManager.load("N2NaveMiwaIzquierda.png", Texture.class);
        assetManager.load("N2NaveEnemigaIzquierda.png", Texture.class);
        assetManager.load("N2NaveEnemigaDerecha.png", Texture.class);
        assetManager.load("FondoNebulosaAzul.png", Texture.class);
        assetManager.load("FondoNebulosaRoja.png", Texture.class);
        assetManager.load("Pantalla_Pausa.png", Texture.class);
        assetManager.load("N2Piedra.png", Texture.class);
        assetManager.load("N2Piedritas.png", Texture.class);
        assetManager.load("Barra.png",Texture.class);
        assetManager.load("N2IconoNave.png",Texture.class);
        assetManager.load("N2ContadorGemas.png",Texture.class);
        assetManager.finishLoading();
        //Textura Vida
        texturaReanudar = assetManager.get("N2Reanudar.png");
        texturaMenuInicial = assetManager.get("N2MenuInicial.png");
        texturaFondo = assetManager.get("FondoEstrellas.png");
        texturaPausa = assetManager.get("Pantalla_Pausa.png");
        texturaNaveEnemiga = assetManager.get("N2NaveEnemiga.png");
        texturaMiwaCentro = assetManager.get("N2NaveMiwa.png");
        texturaMiwaDerecha = assetManager.get("N2NaveMiwaDerecha.png");
        texturaMiwaIzquierda = assetManager.get("N2NaveMiwaIzquierda.png");
        texturaPausa = assetManager.get("pausa.png");
        texturaNaveEnemigaD = assetManager.get("N2NaveEnemigaDerecha.png");
        texturaNaveEnemigaI = assetManager.get("N2NaveEnemigaIzquierda.png");
        texNebAzul = assetManager.get("FondoNebulosaAzul.png");
        texNebRoja = assetManager.get("FondoNebulosaRoja.png");
        texturaPiedra = assetManager.get("N2Piedra.png");
        texturaPiedrita=assetManager.get("N2Piedritas.png");
        texturaVida=assetManager.get("N2Vida.png");
        texturaMiniNave=assetManager.get("N2IconoNave.png");
        texturaBarra=assetManager.get("Barra.png");
        texturaGemaVida=assetManager.get("N2ContadorGemas.png");
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
            tiempoNivel-=Gdx.graphics.getDeltaTime();
            if(tiempoNivel<=0){
                yNave+=5;
                tiempoNivel=1;
                tiempoFinal++;
            }
            if(estadosJuego==EstadosPersecucion.INVENCIBLE){
                tiempoInvencible -= Gdx.graphics.getDeltaTime();
                if (tiempoInvencible <= 0) {
                    estadosJuego = EstadosPersecucion.JUGANDO;
                    tiempoInvencible = 3;
                }
            }
            if(naveMiwa.getVidas()<=0){
                estadosJuego=EstadosPersecucion.PERDIO;
                misionKitsune.setScreen(new FinJuego(misionKitsune, new Texture("FondoNebulosaAzul.png"),2));
            }
            for(Piedra p:piedritas)
                if(p.getEstadosPiedra()== Piedra.EstadosPiedra.NUEVA)
                    if(rnd.nextInt(10)>=5)
                        p.getSprite().setX(naveMiwa.getX());
            botonMenu.setDisabled(true);
            botonReanudar.setDisabled(true);
            botonPausa.setDisabled(false);
            batch.setProjectionMatrix(camaraHUD.combined);
            batch.begin();
            gemaVida.render(batch);
            naveEnemiga.render(batch);
            naveMiwa.render(batch);
            botonPausa.render(batch);
            batch.draw(texturaBarra,ANCHO-texturaBarra.getWidth()*3,ALTO/2-texturaBarra.getHeight()/2);
            batch.draw(texturaVida, texturaVida.getWidth() / 8, ALTO - texturaVida.getHeight() - 16);
            texto.mostrarMensaje(batch, "" + naveMiwa.getVidas(), 126, 722);
            batch.draw(texturaMiniNave,ANCHO-texturaBarra.getWidth()*3-texturaMiniNave.getWidth()/4,yNave);
            for(Piedra x: piedras)
                x.render(batch,7,1);
            for(Piedra x: piedritas)
                x.render(batch,3,0);
            batch.end();
            accelX = Gdx.input.getAccelerometerX();
            if (accelX < -0.5f) {
                naveMiwa.setMovimiento(Nave.MOVIMIENTO.DERECHA);
            } else if (accelX > 0.5f) {
                naveMiwa.setMovimiento(Nave.MOVIMIENTO.IZQUIERDA);
            } else {
                naveMiwa.setMovimiento(Nave.MOVIMIENTO.QUIETO);
            }
            y -= 1;
            if(estadosJuego!=EstadosPersecucion.INVENCIBLE) {
                for (Piedra p : piedras)
                    if (naveMiwa.getRectangle().overlaps(p.getRectangle())) {
                        p.setEstado(Piedra.EstadosPiedra.DESAPARECER);
                        naveMiwa.setVidas(naveMiwa.getVidas() - 1);
                        estadosJuego=EstadosPersecucion.INVENCIBLE;
                        //naveMiwa.getSprite().setAlpha(0.5f);
                    }
                for (Piedra p : piedritas)
                    if (naveMiwa.getRectangle().overlaps(p.getRectangle()))
                        p.setEstado(Piedra.EstadosPiedra.DESAPARECER);
            }
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
        GANO,
        INTRO,
        INVENCIBLE
    }

}
