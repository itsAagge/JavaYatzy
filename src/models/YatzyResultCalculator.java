package models;

/**
 * Used to calculate the score of throws with 5 dice
 */
public class YatzyResultCalculator {

    private final Die[] dice;
    int[] diceCount = new int[6];

    /**
     * @param dice
     */
    public YatzyResultCalculator(Die[] dice) {
        this.dice = dice;
    }

    /**
     * Calculates the score for Yatzy uppersection
     *
     * @param eyes eye value to calculate score for. eyes should be between 1 and 6
     * @return the score for specified eye value
     */
    public int upperSectionScore(int eyes) {
        int count = 0;
        for (Die eye : dice) {
            if (eye.getEyes() == eyes) {
                count++;
            }
        }
        return count * eyes;
    }

    public int onePairScore() {
        int onePair = 0;
        for (int i = 1; i <= 6; i++) {
            diceCount[i - 1] = upperSectionScore(i) / i;
            if (diceCount[i - 1] > 1) {
                onePair = i;
            }
        }
        return onePair * 2;
    }

    public int twoPairScore() {
        int firstPair = 0;
        int secondPair = 0;
        int TwoFactor = 0;
        for (int i = 1; i <= 6; i++) {
            diceCount[i - 1] = upperSectionScore(i) / i;
            if (diceCount[i - 1] > 1 && firstPair == 0)
                firstPair = i;
            else if (diceCount[i - 1] > 1 && firstPair != 0)
                secondPair = i;

        }
        if (firstPair != 0 && secondPair != 0) {
            TwoFactor = (firstPair + secondPair) * 2;
        }
        return TwoFactor;
    }

    public int threeOfAKindScore() {
        int threeOfAKind = 0;
        for (int i = 1; i <= 6; i++) {
            diceCount[i - 1] = upperSectionScore(i) / i;
            if (diceCount[i - 1] > 2) {
                threeOfAKind = i;
            }
        }
        threeOfAKind *= 3;
        return threeOfAKind;
    }

    public int fourOfAKindScore() {
        int fourOfAKind = 0;
        for (int i = 1; i <= 6; i++) {
            diceCount[i - 1] = upperSectionScore(i) / i;
            if (diceCount[i - 1] > 3) {
                fourOfAKind = i;
            }
        }
        fourOfAKind *= 4;
        return fourOfAKind;
    }

    public int smallStraightScore() {
        int smallStraight = 0;
        for (int i = 1; i <= 6; i++) {
            diceCount[i - 1] = upperSectionScore(i) / i;
            if (diceCount[i - 1] == 1 && diceCount[5] == 0) {
                smallStraight += i;
            }
        }
        if (smallStraight != 15) {
            smallStraight = 0;
        }
        return smallStraight;
    }

    public int largeStraightScore() {
        int largeStraight = 0;
        for (int i = 1; i <= 6; i++) {
            diceCount[i - 1] = upperSectionScore(i) / i;

            if (diceCount[i - 1] == 1 && diceCount[0] == 0) {
                largeStraight += i;
            }
        }
        if (largeStraight != 20) {
            largeStraight = 0;
        }
        return largeStraight;
    }

    public int fullHouseScore() {
        int sum = 0;
        int pair = 0;
        int threeOfAKind = 0;
        for (int i = 1; i <= 6; i++) {
            diceCount[i - 1] = upperSectionScore(i) / i;
            if (diceCount[i - 1] == 2) {
                pair = i;
            } else if (diceCount[i - 1] == 3) {
                threeOfAKind = i;
            }
        }
        if (pair != 0 && threeOfAKind != 0) {
            pair *= 2;
            threeOfAKind *= 3;
            sum += pair + threeOfAKind;
        }
        return sum;
    }

    public int chanceScore() {
        int[] diceCount = new int[6];
        int chance = 0;
        for (int i = 1; i <= 6; i++) {
            diceCount[i - 1] = upperSectionScore(i) / i;
            chance += i * diceCount[i - 1];
        }
        return chance;
    }

    public int yatzyScore() {
        int[] diceCount = new int[6];
        int yatzy = 0;
        for (int i = 1; i <= 6; i++) {
            diceCount[i - 1] = upperSectionScore(i) / i;

            if (diceCount[i - 1] == 5) {
                yatzy = 50;
            }
        }
        return yatzy;
    }
}
