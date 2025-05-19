package io.github.ProjetLong;

public interface Minijeu extends SousFenetre {
    // Instructions d'input du minijeu
    @Override
    void input(PecheActiveScreen screen);

    // instructions de logic du minijeu
    @Override
    void logic(PecheActiveScreen screen);

    // instructions de draw du minijeu
    // !!!!! déja entre le batch.begin et le batch.end pas besoin de le remettre
    @Override
    void draw(PecheActiveScreen screen);

    // recupère l'état actuel du minijeu
    // 0 = pas encore lancé, 1 = en cours, 2 = fini
    int getState();
}
