package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AdapterContactorChooser {
    ArrayList<Adapter> adapters;
    ArrayList<Contactor> contactors;
    ControlObject ok;
    ArrayList<Connection> data;
    boolean result;
    String resultInfo;
    HashMap<Adapter, Integer> usedAdapters;
    HashMap<Contactor, Integer> usedContactors;


    public AdapterContactorChooser(ArrayList<Adapter> adapters, ArrayList<Contactor> contactors, ControlObject ok) {
        this.adapters = adapters;
        this.contactors = contactors;
        this.ok = ok;

    }

    public void findAdapterWithAdapter(){
        data = new ArrayList<Connection>();
        result = true;
        resultInfo = "";
        usedAdapters = new HashMap<>();
        usedContactors = new HashMap<>();
        ArrayList<Connector> inConnectors = (ArrayList<Connector>) ok.inputConnectors.clone();
        //ArrayList<Connector> outConnectors = (ArrayList<Connector>) ok.outputConnectors.clone();
        inConnectors.addAll(ok.outputConnectors);
        findAdapters(inConnectors);
        //findAdapters(outConnectors);
    }

    public void findAdapterWithContactor() {
        data = new ArrayList<Connection>();
        result = true;
        resultInfo = "";
        usedAdapters = new HashMap<>();
        usedContactors = new HashMap<>();
        // Определяем приоритетные головы для подключения адаптера, те у которых больше контактов
        ArrayList<Connector> inConnectors;
        ArrayList<Connector> outConnectors;
        int inCount = 0;
        int outCount = 0;
        for (Connector connector : ok.inputConnectors) {
            inCount += connector.getConnectorType().getContactsCount();
        }
        for (Connector connector : ok.outputConnectors) {
            outCount += connector.getConnectorType().getContactsCount();
        }
        if (inCount < outCount) {
            inConnectors = (ArrayList<Connector>) ok.outputConnectors.clone();
            outConnectors =(ArrayList<Connector>) ok.inputConnectors.clone();
        } else {
            inConnectors = (ArrayList<Connector>)ok.inputConnectors.clone();
            outConnectors =(ArrayList<Connector>) ok.outputConnectors.clone();
        }
        findAdapters(inConnectors);
        findContactors(outConnectors);

    }

    private void findContactors(ArrayList<Connector> outConnectors) {
        //Подбираем замыкатели
        ArrayList<Connection> tempData = new ArrayList<>();
        ArrayList<Connector> badConnectors = new ArrayList<>();
        for (Connector okConnector : outConnectors) {
            ConnectorType findAnswerConnector = okConnector.getConnectorType().answerConnectorType;
            boolean tres = false;
            for (Contactor contactor : contactors) {
                if (usedContactors.containsKey(contactor)&&usedContactors.get(contactor)>=contactor.count){
                    continue; // такие замыкатели использованы
                }
                if (contactor.connector.getConnectorType().equals(findAnswerConnector)) {
                    if (usedContactors.containsKey(contactor)) {
                        usedContactors.replace(contactor, usedContactors.get(contactor) + 1);
                    } else {
                        usedContactors.put(contactor, 1);
                    }
                    tempData.add(new Connection(ok,1,okConnector,contactor,usedContactors.get(contactor),contactor.connector));
                    tres = true;
                    break;
                }
            }
            if (tres) {
                tres = false;
            } else {
                badConnectors.add(okConnector);
            }
        }

        if (badConnectors.size() != 0) {
            result = false;
            StringBuilder sb = new StringBuilder();
            for (Connector connector : badConnectors) {
                sb.append("Не найден замыкатель для разъема " + connector.name + " " + connector.getConnectorType().type+"\n\r");
            }
            resultInfo += sb.toString();
            return;
        } else {
            resultInfo += "Замыкатели подобраны\n\r";
            data.addAll(tempData);
        }
    }

    private void findAdapters(ArrayList<Connector> inConnectors) {
        //Создаем списки подходящих разъемов адаптеров для каждого входного (основного разъема)
        if (inConnectors.size() == 0) {
            resultInfo += "Адаптеры подобраны\n\r";
            return;
        }
        HashMap<Connector, ArrayList<ConnectorAdapter>> rateList = new HashMap<>();
        for (Connector okConnector : inConnectors) {
            rateList.put(okConnector, new ArrayList<>());
            ConnectorType findAnswerConnector = okConnector.getConnectorType();
            for (Adapter adapter : adapters) {
                if (usedAdapters.containsKey(adapter)&&usedAdapters.get(adapter)>=adapter.getCount()){
                    continue; //Если все такие адаптеры уже использованы то выходим из шага цикла
                }
                ArrayList<Connector> con = adapter.getAnswerConnectors(findAnswerConnector);
                if (con.size() != 0) {
                    for (Connector c : con) {
                        rateList.get(okConnector).add(new ConnectorAdapter(c, adapter));
                    }
                }
            }
        }
        //Проверка что на каждый разъем найден адаптер
        for (HashMap.Entry<Connector, ArrayList<ConnectorAdapter>> entry : rateList.entrySet()) {
            if (entry.getValue().size() == 0) {
                result = false;
                resultInfo = "Адаптер не найден для разъема " + entry.getKey().name + " " + entry.getKey().connectorType.type+"\n\r";
                return;
            }
        }

        //Прогон
        rateAdapter(rateList, inConnectors);

    }

    private void rateAdapter(HashMap<Connector, ArrayList<ConnectorAdapter>> rateList, ArrayList<Connector> inConnectors) {

        HashMap<Adapter, Integer> adapterRating = new HashMap<>();

        for (HashMap.Entry<Connector, ArrayList<ConnectorAdapter>> entry : rateList.entrySet()) {
            for (ConnectorAdapter connectorAdapter : entry.getValue()) {
                if (!adapterRating.containsKey(connectorAdapter.adapter)) {
                    adapterRating.put(connectorAdapter.adapter, 0);
                }
            }
        }

        int maxRate = 0;
        Adapter res = null;
        for (HashMap.Entry<Adapter, Integer> adapterRate : adapterRating.entrySet()) {
            ArrayList<ConnectorAdapter> stopList = new ArrayList<>();
            for (HashMap.Entry<Connector, ArrayList<ConnectorAdapter>> entry : rateList.entrySet()) {
                for (ConnectorAdapter connectorAdapter : entry.getValue()) {
                    if (connectorAdapter.adapter.equals(adapterRate.getKey()) && !stopList.contains(connectorAdapter)) {
                        adapterRate.setValue(adapterRate.getValue() + 1);
                        if (maxRate < adapterRate.getValue()) {
                            maxRate = adapterRate.getValue();
                            res = adapterRate.getKey();
                        }
                        stopList.add(connectorAdapter);
                        break;
                    }
                }
            }
        }

        if (usedAdapters.containsKey(res)) {
            usedAdapters.replace(res, usedAdapters.get(res) + 1);
        } else {
            usedAdapters.put(res, 1);
        }

        ArrayList<ConnectorAdapter> conAdStopList = new ArrayList<>();
        for (Iterator<HashMap.Entry<Connector, ArrayList<ConnectorAdapter>>> it = rateList.entrySet().iterator(); it.hasNext(); ) {
            HashMap.Entry<Connector, ArrayList<ConnectorAdapter>> entry = it.next();
            for (ConnectorAdapter connectorAdapter : entry.getValue()) {
                if (connectorAdapter.adapter.equals(res) && !conAdStopList.contains(connectorAdapter)) {
                    conAdStopList.add(connectorAdapter);
                    data.add(new Connection(ok,1,entry.getKey(),connectorAdapter.adapter,usedAdapters.get(res),connectorAdapter.connector));
                    inConnectors.remove(entry.getKey());
                    it.remove();
                    break;
                }
            }
        }



        findAdapters(inConnectors);

    }



}
