package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by b-and on 08/09/2016.
 */
public class NivelBusqueda implements Screen, InputProcessor {
    private MisionKitsune misionKitsune;
    //Texturas Pantalla
    private Texture texturaVida,texturaDer,texturaIzq,texturaSaltar,texturaPausa,
            texturaMiwa, texturaGema,texturaBotonReanudar, texturaBotonMenuInicial,texturaMenuPausa, Dial1,Dial2,
    Predial1,Predial2,Predial3,Predial4,Predial5,Predial6,Predial7,Predial8;
    private Texture[] Dialogos,Predialogos;
    //Botones pantalla
    private Boton botonIzq,botonDer,botonSaltar1,botonSaltar2,botonPausa,botonReanudar,botonMenuInicial;
    //AssetManager (Cargar texturas)
    private final AssetManager assetManager=new AssetManager();
    // La cámara principal, la vista y la camara de botones
    private OrthographicCamera camara,camaraHUD;
    private Viewport vista;
    // Objeto para dibujar en la pantalla
    private SpriteBatch batch;
    //Tamaño de pantalla
    public static final int ANCHO=1280,ALTO=800;
    public static int ANCHO_MAPA=2560, ALTO_MAPA=1920;
    //Personaje principal
    private Miwa miwa;
    //Mapa del nivel
    private Mapa.Mapa1 mapa;
    //Tamaño celdas TileMap
    public static final int TAM_CELDA = 32;
    //Estados Juego
    private EstadosJuego estadosJuego;
    //Contador para vidas
    private GemaVida gemaVida;
    //Texto vidas
    private Texto texto;
    //Contador gemas para vida extra
    private int contadorGemas=0,conPre=0,conDial=0;
    private float tiempoInvencible=3,tiempoGemas=2,tiempoDialogo=1;
    private Music SonidoGemas,SonidoPicos,SonidoPre,SonidoDial;



