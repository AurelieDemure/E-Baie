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

        assert firstResultLatLng.getLat().compareTo(48.67103) == 0;
        assert firstResultLatLng.getLng().compareTo(6.15083) == 0;
    }
}
