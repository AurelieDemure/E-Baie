import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.Offer;
import org.codingweek.db.entity.User;
import org.codingweek.model.DatabaseHandler;
import org.codingweek.model.MarketModel;
import org.codingweek.model.filter.Frequency;
import org.codingweek.model.filter.OfferType;
import org.codingweek.model.filter.Price;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MarketModelTest {

    private DatabaseManager dbManager;

    @BeforeEach
    public void setUp() {
        this.dbManager = new DatabaseManager();
        dbManager.setupTest();
        DatabaseHandler.setDatabaseTest();

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
        Offer offer = new Offer("Test", "Test", user1, 410, OfferType.LOAN, Frequency.WEEKLY, "Test", "Test");
        Offer offer1 = new Offer("Test", "Test", user1, 10, OfferType.SERVICE, Frequency.DAYLY, "Test", "Test");
        Offer offer2 = new Offer("Test", "Test", user1, 100, OfferType.SERVICE, Frequency.MONTHLY, "Test", "Test");

        dbManager.saveEntity(offer);
        dbManager.saveEntity(offer1);
        dbManager.saveEntity(offer2);

        assert MarketModel.getOffersAvailable("----").size() == 3;
        assert MarketModel.getOffersAvailable(user1.getEmail()).isEmpty();
        assert MarketModel.getOffersAvailableFiltered("----", OfferType.LOAN, null, null, null).size() == 1;
        assert MarketModel.getOffersAvailableFiltered("----", null, null, Price.LESS100, null).size() == 1;
        assert MarketModel.getOffersAvailableFiltered("----", null, null, Price.FROM400TO500, null).size() == 1;
    }

}
