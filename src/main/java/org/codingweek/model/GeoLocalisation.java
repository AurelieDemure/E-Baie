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
}
