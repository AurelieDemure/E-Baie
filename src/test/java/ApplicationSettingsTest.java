import org.codingweek.ApplicationSettings;
import org.junit.jupiter.api.Test;

public class ApplicationSettingsTest {

    @Test
    public void singletonIntegrity() {
        assert (ApplicationSettings.getInstance() == ApplicationSettings.getInstance());
    }

}
