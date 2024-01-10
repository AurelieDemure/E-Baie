package org.codingweek.model;

import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageLatLng;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import org.codingweek.Configuration;

public class GeoLocalisation {

    /** Return the longitude latitude from a address
     * Return NULL if address not valid*/
    public static JOpenCageLatLng getLatLng(String address) {
        if (address == null) {
            return null;
        }
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(Configuration.API_KEY);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);

        request.setMinConfidence(1);
        request.setNoAnnotations(false);
        request.setNoDedupe(true);
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        if (response == null) return null;
        return response.getFirstPosition();
    }

    /** Return the distance between two address in km
     * Return -1 if address not valid*/
    public static double distance(String address1, String address2) {
        int rayon_earth = 6371;
        JOpenCageLatLng latLng1 = getLatLng(address1);
        JOpenCageLatLng latLng2 = getLatLng(address2);

        if (latLng1 == null || latLng2 == null) return -1;


        double a = Math.pow(Math.sin((latLng2.getLat() - latLng1.getLat()) / 2), 2)
                + Math.cos(latLng1.getLat())
                * Math.cos(latLng2.getLat())
                * Math.pow(Math.sin((latLng2.getLng() - latLng1.getLng()) / 2), 2);

        return rayon_earth * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    }
}
