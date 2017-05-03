package ch.epfl.alpano.gui;


import static ch.epfl.alpano.Math2.angularDistance;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.util.Objects.requireNonNull;
import static ch.epfl.alpano.PanoramaComputer.rayToGroundDistance;
import static ch.epfl.alpano.Math2.firstIntervalContainingRoot;
import static java.lang.Math.round;
import static java.lang.Double.POSITIVE_INFINITY;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import ch.epfl.alpano.PanoramaParameters;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.ElevationProfile;
import ch.epfl.alpano.summit.Summit;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Classe public et immuable representant un etiqueteur pour un panorama
 * 
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class Labelizer {
    private static final int INTERVAL_SEARCH = 64;
    private static final int TOLERANCE = 200;
    private static final int VERTICAL_LIMIT = 170;
    private static final int HORIZONTAL_SPACING = 20;
    private static final int VERTICAL_SPACING = 22;
    private static final int TEXT_LINE_SPACING = 2;
    private static final int ROTATION_ANGLE = 60;

    private final ContinuousElevationModel cDem;
    private final List<Summit> summits;

    /**
     * Constructeur d'un etiqueteur de panorama
     * @param cDem ContinuousElevationModel MNT continu sur lequel l'etiqueteur va pouvoir etiqueter
     * @param summits List<Summit> representant la liste des sommets connus sur le MNT continu
     */
    public Labelizer(ContinuousElevationModel cDem, List<Summit> summits){
        this.cDem  = requireNonNull(cDem);
        this.summits = Collections.unmodifiableList(new ArrayList<>(summits));
    }

    /**
     * Methode privee permettant d'obtenir la liste des sommets visibles
     * @param params PanoramaParameters parametres du panorama a etiqueter
     * @return List<VisibleSummit> la liste des sommets visibles pour le panorama
     */
    private List<VisibleSummit> getVisibleSummits(PanoramaParameters params){
        List<VisibleSummit> visible = new ArrayList<>();

        for(Summit summit : summits){
            double azimuth = params.observerPosition().azimuthTo(summit.position());
            double alpha = angularDistance(params.centerAzimuth(), azimuth);
            double distance = summit.position().distanceTo(params.observerPosition());

            //Controle que le sommet soit pas plus loin que la distance max et qu'il se trouve dans le champs de vision horizontal
            if(distance <= params.maxDistance() && abs(alpha) <= params.horizontalFieldOfView() / 2d){
                ElevationProfile profile= new ElevationProfile(cDem, params.observerPosition(), azimuth, distance);

                double deltaElevation = -rayToGroundDistance(profile, params.observerElevation(),0).applyAsDouble(distance); 
                double altitude = atan2(deltaElevation,distance);

                //controle que le sommet soit dans le champs de vision verticale
                if(abs(altitude) < params.verticalFieldOfView()/2){    
                    DoubleUnaryOperator delta = rayToGroundDistance(profile, params.observerElevation(), deltaElevation/distance);
                    
                    //System.out.println(summit.name()+" "+firstIntervalContainingRoot(delta, 0, distance - TOLERANCE, INTERVAL_SEARCH));
                    //Controle que le sommet se situe dans le voisinage du sommet
                    if(firstIntervalContainingRoot(delta, 0, distance - TOLERANCE, INTERVAL_SEARCH) == POSITIVE_INFINITY){
                        
                        //Controle que le sommet se situe au delà de la limite verticale
                        int y = (int) round(params.yForAltitude(altitude));
                       //if(y >= VERTICAL_LIMIT){
                            visible.add(new VisibleSummit((int) round(params.xForAzimuth(azimuth)), y, summit.name(), summit.elevation()));
                        //}
                    }
                }
            }
        }

        return visible;
    }

    /**
     * Methode qui etiquette les sommets du panorama
     * @param params PanoramaParameters parametres du panorama a etiqueter
     * @return List<Node> la liste des noeuds JavaFx representant les etiquettes
     */
    public List<Node> labels(PanoramaParameters params){
        List<VisibleSummit> visibleSummits = getVisibleSummits(params);
        Collections.sort(visibleSummits);
        List<Node> nodes = new ArrayList<>();
        
        BitSet positionsXAvailable = new BitSet();
        positionsXAvailable.set(0, params.width()-2*HORIZONTAL_SPACING);
        
        BitSet minimalInterval = new BitSet(2*HORIZONTAL_SPACING);
        minimalInterval.set(0,2*HORIZONTAL_SPACING);
        boolean isFirst = true;
        int yl = -VERTICAL_SPACING;
        for(VisibleSummit summit : visibleSummits){
            System.out.println(summit.name+ " ("+summit.x+", "+summit.y+")");
            //Controle que le sommet se trouve au dela de 170 px et se trouve a plus de 20 px des bords
            //et qu'il y ait un espace de 20 px au minimum avec les autres labels 
            if(summit.y >= VERTICAL_LIMIT 
                    && HORIZONTAL_SPACING < summit.x 
                    && summit.x < params.width()-HORIZONTAL_SPACING
                    && positionsXAvailable.get(summit.x-HORIZONTAL_SPACING, summit.x+HORIZONTAL_SPACING).equals(minimalInterval)){
                
                positionsXAvailable.flip(summit.x-HORIZONTAL_SPACING, summit.x+HORIZONTAL_SPACING);
                
                if(isFirst){
                    yl += summit.y;
                    isFirst = false;
                }
                
                //Creations des noeuds
                Text t = new Text(summit.name+" ("+summit.elevation+" m)");
                t.getTransforms().addAll(new Translate(summit.x,yl),new Rotate(ROTATION_ANGLE));
                nodes.add(t);
                nodes.add(new Line(summit.x,yl+TEXT_LINE_SPACING,summit.x,summit.y));
            }
        }

        return nodes;
    }

    /**
     * Classe privee representant un sommet sur l'image
     */
    private final class VisibleSummit implements Comparable<VisibleSummit>{
        private final int x;
        private final int y;
        private final String name;
        private final int elevation;

        /**
         * Construit un sommet visible (sur l'image du panorama)
         * @param x int coordonnee x du pixel representant le sommet
         * @param y int coordonne y du pixel  representant le sommet
         * @param name String nom du sommet
         * @param elevation int altitude du sommet
         */
        public VisibleSummit(int x, int y, String name, int elevation){
            this.x = x;
            this.y = y;
            this.name = name;
            this.elevation = elevation;
        }

        @Override
        public int compareTo(VisibleSummit o) {
            return this.y!=o.y ? Integer.compare(this.y, o.y) : Integer.compare(o.elevation, this.elevation);
        }
    }
}
