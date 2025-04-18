package wein.service.demo.DbConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wein.service.demo.domain.Wein;
import wein.service.demo.repository.WeinRepository;

import java.util.logging.Logger;

@Slf4j
@Configuration
public class WeinDbConfig {

    /**************************************************************
     * Wein Name
     *************************************************************/
    private static final String weinName = "Château Vieux Coutelin 2011";
    private static final String weinName1 = "Château La Lagune";

    /**************************************************************
     * Land Name
     *************************************************************/
    private static final String landName = "Frankreich";
    private static final String landName1 = "Frankreich";

    /**************************************************************
     * Region Name
     *************************************************************/
    private static final  String regionName= "Bordeaux";
    private static final String  regionName1 = "Haut-Médoc";

    /**************************************************************
     * AlkohlGehalt
     *************************************************************/
    private static final double alkohlGehalt = 12;
    private static final double alkohlGehalt1 = 20;

    /**************************************************************
     * Geschmack
     *************************************************************/
    private static final String geschmack = "trocken";
    private static final String geschmack1 = "halb-trocken";


    /**************************************************************
     * Fuell Menge
     *************************************************************/
    private static final double fuellMenge = 0.775;
    private static final double fuellMenge1= 0.80;

    /**************************************************************
     * Preis
     *************************************************************/
    private static final double preis = 25.55;
    private static final double preis1 = 93.00;

    /**************************************************************
     * Farbe
     *************************************************************/
    private static final String farbe = "Rot";
    private static final String farbe1 = "Weiß";

    /**************************************************************
     * Create and Initialize Wein
     *************************************************************/
    private static Wein wein= new Wein();
    private static  Wein wein1 = new Wein();

    @Bean
    public CommandLineRunner initMockData(WeinRepository weinRepository){
        return (String[] args) -> {
            wein.setWeinName(weinName);
            wein.setLandName(landName);
            wein.setRegionName(regionName);
            wein.setAlkohlGehalt(alkohlGehalt);
            wein.setGeschmack(geschmack);
            wein.setFuellMenge(fuellMenge);
            wein.setPreis(preis);
            wein.setFarbe(farbe);
            weinRepository.save(wein);

            wein1.setWeinName(weinName1);
            wein1.setLandName(landName1);
            wein1.setRegionName(regionName1);
            wein1.setAlkohlGehalt(alkohlGehalt1);
            wein1.setGeschmack(geschmack1);
            wein1.setFuellMenge(fuellMenge1);
            wein1.setPreis(preis1);
            wein1.setFarbe(farbe1);

            weinRepository.save(wein1);
            logInitializedStudentIdentityCards(weinRepository);
        };
    }
    private void logInitializedStudentIdentityCards(WeinRepository weinRepository) {
        Logger logger = Logger.getLogger(WeinDbConfig.class.getName());
        logger.info("wein initialized");
        logger.info("-------------------------------");
        for (Wein wein: weinRepository.findAll()) {
            logger.info(wein.toString());
        }
        logger.info("-------------------------------");
    }
}
