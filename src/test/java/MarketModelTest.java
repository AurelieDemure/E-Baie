import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.User;
import org.codingweek.model.MarketModel;
import org.codingweek.model.OfferType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MarketModelTest {

    private DatabaseManager dbManager;

    @BeforeEach
    public void setUp() {
        this.dbManager = new DatabaseManager();
        dbManager.setupTest();

        dbManager.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        dbManager.tearDown();
    }

    @Test
    public void testOffers() {
        User user1 = new User("test@test.com", "Test", "User", "test123", "0601528495", "Nancy", "description", 100);
        dbManager.saveEntity(user1);
        Offer offer = new Offer("Test", "Test", user1, 100, OfferType.LOAN, "Test", "Test", "Test");
        Offer offer1 = new Offer("Test", "Test", user1, 100, OfferType.SERVICE, "Test", "Test", "Test");
        Offer offer2 = new Offer("Test", "Test", user1, 100, OfferType.SERVICE, "Test", "Test", "Test");

        dbManager.saveEntity(offer);
        dbManager.saveEntity(offer1);
        dbManager.saveEntity(offer2);

        MarketModel model = new MarketModel();
        assert model.getOffersAvailable("----").size() == 3;
        assert model.getOffersAvailable(user1.getEmail()).isEmpty();
        assert model.getOffersAvailableFiltered(user1.getEmail(), OfferType.LOAN).size() == 1;
    }

}
