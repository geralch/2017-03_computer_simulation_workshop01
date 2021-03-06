package Logic;

import java.io.IOException;
import static java.lang.Math.ceil;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
/**
 * Class for generate pseudo-random numbers 
 * @author Geraldine Caicedo Hidalgo
 */
public class Gem {

    static final long module = 2147483647L;
    static final long multiplier = 48271L;
    static final long q = 44488L;
    static final long increase = 3399L;

    // Global Variable, initialice the seed with multiplier value diferent from 0
    static long r_seed = 12345678L;

    /**
     * Constructor Gem Class
     */
    public Gem() {
    }

    /**
     * Generator of pseudo-random numbers between 0 and 1 with GEM
     * @return the generate number
     */
    private double generateRandomGem() {
        double generated_number = 0.0;
        
        //This is for the big number multiplication overflow
        long hi = r_seed / q;
        long lo = r_seed - q * hi;
        long t = multiplier * lo - increase * hi;
        if (t > 0) {
            r_seed = t;
        } else {
            r_seed = t + module;
        }
        generated_number = ((double) r_seed / (double) module);
        
        return generated_number;
    }
    
    /**
     * Generator of pseudo-random numbers between 0 and 1 with java random function
     * @return the generate number
     */
    private double generateRandomJava(){
        double generated_number = 0.0;
        Random rnd = new Random();
        generated_number = rnd.nextDouble();
        return generated_number;
    }
    
    /**
     * Chose between GEM and Java generator of pseudo-random number
     * @param generator
     * @return 
     */
    private double choseGenerator(int generator){
        double generated_number = 0.0;
        if(generator == 0){
            generated_number = generateRandomGem();
        }
        if(generator == 1){
            generated_number = generateRandomJava();
        }
        return generated_number;
    }

    /**
     * Generate List of pseudo-random numbers depending of chosen generator and save it in a .txt file
     * @param generator
     * @return list of generates pseudo-random numbers
     * @throws IOException 
     */
    private List<Double> generateListRandomNumbers(int generator) throws IOException {
        List<Double> random_numbers = new LinkedList<>();
        List<String> random_numbers_strings = new LinkedList<>();

        // loop for generate the random numbers
        for (int i = 0; i < 10000; i++) {
            // if i is 1000 save the numbers in a txt file
            if (i == 1000) {
                Path file = Paths.get("GEM1000.txt");
                Files.write(file, random_numbers_strings, Charset.forName("UTF-8"));
            }
            double random_number = choseGenerator(generator);               //generate the random number according to chosen generator
            random_numbers_strings.add(String.valueOf(random_number));      //add the string value of random number into a new list
            random_numbers.add(random_number);                              //add the double value of random number into a list
        }
        // Save the list of numbers in a txt file
        Path file = Paths.get("GEM10000.txt");
        Files.write(file, random_numbers_strings, Charset.forName("UTF-8"));
        
        return random_numbers;
    }
    
    /**
     * Calculates the observed frequency according to the rank
     * @param start
     * @param end
     * @param random_numbers
     * @return the calculated observed frequency 
     */
    private int countOnRange(double start, double end, List<Double> random_numbers) {
        int cant = 0;
        for (int i = 0; i < random_numbers.size(); i++) {
            if (random_numbers.get(i) >= start && random_numbers.get(i) < end) {
                cant++;
            }
        }
        return cant;
    }
    
