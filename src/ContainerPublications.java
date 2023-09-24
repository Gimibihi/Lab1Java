class ContainerPublications
{
    final int CMax = 10000;
    private int amount;
    private Publication[] publication;
    /// <summary>
    /// sukuria konstruktorių
    /// </summary>
    public ContainerPublications()
    {
        publication = new Publication[CMax];
        amount = 0;
    }
    /// <summary>
    /// Leidžia įvesti duomenis į konteinerį
    /// </summary>
    /// <param name="ob">objektas laikinas</param>
    public void Insert(Publication ob) { publication[amount++] = ob; }
    /// <summary>
    /// Leidžia papildyti obektą konteineryje
    /// </summary>
    /// <param name="i">indeksas</param>
    /// <param name="start">periodo pradžia</param>
    /// <param name="length">periodo ilgis</param>
    /// <param name="price">kaina</param>
    public void Insert_j(int i, int start, int length, double price)
    {
        publication[i].Insert(price, start, length);
    }
    /// <summary>
    /// Paima kiekį
    /// </summary>
    /// <returns>kiekis</returns>
    public int Take() { return amount; }
    /// <summary>
    /// Paima publikaciją
    /// </summary>
    /// <param name="i">indeksas</param>
    /// <returns>publikaciją</returns>
    public Publication Take(int i) { return publication[i]; }
    /// <summary>
    /// Rikevimo metodas
    /// </summary>
    public void Sort()
    {
        for (int i = 0; i < amount - 1; i++)
        {
            Publication min = publication[i];
            int im = i;
            for (int j = i + 1; j < amount; j++)
            {
                if (publication[j].TakeName().toLowerCase().compareTo(min.TakeName().toLowerCase()) <= 0)
                {
                    min = publication[j];
                    im = j;
                }
            }
            publication[im] = publication[i];
            publication[i] = min;
        }
    }
}