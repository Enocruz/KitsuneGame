package itesm.kitsune;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Menu implements Screen {

	private final MisionKitsune misionKitsune;
	private AssetManager assetManager;
	private Stage escena;
	private Texture texturaFondo,texturaBtnJugar,texturaBtnInstrucciones,texturaBtnAcercade,texturaGuion,texturaWhite;
	private final int ancho=1280,alto=800;
	private OrthographicCamera camara;
	private Viewport vista;
	public static Music sonidoBotones;
	private Animation guion;
	private float tiempo=0;


	public Menu(MisionKitsune misionKitsune) {
		this.misionKitsune=misionKitsune;
	}

	@Override
	public void show() {

		assetManager=new AssetManager();
		camara=new OrthographicCamera(ancho,alto);
		camara.position.set(ancho/2,alto/2,0);
		camara.update();
		//Vista
		vista= new StretchViewport(ancho,alto,camara);
		cargarTexturas();
		escena=new Stage();
		//Habilitar logs
		Gdx.input.setInputProcessor(escena);
		guion=new Animation(0.5f,new TextureRegion(texturaGuion),new TextureRegion(texturaWhite));
		guion.setPlayMode(Animation.PlayMode.LOOP);
		//Fondo
		Image imgFondo= new Image(texturaFondo);
		escena.addActor(imgFondo);

		//Agregar botones
		//Jugar
		TextureRegionDrawable trBtnJugar=new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));
		ImageButton btnJugar=new ImageButton(trBtnJugar);
		btnJugar.setPosition(ancho/2-btnJugar.getWidth()/2, 0.34f*alto);
		escena.addActor(btnJugar);
		//Opciones
		TextureRegionDrawable trBtnInstrucciones=new TextureRegionDrawable(new TextureRegion(texturaBtnInstrucciones));
		ImageButton btnInstrucciones=new ImageButton(trBtnInstrucciones);
		btnInstrucciones.setPosition(ancho/2-btnInstrucciones.getWidth()/2, 0.21f*alto);
		escena.addActor(btnInstrucciones);
		//Acerca de
		TextureRegionDrawable trBtnAcercade=new TextureRegionDrawable(new TextureRegion(texturaBtnAcercade));
		ImageButton btnAcercade=new ImageButton(trBtnAcercade);
		btnAcercade.setPosition(ancho/2-btnAcercade.getWidth()/2, 0.09f*alto);
		escena.addActor(btnAcercade);

		btnJugar.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sonidoBotones.play();
				misionKitsune.setScreen(new MenuMapas(misionKitsune));

				}
			}
		);
		btnInstrucciones.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

				sonidoBotones.play();
				misionKitsune.setScreen(new Instrucciones(misionKitsune));

				}
			}
		);
		btnAcercade.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

				sonidoBotones.play();
				misionKitsune.setScreen(new AcercaDe(misionKitsune));

				}
			}
		);
	}

	private void cargarTexturas() {
		//Textura fondo
		assetManager.load("Menu.png", Texture.class);
		//Texturas de Botones
		assetManager.load("BtnJugar.png",Texture.class);
		assetManager.load("BtnInstrucciones.png",Texture.class);
		assetManager.load("BtnAcercaDe.png",Texture.class);
		assetManager.load("ClickBotonesMenu.mp3",Music.class);
		assetManager.load("Guion.png",Texture.class);
		assetManager.load("white.png",Texture.class);
		//Se bloquea hasta cargar los recursos
		assetManager.finishLoading();
		//Cuando termina, leemos las texturas
		texturaFondo=assetManager.get("Menu.png");
		texturaBtnJugar=assetManager.get("BtnJugar.png");
		texturaBtnInstrucciones=assetManager.get("BtnInstrucciones.png");
		texturaBtnAcercade=assetManager.get("BtnAcercaDe.png");
		sonidoBotones=assetManager.get("ClickBotonesMenu.mp3");
		texturaGuion=assetManager.get("Guion.png");
		texturaWhite=assetManager.get("white.png");
	}

	@Override
	public void render(float v) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Dependencia de la camara
		tiempo+=Gdx.graphics.getDeltaTime();
		escena.setViewport(vista);
		escena.draw();
		escena.getBatch().begin();
		escena.getBatch().draw(guion.getKeyFrame(tiempo),ancho/1.47f,alto/1.8f);
		escena.getBatch().end();
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
		texturaFondo.dispose();
		texturaBtnAcercade.dispose();
		texturaBtnInstrucciones.dispose();
		texturaBtnJugar.dispose();
	}

	@Override
	public void dispose() {

	}
}
