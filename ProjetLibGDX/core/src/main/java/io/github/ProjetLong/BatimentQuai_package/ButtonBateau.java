package io.github.ProjetLong.BatimentQuai_package;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class ButtonBateau extends Button{
    private int i;

    public ButtonBateau(ButtonStyle buttonStyle, int i) {
        super(buttonStyle);

        this.i = i;
    }
}
