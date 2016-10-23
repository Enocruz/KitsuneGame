package itesm.kitsune;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by b-and, Leslie and D on 05/09/2016.
 */
public class MisionKitsune extends Game{
    public static Music musicaFondo;
    public static int nivel=1;
    @Override
    public void create() {
        musicaFondo=Gdx.audio.newMusic(Gdx.files.internal("MusicaMenu.ogg"));
        musicaFondo.setLooping(true);
        musicaFondo.play();
        setScreen(new Menu(this));
    }
}
