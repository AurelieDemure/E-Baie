import org.codingweek.model.Chat;
import org.codingweek.model.Offer;
import org.codingweek.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.codingweek.db.DatabaseManager;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
    private DatabaseManager dbManager;

    @BeforeEach
    public void setUp() {
        this.dbManager = new DatabaseManager();
        dbManager.setup();
    }

    @AfterEach
    public void tearDown() {
        dbManager.tearDown();
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("testing@example.com");
        user.setPassword("test123");
        user.setBalance(100);

        dbManager.saveEntity(user);

        User loadUser = dbManager.getEntity(User.class, user.getId());
        assertNotNull(loadUser);

        dbManager.deleteEntity(user);
    }

    @Test
    public void testSaveOffer() {
        Offer offer = new Offer();
        User user = new User("Test", "User", "test123", "test@gmail.com", 100);
        dbManager.saveEntity(user);

        offer.setOwner(dbManager.getEntity(User.class, 1));

        dbManager.saveEntity(offer);

        Offer loadOffer = dbManager.getEntity(Offer.class, offer.getId());
        assertNotNull(loadOffer);

        dbManager.deleteEntity(offer);
        dbManager.deleteEntity(offer);
    }

    @Test
    public void testUpdateUser() {
        /*Chat chat = new Chat();
        chat.setMessage("Test");
        chat.setDate(new Date());

        chat.setUser(dbManager.getEntity(User.class, 1));
        chat.setUser2(dbManager.getEntity(User.class, 2));

        dbManager.saveEntity(chat);
        Chat loadChat = dbManager.getEntity(Chat.class, chat.getId());
        assertNotNull(loadChat);

        loadChat.setMessage("Test2");

        dbManager.updateEntity(loadChat);

        assertEquals("Test2", loadChat.getMessage());

        dbManager.deleteEntity(chat);*/
    }
}
