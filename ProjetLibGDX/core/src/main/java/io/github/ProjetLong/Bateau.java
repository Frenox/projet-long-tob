package io.github.ProjetLong;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.ProjetLong.ZonesPeche.Poisson;

public class Bateau {
    private int TailleDispo;
    private int TailleMax;
    private CanneAPeche equipedCanne;
    private List<Stockage> Stockage;
    private List<CanneAPeche> Cannes;
    private List<ModuleBateau> Modules;
    private int TailleStockage;
    private int StockageDispo;

    public Bateau(int taille) {
        this.TailleMax = taille;
        this.TailleDispo = taille;
        Stockage = new ArrayList<Stockage>();
        Cannes = new ArrayList<CanneAPeche>();
        Modules = new ArrayList<ModuleBateau>();
        TailleStockage = 0;
        StockageDispo = 0;
    }

    public int getTailleStockage() {
        return TailleStockage;
    }
    public void setEquipedCanne(CanneAPeche equipedCanne) {
        this.equipedCanne = equipedCanne;
    }
    public void setTailleStockage(int tailleStockage) {
        TailleStockage = tailleStockage;
    }

    public List<ModuleBateau> getModules() {
        return Modules;
    }

    public void addPoisson(Poisson poisson) {
        for (Stockage stock : Stockage) {
            if (stock.getTailleDisponible() > 0) {
                stock.addPoisson(poisson);
                StockageDispo -= 1;
                break;
            }
        }
    }

    public int getStockageDispo() {
        return StockageDispo;
    }

    public void equipCanne(int i) {
        equipedCanne = Cannes.get(i);
    }

    public CanneAPeche getEquipedCanne() {
        return equipedCanne;
    }

    public void addSpriteX(float x) {
    }

    public void addSpriteY(float y) {
    }

    public Sprite getSprite() {
        Sprite ret = new Sprite(new Texture("cregut.png"));
        ret.setPosition(227, 118);
        return ret;
    }

    public boolean addStockage(Stockage stock) {
        if (TailleDispo > 0) {
            this.Stockage.add(stock);
            TailleStockage += stock.getTailleMax();
            StockageDispo += stock.getTailleMax();
            TailleDispo -= 1;
            majModules();
            return true;
        } else {
            return false;
        }
    }

    public boolean addCannes(CanneAPeche canne) {
        if (TailleDispo > 0) {
            this.Cannes.add(canne);
            TailleDispo -= 1;
            majModules();
            return true;
        } else {
            return false;
        }
    }

    private void majModules() {
        Modules = new ArrayList<ModuleBateau>();
        Modules.addAll(Cannes);
        Modules.addAll(Stockage);

    }

    public int getTailleMax() {
        return TailleMax;
    }

    public int getTailleDispo() {
        return TailleDispo;
    }

    public List<Poisson> getContenu() {
        List<Poisson> result = new ArrayList<Poisson>();
        for (Stockage mod : this.Stockage) {
            result.addAll(mod.getContenu());
        }
        return result;
    }
}
