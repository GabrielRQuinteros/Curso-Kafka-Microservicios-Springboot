package com.grq.product_ms.integrations.pokeapi;
public class Stat {
    private long baseStat;
    private long effort;
    private Species stat;

    public long getBaseStat() { return baseStat; }
    public void setBaseStat(long value) { this.baseStat = value; }
    public long getEffort() { return effort; }
    public void setEffort(long value) { this.effort = value; }
    public Species getStat() { return stat; }
    public void setStat(Species value) { this.stat = value; }
}
