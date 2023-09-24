import java.util.ArrayList;

class Subscriptions
{
    private String lastName;
    private String adress;
    private int periodStart;
    private int periodLength;
    private String subscName;
    private double priceMonth;
    /// <summary>
    /// Sukuria konstruktiorių
    /// </summary>
    public Subscriptions()
    {
        lastName = "";
        adress = "";
        periodStart = 0;
        periodLength = 0;
        subscName = "";
        priceMonth = 0.0;
    }
    /// <summary>
    /// Leidžia įvesti duomenis į klasę
    /// </summary>
    /// <param name="lastName">Pavardė</param>
    /// <param name="adress">Adresas</param>
    /// <param name="periodStart">Periodo pradžia</param>
    /// <param name="periodLength">Periodo ilgis</param>
    /// <param name="subscName">Prenumeratos pavadinimas</param>
    /// <param name="priceMonth">Kaina mėnesiui</param>
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
    /// <summary>
    /// Sukuria String išvedimui
    /// </summary>
    /// <returns>eilutę spausdinimui</returns>
    public String ToString()
{
    String line;
    line = String.format("%-12s %-15s %6d %16d        %-12s %8.2f",lastName,
            adress, periodStart, periodLength, subscName, priceMonth);
    return line;
}
    /// <summary>
    /// Randa ilgį
    /// </summary>
    /// <returns>Gražina String kintamojo ilgį</returns>
    public int StringLenght()
    {
        int len = 0;
        len = ToString().length();
        return len;
    }
    public String TakeAdress() { return adress; }
    /// <summary>
    /// Paiima prenumeratos pavadinimą
    /// </summary>
    /// <returns>prenumeratos pavadinimą</returns>
    public String TakeSubscName() { return subscName; }
    /// <summary>
    /// Paima Pavardę
    /// </summary>
    /// <returns>Pavardę</returns>
    public String TakeLastName() { return lastName; }
    /// <summary>
    /// Paima periodo pradžią
    /// </summary>
    /// <returns>periodo pradžią</returns>
    public int TakePeriodStart() { return periodStart; }
    /// <summary>
    /// Paima periodo ilgį
    /// </summary>
    /// <returns>Periodo ilgį</returns>
    public int TakePeriodLenght() { return periodLength; }
    /// <summary>
    /// Paima mėnesio kainą
    /// </summary>
    /// <returns>mėnesio kainą</returns>
    public double TakePriceMonth() { return priceMonth; }
    /// <summary>
    /// Sukuria Mėnesių kuriais buvo prenumeruotą listą
    /// </summary>
    /// <returns>Mėnesių listą</returns>
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
    /// <summary>
    /// Lyginimo metodas
    /// </summary>
    /// <param name="publi">Prenumeratos pavadinimas</param>
    /// <param name="month">Mėnesio numeris</param>
    /// <returns></returns>
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
