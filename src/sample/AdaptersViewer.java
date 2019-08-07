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


public class AdaptersViewer {
    ObservableList<Adapter> adapters;
    ObservableList<Connector> inputs;
    ObservableList<Connector> outputs;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button adOutButton;

    @FXML
    private TextField adapterNameField;

    @FXML
    private ListView<Adapter> adaptersListView;

    @FXML
    private Button addAsapterButton;

    @FXML
    private Button addInButton;

    @FXML
    private TextArea chainsTextArea;

    @FXML
    private TextArea logs;

    @FXML
    private Button deleteInButton;

    @FXML
    private Button deleteOutButton;

    @FXML
    private ListView<Connector> inputConnectorsListView;

    @FXML
    private ListView<Connector> outputConnectorsListView;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteAdapter;

    @FXML
    private Button autogen;

    @FXML
    private TextField count;

    @FXML
    void initialize() {
        LogsReader logsReader = new LogsReader(logs);
        adapters = FXCollections.observableArrayList(Main.resourseSaver.getAdapters());
        FXCollections.sort(adapters, Comparator.comparing(Adapter::getName));
        adaptersListView.setItems(adapters);
        adaptersListView.getSelectionModel().selectedItemProperty().addListener(event -> {
            if (adaptersListView.getSelectionModel().getSelectedItems().size() == 0) {
                return;
            }
            Adapter adapter = adaptersListView.getSelectionModel().getSelectedItem();
            adapterNameField.setText(adapter.getName());
            adapterNameField.disableProperty().setValue(true);
            count.setText(String.valueOf(adapter.getCount()));
            inputs = FXCollections.observableArrayList(adapter.getInputConnectorsList());
            outputs = FXCollections.observableArrayList(adapter.getOutputConnectorsList());
            inputConnectorsListView.setItems(inputs);
            outputConnectorsListView.setItems(outputs);
            StringBuilder sb = new StringBuilder();
            for (Chain c : adapter.getChains()) {
                sb.append(c);
            }
            chainsTextArea.setText(sb.toString());
            logsReader.getLogs();
        });

        deleteInButton.setOnAction(event -> {
            for (Connector c: inputConnectorsListView.getSelectionModel().getSelectedItems()){
                inputs.remove(c);
            }
            logsReader.getLogs();
        });

        deleteOutButton.setOnAction(event -> {
            for (Connector c: outputConnectorsListView.getSelectionModel().getSelectedItems()){
                outputs.remove(c);
            }
            logsReader.getLogs();
        });

        addAsapterButton.setOnAction(event -> {
            adapterNameField.disableProperty().setValue(false);
            adapterNameField.setText("");
            inputs = FXCollections.observableArrayList(new ArrayList<Connector>());
            inputConnectorsListView.setItems(inputs);
            outputs = FXCollections.observableArrayList(new ArrayList<Connector>());
            outputConnectorsListView.setItems(outputs);
            chainsTextArea.setText("");
            count.setText("");
            logsReader.getLogs();
        });

        addInButton.setOnAction(event -> {
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
            addConnectors.inputConnectors = inputs;
            addConnectors.outputConnectors = outputs;
            addConnectorWindow.showAndWait();
            logsReader.getLogs();
        });
        adOutButton.setOnAction(event -> {
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
            addConnectors.inputConnectors = inputs;
            addConnectors.outputConnectors = outputs;
            addConnectorWindow.showAndWait();
            logsReader.getLogs();
        });

            deleteAdapter.setOnAction(event -> {
                Adapter adapter = adaptersListView.getSelectionModel().getSelectedItem();
                if (adapters.size()!=0){
                adapters.remove(adapter);
                Main.resourseSaver.removeAdapter(adapter.getName());
                System.out.println(Main.resourseSaver.adapters);}
                logsReader.getLogs();
            });

            saveButton.setOnAction(event -> {
                if (adapterNameField.getText().equals("")) {
                    Main.resourseSaver.saveAdapters();
                }
                else {

                    if (count.getText().equals("")){
                        System.out.println("Количество не задано");}
                    else {

                     if (outputs.size()!=0&&inputs.size()!=0){



                        Adapter adapter = new Adapter(adapterNameField.getText(), new ArrayList<>(outputs), new ArrayList<>(inputs));
                        if (!chainsTextArea.getText().equals("")) {
                            adapter.setCount(Integer.parseInt(count.getText()));
                            adapter.addChains(chainsTextArea.getText());
                            Main.resourseSaver.removeAdapter(adapter.getName());
                            Main.resourseSaver.adapters.add(adapter);
                            adapters.remove(adapter);
                            adapters.add(adapter);
                            adaptersListView.setItems(null);
                            FXCollections.sort(adapters,Comparator.comparing(Adapter::getName));
                            adaptersListView.setItems(adapters);
                            Main.resourseSaver.saveAdapters();


                        }
                        else {
                            System.out.println("Цепи не заданы");
                            logsReader.getLogs();
                            return;
                        }
                    }

                    }
                }
                logsReader.getLogs();
            });

            autogen.setOnAction(event -> {
                if (inputs.size() == 0 || outputs.size() == 0) {
                    return;
                }
                else {
                    Adapter adapter = new Adapter("NA", new ArrayList<Connector>(outputs), new ArrayList<Connector>(inputs));
                    String s = AdapderChainsGenerator.generateChains(adapter);
                    chainsTextArea.setText(s);
                }
                logsReader.getLogs();
            });

    }



}
