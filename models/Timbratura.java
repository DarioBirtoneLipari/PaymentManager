package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class Timbratura {
 
    private String codiceF;
    private String typeSignature;
    private LocalDate date;
    private LocalTime time;
    private HashMap<LocalDate,String> italianFestivities = new HashMap<>();

    
    public String getCoiceF() {
        return codiceF;
    }
    public void setCodiceF(String codiceF) {
        this.codiceF = codiceF;
    }
   
    public String getTypeSignature() {
        return typeSignature;
    }

    public void setTypeSignature(String typeSignature) {
        this.typeSignature = typeSignature;
    }
  
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public String getCodiceF() {
        return codiceF;
    }
    public HashMap<LocalDate, String> getItalianFestivities() {
        return italianFestivities;
    }
    public void setItalianFestivities() {
        //Populated with Gemini
        italianFestivities.put(LocalDate.of(2024, 1, 1), "Capodanno (New Year’s Day)");
        italianFestivities.put(LocalDate.of(2024, 1, 6), "Epifania (Epiphany)");
        italianFestivities.put(LocalDate.of(2024, 4, 14), "Pasqua (Easter Sunday)"); // Date varies
        italianFestivities.put(LocalDate.of(2024, 4, 15), "Pasquetta (Easter Monday)"); // Date varies
        italianFestivities.put(LocalDate.of(2024, 4, 25), "Festa della Liberazione (Liberation Day)");
        italianFestivities.put(LocalDate.of(2024, 5, 1), "Festa del Lavoro (International Workers Day)");
        italianFestivities.put(LocalDate.of(2024, 6, 2), "Festa della Repubblica (Republic Day)");
        italianFestivities.put(LocalDate.of(2024, 8, 15), "Assunzione di Maria (Assumption of Mary)");
        italianFestivities.put(LocalDate.of(2024, 11, 1), "Tutti i Santi (All Saints' Day)");
        italianFestivities.put(LocalDate.of(2024, 12, 8), "Immacolata Concezione (Immaculate Conception)");
        italianFestivities.put(LocalDate.of(2024, 12, 25), "Natale (Christmas)");
}

}
