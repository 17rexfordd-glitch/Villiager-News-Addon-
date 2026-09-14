package com.vnap.network;

import com.vnap.config.VillagerNewsSettings;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public final class VillagerNewsSettingsNetwork {
    private VillagerNewsSettingsNetwork() {
    }

    public static void register(IEventBus gameBus) {
        gameBus.addListener(VillagerNewsSettingsNetwork::onPlayerLoggedIn);
    }

    public static void handleFromClient(ServerPlayer player, VillagerNewsSettingsPayload payload) {
        if (!canEdit(player)) {
            send(player);
            return;
        }
        VillagerNewsSettings.update(payload.chattiness(), payload.rareVoicelines(), payload.spawnSpecialVillagers());
        send(player);
    }

    private static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            send(player);
        }
    }

    public static void send(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new VillagerNewsSettingsPayload(
            VillagerNewsSettings.chattiness(),
            VillagerNewsSettings.rareVoicelines(),
            VillagerNewsSettings.spawnSpecialVillagers(),
            canEdit(player)
        ));
    }

    private static boolean canEdit(ServerPlayer player) {
        return player.getServer().isSingleplayerOwner(player.getGameProfile()) || player.hasPermissions(2);
    }
}
