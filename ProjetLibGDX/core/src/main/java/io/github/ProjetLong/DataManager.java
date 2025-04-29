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


public class DataManager {
    
    private List<Bateau> bateaux;

    /**
     * Charge un nouveau slot de jeu (sans donnees)
     */
    public DataManager() {
        bateaux = new ArrayList<Bateau>();

        //...
    }

    /**
     * Charge le slot de jeu donne (avec ses donnees)
     */
    public DataManager(String slotName) {
        bateaux = new ArrayList<Bateau>();

        //...

        loadGame(slotName);
    }



    /**
     * Serialise et sauvegarde les donnees du jeu au format JSON 
     * dans le fichier du slot de jeu actuel
     * @return ______ (bool) : La sauvegarde a-t-elle reussi
     */
    public boolean saveGame(String slotName) {
        Map<String, Object> data = new HashMap<>();
        
        try {
            //Ajout des donnees a sauvegarder
            data.put("Bateaux", serializeBateaux());

            ObjectMapper mapper = new ObjectMapper();
            //Ecriture des donnees
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(slotName + ".json"), data);

            System.out.println("Jeu sauvegarde dans " + slotName + ".json");
        } catch (Exception e) {
            System.out.println("Echec de la sauvegarde.");
            return false;
        }

        return true;
    }
    /**
     * Deserialise et charge les donnees du jeu au format JSON 
     * depuis le fichier du slot de jeu sekectionne
     * @return ______ (bool) : Le chargement a-t-il reussi
     */
    public boolean loadGame(String slotName) {
        Map<String, Object> data = new HashMap<>();

        try {
            //Ouverture du fichier
            File dataFile = new File(slotName + ".json");
            
            ObjectMapper mapper = new ObjectMapper();
            //Lecture des donnees
            data = mapper.readValue(dataFile, Map.class);

            System.out.println("Jeu charge depuis " + slotName + ".json");
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
            serializedData += "" + "|";
        }
        return serializedData;
    }
    /**
     * Deserialise et les donnees des bateaux separees par le caractere "|"
     * @param serializedData (String) : Chaine de caracteres serialisee des bateaux
     */
    private void deserializeBateaux(String serializedData) {
        String[] splitData = serializedData.split("|");
        for (String data : splitData) {
            // Deserialisation du bateau
        }
    }

}
