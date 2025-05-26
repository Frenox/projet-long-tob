package io.github.ProjetLong.BatimentQuai_package;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import io.github.ProjetLong.Bateau;
import io.github.ProjetLong.DataManager.DataManager;

public class BatimentQuaiModele {

    public final int CAPACITE_MAX_MENU = 4;

    private int page;
    private int maxPage;

    private DataManager data;
    
    private PropertyChangeSupport support;

    public BatimentQuaiModele(DataManager data) {
        this.page = 0;
        this.data = data;

        this.maxPage = data.getBateaux().size() / 4;
        this.support = new PropertyChangeSupport(this);
    }

    public int getPage() {
        return this.page;
    }

    public int getMaxPage() {
        return this.maxPage;
    }

    public void addPropertyChangeListener(String arg0, PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(arg0, listener);
    }

    public void removePropertyChangeListener(String arg0, PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(arg0, listener);
    }

    public void setBateauCourant(int i) {
        support.firePropertyChange("Nouveau bateau affiché", null, bateau_i(i));
    }

    public void avancerPage(int pas) {
        int prochainePage = this.page + pas;

        if (prochainePage >= 0 && prochainePage <= this.maxPage) {
            this.page = prochainePage;
            support.firePropertyChange("Page changée", page - pas, page);
        }
    }

    public void retirer_element(int i) {
        data.getBateaux().remove(i + this.page * this.CAPACITE_MAX_MENU);

        if (this.maxPage > (data.getBateaux().size() - 1) / this.CAPACITE_MAX_MENU) {
            if (this.page == this.maxPage) {
                page--;
            }
            
        }
        support.firePropertyChange("Nombre de bateaux différent", page + 1, page);
    }

    public boolean element_affichable(int i) {
        return (i + this.page * this.CAPACITE_MAX_MENU < data.getBateaux().size());
    }
    
    public Bateau bateau_i(int i) {
        return this.data.getBateaux().get((i + this.page * this.CAPACITE_MAX_MENU));
    }
    
}
