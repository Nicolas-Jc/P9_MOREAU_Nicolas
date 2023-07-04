package com.mediscreen.assessment.constant;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Triggers {

    public static List<String> getTriggers() {
        return Arrays.asList(
                "Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Anormal",
                "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps"
        );
    }


}
