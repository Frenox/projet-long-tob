package io.github.ProjetLong;

import com.badlogic.gdx.graphics.Texture;

//interface du module permettant d'implements les modules tel que la canne à pêche ou la voile
public interface ModuleBateau {
    public int getNiveau();

    public Texture getTexture();

}
