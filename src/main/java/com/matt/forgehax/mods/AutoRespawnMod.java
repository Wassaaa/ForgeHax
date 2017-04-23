package com.matt.forgehax.mods;

import com.matt.forgehax.events.LocalPlayerUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class AutoRespawnMod extends ToggleMod {
    public AutoRespawnMod() {
        super("AutoRespawn", false, "Auto respawn on death");
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(LocalPlayerUpdateEvent event) {
        if(WRAPPER.getLocalPlayer().getHealth() <= 0) {
            WRAPPER.getLocalPlayer().respawnPlayer();
        }
    }
}