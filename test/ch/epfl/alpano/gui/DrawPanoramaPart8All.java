package ch.epfl.alpano.gui;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import ch.epfl.alpano.Panorama;
import ch.epfl.alpano.PanoramaComputer;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.DiscreteElevationModel;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

final class DrawPanoramaPart8All {private final static File HGT_FILE1 = new File("N45E006.hgt");
private final static File HGT_FILE2 = new File("N45E007.hgt");
private final static File HGT_FILE3 = new File("N45E008.hgt");
private final static File HGT_FILE4 = new File("N45E009.hgt");
private final static File HGT_FILE5 = new File("N46E006.hgt");
private final static File HGT_FILE6 = new File("N46E007.hgt");
private final static File HGT_FILE7 = new File("N46E008.hgt");
private final static File HGT_FILE8 = new File("N46E009.hgt");

    public static void main(String[] as) throws Exception {
        double t=System.currentTimeMillis();
        HashMap<PanoramaUserParameters, String> mapPano = new HashMap<>();
        mapPano.put(PredefinedPanoramas.ALPES_JURA,"ALPES_JURA");
//        mapPano.put(PredefinedPanoramas.FINSTERAARHORN,"FINSTERAARHORN");
//        mapPano.put(PredefinedPanoramas.MONT_RACINE,"MONT_RACINE");
//        mapPano.put(PredefinedPanoramas.NIESEN,"NIESEN");
//        mapPano.put(PredefinedPanoramas.PELICAN,"PELICAN");
//        mapPano.put(PredefinedPanoramas.SAUVABELIN,"SAUVABELIN");

        //try (DiscreteElevationModel dDEM =
        //      new HgtDiscreteElevationModel(HGT_FILE1)) {
        // try(DiscreteElevationModel dDEMUnion = dDEM.union(new HgtDiscreteElevationModel(HGT_FILE2))){

       // DiscreteElevationModel dDEM =
         //       new HgtDiscreteElevationModel(HGT_FILE1);
        //DiscreteElevationModel dDEMUnion = dDEM.union(new HgtDiscreteElevationModel(HGT_FILE2));
        for(Map.Entry<PanoramaUserParameters, String> pano : mapPano.entrySet()){
            //ContinuousElevationModel cDEM = new ContinuousElevationModel(dDEMUnion);
            ContinuousElevationModel cDEM = loadHgt();
            
            Panorama p = new PanoramaComputer(cDEM)
                    .computePanorama(pano.getKey().panoramaParameters());

            ChannelPainter gray =
                    ChannelPainter.maxDistanceToNeighbors(p)
                    .sub(500)
                    .div(4500)
                    .clamped()
                    .inverted();

            ChannelPainter distance = p::distanceAt;
            ChannelPainter opacity =
                    distance.map(d -> d == Float.POSITIVE_INFINITY ? 0 : 1);

            ImagePainter l = ImagePainter.gray(gray, opacity);

            Image i = PanoramaRenderer.renderPanorama(p, l);
            ImageIO.write(SwingFXUtils.fromFXImage(i, null),
                    "png",
                    new File(pano.getValue()+"-profile.png"));

            //image en couleur
            ChannelPainter h = (x,y)->360*distance.div(100000).cycling().valueAt(x, y);

            ChannelPainter s = distance.div(200000).clamped().inverted();

            ChannelPainter slope = p::slopeAt;
            ChannelPainter b = (x,y)->0.3f+0.7f*slope.mul(2).div((float)Math.PI).inverted().valueAt(x, y);

            ImagePainter painter = ImagePainter.hsb(h, s, b, opacity);
            Image col = PanoramaRenderer.renderPanorama(p, painter);
            ImageIO.write(SwingFXUtils.fromFXImage(col, null),
                    "png",
                    new File(pano.getValue()+"-shaded.png"));

        }
        //    }

        //}

        System.out.println(System.currentTimeMillis()-t);
    }
    
    private static ContinuousElevationModel loadHgt(){
        DiscreteElevationModel dem1 = new HgtDiscreteElevationModel(HGT_FILE1);
        DiscreteElevationModel dem2 = new HgtDiscreteElevationModel(HGT_FILE2);
        DiscreteElevationModel dem3 = new HgtDiscreteElevationModel(HGT_FILE3);
        DiscreteElevationModel dem4 = new HgtDiscreteElevationModel(HGT_FILE4);
        DiscreteElevationModel dem5 = new HgtDiscreteElevationModel(HGT_FILE5);
        DiscreteElevationModel dem6 = new HgtDiscreteElevationModel(HGT_FILE6);
        DiscreteElevationModel dem7 = new HgtDiscreteElevationModel(HGT_FILE7);
        DiscreteElevationModel dem8 = new HgtDiscreteElevationModel(HGT_FILE8);
        
        return new ContinuousElevationModel(dem1.union(dem2).union(dem3).union(dem4).union(dem5.union(dem6).union(dem7).union(dem8)));
    }
}


