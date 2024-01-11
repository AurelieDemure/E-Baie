package org.codingweek.model;

import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageLatLng;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import org.codingweek.Configuration;

public class GeoLocalisation {


    private static GeoLocalisation instance = null;
    private JOpenCageGeocoder jOpenCageGeocoder = null;

    public GeoLocalisation() {
        jOpenCageGeocoder = new JOpenCageGeocoder(Configuration.API_KEY);
    }

    public static GeoLocalisation getInstance() {
        if (instance == null) {
            instance = new GeoLocalisation();
        }
        return instance;
    }


    /**
     * Return the longitude latitude from an address
     * Return NULL if address not valid
     */
    public static JOpenCageLatLng getLatLng(String address) {
        if (address == null) {
            return null;
        }
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);

        request.setMinConfidence(1);
        request.setNoAnnotations(false);
        request.setNoDedupe(true);
        JOpenCageResponse response = getInstance().jOpenCageGeocoder.forward(request);
        if (response == null) return null;
        return response.getFirstPosition();
    }

    /**
     * Return the distance between two address in km
     * Return -1 if address not valid
     */
    public static double distance(String address1, String address2) {
        int rayon_earth = 6371;
        JOpenCageLatLng latLng1 = getLatLng(address1);
        JOpenCageLatLng latLng2 = getLatLng(address2);

        if (latLng1 == null || latLng2 == null) return -1;
        double lat1 = Math.toRadians(latLng1.getLat());
        double lon1 = Math.toRadians(latLng1.getLng());
        double lat2 = Math.toRadians(latLng2.getLat());
        double lon2 = Math.toRadians(latLng2.getLng());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);

        return rayon_earth * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        int rayon_earth = 6371;

        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);

        return rayon_earth * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }


    /** Test if two double are equals with a precision
     *  Return true if equals
     *  Return false if not equals
     */
    public static boolean areDoublesEqual(double d1, double d2, int precision) {
        // Calculate the factor to shift the numbers for the specified precision
        double factor = Math.pow(10, precision);

        // Round the numbers to the specified precision
        long roundedD1 = Math.round(d1 * factor);
        long roundedD2 = Math.round(d2 * factor);

        // Compare the rounded values
        return roundedD1 == roundedD2;
    }
}
