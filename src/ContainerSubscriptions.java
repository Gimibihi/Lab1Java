/**
 * Klase prenumeratoriu konteineriui
 */
class ContainerSubscriptions
{
    final int CMax = 10000;
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

}