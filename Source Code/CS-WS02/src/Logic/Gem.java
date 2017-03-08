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

public class Gem {

    static final long module = 2147483647L;
    static final long multiplier = 48271L;
    static final long increase = 3399L;

    // Global Variable, initialice the seed with multiplier value diferent from 0
    static long r_seed = 12345678L;

    /**
     *
     * Constructor Gem Class
     */
    public Gem() {
    }

    /**
     * *
     * Generador de numeros pseudoaleatorios entre 0 y 1
     *
     * @param generador ingresa 1 para usar el generador propio (GEM) y 0 para
     * usar el generador de java
     * @return numero pseudoaleatorio
     */
    private double generateRandomGem() {
        double generated_number = 0.0;

        long ho = (multiplier * r_seed) + increase;
        r_seed  = ho % module;

        generated_number = ((double) r_seed / (double) module);

        return generated_number;
    }
    
    private double generateRandomJava(){
        double generated_number = 0.0;
        Random rnd = new Random();
        generated_number = rnd.nextDouble();
        return generated_number;
    }
    
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

    public List<Double> generateListRandomNumbers(int generator) throws IOException {
        List<Double> random_numbers = new LinkedList<>();
        List<String> random_numbers_strings = new LinkedList<>();

        for (int i = 0; i < 10000; i++) {
            if (i == 1000) {
                Path file = Paths.get("GEM1000.txt");
                Files.write(file, random_numbers_strings, Charset.forName("UTF-8"));
            }
            double random_number = choseGenerator(generator);
            random_numbers_strings.add(String.valueOf(random_number));
            random_numbers.add(random_number);
        }
        Path file = Paths.get("GEM10000.txt");
        Files.write(file, random_numbers_strings, Charset.forName("UTF-8"));
        
        return random_numbers;
    }
    

    public static void main(String[] args) throws IOException {

        double num = 0;
        Gem gem = new Gem();

        System.out.println(gem.generateListRandomNumbers(0));

//        System.out.println(gem.pruebachi2(num10000));
//        System.out.println(gem.poker2(num10000));
//        System.out.println(gem.poker3(num10000));
    }

}

