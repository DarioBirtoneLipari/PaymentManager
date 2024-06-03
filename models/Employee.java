package models;

import java.time.LocalDate;
import java.util.HashMap;

public class Employee{

    private String codiceF;
    private String nome;
    private String cognome;
    private String seniority;
    private LocalDate dataAssunzione;
    private LocalDate dataTermine;
    private LocalDate dataInizioStraordinario;
    private LocalDate dataFineStraordinario;
    private Double totalPayment = 0.00;
    private HashMap<String, Timbratura> emplSignaturesIN = new HashMap<>();
    private HashMap<String, Timbratura> emplSignaturesOUT = new HashMap<>();
    
    
    
    public String getCodiceF() {
        return codiceF;
    }
    public void setCodiceF(String codiceF) {
        this.codiceF = codiceF;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getSeniority() {
        return seniority;
    }
   
    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }
    
    public LocalDate getDataAssunzione() {
        return dataAssunzione;
    }
    public void setDataAssunzione(LocalDate dataAssunzione) {
        this.dataAssunzione = dataAssunzione;
    }
    
    public LocalDate getDataTermine() {
        return dataTermine;
    }
    public void setDataTermine(LocalDate dataTermine) {
        this.dataTermine = dataTermine;
    }
    
    public LocalDate getDataInizioStraordinario() {
        return dataInizioStraordinario;
    }
    public void setDataInizioStraordinario(LocalDate dataInizioStraordinario) {
        this.dataInizioStraordinario = dataInizioStraordinario;
    }
    
    public LocalDate getDataFineStraordinario() {
        return dataFineStraordinario;
    }
    public void setDataFineStraordinario(LocalDate dataFineStraordinario) {
        this.dataFineStraordinario = dataFineStraordinario;
    }
    public Double getTotalPayment() {
        return totalPayment;
    }
    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }
  
    public HashMap<String, Timbratura> getEmplSignaturesIN() {
        return emplSignaturesIN;
    }
    public void setEmplSignaturesIN(HashMap<String, Timbratura> emplSignaturesIN) {
        this.emplSignaturesIN = emplSignaturesIN;
    }
    public HashMap<String, Timbratura> getEmplSignaturesOUT() {
        return emplSignaturesOUT;
    }
    public void setEmplSignaturesOUT(HashMap<String, Timbratura> emplSignaturesOUT) {
        this.emplSignaturesOUT = emplSignaturesOUT;
    }
}