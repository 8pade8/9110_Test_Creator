package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;


public class TestSettings {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox ASKM4WIRE;

    @FXML
    private ChoiceBox<String> Arp_MeasMode;

    @FXML
    private ChoiceBox<String> Arp_Connect;

    @FXML
    private TextField C_DIFF;

    @FXML
    private ComboBox<String> C_MaxRange;

    @FXML
    private CheckBox KZ_IZM;

    @FXML
    private TextArea Logs;

    @FXML
    private RadioButton PRIZ_AC;

    @FXML
    private RadioButton PRIZ_DC;

    @FXML
    private TextField PRIZ_RIZE;

    @FXML
    private TextField PRIZ_Time;

    @FXML
    private TextField PRIZ_U;

    @FXML
    private ChoiceBox<String> RIZ_Aper;

    @FXML
    private TextField RIZ_Delay;

    @FXML
    private CheckBox RIZ_IZM;

    @FXML
    private TextField RIZ_Rize;

    @FXML
    private TextField RIZ_U;

    @FXML
    private ChoiceBox<String> R_Aper;

    @FXML
    private TextField R_DIFF;

    @FXML
    private TextField R_Delay;

    @FXML
    private CheckBox R_IZM;

    @FXML
    private ChoiceBox<String> R_MaxRange;

    @FXML
    private Button SaveDefault;

    @FXML
    private CheckBox TEST_KZ;

    @FXML
    private CheckBox TEST_PRIZ;

    @FXML
    private CheckBox TEST_R;

    @FXML
    private CheckBox TEST_RIZ;

    @FXML
    private Button Use;

    @FXML
    private TextField tstRIZ_N;

    @FXML
    private TextField tstR_N;

    @FXML
    private TextField tstKZ_N;

    @FXML
    private ToggleGroup voltage;



    @FXML
    private ChoiceBox<String> Arp_Range;

    @FXML
    void initialize() {
        LogsReader logsReader = new LogsReader(Logs);

        logsReader.getLogs();
        Use.setOnAction(event -> {
            boolean isChecked = checkTextFields();
            if (isChecked) {
                saveToOk(Main.resourseSaver.okGenerator);
                System.out.println("Настройки сохранены");
            }
                 logsReader.getLogs();
        });

        SaveDefault.setOnAction(event -> {
            boolean isChecked = checkTextFields();
            if (isChecked) {
                saveToOk(Main.resourseSaver.okGenerator);
                System.out.println("Настройки сохранены");
            }
            Main.resourseSaver.saveOkDefault();
            logsReader.getLogs();
        });

        Arp_MeasMode.getSelectionModel().selectedItemProperty().addListener(event ->{
            String s = Arp_MeasMode.getSelectionModel().getSelectedItem();
            switch (s) {
                case ("Омметр"):
                    Arp_Range.setVisible(true);
                    Arp_Range.setItems(FXCollections.observableArrayList(new ArrayList<String>(){{
                        add("Автовыбор");
                        add("10 Ом");
                        add("100 Ом");
                        add("1 кОм");
                        add("10 кОм");
                        add("100 кОм");
                        add("1 МОм");
                        add("10 МОм");
                        add("100 МОм");
                    }}));
                    Arp_Connect.setItems(FXCollections.observableArrayList(new ArrayList<String>(){{
                        add("Точки цепи (относительно опорной точки)");
                        add("Цепи относительно остальных");
                        add("Между всеми цепями попарно");
                    }}));
                    break;
                case ("Мегомметр"):
                    Arp_Range.setVisible(false);
                    Arp_Connect.setItems(FXCollections.observableArrayList(new ArrayList<String>(){{
                        add("Цепи относительно остальных");
                        add("Между всеми цепями попарно");
                    }}));
                    break;
                case ("Измерение емкости"):
                    Arp_Range.setVisible(true);
                    Arp_Range.setItems(FXCollections.observableArrayList(new ArrayList<String>(){{
                        add("Автовыбор");
                        add("1 нФ");
                        add("10 нФ");
                        add("100 нФ");
                        add("1 мкФ");
                    }}));
                    Arp_Connect.setItems(FXCollections.observableArrayList(new ArrayList<String>(){{
                        add("Точки цепи (относительно опорной точки)");
                        add("Цепи относительно остальных");
                        add("Между всеми цепями попарно");
                    }}));
                    break;
            }
        });

        loadOfOk(Main.resourseSaver.okGenerator);



    }

