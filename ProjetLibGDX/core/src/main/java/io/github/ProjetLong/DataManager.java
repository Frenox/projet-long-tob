package io.github.ProjetLong;

import java.util.ArrayList;
import java.util.List;

// JSON manipulation
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import io.github.ProjetLong.Bateau;
import io.github.ProjetLong.DataErrorException;
import io.github.ProjetLong.ZonesPeche.Poisson;

public class DataManager {

    private List<Bateau> bateaux;
    private List<Poisson> stockagePoissons;
    private int argent;

    /**
     * Charge un nouveau slot de jeu (sans donnees)
     */
    public DataManager() {
        bateaux = new ArrayList<Bateau>();
        stockagePoissons = new ArrayList<Poisson>();
        argent = 0;

        // ...
    }

    /**
     * Charge le slot de jeu donne (avec ses donnees)
     * 
     * @param slot (String) : Slot de sauvegarde a charger
     */
    public DataManager(String slot) {
        bateaux = new ArrayList<Bateau>();
        stockagePoissons = new ArrayList<Poisson>();
        argent = 0;

        // ...

        loadGame(slot);
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
        stockagePoissons.add(poisson);
    }

    public void supprimerPoissonStockage(Poisson poisson) {
        stockagePoissons.remove(poisson);
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
            data.put("Bateaux", serializeBateaux());
            data.put("StockagePoissons", serializePoissons());
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
        Map<String, String> data = new HashMap<>();

        try {
            // Ouverture du fichier
            File dataFile = new File(slot + ".json");

            ObjectMapper mapper = new ObjectMapper();
            // Lecture des donnees
            data = mapper.readValue(dataFile, Map.class);

            deserializeBateaux(data.get("Bateaux"));
            deserializePoissons(data.get("StockagePoissons"));
            deserializeArgent(data.get("Argent"));

            System.out.println("Jeu charge depuis " + slot + ".json");
        } catch (Exception e) {
            System.out.println("Echec du chargement.");
            return false;
        }

        return true;
    }

    /**
     * Serialise et concatene les donnees des bateaux en
     * les separant par le caractere "|"
     * 
     * @return ______ (String) : Chaine de caracteres serialisee des bateaux
     */
    private String serializeBateaux() {
        String serializedData = "";
        for (Bateau bateau : bateaux) {
            // Serialisation du bateau
            serializedData += "" + "|";
        }
        return serializedData;
    }

    /**
     * Deserialise et les donnees des bateaux separees par le caractere "|"
     * 
     * @param serializedData (String) : Chaine de caracteres serialisee des bateaux
     */
    private void deserializeBateaux(String serializedData) throws DataErrorException {
        if (serializedData.isEmpty()) { // Si pas de save de bateaux
            return;
        }

        try {
            String[] splitData = serializedData.split("\\|");
            for (String data : splitData) {
                // Deserialisation du poisson
            }
        } catch (Exception e) {
            System.out.println("Echec du chargement des bateaux.");
            throw new DataErrorException("Donnees corrompues");
        }
    }

    /**
     * Serialise et concatene les donnees des poissons en
     * les separant par le caractere "|"
     * 
     * @return ______ (String) : Chaine de caracteres serialisee des poissons
     */
    private String serializePoissons() {
        String serializedData = "";
        for (Poisson poisson : stockagePoissons) {
            // Serialisation du poisson
            serializedData += poisson.getId() + "$" + poisson.getRarete() + "|";
        }
        return serializedData;
    }

    /**
     * Deserialise et les donnees des poissons separees par le caractere "|"
     * 
     * @param serializedData (String) : Chaine de caracteres serialisee des poissons
     */
    private void deserializePoissons(String serializedData) throws DataErrorException {
        if (serializedData.isEmpty()) { // Si pas de save de poissons
            return;
        }

        try {
            String[] splitData = serializedData.split("\\|");
            for (String data : splitData) {
                // Deserialisation du poisson
                String[] poissonData = data.split("\\$");
                stockagePoissons.add(new Poisson(Integer.valueOf(poissonData[0]), Integer.valueOf(poissonData[1])));
            }
        } catch (Exception e) {
            System.out.println("Echec du chargement des poissons.");
            throw new DataErrorException("Donnees corrompues");
        }
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
    private void deserializeArgent(String serializedData) throws DataErrorException {
        if (serializedData.isEmpty()) { // Si pas de save d'argent
            return;
        }

        try {
            argent = Integer.valueOf(serializedData);
        } catch (Exception e) {
            System.out.println("Echec du chargement de l'argent.");
            throw new DataErrorException("Donnees corrompues");
        }
    }

}
