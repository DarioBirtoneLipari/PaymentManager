package services;
import java.io.*;
import java.time.*;
import java.util.*;

import models.Employee;
import models.Timbratura;

public class PaymentManager {
    

    

    HashMap<String,Employee> employees = new HashMap<>();
    HashMap<String,Timbratura> signatures = new HashMap<>();
    HashMap<String,String> calendar = new HashMap<>();

    private static final String PATH_EMPLOYEES = "java\\storage\\dipendenti.csv";
    private static final String PATH_SIGNATURES = "java\\storage\\timbrature.csv";
    private static final String DEFAULT_DATE = "0000-01-01"; //Date for ordinary workers

    


    public void exe(){
        populateSignatures(signatures);
        populateEmployees(employees);
        createPaymentReport(employees);
    }


    public void populateEmployees(HashMap<String,Employee> employees){//ordinary flow works
        try{
            File inFile = new File(PATH_EMPLOYEES);
            Scanner scanner = new Scanner(inFile);
            String str;
            String[] tokens;

            while (scanner.hasNext()) {
                str = scanner.nextLine();        
                tokens = str.split(",");           
                String cfEmployee = tokens[0];
                String firstName = tokens[1];
                String surName = tokens[2];
                String seniority = tokens[3];
                LocalDate dataAssunzione = LocalDate.parse(tokens[4]);
                LocalDate dataTermine= LocalDate.parse(tokens[5]);
                LocalDate dataInizioStraordinario = LocalDate.parse(tokens[6]);
                LocalDate dataFineStraordinario = LocalDate.parse(tokens[7]);

                Employee employee = new Employee();
                
                employee.setCodiceF(cfEmployee);
                employee.setNome(firstName);
                employee.setCognome(surName);
                employee.setSeniority(seniority);
                employee.setDataAssunzione(dataAssunzione);
                employee.setDataTermine(dataTermine);
                employee.setDataInizioStraordinario(dataInizioStraordinario);
                employee.setDataFineStraordinario(dataFineStraordinario);
                populateSignaturesEmployees(signatures, employee);
                OrdinaryPaymentCalculator(employee);
                OverTimePaymentCalculator(employee);
                employees.put(cfEmployee, employee);
            }
            scanner.close();
        }
        catch(FileNotFoundException e){
            System.err.println("File not found exception");
        }
    }
    
    
    public void populateSignatures(HashMap<String,Timbratura> signatures){//ordinary flow works
        try{
            File inFile = new File(PATH_SIGNATURES);
            Scanner scanner = new Scanner(inFile);
            String str;
            String[] tokens;

            while (scanner.hasNext()) {
                str = scanner.nextLine();        
                tokens = str.split(",");           
                String cfEmployee = tokens[0];
                String typeSignature = tokens[1]; 
                cfEmployee = cfEmployee + typeSignature + tokens[2];
                LocalDate date = LocalDate.parse(tokens[2]);
                LocalTime time = LocalTime.parse(tokens[3]);
                
                Timbratura signature = new Timbratura();
                
                signature.setCodiceF(cfEmployee);
                signature.setTypeSignature(typeSignature);
                signature.setDate(date);
                signature.setTime(time);
                signature.setItalianFestivities();
                signatures.put(cfEmployee, signature);
                calendar.put(date.toString(),date.toString());
               
            }
         
            scanner.close();
        }
        catch(FileNotFoundException e){
            System.err.println("File not found exception");
        }

    }

    //TODO: adjust the saving path
    public void createPaymentReport(HashMap<String,Employee> employees){
        for (Employee empl : employees.values()){
            String cf = empl.getCodiceF();
            String surnameName = empl.getCognome() + empl.getNome();
            String outputPath = "java\\output\\";//
            String nameFile = outputPath + cf + surnameName + ".txt";
            File employeePaymentReport = new File(nameFile);
            writeInReport(employeePaymentReport, empl);
        }
    }