    public boolean checkTextFields() {
        return
        check_R_DIFF() &&
        check_C_DIFF() &&
        check_tstR_N() &&
        check_tstKZ_N() &&
        check_tstRIZ_N() &&
        check_PRIZ_Time() &&
        check_R_Delay() &&
        check_RIZ_U() &&
        check_RIZ_Rize()&&
        check_RIZ_Delay()&&
        check_PRIZ_U()&&
        check_PRIZ_RIZE();
    }

    public boolean check_R_DIFF() {
        String s = R_DIFF.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Поправка на сопротивление соединительных проводов, Ом");
            return false;
        }
        if (!TextValidator.isDouble(s)) {
            System.out.println("Неверный формат: Поправка на сопротивление соединительных проводов, Ом");
            return false;
        } else {
            Double d = Double.parseDouble(s);
            if (d < 0) {
                System.out.println("Ошибка. Отрицательное значение: Поправка на сопротивление соединительных проводов, Ом");
                return false;
            } else if (d > 100) {
                System.out.println("Ошибка. Значение превышает максимум: Поправка на сопротивление соединительных проводов, Ом");
                return false;
            } else {return true;}
        }
    }

    public boolean check_C_DIFF() {
        String s = C_DIFF.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Поправка на ёмкость соединительных проводов, нФ");
            return false;
        }
        if (!TextValidator.isDouble(s)) {
            System.out.println("Неверный формат: Поправка на ёмкость соединительных проводов, нФ");
            return false;
        } else {
            Double d = Double.parseDouble(s);
            if (d < 0) {
                System.out.println("Ошибка. Отрицательное значение: Поправка на ёмкость соединительных проводов, нФ");
                return false;
            } else if (d > 100) {
                System.out.println("Ошибка. Значение превышает максимум: Поправка на ёмкость соединительных проводов, нФ");
                return false;
            } else {return true;}
        }
    }

    public boolean check_tstR_N() {
        String s = tstR_N.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Проверка целостности R нормы, Ом");
            return false;
        }
        if (!TextValidator.isDouble(s)) {
            System.out.println("Неверный формат: Проверка целостности R нормы, Ом");
            return false;
        } else {
            Double d = Double.parseDouble(s);
            if (d < 100E-3) {
                System.out.println("Ошибка. Значение меньше минимального: Проверка целостности R нормы, Ом");
                return false;
            } else if (d > 100E+6) {
                System.out.println("Ошибка. Значение превышает максимум: Проверка целостности R нормы, Ом");
                return false;
            } else {return true;}
        }
    }

    public boolean check_tstKZ_N() {
        String s = tstKZ_N.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Проверка на КЗ R нормы, Ом");
            return false;
        }
        if (!TextValidator.isDouble(s)) {
            System.out.println("Неверный формат: Проверка на КЗ R нормы, Ом");
            return false;
        } else {
            Double d = Double.parseDouble(s);
            if (d < 100E-3) {
                System.out.println("Ошибка. Значение меньше минимального: Проверка на КЗ R нормы, Ом");
                return false;
            } else if (d > 100E+6) {
                System.out.println("Ошибка. Значение превышает максимум: Проверка на КЗ R нормы, Ом");
                return false;
            } else {return true;}
        }
    }

    public boolean check_tstRIZ_N() {
        String s = tstRIZ_N.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Проверка сопротивления изоляции R нормы, МОм");
            return false;
        }
        if (!TextValidator.isDouble(s)) {
            System.out.println("Неверный формат: Проверка сопротивления изоляции R нормы, МОм");
            return false;
        } else {
            Double d = Double.parseDouble(s);
            if (d < 0.1) {
                System.out.println("Ошибка. Значение меньше минимального: Проверка сопротивления изоляции R нормы, МОм");
                return false;
            } else if (d > 1000) {
                System.out.println("Ошибка. Значение превышает максимум: Проверка сопротивления изоляции R нормы, МОм");
                return false;
            } else {return true;}
        }
    }

    public boolean check_PRIZ_Time() {
        String s = PRIZ_Time.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Проверка прочности изоляции Время проверки, с");
            return false;
        }
        if (!TextValidator.isDouble(s)) {
            System.out.println("Неверный формат: Проверка прочности изоляции Время проверки, с");
            return false;
        } else {
            Double d = Double.parseDouble(s);
            if (d < 1) {
                System.out.println("Ошибка. Значение меньше минимального: Проверка прочности изоляции Время проверки, с");
                return false;
            } else if (d > 600) {
                System.out.println("Ошибка. Значение превышает максимум: Проверка прочности изоляции Время проверки, с");
                return false;
            } else {return true;}
        }
    }

    public boolean check_R_Delay() {
        String s = R_Delay.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Омметр. Задержка перед измерением, с");
            return false;
        }
        if (!TextValidator.isDouble(s)) {
            System.out.println("Неверный формат: Омметр. Задержка перед измерением, с");
            return false;
        } else {
            Double d = Double.parseDouble(s);
            if (d > 60) {
                System.out.println("Ошибка. Значение превышает максимум: Омметр. Задержка перед измерением, с");
                return false;
            } else {return true;}
        }
    }

    public boolean check_RIZ_U() {
        String s = RIZ_U.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Мегометр Напряжение измерения, В");
            return false;
        }
        if (!TextValidator.isInt(s)) {
            System.out.println("Неверный формат: Мегометр Напряжение измерения, В");
            return false;
        } else {
            int d = Integer.parseInt(s);
            if (d < 10) {
                System.out.println("Ошибка. Значение меньше минимального: Мегометр Напряжение измерения, В");
                return false;
            } else if (d > 500) {
                System.out.println("Ошибка. Значение превышает максимум: Мегометр Напряжение измерения, В");
                return false;
            } else {return true;}
        }
    }

    public boolean check_RIZ_Rize() {
        String s = RIZ_Rize.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Мегометр Нарастание напряжения, с");
            return false;
        }
        if (!TextValidator.isDouble(s)) {
            System.out.println("Неверный формат: Мегометр Нарастание напряжения, с");
            return false;
        } else {
            double d = Double.parseDouble(s);
            if (d < 0) {
                System.out.println("Ошибка. Значение меньше минимального: Мегометр Нарастание напряжения, с");
                return false;
            } else if (d > 1.2) {
                System.out.println("Ошибка. Значение превышает максимум: Мегометр Нарастание напряжения, с");
                return false;
            } else {return true;}
        }
    }

    public boolean check_RIZ_Delay() {
        String s = RIZ_Delay.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Мегометр Задержка перед измерением, с");
            return false;
        }
        if (!TextValidator.isDouble(s)) {
            System.out.println("Неверный формат: Мегометр Задержка перед измерением, с");
            return false;
        } else {
            double d = Double.parseDouble(s);
           if (d > 60) {
                System.out.println("Ошибка. Значение превышает максимум: Мегометр Задержка перед измерением, с");
                return false;
            } else {return true;}
        }
    }

    public boolean check_PRIZ_U() {
        String s = PRIZ_U.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Прочность изоляции Напряжение проверки, В");
            return false;
        }
        if (!TextValidator.isInt(s)) {
            System.out.println("Неверный формат: Прочность изоляции Напряжение проверки, В");
            return false;
        } else {
            int d = Integer.parseInt(s);
            if (d < 10) {
                System.out.println("Ошибка. Значение меньше минимального: Прочность изоляции Напряжение проверки, В");
                return false;
            } else if (d > 500) {
                System.out.println("Ошибка. Значение превышает максимум: Прочность изоляции Напряжение проверки, В");
                return false;
            } else {return true;}
        }
    }

    public boolean check_PRIZ_RIZE() {
        String s = PRIZ_RIZE.getText();
        if (s.equals("")) {
            System.out.println("Не указано: Прочность изоляции Нарастание напряжения, с");
            return false;
        }
        if (!TextValidator.isDouble(s)) {
            System.out.println("Неверный формат: Прочность изоляции Нарастание напряжения, с");
            return false;
        } else {
            Double d = Double.parseDouble(s);

            if (d < 0.1) {
                System.out.println("Ошибка. Значение меньше минимального: Прочность изоляции Нарастание напряжения, с");
                return false;
            } else if (d > 1.2) {
                System.out.println("Ошибка. Значение превышает максимум: Прочность изоляции Нарастание напряжения, с");
                return false;
            } else {return true;}
        }
    }

    public Double getR_DIFF() {
        return Double.parseDouble(R_DIFF.getText());
    }

    public void setR_DIFF(Double r_DIFF) {
        R_DIFF.setText(String.valueOf(r_DIFF));
    }

    public Double getC_DIFF() {
        return Double.parseDouble(C_DIFF.getText())*1E-9;
    }

    public void setC_DIFF(Double c_DIFF) {
        C_DIFF.setText(String.valueOf(c_DIFF*1E+9));
    }

    public int getASKM4WIRE() {
        if (ASKM4WIRE.isSelected()){
            return 1;
        } else return 0;
    }

    public void setASKM4WIRE(int ASKM4WIRE) {
        if (ASKM4WIRE == 1) {
            this.ASKM4WIRE.selectedProperty().setValue(true);
        } else this.ASKM4WIRE.selectedProperty().setValue(false);
    }

    public int getTEST_R() {
        if (TEST_R.isSelected()){
            return 1;
        } else return 0;
    }

    public void setTEST_R(int TEST_R) {
        if (TEST_R == 1) {
            this.TEST_R.selectedProperty().setValue(true);
        } else this.TEST_R.selectedProperty().setValue(false);
    }

    public int getTEST_KZ() {
        if (TEST_KZ.isSelected()){
            return 1;
        } else return 0;
    }

    public void setTEST_KZ(int TEST_KZ) {
        if (TEST_KZ == 1) {
            this.TEST_KZ.selectedProperty().setValue(true);
        } else this.TEST_KZ.selectedProperty().setValue(false);
    }

    public int getTEST_RIZ() {
        if (TEST_RIZ.isSelected()){
            return 1;
        } else return 0;
    }

    public void setTEST_RIZ(int TEST_RIZ) {
        if (TEST_RIZ == 1) {
            this.TEST_RIZ.selectedProperty().setValue(true);
        } else this.TEST_RIZ.selectedProperty().setValue(false);
    }

    public int getTEST_PRIZ() {
        if (TEST_PRIZ.isSelected()){
            return 1;
        } else return 0;
    }

    public void setTEST_PRIZ(int TEST_PRIZ) {
        if (TEST_PRIZ == 1) {
            this.TEST_PRIZ.selectedProperty().setValue(true);
        } else this.TEST_PRIZ.selectedProperty().setValue(false);
    }

    public int getR_IZM() {
        if (R_IZM.isSelected()){
            return 1;
        } else return 0;
    }

    public void setR_IZM(int R_IZM) {
        if (R_IZM == 1) {
            this.R_IZM.selectedProperty().setValue(true);
        } else this.R_IZM.selectedProperty().setValue(false);
    }

    public int getKZ_IZM() {
        if (KZ_IZM.isSelected()){
            return 1;
        } else return 0;
    }

    public void setRIZ_IZM(int RIZ_IZM) {
        if (RIZ_IZM == 1) {
            this.RIZ_IZM.selectedProperty().setValue(true);
        } else this.RIZ_IZM.selectedProperty().setValue(false);
    }

    public int getRIZ_IZM() {
        if (RIZ_IZM.isSelected()){
            return 1;
        } else return 0;
    }

    public void setKZ_IZM(int KZ_IZM) {
        if (KZ_IZM == 1) {
            this.KZ_IZM.selectedProperty().setValue(true);
        } else this.KZ_IZM.selectedProperty().setValue(false);
    }

    public Double gettstR_N() {
        return Double.parseDouble(tstR_N.getText());
    }

    public void settstR_N(Double tstR_N) {
        this.tstR_N.setText(String.valueOf(tstR_N));
    }

    public Double gettstKZ_N() {
        return Double.parseDouble(tstKZ_N.getText());
    }

    public void settstKZ_N(Double tstKZ_N) {
        this.tstKZ_N.setText(String.valueOf(tstKZ_N));
    }

    public Double gettstRIZ_N() {
        return Double.parseDouble(tstRIZ_N.getText());
    }

    public void settstRIZ_N(Double tstRIZ_N) {
        this.tstRIZ_N.setText(String.valueOf(tstRIZ_N));
    }

    public Double getPRIZ_TIME() {
        return Double.parseDouble(PRIZ_Time.getText());
    }

    public void setPRIZ_Time(Double PRIZ_Time) {
        this.PRIZ_Time.setText(String.valueOf(PRIZ_Time));
    }

    public int getArp_MeasMode() {
        String s = Arp_MeasMode.getValue();
        switch (s) {
            case "Омметр":
                return 5;
            case "Мегомметр":
                return 9;
            case "Измерение емкости":
                return 7;
                default:
                    return 5;
        }
    }

    public void setArp_MeasMode(int arp_MeasMode) {
        switch (arp_MeasMode) {
            case 5:
               this.Arp_MeasMode.setValue("Омметр");
               break;
            case 9:
               this.Arp_MeasMode.setValue("Мегомметр");
               break;
            case 7:
                this.Arp_MeasMode.setValue("Измерение емкости");
                break;
        }
    }

    public double getArp_Range() {
         if (Arp_Range.getValue()==null)
             return 0;
        String s = Arp_Range.getValue();

        switch (s) {
            case ("1 нФ"):
                return 1E-9;
            case ("10 нф"):
                return 1E-8;
            case ("100 нФ"):
                return 1E-7;
            case ("1 мкФ"):
                return 1E-6;
            case ("Автовыбор"):
                return 0;
            case ("100 Ом"):
                return 100;
            case ("10 Ом"):
                 return 10;
            case ("1 кОм"):
                return 1000;
            case ("10 кОм"):
                return 10000;
            case ("100 кОм"):
                return 100000;
            case ("1 МОм"):
                return 1000000;
            case ("10 МОм"):
                return 10000000;
            case ("100 МОм"):
                return 100000000;
        }
        return 0;
    }

    public void setArp_Range(double arp_Range) {
        if (arp_Range == 1E-9) {
            this.Arp_Range.setValue("1 нФ");
            return;
        }
        if (arp_Range == 1E-8) {
            this.Arp_Range.setValue("10 нФ");
            return;
        }
        if (arp_Range == 1E-7) {
            this.Arp_Range.setValue("100 нФ");
            return;
        }
        if (arp_Range == 1E-6) {
            this.Arp_Range.setValue("1 мкФ");
            return;
        }
        if (arp_Range == 10) {
            this.Arp_Range.setValue("10 Ом");
            return;
        }
        if (arp_Range == 100) {
            this.Arp_Range.setValue("100 Ом");
            return;
        }
        if (arp_Range == 1000) {
            this.Arp_Range.setValue("1 кОм");
            return;
        }
        if (arp_Range == 10000) {
            this.Arp_Range.setValue("10 кОм");
            return;
        }
        if (arp_Range == 100000) {
            this.Arp_Range.setValue("100 кОм");
            return;
        }
        if (arp_Range == 1000000) {
            this.Arp_Range.setValue("1 МОм");
            return;
        }
        if (arp_Range == 10000000) {
            this.Arp_Range.setValue("10 МОм");
            return;
        }
        if (arp_Range == 100000000) {
            this.Arp_Range.setValue("100 МОм");
            return;
        }
        if (arp_Range == 0) {
            this.Arp_Range.setValue("Автовыбор");
            return;
        }
    }

    public int getArp_Connect() {
        String s = Arp_Connect.getValue();
        switch (s) {
            case ("Точки цепи (относительно опорной точки)"):
                return 1;
            case ("Цепи относительно остальных"):
                return 2;
            case ("Между всеми цепями попарно"):
                return 3;
        }
        return 1;
    }

    public void setArp_Connect(int arp_Connect) {
        switch (arp_Connect) {
            case (1):
                Arp_Connect.setValue("Точки цепи (относительно опорной точки)");
                break;
            case (2):
                Arp_Connect.setValue("Цепи относительно остальных");
                break;
            case (3):
                Arp_Connect.setValue("Между всеми цепями попарно");
                break;
        }
    }

    public double getR_Delay() {
        return Double.parseDouble(R_Delay.getText());
    }

    public void setR_Delay(double r_Delay) {
        R_Delay.setText(String.valueOf(r_Delay));
    }

    public double getR_Aper() {
        String s = R_Aper.getValue();
        switch (s) {
            case ("2 мс"):
                return 0.002;
            case ("20 мс"):
                return 0.02;
            case ("200 мс"):
                return 0.2;

        }
        return 0.002;
    }

    public void setR_Aper(double r_Aper) {
        if (r_Aper == 0.002) {
            R_Aper.setValue("2 мс");
        }
        if (r_Aper == 0.02) {
            R_Aper.setValue("20 мс");
        }
        if (r_Aper == 0.2) {
            R_Aper.setValue("200 мс");
        }
    }

    public int getR_MaxRange() {
        String s = R_MaxRange.getValue();
        switch (s){
        case ("10 Ом"):
         return 10;
        case ("100 Ом"):
        return 100;
        case ("1 кОм"):
        return 1000;
        case ("10 кОм"):
        return 10000;
        case ("100 кОм"):
        return 100000;
        case ("1 МОм"):
        return 1000000;
        case ("10 МОм"):
        return 10000000;
        case ("100 МОм"):
        return 100000000;
    }
        return 0;
    }

    public void setR_MaxRange(int r_MaxRange) {
        if (r_MaxRange == 10) {
            this.R_MaxRange.setValue("10 Ом");
            return;
        }
        if (r_MaxRange == 100) {
            this.R_MaxRange.setValue("100 Ом");
            return;
        }
        if (r_MaxRange == 1000) {
            this.R_MaxRange.setValue("1 кОм");
            return;
        }
        if (r_MaxRange == 10000) {
            this.R_MaxRange.setValue("10 кОм");
            return;
        }
        if (r_MaxRange == 100000) {
            this.R_MaxRange.setValue("100 кОм");
            return;
        }
        if (r_MaxRange == 1000000) {
            this.R_MaxRange.setValue("1 МОм");
            return;
        }
        if (r_MaxRange == 10000000) {
            this.R_MaxRange.setValue("10 МОм");
            return;
        }
        if (r_MaxRange == 100000000) {
            this.Arp_Range.setValue("100 МОм");
            return;
        }
    }

    public int getRIZ_U() {
        return Integer.parseInt(RIZ_U.getText());
    }

    public void setRIZ_U(int RIZ_U) {
        this.RIZ_U.setText(String.valueOf(RIZ_U));
    }

    public double getRIZ_Rize() {
        return Double.parseDouble(RIZ_Rize.getText());
    }

    public void setRIZ_Rize(double RIZ_Rize) {
        this.RIZ_Rize.setText(String.valueOf(RIZ_Rize));
    }

    public double getRIZ_Delay() {
        return Double.parseDouble(RIZ_Delay.getText());
    }

    public void setRIZ_Delay(double RIZ_Delay) {
        this.RIZ_Delay.setText(String.valueOf(RIZ_Delay));
    }

    public double getRIZ_Aper() {
        String s = RIZ_Aper.getValue();
        switch (s) {
            case ("2 мс"):
                return 0.002;
            case ("20 мс"):
                return 0.02;
            case ("200 мс"):
                return 0.2;

        }
        return 0.002;
    }

    public void setRIZ_Aper(double RIZ_Aper) {
        if (RIZ_Aper == 0.002) {
            this.RIZ_Aper.setValue("2 мс");
        }
        if (RIZ_Aper == 0.02) {
            this.RIZ_Aper.setValue("20 мс");
        }
        if (RIZ_Aper == 0.2) {
            this.RIZ_Aper.setValue("200 мс");
        }
    }

    public int getPRIZ_U() {
        return Integer.parseInt(PRIZ_U.getText());
    }

    public void setPRIZ_U(int PRIZ_U) {
        this.PRIZ_U.setText(String.valueOf(PRIZ_U));
    }

    private int getPRIZ_AcDc() {
        if (PRIZ_DC.isSelected()) {
            return 0;
        } else {
            return 1;}
    }

    private void setPRIZ_AcDc(int PRIZ_AcDc) {
        if (PRIZ_AcDc == 0) {
            PRIZ_DC.setSelected(true);
            PRIZ_AC.setSelected(false);
        } else {
            PRIZ_DC.setSelected(false);
            PRIZ_AC.setSelected(true);
        }
    }

    public double getPRIZ_RIZE() {
        return Double.parseDouble(PRIZ_RIZE.getText());
    }

    public void setPRIZ_RIZE(double PRIZ_RIZE) {
        this.PRIZ_RIZE.setText(String.valueOf(PRIZ_RIZE));
    }

    public double getC_MaxRange() {
        String s = C_MaxRange.getValue();
        switch (s) {
            case ("1 нФ"):
                return 1E-9;
            case ("10 нф"):
                return 1E-8;
            case ("100 нФ"):
                return 1E-7;
            case ("1 мкФ"):
                return 1E-6;
        }
        return 1E-9;
    }

    public void setC_MaxRange(double c_MaxRange) {
        if (c_MaxRange == 1E-9) {
            this.C_MaxRange.setValue("1 нФ");
            return;
        }
        if (c_MaxRange == 1E-8) {
            this.C_MaxRange.setValue("10 нФ");
            return;
        }
        if (c_MaxRange == 1E-7) {
            this.C_MaxRange.setValue("100 нФ");
            return;
        }
        if (c_MaxRange == 1E-6) {
            this.C_MaxRange.setValue("1 мкФ");
            return;
        }
    }

    private void saveToOk(OkGenerator ok) {
        ok.ASKM4WIRE=getASKM4WIRE();
        ok.R_DIFF = getR_DIFF();
        ok.C_DIFF = getC_DIFF();
        ok.tstR_N = gettstR_N();
        ok.tstKZ_N = gettstKZ_N();
        ok.tstRIZ_N = gettstRIZ_N();
        ok.R_Delay = getR_Delay();
        ok.R_Aper = getR_Aper();
        ok.R_MaxRange = getR_MaxRange();
        ok.C_MaxRange = getC_MaxRange();
        ok.RIZ_U = getRIZ_U();
        ok.RIZ_Rize = getRIZ_Rize();
        ok.RIZ_Delay = getRIZ_Delay();
        ok.RIZ_Aper = getRIZ_Aper();
        ok.Arp_MeasMode = getArp_MeasMode();
        ok.Arp_Connect = getArp_Connect();
        ok.Arp_Range = getArp_Range();
        ok.PRIZ_U = getPRIZ_U();
        ok.PRIZ_AcDc = getPRIZ_AcDc();
        ok.PRIZ_Rize = getPRIZ_RIZE();
        ok.PRIZ_Time = getPRIZ_TIME();
        ok.TEST_R = getTEST_R();
        ok.TEST_KZ = getTEST_KZ();
        ok.TEST_RIZ = getTEST_RIZ();
        ok.TEST_PRIZ = getTEST_PRIZ();
        ok.RIZ_IZM = getRIZ_IZM();
        ok.R_IZM = getR_IZM();
        ok.KZ_IZM = getKZ_IZM();
    }

    private void loadOfOk(OkGenerator ok) {
       setASKM4WIRE(ok.ASKM4WIRE);
       setR_DIFF(ok.R_DIFF);
        setC_DIFF(ok.C_DIFF);
        settstR_N(ok.tstR_N);
        settstKZ_N(ok.tstKZ_N);
        settstRIZ_N(ok.tstRIZ_N);
        setR_Delay(ok.R_Delay);
        setR_Aper(ok.R_Aper );
        setR_MaxRange(ok.R_MaxRange);
        setC_MaxRange(ok.C_MaxRange);
        setRIZ_U(ok.RIZ_U);
        setRIZ_Rize(ok.RIZ_Rize );
        setRIZ_Delay(ok.RIZ_Delay);
        setRIZ_Aper(ok.RIZ_Aper);
        setArp_MeasMode(ok.Arp_MeasMode);
        setArp_Connect(ok.Arp_Connect);
        setArp_Range(ok.Arp_Range);
        setPRIZ_U(ok.PRIZ_U);
        setPRIZ_AcDc(ok.PRIZ_AcDc);
        setPRIZ_RIZE(ok.PRIZ_Rize);
        setPRIZ_Time(ok.PRIZ_Time);
        setTEST_R(ok.TEST_R);
        setTEST_KZ(ok.TEST_KZ);
        setTEST_RIZ(ok.TEST_RIZ);
        setTEST_PRIZ(ok.TEST_PRIZ);
        setTEST_RIZ(ok.TEST_RIZ);
        setRIZ_IZM(ok.RIZ_IZM);
        setR_IZM(ok.R_IZM);
        setKZ_IZM(ok.KZ_IZM);
    }
}
