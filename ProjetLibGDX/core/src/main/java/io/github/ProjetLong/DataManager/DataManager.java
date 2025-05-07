package io.github.ProjetLong.DataManager;

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
import io.github.ProjetLong.Stockage;
import io.github.ProjetLong.ZonesPeche.Poisson;


// DOIT AJOUTER LE SEPARATEUR FINAL EN PARAMETRE DE (DE)SERIALISATION POUR TRAITER LES CAS SIMPLES ET COMPOSITES
// OU RAJOUTER UN PARAMETRE "ESTCOMPOSITE" ET AVOIR LE SEPARATEUR EN TERNAIRE

/*
 * Infos serialisation:
 *      Separateur d'elements unitaires : |
 *      Separateur d'elements composites niveau 1 : !
 *      Separateur d'elements composites niveau 2 : @
 * 
 *      Format de donnees des poissons : ID$rarete      ->       1$2
 *      Format de donnees de l'argent :  valeur         ->       14
 *      Format de donnees des bateaux :  modeleName!name!tailleMax![STOCKAGE]![CANNES A PECHE]![MODULES]    ->     modele1!nom1!14![...]![...]![...]
 *      Format de donnees du stockage :  niveau^nom^tailleMax^[POISSONS]         ->       2^nom1^12^[...]
 */


public class DataManager {
    
    private List<Bateau> bateaux;
    private List<Poisson> stockagePoissons;
    private int argent;

    private SerializerListePoisson poissonSerializer;

    /**
     * Charge un nouveau slot de jeu (sans donnees)
     */
    public DataManager() {
        bateaux = new ArrayList<Bateau>();
        stockagePoissons = new ArrayList<Poisson>();
        argent = 0;

        //...

        poissonSerializer = new SerializerListePoisson();
    }

    /**
     * Charge le slot de jeu donne (avec ses donnees)
     * @param slot (String) : Slot de sauvegarde a charger
     */
    public DataManager(String slot) {
        bateaux = new ArrayList<Bateau>();
        stockagePoissons = new ArrayList<Poisson>();
        argent = 0;

        //...

        poissonSerializer = new SerializerListePoisson();

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
     * @param slot (String) : Slot de sauvegarde a charger
     * @return ______ (bool) : La sauvegarde a-t-elle reussi
     */
    public boolean saveGame(String slot) {
        Map<String, Object> data = new HashMap<>();
        
        try {
            //Ajout des donnees a sauvegarder
            data.put("Bateaux", serializeBateaux());
            data.put("StockagePoissons", poissonSerializer.serializeListData(stockagePoissons, 0));
            data.put("Argent", serializeArgent());

            ObjectMapper mapper = new ObjectMapper();
            //Ecriture des donnees
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
     * @param slot (String) : Slot de sauvegarde a charger
     * @return ______ (bool) : Le chargement a-t-il reussi
     */
    public boolean loadGame(String slot) {
        Map<String, String> data = new HashMap<>();

        try {
            //Ouverture du fichier
            File dataFile = new File(slot + ".json");
            
            ObjectMapper mapper = new ObjectMapper();
            //Lecture des donnees
            data = mapper.readValue(dataFile, Map.class);

            deserializeBateaux(data.get("Bateaux"));
            stockagePoissons = poissonSerializer.deserializeListData(data.get("StockagePoissons"), 0);
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
     * @return ______ (String) : Chaine de caracteres serialisee des bateaux
     */
    private String serializeBateaux() {
        String serializedData = "";
        for (Bateau bateau : bateaux) {
            // Serialisation du bateau
            serializedData += bateau.getModeleName() + "!";
            serializedData += bateau.getName() + "!";
            serializedData += bateau.getTailleMax() + "!";
            
            // Serialize lists

            serializedData += "" + "|";
        }
        return serializedData;
    }    
    /**
     * Deserialise et les donnees des bateaux separees par le caractere "|"
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
     * Serialise l'argent
     * @return ______ (String) : Chaine de caracteres serialisee de l'argent
     */
    private String serializeArgent() {
        return String.valueOf(argent);
    }
    /**
     * Deserialise et les donnees de l'argent
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
