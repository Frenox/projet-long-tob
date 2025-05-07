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

import io.github.ProjetLong.Stockage;
import io.github.ProjetLong.ZonesPeche.Poisson;


public class SerializerListeStockage extends SerializerBaseClass<Stockage> {

    private SerializerListePoisson poissonSerializer;

    public SerializerListeStockage() {
        poissonSerializer = new SerializerListePoisson();
    }
    
    protected String serializeElement(Stockage element, int compositionLevel) {
        String contenu = poissonSerializer.serializeListData(element.getContenu(), compositionLevel+1);
        return element.getNiveau() + getSeparateur(compositionLevel) + contenu;
    }
    protected Stockage deserializeElement(String element, int compositionLevel) throws DataErrorException {
        try {
            String[] stockageData = element.split(getSeparateur(compositionLevel));
            Stockage newStockage = new Stockage(Integer.valueOf(stockageData[0]));
            
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
