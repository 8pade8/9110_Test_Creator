package sample;

import java.io.FileWriter;
import java.io.Serializable;

public class OkGenerator implements Serializable {
    StringBuilder sb;
    String directory;

    String OK_NAME = "1";
    String CONN_CFG_FILE = "";
    String CHAINFILE = "1.cha";
    String CONNECTFILE = "1.con";
    String MEAS_CHAINFILE = "";
    String R_CHAINFILE = "1.cha";
    String KZ_CHAINFILE = "1off.cha";
    String RIZ_CHAINFILE = "1off.cha";
    String PRIZ_CHAINFILE = "1off.cha";
    String DIOD_CHAINFILE = "";
    int ASKM4WIRE = 0;
    double R_DIFF = 0;
    double C_DIFF = 0;
    double tstR_N = 2;
    double tstKZ_N = 100;
    double tstRIZ_N = 20;
    double R_Delay = -1;
    double R_Aper = 0.02;
    int R_MaxRange = 10;
    double C_MaxRange = 1e-06;
    int RIZ_U = 100;
    double RIZ_Rize = 0.1;
    double RIZ_Delay = -1;
    double RIZ_Aper = 0.02;
    double IZM_Time = 0;
    double IZM_WithTime = 0;
    int Arp_MeasMode = 9;
    int Arp_MeasMode_2 = 0;
    int Arp_Connect = 3;
    double Arp_Range = 1000;
    int Arp_RangeR4 = 0;
    int Arp_RangeC = 0;
    int Arp_ChkpDo = 0;
    int Arp_ChkpWhat = 0;
    int Arp_ChkpOnlyErr = 0;
    String Arp_LogChainMsg = "";
    String EtalonFileName = "";
    double Arp_ChkpMIN = 9;
    double Arp_ChkpMAX = 10;
    double Arp_ChkpMIN_REV = 0.1;
    double Arp_ChkpMAX_REV = 5;
    double Arp_ChkpRet = 0;
    double Arp_ChkpProc = 20;
    double Arp_ChkpMDelta = 5;
    double Arp_ChkpPDelta = 5;
    double Arp_ChkpDeltaP = 0;
    int PRIZ_U = 250;
    int PRIZ_AcDc = 0;
    double PRIZ_Time = 1;
    double PRIZ_Rize = 0.2;
    int TEST_R = 1;
    int TEST_KZ = 1;
    int TEST_RIZ = 1;
    int TEST_PRIZ = 1;
    int POLAR_R = 0;
    int POLAR_KZ = 0;
    int POLAR_RIZ = 0;
    int POLAR_PRIZ = 0;
    int TEST_DIOD = 0;
    double TEST_DIOD_I = 10;
    double TEST_DIOD_U = 2.5;
    double TEST_DIOD_UREV = 2.5;
    int RIZ_IZM = 1;
    int KZ_IZM = 1;
    int R_IZM = 1;
    int RIZ_ALLPOINTS = 0;
    int R_ALLPOINTS = 0;
    int ITOGLOG = 0;
    int ITOGLOG_SN = 0;
    String ITOGLOG_TXT = "";
    int PRIZ_DOFAST = 1;
    int RIZ_DOFAST = 1;
    int KZ_DOFAST = 1;
    int RIZ_MAXCHAINS = 0;
    int PANEL_WIDTH = 773;
    int PANEL_HEIGHT = 612;
    int PANEL_TOP = 185;
    int PANEL_LEFT = 414;

    public void setOK_NAME(String OK_NAME) {
        this.OK_NAME = OK_NAME;
    }

    public void setCHAINFILE(String CHAINFILE) {
        this.CHAINFILE = CHAINFILE;
    }

    public void setCONNECTFILE(String CONNECTFILE) {
        this.CONNECTFILE = CONNECTFILE;
    }

    public void setR_CHAINFILE(String r_CHAINFILE) {
        R_CHAINFILE = r_CHAINFILE;
    }

    public void setKZ_CHAINFILE(String KZ_CHAINFILE) {
        this.KZ_CHAINFILE = KZ_CHAINFILE;
    }

