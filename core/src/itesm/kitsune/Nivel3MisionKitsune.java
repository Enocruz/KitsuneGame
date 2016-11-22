package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Leslie on 04/11/2016.
 */

public class Nivel3MisionKitsune implements Screen, InputProcessor {
    private MisionKitsune misionKitsune;
    public static final int ANCHO = 1280, ALTO = 800;

    //Todas las texturas del nivel
    private Texture texturaVida, texturaSaltar, texturaDisparar,texturaDisparo,texturaMiwaDisparando, texturaMiwa,texturaGema, texturaPausa,texturaBotonReanudar, texturaBotonMenuInicial, texturaMenuPausa,
            texturaPlataforma, texturaJefe,texturaEstrellas, texturaFondoNA, texturaFondoNR, texturaFondoP,texturaMarcoVida,texturaVidaJefe,texturagemita,texturaD1,texturaD2,texturaD3,
            texturaD4,texturaD5,texturaD6,texturaSkip,texturaFelicidades,textSonido;
    private Texture[] Dialogos;
    private TextureRegion texturaSonido;
    private TextureRegion[] texturaBtnSonido;
    private TextureRegion[][] texbtnson;

    // para cargar texturas
    private AssetManager assetManager = new AssetManager();

    // Los botones del nivel
    private Boton botonSaltar, botonPausa, botonDisparar, botonReanudar, botonMenuInicial,botonSkip,botonSonido;

    //Camara del nivel y de los botones
    private OrthographicCamera camara, camaraHUD,camaraDialogos;
    private Viewport vista;

    // SPrite para dibujar
    private SpriteBatch batch;

    //Estados del juego
    private EstadosJuego estadosJuego = EstadosJuego.INTRO;

    //Gema superior
    private GemaVida gemaVida;

    //Texto vidas
    private Texto texto;

    //Contador gemas para vida extra
    private int contadorGemas = 0, conPre = 0, conDial = 0,nivel;

    // Música del nivel
    private Music sonidoGemas, sonidoJuego,sonidoDialogos, sonidoDisparo;

    //Personaje principal
    private Miwa miwa;

    //Jefe Final
    private JefeN3 jefe;
    //coso fondo
    private int  x=0,velXGema, xGema;
    private float xp=0,posx,posy;
    //Velocidad con la que avanza el nivel
    private float velNivel = -2,tiempovida,tiempoGemas,tiempomasgemas;

    //plataformas
    float r;
    private PlataformaN3 plataforma,p_apoyo,p_apoyo2;
    private Random randint= new Random ();
    private Array<PlataformaN3> plataformas,p_apoyoa;
    private Rectangle rec;

    //disparos
    private Disparo disparo;
    private Array<Disparo> disparos;

    // Gemas
    private Gemas gema;

    //bara vida jefe
    private NinePatch barra;


    public Nivel3MisionKitsune(MisionKitsune misionKitsune) {
        this.misionKitsune=misionKitsune;
        nivel =3;
    }

    public Nivel3MisionKitsune(MisionKitsune misionKitsune,EstadosJuego estado,int nivel) {
        this.misionKitsune=misionKitsune;
        this.estadosJuego= estado;
        this.nivel = nivel;
    }


