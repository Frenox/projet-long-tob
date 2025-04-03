package io.github.ProjetLong;

import com.badlogic.gdx.Screen;

public interface Minijeu {
    // Instructions d'input du minijeu
    void input(PecheActiveScreen screen);

    // instructions de logic du minijeu
    void logic(PecheActiveScreen screen);

    // instructions de draw du minijeu
    // !!!!! déja entre le batch.begin et le batch.end pas besoin de le remettre
    void draw(PecheActiveScreen screen);
}
