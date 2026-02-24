package com.grq.product_ms.integrations.pokeapi;
public class PastAbility {
    private Ability[] abilities;
    private Species generation;

    public Ability[] getAbilities() { return abilities; }
    public void setAbilities(Ability[] value) { this.abilities = value; }
    public Species getGeneration() { return generation; }
    public void setGeneration(Species value) { this.generation = value; }
}
