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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Random;


/**
 * Created by b-and on 28/10/2016.
 */

public class NivelPersecucion implements Screen,InputProcessor {
    private MisionKitsune misionKitsune;
    private NaveMiwa naveMiwa;
    private Music musicaIntro,musicaFondo,naveSonido,sonidoGemas,sonidoRoca;
    private OrthographicCamera camara, camaraHUD, camaraDialogos;
    private Viewport vista;
    public static final int ANCHO = 1280, ALTO = 800;
    private SpriteBatch batch;
    private Texture texturaFondo, texturaNaveEnemiga, texturaMiwaCentro, texturaMiwaDerecha, texturaMiwaIzquierda,
            texturaPausa, texNebAzul, texNebRoja, texturaMenuInicial, texturaReanudar,
            texturaPiedra, texturaPiedrita, texturaVida, texturaBarra, texturaMiniNave, texturaGemaVida, texturaGema,
            texPreNiv, texPreNiv2, texPreNiv3, texPreNiv4, texNiv, texNiv2, texNiv3, texNiv4, texNiv5, texturaSkip,textSonido,
            texturaFin;
    private TextureRegion texturaSonido;
    private TextureRegion[] texturaBtnSonido;
    private TextureRegion[][] texbtnson;
    private AssetManager assetManager;
    private int y = 0, yNave, xGema, velXGema, nivel,xNave,velxNave;
    private Piedra piedra, piedrita;
    private Array<Piedra> piedras, piedritas;
    private float accelX,velyNave,yNaveEnemiga;
    public static EstadosPersecucion estadosJuego;
    private Boton botonPausa, botonMenu, botonReanudar, botonSkip,botonSonido;
    private Random rnd;
    private GemaVida gemaVida;
    private Gemas gemas;
    private Texto texto;
    private int contadorGemas, conPre = 0, conDial = 0;
    private float tiempoInvencible, tiempoNivel, tiempoFinal, tiempoGemas, tiempoInvencibleG;
    private Texture[] Predialogos, Dialogos;

    NivelPersecucion(MisionKitsune misionKitsune, EstadosPersecucion estado, int nivel) {
        this.estadosJuego = estado;
        this.misionKitsune = misionKitsune;
        this.nivel = nivel;
    }

    @Override
    public void show() {
        tiempoInvencible = 2;
        rnd = new Random();
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        assetManager = misionKitsune.getAssetManager();
        texturaBtnSonido = new TextureRegion[2];
        inicializarCamara();
        cargarTexturas();
        inicializarBotones();
        naveMiwa = new NaveMiwa(texturaMiwaCentro, texturaMiwaDerecha, texturaMiwaIzquierda);
        naveMiwa.getSprite().setPosition(ANCHO / 2, 0);
        piedras = new Array<Piedra>(3);
        piedritas = new Array<Piedra>(3);
        gemas = new Gemas(texturaGema, ANCHO, ALTO);
        gemaVida = new GemaVida(texturaGemaVida);
        gemas.getSprite().setY(ALTO);
        texto = new Texto("DominoFont.fnt");
        contadorGemas = 0;
        gemaVida.getSprite().setPosition(texturaGemaVida.getWidth() / 2, ALTO - texturaGemaVida.getHeight() - ANCHO / 80);
        for (int i = 0; i < 3; i++) {
            piedra = new Piedra(texturaPiedra, (350 * (i + 1)) - texturaPiedra.getWidth() / 2, ALTO + texturaPiedrita.getHeight());
            piedras.add(piedra);
        }
        for (int i = 0; i < 3; i++) {
            piedrita = new Piedra(texturaPiedrita, 250 * (i + 1), ALTO + texturaPiedrita.getHeight());
            piedritas.add(piedrita);
        }
        xGema = 0;
        tiempoGemas = 1;
        velXGema = 2;
        yNave = ALTO / 2 - texturaBarra.getHeight() / 2 - texturaMiniNave.getHeight() / 2;
        tiempoNivel = 1;
        tiempoFinal = 0;
        tiempoInvencibleG = 0.2f;
        xNave=ANCHO/2;
        yNaveEnemiga=ALTO-texturaNaveEnemiga.getHeight();
        velyNave=0;
        velxNave=3;
        Predialogos = new Texture[]{texPreNiv, texPreNiv2, texPreNiv3};
        Dialogos = new Texture[]{texNiv, texNiv2, texNiv3, texNiv4, texNiv5};
        musicaFondo.setVolume(0.5f);
        musicaFondo.setLooping(true);
        naveSonido.setLooping(true);
        naveSonido.setVolume(0.5f);
    }

