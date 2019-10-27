package dream.fcard.gui.controllers.Cards;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.Windows.MainWindow;
import dream.fcard.model.cards.FlashCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class BasicFrontBackCard extends AnchorPane {
    @FXML
    private Label questionLabel;
    @FXML
    private Button seeBackButton;

    public BasicFrontBackCard(FlashCard card, Consumer<Boolean> wantToSeeBack) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Cards/Front/BasicFrontBackCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();

            String front = card.getFront();
            questionLabel.setText(front);
            seeBackButton.setOnAction(e -> {
                wantToSeeBack.accept(true);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
