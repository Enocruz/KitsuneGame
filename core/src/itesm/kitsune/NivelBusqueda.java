package itesm.kitsune;

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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
            Predial1,Predial2,Predial3,Predial4,Predial5,Predial6,Predial7,Predial8,texturaSkip,textSonido;
    private Texture[] Dialogos,Predialogos;
    private TextureRegion texturaSonido;
    private TextureRegion[] texturaBtnSonido;
    private TextureRegion[][] texbtnson;

    //Botones pantalla
    private Boton botonIzq,botonDer,botonSaltar1,botonSaltar2,botonPausa,botonReanudar,botonMenuInicial,botonSkip,botonSonido;
    //AssetManager (Cargar texturas)
    private  AssetManager assetManager;
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
    private Mapa mapa;
    //Tamaño celdas TileMap
    public static final int TAM_CELDA = 32;
    //Estados Juego
    public static EstadosJuego estadosJuego;
    //Contador para vidas
    private GemaVida gemaVida;
    //Texto vidas
    private Texto texto;
    //Contador gemas para vida extra
    private int contadorGemas=0,conPre=0,conDial=0,nivel;
    private float tiempoInvencible=2,tiempoGemas=2;
    private Music SonidoGemas,SonidoPicos,SonidoPre,SonidoDial,SonidoJuego;

    public NivelBusqueda(MisionKitsune misionKitsune, EstadosJuego estado,int nivel) {
        this.estadosJuego=estado;
        this.misionKitsune = misionKitsune;
        this.nivel=nivel;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        texturaBtnSonido = new TextureRegion[2];
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
        miwa.getSprite().setPosition(ANCHO/5,ALTO/5.333f); //Posicion inicial de Miwa
        //Escena con "Listeners"

        batch=new SpriteBatch();
        //Contador para vida Extra
        gemaVida=new GemaVida(texturaGema);
        gemaVida.getSprite().setPosition(texturaGema.getWidth()/2,ALTO-texturaGema.getHeight()-ANCHO/80);
        //Iniciamos texto3
        texto=new Texto("DominoFont.fnt");
        Predialogos=new Texture[]{Predial1,Predial2,Predial3,Predial4,Predial5,Predial6,Predial7,Predial8};
        Dialogos=new Texture[]{Dial1,Dial2};
        misionKitsune.getMusicaFondo().stop();

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
        botonSkip=new Boton(texturaSkip);
        botonSkip.setPosicion(ANCHO-texturaSkip.getWidth()*1.5f,ALTO-texturaSkip.getHeight()*1.5f);
        botonSonido=new Boton(texturaBtnSonido[0].getTexture());
        botonSonido.setPosicion(ANCHO/2-botonSonido.getWidth()/2,ALTO-botonSonido.getHeight());
        if (misionKitsune.isMudo()){
            botonSonido.setTexture(texturaBtnSonido[1]);
        }else{
            botonSonido.setTexture(texturaBtnSonido[0]);
        }
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
        assetManager=misionKitsune.getAssetManager();

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
        texturaSkip=assetManager.get("Skip.png");
        //Sonido
        assetManager.load("sonido.png",Texture.class);
        assetManager.finishLoading();
        textSonido=assetManager.get("sonido.png");
        texturaSonido = new TextureRegion(textSonido);
        texbtnson = texturaSonido.split(texturaSonido.getRegionWidth()/2,textSonido.getHeight());
        for (int i = 0; i<2;i++){
            System.out.println("wytfrgvh");
            texturaBtnSonido [i] = texbtnson[0][i];
        }
        //Texturas Dialogos
        Dial1=assetManager.get("Dialogo_Nivel1_1.jpg");
        Dial2=assetManager.get("Dialogo_Nivel1_2.jpg");
        Predial1=assetManager.get("Dialogo_PreNivel1_1.jpg");
        Predial2=assetManager.get("Dialogo_PreNivel1_2.jpg");
        Predial3=assetManager.get("Dialogo_PreNivel1_3.jpg");
        Predial4=assetManager.get("Dialogo_PreNivel1_4.jpg");
        Predial5=assetManager.get("Dialogo_PreNivel1_5.jpg");
        Predial6=assetManager.get("Dialogo_PreNivel1_6.jpg");
        Predial7=assetManager.get("Dialogo_PreNivel1_7.jpg");
        Predial8=assetManager.get("Dialogo_PreNivel1_8.jpg");
        //Musica
        SonidoPicos=assetManager.get("SonidoPicos.mp3");
        SonidoGemas=assetManager.get("SonidoGemas.mp3");
        SonidoGemas.setVolume(1);
        SonidoPre=assetManager.get("MusicaDialogoInicioNivel1.mp3");
        SonidoPre.setVolume(0.6f);
        SonidoDial=assetManager.get("MusicaDialogoFinalNivel1.mp3");
        SonidoJuego = assetManager.get("MusicaJuegoN1.mp3");
        SonidoDial.setLooping(true);
        SonidoPre.setLooping(true);
        SonidoJuego.setLooping(true);
        SonidoJuego.setVolume(0.5f);
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
        actualizarCamara();
        if (misionKitsune.isMudo()){
            mute();
        }else{
            unmute();
        }

    }


    private void consultarEstado() {

        switch (estadosJuego) {
            case JUGANDO:
            case INVENCIBLE:
            case PAUSADO:
                if(contadorGemas<=0)
                    contadorGemas=0;
                SonidoJuego.play();
                if (estadosJuego == EstadosJuego.INVENCIBLE) {
                    tiempoInvencible -= Gdx.graphics.getDeltaTime();
                    if(tiempoInvencible%0.5<0.25)
                        miwa.getSprite().setAlpha(0.7f);
                    else if(tiempoInvencible%0.5>=0.25)
                        miwa.getSprite().setAlpha(0.9f);
                    if (tiempoInvencible <= 0) {
                        estadosJuego = EstadosJuego.JUGANDO;
                        tiempoInvencible = 2;
                        miwa.getSprite().setAlpha(1);
                    }
                }
                if (miwa.getY() + texturaMiwa.getHeight() <= 0) {
                    miwa.getSprite().setPosition(ANCHO / 5, ALTO / 5.333f);
                    camara.position.set(ANCHO / 2, ALTO / 2, 0);
                    miwa.setVidas(miwa.getVidas() - 1);
                    miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
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
                    if (misionKitsune.isMudo()){
                        botonSonido.setTexture(texturaBtnSonido[1]);
                    }else{
                        botonSonido.setTexture(texturaBtnSonido[0]);
                    }
                    botonSonido.render(batch);
                        //Desactiva los botones para que no puedan ser usados mientras está en pausa
                    botonIzq.setDisabled(true);
                    botonDer.setDisabled(true);
                    botonPausa.setDisabled(true);
                    botonSaltar1.setDisabled(true);
                    botonSaltar2.setDisabled(true);
                    botonReanudar.setDisabled(false);
                    botonMenuInicial.setDisabled(false);
                    botonSonido.setDisabled(false);
                }
                else{
                    botonIzq.setDisabled(false);
                    botonDer.setDisabled(false);
                    botonPausa.setDisabled(false);
                    botonSaltar1.setDisabled(false);
                    botonSaltar2.setDisabled(false);
                    botonReanudar.setDisabled(true);
                    botonMenuInicial.setDisabled(true);
                    botonSonido.setDisabled(true);

                }
                if (miwa.getVidas() <= 0) {
                    SonidoJuego.stop();
                    estadosJuego = EstadosJuego.PERDIO;
                    misionKitsune.setScreen(new FinJuego(misionKitsune, new Texture("fondoNivel1.png"),1));
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
                    if(miwa.getEstadosSalto()!= Miwa.EstadosSalto.SUBIENDO)
                        miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
                } else if (mapa.esHielo(celda)) {
                    if(miwa.getEstadosSalto()!= Miwa.EstadosSalto.SUBIENDO)
                        miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
                    miwa.setVelocidadX(13);
                } else if (mapa.esPegajoso(celda)) {
                    if(miwa.getEstadosSalto()!= Miwa.EstadosSalto.SUBIENDO)
                    miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
                    miwa.setVelocidadX(3);
                } else {
                    if(miwa.getEstadosSalto()!= Miwa.EstadosSalto.SUBIENDO)
                        miwa.setEstadoSalto(Miwa.EstadosSalto.BAJANDO);
                    miwa.setVelocidadX(6);

                }

                TiledMapTileLayer capaGanar = (TiledMapTileLayer) mapa.getMapa().getLayers().get(5);
                TiledMapTileLayer.Cell celdaGanar = capaGanar.getCell(celdaX, celdaY);
                if (celdaGanar != null) {
                    SonidoJuego.stop();
                    estadosJuego = EstadosJuego.GANO;
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
                    else  if (mapa.esMiniPico(celdaMini)) {
                        estadosJuego = EstadosJuego.INVENCIBLE;
                        contadorGemas -= 1;
                        gemaVida.setGemas(contadorGemas);
                    }
                    if (contadorGemas >= 3) {
                        tiempoGemas -= Gdx.graphics.getDeltaTime();
                        if(tiempoGemas%0.4>0.2)
                            gemaVida.getSprite().setAlpha(0.5f);
                        if(tiempoGemas%0.4<=0.2)
                            gemaVida.getSprite().setAlpha(1);
                        if (tiempoGemas <= 0) {
                            gemaVida.getSprite().setAlpha(1);
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
                }
                break;
            case INTRO:
                if (conPre < Predialogos.length) {
                    batch.setProjectionMatrix(camara.combined);
                    batch.begin();
                    batch.draw(Predialogos[conPre], 0, 0);
                    if(misionKitsune.getNivel()!=1){
                        botonSkip.render(batch);
                    }
                    batch.end();
                    SonidoPre.play();
                }
                else{
                    SonidoPre.stop();
                    estadosJuego=EstadosJuego.JUGANDO;
                }
                botonIzq.setDisabled(true);
                botonDer.setDisabled(true);
                botonPausa.setDisabled(true);
                botonSaltar1.setDisabled(true);
                botonSaltar2.setDisabled(true);
                botonReanudar.setDisabled(true);
                botonMenuInicial.setDisabled(true);
                break;
            case GANO:
                camara.setToOrtho(false,ANCHO,ALTO);
                if (conDial < Dialogos.length) {
                    SonidoDial.play();
                    batch.setProjectionMatrix(camara.combined);
                    batch.begin();
                    batch.draw(Dialogos[conDial], 0, 0);
                    if(misionKitsune.getNivel()!=1){
                        botonSkip.render(batch);
                    }
                    batch.end();
                }
                else{
                    SonidoDial.stop();
                    if(misionKitsune.getNivel()==1)
                        misionKitsune.setNivel(2);
                    misionKitsune.setScreen(new MenuMapas(misionKitsune));
                    misionKitsune.getMusicaFondo().play();
                }
                botonIzq.setDisabled(true);
                botonDer.setDisabled(true);
                botonPausa.setDisabled(true);
                botonSaltar1.setDisabled(true);
                botonSaltar2.setDisabled(true);
                botonReanudar.setDisabled(true);
                botonMenuInicial.setDisabled(true);
                botonSonido.setDisabled(true);

                break;
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
        texturaBotonReanudar.dispose();
        texturaMiwa.dispose();
        texturaGema.dispose();
        texturaBotonMenuInicial.dispose();
        texturaMenuPausa.dispose();
        texturaSkip.dispose();
        textSonido.dispose();
        Predial1.dispose();
        Predial5.dispose();
        Predial3.dispose();
        Predial2.dispose();
        Predial4.dispose();
        Predial6.dispose();
        Predial7.dispose();
        Predial8.dispose();
        Dial2.dispose();
        Dial1.dispose();
        SonidoPicos.dispose();
        SonidoPre.dispose();
        SonidoDial.dispose();
        SonidoJuego.dispose();
        SonidoGemas.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            misionKitsune.getMusicaFondo().play();
            SonidoJuego.stop();
            SonidoPre.stop();
            SonidoDial.stop();
            if(estadosJuego==EstadosJuego.GANO){
                if(misionKitsune.getNivel()==1)
                    misionKitsune.setNivel(2);
            }

            misionKitsune.setScreen(new MenuMapas(misionKitsune));
        }
        if(keycode==(Input.Keys.DPAD_LEFT))
            miwa.setEstadoMovimiento(Miwa.Estados.IZQUIERDA);
        else if(keycode==(Input.Keys.DPAD_RIGHT))
            miwa.setEstadoMovimiento(Miwa.Estados.DERECHA);
        else if(keycode==(Input.Keys.DPAD_UP))
            miwa.setEstadoSalto(Miwa.EstadosSalto.SUBIENDO);

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode==(Input.Keys.DPAD_LEFT)||keycode==Input.Keys.DPAD_RIGHT)
            miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
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
            if (x > 0 && x < ANCHO-texturaSkip.getWidth()/2&& y > 0 && y < ALTO-texturaSkip.getHeight()/2) {
                conPre++;
                misionKitsune.getSonidoBotones().play();
            }

        if(estadosJuego==EstadosJuego.GANO)
            if (x > 0 && x < ANCHO && y > 0 && y < ALTO) {
                conDial++;
                misionKitsune.getSonidoBotones().play();
            }

        if(botonIzq.contiene(x,y)){
            miwa.setEstadoMovimiento(Miwa.Estados.IZQUIERDA);
        }
        else if(botonIzq.contiene(x,y)&&pointer==0&&botonSaltar2.contiene(x,y)&&pointer==1){
            if (miwa.getEstadosSalto() != Miwa.EstadosSalto.BAJANDO) {
                miwa.setEstadoSalto(Miwa.EstadosSalto.SUBIENDO) ;
                miwa.setEstadoMovimiento(Miwa.Estados.IZQUIERDA);
            }
        }
        else if(botonDer.contiene(x,y)&&pointer==1&&botonSaltar2.contiene(x,y)&&pointer==0){
            if (miwa.getEstadosSalto() != Miwa.EstadosSalto.BAJANDO) {
                miwa.setEstadoSalto(Miwa.EstadosSalto.SUBIENDO) ;
                miwa.setEstadoMovimiento(Miwa.Estados.IZQUIERDA);
            }
        }
        else if(botonDer.contiene(x,y)){
            miwa.setEstadoMovimiento(Miwa.Estados.DERECHA);
        }
        else if(botonSaltar1.contiene(x,y)||botonSaltar2.contiene(x,y)) {
            if (miwa.getEstadosSalto() != Miwa.EstadosSalto.BAJANDO)
            miwa.setEstadoSalto(Miwa.EstadosSalto.SUBIENDO) ;
        }
        return true;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 v=new Vector3(screenX,screenY,0);
        camaraHUD.unproject(v);
        float x=v.x,y=v.y;
        if(botonIzq.contiene(x,y)){
            if (miwa.getEstadosSalto() == Miwa.EstadosSalto.EN_PISO||
                    miwa.getEstadosSalto() == Miwa.EstadosSalto.BAJANDO )
                miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
        }
        else if(botonDer.contiene(x,y)){
            if (miwa.getEstadosSalto() == Miwa.EstadosSalto.EN_PISO||
                    miwa.getEstadosSalto() == Miwa.EstadosSalto.BAJANDO )
                miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
        }
         if(botonPausa.contiene(x,y)){
             misionKitsune.getSonidoBotones().play();
            this.estadosJuego = EstadosJuego.PAUSADO;
        }
        else if (botonReanudar.contiene(x,y)){
             misionKitsune.getSonidoBotones().play();
            estadosJuego= EstadosJuego.JUGANDO;
        }
        else if (botonMenuInicial.contiene(x,y)){
            SonidoJuego.stop();
            misionKitsune.getMusicaFondo().play();
            misionKitsune.getSonidoBotones().play();
            misionKitsune.setScreen(new Menu(misionKitsune));
        }
        else if(botonSkip.contiene(x,y)&&estadosJuego==EstadosJuego.GANO){
            SonidoDial.stop();
            misionKitsune.getMusicaFondo().play();
            if(misionKitsune.getNivel()==1)
                misionKitsune.setNivel(2);
            misionKitsune.setScreen(new MenuMapas(misionKitsune));
        }
        else if(botonSkip.contiene(x,y)&&estadosJuego==EstadosJuego.INTRO){
            SonidoPre.stop();
            estadosJuego=EstadosJuego.JUGANDO;
        }else if(botonSonido.contiene(x,y)){
             if (misionKitsune.isMudo()){
                 misionKitsune.setMudo(false);
             }else{
                 misionKitsune.setMudo(true);
             }
         }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 v = new Vector3(screenX, screenY, 0);
        camaraHUD.unproject(v);
        float x = v.x, y = v.y;
        if (botonIzq.contiene(x, y)&&pointer==0) {
            if (miwa.getEstadosSalto() == Miwa.EstadosSalto.EN_PISO ||
                    miwa.getEstadosSalto() == Miwa.EstadosSalto.BAJANDO)
                miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
        } else if (botonDer.contiene(x, y)&&pointer==0) {
            if (miwa.getEstadosSalto() == Miwa.EstadosSalto.EN_PISO ||
                    miwa.getEstadosSalto() == Miwa.EstadosSalto.BAJANDO)
                miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
        }
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
        SonidoGemas.setVolume(0);
        SonidoPicos.setVolume(0);
        SonidoPre.setVolume(0);
        SonidoDial.setVolume(0);
        SonidoJuego.setVolume(0);
    }
    private void unmute(){
        SonidoGemas.setVolume(1);
        SonidoPicos.setVolume(1);
        SonidoPre.setVolume(0.6f);
        SonidoDial.setVolume(1);
        SonidoJuego.setVolume(0.5f);
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
