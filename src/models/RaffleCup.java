package models;

public class RaffleCup {
    private Die[] dice = new Die[5];

    public RaffleCup() {
        for (int i = 0; i < dice.length; i++) {
            this.dice[i] = new Die(0);
        }
    }

    public void throwDice() {
        for (Die die : this.dice) {
            die.roll();
        }
    }

    public Die[] getDice() {
        return dice;
    }
}
