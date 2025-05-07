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


// DOIT AJOUTER LE SEPARATEUR FINAL EN PARAMETRE DE (DE)SERIALISATION POUR TRAITER LES CAS SIMPLES ET COMPOSITES
// OU RAJOUTER UN PARAMETRE "ESTCOMPOSITE" ET AVOIR LE SEPARATEUR EN TERNAIRE

/*
 * Infos serialisation:
 *      Separateur d'elements : #niveauComposition#     -> #2#
 * 
 *      Format de donnees des poissons : ID$rarete      ->       1$2
 *      Format de donnees de l'argent :  valeur         ->       14
 *      Format de donnees des bateaux :  modeleName!name!tailleMax![STOCKAGE]![CANNES A PECHE]![MODULES]    ->     modele1!nom1!14![...]![...]![...]
 *      Format de donnees du stockage :  niveau^nom^tailleMax^[POISSONS]         ->       2^nom1^12^[...]
 */


public abstract class SerializerBaseClass<T> {
    
    protected String getSeparateur(int compositionLevel) {
        return "#-" + compositionLevel + "-#";
    }

    protected abstract String serializeElement(T element, int compositionLevel);
    protected abstract T deserializeElement(String element, int compositionLevel) throws DataErrorException;

    /**
     * Serialise et concatene les donnees de la liste en parametre en 
     * les separant par le caractere correspondant au niveau de composition
     * @param inVariable (List<T>) : Liste de variables a serialiser
     * @param compositionLevel (int) : Niveau de composition de la serialisation
     * @return ______ (String) : Chaine de caracteres serialisee des bateaux
     */
    public String serializeListData(List<T> inVariable, int compositionLevel) {
        String serializedData = "";
        String separateur = getSeparateur(compositionLevel);
        for (T element : inVariable) {      
            serializedData += serializeElement(element, compositionLevel+1) + separateur;
        }
        return serializedData;
    }    
    /**
     * Deserialise et les donnees des bateaux separees par le caractere "|"
     * @param serializedData (String) : Chaine de caracteres serialisee
     * @param compositionLevel (int) : Niveau de composition de la serialisation
     */
    public List<T> deserializeListData(String serializedData, int compositionLevel) throws DataErrorException {
        if (serializedData.isEmpty()) { // Si pas de save de l'element
            return new ArrayList<T>();
        }

        try {
            String separateur = getSeparateur(compositionLevel);
            String[] splitData = serializedData.split(separateur);

            List<T> deserializedList = new ArrayList<T>();
            
            for (String element : splitData) {
                deserializedList.add(deserializeElement(element, compositionLevel+1));
            }
            return deserializedList;

        } catch (Exception e) {
            throw new DataErrorException("Donnees corrompues");
        }
    }
}
