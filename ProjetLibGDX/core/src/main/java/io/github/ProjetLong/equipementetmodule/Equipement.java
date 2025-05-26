package io.github.ProjetLong.equipementetmodule;

public interface Equipement {
    String getNom();

    void setNom(String nom);

    String getCategorie();

    int getNiveau();

    void ameliorer();

    int getPrix();

    void enleverDeLaVente();

    Equipement dupliquer();
}