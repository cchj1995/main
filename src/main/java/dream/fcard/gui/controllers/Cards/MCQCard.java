package dream.fcard.gui.controllers.Cards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.Windows.MainWindow;
import dream.fcard.model.ConsumerSchema;
import dream.fcard.model.State;
import dream.fcard.model.cards.MultipleChoiceCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MCQCard extends AnchorPane {
    @FXML
    private Label questionLabel;
    @FXML
    private Button seeBackButton;
    @FXML
    private ScrollPane choiceScrollPane;
    @FXML
    private VBox choiceContainer;

    private ToggleGroup toggleGroup = new ToggleGroup();

    @SuppressWarnings("unchecked")
    private Consumer<String> displayMessage = State.getState().getConsumer(ConsumerSchema.DISPLAY_MESSAGE);

    public MCQCard(MultipleChoiceCard mcqCard, Consumer<Integer> seeBackOfMcqCard, int preselectedOption) {
        //if preselectedOption is -1, it means that the user has not done this card before in this session.
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Cards/Front/MCQCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            questionLabel.setText(mcqCard.getFront());
            populateOptions(mcqCard, preselectedOption);
            seeBackButton.setOnAction(e -> {
                if (toggleGroup.getSelectedToggle() == null) {
                    displayMessage.accept("You need to select an option!");
                } else {
                    RadioButton chosen = (RadioButton) toggleGroup.getSelectedToggle();
                    int selectedAnswer = toggleGroup.getToggles().indexOf(chosen) + 1;
                    seeBackOfMcqCard.accept(selectedAnswer);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateOptions(MultipleChoiceCard mcqCard, int preselectedOption) {
        if (preselectedOption == -1) { //shuffle and deselect all choices
            ArrayList<String> shuffledChoices = mcqCard.getShuffledChoices();
            shuffledChoices.forEach(option -> {
                RadioButton radioButton = new RadioButton(option);
                radioButton.setToggleGroup(toggleGroup);
                choiceContainer.getChildren().add(radioButton);
            });
        } else {
            //do not shuffle and select the preselected option
            ArrayList<String> previousArrangementOfChoices = mcqCard.getDisplayChoices();
            int selectedIndex = preselectedOption - 1; // preselectedOption is 1-based
            for (int i = 0; i < previousArrangementOfChoices.size(); i++) {
                RadioButton radioButton = new RadioButton(previousArrangementOfChoices.get(i));
                radioButton.setToggleGroup(toggleGroup);
                choiceContainer.getChildren().add(radioButton);
                if (i == selectedIndex) {
                    radioButton.setSelected(true);
                }
            }
        }
    }
}
