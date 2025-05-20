package io.github.ProjetLong.DataManager;

import java.util.List;

import io.github.ProjetLong.Bateau;
import io.github.ProjetLong.CanneAPeche;
import io.github.ProjetLong.Stockage;
import io.github.ProjetLong.Voile;


public class SerializerBateau extends SerializerBaseClass<Bateau> {

    private final SerializerStockage stockageSerializer;
    private final SerializerCanneAPeche canneAPecheSerializer;
    private final SerializerVoile voileSerializer;

    public SerializerBateau() {
        stockageSerializer = new SerializerStockage();
        canneAPecheSerializer = new SerializerCanneAPeche();
        voileSerializer = new SerializerVoile();
    }
    
    @Override
    public String serializeElement(Bateau element, int compositionLevel) {
        if (element == null) {
            return "";
        }
        
        String separateur = getSeparateur(compositionLevel);

        String stockage = stockageSerializer.serializeListData(element.getStockage(), compositionLevel+1);
        String canneAPeche = canneAPecheSerializer.serializeElement(element.getEquipedCanne(), compositionLevel+1);
        String listeCanneAPeches = canneAPecheSerializer.serializeListData(element.getCannes(), compositionLevel+1);
        String listeVoiles = voileSerializer.serializeListData(element.getVoiles(), compositionLevel+1);

        return element.getName() + separateur + element.getModeleName() + separateur + element.getTailleMax() +
                  separateur + stockage + separateur + canneAPeche + separateur + listeCanneAPeches +
                  separateur + listeVoiles;
    }
    @Override
    public Bateau deserializeElement(String element, int compositionLevel){
        try {
            String[] bateauData = element.split(getSeparateur(compositionLevel));
            Bateau newBateau = new Bateau(Integer.parseInt(bateauData[2]));
            
            newBateau.setName(bateauData[0]);
            newBateau.setModeleName(bateauData[1]);

            
            List<Stockage> listeStockages = stockageSerializer.deserializeListData(bateauData[3], compositionLevel+1);
            for (Stockage stockage : listeStockages) {
                newBateau.addStockage(stockage);
            }
            
            newBateau.setEquipedCanne(canneAPecheSerializer.deserializeElement(bateauData[4], compositionLevel+1));

            List<CanneAPeche> listeCannes = canneAPecheSerializer.deserializeListData(bateauData[5], compositionLevel+1);
            for (CanneAPeche canne : listeCannes) {
                 newBateau.addCannes(canne);
            }

            List<Voile> listeVoiles = voileSerializer.deserializeListData(bateauData[6], compositionLevel+1);
            for (Voile voile : listeVoiles) {
                 newBateau.addVoile(voile);
            }
    
            return newBateau;
        } catch (Exception e) {
            return null;
        }
    }

}