    private void cargarTexturas(){
        //miwa
        assetManager=misionKitsune.getAssetManager();
        texturaMiwa = assetManager.get("miwa.png");
        texturaMiwaDisparando = assetManager.get("SpriteDisparando.png");
        texturaDisparo = assetManager.get("Disparo.png");
        //jefe final
        texturaJefe = assetManager.get("SpritesJefe.png");
        texturaMarcoVida = assetManager.get("VidaEnemigoMarco.png");
        texturaVidaJefe = assetManager.get("VidaEnemigoColor.png");
        //camara de botones
        texturaPausa = assetManager.get("pausa.png");
        texturaSaltar = assetManager.get("salto.png");
        texturaDisparar = assetManager.get ("BtnDisparo.png");
        texturaGema = assetManager.get ("GemaContador.png");
        texturaVida = assetManager.get ("Vida.png");
        texturaSkip = assetManager.get("Skip.png");
        //pantalla pausa
        texturaMenuPausa = assetManager.get ("Pantalla_Pausa.png");
        texturaBotonMenuInicial = assetManager.get ("Menu_Inicial.png");
        texturaBotonReanudar = assetManager.get("Reanudar.png");
        textSonido=assetManager.get("sonido.png");
        texturaSonido = new TextureRegion(textSonido);
        texbtnson = texturaSonido.split(texturaSonido.getRegionWidth()/2,textSonido.getHeight());
        for (int i = 0; i<2;i++){
            System.out.println("wytfrgvh");
            texturaBtnSonido [i] = texbtnson[0][i];
        }
        //musica
        sonidoGemas = assetManager.get ("SonidoGemas.mp3");
        sonidoJuego =assetManager.get("MusicaNivel3.mp3");
        sonidoDialogos = assetManager.get("MusicaDialogoInicioN3.mp3");
        sonidoDisparo =assetManager.get("MusicaDisparo.mp3");
        //fondo
        texturaEstrellas = assetManager.get("FondoEstrellas.png");
        texturaFondoNA  = assetManager.get("FondoNebulosaAzul.png");
        texturaFondoNR= assetManager.get("FondoNebulosaRoja.png");
        texturaFondoP = assetManager.get("FondoPlaneta.png");
        texturaPlataforma = assetManager.get("PlataformaFinal.png");
        //dialogos
        texturaD1 = assetManager.get("Dialogo_PreNivel3_1.jpg");
        texturaD2 = assetManager.get("Dialogo_PreNivel3_2.jpg");
        texturaD3 = assetManager.get("Dialogo_PreNivel3_3.jpg");
        texturaD4 = assetManager.get("Dialogo_PreNivel3_4.jpg");
        texturaD5 = assetManager.get("Dialogo_PreNivel3_5.jpg");
        texturaD6 = assetManager.get("Dialogo_PreNivel3_6.jpg");
        //gema
        texturagemita = assetManager.get("N3Gema.png");
        //final
        texturaFelicidades = assetManager.get("Felicidades.png");

    }
    private void inicializarCamara() {
        camara=new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();

        vista=new StretchViewport(ANCHO,ALTO,camara);

        camaraHUD = new OrthographicCamera(ANCHO,ALTO);
        camaraHUD.position.set(ANCHO/2, ALTO/2, 0);
        camaraHUD.update();

        camaraDialogos = new OrthographicCamera(ANCHO, ALTO);
        camaraDialogos.position.set(ANCHO / 2, ALTO / 2, 0);
        camaraDialogos.update();
    }

    private void crearBotones(){
        //CAMBIAR TEXTURA DE BOTON
        botonDisparar=new Boton(texturaDisparar);
        botonDisparar.setPosicion(botonDisparar.getX(),texturaDisparar.getHeight());
        botonDisparar.setAlfa(0.6f);
        botonPausa=new Boton(texturaPausa);
        botonPausa.setPosicion(ANCHO-texturaPausa.getWidth()-ANCHO/64,ALTO-texturaPausa.getHeight()-ANCHO/64);
        botonPausa.setAlfa(0.8f);
        botonSaltar=new Boton(texturaSaltar);
        botonSaltar.setPosicion(ANCHO/1.1f,texturaSaltar.getHeight());
        botonSaltar.setAlfa(0.6f);
        botonReanudar=new Boton(texturaBotonReanudar);
        botonReanudar.setPosicion(ANCHO/2-texturaBotonReanudar.getWidth()/2,ALTO/2+texturaBotonReanudar.getHeight()/2);
        botonMenuInicial=new Boton(texturaBotonMenuInicial);
        botonMenuInicial.setPosicion(ANCHO/2-texturaBotonMenuInicial.getWidth()/2,ALTO/4+texturaBotonMenuInicial.getHeight()/2);
        botonSkip=new Boton(texturaSkip);
        botonSkip.setPosicion(ANCHO-texturaSkip.getWidth()*1.5f,ALTO-texturaSkip.getHeight()*1.5f);
        botonSonido = new Boton(texturaBtnSonido[0].getTexture());
        botonSonido.setPosicion(ANCHO/2-botonSonido.getWidth()/2,ALTO-botonSonido.getHeight());
    }

