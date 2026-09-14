package com.vnap.client;

import com.vnap.VillagerNewsAddonPort;
import com.vnap.item.VillagerNewsItems;
import com.vnap.network.DialogueAnimationPayload;
import com.vnap.network.VillagerNewsSettingsPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import traben.entity_model_features.EMFAnimationApi;

import java.io.IOException;
import java.util.function.Supplier;

public final class VillagerNewsAddonPortClient {
    private VillagerNewsAddonPortClient() {}

    public static void register(IEventBus modBus, IEventBus gameBus, ModContainer modContainer) {
        VillagerNewsAddonPort.LOGGER.info("[startup] client EMF animation registration: begin");
        try {
            DialogueAnimationState.load();
            registerFloat("vnap_speaking", DialogueAnimationState::speaking, "Whether the Villager News character is speaking");
            registerFloat("vnap_mouth_open", DialogueAnimationState::mouthOpen, "Current Villager News mouth opening");
            registerFloat("vnap_mouth_width", DialogueAnimationState::mouthWidth, "Current Villager News mouth width");
            registerFloat("vnap_mouth_closed", DialogueAnimationState::mouthClosed, "Current Villager News closed-mouth layer");
            registerFloat("vnap_has_nose", DialogueAnimationState::hasNose, "Villager News nose visibility");
            registerFloat("vnap_cosmetic_mayor_hat", () -> DialogueAnimationState.cosmetic(1), "Villager News mayor hat visibility");
            registerFloat("vnap_cosmetic_helmet", () -> DialogueAnimationState.cosmetic(2), "Villager News helmet visibility");
            registerFloat("vnap_cosmetic_microphone", () -> DialogueAnimationState.cosmetic(3), "Villager News microphone visibility");
            registerFloat("vnap_cosmetic_moustache", () -> DialogueAnimationState.cosmetic(4), "Villager News moustache visibility");
            for (String variable : DialogueAnimationState.animationVariables()) {
                registerFloat(variable, () -> DialogueAnimationState.transform(variable), "Synchronized Villager News dialogue transform");
            }
        } catch (IOException | RuntimeException exception) {
            throw new IllegalStateException("Could not load Villager News animations", exception);
        } catch (Exception exception) {
            throw new IllegalStateException("Could not register Villager News EMF animation variables", exception);
        }

        VillagerNewsAddonPort.LOGGER.info("[startup] client EMF animation registration: complete");
        VillagerNewsAddonPort.LOGGER.info("[startup] client event/config registration: begin");
        modBus.addListener(VillagerNewsAddonPortClient::addLayers);
        gameBus.addListener(VillagerNewsAddonPortClient::onDisconnect);
        gameBus.addListener(VillagerNewsAddonPortClient::onUseHandbook);
        gameBus.addListener(VillagerNewsAddonPortClient::onClientTick);
        gameBus.addListener(VillagerNewsAddonPortClient::onRenderGui);
        gameBus.addListener(VillagerNewsAddonPortClient::onRenderLiving);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
            (minecraft, parent) -> HandbookScreen.settingsScreen(parent));

        VillagerNewsAddonPort.LOGGER.info("[startup] client event/config registration: complete");
        VillagerNewsAddonPort.LOGGER.info("Registered synchronized EMF facial and dialogue animations");
    }

    public static void handleDialogueAnimation(DialogueAnimationPayload payload) {
        Minecraft client = Minecraft.getInstance();
        client.execute(() -> {
            DialogueSoundState.start(payload);
            DialogueAnimationState.start(payload);
            DialogueSubtitleState.start(payload);
        });
    }

    public static void handleSettings(VillagerNewsSettingsPayload payload) {
        Minecraft.getInstance().execute(() -> VillagerNewsSettingsState.apply(payload));
    }

    private static void addLayers(EntityRenderersEvent.AddLayers event) {
        var renderer = event.getRenderer(EntityType.VILLAGER);
        if (renderer instanceof VillagerRenderer villagerRenderer) {
            villagerRenderer.addLayer(new VillagerNewsSignLayer(villagerRenderer));
        }
    }

    private static void onDisconnect(ClientPlayerNetworkEvent.LoggingOut event) {
        Minecraft client = Minecraft.getInstance();
        DialogueSoundState.clear(client);
        DialogueAnimationState.clear();
        DialogueSubtitleState.clear();
        VillagerNewsSettingsState.reset();
    }

    private static void onUseHandbook(PlayerInteractEvent.RightClickItem event) {
        if (!event.getEntity().level().isClientSide()) return;
        if (event.getEntity().getItemInHand(event.getHand()).getItem() != VillagerNewsItems.HANDBOOK.get()) return;
        Minecraft.getInstance().setScreen(new HandbookScreen());
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        DialogueSoundState.tick(client);
        DialogueAnimationState.tick(client);
        DialogueSubtitleState.tick(client);
    }

    private static void onRenderGui(RenderGuiEvent.Post event) {
        DialogueSubtitleState.render(event.getGuiGraphics(), event.getPartialTick());
    }

    private static void onRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
        if (!(event.getEntity() instanceof Villager villager)) return;
        float partialTick = event.getPartialTick();
        float bodyRotation = Mth.rotLerp(partialTick, villager.yBodyRotO, villager.yBodyRot);
        DialogueAnimationState.trackBodyRotation(villager, bodyRotation, villager.tickCount + partialTick);
    }

    private static void registerFloat(String name, Supplier<Float> supplier, String description) throws Exception {
        EMFAnimationApi.registerSingletonAnimationVariable(VillagerNewsAddonPort.MOD_ID, name, description, supplier);
    }
}
