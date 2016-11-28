package itesm.kitsune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by b-and on 05/09/2016.
 */
public class AcercaDe implements Screen {
    private final MisionKitsune misionKitsune;
    private Stage escena;
    private Texture texturaFondo,textureEnock,textureEli,textureLes,textureSnell,textureDaph,popupEnock,
            popupEli,popupSnell,popupDaph,popupLes,textureCerrar,texturaMateria,texturaCampus,textureMusica;
    private AssetManager assetManager;
    private Table tablepopup,tabla;
    private final int ancho=1280,alto=800;

    //Camara
    private OrthographicCamera camara;
    private Viewport vista;

    public AcercaDe(MisionKitsune misionKitsune){
        this.misionKitsune=misionKitsune;
    }
    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        assetManager=new AssetManager();
        //Camara
        camara=new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        //Vista
        vista= new StretchViewport(ancho,alto,camara);
        cargarTexturas();
        escena=new Stage();
        Gdx.input.setInputProcessor(escena);

        //Fondo
        Image imgFondo= new Image(texturaFondo);
        escena.addActor(imgFondo);
        //Boton Regresar
        TextureRegionDrawable trBtnRegresar=new TextureRegionDrawable(new TextureRegion(textureCerrar));
        ImageButton btnRegresar=new ImageButton(trBtnRegresar);


        //Tabla para los pop ups
        tabla=new Table();
        tabla.setPosition(ancho/1.10f-btnRegresar.getWidth()/2, 0.8f*alto);
        tabla.add(btnRegresar);
        escena.addActor(tabla);
        tablepopup=new Table();
        tablepopup.setPosition(ancho/2+17f,alto/2-10f);
        tablepopup.setVisible(false);




        //Boton Brandon
        TextureRegionDrawable trEnock=new TextureRegionDrawable(new TextureRegion(textureEnock));
        ImageButton btEnock=new ImageButton(trEnock);
        btEnock.setPosition(ancho/3.8f-btEnock.getWidth()/2,0.12f*alto);
        escena.addActor(btEnock);
        //Boton Leslie
        TextureRegionDrawable trLes=new TextureRegionDrawable(new TextureRegion(textureLes));
        ImageButton btLes=new ImageButton(trLes);
        btLes.setPosition(ancho/2f-btLes.getWidth()/2,0.12f*alto);
        escena.addActor(btLes);
        //Boton Daphne
        TextureRegionDrawable trDaph=new TextureRegionDrawable(new TextureRegion(textureDaph));
        ImageButton btDaph=new ImageButton(trDaph);
        btDaph.setPosition(ancho/2f-btDaph.getWidth()/2,0.45f*alto);
        escena.addActor(btDaph);
        //Boton Snell
        TextureRegionDrawable trSnell=new TextureRegionDrawable(new TextureRegion(textureSnell));
        ImageButton btSnell=new ImageButton(trSnell);
        btSnell.setPosition(ancho/1.36f-btSnell.getWidth()/2,0.45f*alto);
        escena.addActor(btSnell);
        //Boton Eli
        TextureRegionDrawable trEli=new TextureRegionDrawable(new TextureRegion(textureEli));
        ImageButton btEli=new ImageButton(trEli);
        btEli.setPosition(ancho/3.8f-btEli.getWidth()/2,0.45f*alto);
        escena.addActor(btEli);


        //Boton de cerrar
        TextureRegionDrawable trCerrar=new TextureRegionDrawable(new TextureRegion(textureCerrar));
        final ImageButton btCerrar=new ImageButton(trCerrar);

        //Más info (Pop Up) de Brandon
        final Image btPopUpEnock=new Image(popupEnock);
        final Image btPopUpDaph=new Image(popupDaph);
        final Image btPopUpLeslie=new Image(popupLes);
        final Image btPopUpSnell=new Image(popupSnell);
        final Image btPopUpEli=new Image(popupEli);
        final Image Materia = new Image(texturaMateria);
        final Image Campus=new Image(texturaCampus);
        final Image Musica=new Image(textureMusica);

        Materia.setPosition(ancho/2-Materia.getWidth()/2,alto-Materia.getHeight()*2);
        Campus.setPosition(ancho/2-Campus.getWidth()/2,Campus.getHeight()/2);
        Musica.setPosition(ancho/1.4f-Musica.getWidth()/3.2f,0.15f*alto);

        //Agregando la tabla vacía a la escena
        escena.addActor(tablepopup);
        escena.addActor(Materia);
        escena.addActor(Campus);
        escena.addActor(Musica);

