package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by b-and on 11/11/2016.
 */

public class Cargando implements Screen {
    private MisionKitsune misionKitsune;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;
    private final int ancho=1280,alto=800;
    private AssetManager assetManager;
    Cargando(MisionKitsune misionKitsune){
        this.misionKitsune=misionKitsune;
    }
    static class CargandoBusqueda extends Cargando{
        CargandoBusqueda(MisionKitsune misionKitsune){
            super(misionKitsune);
        }
        @Override
        public void show(){
            super.show();
            cargarElementos();
        }
        private void cargarElementos(){
            Gdx.app.log("cargarRecursos","Iniciando...");
            super.assetManager.load("Skip.png",Texture.class);
            super.assetManager.load("Dialogo_Nivel1_1.png",Texture.class);
            super.assetManager.load("Dialogo_Nivel1_2.png",Texture.class);
            super.assetManager.load("Dialogo_PreNivel1_1.png",Texture.class);
            super.assetManager.load("Dialogo_PreNivel1_2.png",Texture.class);
            super.assetManager.load("Dialogo_PreNivel1_3.png",Texture.class);
            super.assetManager.load("Dialogo_PreNivel1_4.png",Texture.class);
            super.assetManager.load("Dialogo_PreNivel1_5.png",Texture.class);
            super.assetManager.load("Dialogo_PreNivel1_6.png",Texture.class);
            super.assetManager.load("Dialogo_PreNivel1_7.png",Texture.class);
            super.assetManager.load("Dialogo_PreNivel1_8.png",Texture.class);
            //Musica
            super.assetManager.load("MusicaJuegoN1.mp3",Music.class);
            super.assetManager.load("SonidoGemas.mp3",Music.class);
            super.assetManager.load("SonidoPicos.mp3",Music.class);
            super.assetManager.load("MusicaDialogoInicioNivel1.mp3",Music.class);
            super.assetManager.load("MusicaDialogoFinalNivel1.mp3",Music.class);
            //Textura Miwa
            super.assetManager.load("miwa.png",Texture.class);
            //Textura Vida
            super.assetManager.load("Vida.png",Texture.class);
            super.assetManager.load("GemaContador.png",Texture.class);
            //Texturas de Boton
            super.assetManager.load("pausa.png",Texture.class);
            super.assetManager.load("izquierda.png",Texture.class);
            super.assetManager.load("derecha.png",Texture.class);
            super.assetManager.load("salto.png",Texture.class);
            //Texturas Menu Pausa
            super.assetManager.load("Pantalla_Pausa.png", Texture.class);
            super.assetManager.load("Menu_Inicial.png", Texture.class);
            super.assetManager.load("Reanudar.png", Texture.class);
            super.assetManager.load("Pantalla_Perder.png",Texture.class);
            //Se bloquea hasta cargar los recursos
            Gdx.app.log("cargarRecursos", "Terminando...");
        }
        @Override
        public void render(float delta) {
            // Actualizar carga
            actualizar();
            // Dibujar
            super.borrarPantalla();

        }
        private void actualizar() {

            if (super.assetManager.update()) {
                // Terminó la carga, cambiar de pantalla
                super.misionKitsune.setScreen(new NivelBusqueda(super.misionKitsune, NivelBusqueda.EstadosJuego.INTRO,1));
            } else {
                // Aún no termina la carga de assets, leer el avance
                float avance = super.assetManager.getProgress()*100;
                Gdx.app.log("Cargando",avance+"%");
            }
        }

    }
    static class CargandoPersecucion extends Cargando{
        CargandoPersecucion(MisionKitsune misionKitsune){
            super(misionKitsune);
        }
        @Override
        public void show(){
            super.show();
            cargarElementos();
        }
        private void cargarElementos(){
            Gdx.app.log("cargarRecursos","Iniciando...");
            super.assetManager.load("N2HoyoNegro.png",Texture.class);
            super.assetManager.load("N2Vida.png",Texture.class);
            super.assetManager.load("N2Reanudar.png", Texture.class);
            super.assetManager.load("N2MenuInicial.png", Texture.class);
            super.assetManager.load("N2Pausa.png", Texture.class);
            super.assetManager.load("FondoEstrellas.png", Texture.class);
            super.assetManager.load("N2NaveEnemiga.png", Texture.class);
            super.assetManager.load("N2NaveMiwa.png", Texture.class);
            super.assetManager.load("N2NaveMiwaDerecha.png", Texture.class);
            super.assetManager.load("N2NaveMiwaIzquierda.png", Texture.class);
            super.assetManager.load("N2NaveEnemigaIzquierda.png", Texture.class);
            super.assetManager.load("N2NaveEnemigaDerecha.png", Texture.class);
            super.assetManager.load("FondoNebulosaAzul.png", Texture.class);
            super.assetManager.load("FondoNebulosaRoja.png", Texture.class);
            //assetManager.load("Pantalla_Pausa.png", Texture.class);
            super.assetManager.load("N2Piedra.png", Texture.class);
            super.assetManager.load("N2Piedritas.png", Texture.class);
            super.assetManager.load("Barra.png",Texture.class);
            super.assetManager.load("N2IconoNave.png",Texture.class);
            super.assetManager.load("N2ContadorGemas.png",Texture.class);
            super.assetManager.load("N2Gema.png",Texture.class);
            super.assetManager.finishLoading();
            //Se bloquea hasta cargar los recursos
            Gdx.app.log("cargarRecursos", "Terminando...");
        }
        @Override
        public void render(float delta) {
            // Actualizar carga
            actualizar();
            // Dibujar
            super.borrarPantalla();
        }
        private void actualizar() {

            if (super.assetManager.update()) {
                // Terminó la carga, cambiar de pantalla
                super.misionKitsune.setScreen(new NivelPersecucion(super.misionKitsune));
            } else {
                // Aún no termina la carga de assets, leer el avance
                float avance = super.assetManager.getProgress()*100;
                Gdx.app.log("Cargando",avance+"%");
            }
        }

    }

    private void borrarPantalla() {
        Gdx.gl.glClearColor(0.42f, 0.55f, 1, 1);    // r, g, b, alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    @Override
    public void show() {
        batch=new SpriteBatch();
        cargarCamara();
        assetManager=misionKitsune.getAssetManager();
    }
    private void cargarCamara() {
        //Camara
        camara=new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        //Vista
        vista= new StretchViewport(ancho,alto,camara);
    }
    @Override
    public void render(float delta) {

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
}
