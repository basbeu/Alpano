package ch.epfl.alpano;

import static java.util.Arrays.fill;

/**
 * Classe representant les panorama accompagnee d'un batisseur
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */

public final class Panorama {

    private final PanoramaParameters parameters;
    private final float[] distance;
    private final float[] longitude;
    private final float[] latitude;
    private final float[] elevation;
    private final float[] slope;


    /**
     * Constructeur d'un panorama
     * @param parameters PanoramaParameters parametres du panorama
     * @param distance float[] tableau contenant  les valeurs des echantillons de la distance
     * @param longitude float[] tableau contenant  les valeurs des echantillons de la longitude
     * @param latitude float[] tableau contenant  les valeurs des echantillons de la latitude
     * @param elevation float[] tableau contenant  les valeurs des echantillons de l'elevation
     * @param slope float[] tableau contenant  les valeurs des echantillons de la pente
     */
    private Panorama (PanoramaParameters parameters, float[] distance, float[] longitude, float[] latitude,float[] elevation, float[] slope){
        this.parameters = parameters;    
        this.distance = distance;
        this.longitude = longitude;
        this.latitude = latitude;
        this.elevation = elevation;
        this.slope = slope;
    }

    /**
     * Accesseur public des parametres
     * @return PanoramaParameters parametre du panorama
     */
    public PanoramaParameters parameters(){
        return parameters;
    }

    /**
     * Methode privee pour tester si les coordonnees du point passe sont hors des bornes du panorama
     * @param x int coordonnee x du point
     * @param y int coordonnee y du point
     * @throws IndexOutOfBoundsException si les coordonnees sont hors des bornes
     */
    private void checkInBound(int x, int y){
        if(!parameters.isValidSampleIndex(x, y)){
            throw new IndexOutOfBoundsException("Les coordonnées sont hors des bornes du panorama!");
        }
    }

    /**
     * Accesseur public de la distance au point de coordonnee (x, y)
     * @param x int coorodnnee d'index x
     * @param y int coordonnee d'index y
     * @return float distance au point de coordonne (x, y)
     * @throws IndexOutOfBoundsException si les coordonnees sont hors des bornes
     */
    public float distanceAt(int x, int y){
        checkInBound(x,y);
        return distance[parameters.linearSampleIndex(x, y)];
    }

    /**
     * Accesseur public surcharge de la distance au point de coordonnee (x, y)    
     * @param x int coordonnee x
     * @param y int coordonnee y
     * @param d float valeur par default si l'index est hors des bornes
     * @return float distance au point de coordonnee (x, y) ou la valeur d si celui-ci est hors des bornes
     */
    public float distanceAt(int x, int y, float d){
        if(!(parameters.isValidSampleIndex(x, y))){
            return d;
        }else {
            return distance[parameters.linearSampleIndex(x, y)];
        }
    }

    /**
     * Accesseur public de la longitude au point de coordonnee (x, y)
     * @param x int coordonnee x
     * @param y int coordonnee y
     * @return float longitude au point de coordonnee (x,y)
     * @throws IndexOutOfBoundsException si les coordonnees sont hors des bornes
     */
    public float longitudeAt(int x, int y){
        checkInBound(x,y);
        return longitude[parameters.linearSampleIndex(x, y)];
    }

    /**
     * Accesseur public de la latitude au point de coordonnee (x, y)
     * @param x int coordonnee x
     * @param y int coordonnee y
     * @return float latitude au point de coordonnee (x,y)
     * @throws IndexOutOfBoundsException si les coordonnees sont hors des bornes
     */
    public float latitudeAt(int x, int y){
        checkInBound(x,y);
        return latitude[parameters.linearSampleIndex(x, y)];
    }

    /**
     * Accesseur public de l'altitude a un point de coordonnee (x, y)
     * @param x int coordonnee x
     * @param y int coordonnee y
     * @return float altitude au point de coordonnee (x,y)
     * @throws IndexOutOfBoundsException si les coordonnees sont hors des bornes
     */
    public float elevationAt(int x, int y){
        checkInBound(x,y);
        return elevation[parameters.linearSampleIndex(x, y)];
    }

    /**
     * Accesseur public de la pente a un point de coordonnee (x, y)
     * @param x int coordonnee x
     * @param y int coordonnee y
     * @return float pente au point de coordonnee (x,y)
     * @throws IndexOutOfBoundsException si les coordonnees sont hors des bornes
     */
    public float slopeAt(int x, int y){
        checkInBound(x,y);
        return slope[parameters.linearSampleIndex(x, y)];
    }

    /**
     * Classe representant un batisseur de panorama
     * 
     */    
    public final static class Builder {
        private final PanoramaParameters param;
        private float[] distanceBuild, longitudeBuild, latitudeBuild, elevationBuild, slopeBuild;
        private boolean isBuilt = false;

        /**
         * Constructeur d'un builder
         * @param parameters PanoramaParameters parametres du panorama
         */
        public Builder(PanoramaParameters parameters){
            param = parameters;
            int taille = param.width()*param.height();
            distanceBuild = new float[taille];
            fill(distanceBuild, Float.POSITIVE_INFINITY);
            longitudeBuild = new float[taille];
            latitudeBuild = new float[taille];
            elevationBuild = new float[taille];
            slopeBuild = new float[taille];

        }

