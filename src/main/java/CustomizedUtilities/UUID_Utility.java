package CustomizedUtilities;

import java.util.UUID;

public class UUID_Utility {
    public static String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
