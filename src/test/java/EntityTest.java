import org.codingweek.model.Chat;
import org.codingweek.model.Notification;
import org.codingweek.model.Offer;
import org.codingweek.model.User;
import org.codingweek.model.Query;
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

        dbManager.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        dbManager.tearDown();
    }

    @Test
    public void testSaveUser() {
        User user = new User("test@gmail.com","Test", "User", "test123", "0601528495", "Nancy", "description", 100);

        dbManager.saveEntity(user);

        User loadUser = dbManager.getEntity(User.class, user.getEmail());
        assertNotNull(loadUser);

        dbManager.deleteEntity(user);
    }

    @Test
    public void testSaveOffer() {
        Offer offer = new Offer("Test", "Test", null, 100, "Test", "Test", "Test", "Test");
        User user = new User("test@gmail.com", "Test", "User", "test123", "0601528495", "Nancy", "description", 100);
        dbManager.saveEntity(user);

        offer.setOwner(dbManager.getEntity(User.class, user.getEmail()));

        dbManager.saveEntity(offer);

        Offer loadOffer = dbManager.getEntity(Offer.class, offer.getId());
        assertNotNull(loadOffer);
        assertNotNull(loadOffer.getOwner());

        dbManager.deleteEntity(offer);
        dbManager.deleteEntity(user);
    }

    @Test
    public void testUpdateUser() {

        User user1 = new User("test1@test.com", "Test", "User", "test123", "0601528495", "Nancy", "description", 100);
        User user2 = new User("test2@test.com","Test", "User", "test123",  "0601528495", "Nancy", "description", 100);

        dbManager.saveEntity(user1);
        dbManager.saveEntity(user2);

        Chat chat = new Chat(user1, user2, "Test", new Date());

        dbManager.saveEntity(chat);

        Chat loadChat = dbManager.getEntity(Chat.class, chat.getId());

        assertNotNull(loadChat);
        assertNotNull(loadChat.getSender());
        assertNotNull(loadChat.getReceiver());

        loadChat.setMessage("Test2");

        dbManager.updateEntity(loadChat);

        assertEquals("Test2", loadChat.getMessage());

        dbManager.deleteEntity(chat);
    }

    @Test
    public void testNotification() {
        User user1 = new User("test@test.com","Test", "User", "test123","0601528495", "Nancy", "description", 100);

        dbManager.saveEntity(user1);

        Notification notif = new Notification("Test", user1, false, "Test", new Date());

        dbManager.saveEntity(notif);

        Notification loadNotif = dbManager.getEntity(Notification.class, notif.getId());

        assertNotNull(loadNotif);
        assertNotNull(loadNotif.getUser());

        dbManager.deleteEntity(notif);
        dbManager.deleteEntity(user1);
    }

    @Test
    public void testQuery() {
        User user1 = new User("test@test.com", "Test", "User", "test123", "0601528495", "Nancy", "description", 100);
        dbManager.saveEntity(user1);

        Offer offer = new Offer("Test", "Test", user1, 100, "Test", "Test", "Test", "Test");

        dbManager.saveEntity(offer);

        Query query = new Query(offer, user1, new Date(), false, 0);

        dbManager.saveEntity(query);

        Query loadQuery = dbManager.getEntity(Query.class, query.getId());

        assertNotNull(loadQuery);
        assertNotNull(loadQuery.getUser());
        assertNotNull(loadQuery.getOffer());

        dbManager.deleteEntity(query);
        dbManager.deleteEntity(user1);
        dbManager.deleteEntity(offer);
    }
}