        /**
         * Methode privee pour tester la validite des coordonnees du point
         * @param x int coordonnee x du point
         * @param y int coordonnee y du point
         * @throws IndexOutOfBoundsException si les coordonnees sont invalides
         */
        private void checkInBound(int x, int y){
            if(!param.isValidSampleIndex(x, y)){
                throw new IndexOutOfBoundsException("Les coordonnées du point passé sont invalides!");
            }
        }

        /**
         * Methode privee pour tester si la methode build() a deja ete appelee une fois
         * @param isBuilt boolean a true si la methode build() a deja ete appelee une fois
         * @throws IllegalStateException si la methode build() a deja ete appelee une fois
         */
        private void checkIsBuilt(boolean isBuilt){
            if(isBuilt){
                throw new IllegalStateException("La méthode build() a déjà été appelée!");
            }
        }

        /**
         * Methode permettant de definir la valeur de la distance du panorama en cours de construction a un index donne
         * @param x int coordonnee x
         * @param y int coordonnee y
         * @param distance float distance a cet index
         * @return elle-même pour pouvoir faire le chainage d'appels
         * @throws IllegalStateException si la methode build() a deja ete appelee une fois
         * @throws IndexOutOfBoundsException si les coordonnes du point passe (l'index x, y) sont invalides
         */
        public Builder setDistanceAt(int x, int y, float distance){
            checkInBound(x,y);
            checkIsBuilt(isBuilt);
            distanceBuild[param.linearSampleIndex(x, y)] = distance;
            return this;
        }

        /**
         * Methode permettant de definir la valeur de la longitude du panorama en cours de construction a un index donne
         * @param x int coordonnee x
         * @param y int coordonnee y
         * @param longitude float la longitude a cet index
         * @return elle-même pour pouvoir faire le chainage d'appels
         * @throws IllegalStateException si la methode build() a deja ete appelee une fois
         * @throws IndexOutOfBoundsException si les coordonnes du point passe (l'index x, y) sont invalides
         */
        public Builder setLongitudeAt(int x, int y, float longitude){
            checkInBound(x,y);
            checkIsBuilt(isBuilt);
            longitudeBuild[param.linearSampleIndex(x, y)] = longitude;
            return this;
        }

        /**
         * Methode permettant de definir la valeur de la latitude du panorama en cours de construction a un index donne
         * @param x int coordonnee x
         * @param y int coordonnee y
         * @param latitude float la latitude a cet index
         * @return elle-même pour pouvoir faire le chainage d'appels
         * @throws IllegalStateException si la methode build() a deja ete appelee une fois
         * @throws IndexOutOfBoundsException si les coordonnes du point passe (l'index x, y) sont invalides
         */
        public Builder setLatitudeAt(int x, int y, float latitude){
            checkInBound(x,y);
            checkIsBuilt(isBuilt);
            latitudeBuild[param.linearSampleIndex(x, y)] = latitude;
            return this;
        }

        /**
         * Methode permettant de definir la valeur de l'altitude du panorama en cours de construction a un index donne
         * @param x int coordonnee x
         * @param y int coordonnee y
         * @param elevation float l'altitude a cet index
         * @return elle-même pour pouvoir faire le chainage d'appels
         * @throws IllegalStateException si la methode build() a deja ete appelee une fois
         * @throws IndexOutOfBoundsException si les coordonnes du point passe (l'index x, y) sont invalides
         */
        public Builder setElevationAt(int x, int y, float elevation){
            checkInBound(x,y);
            checkIsBuilt(isBuilt);
            elevationBuild[param.linearSampleIndex(x, y)] = elevation;
            return this;
        }

        /**
         * Methode permettant de definir la valeur de la pente du panorama en cours de construction a un index donne
         * @param x int coordonnee x
         * @param y int coordonnee y
         * @param slope float la pente a cet index
         * @return elle-même pour pouvoir faire le chainage d'appels
         * @throws IllegalStateException si la methode build() a deja ete appelee une fois
         * @throws IndexOutOfBoundsException si les coordonnes du point passe (l'index x, y) sont invalides
         */
        public Builder setSlopeAt(int x, int y, float slope){
            checkInBound(x,y);
            checkIsBuilt(isBuilt);
            slopeBuild[param.linearSampleIndex(x, y)] = slope;
            return this;
        }

        /**
         * Methode de construction et retourne le panorama
         * @return Panorama un panorama
         * @throws IllegalStateException si elle a deja ete appelee une fois
         */
        public Panorama build(){
            checkIsBuilt(isBuilt);
            isBuilt = true;
            Panorama p = new Panorama(param, distanceBuild, longitudeBuild, latitudeBuild, elevationBuild, slopeBuild);
            
            distanceBuild = null;
            longitudeBuild = null;
            latitudeBuild = null;
            elevationBuild = null; 
            slopeBuild = null;
           
            return p;
        }     
    }
}