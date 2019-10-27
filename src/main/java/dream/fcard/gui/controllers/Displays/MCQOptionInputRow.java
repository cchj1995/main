package dream.fcard.gui.controllers.Displays;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.Windows.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class MCQOptionInputRow extends HBox {
    @FXML
    private Label optionValue;
    @FXML
    private TextField optionText;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addNewRowButton;
    @FXML
    private RadioButton rightAnswerRadio;

    public MCQOptionInputRow(ToggleGroup rightAnswer, Consumer<MCQOptionInputRow> deleteRow, Consumer<Boolean> addNewRow) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays/MCQOptionInputRow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            deleteButton.setOnAction(e -> deleteRow.accept(this));
            addNewRowButton.setOnAction(e->addNewRow.accept(true));
            rightAnswerRadio.setToggleGroup(rightAnswer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setOptionNumber(int i) {
        optionValue.setText(i + ".");
    }

    boolean isBlank() {
        return optionText.getText().isBlank();
    }

    boolean hasRightAnswer() {
        return rightAnswerRadio.isSelected();
    }

    String getOption() {
        return optionText.getText();
    }

    void setOptionText(String text) {optionText.setText(text);}

    void setRightAnswerRadio() {rightAnswerRadio.setSelected(true);}
}
