import java.util.ArrayList;

/**
 * Klasė prenumeratoriu duomenims saugoti
 */
class Subscriptions
{
    private String lastName;
    private String adress;
    private int periodStart;
    private int periodLength;
    private String subscName;
    private double priceMonth;
    /**
     * Sukuria konstruktiorių
     */
    public Subscriptions()
    {
        lastName = "";
        adress = "";
        periodStart = 0;
        periodLength = 0;
        subscName = "";
        priceMonth = 0.0;
    }
    /**
     * Leidžia įvesti duomenis į klasę
     * @param lastName Pavardė
     * @param adress Adresas
     * @param periodStart Periodo pradžia
     * @param periodLength Periodo ilgis
     * @param subscName Prenumeratos pavadinimas
     * @param priceMonth Kaina mėnesiui
     */
    public void Insert(String lastName, String adress, int periodStart, int periodLength,
                       String subscName, double priceMonth)
    {
        this.lastName = lastName;
        this.adress = adress;
        this.periodStart = periodStart;
        this.periodLength = periodLength;
        this.subscName = subscName;
        this.priceMonth = priceMonth;
    }
    /**
     * Sukuria String išvedimui
     * @return eilutę spausdinimui
     */
    public String ToString()
    {
    String line;
    line = String.format("%-12s %-15s %6d %16d        %-12s %8.2f",lastName,
            adress, periodStart, periodLength, subscName, priceMonth);
    return line;
    }
    /**
     * Randa ilgį
     * @return Gražina String kintamojo ilgį
     */
    public int StringLenght()
    {
        int len = 0;
        len = ToString().length();
        return len;
    }

    /**
     * Paima adresa
     * @return grazina gyvenamaji adresa
     */
    public String TakeAdress() { return adress; }
    /**
     * Paiima prenumeratos pavadinimą
     * @return prenumeratos pavadinimą
     */
    public String TakeSubscName() { return subscName; }
    /**
     * Paima Pavardę
     * @return Pavardę
     */
    public String TakeLastName() { return lastName; }
    /**
     * Paima periodo pradžią
     * @return periodo pradžią
     */
    public int TakePeriodStart() { return periodStart; }
    /**
     * Paima periodo ilgį
     * @return Periodo ilgį
     */
    public int TakePeriodLenght() { return periodLength; }
    /**
     * Paima mėnesio kainą
     * @return mėnesio kainą
     */
    public double TakePriceMonth() { return priceMonth; }
    /**
     * Sukuria Mėnesių kuriais buvo prenumeruotą listą
     * @return Mėnesių listą
     */
    public int[] Months()
    {
        int index=0;
        int start = periodStart;
        int lenght = periodLength;
        int[] Months = new int[12];
        for (int i = 0; i < lenght; i++)
        {
            if (start <= 12)
            {
                Months[index]=(start);
                start++;
                index++;
            }
            else
            {
                start = 1;
                Months[index]=(start);
                index++;
            }
        }
        return Months;
    }
    /**
     * Lyginimo metodas
     * @param publi Prenumeratos pavadinimas
     * @param month Mėnesio numeris
     * @return True arba False pagal loginės sąlygos rezultatą
     */
    public boolean Equals(String publi, int month)
    {
        int p = 1;
        int[] months = new int[12];
        months = Months();
        for (int i = 0; i < months.length; i++)
        {
            if (publi.equals(subscName) & month == months[i])
            {
                return true;
            }

        }
        return false;
    }
}