    private void inicializarBotones() {
        botonPausa = new Boton(texturaPausa);
        botonPausa.setPosicion(ANCHO - texturaPausa.getWidth() * 1.5f, ALTO - texturaPausa.getHeight() * 1.5f);
        botonPausa.setAlfa(0.7f);
        botonPausa.setDisabled(true);
        botonMenu = new Boton(texturaMenuInicial);
        botonMenu.setPosicion(ANCHO / 2 - texturaMenuInicial.getWidth() / 2, ALTO / 2);
        botonMenu.setDisabled(true);
        botonReanudar = new Boton(texturaReanudar);
        botonReanudar.setPosicion(ANCHO / 2 - texturaReanudar.getWidth() / 2, ALTO / 3);
        botonReanudar.setDisabled(true);
        botonSkip = new Boton(texturaSkip);
        botonSkip.setPosicion(ANCHO - texturaSkip.getWidth() * 1.5f, ALTO - texturaSkip.getHeight() * 1.5f);
        botonSonido=new Boton(texturaBtnSonido[0].getTexture());
        botonSonido.setPosicion(ANCHO/2-botonSonido.getWidth()/2,ALTO-botonSonido.getHeight());
        if (misionKitsune.isMudo()){
            botonSonido.setTexture(texturaBtnSonido[1]);
        }else{
            botonSonido.setTexture(texturaBtnSonido[0]);
        }
    }

    private void cargarTexturas() {
        batch = new SpriteBatch();
        //Texturas Dialogos
        texturaSkip = assetManager.get("Skip.png");
        texPreNiv = assetManager.get("Dialogo_PreNivel2_1.jpg");
        texPreNiv2 = assetManager.get("Dialogo_PreNivel2_2.jpg");
        texPreNiv3 = assetManager.get("Dialogo_PreNivel2_3.jpg");
        texPreNiv4 = assetManager.get("Dialogo_PreNivel2_4.jpg");
        texNiv = assetManager.get("Dialogo_Nivel2_1.jpg");
        texNiv2 = assetManager.get("Dialogo_Nivel2_2.jpg");
        texNiv3 = assetManager.get("Dialogo_Nivel2_3.jpg");
        texNiv4 = assetManager.get("Dialogo_Nivel2_4.jpg");
        texNiv5 = assetManager.get("Dialogo_Nivel2_5.jpg");
        texturaReanudar = assetManager.get("N2Reanudar.png");
        texturaMenuInicial = assetManager.get("N2MenuInicialPausa.png");
        texturaFondo = assetManager.get("FondoEstrellas.png");
        texturaNaveEnemiga = assetManager.get("N2NaveEnemiga.png");
        texturaMiwaCentro = assetManager.get("N2NaveMiwa.png");
        texturaMiwaDerecha = assetManager.get("N2NaveMiwaDerecha.png");
        texturaMiwaIzquierda = assetManager.get("N2NaveMiwaIzquierda.png");
        texturaPausa = assetManager.get("N2Pausa.png");
        texNebAzul = assetManager.get("FondoNebulosaAzul.png");
        texNebRoja = assetManager.get("FondoNebulosaRoja.png");
        texturaPiedra = assetManager.get("N2Piedra.png");
        texturaPiedrita = assetManager.get("N2Piedritas.png");
        texturaVida = assetManager.get("N2Vida.png");
        texturaMiniNave = assetManager.get("N2IconoNave.png");
        texturaBarra = assetManager.get("Barra.png");
        texturaGemaVida = assetManager.get("N2ContadorGemas.png");
        texturaGema = assetManager.get("N2Gema.png");
        musicaIntro = assetManager.get("MusicaDialogoFinalNivel1.mp3");
        musicaFondo=assetManager.get("FondoPersecucion.mp3");
        naveSonido=assetManager.get("naveSonido.wav");
        sonidoGemas=assetManager.get("SonidoGemas.mp3");
        sonidoRoca=assetManager.get("blast.mp3");
        sonidoRoca.setVolume(0.6f);
        assetManager.load("sonido.png",Texture.class);
        assetManager.finishLoading();
        textSonido =assetManager.get("sonido.png");
        textSonido=assetManager.get("sonido.png");
        texturaFin=assetManager.get("Alerta.png");
        texturaSonido = new TextureRegion(textSonido);
        texbtnson = texturaSonido.split(texturaSonido.getRegionWidth()/2,textSonido.getHeight());
        for (int i = 0; i<2;i++){
            texturaBtnSonido [i] = texbtnson[0][i];
        }
    }


