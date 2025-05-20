package io.github.ProjetLong;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.ProjetLong.ZonesPeche.Poisson;

public class Bateau {
    private Texture Logo;
    private int TailleDispo;
    private int TailleMax;
    private String name;
    private String modeleName;
    private CanneAPeche equipedCanne;
    private List<Stockage> Stockage;
    private List<CanneAPeche> Cannes;
    private List<ModuleBateau> Modules;
    private List<Voile> Voiles;
    private int TailleStockage;
    private int StockageDispo;
    private String state;

    public Bateau(int taille) {
        this.TailleMax = taille;
        this.TailleDispo = taille;
        Stockage = new ArrayList<Stockage>();
        Cannes = new ArrayList<CanneAPeche>();
        Voiles = new ArrayList<Voile>();
        Modules = new ArrayList<ModuleBateau>();
        TailleStockage = 0;
        StockageDispo = 0;
        name = "Cregut";
        state = "A quai";
        modeleName = "Cregut";
        Logo = new Texture("cregut.png");
    }

    public void setModeleName(String modeleName) {
        this.modeleName = modeleName;
    }

    public String getModeleName() {
        return modeleName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getTailleStockage() {
        return TailleStockage;
    }

    public List<Stockage> getStockage() {
        return Stockage;
    }

    public void setEquipedCanne(CanneAPeche equipedCanne) {
        this.equipedCanne = equipedCanne;
    }

    public void setTailleStockage(int tailleStockage) {
        TailleStockage = tailleStockage;
    }

    public void setLogo(Texture logo) {
        Logo = logo;
    }

    public Texture getLogo() {
        return Logo;
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

    public List<CanneAPeche> getCannes() {
        return Cannes;
    }

    public List<Voile> getVoiles() {
        return Voiles;
    }

    public void addSpriteX(float x) {
    }

    public void addSpriteY(float y) {
    }

    public void setSprite(float x, float y) {
    }

    public Sprite getSprite() {
        Sprite ret = new Sprite(new Texture("cregut.png"));
        ret.setPosition(227, 118);
        return ret;
    }

    public boolean addStockage(Stockage stock) {
        if (TailleDispo > 0) {
            this.Stockage.add(stock);
            TailleStockage += stock.getTailleDisponible();
            StockageDispo += stock.getTailleDisponible();
            TailleDispo -= 1;
            majModules();
            return true;
        } else {
            return false;
        }
    }

    public boolean remStockage(Stockage stock) {
        this.Stockage.remove(stock);
        TailleStockage -= stock.getTailleDisponible();
        StockageDispo -= stock.getTailleDisponible();
        TailleDispo += 1;
        majModules();
        return true;

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

    public boolean remCannes(CanneAPeche canne) {

        this.Cannes.remove(canne);
        TailleDispo += 1;
        majModules();
        return true;
    }

    public boolean addVoile(Voile voile) {
        if (TailleDispo > 0) {
            this.Voiles.add(voile);
            TailleDispo -= 1;
            majModules();
            return true;
        } else {
            return false;
        }
    }

    public boolean remVoile(Voile voile) {

        this.Voiles.remove(voile);
        TailleDispo += 1;
        majModules();
        return true;

    }

    private void majModules() {
        Modules = new ArrayList<ModuleBateau>();
        Modules.addAll(Cannes);
        Modules.addAll(Stockage);
        Modules.addAll(Voiles);

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

    public Poisson remFirstPoisson() {
        Poisson temp = null;
        for (Stockage mod : this.Stockage) {
            if (mod.getContenu().size() != 0) {
                temp = mod.getContenu().removeFirst();
                break;
            }
        }
        return temp;

    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