    /**
     * Calculates if generator is good with the chi squared test
     * @param radom_numbers
     */
    private void chiSquaredTest(List<Double> radom_numbers){
                
        double critical_chi_squared = 0;                                    
        double total_numbers = radom_numbers.size();                        //total number of random numbers
        
        if (total_numbers == 1000) {
            critical_chi_squared = 41.4217;                                 //calculates with the chi squared table and the degrees of freedom
        }
        if (total_numbers == 10000) {
            critical_chi_squared = 118.4980;                                //calculates with the chi squared table and the degrees of freedom
        }
        
        double classes_number = ceil(sqrt(total_numbers));                  //calculates the number of classes
        double degrees_freedom = classes_number -1 ;                        //claculates the degrees of freedom
        double expected_frequency = total_numbers / classes_number;         //calculates the expected frequency
        double calculated_chi_squared = 0;                                  //calculates the calculated chi squared
        System.out.println("Classes Number: " + classes_number);            
        System.out.println("Degrees of Freedom: " + degrees_freedom);
        System.out.println("Rank \t\t\t\t OF \t\t EF \t\t (EF-OF)^2/EF");
        
        int OFsum = 0;
        // loop for calculate the observed frequency, expected frequency and the error
        for (double i = 0; i < 1; i = i + (1 / classes_number)) {
            int observed_frequency = countOnRange(i, (i + (1 / classes_number)), radom_numbers);                //calculates the observed frequency
            OFsum += observed_frequency;
            double calculated_chi_squared_i = pow((expected_frequency - observed_frequency), 2) / expected_frequency;
            calculated_chi_squared += pow((expected_frequency - observed_frequency), 2) / expected_frequency;
            System.out.println("[" + i + " - " + (i + (1 / classes_number)) + ")" + "\t\t" + observed_frequency + "\t\t" + expected_frequency + "\t\t" + calculated_chi_squared_i);
        }
        
        System.out.println("Critical Chi Squared " + critical_chi_squared);
        System.out.println("Calculated Chi Squared: " + calculated_chi_squared);
        System.out.println("Total Obtained Frequency: " + OFsum);
        
        boolean good_generator = calculated_chi_squared <= critical_chi_squared;            //set if the generator is good
         
        System.out.println("Past the Test?: "+good_generator);
     
    }
    
     /**
     * Format the numbers to a douuble with a given number of decimals
     *
     * @param list list of pseudo-random numbers
     * @param decimals amount of decimals accepted
     * @return list of formated pseudo-random numbers
     */
    private List<Double> formatList(List<Double> list, String decimals) {
        List<Double> newList = new LinkedList<>();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat decimal = new DecimalFormat(decimals, otherSymbols);
        decimal.setRoundingMode(RoundingMode.UP);
        for (int i = 0; i < list.size(); i++) {
            newList.add(Double.parseDouble(decimal.format(list.get(i))));
        }
        return newList;
    }
    
    /**
     * Calculates if generator is good with the poker two test
     * @param radom_numbers
     */
    private void pokerTwoTest(List<Double> radom_numbers){
      
        List<Double> information = formatList(radom_numbers, "#.##");
        System.out.println("Class \t\t\t\t OF \t\t EF \t\t x2");
        int equals_two = 0;
        int equals_zero = 0;
        double critical_chi_squared = 2.7055;           //calculates with the chi squared table and the degrees of freedom

        for (Double number : information) {
            double num = ceil(number * 100);
            double num_1 = ((int) num / 10);
            double num_2 = num % 10;
            if (num_1 == num_2) {                      //The two decimals are equals
                equals_two++;
            } else {                                   //The two decimals are diferents
                equals_zero++;
            }
        }
        
        double expected_frequency = information.size();     //calculates the expected frequency
        double chi_2_1 = (pow((expected_frequency * 0.1) - equals_two, 2)) / (expected_frequency * 0.1);
        double chi_2_2 = (pow((expected_frequency * 0.9) - equals_zero, 2)) / (expected_frequency * 0.9);
        double calculated_chi_squared = chi_2_1 + chi_2_2;  //calculates the calculated chi squared
        System.out.println("2 Equals      \t\t\t " + equals_two + " \t\t " + expected_frequency * 0.1 + " \t\t " + chi_2_1);
        System.out.println("2 Diferents   \t\t\t " + equals_zero + " \t\t " + expected_frequency * 0.9 + " \t\t " + chi_2_2);
        System.out.println("........................................................");
        System.out.println("Calculated Chi Squared:        " + calculated_chi_squared);
        System.out.println("Critical Chi Squared           " + critical_chi_squared);
        
        boolean good_generator = calculated_chi_squared <= critical_chi_squared;            //set if the generator is good  
        System.out.println("Past the Test?: "+good_generator);
    }
    
