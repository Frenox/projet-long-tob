package io.github.ProjetLong;

public interface Batiment {
    // Instructions d'input du minijeu
    void input(VilleScreen screen);

    // instructions de logic du minijeu
    void logic(VilleScreen screen);

    // instructions de draw du minijeu
    // !!!!! déja entre le batch.begin et le batch.end pas besoin de le remettre
    // position en x de 0 à 7 à multiplier par 64px
    void draw(VilleScreen screen, int position);
}