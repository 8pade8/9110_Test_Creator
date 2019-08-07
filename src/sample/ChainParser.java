package sample;

import java.util.ArrayList;

public class ChainParser {
    public String inputName;
    public String outputName;
    public ArrayList<Integer> in;
    public ArrayList<Integer> out;
    public boolean parralel=false;

    public ChainParser(String s) { //X1:1-100=X2:1-100 ||X1:1=X2:3  || X1:1 = X2:1,2,3

        try{
        String temp[] = s.split("\\s*=\\s*");
        String firstConnector = temp[0];
        String secondConnector = temp[1];
        String temp2[]=firstConnector.split("\\s*:\\s*");
        String temp3[]=secondConnector.split("\\s*:\\s*");
        inputName = temp2[0].trim();
        outputName = temp3[0].trim();
        String inContacts = temp2[1];
        String outContacts = temp3[1];
        in = new ArrayList<>();
        out = new ArrayList<>();
        parseContacts(inContacts,in);
        parseContacts(outContacts,out);
        if (inContacts.contains("-") && outContacts.contains("-")) {
            if (in.size() == out.size()) {
                 parralel = true;

            } else {
                System.out.println("Неверный формат цепей, кол-во контактов одного разъема не соответсвует количеству контактов другого разъема");
            }
        }} catch (Exception e){
            System.out.println("Неверный формат цепей");
        }
    }
    private void parseContacts(String s,ArrayList<Integer> list) {
        try {
            if (s.contains("-")) {
                String temp[] = s.split("\\s*-\\s*");
                int start = Integer.parseInt(temp[0].trim());
                int finish = Integer.parseInt(temp[1].trim());
                for (int i = start; i <= finish; i++) {
                    list.add(i);
                }

            } else if (s.contains(",")) {
                String temp[] = s.split("\\s*,\\s*");
                for (int i = 0; i < temp.length; i++) {
                    list.add(Integer.parseInt(temp[i].trim()));
                }
            } else {
                list.add(Integer.parseInt(s.trim()));
            }
        } catch (Exception e) {
            System.out.println("Ошибка формата цепей! "+s);
        }

    }

    public static void testChains(String s) {
        s = s.trim();
        String str[] = s.split("\\s*;\\s*");
        for (String m : str) {
            ChainParser cp = new ChainParser(m.trim());
        }
    }
}
