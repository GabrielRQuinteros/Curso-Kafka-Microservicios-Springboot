package com.grq.product_ms.integrations.pokeapi;
public class Response {
    private Ability[] abilities;
    private long baseExperience;
    private Cries cries;
    private Species[] forms;
    private GameIndex[] gameIndices;
    private long height;
    private Object[] heldItems;
    private long id;
    private boolean isDefault;
    private String locationAreaEncounters;
    private Move[] moves;
    private String name;
    private long order;
    private PastAbility[] pastAbilities;
    private PastStat[] pastStats;
    private Object[] pastTypes;
    private Species species;
    private Sprites sprites;
    private Stat[] stats;
    private Type[] types;
    private long weight;

    public Ability[] getAbilities() { return abilities; }
    public void setAbilities(Ability[] value) { this.abilities = value; }
    public long getBaseExperience() { return baseExperience; }
    public void setBaseExperience(long value) { this.baseExperience = value; }
    public Cries getCries() { return cries; }
    public void setCries(Cries value) { this.cries = value; }
    public Species[] getForms() { return forms; }
    public void setForms(Species[] value) { this.forms = value; }
    public GameIndex[] getGameIndices() { return gameIndices; }
    public void setGameIndices(GameIndex[] value) { this.gameIndices = value; }
    public long getHeight() { return height; }
    public void setHeight(long value) { this.height = value; }
    public Object[] getHeldItems() { return heldItems; }
    public void setHeldItems(Object[] value) { this.heldItems = value; }
    public long getid() { return id; }
    public void setid(long value) { this.id = value; }
    public boolean getIsDefault() { return isDefault; }
    public void setIsDefault(boolean value) { this.isDefault = value; }
    public String getLocationAreaEncounters() { return locationAreaEncounters; }
    public void setLocationAreaEncounters(String value) { this.locationAreaEncounters = value; }
    public Move[] getMoves() { return moves; }
    public void setMoves(Move[] value) { this.moves = value; }
    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
    public long getOrder() { return order; }
    public void setOrder(long value) { this.order = value; }
    public PastAbility[] getPastAbilities() { return pastAbilities; }
    public void setPastAbilities(PastAbility[] value) { this.pastAbilities = value; }
    public PastStat[] getPastStats() { return pastStats; }
    public void setPastStats(PastStat[] value) { this.pastStats = value; }
    public Object[] getPastTypes() { return pastTypes; }
    public void setPastTypes(Object[] value) { this.pastTypes = value; }
    public Species getSpecies() { return species; }
    public void setSpecies(Species value) { this.species = value; }
    public Sprites getSprites() { return sprites; }
    public void setSprites(Sprites value) { this.sprites = value; }
    public Stat[] getStats() { return stats; }
    public void setStats(Stat[] value) { this.stats = value; }
    public Type[] getTypes() { return types; }
    public void setTypes(Type[] value) { this.types = value; }
    public long getWeight() { return weight; }
    public void setWeight(long value) { this.weight = value; }
}
