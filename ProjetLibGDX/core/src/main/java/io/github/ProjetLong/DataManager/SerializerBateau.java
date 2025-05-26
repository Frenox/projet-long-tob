package io.github.ProjetLong.DataManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.ProjetLong.Bateau;
import io.github.ProjetLong.CanneAPeche;
import io.github.ProjetLong.ModuleBateau;
import io.github.ProjetLong.Stockage;
import io.github.ProjetLong.Voile;
import io.github.ProjetLong.Barque;
import io.github.ProjetLong.Corvette;
import io.github.ProjetLong.Fregate;
import io.github.ProjetLong.Vaisseau;
import io.github.ProjetLong.Voilier;


public class SerializerBateau extends SerializerBaseClass<Bateau> {

    private static final Map<Class<? extends Bateau>, String> bateauxCodes;

    private final SerializerStockage stockageSerializer;
    private final SerializerCanneAPeche canneAPecheSerializer;
    private final SerializerVoile voileSerializer;

    public SerializerBateau() {
        stockageSerializer = new SerializerStockage();
        canneAPecheSerializer = new SerializerCanneAPeche();
        voileSerializer = new SerializerVoile();
    }

    static {
        bateauxCodes = new HashMap<>();
        
        bateauxCodes.put(Barque.class, "Barque");
        bateauxCodes.put(Corvette.class, "Corvette");
        bateauxCodes.put(Fregate.class, "Fregate");
        bateauxCodes.put(Vaisseau.class, "Vaisseau");  
        bateauxCodes.put(Voilier.class, "Voilier");
    }
    
    /**
     * Serialise un objet de type Bateau sous la forme :
     * type|Nom#-1-#Modele#-1-#ListeStockage#-1-#CanneAPeche#-1-#ListeCannesAPeche#-1-#ListeVoiles
     * 
     * @param element (Bateau) : Objet a serialiser
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (String) : Chaine de caractere serialisee
     */
    @Override
    public String serializeElement(Bateau element, int compositionLevel) {
        if (element == null) {
            return "";
        }
        
        String separateur = getSeparateur(compositionLevel);

        String type = getType(element);

        String stockage = stockageSerializer.serializeListData(element.getStockage(), compositionLevel+1);
        String canneAPeche = canneAPecheSerializer.serializeElement(element.getEquipedCanne(), compositionLevel+1);
        String listeCanneAPeches = canneAPecheSerializer.serializeListData(element.getCannes(), compositionLevel+1);
        String listeVoiles = voileSerializer.serializeListData(element.getVoiles(), compositionLevel+1);

        return    type + "|" + element.getName() + separateur + element.getModeleName() + 
                  separateur + stockage + separateur + canneAPeche + 
                  separateur + listeCanneAPeches + separateur + listeVoiles;
    }

    /**
     * Deserialise un objet de type Bateau etant sous la forme :
     * type|Nom#-1-#Modele#-1-#ListeStockage#-1-#CanneAPeche#-1-#ListeCannesAPeche#-1-#ListeVoiles
     * 
     * @param element (String) : Chaine de caractere serialisee
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (Bateau) : Objet deserialise
     */
    @Override
    public Bateau deserializeElement(String element, int compositionLevel){
        try {
            String[] data = element.split("\\|");
            String[] bateauData = data[1].split(getSeparateur(compositionLevel));
            Bateau newBateau = creerBateau(data[0]);
            
            newBateau.setName(bateauData[0]);
            newBateau.setModeleName(bateauData[1]);

            
            List<Stockage> listeStockages = stockageSerializer.deserializeListData(bateauData[2], compositionLevel+1);
            for (Stockage stockage : listeStockages) {
                newBateau.addStockage(stockage);
            }
            
            newBateau.setEquipedCanne(canneAPecheSerializer.deserializeElement(bateauData[3], compositionLevel+1));

            List<CanneAPeche> listeCannes = canneAPecheSerializer.deserializeListData(bateauData[4], compositionLevel+1);
            for (CanneAPeche canne : listeCannes) {
                 newBateau.addCannes(canne);
            }

            List<Voile> listeVoiles = voileSerializer.deserializeListData(bateauData[5], compositionLevel+1);
            for (Voile voile : listeVoiles) {
                 newBateau.addVoile(voile);
            }
    
            return newBateau;
        } catch (Exception e) {
            return null;
        }
    }

    /* Permet de recuperer le type du Bteau
     * @param element (Bateau) : bateau dont on veut le type
     * @return _______ (String) : Type du bateau
     */
    private String getType(Bateau element) {
        try {
            return bateauxCodes.get(element.getClass());
        } catch (Exception e) {
            return "";
        }
    }

    /* Permet de creer un bateau du bon type
     * @param type (String) : Type de module (Canne, Stockage, Voile, ...)
     * @return newBateau (bateau) : Bateau
     */
    private Bateau creerBateau(String type) {
        try {
            Bateau newBateau = null;
            switch (type) {
                case "Voilier":
                    newBateau = new Voilier();
                    break;
                case "Vaisseau":
                    newBateau = new Vaisseau();
                    break;
                case "Fregate":
                    newBateau = new Fregate();
                    break;
                case "Corvette":
                    newBateau = new Corvette();
                    break;
                case "Barque":
                    newBateau = new Barque();
                    break;
                default:
                    break;
            }
            return newBateau;
        } catch (Exception e) {
            return null;
        }
    }

}
