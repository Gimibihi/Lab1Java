class ContainerSubscriptions
{
    final int CMax = 10000;
    private Subscriptions[] subscriptions;
    private int amount;
    /// <summary>
    /// Sukuria konstruktorių
    /// </summary>
    public ContainerSubscriptions()
    {
        subscriptions = new Subscriptions[CMax];
        amount = 0;
    }
    /// <summary>
    /// Leidžia įvesti duomenis į konteinerį
    /// </summary>
    /// <param name="ob">Objektas</param>
    public void Insert(Subscriptions ob) { subscriptions[amount++] = ob; }
    /// <summary>
    /// Paima kiekį
    /// </summary>
    /// <returns>kiekį</returns>
    public int Take() { return amount; }
    /// <summary>
    /// Paima klasę
    /// </summary>
    /// <param name="i">indeksas</param>
    /// <returns>klasę</returns>
    public Subscriptions Take(int i) { return subscriptions[i]; }

}