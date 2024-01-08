import org.codingweek.model.PasswordUtility;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    public void passwordSafety() {
        String password = "ultrasafepassword";
        String hashedPassword = PasswordUtility.hashPassword(password);
        assert(PasswordUtility.checkPassword(password, hashedPassword));
        assert (!PasswordUtility.checkPassword("wrongpassword", hashedPassword));
        assert (!password.equals(hashedPassword));
    }
}