    public void populateSignaturesEmployees(HashMap<String,Timbratura> signatures, Employee empl){//ordinary flow works   
        for (String signaturesCF : signatures.keySet()){
            for (String date : calendar.keySet()){

                String cfIN = empl.getCodiceF() + "in" + date;
                String cfOUT = empl.getCodiceF() + "out" + date;

            if (signaturesCF.equals(cfIN)){
                String signatureDate = signatures.get(cfIN).getDate().toString();
                String signatureKey = signatureDate; 
                empl.getEmplSignaturesIN().put(signatureKey,signatures.get(cfIN));
            }
            
            else if(signaturesCF.equals(cfOUT)){
                String signatureDate = signatures.get(cfOUT).getDate().toString();
                String signatureKey = signatureDate; 
                empl.getEmplSignaturesOUT().put(signatureKey,signatures.get(cfOUT));
            }
            
            else{
                //do nothing block
                }
            
            }
        
        }
    
    }   
    

    public void OrdinaryPaymentCalculator(Employee employee){
        HashMap<String,Timbratura> signaturesIN = employee.getEmplSignaturesIN();
        HashMap<String,Timbratura> signaturesOUT = employee.getEmplSignaturesOUT();
        for(String dateStringIn : signaturesIN.keySet()){
            for (String dateStringOut : signaturesOUT.keySet()){
                
                if (dateStringIn.equals(dateStringOut)){
                    Double signIN  = (double)(signaturesIN.get(dateStringIn).getTime().getHour() +
                                     signaturesIN.get(dateStringIn).getTime().getMinute() / 60.0);

                    Double signOUT  = (double)(signaturesOUT.get(dateStringOut).getTime().getHour() +
                                     signaturesOUT.get(dateStringOut).getTime().getMinute() / 60.0);
                    
                    if (signOUT >= 18.00){
                        signOUT = 18.00;
                    }

                    if (signIN <= 9.00){
                        signIN = 9.00;
                    }

                    Double day = signOUT - signIN - 1.00;
                    
                    if(employee.getSeniority().equals("junior") &&
                       employee.getDataInizioStraordinario().toString().equals("0000-01-01") && 
                       employee.getDataFineStraordinario().toString().equals("0000-01-01"))
                    {
                        double payment = day * 15.00;
                        employee.setTotalPayment(employee.getTotalPayment() + payment);
                    }

                    else if(employee.getSeniority().equals("middle") &&
                            employee.getDataInizioStraordinario().toString().equals(DEFAULT_DATE) &&
                            employee.getDataFineStraordinario().toString().equals(DEFAULT_DATE)){
                        
                                double payment = day * 20.00;
                                employee.setTotalPayment(employee.getTotalPayment() + payment);
                            }

                    else if(employee.getSeniority().equals("senior") &&
                            employee.getDataInizioStraordinario().toString().equals(DEFAULT_DATE) &&
                            employee.getDataFineStraordinario().toString().equals(DEFAULT_DATE)){
                                
                                double payment = day * 25.00;
                                employee.setTotalPayment(employee.getTotalPayment() + payment);
                            }
                   
                    else{
                        //do nothing block
                    } 
                   
                }
            
            }

        }
    
    }