        //Boton cerrar
        btCerrar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                misionKitsune.getSonidoBotones().play();
                tabla.setVisible(true);
                tablepopup.setVisible(false);
                tablepopup.clear();
            }
        });
        //Boton Imagen Brandon
        btEnock.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                misionKitsune.getSonidoBotones().play();
                tabla.setVisible(false);
                tablepopup.add(btPopUpEnock).width(ancho/1.38f).height(alto/1.28f);
                tablepopup.add(btCerrar).top().left();
                tablepopup.setVisible(true);
            }
        });
        //Boton Imagen Leslie
        btLes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                misionKitsune.getSonidoBotones().play();
                tabla.setVisible(false);
                tablepopup.add(btPopUpLeslie).width(ancho/1.38f).height(alto/1.28f);
                tablepopup.add(btCerrar).top().left();
                tablepopup.setVisible(true);
            }
        });

        //Boton Imagen Snell
        btSnell.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                misionKitsune.getSonidoBotones().play();
                tabla.setVisible(false);
                tablepopup.add(btPopUpSnell).width(ancho/1.38f).height(alto/1.28f);
                tablepopup.add(btCerrar).top().left();
                tablepopup.setVisible(true);
            }
        });

        //Boton Imagen Eli
        btEli.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                misionKitsune.getSonidoBotones().play();
                tabla.setVisible(false);
                tablepopup.add(btPopUpEli).width(ancho/1.38f).height(alto/1.28f);
                tablepopup.add(btCerrar).top().left();
                tablepopup.setVisible(true);
            }
        });
        btDaph.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                misionKitsune.getSonidoBotones().play();
                tabla.setVisible(false);
                tablepopup.add(btPopUpDaph).width(ancho/1.38f).height(alto/1.28f);
                tablepopup.add(btCerrar).top().left();
                tablepopup.setVisible(true);

            }
        });


        //Boton Regresar
        btnRegresar.addListener(new ClickListener(){
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        misionKitsune.getSonidoBotones().play();
                                        //Regresar a la pantalla del Menu
                                        misionKitsune.setScreen(new Menu(misionKitsune));
                                    }
                                }
        );

    }

    private void cargarTexturas() {
        assetManager.load("MusicaInfo.png",Texture.class);
        //Textura fondo
        assetManager.load("Campus.png",Texture.class);
        assetManager.load("AcercaDe.png", Texture.class);
        //Texturas de Boton
        assetManager.load("enock.png",Texture.class);
        assetManager.load("leslie.png",Texture.class);
        assetManager.load("snell.png",Texture.class);
        assetManager.load("daff.png",Texture.class);
        assetManager.load("eli.png",Texture.class);

        //imagen pop up prueba
        assetManager.load("enockCard.png",Texture.class);
        assetManager.load("daffCard.png",Texture.class);
        assetManager.load("eliCard.png",Texture.class);
        assetManager.load("snellCard.png",Texture.class);
        assetManager.load("leslieCard.png",Texture.class);
        //Boton cerrar pop up
        assetManager.load("cerrar.png",Texture.class);

        assetManager.load("Materia.png",Texture.class);
        //Se bloquea hasta cargar los recursos
        assetManager.finishLoading();
        //Cuando termina, leemos las texturas

        //Texturas pop ups (prueba)
        popupEnock=assetManager.get("enockCard.png");
        popupLes=assetManager.get("leslieCard.png");
        popupSnell=assetManager.get("snellCard.png");
        popupDaph=assetManager.get("daffCard.png");
        popupEli=assetManager.get("eliCard.png");

        //Texturas Botones
        textureDaph=assetManager.get("daff.png");
        textureEli=assetManager.get("eli.png");
        textureSnell=assetManager.get("snell.png");
        textureEnock=assetManager.get("enock.png");
        textureLes=assetManager.get("leslie.png");
        texturaFondo=assetManager.get("AcercaDe.png");
        textureCerrar=assetManager.get("cerrar.png");
        texturaMateria = assetManager.get("Materia.png");
        texturaCampus=assetManager.get("Campus.png");
        textureMusica=assetManager.get("MusicaInfo.png");
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Dependencia de la camara
        escena.setViewport(vista);
        escena.draw();
        if(Gdx.input.isKeyPressed(Input.Keys.BACK))
            misionKitsune.setScreen(new Menu(misionKitsune));
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
        dispose();
    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        textureLes.dispose();
        textureSnell.dispose();
        textureCerrar.dispose();
        textureDaph.dispose();
        textureEnock.dispose();
        textureEli.dispose();
        popupEnock.dispose();
        popupDaph.dispose();
        popupEli.dispose();
        popupLes.dispose();
        popupSnell.dispose();
    }
}