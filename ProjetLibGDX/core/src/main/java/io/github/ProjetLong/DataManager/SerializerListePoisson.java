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

import io.github.ProjetLong.ZonesPeche.Poisson;


public class SerializerListePoisson extends SerializerBaseClass<Poisson> {
    
    protected String serializeElement(Poisson element, int compositionLevel) {
        return element.getId() + getSeparateur(compositionLevel) + element.getRarete();
    }
    protected Poisson deserializeElement(String element, int compositionLevel) throws DataErrorException {
        try {
            String[] poissonData = element.split(getSeparateur(compositionLevel));
            return new Poisson(Integer.valueOf(poissonData[0]), Integer.valueOf(poissonData[1]));
        } catch (Exception e) {
            throw new DataErrorException();
        }
    }

}
