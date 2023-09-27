/**
 * Klasė naudojama saugoti publikacijoms
 */
class Publication
{
    private String name;
    private double[] profit;

    /**
     * Sukuria konstruktorių
     */
    public Publication()
    {
        name = "";
        profit = new double[12];
    }
    /**
     * Leidžia įvesti duomenis į klasę
     * @param name Publikacijos pavadinimas
     * @param price kaina publikacijos
     * @param start pradžia
     * @param length ilgis periodo
     */
    public void Insert(String name, double price, int start, int length)
    {
        this.name = name;
        start = start - 1;
        if ((start + length) < 13)
        {
            for (int i = 0; i < length; i++)
            {
                profit[start++] += price;
            }
        }
        else
        {
            for(int i = 0; i < length; i++)
            {
                profit[start++] += price;
                if (start == 12) start = 0;
            }
        }
    }
    /**
     * Leidžia papildyti kalsę
     * @param price Kaina
     * @param start periodo pradžia
     * @param length periodo ilgis
     */
    public void Insert(double price, int start, int length)
    {
        start = start - 1;
        if ((start + length) < 13)
        {
            for (int i = 0; i < length; i++)
            {
                profit[start++] += price;
            }
        }
        else
        {
            for (int i = 0; i < length; i++)
            {
                profit[start++] += price;
                if (start == 12) start = 0;
            }
        }
    }
    /**
     * Paima visą uždarbį
     * @return visą uždarbį
     */
    public double TakeGrossProfit()
    {
        double grossProfit = 0.0;
        for (int i = 0; i < 12; i++)
        {
            grossProfit += profit[i];
        }
        return grossProfit;
    }
    /**
     * Sukuria String eilutę
     * @return String eilutę
     */
    public String ToString()
    {
    String line;
    line = String.format("%-16s %6.2f", name,TakeGrossProfit());
    return line;
    }
    /**
     * Paima publikacijos pavadinimą
     * @return publikacijos pavadinimas
     */
    public String TakeName() { return name; }
    /// <summary>
    /// Paima mėnesio uždarbį
    /// </summary>
    /// <param name="i">mėnesio indeksas</param>
    /// <returns>mėnesio uždarbį</returns>

    /**
     * Paima mėnesio uždarbį
     * @param i mėnesio indeksas
     * @return mėnesio uždarbį
     */
    public double TakeProfit(int i) { return profit[i]; }
}