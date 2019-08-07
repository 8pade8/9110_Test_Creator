package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogsReader {
    FileReader fileReader;
    BufferedReader bufferedReader;
    StringBuilder s;
    @FXML
    TextArea textArea;

    public LogsReader(TextArea textArea) {
        s = new StringBuilder();
        this.textArea = textArea;
        try {this.fileReader = new FileReader("Resources\\logs.txt");
        } catch (Exception e) {
            System.out.println("Файл logs.txt не найден");
        }
        bufferedReader = new BufferedReader(fileReader);
    }

    public void getLogs() {

        while (true){
            try {
                if (!bufferedReader.ready()) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                s.append(bufferedReader.readLine()+"\n\r");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        textArea.setText(s.toString());
        textArea.setScrollTop(Double.MAX_VALUE);
    }
}
