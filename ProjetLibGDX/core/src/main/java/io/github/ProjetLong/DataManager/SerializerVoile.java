package io.github.ProjetLong.DataManager;

import io.github.ProjetLong.Voile;


public class SerializerVoile extends SerializerBaseClass<Voile> {
    

    @Override
    public String serializeElement(Voile element, int compositionLevel) {
        if (element == null) {
            return "";
        }
        
        String separateur = getSeparateur(compositionLevel);
        return element.getNom() + separateur + element.getNiveau();
    }
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
