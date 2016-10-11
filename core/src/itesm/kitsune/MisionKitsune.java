package itesm.kitsune;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by b-and, Leslie and D on 05/09/2016.
 */
public class MisionKitsune extends Game{
    public static Music music;
    @Override
    public void create() {
        setScreen(new Menu(this));
    }
}
