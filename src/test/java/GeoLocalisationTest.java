import com.opencagedata.jopencage.model.JOpenCageLatLng;
import org.codingweek.model.GeoLocalisation;
import org.junit.jupiter.api.Test;

public class GeoLocalisationTest {

    @Test
    public void APIKeyTest() {
        JOpenCageLatLng firstResultLatLng = GeoLocalisation.getLatLng("193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex");

        assert firstResultLatLng.getLat() > 48.6 && firstResultLatLng.getLat() < 49;
        assert firstResultLatLng.getLng() > 6 && firstResultLatLng.getLng() < 6.2;

        JOpenCageLatLng secondResultLatLng = GeoLocalisation.getLatLng("rue de Rungis, 75013 Paris");
        assert secondResultLatLng.getLat() > 48.7 && secondResultLatLng.getLat() < 48.9;
        assert secondResultLatLng.getLng() < 2.4 && secondResultLatLng.getLng() > 2.2;

    }

    @Test
    public void DistanceTest() {
        assert GeoLocalisation.distance("193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex", "193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex") == 0;
        assert GeoLocalisation.distance("193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex", "1, rue Grandville 54000 Nancy") > 0;
        assert GeoLocalisation.distance("1 rue de Rungis, 75013 Paris", "193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex") > 270;
        assert GeoLocalisation.distance("1 rue de Rungis, 75013 Paris", "193, avenue Paul Muller BP 90172 Villers-lès-Nancy Cedex") < 290;

    }
}
