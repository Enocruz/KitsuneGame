package itesm.MisionKitsune;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by b-and, Leslie and D on 05/09/2016.
 */
public class MisionKitsune extends Game{
    private Music musicaFondo;
    private Sound sonidoBotones;
    private int nivel;
    private final AssetManager assetManager = new AssetManager();
    private boolean mudo;
    @Override
    public void create() {
        try{
            if(Gdx.files.local("nivel.txt").exists()){
                FileHandle file = Gdx.files.local("nivel.txt");
                nivel=Integer.parseInt(file.readString());
            }
            else
                nivel=1;
        }
        catch(Exception e){

        }
        assetManager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        cargarMusica();
        setScreen(new Splash(this));
    }
    private void cargarMusica(){
        assetManager.load("MusicaMenu.mp3",Music.class);
        assetManager.load("ClickBotonesMenu.mp3",Sound.class);
        assetManager.finishLoading();
        musicaFondo=assetManager.get("MusicaMenu.mp3");
        sonidoBotones=assetManager.get("ClickBotonesMenu.mp3");
        musicaFondo.setLooping(true);
    }
    public int getNivel(){
        return this.nivel;
    }
    public Music getMusicaFondo(){
        return this.musicaFondo;
    }
    public Sound getSonidoBotones(){
        return this.sonidoBotones;
    }
    public AssetManager getAssetManager(){
        return this.assetManager;
    }

    public void setNivel(int nivel) {
        try {
            FileHandle file = Gdx.files.local("nivel.txt");
            file.writeString(""+nivel, false);
        }
        catch(Exception e){

        }
        this.nivel = nivel;
    }
    public boolean isMudo(){return mudo;}
    public void setMudo(boolean mudo){this.mudo=mudo;}
}
