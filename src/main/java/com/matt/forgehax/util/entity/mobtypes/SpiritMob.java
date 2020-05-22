package com.matt.forgehax.util.entity.mobtypes;

import com.matt.forgehax.util.common.PriorityEnum;
import net.minecraft.entity.Entity;
import lumien.randomthings.entitys.ModEntitys;
import lumien.randomthings.entitys.EntitySpirit;

/**
 * Created on 6/27/2017 by fr1kin
 */
public class SpiritMob extends MobType {
  
  @Override
  protected PriorityEnum getPriority() {
    return PriorityEnum.LOW;
  }
  
  @Override
  public boolean isMobType(Entity entity) {
    return entity instanceof EntitySpirit;
  }
  
  @Override
  protected MobTypeEnum getMobTypeUnchecked(Entity entity) {
    return MobTypeEnum.NEUTRAL;
  }
}
