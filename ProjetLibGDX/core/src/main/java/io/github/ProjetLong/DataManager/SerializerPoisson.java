package io.github.ProjetLong.DataManager;

import io.github.ProjetLong.ZonesPeche.Poisson;


public class SerializerPoisson extends SerializerBaseClass<Poisson> {
    
    @Override
    public String serializeElement(Poisson element, int compositionLevel) {
        if (element == null) {
            return "";
        }
        
        return element.getId() + getSeparateur(compositionLevel) + element.getRarete();
    }
    @Override
    public Poisson deserializeElement(String element, int compositionLevel) throws DataErrorException {
        try {
            String[] poissonData = element.split(getSeparateur(compositionLevel));
            return new Poisson(Integer.parseInt(poissonData[0]), Integer.parseInt(poissonData[1]));
        } catch (Exception e) {
            throw new DataErrorException();
        }
    }

}
