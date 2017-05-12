package ch.epfl.alpano.gui;

/**
 * Interface fournissant des panoramas prédéfinis sous forme de paramètre
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public interface PredefinedPanoramas {
    PanoramaUserParameters NIESEN         = new PanoramaUserParameters(76_500,467_300,600,180,110,300,2500,800,0);
    PanoramaUserParameters ALPES_JURA     = new PanoramaUserParameters(68_087,470_085,1380,162,27,300,2500,800,0);
    PanoramaUserParameters MONT_RACINE    = new PanoramaUserParameters(68_200,470_200,1500,135,20,300,2500,800,0);
    PanoramaUserParameters FINSTERAARHORN = new PanoramaUserParameters(81_260,465_374,4300,205,20,300,2500,800,0); 
    PanoramaUserParameters SAUVABELIN     = new PanoramaUserParameters(66_385,465_353,700,135,100,300,2500,800,0);
    PanoramaUserParameters PELICAN        = new PanoramaUserParameters(65_728,465_132,380,135,60,300,2500,800,0);
}
