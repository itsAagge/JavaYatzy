package models;

import java.util.Random;

public class Die {
    private int eyes = 0;
    private final Random random = new Random();

    /**
     * Creates a new Die object, with face set to eyes. Used for test purpose
     * @param eyes value should be between 1 and 6
     */
    public Die(int eyes) {
        this.eyes = eyes;
    }

    public Die() {
    }

    public int getEyes() {
        return eyes;
    }

    public void roll() {
        this.eyes = random.nextInt(1,7);
    }
}
