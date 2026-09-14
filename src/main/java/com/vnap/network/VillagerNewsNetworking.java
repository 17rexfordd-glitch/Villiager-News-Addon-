package com.vnap.network;

import com.vnap.VillagerNewsAddonPort;
import com.vnap.client.VillagerNewsAddonPortClient;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;

public final class VillagerNewsNetworking {
    private static final String NETWORK_VERSION = "1";

    private VillagerNewsNetworking() {
    }

    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        VillagerNewsAddonPort.LOGGER.info("[startup] network payload registration: begin");
        var registrar = event.registrar(NETWORK_VERSION);
        registrar.playToClient(
            DialogueAnimationPayload.TYPE,
            DialogueAnimationPayload.CODEC,
            (payload, context) -> VillagerNewsAddonPortClient.handleDialogueAnimation(payload)
        );
        registrar.playBidirectional(
            VillagerNewsSettingsPayload.TYPE,
            VillagerNewsSettingsPayload.CODEC,
            new DirectionalPayloadHandler<>(
                (payload, context) -> VillagerNewsAddonPortClient.handleSettings(payload),
                (payload, context) -> {
                    if (context.player() instanceof ServerPlayer player) {
                        VillagerNewsSettingsNetwork.handleFromClient(player, payload);
                    }
                }
            )
        );
        VillagerNewsAddonPort.LOGGER.info("[startup] network payload registration: complete");
    }
}
