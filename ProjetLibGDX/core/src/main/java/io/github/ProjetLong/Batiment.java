package io.github.ProjetLong;

public interface Batiment {
    // Instructions d'input du Batiment
    void input(VilleScreen screen);

    // instructions de logic du Batiment
    void logic(VilleScreen screen);

    // instructions de draw du Batiment
    // !!!!! déja entre le batch.begin et le batch.end pas besoin de le remettre
    // position en x de 0 à 7 à multiplier par 64px
    void draw(VilleScreen screen, int position, int offset);

    // draw de l'avant plan (Overlay)
    void affichageInterface(VilleScreen screen);

    boolean getIsOpened();

    void setIsOpened(boolean value);

    // Code à éxécuter à l'affichage de l'overlay
    void open();

    // Code à éxécuter au désaffichage de l'overlay
    void close();
}