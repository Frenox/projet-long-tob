package io.github.ProjetLong.DataManager;

import io.github.ProjetLong.Voile;


public class SerializerVoile extends SerializerBaseClass<Voile> {
    
    /**
     * Serialise un objet de type Voile sous la forme :
     * Nom#-1-#Niveau
     * 
     * @param element (Voile) : Objet a serialiser
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (String) : Chaine de caractere serialisee
     */
    @Override
    public String serializeElement(Voile element, int compositionLevel) {
        if (element == null) {
            return "";
        }
        
        String separateur = getSeparateur(compositionLevel);
        return element.getNom() + separateur + element.getNiveau();
    }

    /**
     * Deserialise un objet de type Voile etant sous la forme :
     * Nom#-1-#Niveau
     * 
     * @param element (String) : Chaine de caractere serialisee
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (Voile) : Objet deserialise
     */
    @Override
    public Voile deserializeElement(String element, int compositionLevel) {
        try {
            String[] voileData = element.split(getSeparateur(compositionLevel));
            Voile newVoile = new Voile(voileData[0], Integer.parseInt(voileData[1]));
    
            return newVoile;
        } catch (Exception e) {
            return null;
        }
    }

}
