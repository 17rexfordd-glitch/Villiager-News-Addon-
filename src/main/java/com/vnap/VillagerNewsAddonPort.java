package com.vnap;

import com.vnap.client.VillagerNewsAddonPortClient;
import com.vnap.command.DialogueTestCommand;
import com.vnap.config.VillagerNewsBuildSettings;
import com.vnap.config.VillagerNewsSettings;
import com.vnap.dialogue.ContextualDialogueController;
import com.vnap.dialogue.DialogueCatalog;
import com.vnap.item.VillagerNewsItems;
import com.vnap.network.VillagerNewsNetworking;
import com.vnap.network.VillagerNewsSettingsNetwork;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(VillagerNewsAddonPort.MOD_ID)
public final class VillagerNewsAddonPort {
    /** NeoForge requires a Java-style mod id; existing assets intentionally keep the original dashed namespace. */
    public static final String MOD_ID = "villager_news_addon_port";
    public static final String RESOURCE_NAMESPACE = "villager-news-addon-port";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public VillagerNewsAddonPort(IEventBus modBus, ModContainer modContainer) {
        LOGGER.info("[startup] item registration: begin");
        VillagerNewsItems.register(modBus);
        LOGGER.info("[startup] item registration: complete");

        LOGGER.info("[startup] dialogue catalog registration: begin");
        DialogueCatalog.register(modBus);
        LOGGER.info("[startup] dialogue catalog registration: complete");

        LOGGER.info("[startup] network payload registration listener: begin");
        modBus.addListener(VillagerNewsNetworking::registerPayloads);
        LOGGER.info("[startup] network payload registration listener: complete");

        LOGGER.info("[startup] settings load: begin");
        VillagerNewsSettings.load();
        LOGGER.info("[startup] settings load: complete");

        LOGGER.info("[startup] settings network registration: begin");
        VillagerNewsSettingsNetwork.register(NeoForge.EVENT_BUS);
        LOGGER.info("[startup] settings network registration: complete");

        LOGGER.info("[startup] contextual dialogue controller registration: begin");
        ContextualDialogueController.register(NeoForge.EVENT_BUS);
        LOGGER.info("[startup] contextual dialogue controller registration: complete");

        if (VillagerNewsBuildSettings.dialogueTestCommand()) {
            DialogueTestCommand.register(NeoForge.EVENT_BUS);
        }

        if (FMLEnvironment.dist.isClient()) {
            LOGGER.info("[startup] client registration: begin");
            VillagerNewsAddonPortClient.register(modBus, NeoForge.EVENT_BUS, modContainer);
            LOGGER.info("[startup] client registration: complete");
        }

        LOGGER.info("Villager News models, textures, and contextual dialogue are ready.");
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(RESOURCE_NAMESPACE, path);
    }
}
