package com.grq.product_ms.integrations.pokeapi;

public class Ability {
    private Species ability;
    private boolean isHidden;
    private long slot;

    public Species getAbility() { return ability; }
    public void setAbility(Species value) { this.ability = value; }
    public boolean getIsHidden() { return isHidden; }
    public void setIsHidden(boolean value) { this.isHidden = value; }
    public long getSlot() { return slot; }
    public void setSlot(long value) { this.slot = value; }
}