    private void inicializarCamara() {
        //Creamos la camara principal del nivel
        camara = new OrthographicCamera(ALTO, ANCHO);
        camara.position.set(ANCHO / 2, ALTO / 2, 0);
        camara.rotate(90);
        camara.update();
        //Creamos la vista
        vista = new StretchViewport(ALTO, ANCHO, camara);
        //Creamos la camara con los botones
        camaraHUD = new OrthographicCamera(ALTO, ANCHO);
        camaraHUD.position.set(ANCHO / 2, ALTO / 2, 0);
        camaraHUD.rotate(90);
        camaraHUD.update();
        camaraDialogos = new OrthographicCamera(ANCHO, ALTO);
        camaraDialogos.position.set(ANCHO / 2, ALTO / 2, 0);
        camaraDialogos.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        consultarEstado();
        if (misionKitsune.isMudo()){
            mute();
        }else{
            unmute();
        }
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
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
        textSonido.dispose();
        texturaReanudar.dispose();
        texturaMenuInicial.dispose();
        texturaFondo.dispose();
        texturaNaveEnemiga.dispose();
        texturaMiwaCentro.dispose();
        texturaMiwaDerecha.dispose();
        texturaMiwaIzquierda.dispose();
        texturaPausa.dispose();
        texNebAzul.dispose();
        texNebRoja.dispose();
        texturaPiedra.dispose();
        texturaPiedrita.dispose();
        texturaVida.dispose();
        texturaMiniNave.dispose();
        texturaBarra.dispose();
        texturaGemaVida.dispose();
        texturaGema.dispose();
        musicaFondo.dispose();
        musicaIntro.dispose();
        sonidoGemas.dispose();
        texturaSkip.dispose();
        naveSonido.dispose();
        sonidoRoca.dispose();
    }

