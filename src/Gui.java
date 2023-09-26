import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Gui klasė kurioje dirbama su GUI
 */
public class Gui extends JFrame{
    /**
     * Pagrindine GUI panele
     */
    private JPanel panelMain;
    /**
     * Slankiojimui reikalinga panele
     */
    private JScrollPane scrollPane;
    /**
     * Ivedimo mygtukas
     */
    private JButton inputButton;
    /**
     * Rykiavimo mygtukas
     */
    private JButton sortButton;
    /**
     * Teksto laukas
     */
    private JTextArea textArea;
    /**
     * Skaiciavimo mygtukas
     */
    private JButton calculateButton;

    /**
     * Lenteles nr1 virsutine dalis
     */
    final String virsus1 = "Pavardė     Adresas        Laikotarpio    Laikotarpio   Leidinio       Kaina\r\n" +
            "                                           Pradžia         Ilgis     Pavadinimas     Mėnesiui";
    /**
     * Lenteles nr2 virsutine dalis
     */
    final String virsus2 = "Mėn.    Publikacija";
    /**
     * Lenteles nr3 virsutine dalis
     */
    final String virsus3 = "Publikacija       Eurai";
    /**
     * Duomenu failo nuoroda
     */
    static final File CFd = new File("@..\\..\\duomenys.txt");

    /**
     * GUI klases konstruktorius/metodas
     * @param month menesiai pagal paieska
     * @param publication publikacija pagal paieska
     */
    public Gui(int month, String publication){

        String[] profitMonth = new String[100000];
        ArrayList subscribers = new ArrayList();

        ContainerSubscriptions contSubscrip = new ContainerSubscriptions();
        ContainerPublications contPubli = new ContainerPublications();
        ContainerPublications contPubliNew = new ContainerPublications();

        setTitle("Programa");
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,900);
        //setLayout(null);
        textArea.setEditable(false);
        setVisible(true);

        inputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long starTime = System.currentTimeMillis();
                try {
                    Read(CFd,contSubscrip);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                PrintOriginal(contSubscrip);
                if (contSubscrip.Take() <= 0) { System.out.println("Nėra pradinių duomenų"); return; }
                long endTime = System.currentTimeMillis();
                System.out.println("Įvedimo dalies laikas: "+(endTime-starTime));
            }
        });
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long starTime = System.currentTimeMillis();
                contPubli.Sort();
                PrintGrossProfit(contPubli);
                long endTime = System.currentTimeMillis();
                System.out.println("Rikiavimo dalies laikas: "+(endTime-starTime));
            }
        });
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long starTime = System.currentTimeMillis();
                FormSubscriberArray(contSubscrip, publication, month,  subscribers);
                Contraction(contSubscrip, contPubli);
                FormProfitMonth(contPubli,  profitMonth);
                FormBelowAverage(contPubli,  contPubliNew);
                long startTimePrint = System.currentTimeMillis();
                PrintSearchArray(subscribers);
                PrintMaxMonth(profitMonth);
                PrintGrossProfit(contPubli);
                PrintBelowAverage(contPubliNew);
                long endTimePrint = System.currentTimeMillis();
                long endTime = System.currentTimeMillis();
                System.out.println("Skaičiavimo dalies laikas: "+(endTimePrint-startTimePrint));
                System.out.println("Skaičiavimo dalies laikas: "+(endTime-starTime));
            }
        });
    }

    /**
     * Nuskaitymo metodą
     * @param file Failas
     * @param contSubs Prenumeratų konteineris
     * @throws FileNotFoundException Jei neegzistuoja failas ismeta exceptiona
     */
    public void Read(File file, ContainerSubscriptions contSubs) throws FileNotFoundException {
        String line;
        String[] parts;
        String lastName;
        String adress;
        String subscName;
        int periodStart;
        int periodLength;
        double priceMonth;
        Scanner scanner;
        if(file.exists()){
            scanner = new Scanner(file);
            while(scanner.hasNext())
            {
                line = scanner.nextLine();
                parts = line.split(";");
                lastName = parts[0];
                adress = parts[1];
                periodStart = Integer.parseInt(parts[2]);
                periodLength = Integer.parseInt(parts[3]);
                subscName = parts[4];
                priceMonth = Double.parseDouble(parts[5]);
                Subscriptions subs = new Subscriptions();
                subs.Insert(lastName, adress, periodStart, periodLength, subscName, priceMonth);
                contSubs.Insert(subs);
            }
        }
        else textArea.setText("Nėra duomenų");
    }

    /**
     * Sukuria prenumeratorių listą
     * @param contSubs prenumeratų konteineris
     * @param publication publikacija
     * @param month mėnesis
     * @param subscribers prenumeratriai
     */
    public void FormSubscriberArray(ContainerSubscriptions contSubs, String publication, int month, ArrayList subscribers)
    {
        for(int i = 0; i < contSubs.Take(); i++)
        {
            if (contSubs.Take(i).Equals(publication, month))
            {
                subscribers.add(String.format(contSubs.Take(i).TakeLastName()+"   "+
                        contSubs.Take(i).TakeAdress()));
            }
        }
    }
    /**
     * Sukuria konteinerį publikacijų mažiau už vidurkį
     * @param contPubl Publikacijų konteineris
     * @param contPublNew Naujas publikacijų konteineris
     */
    public void FormBelowAverage(ContainerPublications contPubl,
                                 ContainerPublications contPublNew)
    {
        contPublNew = new ContainerPublications();
        for(int i = 0; i < contPubl.Take(); i++)
        {
            if (contPubl.Take(i).TakeGrossProfit() < Average(contPubl))
            {
                contPublNew.Insert(contPubl.Take(i));
            }
        }
    }
    /**
     * Sukuria uždarbio listą
     * @param contPubl publikacijų konteineris
     * @param profitMonth Mėnesio uždarbis
     */
    public void FormProfitMonth(ContainerPublications contPubl, String[] profitMonth)
    {
        int max;
        String Names="";
        int index = 0;
        int stringIndex = 0;
        for(int i = 0; i < 12; i++)
        {
            max = 0;
            for (int j = 0; j < contPubl.Take(); j++)
            {
                if (contPubl.Take(j).TakeProfit(i) > contPubl.Take(max).TakeProfit(i))
                {
                    max = j;
                    index++;
                }
                else if (contPubl.Take(j).TakeProfit(i) == contPubl.Take(max).TakeProfit(i))
                {
                    Names += contPubl.Take(j).TakeName()+" ";
                }
            }
            if (index > 0) {
                profitMonth[stringIndex] = (contPubl.Take(max).TakeName());
                stringIndex++;
            }
            else{
                profitMonth[stringIndex] = Names;
                stringIndex++;
            }
        }
    }
    /**
     * Sutraukimo metodas
     * @param contSubs Prenumeratų konteineris
     * @param contPubl Publikacijų konteineris
     */
    public void Contraction(ContainerSubscriptions contSubs, ContainerPublications contPubl)
    {
        int poz;
        for(int i = 0; i < contSubs.Take(); i++)
        {
            poz = 0;
            for(int j = 0; j < contPubl.Take(); j++)
            {
                if(contSubs.Take(i).TakeSubscName().compareTo(contPubl.Take(j).TakeName()) == 0)
                {
                    poz = 1;
                    contPubl.Insert_j(j, contSubs.Take(i).TakePeriodStart(),
                            contSubs.Take(i).TakePeriodLenght(),
                            contSubs.Take(i).TakePriceMonth());
                    break;

                }
            }
            if (poz == 0)
            {
                Publication publication = new Publication();
                publication.Insert(contSubs.Take(i).TakeSubscName(),
                        contSubs.Take(i).TakePriceMonth(),
                        contSubs.Take(i).TakePeriodStart(),
                        contSubs.Take(i).TakePeriodLenght());
                contPubl.Insert(publication);
            }

        }
    }
    /**
     * Vidurkio metodas
     * @param contPubl Publikacijų konteineris
     * @return vidurkį
     */
    public double Average(ContainerPublications contPubl)
    {
        double sum = 0.0;
        for(int i = 0; i < contPubl.Take(); i++)
        {
            sum += contPubl.Take(i).TakeGrossProfit();
        }
        return sum/contPubl.Take();
    }
    /**
     * Atspausdina į GUI originalius duomenis
     * @param contSubs Prenumeratų konteineris
     */
    public void PrintOriginal(ContainerSubscriptions contSubs)
    {
        String strKiek = new String(new char[contSubs.Take(0).StringLenght() + 1]).replace('\0', '-');

        textArea.setText("Pradiniai duomenys:\n");
        textArea.append(strKiek+"\n");
        textArea.append(virsus1+"\n");
        textArea.append(strKiek+"\n");
        for(int i = 0; i < contSubs.Take(); i++)
        {
            textArea.append(contSubs.Take(i).ToString()+"\n");
            textArea.append(strKiek+"\n");
        }
    }
    /**
     *  Atspausdiną duomenis pagal paiešką
     * @param subscribers Prenumeratoriai
     */
    public void PrintSearchArray(ArrayList subscribers)
    {
        textArea.setText("Paieškos rezultatai:\n");
        if (subscribers.size() == 0) textArea.append("Duomenų pagal paiešką nėra\n");
        else
        {
            textArea.append("-------------------\n");
            textArea.append("Pavardė     Adresas\n");
            textArea.append("-------------------\n");
            for(int i = 0; i < subscribers.size(); i++)
            {
                textArea.append(String.format("%-19s\n", subscribers.get(i).toString()));
            }
            textArea.append("-------------------\n");
        }

    }
    /**
     * Išpausdiną kuri prenumerata turėjo didžiausia uždarbi tam tikrą mėnesį
     * @param profitMonth Mėnesių uždarbiai
     */
    public void PrintMaxMonth(String[] profitMonth)
    {
        String strKiek = "-".repeat(24);
        textArea.append("\nDaugiausiai tą mėnęsi uždirbusios publikacijos:\n");
        textArea.append(strKiek+"\n");
        textArea.append(virsus2+"\n");
        textArea.append(strKiek+"\n");
        for (int i = 0; i < 12; i++)
        {
            textArea.append(String.format("%2d      %-13s\n", i+1, profitMonth[i]));
        }
        textArea.append(strKiek+"\n");

    }
    /**
     * Išpausdiną visą Publikacijų uždarbius
     * @param contPubli publikacijų konteineris
     */
    public void PrintGrossProfit(ContainerPublications contPubli)
    {
        String strKiek = "-".repeat(24);
        textArea.append("\nPublikacijų visas uždarbis:\n");
        textArea.append(strKiek+"\n");
        textArea.append(virsus3+"\n");
        textArea.append(strKiek+"\n");
        for (int i = 0; i < contPubli.Take(); i++)
        {
            textArea.append(String.format("%-12s %10.2f\n", contPubli.Take(i).TakeName(), contPubli.Take(i).TakeGrossProfit()));
        }
        textArea.append(strKiek+"\n");

    }
    /**
     * Isspausdina visus mazesnius už vidurki
     * @param contPubli Publikaciju konteineris
     */
    public void PrintBelowAverage(ContainerPublications contPubli)
    {

        textArea.append("\nPublikacijos kurios uždarbis mažesnis už vidurkį:\n");
        if (contPubli.Take() > 0)
        {
            textArea.append("----------------------\n");
            textArea.append("Publikacijos    Pinigai\r\n Pavadinimas      Eurai\n");
            textArea.append("----------------------\n");
            for (int i = 0; i < contPubli.Take(); i++)
            {
                textArea.append(contPubli.Take(i).ToString()+"\n");
            }
            textArea.append("----------------------\n");
        }
        else textArea.append("Mažesnių už vidurkį nėra\n");
    }
}
