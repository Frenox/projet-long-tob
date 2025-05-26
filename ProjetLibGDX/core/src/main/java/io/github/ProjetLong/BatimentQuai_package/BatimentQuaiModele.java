package io.github.ProjetLong.BatimentQuai_package;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import io.github.ProjetLong.Bateaux.Bateau;
import io.github.ProjetLong.DataManager.DataManager;

/*
 * Modèle du batiment quai
 */
public class BatimentQuaiModele {

    /*Nombre maximum de boutons affichés dans le menu */
    public final int CAPACITE_MAX_MENU = 4;

    /*Page de boutons actuelle */
    private int page;
    /*Page maximale de boutons */
    private int maxPage;
    /*Nombre de bateaux contenus dans data */
    private int nb_elements_bateaux;

    /*Ensemble des données du jeu*/
    private DataManager data;
    
    /*Objet pour envoyer et recevoir des notifications aux différents objets */
    private PropertyChangeSupport support;

    public BatimentQuaiModele(DataManager data) {
        this.page = 0;
        this.data = data;

        this.nb_elements_bateaux = data.getBateaux().size();

        this.maxPage = data.getBateaux().size() / CAPACITE_MAX_MENU;
        this.support = new PropertyChangeSupport(this);
    }

    //Obtenir la page actuelle
    public int getPage() {
        return this.page;
    }

    //Obtenir le maximum de pages
    public int getMaxPage() {
        return this.maxPage;
    }

    //Ajouter un PropertyChangeListener. Cela permet de savoir si une variable ou un objet a changé de valeur
    public void addPropertyChangeListener(String arg0, PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(arg0, listener);
    }

    //Enlever un PropertyChangeListener
    public void removePropertyChangeListener(String arg0, PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(arg0, listener);
    }

    //Afficher le bateau numéro i + this.page * this.CAPACITE_MAX_MENU de data
    public void setBateauCourant(int i) {
        support.firePropertyChange("Nouveau bateau affiché", null, bateau_i(i));
    }

    //Avancer de "pas" pages
    public void avancerPage(int pas) {
        int prochainePage = this.page + pas;

        if (prochainePage >= 0 && prochainePage <= this.maxPage) {
            this.page = prochainePage;
            support.firePropertyChange("Page changée", page - pas, page);
        }
    }

    //Retirer le bateau numéro i + this.page * this.CAPACITE_MAX_MENU de data des bateaux
    public void retirer_element(int i) {
        data.getBateaux().remove(i + this.page * this.CAPACITE_MAX_MENU);

        if (this.maxPage > (data.getBateaux().size() - 1) / this.CAPACITE_MAX_MENU) {
            if (this.page == this.maxPage) {
                page--;
            }
            
        }
        support.firePropertyChange("Nombre de bateaux différent", 0, 1);
    }

    //Savoir si le bateau numéro i + this.page * this.CAPACITE_MAX_MENU de data peut être affiché
    public boolean element_affichable(int i) {
        return (i + this.page * this.CAPACITE_MAX_MENU < data.getBateaux().size());
    }
    
    //Obtenir le bateau numéro i + this.page * this.CAPACITE_MAX_MENU de data
    public Bateau bateau_i(int i) {
        return this.data.getBateaux().get((i + this.page * this.CAPACITE_MAX_MENU));
    }

    //Mettre à jour le nombre de pages maximales en fonction du nombre de bateaux dans data et avertir les différents objets si le nombre de bateaux change
    public void miseAJour() {
        if (this.nb_elements_bateaux != this.data.getBateaux().size()) {
            this.maxPage = data.getBateaux().size() / CAPACITE_MAX_MENU;
            this.support.firePropertyChange("Nombre de bateaux différent", this.nb_elements_bateaux, this.data.getBateaux().size());
        }
    }
    
}