    public void OverTimePaymentCalculator(Employee employee){
        HashMap<String,Timbratura> signaturesIN = employee.getEmplSignaturesIN();
        HashMap<String,Timbratura> signaturesOUT = employee.getEmplSignaturesOUT();
        for(String dateStringIn : signaturesIN.keySet()){
            for (String dateStringOut : signaturesOUT.keySet()){
                if (dateStringIn.equals(dateStringOut)){
                    Double signIN  = (double)(signaturesIN.get(dateStringIn).getTime().getHour() +
                                     signaturesIN.get(dateStringIn).getTime().getMinute() / 60.0);
                    Double signOUT  = (double)(signaturesOUT.get(dateStringOut).getTime().getHour() +
                                     signaturesOUT.get(dateStringOut).getTime().getMinute() / 60.0);
                    Double day = signOUT - signIN - 1.00;
                    
                    if(employee.getSeniority().equals("junior") &&
                       !employee.getDataInizioStraordinario().toString().equals(DEFAULT_DATE) && 
                        employee.getDataFineStraordinario().toString().equals(DEFAULT_DATE)){
                        if (signaturesIN.get(dateStringIn).getDate().getDayOfWeek().toString() == "SATURDAY"){
                            double payment = (day * 15.00)* 1.3;
                            employee.setTotalPayment(employee.getTotalPayment() + payment);
                        }
                        else if (signaturesIN.get(dateStringIn).getDate().getDayOfWeek().toString() == "SUNDAY" || 
                                 signaturesIN.get(dateStringIn).getItalianFestivities().containsKey(LocalDate.parse(dateStringIn))){
                            double payment = (day * 15.00)* 1.5;
                            employee.setTotalPayment(employee.getTotalPayment() + payment);
                        }

                        else if(signaturesIN.get(dateStringIn).getTypeSignature().equals("holdayIN")){
                            double payment = (day * 15.00)* 1.2;
                            employee.setTotalPayment(employee.getTotalPayment() + payment);
                        }
                        else{
                            double payment = day * 15.00;
                            employee.setTotalPayment(employee.getTotalPayment() + payment);
                        }
                    }

                    else if(employee.getSeniority().equals("middle") &&
                            !employee.getDataInizioStraordinario().toString().equals(DEFAULT_DATE) &&
                             employee.getDataFineStraordinario().toString().equals(DEFAULT_DATE)){
                        if (signaturesIN.get(dateStringIn).getDate().getDayOfWeek().toString() == "SATURDAY"){
                            double payment = (day * 20.00)* 1.3;
                            employee.setTotalPayment(employee.getTotalPayment() + payment);
                        }
                        else if (signaturesIN.get(dateStringIn).getDate().getDayOfWeek().toString() == "SUNDAY" || 
                                 signaturesIN.get(dateStringIn).getItalianFestivities().containsKey(LocalDate.parse(dateStringIn))){
                            double payment = (day * 20.00)* 1.5;
                            employee.setTotalPayment(employee.getTotalPayment() + payment);
                        }

                        else if(signaturesIN.get(dateStringIn).getTypeSignature().equals("holdayIN")){
                            double payment = (day * 15.00)* 1.2;
                            employee.setTotalPayment(employee.getTotalPayment() + payment);
                        }

                        else{
                            double payment = day * 20.00;
                            employee.setTotalPayment(employee.getTotalPayment() + payment);
                        }
                    }

                    else if(employee.getSeniority().equals("senior") &&
                            !employee.getDataInizioStraordinario().toString().equals(DEFAULT_DATE) &&
                             employee.getDataFineStraordinario().toString().equals(DEFAULT_DATE)
                            ){
                                if (signaturesIN.get(dateStringIn).getDate().getDayOfWeek().toString() == "SATURDAY"){
                                    double payment = (day * 25.00)* 1.3;
                                    employee.setTotalPayment(employee.getTotalPayment() + payment);
                            }
                                else if (signaturesIN.get(dateStringIn).getDate().getDayOfWeek().toString() == "SUNDAY" || 
                                         signaturesIN.get(dateStringIn).getItalianFestivities().containsKey(LocalDate.parse(dateStringIn))){
                                    double payment = (day * 25.00)* 1.5;
                                    employee.setTotalPayment(employee.getTotalPayment() + payment);
                                }
        
                                else if(signaturesIN.get(dateStringIn).getTypeSignature().equals("holdayIN")){
                                    double payment = (day * 15.00)* 1.2;
                                    employee.setTotalPayment(employee.getTotalPayment() + payment);
                                }
        
                                else{
                                    double payment = day * 55.00;
                                    employee.setTotalPayment(employee.getTotalPayment() + payment);
                                }
                    }
                    else{
                        //do nothing block
                    } 
                }
            }
        }
    }


    public void writeInReport(File Report, Employee employee){
        try {
            FileWriter myWriter = new FileWriter(Report.getName());
            
        
            HashMap<String,Timbratura> signaturesIN = employee.getEmplSignaturesIN();
            HashMap<String,Timbratura> signaturesOUT = employee.getEmplSignaturesOUT();
            for(String dateStringIn : signaturesIN.keySet()){
                for (String dateStringOut : signaturesOUT.keySet()){
                    String cfIN = employee.getCodiceF() + "in" + dateStringIn;
                    if (dateStringIn.equals(dateStringOut)){
                        
                        String cfOUT = employee.getCodiceF() + "out" + dateStringOut;
                String date = signatures.get(cfIN).getDate().toString();
                String timeStart = signatures.get(cfIN).getTime().toString();
                String timeEnd = signatures.get(cfOUT).getTime().toString();
                myWriter.write(date + " " + timeStart + " " + timeEnd + "\n");
                signaturesOUT.remove(cfOUT);
            }
            signaturesIN.remove(cfIN);
            }
        }
            myWriter.write(employee.getTotalPayment().toString());
            myWriter.close();
          } catch (IOException e) {
            System.err.println("An error's occurred.");
            e.printStackTrace();
        }
    }
}      