    public NivelBusqueda(MisionKitsune misionKitsune) {
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
        mapa=new Mapa.Mapa1("MapaN1.tmx");
        //Creamos personaje principal
        miwa=new Miwa(texturaMiwa);
        miwa.getSprite().setPosition(ANCHO/5,ALTO/5.333f); //Posicion inicial de Miwa
        //Escena con "Listeners"
        Gdx.input.setInputProcessor(this);
        batch=new SpriteBatch();
        //Estado Juego
        estadosJuego=EstadosJuego.INTRO;
        //Contador para vida Extra
        gemaVida=new GemaVida(texturaGema);
        gemaVida.getSprite().setPosition(texturaGema.getWidth()/2,ALTO-texturaGema.getHeight()-ANCHO/80);
        //Iniciamos texto
        texto=new Texto("DominoFont.fnt");
        Predialogos=new Texture[]{Predial1,Predial2,Predial3,Predial4,Predial5,Predial6,Predial7,Predial8};
        Dialogos=new Texture[]{Dial1,Dial2};
        //SonidoGemas=Gdx.audio.newMusic(Gdx.files.internal("SonidoGemas.ogg"));
        //SonidoPicos=Gdx.audio.newMusic(Gdx.files.internal("SonidoPicos.aiff"));

    }
    //Crear los botones del menú principal
    private void crearBotones(){
        botonIzq=new Boton(texturaIzq);
        botonIzq.setPosicion(botonIzq.getX(),texturaIzq.getHeight()/2);
        botonIzq.setAlfa(0.6f);
        botonDer=new Boton(texturaDer);
        botonDer.setPosicion(ANCHO/1.1f,texturaDer.getHeight()/2);
        botonDer.setAlfa(0.6f);
        botonPausa=new Boton(texturaPausa);
        botonPausa.setPosicion(ANCHO-texturaPausa.getWidth()-ANCHO/64,ALTO-texturaPausa.getHeight()-ANCHO/64);
        botonPausa.setAlfa(0.8f);
        botonSaltar1=new Boton(texturaSaltar);
        botonSaltar1.setPosicion(botonSaltar1.getX(),texturaSaltar.getHeight()*1.5f);
        botonSaltar1.setAlfa(0.6f);
        botonSaltar2=new Boton(texturaSaltar);
        botonSaltar2.setPosicion(ANCHO/1.1f,texturaSaltar.getHeight()*1.5f);
        botonSaltar2.setAlfa(0.6f);
        botonReanudar=new Boton(texturaBotonReanudar);
        botonReanudar.setPosicion(ANCHO/2-texturaBotonReanudar.getWidth()/2,ALTO/2+texturaBotonReanudar.getHeight()/2);
        botonMenuInicial=new Boton(texturaBotonMenuInicial);
        botonMenuInicial.setPosicion(ANCHO/2-texturaBotonMenuInicial.getWidth()/2,ALTO/4+texturaBotonMenuInicial.getHeight()/2);
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
        camaraHUD.position.set(ANCHO/2, ALTO/2, 0);
        camaraHUD.update();
    }
    private void cargarTexturas() {
        //Texturas Dialogos
        assetManager.load("Dialogo_Nivel1_1.png",Texture.class);
        assetManager.load("Dialogo_Nivel1_2.png",Texture.class);
        assetManager.load("Dialogo_PreNivel1_1.png",Texture.class);
        assetManager.load("Dialogo_PreNivel1_2.png",Texture.class);
        assetManager.load("Dialogo_PreNivel1_3.png",Texture.class);
        assetManager.load("Dialogo_PreNivel1_4.png",Texture.class);
        assetManager.load("Dialogo_PreNivel1_5.png",Texture.class);
        assetManager.load("Dialogo_PreNivel1_6.png",Texture.class);
        assetManager.load("Dialogo_PreNivel1_7.png",Texture.class);
        assetManager.load("Dialogo_PreNivel1_8.png",Texture.class);
        //Musica
        assetManager.load("SonidoGemas.ogg",Music.class);
        assetManager.load("SonidoPicos.ogg",Music.class);
        assetManager.load("MusicaDialogoInicioNivel1.ogg",Music.class);
        assetManager.load("MusicaDialogoFinalNivel1.ogg",Music.class);
        //Textura Miwa
        assetManager.load("miwa.png",Texture.class);
        //Textura Vida
        assetManager.load("Vida.png",Texture.class);
        assetManager.load("GemaContador.png",Texture.class);
        //Texturas de Boton
        assetManager.load("pausa.png",Texture.class);
        assetManager.load("izquierda.png",Texture.class);
        assetManager.load("derecha.png",Texture.class);
        assetManager.load("salto.png",Texture.class);
        //Texturas Menu Pausa
        assetManager.load("Pantalla_Pausa.png", Texture.class);
        assetManager.load("Menu_Inicial.png", Texture.class);
        assetManager.load("Reanudar.png", Texture.class);
        assetManager.load("Pantalla_Perder.png",Texture.class);
        //Se bloquea hasta cargar los recursos
        assetManager.finishLoading();
        //Textura Vida
        texturaVida=assetManager.get("Vida.png");
        texturaGema=assetManager.get("GemaContador.png");
        //Texturas botones
        texturaPausa=assetManager.get("pausa.png");
        texturaDer=assetManager.get("derecha.png");
        texturaIzq=assetManager.get("izquierda.png");
        texturaSaltar=assetManager.get("salto.png");
        //Textura Miwa
        texturaMiwa=assetManager.get("miwa.png");
        //Texturas Menu Pausa
        texturaMenuPausa = assetManager.get("Pantalla_Pausa.png");
        texturaBotonReanudar = assetManager.get("Reanudar.png");
        texturaBotonMenuInicial = assetManager.get("Menu_Inicial.png");
        //Texturas Dialogos
        Dial1=assetManager.get("Dialogo_Nivel1_1.png");
        Dial2=assetManager.get("Dialogo_Nivel1_2.png");
        Predial1=assetManager.get("Dialogo_PreNivel1_1.png");
        Predial2=assetManager.get("Dialogo_PreNivel1_2.png");
        Predial3=assetManager.get("Dialogo_PreNivel1_3.png");
        Predial4=assetManager.get("Dialogo_PreNivel1_4.png");
        Predial5=assetManager.get("Dialogo_PreNivel1_5.png");
        Predial6=assetManager.get("Dialogo_PreNivel1_6.png");
        Predial7=assetManager.get("Dialogo_PreNivel1_7.png");
        Predial8=assetManager.get("Dialogo_PreNivel1_8.png");
        //Musica
        SonidoPicos=assetManager.get("SonidoPicos.ogg");
        SonidoGemas=assetManager.get("SonidoGemas.ogg");
        SonidoPre=assetManager.get("MusicaDialogoInicioNivel1.ogg");
        SonidoDial=assetManager.get("MusicaDialogoFinalNivel1.ogg");
        SonidoDial.setLooping(true);
        SonidoPre.setLooping(true);
    }

