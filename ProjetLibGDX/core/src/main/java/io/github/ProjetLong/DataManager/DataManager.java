package io.github.ProjetLong.DataManager;

import java.util.ArrayList;
import java.util.List;

// JSON manipulation
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.github.ProjetLong.Bateau;
import io.github.ProjetLong.ModuleBateau;
import io.github.ProjetLong.Batiment;
import io.github.ProjetLong.ZonesPeche.Poisson;

public class DataManager {

    private List<Bateau> bateaux;
    private List<Poisson> stockagePoissons;
    private int argent;
    private List<ModuleBateau> modulesDispo;
    private Map<Batiment, Boolean> batimentsMap;

    private int stockagePoissonMax;
    private final SerializerPoisson poissonSerializer;
    private final SerializerBateau bateauSerializer;
    private final SerializerModule moduleSerializer;
    private String actData;

    /**
     * Charge un nouveau slot de jeu (sans donnees)
     */
    public DataManager() {

        bateaux = new ArrayList<Bateau>();
        stockagePoissons = new ArrayList<Poisson>();
        modulesDispo = new ArrayList<ModuleBateau>();
        argent = 0;
        stockagePoissonMax = 100;
        batimentsMap = new HashMap<Batiment, Boolean>();
        actData = "";
        // ...

        poissonSerializer = new SerializerPoisson();
        bateauSerializer = new SerializerBateau();
        moduleSerializer = new SerializerModule();
    }

    /**
     * Charge le slot de jeu donne (avec ses donnees)
     * 
     * @param slot (String) : Slot de sauvegarde a charger
     */
    public DataManager(String slot) {
        bateaux = new ArrayList<Bateau>();
        stockagePoissons = new ArrayList<Poisson>();
        modulesDispo = new ArrayList<ModuleBateau>();
        argent = 0;
        batimentsMap = new HashMap<Batiment, Boolean>();

        // ...

        poissonSerializer = new SerializerPoisson();
        bateauSerializer = new SerializerBateau();
        moduleSerializer = new SerializerModule();

        loadGame(slot);
    }

    public int getStockagePoissonMax() {
        return stockagePoissonMax;
    }

    public List<Bateau> getBateaux() {
        return bateaux;
    }

    public void ajouterBateauPort(Bateau bateau) {
        bateaux.add(bateau);
    }

    public void supprimerBateauPort(Bateau bateau) {
        bateaux.remove(bateau);
    }

    public List<Poisson> getStockage() {
        return stockagePoissons;
    }

    public void ajouterPoissonStockage(Poisson poisson) {
        if (reste1place()) {
            stockagePoissons.add(poisson);
        }
    }

    public boolean reste1place() {
        if (this.stockagePoissons.size() < stockagePoissonMax) {
            return true;
        } else {
            return false;
        }
    }

    public void supprimerPoissonStockage(Poisson poisson) {
        stockagePoissons.remove(poisson);
    }

    public List<ModuleBateau> getModules() {
        return modulesDispo;
    }

    public void ajouterModule(ModuleBateau mod) {
        modulesDispo.add(mod);
    }

    public void supprimerModule(ModuleBateau mod) {
        modulesDispo.remove(mod);
    }

    public int getArgent() {
        return argent;
    }

    public void ajouterArgent(int montant) {
        if (montant <= 0) {
            return;
        }
        argent += montant;
    }

    public void retirerArgent(int montant) {
        if (montant <= 0) {
            return;
        }
        argent -= montant;
    }

    public Map<Batiment, Boolean> getBatimentsMap() {
        return batimentsMap;
    }

    public void ajouterBatiment(Batiment batiment) {
        batimentsMap.put(batiment, true);
    }

    public void supprimerBatiment(Batiment batiment) {
        batimentsMap.remove(batiment);
    }

    /**
     * Serialise et sauvegarde les donnees du jeu au format JSON
     * dans le fichier du slot de jeu actuel
     * 
     * @param slot (String) : Slot de sauvegarde a charger
     * @return ______ (bool) : La sauvegarde a-t-elle reussi
     */
    public boolean saveGame(String slot) {
        Map<String, Object> data = new HashMap<>();

        try {
            // Ajout des donnees a sauvegarder
            data.put("Bateaux", bateauSerializer.serializeListData(bateaux, 0));
            data.put("StockagePoissons", poissonSerializer.serializeListData(stockagePoissons, 0));
            data.put("ModulesDisponibles", moduleSerializer.serializeListData(modulesDispo, 0));
            data.put("Argent", serializeArgent());

            ObjectMapper mapper = new ObjectMapper();
            // Ecriture des donnees
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(slot + ".json"), data);

            System.out.println("Jeu sauvegarde dans " + slot + ".json");
        } catch (Exception e) {
            System.out.println("Echec de la sauvegarde.");
            return false;
        }

        return true;
    }

    /**
     * Deserialise et charge les donnees du jeu au format JSON
     * depuis le fichier du slot de jeu selectionne
     * 
     * @param slot (String) : Slot de sauvegarde a charger
     * @return ______ (bool) : Le chargement a-t-il reussi
     */
    public boolean loadGame(String slot) {
        Map<String, String> data;

        try {
            // Ouverture du fichier
            File dataFile = new File(slot + ".json");

            ObjectMapper mapper = new ObjectMapper();
            // Lecture des donnees
            data = mapper.readValue(dataFile, Map.class);
            actData = slot;
            bateaux = bateauSerializer.deserializeListData(data.get("Bateaux"), 0);
            stockagePoissons = poissonSerializer.deserializeListData(data.get("StockagePoissons"), 0);
            modulesDispo = moduleSerializer.deserializeListData(data.get("ModulesDisponibles"), 0);
            deserializeArgent(data.get("Argent"));

            System.out.println("Jeu charge depuis " + slot + ".json");
        } catch (Exception e) {
            System.out.println("Echec du chargement.");
            return false;
        }

        return true;
    }

    public String getActData() {
        return actData;
    }

    /**
     * Serialise l'argent
     * 
     * @return ______ (String) : Chaine de caracteres serialisee de l'argent
     */
    private String serializeArgent() {
        return String.valueOf(argent);
    }

    /**
     * Deserialise et les donnees de l'argent
     * 
     * @param serializedData (String) : Chaine de caracteres serialisee de l'argent
     */
    private void deserializeArgent(String serializedData) {
        if (serializedData.isEmpty()) { // Si pas de save d'argent
            return;
        }

        try {
            argent = Integer.parseInt(serializedData);
        } catch (Exception e) {
            argent = 0;
        }
    }

}
