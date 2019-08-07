package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


public class ConnectorsViewer {

    ObservableList<ConnectorsTypePair> connectorsTypePairs;
    ObservableList<ConnectorType> connectorsTypes;
    private boolean edit = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TextField connectorNameField;

    @FXML
    private TextField answerconnectorNameField;

    @FXML
    private ListView<ConnectorType> connectorsListView;

    @FXML
    private AnchorPane consoleField;

    @FXML
    private TextField contactCount;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextArea logs;

    @FXML
    private ChoiceBox<String> mainTypeChoiceBox;

    @FXML
    private ChoiceBox<String> answerTypeChoiceBox;


    @FXML
    void initialize() {
        LogsReader logsReader = new LogsReader(logs);
        connectorsTypes = FXCollections.observableArrayList(Main.resourseSaver.getConnectors());
        connectorsListView.setItems(connectorsTypes);
        FXCollections.sort(connectorsTypes, Comparator.comparing(ConnectorType::toString));
        connectorsListView.getSelectionModel().selectedItemProperty().addListener(event -> {
            if (connectorsListView.getSelectionModel().getSelectedItems().size()==0) {
                return;
            }
            edit = true;
            ConnectorType connector = connectorsListView.getSelectionModel().getSelectedItem();
            ConnectorType answerconnector = connector.answerConnectorType;
            mainTypeChoiceBox.setValue(connector.type.contains("Вилка")?"Вилка":"Розетка");
            connectorNameField.setText(connector.toString().replace("Вилка ","").replace("Розетка ",""));
            answerTypeChoiceBox.setValue(answerconnector.type.contains("Вилка")?"Вилка":"Розетка");
            answerconnectorNameField.setText(answerconnector.toString().replace("Вилка ","").replace("Розетка ",""));
            contactCount.setText(String.valueOf(connector.contactsCount));

        });

        mainTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(event ->{
            String s = mainTypeChoiceBox.getSelectionModel().getSelectedItem();
            switch (s) {
                case "Розетка":
                    answerTypeChoiceBox.setValue("Вилка");
                    break;
                case "Вилка":
                    answerTypeChoiceBox.setValue("Розетка");
                    break;
            }
        });

        answerTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(event ->{
            String s = answerTypeChoiceBox.getSelectionModel().getSelectedItem();
            switch (s) {
                case "Розетка":
                    mainTypeChoiceBox.setValue("Вилка");
                    break;
                case "Вилка":
                    mainTypeChoiceBox.setValue("Розетка");
                    break;
            }
        });

        deleteButton.setOnAction(event -> {
            ConnectorType connector = connectorsListView.getSelectionModel().getSelectedItem();

            ArrayList<Connectable> list = Main.resourseSaver.analizeUseConnector(connector);
            list.addAll(Main.resourseSaver.analizeUseConnector(connector.answerConnectorType));
            if (list.size() != 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Удаление не возможно!");
                StringBuilder stringBuilder = new StringBuilder("Разъем "+connector+" используется в ");
                for (Connectable c : list) {
                    stringBuilder.append(c.getType()+" "+c.getName()+", ");
                }
                stringBuilder.append("удаление не возможно.");
                alert.setContentText(stringBuilder.toString());
                alert.showAndWait();
                return;
            }
            if (connectorsTypes.size()!=0){
                connectorsTypes.remove(connector);
                connectorsTypes.remove(connector.answerConnectorType);
                Main.resourseSaver.removeConnector(connector.type);
                Main.resourseSaver.removeConnector(connector.answerConnectorType.type);
                System.out.println(Main.resourseSaver.connectors);}
            logsReader.getLogs();
        });

        saveButton.setOnAction(event -> {

                    if (!contactCount.getText().equals("")&&!connectorNameField.equals("")&&!mainTypeChoiceBox.getValue().equals("")
                    &&!answerTypeChoiceBox.getValue().equals("")&&!answerconnectorNameField.getText().equals("")){
                        ConnectorType connector = connectorsListView.getSelectionModel().getSelectedItem();
                        if (!edit) {
                            ConnectorsTypePair newpair = new ConnectorsTypePair(mainTypeChoiceBox.getValue()+" "+connectorNameField.getText(),
                                    answerTypeChoiceBox.getValue()+" "+answerconnectorNameField.getText(),Integer.parseInt(contactCount.getText()));
                            Main.resourseSaver.addConnector(newpair.getFirstConnectorType());
                            Main.resourseSaver.addConnector(newpair.getSecondConnectorType());
                            connectorsTypes.add(newpair.getFirstConnectorType());
                            connectorsTypes.add(newpair.getSecondConnectorType());
                            Main.resourseSaver.saveConnectors();
                            connectorsListView.setItems(null);
                            FXCollections.sort(connectorsTypes, Comparator.comparing(ConnectorType::toString));
                            connectorsListView.setItems(connectorsTypes);
                        } else {
                            Main.resourseSaver.removeConnector(connector.type);
                            Main.resourseSaver.removeConnector(connector.answerConnectorType.type);
                            ConnectorType oldType = connector;
                            ConnectorType oldType2 = connector.answerConnectorType;
                            ConnectorsTypePair connectorpair = new ConnectorsTypePair(mainTypeChoiceBox.getValue()+" "+connectorNameField.getText(),
                                    answerTypeChoiceBox.getValue()+" "+answerconnectorNameField.getText(),Integer.parseInt(contactCount.getText()));
                            Main.resourseSaver.updateAdapters(oldType, connectorpair);
                            Main.resourseSaver.updateAdapters(oldType2, connectorpair);
                            Main.resourseSaver.updateContactors(oldType, connectorpair);
                            Main.resourseSaver.updateContactors(oldType2, connectorpair);
                            Main.resourseSaver.addConnector(connectorpair.getFirstConnectorType());
                            Main.resourseSaver.addConnector(connectorpair.getSecondConnectorType());
                            Main.resourseSaver.saveAll();
                            connectorsListView.setItems(null);
                            connectorsTypes = FXCollections.observableArrayList(Main.resourseSaver.getConnectors());
                            FXCollections.sort(connectorsTypes, Comparator.comparing(ConnectorType::toString));
                            connectorsListView.setItems(connectorsTypes);


                        }
                        connectorNameField.setText("");
                        answerconnectorNameField.setText("");
                        contactCount.setText("");
                        contactCount.setText("");
                        contactCount.disableProperty().setValue(true);
                    }
                    else {
                        System.out.println("Поля не заполнены");
                    }

                logsReader.getLogs();

        });

        addButton.setOnAction(event -> {
            edit=false;
            connectorNameField.setText("");
            answerconnectorNameField.setText("");
            contactCount.setText("");
            contactCount.setText("");
            contactCount.disableProperty().setValue(false);
            logsReader.getLogs();
        });
    }

}
