package itesm.kitsune;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by b-and, Leslie and D on 05/09/2016.
 */
public class MisionKitsune extends Game{
    private Music musicaFondo,sonidoBotones;
    private int nivel=3;
    private final AssetManager assetManager = new AssetManager();
    private boolean mudo;

    @Override
    public void create() {
        assetManager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        musicaFondo=Gdx.audio.newMusic(Gdx.files.internal("MusicaMenu.mp3"));
        sonidoBotones=Gdx.audio.newMusic(Gdx.files.internal("ClickBotonesMenu.mp3"));
        musicaFondo.setLooping(true);
        setScreen(new Splash(this));
    }
    public int getNivel(){
        return this.nivel;
    }
    public Music getMusicaFondo(){
        return this.musicaFondo;
    }
    public Music getSonidoBotones(){
        return this.sonidoBotones;
    }
    public AssetManager getAssetManager(){
        return this.assetManager;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    public boolean isMudo(){return mudo;}
    public void setMudo(boolean mudo){this.mudo=mudo;}
}
