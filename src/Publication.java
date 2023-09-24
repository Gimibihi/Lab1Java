class Publication
{
    private String name;
    private double[] profit;
    /// <summary>
    /// Sukuria konstruktorių
    /// </summary>
    public Publication()
    {
        name = "";
        profit = new double[12];
    }
    /// <summary>
    /// Leidžia įvesti duomenis į klasę
    /// </summary>
    /// <param name="name">Publikacijos pavadinimas</param>
    /// <param name="price">kaina publikacijos</param>
    /// <param name="start">pradžia</param>
    /// <param name="length">ilgis periodo</param>
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
    /// <summary>
    /// Leidžia papildyti kalsę
    /// </summary>
    /// <param name="price">Kaina</param>
    /// <param name="start">periodo pradžia</param>
    /// <param name="length">periodo ilgis</param>
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
    /// <summary>
    /// Paima visą uždarbį
    /// </summary>
    /// <returns>visą uždarbį</returns>
    public double TakeGrossProfit()
    {
        double grossProfit = 0.0;
        for (int i = 0; i < 12; i++)
        {
            grossProfit += profit[i];
        }
        return grossProfit;
    }
    /// <summary>
    /// Sukuria String eilutę
    /// </summary>
    /// <returns>String eilutę</returns>
    public String ToString()
    {
    String line;
    line = String.format("{0,-16}{1,6:f2}", name,TakeGrossProfit());
    return line;
    }
    /// <summary>
    /// Paima publikacijos pavadinimą
    /// </summary>
    /// <returns>publikacijos pavadinimas</returns>
    public String TakeName() { return name; }
    /// <summary>
    /// Paima mėnesio uždarbį
    /// </summary>
    /// <param name="i">mėnesio indeksas</param>
    /// <returns>mėnesio uždarbį</returns>
    public double TakeProfit(int i) { return profit[i]; }

    public boolean Equals(Object obj)
    {
    /*return obj is Publication publication &&
        name == publication.name &&
        EqualityComparer<double[]>.Default.Equals(profit, publication.profit);*/
        return false;
    }

}