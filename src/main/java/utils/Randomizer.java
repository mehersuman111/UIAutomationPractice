package utils;

import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class Randomizer {

    // Private constructor as it is a utility class - Prevent object instantiation
    private Randomizer() {

    }

    public static int getRandomIntegerUpTo(int upToInteger) {
        Random random = new Random();
        return random.nextInt(upToInteger);
    }

    // Generate decimal number from 0.0 to < 1.0
    public static double getRandomDecimal() {
        return Math.random();
    }
}
