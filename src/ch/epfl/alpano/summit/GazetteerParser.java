package ch.epfl.alpano.summit;

import static java.lang.Math.signum;
import static java.lang.Math.toRadians;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.alpano.GeoPoint;

/**
 * Classe immuable et non instanciable representant un lecteur de fichier
 *
 * @author Philippine Favre (258854)
 * @author Bastien Beuchat  (257117)
 */
public final class GazetteerParser {
    private GazetteerParser(){}

    /**
     * Lit un fichier de sommmet et le retourne dans une liste
     * @param file File representant le fichier a lire
     * @return List de Summit representant la liste de sommet contenue dans le fichier
     * @throws IOException en cas d'erreur avec le fichier ou mauvais formatage
     */
    public static List<Summit> readSummitsFrom(File file) throws IOException{
        ArrayList<Summit> listSummit = new ArrayList<>(); 

        //ouverture du fichier
        try(BufferedReader s = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            String line;
            while((line=s.readLine()) != null){
                listSummit.add(toSummit(line));
            }
        } catch (FileNotFoundException e) {
            throw new IOException("Fichier incorrect");
        }

        return Collections.unmodifiableList(listSummit);
    }

    /**
     * Methode retournant le sommet associe a la ligne du fichier
     * @param l String representant une ligne d'un fichier de sommet
     * @return Summit representant le sommet associe a la ligne
     * @throws IOException si le fichier n'est pas correctement formate
     */
    private static Summit toSummit(String l) throws IOException{
        try{
            double lon = angleStringToRadians(l.substring(0, 9).trim());
            double lat = angleStringToRadians(l.substring(10, 18).trim());

            GeoPoint position = new GeoPoint(lon, lat);
            int elevation = Integer.parseInt(l.substring(20, 24).trim());

            String name = l.substring(36).trim();
            return new Summit(name, position, elevation);
        }catch(NumberFormatException e){
            throw new IOException("Fichier pas formaté correctement");
        }catch(IndexOutOfBoundsException e){
            throw new IOException("Fichier pas formaté correctement");
        }
    }

    /**
     * Metode retournant l'angle HMS en radian
     * @param angle String representant un angle avec le format suivant °°:'':""
     * @return un double representant l'angle en radian
     */
    private static double angleStringToRadians(String angle){
        String hms[] = angle.split(":");

        int h = Integer.parseInt(hms[0]);
        return toRadians(h + signum(h) * Integer.parseInt(hms[1]) / 60d + signum(h) * Integer.parseInt(hms[2]) / 3600d);
    }
}