    @Override
    public void show() {
        texturaBtnSonido = new TextureRegion[2];
        inicializarCamara();
        cargarTexturas();
        crearBotones();

        texturaEstrellas.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        texturaFondoNA.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        texturaFondoNR.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        texturaFondoP.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        batch=new SpriteBatch();
        //musica
        sonidoJuego.setLooping(true);
        sonidoJuego.setVolume(.5f);
        //miwa
        miwa = new Miwa(texturaMiwa);
        miwa.getSprite().setPosition(ANCHO/2,ALTO/2);
        miwa.setEstadoMovimiento(Miwa.Estados.N3);
        miwa.setEstadoSalto(Miwa.EstadosSalto.BAJANDO);
        tiempovida=2;

        //jefe
        jefe = new JefeN3(texturaJefe,-190);
        barra = new NinePatch(new TextureRegion(texturaVidaJefe,0,0,texturaVidaJefe.getWidth(),texturaVidaJefe.getHeight()),8,8,8,8);
        // gemita
        gema = new Gemas(texturagemita,ANCHO/2,ALTO);
        tiempoGemas = 1;
        velXGema = 2;
        tiempomasgemas = 0.2f;

        plataformas = new Array<PlataformaN3>();
        disparos = new Array<Disparo>();
        p_apoyoa = new Array <PlataformaN3>();

        //listener
        Gdx.input.setInputProcessor(this);

        //vidas extra
        texto=new Texto("DominoFont.fnt");
        gemaVida=new GemaVida(texturaGema);
        gemaVida.getSprite().setPosition(texturaGema.getWidth()/2,ALTO-texturaGema.getHeight()-ANCHO/80);

        //Dialogos
        Dialogos = new Texture[]{texturaD1,texturaD2,texturaD3,texturaD4,texturaD5,texturaD6};

        // Plataformas que aparecen inicialmente
        p_apoyo = new PlataformaN3(texturaPlataforma, miwa.getX(),291);
        p_apoyo2 = new PlataformaN3(texturaPlataforma, miwa.getX()+texturaPlataforma.getWidth()/1.5f*1.75f,291);
        p_apoyo.setTiempo(0);
        p_apoyo2.setTiempo(0);
        p_apoyo.setEstado(PlataformaN3.estadosP.DENTRO);
        p_apoyo2.setEstado(PlataformaN3.estadosP.DENTRO);
        p_apoyoa.add(p_apoyo);
        p_apoyoa.add(p_apoyo2);
        //plataformas que se estarán repitiendo
        for (int i = 0; i<9; i++) {
            if (i<=2){
                plataforma = new PlataformaN3(texturaPlataforma, ANCHO+texturaPlataforma.getWidth(),181);
                plataformas.add(plataforma);
            }else if (i<=5){
                plataforma = new PlataformaN3(texturaPlataforma, ANCHO + texturaPlataforma.getWidth()*2, 291);
                plataformas.add(plataforma);
            }else{
                plataforma = new PlataformaN3(texturaPlataforma, ANCHO + texturaPlataforma.getWidth()*4, 401);
                plataformas.add(plataforma);
            }
        }
        plataforma = new PlataformaN3(texturaPlataforma,ANCHO+ texturaPlataforma.getWidth()/2,291);
        plataforma.setTiempo(0);
        plataforma.setEstado(PlataformaN3.estadosP.DENTRO);

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


    private void consultarEstado() {
        switch (estadosJuego) {
            case JUGANDO:
            case PAUSADO:
            case MENOSVIDA:
            case MASGEMA:
            case PERDIO:
                sonidoJuego.play();
                //gemas
                if (contadorGemas <= 0)
                    contadorGemas = 0;
                if (gema.getEstadoGemas() == Gemas.EstadoGema.NUEVA)
                    gema.getSprite().setY(ALTO);
                if (randint.nextInt(100) >= 99)
                    gema.getSprite().setX(xGema);


                batch.setProjectionMatrix(camara.combined);
                batch.begin();
                //PROBLEMAS CON VOLTEAR LA TEXTURA (NebulosaAzul y roja)
                batch.draw(texturaEstrellas, 0, 0,x, 0, ANCHO, ALTO);
                batch.draw(texturaFondoP, 0,0,xp,0,ANCHO,ALTO,2,2,0,(int)xp,0,ANCHO,ALTO/2,false,false);

                x += 4;
                xp += .1;

                batch.end();
                batch.setProjectionMatrix(camara.combined);
                batch.begin();

                if (p_apoyoa.size != 0) {
                    for (PlataformaN3 p : p_apoyoa) {
                        p.render(batch, velNivel);
                        if (p.getRectangle().getX()<300){
                            p_apoyoa.removeIndex(p_apoyoa.indexOf(p,true));
                        }
                    }
                }
                plataforma.render(batch,velNivel);
                rec = plataforma.getRectangle();
                for (PlataformaN3 p: plataformas){
                    if (p.getRectangle().overlaps(rec)){
                        p.setEstado(PlataformaN3.estadosP.FUERA);
                    }
                    p.render(batch,velNivel);
                    rec = p.getRectangle();
                }


                for (Disparo d :disparos){
                    if (d.estado == Disparo.estadoD.ADENTRO){
                        d.render(batch);
                    }
                    if (d.colision.overlaps(jefe.getRectangle())) {
                        jefe.setEstado(JefeN3.Estado.ATACADO);
                        disparos.removeIndex(disparos.indexOf(d, true));
                    }
                }

                jefe.render(batch);
                miwa.render(batch);
                barra.draw(batch,0,0,texturaVidaJefe.getWidth(),texturaVidaJefe.getHeight()*jefe.vida/100);
                batch.draw(texturaMarcoVida,0,0);
                gema.render(batch,xGema);
                batch.end();

                batch.setProjectionMatrix(camaraHUD.combined);
                batch.begin();
                botonDisparar.render(batch);
                botonSaltar.render(batch);
                botonPausa.render(batch);
                gemaVida.render(batch);
                batch.draw(texturaVida, texturaVida.getWidth() / 8, ALTO - texturaVida.getHeight() - 16);
                texto.mostrarMensaje(batch, "" + miwa.getVidas(), 126, 722);
                batch.end();

                if (estadosJuego == EstadosJuego.PAUSADO) {
                    miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
                    miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
                    jefe.setCorriendo(false);
                    batch.begin();
                    batch.draw(texturaMenuPausa, ANCHO / 2 - texturaMenuPausa.getWidth() / 2, ALTO / 2 - texturaMenuPausa.getHeight() / 2);
                    botonReanudar.render(batch);
                    botonMenuInicial.render(batch);
                    batch.end();
                    botonDisparar.setDisabled(true);
                    botonPausa.setDisabled(true);
                    botonSaltar.setDisabled(true);
                    botonMenuInicial.setDisabled(false);
                    botonReanudar.setDisabled(false);
                    botonSonido.setDisabled(false);
                }else if (estadosJuego==EstadosJuego.MENOSVIDA){
                    gema.setEstadoGema(Gemas.EstadoGema.DESAPARECER);
                    posx=ANCHO/2;
                    posy = 410;
                    if (plataforma.estado== PlataformaN3.estadosP.DENTRO && !jefe.getRectangle().contains(plataforma.getX(),plataforma.getY())&&plataforma.getX()<ANCHO){
                        posx = plataforma.getX();
                        posy = plataforma.getY()+plataforma.getSprite().getHeight();
                    }else {
                        for (PlataformaN3 p : plataformas) {
                            if (p.estado == PlataformaN3.estadosP.DENTRO&&p.getX()<ANCHO&&!jefe.getRectangle().contains(p.getX(),p.getY())) {
                                posx = p.getX()+p.getSprite().getWidth()/4;
                                posy = p.getY() + p.getSprite().getHeight();
                                break;
                            }
                        }
                    }
                    velNivel = 0;
                    jefe.setCorriendo(false);
                    sonidoDisparo.pause();
                    miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
                    miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
                    miwa.getSprite().setPosition(posx,posy);
                    botonDisparar.setDisabled(true);
                    botonPausa.setDisabled(true);
                    botonSaltar.setDisabled(true);
                    botonMenuInicial.setDisabled(true);
                    botonReanudar.setDisabled(true);
                    botonSonido.setDisabled(true);
                    tiempovida -= Gdx.graphics.getDeltaTime();
                    if(tiempovida%0.5<0.25){
                        miwa.getSprite().setAlpha(0.7f);}
                    else if(tiempovida%0.5>=0.25)
                        miwa.getSprite().setAlpha(0.9f);
                    if (tiempovida <= 0) {
                        miwa.setVidas(miwa.getVidas()-1);
                        miwa.setEstadoSalto(Miwa.EstadosSalto.BAJANDO);
                        miwa.setEstadoMovimiento(Miwa.Estados.N3);
                        estadosJuego = EstadosJuego.JUGANDO;
                        velNivel=-2;
                        tiempovida = 2;
                        miwa.getSprite().setAlpha(1);
                    }
                }
                else if (estadosJuego == EstadosJuego.MASGEMA){
                    tiempomasgemas -= Gdx.graphics.getDeltaTime();
                    if (tiempomasgemas <=0){
                        estadosJuego =EstadosJuego.JUGANDO;
                        tiempomasgemas = 0.2f;
                    }
                    else if (estadosJuego == EstadosJuego.PERDIO){
                        sonidoDisparo.stop();
                        sonidoJuego.stop();
                        jefe.setCorriendo(false);
                        botonDisparar.setDisabled(true);
                        botonPausa.setDisabled(true);
                        botonSaltar.setDisabled(true);
                        botonMenuInicial.setDisabled(true);
                        botonReanudar.setDisabled(true);
                        botonSonido.setDisabled(true);
                    }
                }else {
                    sonidoJuego.play();
                    moverGema();
                    jefe.setCorriendo(true);
                    botonDisparar.setDisabled(false);
                    botonPausa.setDisabled(false);
                    botonSaltar.setDisabled(false);
                    botonMenuInicial.setDisabled(true);
                    botonReanudar.setDisabled(true);
                    botonSonido.setDisabled(true);
                    if (p_apoyoa.size != 0) {
                        for (PlataformaN3 p : p_apoyoa) {
                            if (miwa.getEstadosSalto() != Miwa.EstadosSalto.SUBIENDO) {
                                if (p.getRectangle().contains(miwa.getX() + miwa.getSprite().getWidth() / 2 - miwa.getSprite().getWidth() / 4, miwa.getY())) {
                                    miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
                                    break;
                                } else if (miwa.getEstadosSalto() != Miwa.EstadosSalto.BAJANDO) {
                                    miwa.setEstadoSalto(Miwa.EstadosSalto.BAJANDO);
                                }
                            }
                        }
                        if (miwa.getEstadosSalto() != Miwa.EstadosSalto.SUBIENDO) {
                            if (plataforma.getRectangle().contains(miwa.getX() + miwa.getSprite().getWidth() / 2 - miwa.getSprite().getWidth() / 4, miwa.getY())) {
                                miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
                            }
                        }
                    }else {
                        for (PlataformaN3 p : plataformas) {
                            if (miwa.getEstadosSalto() != Miwa.EstadosSalto.SUBIENDO) {
                                if (p.getRectangle().contains(miwa.getX() + miwa.getSprite().getWidth() / 2 - miwa.getSprite().getWidth() / 4, miwa.getY())) {
                                    miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
                                    break;
                                } else if (miwa.getEstadosSalto() != Miwa.EstadosSalto.BAJANDO) {
                                    miwa.setEstadoSalto(Miwa.EstadosSalto.BAJANDO);
                                }
                            }
                        }
                        if (miwa.getEstadosSalto() != Miwa.EstadosSalto.SUBIENDO) {
                            if (plataforma.getRectangle().contains(miwa.getX() + miwa.getSprite().getWidth() / 2 - miwa.getSprite().getWidth() / 4, miwa.getY())) {
                                miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
                            }
                        }
                    }

                }

                if (estadosJuego!=EstadosJuego.MASGEMA){
                    if (gema.getRectangle().overlaps(miwa.getRectangle())){
                        contadorGemas+=1;
                        gema.setEstadoGema(Gemas.EstadoGema.DESAPARECER);
                        gemaVida.setGemas(contadorGemas);
                        sonidoGemas.play();
                        estadosJuego =EstadosJuego.MASGEMA;
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
                        miwa.setVidas(miwa.getVidas() + 1);
                    }
                }
                if (miwa.getVidas() <= 0) {
                    sonidoJuego.stop();
                    sonidoDisparo.pause();
                    estadosJuego = EstadosJuego.PERDIO;
                    misionKitsune.setScreen(new FinJuego(misionKitsune, new Texture("FondoEstrellas.png"),3));
                }

                if (gema.getRectangle().overlaps(jefe.getRectangle())){
                    gema.setEstadoGema(Gemas.EstadoGema.DESAPARECER);
                }
                if (miwa.getY()< -miwa.getSprite().getHeight()){
                    estadosJuego=EstadosJuego.MENOSVIDA;
                }
                if (jefe.getEstado() == JefeN3.Estado.MUERTO){
                    estadosJuego = EstadosJuego.GANO;
                }
                if(miwa.getRectangle().overlaps(jefe.getRectangle())) {
                    miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
                    miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
                    sonidoDisparo.stop();
                    sonidoJuego.stop();
                    estadosJuego =EstadosJuego.PERDIO;
                    misionKitsune.setScreen(new FinJuego(misionKitsune, new Texture("FondoEstrellas.png"), 3));

                }

                break;
            case GANO:
                sonidoJuego.stop();
                batch.begin();
                batch.draw(texturaFelicidades,0,0);
                batch.end();
                break;
            case INTRO:
                sonidoDialogos.play();
                if (conDial < Dialogos.length) {
                    batch.setProjectionMatrix(camaraDialogos.combined);
                    batch.begin();
                    batch.draw(Dialogos[conDial], 0, 0);
                    if(misionKitsune.getNivel()!=3){
                        botonSkip.render(batch);
                    }
                    batch.end();
                }
                else{
                    sonidoDialogos.stop();
                    estadosJuego=EstadosJuego.JUGANDO;
                }
                botonDisparar.setDisabled(true);
                botonPausa.setDisabled(true);
                botonSaltar.setDisabled(true);
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
        textSonido.dispose();
        sonidoDialogos.dispose();
        sonidoJuego.dispose();
        sonidoDisparo.dispose();
        sonidoGemas.dispose();
        texturaVida.dispose();
        texturaSaltar.dispose();
        texturaDisparar.dispose();
        texturaDisparo.dispose();
        texturaMiwa.dispose();
        texturaGema.dispose();
        texturaPausa.dispose();
        texturaBotonReanudar.dispose();
        texturaBotonMenuInicial.dispose();
        texturaMenuPausa.dispose();
        texturaPlataforma.dispose();
        texturaJefe.dispose();
        texturaEstrellas.dispose();
        texturaFondoNA.dispose();
        texturaFondoNR.dispose();
        texturaFondoP.dispose();
        texturaMarcoVida.dispose();
        texturagemita.dispose();
        texturaD1.dispose();
        texturaD2.dispose();
        texturaD3.dispose();
        texturaD4.dispose();
        texturaD5.dispose();
        texturaD6.dispose();
        texturaSkip.dispose();
        texturaVidaJefe.dispose();
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
        camaraDialogos.unproject(v);
        float x=v.x,y=v.y;
        if (estadosJuego==EstadosJuego.GANO) {
            if (x > 0 && x < ANCHO && y > 0 && y < ALTO) {
                misionKitsune.setScreen(new Menu(misionKitsune));
            }
        }
        if(estadosJuego== EstadosJuego.INTRO) {
            if (x > 0 && x < ANCHO && y > 0 && y < ALTO) {
                conDial++;
            }
        }
        camaraHUD.unproject(v);
        if(botonDisparar.contiene(x,y)){
            if (miwa.getEstadosSalto()== Miwa.EstadosSalto.EN_PISO) {
                Timer.instance().start();
                miwa.getSprite().setRegion(texturaMiwaDisparando);
                miwa.setVelocidadX(velNivel);
                miwa.setEstadoMovimiento(Miwa.Estados.DISPARANDO);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        sonidoDisparo.play();
                        disparo = new Disparo(texturaDisparo, (int) miwa.getSprite().getX(), (int) miwa.getY() + 63);
                        disparos.add(disparo);
                    }
                }, 0, 1);
            }
        }
        else if (botonSaltar.contiene(x,y)) {
            if (miwa.getEstadosSalto() == Miwa.EstadosSalto.EN_PISO && miwa.getEstadoMovimiento() != Miwa.Estados.DISPARANDO) {
                miwa.setEstadoSalto(Miwa.EstadosSalto.SUBIENDO);
            }
        }else if(botonSkip.contiene(x, y)) {
            if (estadosJuego == EstadosJuego.INTRO)
                estadosJuego = EstadosJuego.JUGANDO;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        Vector3 v=new Vector3(screenX,screenY,0);
        camaraHUD.unproject(v);
        float x=v.x,y=v.y;
        if(botonDisparar.contiene(x,y)){
            miwa.getSprite().setRegion(texturaMiwa);
            miwa.setEstadoMovimiento(Miwa.Estados.N3);
            miwa.setVelocidadX(0);
            sonidoDisparo.pause();
            Timer.instance().stop();
        }

        if(botonPausa.contiene(x,y)){
            velNivel = 0;
            miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
            miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
            this.estadosJuego = EstadosJuego.PAUSADO;
        }else if(botonReanudar.contiene(x,y)){
            velNivel = -2;
            miwa.setEstadoSalto(Miwa.EstadosSalto.BAJANDO);
            estadosJuego=EstadosJuego.JUGANDO;
        }else if(botonMenuInicial.contiene(x,y)){
            sonidoJuego.stop();
            misionKitsune.getMusicaFondo().play();
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
        return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void moverGema(){
        if(xGema>=ANCHO-texturagemita.getWidth())
            velXGema=-5;
        if(xGema<=0+texturagemita.getWidth()/2)
            velXGema=+5;
        xGema+=velXGema;
    }

    private void mute (){
        sonidoDisparo.setVolume(0);
        sonidoJuego.setVolume(0);
        sonidoDialogos.setVolume(0);
        sonidoGemas.setVolume(0);
    }
    private void unmute(){
        sonidoDisparo.setVolume(1);
        sonidoJuego.setVolume(1);
        sonidoDialogos.setVolume(1);
        sonidoGemas.setVolume(1);
    }
    enum EstadosJuego{
        PAUSADO,
        PERDIO,
        GANO,
        JUGANDO,
        INTRO,
        MENOSVIDA,
        MASGEMA
    }
}
