import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Gui extends JFrame{
    private JPanel panelMain;
    private JScrollPane scrollPane;
    private JButton inputButton;
    private JButton sortButton;
    private JTextArea textArea;
    private JButton calculateButton;


    final String virsus1 = "Pavardė     Adresas        Laikotarpio    Laikotarpio   Leidinio       Kaina\r\n" +
            "                                           Pradžia         Ilgis     Pavadinimas     Mėnesiui";
    final String virsus2 = "Mėn.    Publikacija";
    final String virsus3 = "Publikacija       Eurai";
    static final File CFd = new File("@..\\..\\duomenys.txt");
    public Gui(){

        int month = 4;
        String publication = "Zmones";

        String[] profitMonth = new String[10000];
        String[] subscribers = new String[10000];

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
                try {
                    Read(CFd,contSubscrip);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                PrintOriginal(contSubscrip);
                if (contSubscrip.Take() <= 0) { System.out.println("Nėra pradinių duomenų"); return; }
            }
        });
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contPubli.Sort();
                PrintGrossProfit(contPubli);
            }
        });
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormSubscriberArray(contSubscrip, publication, month,  subscribers);
                Contraction(contSubscrip, contPubli);
                FormProfitMonth(contPubli,  profitMonth);
                FormBelowAverage(contPubli,  contPubliNew);
                PrintSearchArray(subscribers);
                PrintMaxMonth(profitMonth);
                PrintGrossProfit(contPubli);
                PrintBelowAverage(contPubliNew);
            }
        });
    }

    /// <summary>
    /// Nuskaitymo metodą
    /// </summary>
    /// <param name="file">Failas</param>
    /// <param name="contSubs">Prenumeratų konteineris</param>
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

    /// <summary>
    /// Sukuria prenumeratorių listą
    /// </summary>
    /// <param name="contSubs">prenumeratų konteineris</param>
    /// <param name="publication">publikacija</param>
    /// <param name="month">mėnesis</param>
    /// <param name="subscribers">prenumeratriai</param>
    public void FormSubscriberArray(ContainerSubscriptions contSubs, String publication, int month, String[] subscribers)
    {
        int index = 0;
        for(int i = 0; i < contSubs.Take(); i++)
        {
            if (contSubs.Take(i).Equals(publication, month))
            {
                subscribers[index]=String.format(contSubs.Take(i).TakeLastName()+"   "+
                        contSubs.Take(i).TakeAdress());
                index++;
            }
        }
    }
    /// <summary>
    /// Sukuria konteinerį publikacijų mažiau už vidurkį
    /// </summary>
    /// <param name="contPubl">Publikacijų konteineris</param>
    /// <param name="contPublNew">Naujas publikacijų konteineris</param>
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
    /// <summary>
    /// Sukuria uždarbio listą
    /// </summary>
    /// <param name="contPubl">publikacijų konteineris</param>
    /// <param name="profitMonth">Mėnesio uždarbis</param>
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
    /// <summary>
    /// Sutraukimo metodas
    /// </summary>
    /// <param name="contSubs">Prenumeratų konteineris</param>
    /// <param name="contPubl">Publikacijų konteineris</param>
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
    /// <summary>
    /// Vidurkio metodas
    /// </summary>
    /// <param name="contPubl">Publikacijų konteineris</param>
    /// <returns>vidurkį</returns>
    public double Average(ContainerPublications contPubl)
    {
        double sum = 0.0;
        for(int i = 0; i < contPubl.Take(); i++)
        {
            sum += contPubl.Take(i).TakeGrossProfit();
        }
        return sum/contPubl.Take();
    }
    /// <summary>
    /// Atspausdina originalų failą
    /// </summary>
    /// <param name="file">failas</param>
    /// <param name="contSubs">Prenumeratų konteineris</param>
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
    /// <summary>
    /// Atspausdiną duomenis pagal paiešką
    /// </summary>
    /// <param name="file">failas</param>
    /// <param name="subscribers">Prenumeratoriai</param>
    public void PrintSearchArray(String[] subscribers)
    {
        textArea.setText("Paieškos rezultatai:\n");
        if (subscribers.length == 0) textArea.append("Duomenų pagal paiešką nėra\n");
        else
        {
            textArea.append("-------------------\n");
            textArea.append("Pavardė     Adresas\n");
            textArea.append("-------------------\n");
            for(int i = 0; i < subscribers.length; i++)
            {
                if(subscribers[i]!=null) {
                    textArea.append(String.format("%-19s\n", subscribers[i]));
                }
            }
            textArea.append("-------------------\n");
        }

    }
    /// <summary>
    /// Išpausdiną kuri prenumerata didžiausia tam tikrą mėnesį
    /// </summary>
    /// <param name="file">failas</param>
    /// <param name="profitMonth">Mėnesių uždarbiai</param>
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
    /// <summary>
    /// Išpausdiną visą Publikacijų uždarbius
    /// </summary>
    /// <param name="file">failas</param>
    /// <param name="contPubli">publikacijų konteineris</param>
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
    /// <summary>
    /// Isspausdina visus mazesnius už vidurki
    /// </summary>
    /// <param name="file">failas</param>
    /// <param name="contPubli">Publikaciju konteineris</param>
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
