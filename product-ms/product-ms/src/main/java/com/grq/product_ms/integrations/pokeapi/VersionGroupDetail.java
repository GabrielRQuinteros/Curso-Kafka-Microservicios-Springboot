package com.grq.product_ms.integrations.pokeapi;
public class VersionGroupDetail {
    private long levelLearnedAt;
    private Species moveLearnMethod;
    private Long order;
    private Species versionGroup;

    public long getLevelLearnedAt() { return levelLearnedAt; }
    public void setLevelLearnedAt(long value) { this.levelLearnedAt = value; }
    public Species getMoveLearnMethod() { return moveLearnMethod; }
    public void setMoveLearnMethod(Species value) { this.moveLearnMethod = value; }
    public Long getOrder() { return order; }
    public void setOrder(Long value) { this.order = value; }
    public Species getVersionGroup() { return versionGroup; }
    public void setVersionGroup(Species value) { this.versionGroup = value; }
}
