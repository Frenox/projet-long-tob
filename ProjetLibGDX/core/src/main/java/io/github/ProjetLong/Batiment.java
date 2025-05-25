package io.github.ProjetLong;

import com.badlogic.gdx.utils.viewport.FitViewport;

public interface Batiment {
<<<<<<< Updated upstream
    // Instructions d'input du minijeu
=======
    FitViewport viewport = null;

    // Instructions d'input du Batiment
>>>>>>> Stashed changes
    void input(VilleScreen screen);

    // instructions de logic du minijeu
    void logic(VilleScreen screen);

    // instructions de draw du minijeu
    // !!!!! déja entre le batch.begin et le batch.end pas besoin de le remettre
    // position en x de 0 à 7 à multiplier par 64px
    void draw(VilleScreen screen, int position);
<<<<<<< Updated upstream
=======

    // draw de l'avant plan (Overlay)
    void affichageInterface(VilleScreen screen);

    String getNom();

    void agir();
>>>>>>> Stashed changes
}