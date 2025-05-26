package io.github.ProjetLong.DataManager;

import java.util.HashMap;
import java.util.Map;

import io.github.ProjetLong.equipementetmodule.CanneAPeche;
import io.github.ProjetLong.equipementetmodule.ModuleBateau;
import io.github.ProjetLong.equipementetmodule.Stockage;
import io.github.ProjetLong.equipementetmodule.Voile;

public class SerializerModule extends SerializerBaseClass<ModuleBateau> {

    private static final Map<Class<? extends ModuleBateau>, String> modulesCodes;

    private final SerializerCanneAPeche canneAPecheSerialiser;
    private final SerializerStockage stockageSerialiser;
    private final SerializerVoile voileSerialiser;

    public SerializerModule() {
        canneAPecheSerialiser = new SerializerCanneAPeche();
        stockageSerialiser = new SerializerStockage();
        voileSerialiser = new SerializerVoile();
    }

    static {
        modulesCodes = new HashMap<>();

        modulesCodes.put(CanneAPeche.class, "Canne");
        modulesCodes.put(Stockage.class, "Stockage");
        modulesCodes.put(Voile.class, "Voile");
    }

    /**
     * Serialise un objet de type ModuleBateau sous la forme :
     * type|[CHAINE DU MODULE SERIALISE]
     * 
     * @param element          (ModuleBateau) : Objet a serialiser
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (String) : Chaine de caractere serialisee
     */
    @Override
    public String serializeElement(ModuleBateau element, int compositionLevel) {
        if (element == null) {
            return "";
        }

        String type = getType(element);
        return type + "|" + serializeGeneric(element, type, compositionLevel);
    }

    /**
     * Deserialise un objet de type ModuleBateau etant sous la forme :
     * type|[CHAINE DU MODULE SERIALISE]
     * 
     * @param element          (String) : Chaine de caractere serialisee
     * @param compositionLevel (int) : Niveau de composition de l'objet
     * @return _______ (ModuleBateau) : Objet deserialise
     */
    @Override
    public ModuleBateau deserializeElement(String element, int compositionLevel) {
        try {
            String[] moduleData = element.split("\\|");
            ModuleBateau newModule = deserializeGeneric(moduleData[1], moduleData[0], compositionLevel);

            return newModule;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * Permet de recuperer le type du Module
     * 
     * @param element (ModuleBateau) : Module dont on veut le type
     * 
     * @return _______ (String) : Type du Module
     */
    private String getType(ModuleBateau element) {
        try {
            return modulesCodes.get(element.getClass());
        } catch (Exception e) {
            return "";
        }
    }

    /*
     * Permet de serialiser tout objet de type ModuleBateau
     * 
     * @param element (ModuleBateau) : Module a serialiser
     * 
     * @param type (String) : Type de module (Canne, Stockage, Voile, ...)
     * 
     * @param compositionLevel (int) : Niveau de composition de la serialisation
     * 
     * @return serializedElement (String) : Module serialise
     */
    private String serializeGeneric(ModuleBateau element, String type, int compositionLevel) {
        try {
            String serializedElement = "";
            switch (type) {
                case "Canne":
                    serializedElement = canneAPecheSerialiser.serializeElement((CanneAPeche) element, compositionLevel);
                    break;
                case "Stockage":
                    serializedElement = stockageSerialiser.serializeElement((Stockage) element, compositionLevel);
                    break;
                case "Voile":
                    serializedElement = voileSerialiser.serializeElement((Voile) element, compositionLevel);
                    break;
                default:
                    break;
            }
            return serializedElement;
        } catch (Exception e) {
            return "";
        }
    }

    /*
     * Permet de deserialiser tout objet de type ModuleBateau
     * 
     * @param element (String) : Chaine de caractere serialisee
     * 
     * @param type (String) : Type de module (Canne, Stockage, Voile, ...)
     * 
     * @param compositionLevel (int) : Niveau de composition de la serialisation
     * 
     * @return deserializedElement (ModuleBateau) : Module deserialise
     */
    private ModuleBateau deserializeGeneric(String element, String type, int compositionLevel) {
        try {
            ModuleBateau deserializedElement = null;
            switch (type) {
                case "Canne":
                    deserializedElement = canneAPecheSerialiser.deserializeElement(element, compositionLevel);
                    break;
                case "Stockage":
                    deserializedElement = stockageSerialiser.deserializeElement(element, compositionLevel);
                    break;
                case "Voile":
                    deserializedElement = voileSerialiser.deserializeElement(element, compositionLevel);
                    break;
                default:
                    break;
            }
            return deserializedElement;
        } catch (Exception e) {
            return null;
        }
    }

}