    private void consultarEstado() {
        switch (estadosJuego) {
            case JUGANDO:
            case PAUSADO:
            case INVENCIBLE:
            case INVENCIBLEG:
                musicaFondo.play();
                naveMiwa.setAlfa(1);
                if (contadorGemas <= 0)
                    contadorGemas = 0;
                texturaFondo.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
                texNebAzul.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
                texNebRoja.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
                texNebRoja.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.Repeat);
                batch.setProjectionMatrix(camara.combined);
                batch.begin();
                batch.draw(texturaFondo, 0, 0, 0, y, ANCHO, ALTO);
                batch.draw(texNebAzul, ANCHO / 2, 0, 0, y, ANCHO, ALTO);
                batch.draw(texNebRoja, 0, 0, 0, y, ANCHO, ALTO);
                batch.end();
                if (estadosJuego == EstadosPersecucion.PAUSADO) {
                    naveSonido.stop();
                    batch.setProjectionMatrix(camaraHUD.combined);
                    botonMenu.setDisabled(false);
                    botonReanudar.setDisabled(false);
                    botonPausa.setDisabled(true);
                    botonSonido.setDisabled(false);
                    batch.begin();
                    botonMenu.render(batch);
                    botonReanudar.render(batch);
                    if (misionKitsune.isMudo()){
                        botonSonido.setTexture(texturaBtnSonido[1]);
                    }else{
                        botonSonido.setTexture(texturaBtnSonido[0]);
                    }
                    botonSonido.render(batch);
                    batch.end();
                } else {
                    naveSonido.play();
                    moverGema();
                    moverNave();
                    tiempoNivel -= Gdx.graphics.getDeltaTime();
                    if (tiempoNivel <= 0) {
                        yNave += 5;//7.6p * segundos
                        tiempoNivel = 1;
                        tiempoFinal++;
                    }
                    if (tiempoFinal >=90 )//Cambiar el tiempo
                        estadosJuego = EstadosPersecucion.ESPERAFIN;

                    if (estadosJuego == EstadosPersecucion.INVENCIBLE) {
                        tiempoInvencible -= Gdx.graphics.getDeltaTime();
                        if (tiempoInvencible % 0.5 < 0.25)
                            naveMiwa.setAlfa(0.7f);
                        else if (tiempoInvencible % 0.5 >= 0.25)
                            naveMiwa.setAlfa(1);

                        if (tiempoInvencible <= 0) {
                            tiempoInvencible = 2;
                            estadosJuego = EstadosPersecucion.JUGANDO;
                        }
                    }
                    if (estadosJuego == EstadosPersecucion.INVENCIBLEG) {
                        tiempoInvencibleG -= Gdx.graphics.getDeltaTime();
                        if (tiempoInvencibleG <= 0) {
                            estadosJuego = estadosJuego.JUGANDO;
                            tiempoInvencibleG = 0.2f;

                        }
                    }
                    if (naveMiwa.getVidas() <= 0) {
                        naveSonido.stop();
                        sonidoRoca.stop();
                        musicaFondo.stop();
                        estadosJuego = EstadosPersecucion.PERDIO;
                        misionKitsune.setScreen(new FinJuego(misionKitsune, new Texture("FondoEstrellas.png"), 2));
                    }
                    for (Piedra p : piedritas)
                        if (p.getEstadosPiedra() == Piedra.EstadosPiedra.NUEVA)
                                p.getSprite().setX(naveMiwa.getX());
                    if (gemas.getEstadoGemas() == Gemas.EstadoGema.NUEVA)
                        gemas.getSprite().setY(ALTO);
                    if (rnd.nextInt(100) >= 99)
                        gemas.getSprite().setX(xGema);
                    botonMenu.setDisabled(true);
                    botonReanudar.setDisabled(true);
                    botonSonido.setDisabled(true);
                    botonPausa.setDisabled(false);
                    batch.setProjectionMatrix(camaraHUD.combined);
                    batch.begin();
                    gemaVida.render(batch);
                    batch.draw(texturaNaveEnemiga, xNave, yNaveEnemiga);
                    naveMiwa.render(batch);
                    gemas.render(batch, xGema);
                    botonPausa.render(batch);
                    batch.draw(texturaBarra, ANCHO - texturaBarra.getWidth() * 3, ALTO / 2 - texturaBarra.getHeight() / 2);
                    batch.draw(texturaVida, texturaVida.getWidth() / 8, ALTO - texturaVida.getHeight() - 16);
                    texto.mostrarMensaje(batch, "" + naveMiwa.getVidas(), 126, 752);
                    batch.draw(texturaMiniNave, ANCHO - texturaBarra.getWidth() * 3 - texturaMiniNave.getWidth() / 4, yNave);
                    for (Piedra x : piedras)
                        x.render(batch, 7);
                    for (Piedra x : piedritas)
                        x.render(batch, 3);
                    batch.end();
                    accelX = Gdx.input.getAccelerometerX();
                    if (accelX < -0.5f) {
                        naveMiwa.setMovimiento(NaveMiwa.MOVIMIENTO.DERECHA);
                    } else if (accelX > 0.5f) {
                        naveMiwa.setMovimiento(NaveMiwa.MOVIMIENTO.IZQUIERDA);
                    } else {
                        naveMiwa.setMovimiento(NaveMiwa.MOVIMIENTO.QUIETO);
                    }
                    //Movimiento con teclas
                    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
                        naveMiwa.setMovimiento(NaveMiwa.MOVIMIENTO.IZQUIERDA);
                    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
                        naveMiwa.setMovimiento(NaveMiwa.MOVIMIENTO.DERECHA);
                    //Movimiento con teclas

                    if (estadosJuego != EstadosPersecucion.INVENCIBLEG) {
                        if (naveMiwa.getRectangle().overlaps(gemas.getRectangle())) {
                            gemas.setEstadoGema(Gemas.EstadoGema.DESAPARECER);
                            contadorGemas += 1;
                            gemaVida.setGemas(contadorGemas);
                            sonidoGemas.play();
                            estadosJuego = EstadosPersecucion.INVENCIBLEG;
                        }
                    }
                    y -= 1;
                    if (estadosJuego != EstadosPersecucion.INVENCIBLE) {
                        for (Piedra p : piedras)
                            if (naveMiwa.getRectangle().overlaps(p.getRectangle())) {
                                p.setEstado(Piedra.EstadosPiedra.DESAPARECER);
                                naveMiwa.setVidas(naveMiwa.getVidas() - 1);
                                estadosJuego = EstadosPersecucion.INVENCIBLE;
                                sonidoRoca.play();
                            }
                        for (Piedra p : piedritas)
                            if (naveMiwa.getRectangle().overlaps(p.getRectangle())) {
                                p.setEstado(Piedra.EstadosPiedra.DESAPARECER);
                                contadorGemas -= 1;
                                gemaVida.setGemas(contadorGemas);
                                estadosJuego = EstadosPersecucion.INVENCIBLE;
                                sonidoRoca.play();
                            }
                    }
                    if (contadorGemas >= 3) {
                        tiempoGemas -= Gdx.graphics.getDeltaTime();
                        if(tiempoGemas%0.4>0.2)
                            gemaVida.getSprite().setAlpha(0.5f);
                        if(tiempoGemas%0.4<=0.2)
                            gemaVida.getSprite().setAlpha(1);
                        if (tiempoGemas <= 0) {
                            contadorGemas = 0;
                            gemaVida.getSprite().setAlpha(1);
                            gemaVida.setGemas(contadorGemas);
                            tiempoGemas = 1;
                            naveMiwa.setVidas(naveMiwa.getVidas() + 1);
                        }
                    }
                }
                break;
            case GANO:
                naveSonido.stop();
                musicaFondo.stop();
                botonPausa.setDisabled(true);
                if (conDial < Dialogos.length) {
                    musicaIntro.play();
                    batch.setProjectionMatrix(camaraDialogos.combined);
                    batch.begin();
                    batch.draw(Dialogos[conDial], 0, 0);
                    if (misionKitsune.getNivel() != 2)
                        botonSkip.render(batch);
                    batch.end();
                } else {
                    musicaIntro.stop();
                    if(misionKitsune.getNivel()==2)
                        misionKitsune.setNivel(3);
                    misionKitsune.setScreen(new MenuMapas(misionKitsune));
                    misionKitsune.getMusicaFondo().play();
                }
                break;
            case INTRO:
                if (conPre < Predialogos.length) {
                    musicaIntro.play();
                    batch.setProjectionMatrix(camaraDialogos.combined);
                    batch.begin();
                    batch.draw(Predialogos[conPre], 0, 0);
                    if (misionKitsune.getNivel() != 2)
                        botonSkip.render(batch);
                    batch.end();
                } else {
                    botonPausa.setDisabled(false);
                    //Modificar esta linea para
                    //estadosJuego= EstadosPersecucion.ESPERA;
                    estadosJuego = EstadosPersecucion.JUGANDO;
                    musicaIntro.stop();
                }

                break;
            case ESPERA:
                batch.setProjectionMatrix(camaraDialogos.combined);
                batch.begin();
                batch.draw(texPreNiv4, 0, 0);
                batch.end();
                if (Gdx.input.getAccelerometerY() >= 7) {
                    botonPausa.setDisabled(false);
                    estadosJuego = EstadosPersecucion.JUGANDO;
                    musicaIntro.stop();
                }
                break;
            case ESPERAFIN:
                batch.setProjectionMatrix(camara.combined);
                batch.begin();
                batch.draw(texturaFin,0,0);
                batch.end();
                if(Gdx.input.getAccelerometerY()<=2){
                    botonPausa.setDisabled(false);
                    estadosJuego=EstadosPersecucion.GANO;
                    musicaFondo.stop();
                }
                break;
        }
    }

    private void moverGema(){
        if(xGema>=ANCHO-texturaGema.getWidth())
        velXGema=-5;
        if(xGema<=0+texturaGema.getWidth()/2)
        velXGema=+5;
        xGema+=velXGema;
    }
    private  void moverNave(){
        int algo=rnd.nextInt(100);
        if(xNave<=texturaNaveEnemiga.getWidth()*2.5f)
            velxNave+=5;
        if(xNave>=ANCHO-texturaNaveEnemiga.getWidth()*2.5f)
           velxNave-=5;
        if (yNaveEnemiga >= ALTO + texturaNaveEnemiga.getHeight())
            if (algo >= 75)
                velyNave = -2;
        if(yNaveEnemiga<=ALTO-texturaNaveEnemiga.getHeight())
            if (algo <= 25)
                velyNave = 2;
        if(tiempoFinal>=85) //Corregir
            velyNave = -2.3f;

        yNaveEnemiga+=velyNave;
        xNave+=velxNave;

    }
    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.BACK){
            misionKitsune.getMusicaFondo().play();
            musicaIntro.stop();
            musicaFondo.stop();
            naveSonido.stop();
            if(estadosJuego==EstadosPersecucion.GANO)
                if (misionKitsune.getNivel() == 2)
                    misionKitsune.setNivel(3);

            misionKitsune.setScreen(new MenuMapas(misionKitsune));
        }return false;
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
        camaraDialogos.unproject(v);
        float x=v.x,y=v.y;
        if(estadosJuego== EstadosPersecucion.INTRO)
            if (x > 0 && x < ANCHO && y > 0 && y < ALTO) {
                conPre++;
                misionKitsune.getSonidoBotones().play();
            }
        if(estadosJuego== EstadosPersecucion.GANO)
            if (x > 0 && x < ANCHO && y > 0 && y < ALTO) {
                conDial++;
                misionKitsune.getSonidoBotones().play();
            }
        if(botonSkip.contiene(x, y)) {
            if (estadosJuego == EstadosPersecucion.INTRO)
                estadosJuego = EstadosPersecucion.ESPERA;
            else if(estadosJuego==EstadosPersecucion.GANO){
                musicaIntro.stop();
                if (misionKitsune.getNivel() == 2)
                    misionKitsune.setNivel(3);
                misionKitsune.setScreen(new MenuMapas(misionKitsune));
                misionKitsune.getMusicaFondo().play();
            }
        }




        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 v=new Vector3(screenX,screenY,0);
        camaraHUD.unproject(v);
        float x=v.x,y=v.y;
        if(botonPausa.contiene(x,y)) {
            misionKitsune.getSonidoBotones().play();
            estadosJuego=EstadosPersecucion.PAUSADO;
        }
        else if(botonReanudar.contiene(x,y)){
            misionKitsune.getSonidoBotones().play();
            estadosJuego=EstadosPersecucion.JUGANDO;
        }
        else if(botonMenu.contiene(x,y)){
            musicaFondo.stop();
            naveSonido.stop();
            misionKitsune.getMusicaFondo().play();
            misionKitsune.getSonidoBotones().play();
            misionKitsune.setScreen(new Menu(misionKitsune));
        }else if(botonSonido.contiene(x,y)){
            if (misionKitsune.isMudo()) {
                botonSonido.setTexture(texturaBtnSonido[0]);
                misionKitsune.setMudo(false);
            }else{
                botonSonido.setTexture(texturaBtnSonido[1]);
                misionKitsune.setMudo(true);
            }
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

    private void mute(){
        sonidoGemas.setVolume(0);
        naveSonido.setVolume(0);
        musicaFondo.setVolume(0);
        musicaIntro.setVolume(0);
    }
    private void unmute(){
        sonidoGemas.setVolume(1);
        naveSonido.setVolume(1);
        musicaFondo.setVolume(0.3f);
        musicaIntro.setVolume(1);
    }

    public enum EstadosPersecucion{
        JUGANDO,
        PAUSADO,
        PERDIO,
        GANO,
        INTRO,
        INVENCIBLE,
        INVENCIBLEG,
        ESPERA,
        ESPERAFIN
    }

}