    private void actualizarCamara() {
        float posY=miwa.getY();
        float posX = miwa.getX();
        // Si está en la parte 'media'
        if (posX>=ANCHO/2 && posX<=ANCHO_MAPA-ANCHO/2) {
            // El personaje define el centro de la cámara
            camara.position.set((int)posX, camara.position.y, 0);
            if(posY>=ALTO/2 &&posY<=ALTO_MAPA-ALTO/2){
                camara.position.set(camara.position.x,(int)posY,0);
            }
        } else if (posX>=ANCHO_MAPA-ANCHO/2) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camara.position.set(ANCHO_MAPA-ANCHO/2, camara.position.y, 0);
            if(posY>=ALTO/2 &&posY<=ALTO_MAPA-ALTO/2){
                camara.position.set(camara.position.x,(int)posY,0);
            }
        }else if(posY>=ALTO/2 &&posY<=ALTO_MAPA-ALTO/2)
            camara.position.set(camara.position.x,(int)posY,0);
        camara.update();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        consultarEstado();
        if (estadosJuego == EstadosJuego.INTRO) {
            if(conPre<8) {
                SonidoPre.play();
                batch.setProjectionMatrix(camara.combined);
                batch.begin();
                batch.draw(Predialogos[conPre], 0, 0);
                batch.end();
            }
            else{
                tiempoDialogo-=Gdx.graphics.getDeltaTime();
                if(tiempoDialogo<=0){
                    SonidoPre.stop();
                    estadosJuego=EstadosJuego.JUGANDO;
                    tiempoDialogo=1;
                }
            }
        } else if (estadosJuego == EstadosJuego.GANO) {
            if(conDial<2) {
                SonidoDial.play();
                batch.begin();
                batch.draw(texturaMiwa,0,0);
                batch.end();
            }
            else{
                tiempoDialogo-=Gdx.graphics.getDeltaTime();
                if(tiempoDialogo<=0){
                    SonidoDial.stop();
                    MisionKitsune.nivel=2;
                    misionKitsune.setScreen(new MenuMapas(misionKitsune));
                    tiempoDialogo=1;
                    MisionKitsune.musicaFondo.play();
                }
            }

        } else {
            if (contadorGemas >= 3) {
                tiempoGemas -= Gdx.graphics.getDeltaTime();
                if (tiempoGemas <= 0) {
                    contadorGemas = 0;
                    gemaVida.setGemas(contadorGemas);
                    tiempoGemas = 2;
                    miwa.setVidas(miwa.getVidas() + 1);
                }
            }
            if (estadosJuego == EstadosJuego.INVENCIBLE) {
                tiempoInvencible -= Gdx.graphics.getDeltaTime();
                if (tiempoInvencible <= 0) {
                    estadosJuego = EstadosJuego.JUGANDO;
                    tiempoInvencible = 3;
                }
            }
            actualizarCamara();


            if (miwa.getY() + texturaMiwa.getHeight() <= 0) {
                miwa.getSprite().setPosition(ANCHO / 5, ALTO / 5.333f);
                camara.position.set(ANCHO / 2, ALTO / 2, 0);
                miwa.setVidas(miwa.getVidas() - 1);
            }
            //Camara principal
            batch.setProjectionMatrix(camara.combined);
            mapa.render(batch, camara);


            //Dependencia de la camara con todos los botones
            batch.setProjectionMatrix(camaraHUD.combined);
            batch.begin();
            if (estadosJuego == EstadosJuego.PAUSADO) {
                batch.draw(texturaMenuPausa, ANCHO / 2 - texturaMenuPausa.getWidth() / 2, ALTO / 2 - texturaMenuPausa.getHeight() / 2);
                botonReanudar.render(batch);
                botonMenuInicial.render(batch);
            }
            if (miwa.getVidas() <= 0) {
                estadosJuego = EstadosJuego.PERDIO;
                misionKitsune.setScreen(new FinJuego(misionKitsune, new Texture("fondoNivel1.png")));
            }

            gemaVida.render(batch);
            botonDer.render(batch);
            botonIzq.render(batch);
            batch.draw(texturaVida, texturaVida.getWidth() / 8, ALTO - texturaVida.getHeight() - 16);
            botonSaltar1.render(batch);
            botonSaltar2.render(batch);
            botonPausa.render(batch);
            texto.mostrarMensaje(batch, "" + miwa.getVidas(), 126, 722);
            batch.end();
            batch.setProjectionMatrix(camara.combined);
            batch.begin();
            if (estadosJuego == EstadosJuego.JUGANDO || estadosJuego == EstadosJuego.INVENCIBLE) {
                miwa.render(batch);
            }
            batch.end();
            int celdaX = (int) ((miwa.getX() + miwa.getSprite().getWidth() / 2) / TAM_CELDA);
            int celdaY = (int) ((miwa.getY()) / TAM_CELDA);
            TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getMapa().getLayers().get(1);
            TiledMapTileLayer.Cell celda = capa.getCell(celdaX, celdaY);
            if (mapa.esPiso(celda)) {
                miwa.setVelocidadX(7);
            } else if (mapa.esHielo(celda)) {
                miwa.setVelocidadX(13);
            } else if (mapa.esPegajoso(celda)) {
                miwa.setVelocidadX(3);
            } else {
                miwa.caer();
                miwa.setVelocidadX(7);
            }
            TiledMapTileLayer capaGanar = (TiledMapTileLayer) mapa.getMapa().getLayers().get(5);
            TiledMapTileLayer.Cell celdaGanar = capaGanar.getCell(celdaX, celdaY);
            if(celdaGanar!=null)
            {
                estadosJuego=EstadosJuego.GANO;
            }
            TiledMapTileLayer capaGemas = (TiledMapTileLayer) mapa.getMapa().getLayers().get(3);
            TiledMapTileLayer.Cell celdaGema = capaGemas.getCell(celdaX, (int) (miwa.getY() + miwa.getSprite().getHeight() / 2) / TAM_CELDA);
            if (celdaGema != null) {
                contadorGemas += 1;
                gemaVida.setGemas(contadorGemas);
                SonidoGemas.play();
                capaGemas.setCell(celdaX, (int) (miwa.getY() + miwa.getSprite().getHeight() / 2) / TAM_CELDA, null);
            }
            if (estadosJuego != EstadosJuego.INVENCIBLE) {
                TiledMapTileLayer capaPicos = (TiledMapTileLayer) mapa.getMapa().getLayers().get(2);
                TiledMapTileLayer.Cell celdaPicos = capaPicos.getCell(celdaX, (int) (miwa.getY() + miwa.getSprite().getHeight() / 2) / TAM_CELDA);
                TiledMapTileLayer.Cell celdaMini = capaPicos.getCell(celdaX, (int) (miwa.getY() + miwa.getSprite().getHeight()) / TAM_CELDA);
                if (mapa.esPico(celdaPicos)) {
                    estadosJuego = EstadosJuego.INVENCIBLE;
                    miwa.setVidas(miwa.getVidas() - 1);
                    SonidoPicos.play();
                }
                if (mapa.esMiniPico(celdaMini)) {
                    estadosJuego = EstadosJuego.INVENCIBLE;
                    contadorGemas -= 1;
                    gemaVida.setGemas(contadorGemas);

                }
            }
        }
    }

    private void consultarEstado() {
        switch(estadosJuego){
            case JUGANDO:
                //Activa los botones para que puedan ser usados
                botonIzq.setDisabled(false);
                botonDer.setDisabled(false);
                botonPausa.setDisabled(false);
                botonSaltar1.setDisabled(false);
                botonSaltar2.setDisabled(false);
                botonReanudar.setDisabled(true);
                botonMenuInicial.setDisabled(true);
                break;
            case PAUSADO:
                //Desactiva los botones para que no puedan ser usados mientras está en pausa
                botonIzq.setDisabled(true);
                botonDer.setDisabled(true);
                botonPausa.setDisabled(true);
                botonSaltar1.setDisabled(true);
                botonSaltar2.setDisabled(true);
                botonReanudar.setDisabled(false);
                botonMenuInicial.setDisabled(false);
                break;
            case INTRO:
            case GANO:
                botonIzq.setDisabled(true);
                botonDer.setDisabled(true);
                botonPausa.setDisabled(true);
                botonSaltar1.setDisabled(true);
                botonSaltar2.setDisabled(true);
                botonReanudar.setDisabled(true);
                botonMenuInicial.setDisabled(true);
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
        texturaVida.dispose();
        texturaIzq.dispose();
        texturaDer.dispose();
        texturaPausa.dispose();
        texturaSaltar.dispose();
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
        if(estadosJuego==EstadosJuego.INTRO)
            if(x>0&&x<ANCHO&&y>0&&y<ALTO) {
                conPre++;
                Menu.sonidoBotones.play();
            }
        if(estadosJuego==EstadosJuego.GANO)
            if(x>0&&x<ANCHO&&y>0&&y<ALTO) {
                conDial++;
                Menu.sonidoBotones.play();
            }
        if (botonIzq.contiene(x, y)) {
            miwa.setEstadoMovimiento(Miwa.Estados.IZQUIERDA);
        } else if (botonDer.contiene(x, y)) {
            miwa.setEstadoMovimiento(Miwa.Estados.DERECHA);
        } else if (botonSaltar1.contiene(x, y) || botonSaltar2.contiene(x, y)) {
            miwa.setEstadoMovimiento(Miwa.Estados.SUBIENDO);
        } else if (botonPausa.contiene(x, y)) {

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
        else if(botonSaltar1.contiene(x,y)||botonSaltar2.contiene(x,y)){


        }
        else if(botonPausa.contiene(x,y)){
            Menu.sonidoBotones.play();
            this.estadosJuego = EstadosJuego.PAUSADO;
        }
        else if (botonReanudar.contiene(x,y)){
            Menu.sonidoBotones.play();
            estadosJuego= EstadosJuego.JUGANDO;
        }
        else if (botonMenuInicial.contiene(x,y)){
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
    public enum EstadosJuego {
        GANO,
        INTRO,
        JUGANDO,
        PAUSADO,
        PERDIO,
        INVENCIBLE
    }
}
