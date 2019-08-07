package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ContactorsViewer {
    ObservableList<Connector> connectors;
    ObservableList<Contactor> contactors;
    ArrayList<Connector> connectorsList;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addAsapterButton;

    @FXML
    private Button addConnectorButton;

    @FXML
    private Button autoGen;

    @FXML
    private TextArea chainsTextArea;

    @FXML
    private ListView<Connector> connectorsListView;

    @FXML
    private AnchorPane consoleField;

    @FXML
    private TextField contactorNameField;

    @FXML
    private ListView<Contactor> contactorsListView;

    @FXML
    private Button deleteAdapter;

    @FXML
    private Button deleteConnectorButton;

    @FXML
    private TextArea logs;

    @FXML
    private Button saveButton;

    @FXML
    TextField count;

    @FXML
    void initialize() {
        LogsReader logsReader = new LogsReader(logs);
        contactors = FXCollections.observableArrayList(Main.resourseSaver.getContactors());
        FXCollections.sort(contactors, Comparator.comparing(Contactor::toString));
        contactorsListView.setItems(contactors);

        contactorsListView.getSelectionModel().selectedItemProperty().addListener(event ->{
            if (contactorsListView.getSelectionModel().getSelectedItems().size()==0) {
                return;
            }
            Contactor contactor = contactorsListView.getSelectionModel().getSelectedItem();
           contactorNameField.setText(contactor.name);
           contactorNameField.disableProperty().setValue(true);
           count.setText(String.valueOf(contactor.count));
           connectorsList = new ArrayList<Connector>(){{
               add(contactor.connector);
           }};
            connectors = FXCollections.observableArrayList(connectorsList);
            connectorsListView.setItems(connectors);
            StringBuilder sb = new StringBuilder();
            for (Chain c : contactor.getChains()) {
                sb.append(c);
            }
            chainsTextArea.setText(sb.toString());
            logsReader.getLogs();

        });

        deleteConnectorButton.setOnAction(event -> {
            for (Connector c: connectorsListView.getSelectionModel().getSelectedItems()){
                connectors.remove(c);
            }
            logsReader.getLogs();
        });

        addAsapterButton.setOnAction(event -> {
            contactorNameField.disableProperty().setValue(false);
            contactorNameField.setText("");
            connectors = FXCollections.observableArrayList(new ArrayList<Connector>());
            connectorsListView.setItems(connectors);
            chainsTextArea.setText("");
            logsReader.getLogs();
        });

        addConnectorButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addConnectors.fxml"));
            AnchorPane page = null;
            try {
                page = (AnchorPane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage addConnectorWindow = new Stage();
            addConnectorWindow.setTitle("9110 Test Creator");
            addConnectorWindow.initModality(Modality.WINDOW_MODAL);
//        addConnectorWindow.initOwner(); хз
            Scene scene = new Scene(page);
            addConnectorWindow.setScene(scene);
            AddConnectors addConnectors =  loader.getController();
            addConnectors.inputConnectors = connectors;
            addConnectors.outputConnectors = connectors;
            addConnectorWindow.showAndWait();
            logsReader.getLogs();
        });

        deleteAdapter.setOnAction(event -> {
            Contactor contactor = contactorsListView.getSelectionModel().getSelectedItem();
            if (contactors.size()!=0){
                contactors.remove(contactor);
                Main.resourseSaver.removeContactor(contactor.name);
                System.out.println(Main.resourseSaver.contactors);}
            logsReader.getLogs();
        });

        saveButton.setOnAction(event -> {
            if (contactorNameField.getText().equals("")) {
                Main.resourseSaver.saveContactors();
            }
            else {
                if (connectors.size()==1){
                    if (!count.getText().equals("")){
                        Contactor contactor = new Contactor(contactorNameField.getText(),connectors.get(0),Integer.parseInt(count.getText())); //co-vo
                        if (!chainsTextArea.getText().equals("")){
                            contactor.addChains(chainsTextArea.getText());
                            Main.resourseSaver.removeContactor(contactor.name);
                            Main.resourseSaver.contactors.add(contactor);
                            contactors.remove(contactor);
                            contactors.add(contactor);
                            contactorsListView.setItems(null);
                            FXCollections.sort(contactors,Comparator.comparing(Contactor::toString));
                            contactorsListView.setItems(contactors);

                            Main.resourseSaver.saveContactors();
                        }
                        else {
                            System.out.println("Цепи не заданы");
                        }
                    } else {
                        System.out.println("Количество замыкателей не задано");
                    }

                }
                else {
                    System.out.println("Кол-во разъемов не соответствует 1");
                }

            }
            logsReader.getLogs();
        });

        autoGen.setOnAction(event -> {
            if (connectors.size() != 1) {
                System.out.println("Кол-во разъемов не соответствует 1");
            }
            else {
                Contactor contactor = new Contactor("NA", connectors.get(0));
                String s = ContactorChainsGenerator.generateChains(contactor);
                chainsTextArea.setText(s);
            }
            logsReader.getLogs();
        });
    }

}
