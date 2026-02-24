package com.grq.product_ms.integrations.pokeapi;
public class PastStat {
    private Species generation;
    private Stat[] stats;

    public Species getGeneration() { return generation; }
    public void setGeneration(Species value) { this.generation = value; }
    public Stat[] getStats() { return stats; }
    public void setStats(Stat[] value) { this.stats = value; }
}
