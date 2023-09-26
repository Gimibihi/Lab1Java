/**
 * Klase publikaciju konteineriui
 */
class ContainerPublications
{
    final int CMax = 100000;
    private int amount;
    private Publication[] publication;
    /**
     * sukuria konstruktorių
     */
    public ContainerPublications()
    {
        publication = new Publication[CMax];
        amount = 0;
    }
    /**
     * Leidžia įvesti duomenis į konteinerį
     * @param ob objektas laikinas
     */
    public void Insert(Publication ob) { publication[amount++] = ob; }
    /**
     * Leidžia papildyti obektą konteineryje
     * @param i indeksas
     * @param start periodo pradžia
     * @param length periodo ilgis
     * @param price kaina
     */
    public void Insert_j(int i, int start, int length, double price)
    {
        publication[i].Insert(price, start, length);
    }
    /**
     * Paima kiekį
     * @return kiekis
     */
    public int Take() { return amount; }
    /**
     * Paima publikaciją
     * @param i indeksas
     * @return publikaciją
     */
    public Publication Take(int i) { return publication[i]; }
    /**
     * Rikiavimo metodas
     */
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