package sample;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Controller {
    ControlObject controlObject;
    String controlObjectName;
    String chains;
    ObservableList<Connector> inputConnectors;
    ObservableList<Connector> outputConnectors;
    String directory;
    AdapterContactorChooser adapterContactorChooser;
    boolean result = false;
    Test9110 test9110;
    LogsReader logsReader;
    boolean withContactors = false;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addConnectorButton;

    @FXML
    private Button chooseDirectoryButton;

    @FXML
    private AnchorPane consoleField;

    @FXML
    private TextArea logs;

    @FXML
    private TextArea controlObjectChainsField;

    @FXML
    private ListView<Connector> controlObjectInputConnectorsListView;

    @FXML
    private ListView<Connector> controlObjectOutputConnectorsListView;

    @FXML
    private TextField controlObjectNameField;

    @FXML
    private RadioButton defaultSettingsButton;

    @FXML
    private Button deleteInConnectorButton;

    @FXML
    private Button deleteOutConnectorButton;

    @FXML
    private TextField directoryField;

    @FXML
    private Button findAdaptersAdaptersButton;

    @FXML
    private Button findAdaptersConnectorsButton;

    @FXML
    private Button generateFilesButton;

    @FXML
    private Button saveChainsButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button adapterButton;

    @FXML
    private Button connectorsButton;

    @FXML
    private Button contactorsButton;

    @FXML
    private Button autogen;

    @FXML
    private CheckBox chBoxParalel;


    @FXML
    void initialize() {



        logsReader = new LogsReader(logs);
        logsReader.getLogs();
        controlObject = new ControlObject();
        Test9110 test9110 = new Test9110(new ArrayList<Connector>(){{
            add(new Connector("1X1",Main.resourseSaver.getConnectorType("Вилка 6Р-100*")));
            add(new Connector("1X2",Main.resourseSaver.getConnectorType("Вилка 6Р-100*")));
            add(new Connector("1X3", Main.resourseSaver.getConnectorType("Вилка 6Р-100*")));
            add(new Connector("1X4",Main.resourseSaver.getConnectorType("Вилка 6Р-100*")));
            add(new Connector("1X5", Main.resourseSaver.getConnectorType("Вилка 6Р-100*")));
            add(new Connector("1X6", Main.resourseSaver.getConnectorType("Вилка 6Р-100*")));
        }});
        inputConnectors = FXCollections.observableArrayList();
        outputConnectors = FXCollections.observableArrayList();

        controlObjectInputConnectorsListView.setItems(inputConnectors);
        controlObjectOutputConnectorsListView.setItems(outputConnectors);

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
        Scene scene = new Scene(page);
        addConnectorWindow.setScene(scene);
        AddConnectors addConnectors =  loader.getController();
        addConnectors.inputConnectors = inputConnectors;
        addConnectors.outputConnectors = outputConnectors;
        addConnectorWindow.showAndWait();
            logsReader.getLogs();
        });

        deleteInConnectorButton.setOnAction(event -> {
             for (Connector c: controlObjectInputConnectorsListView.getSelectionModel().getSelectedItems()){
                 inputConnectors.remove(c);
             }
            logsReader.getLogs();
        });

        deleteOutConnectorButton.setOnAction(event -> {
            for (Connector c: controlObjectOutputConnectorsListView.getSelectionModel().getSelectedItems()){
                outputConnectors.remove(c);
                logsReader.getLogs();
            }
        });

        saveChainsButton.setOnAction(event -> {
            if (!controlObjectChainsField.getText().equals("")) {
                if (outputConnectors.size() == 0 && inputConnectors.size() == 0) {
                    System.out.println("Не заданы разъемы ОК");
                } else {
                    ArrayList<Connector> ins = new ArrayList<>(inputConnectors);
                    ArrayList<Connector> outs = new ArrayList<>(outputConnectors);
                    controlObject.setInputConnectors(ins);
                    controlObject.setOutputConnectors(outs);
                    controlObject.chains = new ArrayList<Chain>();
                    controlObject.addChains(controlObjectChainsField.getText());
                    System.out.println("Цепи добавлены: "+controlObject.chains);

                }

            }
            logsReader.getLogs();
        });

        chooseDirectoryButton.setOnAction(event -> {
            DirectoryChooser fileChooser = new DirectoryChooser();
            directoryField.setText("");
            fileChooser.setTitle("Выбор папки");
            File file = fileChooser.showDialog(new Stage());
            if (file != null) {
                directoryField.setText(file.getAbsolutePath());
            } else {
                directoryField.setText("");}
            logsReader.getLogs();
        });

        findAdaptersConnectorsButton.setOnAction(event -> {
            if (checkFields()) {
                controlObject.setName(controlObjectNameField.getText());
                adapterContactorChooser = new AdapterContactorChooser(Main.resourseSaver.getAdapters(), Main.resourseSaver.getContactors(), controlObject);
                adapterContactorChooser.findAdapterWithContactor();
                result = adapterContactorChooser.result;
                System.out.println(adapterContactorChooser.resultInfo);
                if (result){
                    test9110.findConnectors(adapterContactorChooser.data);
                    System.out.println(test9110.adaptersConnects);
                    System.out.println(adapterContactorChooser.data);
                }
            }
            withContactors = true;
            logsReader.getLogs();
        });

        findAdaptersAdaptersButton.setOnAction(event -> {
            if (checkFields()) {
                controlObject.setName(controlObjectNameField.getText());
                adapterContactorChooser = new AdapterContactorChooser(Main.resourseSaver.getAdapters(), Main.resourseSaver.getContactors(), controlObject);
                adapterContactorChooser.findAdapterWithAdapter();
                result = adapterContactorChooser.result;
                System.out.println(adapterContactorChooser.resultInfo);
                if (result){
                test9110.findConnectors(adapterContactorChooser.data);
                System.out.println(test9110.adaptersConnects);
                System.out.println(adapterContactorChooser.data);
                }

            }
            withContactors = false;
            logsReader.getLogs();
        });

        generateFilesButton.setOnAction(event -> {
            if (!result){
                System.out.println("Вспомогательные устройства не подобраны!");
            } else {
                if (directoryField.getText().equals("")) {
                    System.out.println("Укажите директорию для генерации файлов.");
                } else {
                    controlObjectName = controlObjectNameField.getText();
                    directory = directoryField.getText()+"\\" + controlObjectName;
                    File dir = new File(directory);
                    dir.mkdir();

                    try {
                        Con2Generator.writeConFile(test9110, directory + "\\" + controlObjectName + ".con");
                        System.out.println(directory + "\\" + controlObjectName + ".con"+" Записан.");
                    } catch (IOException e) {
                        System.out.println("Не удалось записать файл "+directory + "\\" + controlObjectName + ".con");
                    }
                    try {
                        ChGenetator.writeChFile(test9110,adapterContactorChooser.data,directory + "\\" + controlObjectName + ".cha");
                        System.out.println(directory + "\\" + controlObjectName + ".cha"+" Записан.");
                    } catch (IOException e) {
                        System.out.println("Не удалось записать файл "+directory + "\\" + controlObjectName + ".cha");
                    }

                    if (withContactors) {
                        try {
                            OffGenerator.writeOffFile(test9110,adapterContactorChooser.data,directory + "\\" +  controlObjectName + "_off.cha");
                            System.out.println(directory + "\\" + controlObjectName + "_off.cha"+" Записан.");
                        } catch (IOException e) {
                            System.out.println("Не удалось записать файл "+directory + "\\" + controlObjectName + "_off.cha");
                        }
                    }

                    OkGenerator okGenerator = Main.resourseSaver.okGenerator;
                    okGenerator.OK_NAME = controlObjectName;
                    okGenerator.CHAINFILE = controlObjectName + ".cha";
                    okGenerator.CONNECTFILE = controlObjectName+ ".con";
                    okGenerator.R_CHAINFILE = controlObjectName + ".cha";
                    if (withContactors) {
                        okGenerator.KZ_CHAINFILE = controlObjectName + "_off.cha";
                        okGenerator.RIZ_CHAINFILE = controlObjectName + "_off.cha";
                        okGenerator.PRIZ_CHAINFILE = controlObjectName + "_off.cha";
                    }
                    okGenerator.directory=directory;
                    okGenerator.writeOkFile();

                    try {
                            FileWriter fileWriter = new FileWriter(directory+"\\Инструкция по подключению.txt");
                            fileWriter.write(test9110.adaptersConnects.toString()+"\r\n");
                            fileWriter.write(adapterContactorChooser.data.toString());
                            fileWriter.close();
                        } catch (IOException e) {
                            System.out.println("Не удалось записать файл "+directory+"\\Инструкция по подключению.txt");
                        }
                    }

            }
            logsReader.getLogs();
        });

            adapterButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdaptersViewer.fxml"));
            AnchorPane page = null;
            try {
                page = (AnchorPane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("9110 Test Creator");
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.showAndWait();
                logsReader.getLogs();
            });

        connectorsButton.setOnAction(event ->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ConnectorsViewer.fxml"));
            AnchorPane page = null;
            try {
                page = (AnchorPane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("9110 Test Creator");
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.showAndWait();
            logsReader.getLogs();

        });

        contactorsButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ContactorsViewer.fxml"));
            AnchorPane page = null;
            try {
                page = (AnchorPane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("9110 Test Creator");
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.showAndWait();
            logsReader.getLogs();

        });

        autogen.setOnAction(event -> {
            if (inputConnectors.size() == 0 && outputConnectors.size() == 0) {
                System.out.println("Не заданы все разъемы");
            } else {
                if (chBoxParalel.isSelected()){
                StringBuilder stringBuilder = new StringBuilder();
                for (Connector inConnector : inputConnectors) {
                    for (Connector outConnector : outputConnectors) {
                        for (int i = 1; i <= outConnector.connectorType.contactsCount; i++) {
                            if (i > inConnector.connectorType.getContactsCount()) {
                                continue;
                            }
                            stringBuilder.append(inConnector.name + ":" + i + "=" + outConnector.name + ":" + i+";\n\r");
                        }
                    }
                }
                controlObjectChainsField.setText(stringBuilder.toString());
            }
            else {
                    StringBuilder stringBuilder = new StringBuilder();
                    int outContact = 1;
                    Iterator<Connector> connectorIterator = outputConnectors.iterator();
                    Connector currentOutConnector = connectorIterator.next();
                    for (Connector inConnector : inputConnectors) {
                        for (int i = 1; i <= inConnector.connectorType.getContactsCount(); i++) {
                            if (outContact > currentOutConnector.connectorType.getContactsCount()) {
                                if (!connectorIterator.hasNext()) {
                                    controlObjectChainsField.setText(stringBuilder.toString());
                                    return;
                                }
                                currentOutConnector = connectorIterator.next();
                                outContact=1;
                            }
                                stringBuilder.append(inConnector.name + ":" + i + "=" + currentOutConnector.name + ":" + outContact++ + ";\n\r");
                            }
                        }
                        controlObjectChainsField.setText(stringBuilder.toString());
                    }
            }
            logsReader.getLogs();

        });

        settingsButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("TestSettings.fxml"));
            AnchorPane page = null;
            try {
                page = (AnchorPane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("9110 Test Creator");
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.showAndWait();
            logsReader.getLogs();
        });

}

    private boolean checkFields() {
        if (controlObjectNameField.getText().equals("")) {
            System.out.println("Не задано имя ОК");
            return false;
        }
        if (controlObject.chains == null || controlObject.chains.size() == 0) {
            System.out.println("Не заданы цепи ОК");
            return false;
        }
        return true;
    }
}

