/**
 * Klase prenumeratoriu konteineriui
 */
class ContainerSubscriptions
{
    final int CMax = 100000;
    private Subscriptions[] subscriptions;
    private int amount;
    /**
     * Sukuria konstruktorių
     */
    public ContainerSubscriptions()
    {
        subscriptions = new Subscriptions[CMax];
        amount = 0;
    }
    /**
     * Leidžia įvesti duomenis į konteinerį
     * @param ob Objektas
     */
    public void Insert(Subscriptions ob) { subscriptions[amount++] = ob; }
    /**
     * Paima kiekį
     * @return kiekį
     */
    public int Take() { return amount; }
    /**
     * Paima klasę
     * @param i indeksas
     * @return klasę
     */
    public Subscriptions Take(int i) { return subscriptions[i]; }

    public void Remove(int indexToRemove){
        if (indexToRemove >= 0) {
            for (int i = indexToRemove; i < subscriptions.length; i++) {
                if((i+1)<subscriptions.length)
                    subscriptions[i] = subscriptions[i+1];
            }
        }
    }

    public void Sort(){
        for (int i = 0; i < amount - 1; i++)
        {
            Subscriptions min = subscriptions[i];
            int im = i;
            for (int j = i + 1; j < amount; j++)
            {
                if (subscriptions[j].TakeLastName().toLowerCase().compareTo(min.TakeLastName().toLowerCase()) <= 0)
                {
                    min = subscriptions[j];
                    im = j;
                }
            }
            subscriptions[im] = subscriptions[i];
            subscriptions[i] = min;
        }
    }

}