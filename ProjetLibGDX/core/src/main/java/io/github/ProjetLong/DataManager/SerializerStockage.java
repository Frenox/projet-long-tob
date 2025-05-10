package io.github.ProjetLong.DataManager;

import java.util.List;

import io.github.ProjetLong.Stockage;
import io.github.ProjetLong.ZonesPeche.Poisson;


public class SerializerStockage extends SerializerBaseClass<Stockage> {

    private final SerializerPoisson poissonSerializer;

    public SerializerStockage() {
        poissonSerializer = new SerializerPoisson();
    }
    
    @Override
    public String serializeElement(Stockage element, int compositionLevel) {
        if (element == null) {
            return "";
        }
        
        String separateur = getSeparateur(compositionLevel);
        String contenu = poissonSerializer.serializeListData(element.getContenu(), compositionLevel+1);
        return element.getNiveau() + separateur + contenu;
    }
    @Override
    public Stockage deserializeElement(String element, int compositionLevel) throws DataErrorException {
        try {
            String[] stockageData = element.split(getSeparateur(compositionLevel));
            Stockage newStockage = new Stockage(Integer.parseInt(stockageData[0]));
            
            List<Poisson> contenu = poissonSerializer.deserializeListData(stockageData[1], compositionLevel+1);
            for (Poisson poisson : contenu) {
                newStockage.addPoisson(poisson);
            }
    
            return newStockage;
        } catch (Exception e) {
            throw new DataErrorException();
        }
    }

}
