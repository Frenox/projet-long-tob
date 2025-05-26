package io.github.ProjetLong.DataManager;

import java.util.List;

import io.github.ProjetLong.ZonesPeche.Poisson;
import io.github.ProjetLong.equipementetmodule.Stockage;

public class SerializerStockage extends SerializerBaseClass<Stockage> {

    private final SerializerPoisson poissonSerializer;

    public SerializerStockage() {
        poissonSerializer = new SerializerPoisson();
    }

    /**
     * Serialise un objet de type Stockage sous la forme :
     * Niveau#-1-#Contenu
     * 
     * @param element          (Stockage) : Objet a serialiser
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (String) : Chaine de caractere serialisee
     */
    @Override
    public String serializeElement(Stockage element, int compositionLevel) {
        if (element == null) {
            return "";
        }

        String separateur = getSeparateur(compositionLevel);
        String contenu = poissonSerializer.serializeListData(element.getContenu(), compositionLevel + 1);
        return element.getNiveau() + separateur + contenu;
    }

    /**
     * Deserialise un objet de type Stockage etant sous la forme :
     * Niveau#-1-#Contenu
     * 
     * @param element          (String) : Chaine de caractere serialisee
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (Stockage) : Objet deserialise
     */
    @Override
    public Stockage deserializeElement(String element, int compositionLevel) {
        try {
            String[] stockageData = element.split(getSeparateur(compositionLevel));
            Stockage newStockage = new Stockage(Integer.parseInt(stockageData[0]));

            List<Poisson> contenu = poissonSerializer.deserializeListData(stockageData[1], compositionLevel + 1);
            for (Poisson poisson : contenu) {
                newStockage.addPoisson(poisson);
            }

            return newStockage;
        } catch (Exception e) {
            return null;
        }
    }

}