    public void setRIZ_CHAINFILE(String RIZ_CHAINFILE) {
        this.RIZ_CHAINFILE = RIZ_CHAINFILE;
    }

    public void setPRIZ_CHAINFILE(String PRIZ_CHAINFILE) {
        this.PRIZ_CHAINFILE = PRIZ_CHAINFILE;
    }

    private void generateString() {
        sb = new StringBuilder();
        String s = "\r\n";
        sb.append("[OK]" + s);
        sb.append("OK_NAME = \"").append(OK_NAME).append("\"" + s);
        sb.append("CONN_CFG_FILE = \"").append(CONN_CFG_FILE).append("\"" + s);
        sb.append("CHAINFILE = \"").append(CHAINFILE).append("\"" + s);
        sb.append("CONNECTFILE = \"").append(CONNECTFILE).append("\"" + s);
        sb.append("MEAS_CHAINFILE = \"").append(MEAS_CHAINFILE).append("\"" + s);
        sb.append("R_CHAINFILE = \"").append(R_CHAINFILE).append("\"" + s);
        sb.append("KZ_CHAINFILE = \"").append(KZ_CHAINFILE).append("\"" + s);
        sb.append("RIZ_CHAINFILE = \"").append(RIZ_CHAINFILE).append("\"" + s);
        sb.append("PRIZ_CHAINFILE = \"").append(PRIZ_CHAINFILE).append("\"" + s);
        sb.append("DIOD_CHAINFILE = \"").append(DIOD_CHAINFILE).append("\"" + s);
        sb.append("ASKM4WIRE = ").append(ASKM4WIRE).append(s);
        sb.append("R_DIFF = ").append(R_DIFF).append(s);
        sb.append("C_DIFF = ").append(C_DIFF).append(s);
        sb.append("tstR_N = ").append(tstR_N).append(s);
        sb.append("tstKZ_N = ").append(tstKZ_N).append(s);
        sb.append("tstRIZ_N = ").append(tstRIZ_N).append(s);
        sb.append("R_Delay = ").append(R_Delay).append(s);
        sb.append("R_Aper = ").append(R_Aper).append(s);
        sb.append("R_MaxRange = ").append(R_MaxRange).append(s);
        sb.append("C_MaxRange = ").append(C_MaxRange).append(s);
        sb.append("RIZ_U = ").append(RIZ_U).append(s);
        sb.append("RIZ_Rize = ").append(RIZ_Rize).append(s);
        sb.append("RIZ_Delay = ").append(RIZ_Delay).append(s);
        sb.append("RIZ_Aper = ").append(RIZ_Aper).append(s);
        sb.append("IZM_Time = ").append(IZM_Time).append(s);
        sb.append("IZM_WithTime = ").append(IZM_WithTime).append(s);
        sb.append("Arp_MeasMode = ").append(Arp_MeasMode).append(s);
        sb.append("Arp_MeasMode_2 = ").append(Arp_MeasMode_2).append(s);
        sb.append("Arp_Connect = ").append(Arp_Connect).append(s);
        sb.append("Arp_Range = ").append(Arp_Range).append(s);
        sb.append("Arp_RangeR4 = ").append(Arp_RangeR4).append(s);
        sb.append("Arp_RangeC = ").append(Arp_RangeC).append(s);
        sb.append("Arp_ChkpDo = ").append(Arp_ChkpDo).append(s);
        sb.append("Arp_ChkpWhat = \"").append(Arp_ChkpWhat).append("\"" + s);
        sb.append("Arp_ChkpOnlyErr = ").append(Arp_ChkpOnlyErr).append(s);
        sb.append("Arp_LogChainMsg = ").append(Arp_LogChainMsg).append(s);
        sb.append("EtalonFileName = \"").append(EtalonFileName).append("\"" + s);
        sb.append("Arp_ChkpMIN = ").append(Arp_ChkpMIN).append(s);
        sb.append("Arp_ChkpMAX = ").append(Arp_ChkpMAX).append(s);
        sb.append("Arp_ChkpMIN_REV = ").append(Arp_ChkpMIN_REV).append(s);
        sb.append("Arp_ChkpMAX_REV = ").append(Arp_ChkpMAX_REV).append(s);
        sb.append("Arp_ChkpRet = ").append(Arp_ChkpRet).append(s);
        sb.append("Arp_ChkpProc = ").append(Arp_ChkpProc).append(s);
        sb.append("Arp_ChkpMDelta = ").append(Arp_ChkpMDelta).append(s);
        sb.append("Arp_ChkpPDelta = ").append(Arp_ChkpPDelta).append(s);
        sb.append("Arp_ChkpDeltaP = ").append(Arp_ChkpDeltaP).append(s);
        sb.append("PRIZ_U = ").append(PRIZ_U).append(s);
        sb.append("PRIZ_AcDc = ").append(PRIZ_AcDc).append(s);
        sb.append("PRIZ_Time = ").append(PRIZ_Time).append(s);
        sb.append("PRIZ_Rize = ").append(PRIZ_Rize).append(s);
        sb.append("TEST_R = ").append(TEST_R).append(s);
        sb.append("TEST_KZ = ").append(TEST_KZ).append(s);
        sb.append("TEST_RIZ = ").append(TEST_RIZ).append(s);
        sb.append("TEST_PRIZ = ").append(TEST_PRIZ).append(s);
        sb.append("POLAR_R = ").append(POLAR_R).append(s);
        sb.append("POLAR_KZ = ").append(POLAR_KZ).append(s);
        sb.append("POLAR_RIZ = ").append(POLAR_RIZ).append(s);
        sb.append("POLAR_PRIZ = ").append(POLAR_PRIZ).append(s);
        sb.append("TEST_DIOD = ").append(TEST_DIOD).append(s);
        sb.append("TEST_DIOD_I = ").append(TEST_DIOD_I).append(s);
        sb.append("TEST_DIOD_U = ").append(TEST_DIOD_U).append(s);
        sb.append("TEST_DIOD_UREV = ").append(TEST_DIOD_UREV).append(s);
        sb.append("RIZ_IZM = ").append(RIZ_IZM).append(s);
        sb.append("KZ_IZM = ").append(KZ_IZM).append(s);
        sb.append("R_IZM = ").append(R_IZM).append(s);
        sb.append("RIZ_ALLPOINTS = ").append(RIZ_ALLPOINTS).append(s);
        sb.append("R_ALLPOINTS = ").append(R_ALLPOINTS).append(s);
        sb.append("ITOGLOG = ").append(ITOGLOG).append(s);
        sb.append("ITOGLOG_SN = ").append(ITOGLOG_SN).append(s);
        sb.append("ITOGLOG = \"").append(ITOGLOG).append("\"" + s);
        sb.append("PRIZ_DOFAST = ").append(PRIZ_DOFAST).append(s);
        sb.append("RIZ_DOFAST = ").append(RIZ_DOFAST).append(s);
        sb.append("KZ_DOFAST = ").append(KZ_DOFAST).append(s);
        sb.append("RIZ_MAXCHAINS = ").append(RIZ_MAXCHAINS).append(s);
        sb.append("PANEL_WIDTH = ").append(PANEL_WIDTH).append(s);
        sb.append("PANEL_HEIGHT = ").append(PANEL_HEIGHT).append(s);
        sb.append("PANEL_TOP = ").append(PANEL_TOP).append(s);
        sb.append("PANEL_LEFT = ").append(PANEL_LEFT).append(s);
        sb.append(s);
        sb.append("[ARP_PROGRAM]").append(s).append("TYPE_ALL = 0");
    }

    public void writeOkFile(){
        generateString();
        try {
            FileWriter fileWriter = new FileWriter(directory + "\\" + OK_NAME + ".ok");
            fileWriter.write(sb.toString());
            fileWriter.close();
            System.out.println(directory + "\\" + OK_NAME + ".ok"+" Записан.");
        } catch (Exception e) {
            System.out.println("Директория "+directory+" не найдена.");
        }
    }

}
