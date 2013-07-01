package builders;

import org.apache.commons.lang3.RandomStringUtils;

public class MsisdnGenerator {
    public static String randomizedFr() {
        return "+336" + RandomStringUtils.randomNumeric(8);
    }
}
