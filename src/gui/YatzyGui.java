package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.RaffleCup;
import models.YatzyResultCalculator;

public class YatzyGui extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Yatzy");
        GridPane gridPane = new GridPane();
        this.initContent(gridPane);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToHeight(false);
        scrollPane.setFitToWidth(false);

        Scene scene = new Scene(scrollPane,343,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    RaffleCup raffleCup = new RaffleCup();
    Label[] diceLabels = new Label[5];
    CheckBox[] diceCheckBoxes = new CheckBox[5];
    Label labelNrOfThrowsLeft = new Label();
    int nrOfThrowsLeft = 3;
    TextField[] upperTextFields = new TextField[6];
    Button[] upperButtons = new Button[6];
    TextField txfSum = new TextField();
    TextField txfBonus = new TextField();
    TextField[] lowerTextFields = new TextField[9];
    Button[] lowerButtons = new Button[9];
    TextField txfTotal = new TextField();

    private void initContent(GridPane gridPane) {
        gridPane.setGridLinesVisible(false);

        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(8);

        VBox vb = new VBox();
        vb.setLayoutX(5);
        vb.setSpacing(10);
        vb.setStyle("-fx-border-style: solid inside;" + "-fx-border-radius: 5;" + "-fx-padding: 50;");
        gridPane.add(vb, 0, 0, 7, 5);

        VBox vb2 = new VBox();
        vb2.setLayoutX(5);
        vb2.setSpacing(10);
        vb2.setStyle("-fx-border-style: solid inside;" + "-fx-border-radius: 5;" + "-fx-padding: 50;");
        gridPane.add(vb2, 0, 5, 7, 23);

        //Laver labels til at vise tallene for de kastede terninger
        for (int i = 0; i < diceLabels.length; i++) {
            diceLabels[i] = new Label(raffleCup.getDice()[i].getEyes() + "");
            diceLabels[i].setStyle("-fx-border-style: solid inside;" + "-fx-border-radius: 5;" + "-fx-padding: 20;");
            gridPane.add(diceLabels[i],i+1,1);
        }

        //Laver CheckBoxe til at holde tal man ikke vil ændre
        for (int i = 0; i < diceCheckBoxes.length; i++) {
            diceCheckBoxes[i] = new CheckBox("Hold");
            gridPane.add(diceCheckBoxes[i],i+1,2);
        }

        Label labelTextNrOfThrowsLeft = new Label("Antal kast tilbage:");
        gridPane.add(labelTextNrOfThrowsLeft,1,3,2,1);

        labelNrOfThrowsLeft.setText(nrOfThrowsLeft + "");
        gridPane.add(labelNrOfThrowsLeft,3,3);

        Button btnThrowDice = new Button("Kast terningerne");
        gridPane.add(btnThrowDice,4,3,2,1);
        btnThrowDice.setOnAction(event -> throwDiceAction());

        //Opretter labels, textfields og knapper til de øvre tal
        for (int i = 0; i < upperTextFields.length; i++) {
            Label label = new Label();
            label.setText(i+1 + "'ere");
            gridPane.add(label,1,i+7);
            upperTextFields[i] = new TextField();
            upperTextFields[i].setPrefWidth(10);
            upperTextFields[i].setEditable(false);
            gridPane.add(upperTextFields[i],3,i+7);
            upperButtons[i] = new Button("Gem");
            gridPane.add(upperButtons[i],5,i+7);
            upperButtons[i].setOnAction(event -> saveUpperFieldsValueAction(event));
        }

        //Opretter labels og textfields til summen og bonussen af de øvre tal
        Label lblSum = new Label("Sum");
        gridPane.add(lblSum,3,14);
        gridPane.add(txfSum,4,14);
        txfSum.setPrefWidth(10);
        txfSum.setEditable(false);
        Label lblBonus = new Label("Bonus");
        gridPane.add(lblBonus,3,15);
        txfBonus.setPrefWidth(10);
        txfBonus.setEditable(false);
        gridPane.add(txfBonus,4,15);

        //Opretter labels, textfields og buttons til de nedre pointmuligheder
        Label[] lowerLabels = {new Label("Et par"), new Label("To par"), new Label("3 ens"), new Label("4 ens"), new Label("Lille straight"), new Label("Store straight"), new Label("Fuldt hus"), new Label("Chance"), new Label("Yatzy")};
        for (int i = 0; i < lowerTextFields.length; i++) {
            gridPane.add(lowerLabels[i],1,i+16,2,1);
            lowerTextFields[i] = new TextField();
            lowerTextFields[i].setPrefWidth(10);
            lowerTextFields[i].setEditable(false);
            gridPane.add(lowerTextFields[i],3,i+16);
            lowerButtons[i] = new Button("Gem");
            gridPane.add(lowerButtons[i],5,i+16);
            lowerButtons[i].setOnAction(event -> chooseEventAction(event));
        }

        Label lblTotal = new Label("Total");
        gridPane.add(lblTotal,3,26);
        gridPane.add(txfTotal,4,26);
        txfTotal.setPrefWidth(10);
        txfTotal.setEditable(false);
    }

    private void chooseEventAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        YatzyResultCalculator resultCalculator = new YatzyResultCalculator(raffleCup.getDice());

        for (int i = 0; i < lowerButtons.length; i++) {
            if (button == lowerButtons[i]) lowerButtons[i].setDisable(true);
        }
        updateTotal();
    }

    private void updateTotal() {
        int sum = 0;
        boolean allUpperFieldsFilled = true;
        boolean allLowerFieldsFilled = true;
        for (int i = 0; i < lowerTextFields.length; i++) {
            if (lowerButtons[i].isDisabled()) sum += Integer.parseInt(lowerTextFields[i].getText().trim());
        }
        for (int i = 0; i < upperTextFields.length; i++) {
            if (!upperButtons[i].isDisabled()) allUpperFieldsFilled = false;
        }
        for (int i = 0; i < lowerTextFields.length; i++) {
            if (!lowerButtons[i].isDisabled()) allLowerFieldsFilled = false;
        }
        if (allUpperFieldsFilled == true) {
            sum += Integer.parseInt(txfSum.getText().trim());
            sum += Integer.parseInt(txfBonus.getText().trim());
        }
        txfTotal.setText(sum + "");
        if (allUpperFieldsFilled == true && allLowerFieldsFilled == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Yatzy");
            alert.setHeaderText("Tillykke");
            alert.setContentText("Du er nu færdig med Yatzy. Du fik " + txfTotal.getText().trim() + " point");
            alert.show();
        }
        reset();
    }

    private void throwDiceAction() {
        if (nrOfThrowsLeft > 0) {
            throwUnchosenDice();
            nrOfThrowsLeft--;
            labelNrOfThrowsLeft.setText(nrOfThrowsLeft + "");
            updatePointFields();
        }
        for (int i = 0; i < diceLabels.length; i++) {
            diceLabels[i].setText(raffleCup.getDice()[i].getEyes() + "");
        }
    }

    private void updatePointFields() {
        YatzyResultCalculator resultCalculator = new YatzyResultCalculator(raffleCup.getDice());
        for (int i = 0; i < upperTextFields.length; i++) {
            if (!upperButtons[i].isDisable()) upperTextFields[i].setText(resultCalculator.upperSectionScore(i+1) + "");
        }
        if (!lowerButtons[0].isDisabled()) lowerTextFields[0].setText(resultCalculator.onePairScore() + "");
        if (!lowerButtons[1].isDisabled()) lowerTextFields[1].setText(resultCalculator.twoPairScore() + "");
        if (!lowerButtons[2].isDisabled()) lowerTextFields[2].setText(resultCalculator.threeOfAKindScore() + "");
        if (!lowerButtons[3].isDisabled()) lowerTextFields[3].setText(resultCalculator.fourOfAKindScore() + "");
        if (!lowerButtons[4].isDisabled()) lowerTextFields[4].setText(resultCalculator.smallStraightScore() + "");
        if (!lowerButtons[5].isDisabled()) lowerTextFields[5].setText(resultCalculator.largeStraightScore() + "");
        if (!lowerButtons[6].isDisabled()) lowerTextFields[6].setText(resultCalculator.fullHouseScore() + "");
        if (!lowerButtons[7].isDisabled()) lowerTextFields[7].setText(resultCalculator.chanceScore() + "");
        if (!lowerButtons[8].isDisabled()) lowerTextFields[8].setText(resultCalculator.yatzyScore() + "");
    }

    private void throwUnchosenDice() {
        for (int i = 0; i < raffleCup.getDice().length; i++) {
            if (!diceCheckBoxes[i].isSelected()) raffleCup.getDice()[i].roll();
        }
    }

    private void saveUpperFieldsValueAction(Event event) {
        Button button = (Button) event.getSource();


        for (int i = 0; i < upperTextFields.length; i++) {
            if (button == upperButtons[i]) upperButtons[i].setDisable(true);
        }

        updateSumAndBonus();
        updateTotal();
    }

    private void updateSumAndBonus() {
        int sumField = 0;
        //Opdaterer sum feltet
        for (int i = 0; i < upperTextFields.length; i++) {
            if (upperButtons[i].isDisable()) {
                sumField += Integer.parseInt(upperTextFields[i].getText().trim());
            }
        }
        txfSum.setText(sumField + "");

        //Opdaterer bonus feltet
        int sum = Integer.parseInt(txfSum.getText().trim());
        if (sum >= 63) txfBonus.setText("50");
        else txfBonus.setText("0");
    }

    private void reset() {
        raffleCup = new RaffleCup();
        for (int i = 0; i < diceLabels.length; i++) {
            diceLabels[i].setText(raffleCup.getDice()[i].getEyes() + "");
        }
        nrOfThrowsLeft = 3;
        labelNrOfThrowsLeft.setText(nrOfThrowsLeft + "");
        for (int i = 0; i < upperTextFields.length; i++) {
            if (!upperButtons[i].isDisabled()) upperTextFields[i].setText("0");
        }
        for (int i = 0; i < lowerTextFields.length; i++) {
            if (!lowerButtons[i].isDisabled()) lowerTextFields[i].setText("0");
        }
        for (CheckBox checkBox : diceCheckBoxes) checkBox.setSelected(false);
    }
}