    /**
     * Calculates if generator is good with the poker three test
     * @param radom_numbers
     */
    private void pokerThreeTest(List<Double> radom_numbers){
        
        List<Double> information = formatList(radom_numbers, "#.###");//define el formato para 3 decimales
        System.out.println("Class \t\t\t\t OF \t\t EF \t\t x2");
        int equals_three = 0;
        int equals_two = 0;
        int equals_zero = 0;
        double critical_chi_squared = 4.6052;           //calculates with the chi squared table and the degrees of freedom

        for (Double number : information) {
            double num = ceil(number * 1000);
            double num1 = (int) num / 100;
            double num2 = ((int) num / 10) % 10;
            double num3 = num % 10;
            if (num1 == num2 && num1 == num3) {         //the three numbers are equals
                equals_three++;
            } else if (num1 != num2 && num1 != num3 && num2 != num3) {      //the three numbers are diferents
                equals_zero++;
            } else {                                                        //there are two equals numbers
                equals_two++;
            }
        }
        double expected_frequency = information.size();                 //calculates the expected frequency
        double chi_2_1 = (pow((expected_frequency * 0.01) - equals_three, 2)) / (expected_frequency * 0.01);
        double chi_2_2 = (pow((expected_frequency * 0.27) - equals_two, 2)) / (expected_frequency * 0.27);
        double chi_2_3 = (pow((expected_frequency * 0.72) - equals_zero, 2)) / (expected_frequency * 0.72);
        double calculated_chi_squared = chi_2_1 + chi_2_2 + chi_2_3;    //calculates the calculated chi squared
        System.out.println("3 Equals                \t " + equals_three + " \t\t " + expected_frequency * 0.01 + " \t\t " + chi_2_1);
        System.out.println("2 Equals, 1 Diferent   \t " + equals_two + " \t\t " + expected_frequency * 0.27 + " \t\t " + chi_2_2);
        System.out.println("3 Diferents             \t " + equals_zero + " \t\t " + expected_frequency * 0.72 + " \t\t " + chi_2_3);
        System.out.println("........................................................");
        System.out.println("Calculated Chi Squared:        " + calculated_chi_squared);
        System.out.println("Critical Chi Squared           " + critical_chi_squared);
        
        boolean good_generator = calculated_chi_squared <= critical_chi_squared;            //set if the generator is good  
        System.out.println("Past the Test?: "+good_generator);    
    }
    
    /**
     * Print the information with the test, generator and number of pseudo-random numbers given
     * @param goodness_of_fit
     * @param generator
     * @param number
     * @throws IOException 
     */
    public void printInformation(int goodness_of_fit, int generator, int number) throws IOException {
        
        List<Double> random_numbers = generateListRandomNumbers(generator);             //set the random numbers list according to generator
        
        if(number==1000){
            random_numbers = generateListRandomNumbers(generator).subList(0, 1000);     //set the random numbers list according to generator with 1000 numbers only
        }
        
        // Chi Squared Test choosen
        if(goodness_of_fit == 1){
            System.out.println("--------------------- Chi Squared Test ---------------------");
            chiSquaredTest(random_numbers);
        
        // Poker 2 Test choosen
        }else if(goodness_of_fit == 2){
            System.out.println("--------------------- Poker 2 Test ---------------------");
            pokerTwoTest(random_numbers);
            
        // Poker 3 Squared Test choosen
        }else if(goodness_of_fit == 3){
            System.out.println("--------------------- Poker 3 Test ---------------------");
            pokerThreeTest(random_numbers);
        }
    }

    /**
     * Main method
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {

        Gem gem = new Gem();
        
        // Goodness of Fit Chi² of GEM Generator
        //gem.printInformation(1,0,1000);
        //gem.printInformation(1, 0, 10000);
        
        // Goodness of Fit Poker 2 of GEM Generator
        //gem.printInformation(2,0,1000);
        //gem.printInformation(2,0,10000);
        
        // Goodness of Fit Poker 3 of GEM Generator
        //gem.printInformation(3,0,1000);
        //gem.printInformation(3,0,10000);
        
        // Goodness of Fit Chi² of Java Generator
        //gem.printInformation(1,1,1000);
        //gem.printInformation(1,1,10000);
        
        // Goodness of Fit Poker 2 of Java Generator
        gem.printInformation(2,1,1000);
        gem.printInformation(2,1,10000);
        
        // Goodness of Fit Poker 3 of Java Generator
        gem.printInformation(3,1,1000);
        gem.printInformation(3,1,10000);
    }

}

