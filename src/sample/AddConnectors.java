package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


public class AddConnectors {

    ObservableList<Connector> inputConnectors;
    ObservableList<Connector> outputConnectors;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private ChoiceBox<ConnectorType> chooseConnectorTypeField;

    @FXML
    private TextField connectorNameField;

    @FXML
    private AnchorPane consoleField;

    @FXML
    private RadioButton isInConnectorButton;

    @FXML
    private RadioButton isOutConnectorButton;

    @FXML
    private TextArea logs;


    @FXML
    void initialize() {
        LogsReader logsReader = new LogsReader(logs);
        ObservableList<ConnectorType> list = FXCollections.observableArrayList(Main.resourseSaver.getConnectors());
        FXCollections.sort(list, Comparator.comparing(ConnectorType::toString));
        chooseConnectorTypeField.setItems(list);

        addButton.setOnAction(event -> {
           if( checkFields()){
               if (isInConnectorButton.isSelected()) {
                   inputConnectors.add(new Connector(connectorNameField.getText(), Main.resourseSaver.getConnectorType(chooseConnectorTypeField.getValue().toString())));
                   System.out.println("Разъем "+connectorNameField.getText()+" добавлен");
               } else if(isOutConnectorButton.isSelected()){
                   outputConnectors.add(new Connector(connectorNameField.getText(), Main.resourseSaver.getConnectorType(chooseConnectorTypeField.getValue().toString())));
                   System.out.println("Разъем "+connectorNameField.getText()+" добавлен");
               } else {
                   System.out.println("Ошибка");
               }
               connectorNameField.setText("");
           }
           logsReader.getLogs();
        });
    }

    private boolean checkFields() {
        if (chooseConnectorTypeField.getValue() != null&&!connectorNameField.getText().equals("")) {
            return true;
        }
        return false;
    }

}
