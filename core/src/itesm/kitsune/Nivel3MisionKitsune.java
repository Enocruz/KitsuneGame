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
            texturaD4,texturaD5,texturaD6,texturaSkip;
    private Texture[] Dialogos;
    // para cargar texturas
    private AssetManager assetManager = new AssetManager();

    // Los botones del nivel
    private Boton botonSaltar, botonPausa, botonDisparar, botonReanudar, botonMenuInicial,botonSkip;

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
    private Music SonidoGemas, SonidoJuego;

    //Personaje principal
    Miwa miwa;

    //Jefe Final
    JefeN3 jefe;
    //coso fondo
    private int  x=0,xp=0;
    //Velocidad con la que avanza el nivel
    private float velNivel = -2,tiempovida = 2;

    //plataformas
    float r;
    private PlataformaN3 plataforma,p_apoyo,p_apoyo2;
    private Random randfloat= new Random ();
    private Array<PlataformaN3> plataformas,p_apoyoa;

    //disparos
    private Disparo disparo;
    private Array<Disparo> disparos;

    // Gemas
    private Gemas gema;
    private Array<Gemas> gemitas;

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
        assetManager.load("miwa.png",Texture.class);
        assetManager.load("Disparo.png",Texture.class);
        assetManager.load("SpriteDisparando.png",Texture.class);
        //jefe final
        assetManager.load("SpritesJefe.png",Texture.class);
        assetManager.load("VidaEnemigoColor.png",Texture.class);
        assetManager.load("VidaEnemigoMarco.png",Texture.class);
        //plataformas
        assetManager.load("PlataformaFinal.png",Texture.class);
        //en la camara de botones...
        assetManager.load("pausa.png",Texture.class);
        assetManager.load("salto.png",Texture.class);
        assetManager.load("BtnDisparo.png",Texture.class);
        assetManager.load("GemaContador.png",Texture.class);
        assetManager.load("Vida.png",Texture.class);
        assetManager.load("Skip.png",Texture.class);
        //pantalla de pausa
        assetManager.load("Pantalla_Pausa.png", Texture.class);
        assetManager.load("Menu_Inicial.png", Texture.class);
        assetManager.load("Reanudar.png", Texture.class);
        assetManager.load("Pantalla_Perder.png",Texture.class);
        //musica
        assetManager.load("SonidoGemas.mp3",Music.class);
        //fondo
        assetManager.load("FondoEstrellas.png",Texture.class);
        assetManager.load("FondoNebulosaAzul.png",Texture.class);
        assetManager.load("FondoNebulosaRoja.png",Texture.class);
        assetManager.load("FondoPlaneta.png",Texture.class);
        //Dialogos
        assetManager.load("Dialogo_PreNivel3_1.jpg",Texture.class);
        assetManager.load("Dialogo_PreNivel3_2.jpg",Texture.class);
        assetManager.load("Dialogo_PreNivel3_3.jpg",Texture.class);
        assetManager.load("Dialogo_PreNivel3_4.jpg",Texture.class);
        assetManager.load("Dialogo_PreNivel3_5.jpg",Texture.class);
        assetManager.load("Dialogo_PreNivel3_6.jpg",Texture.class);

        assetManager.finishLoading();

        //miwa
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
        //musica
        SonidoGemas = assetManager.get ("SonidoGemas.mp3");
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
        botonDisparar.setPosicion(botonDisparar.getX(),texturaDisparar.getHeight()*1.5f);
        botonDisparar.setAlfa(0.6f);
        botonPausa=new Boton(texturaPausa);
        botonPausa.setPosicion(ANCHO-texturaPausa.getWidth()-ANCHO/64,ALTO-texturaPausa.getHeight()-ANCHO/64);
        botonPausa.setAlfa(0.8f);
        botonSaltar=new Boton(texturaSaltar);
        botonSaltar.setPosicion(ANCHO/1.1f,texturaSaltar.getHeight()*1.5f);
        botonSaltar.setAlfa(0.6f);
        botonReanudar=new Boton(texturaBotonReanudar);
        botonReanudar.setPosicion(ANCHO/2-texturaBotonReanudar.getWidth()/2,ALTO/2+texturaBotonReanudar.getHeight()/2);
        botonMenuInicial=new Boton(texturaBotonMenuInicial);
        botonMenuInicial.setPosicion(ANCHO/2-texturaBotonMenuInicial.getWidth()/2,ALTO/4+texturaBotonMenuInicial.getHeight()/2);
        botonSkip=new Boton(texturaSkip);
        botonSkip.setPosicion(ANCHO-texturaSkip.getWidth()*1.5f,ALTO-texturaSkip.getHeight()*1.5f);
    }

    @Override
    public void show() {
        inicializarCamara();
        cargarTexturas();
        crearBotones();

        texturaEstrellas.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        texturaFondoNA.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        texturaFondoNR.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        texturaFondoP.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        batch=new SpriteBatch();

        //miwa
        miwa = new Miwa(texturaMiwa);
        miwa.getSprite().setPosition(ANCHO/2,400);
        miwa.setEstadoMovimiento(Miwa.Estados.N3);
        miwa.setEstadoSalto(Miwa.EstadosSalto.EN_PISO);
        //jefe
        jefe = new JefeN3(texturaJefe,-150);
        barra = new NinePatch(new TextureRegion(texturaVidaJefe,0,0,texturaVidaJefe.getWidth(),texturaVidaJefe.getHeight()),8,8,8,8);

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

        p_apoyo = new PlataformaN3(texturaPlataforma, miwa.getX(),321);
        p_apoyo2 = new PlataformaN3(texturaPlataforma, miwa.getX()+texturaPlataforma.getWidth()*1.75f,321);
        p_apoyoa.add(p_apoyo);
        p_apoyoa.add(p_apoyo2);
        //plataformas qe se estarán repitiendo
        for (int i = 0; i<9; i++) {
            if (i<=2){
                plataforma = new PlataformaN3(texturaPlataforma, ANCHO + texturaPlataforma.getWidth(), (181 * (i + 1)));
                plataformas.add(plataforma);
            }else if (i<=5){
                plataforma = new PlataformaN3(texturaPlataforma, ANCHO + texturaPlataforma.getWidth()*3, (181 * (i + -2)));
                plataformas.add(plataforma);
            }else{
                plataforma = new PlataformaN3(texturaPlataforma, ANCHO + texturaPlataforma.getWidth()*6, (181 * (i + -5)));
                plataformas.add(plataforma);
            }
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        consultarEstado();
    }


    private void consultarEstado() {
        switch (estadosJuego) {
            case JUGANDO:
            case PAUSADO:
            case MENOSVIDA:

                batch.setProjectionMatrix(camara.combined);
                batch.begin();
                //PROBLEMAS CON VOLTEAR LA TEXTURA (NebulosaAzul y roja)
                batch.draw(texturaEstrellas, 0, 0, x, 0, ANCHO, ALTO);
                batch.draw(texturaFondoNA, 0, 0, x, 0, ANCHO, ALTO,0,0,90,0,0,texturaFondoNA.getWidth(),texturaFondoNA.getHeight(),false,false);
                batch.draw(texturaFondoNR, 0, 0, ANCHO/2, ALTO/2, ANCHO, ALTO);
                batch.draw(texturaFondoP,0,0,x,0,ANCHO,ALTO);
                x+=4;xp+=1;
                batch.end();
                batch.setProjectionMatrix(camara.combined);
                batch.begin();
                if (p_apoyoa.size!=0) {
                    for (PlataformaN3 p : p_apoyoa) {
                        if (p.estado != PlataformaN3.estadosP.FUERA) {
                            p.render(batch,velNivel);
                        }else p_apoyoa.removeIndex(p_apoyoa.indexOf(p, true));
                    }
                }
                for (PlataformaN3 p: plataformas){
                    p.render(batch,velNivel);
                }

                for (Disparo d :disparos){
                    if (d.estado == Disparo.estadoD.ADENTRO){
                        d.render(batch);
                    }
                    if (d.colision.overlaps(jefe.colision)) {
                        jefe.setEstado(JefeN3.Estado.ATACADO);
                        disparos.removeIndex(disparos.indexOf(d, true));
                    }
                }

                jefe.render(batch);
                miwa.render(batch);
                barra.draw(batch,0,0,texturaVidaJefe.getWidth(),texturaVidaJefe.getHeight()*jefe.vida/100);
                batch.draw(texturaMarcoVida,0,0);
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
                if (miwa.colision.overlaps(jefe.colision)){
                    System.out.println("PERO NO SE ESTAN FUCKIN TOCANDO");
                    estadosJuego=EstadosJuego.PERDIO;
                }
                if (estadosJuego == EstadosJuego.PAUSADO) {
                    batch.begin();
                    batch.draw(texturaMenuPausa, ANCHO / 2 - texturaMenuPausa.getWidth() / 2, ALTO / 2 - texturaMenuPausa.getHeight() / 2);
                    botonReanudar.render(batch);
                    botonMenuInicial.render(batch);
                    batch.end();
                    miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
                    botonDisparar.setDisabled(true);
                    botonPausa.setDisabled(true);
                    botonSaltar.setDisabled(true);
                    botonMenuInicial.setDisabled(false);
                    botonReanudar.setDisabled(false);
                }else if (estadosJuego==EstadosJuego.MENOSVIDA){
                    velNivel = 0;
                    miwa.getSprite().setPosition(ANCHO/2,400);
                    miwa.setEstadoMovimiento(Miwa.Estados.QUIETO);
                    botonDisparar.setDisabled(true);
                    botonPausa.setDisabled(true);
                    botonSaltar.setDisabled(true);
                    botonMenuInicial.setDisabled(true);
                    botonReanudar.setDisabled(true);
                    tiempovida -= Gdx.graphics.getDeltaTime();
                    if(tiempovida%0.5<0.25){
                        miwa.getSprite().setAlpha(0.7f);}
                    else if(tiempovida%0.5>=0.25)
                        miwa.getSprite().setAlpha(0.9f);
                    if (tiempovida <= 0) {
                        miwa.setVidas(miwa.getVidas()-1);
                        estadosJuego = EstadosJuego.JUGANDO;
                        velNivel=-2;
                        tiempovida = 2;
                        miwa.getSprite().setAlpha(1);
                    }

                }else if (estadosJuego==EstadosJuego.JUGANDO){
                    botonDisparar.setDisabled(false);
                    botonPausa.setDisabled(false);
                    botonSaltar.setDisabled(false);
                    botonMenuInicial.setDisabled(true);
                    botonReanudar.setDisabled(true);
                }
                if (miwa.getVidas() <= 0) {
                    estadosJuego = EstadosJuego.PERDIO;
                    misionKitsune.setScreen(new FinJuego(misionKitsune, new Texture("FondoEstrellas.png"),3));
                }


                if (miwa.getY()< -miwa.getSprite().getHeight()){
                    estadosJuego=EstadosJuego.MENOSVIDA;
                }
                if (jefe.getEstado() == JefeN3.Estado.MUERTO){
                    estadosJuego = EstadosJuego.GANO;
                }

                break;
            case PERDIO:
                break;
            case GANO:
                break;
            case INTRO:
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
                    estadosJuego=EstadosJuego.JUGANDO;
                }
                botonDisparar.setDisabled(true);
                botonPausa.setDisabled(true);
                botonSaltar.setDisabled(true);
                botonReanudar.setDisabled(true);
                botonMenuInicial.setDisabled(true);
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
        if(estadosJuego== EstadosJuego.INTRO)
            if (x > 0 && x < ANCHO && y > 0 && y < ALTO) {
                conDial++;
                Menu.sonidoBotones.play();
            }

        camaraHUD.unproject(v);
        if(botonDisparar.contiene(x,y)){
            Timer.instance().start();
            miwa.getSprite().setRegion(texturaMiwaDisparando);
            miwa.setVelocidadX(velNivel);
            miwa.setEstadoMovimiento(Miwa.Estados.DISPARANDO);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    disparo = new Disparo(texturaDisparo,(int)miwa.getSprite().getX(),(int)miwa.getY()+63);
                    disparos.add(disparo);
                }
            },0,1);

        }
        else if (botonSaltar.contiene(x,y)){
            if (miwa.getEstadosSalto() != Miwa.EstadosSalto.BAJANDO &&
                    miwa.getEstadosSalto()!= Miwa.EstadosSalto.CAIDA_LIBRE)
                miwa.setEstadoSalto(Miwa.EstadosSalto.SUBIENDO) ;        }
        else if(botonSkip.contiene(x, y)) {
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
            Timer.instance().stop();
        }

        if(botonPausa.contiene(x,y)){
            velNivel = 0;
            this.estadosJuego = EstadosJuego.PAUSADO;
        }else if(botonReanudar.contiene(x,y)){
            velNivel = -2;
            estadosJuego=EstadosJuego.JUGANDO;
        }else if(botonMenuInicial.contiene(x,y)){
            misionKitsune.getMusicaFondo().play();
            Menu.sonidoBotones.play();
            misionKitsune.setScreen(new Menu(misionKitsune));
        }return true;
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

    enum EstadosJuego{
        PAUSADO,
        PERDIO,
        GANO,
        JUGANDO,
        INTRO,
        MENOSVIDA
    }
}
