import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageLatLng;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import org.codingweek.Configuration;
import org.codingweek.model.GeoLocalisation;
import org.junit.jupiter.api.Test;

public class GeoLocalisationTest {

    @Test
    public void APIKeyTest() {
// In real live application the JOpenCageGeocoder should be a Singleton

        JOpenCageLatLng firstResultLatLng = GeoLocalisation.getLatLng("193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex");

        assert GeoLocalisation.areDoublesEqual(firstResultLatLng.getLat(), 48.67103, 5);
        assert GeoLocalisation.areDoublesEqual(firstResultLatLng.getLng(), 6.15083, 4);

        JOpenCageLatLng secondResultLatLng = GeoLocalisation.getLatLng("rue de Rungis, 75013 Paris");
        assert GeoLocalisation.areDoublesEqual(secondResultLatLng.getLat(), 48.82, 2);
        assert GeoLocalisation.areDoublesEqual(secondResultLatLng.getLng(), 2.346, 3);

    }

    @Test
    public void DistanceTest() {
        assert GeoLocalisation.distance("193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex", "193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex") == 0;
        assert GeoLocalisation.distance("193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex", "1, rue Grandville 54000 Nancy") > 0;
        assert GeoLocalisation.distance("1 rue de Rungis, 75013 Paris", "193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex") > 270;
        assert GeoLocalisation.distance("1 rue de Rungis, 75013 Paris", "193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex") < 290;

    }
}
