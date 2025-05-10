package io.github.ProjetLong.DataManager;

import java.util.ArrayList;
import java.util.List;


/*
 * Infos serialisation:
 *      Separateur d'elements : #niveauComposition#     -> #2#
 */


public abstract class SerializerBaseClass<T> {
    
    protected String getSeparateur(int compositionLevel) {
        return "#-" + compositionLevel + "-#";
    }

    public abstract String serializeElement(T element, int compositionLevel);
    public abstract T deserializeElement(String element, int compositionLevel) throws DataErrorException;

    /**
     * Serialise et concatene les donnees de la liste en parametre en 
     * les separant par le caractere correspondant au niveau de composition
     * @param inVariable (List<T>) : Liste de variables a serialiser
     * @param compositionLevel (int) : Niveau de composition de la serialisation
     * @return ______ (String) : Chaine de caracteres serialisee des bateaux
     */
    public String serializeListData(List<T> inVariable, int compositionLevel) {
        if (inVariable.isEmpty()) {
            return getSeparateur(compositionLevel) + getSeparateur(compositionLevel);
        }
        String serializedData = "";
        String separateur = getSeparateur(compositionLevel);
        for (T element : inVariable) {      
            serializedData += serializeElement(element, compositionLevel+1) + separateur;
        }
        return serializedData;
    }    
    /**
     * Deserialise et les donnees de la liste en parametre en 
     * separees par le caractere correspondant au niveau de composition
     * @param serializedData (String) : Chaine de caracteres serialisee
     * @param compositionLevel (int) : Niveau de composition de la serialisation
     */
    public List<T> deserializeListData(String serializedData, int compositionLevel) throws DataErrorException {
        if (serializedData.isEmpty()) { // Si pas de save de l'element
            return new ArrayList<>();
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
