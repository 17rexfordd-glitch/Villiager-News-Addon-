package com.vnap.item;

import com.vnap.VillagerNewsAddonPort;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.Map;

public final class VillagerNewsItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(VillagerNewsAddonPort.RESOURCE_NAMESPACE);
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VillagerNewsAddonPort.RESOURCE_NAMESPACE);

    public static final DeferredItem<Item> HANDBOOK = ITEMS.register("handbook", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<HeadCosmeticItem> MAYOR_HAT = ITEMS.register("mayor_hat", () -> new HeadCosmeticItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MICROPHONE = ITEMS.register("microphone", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<HeadCosmeticItem> MOUSTACHE = ITEMS.register("moustache", () -> new HeadCosmeticItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<HeadCosmeticItem> TESTIFICATE_MAN_HELMET = ITEMS.register("testificate_man_helmet", () -> new HeadCosmeticItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<HeadCosmeticItem> VILLAGER_NOSE = ITEMS.register("villager_nose", () -> new HeadCosmeticItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<SpawnEggItem> MAYOR_VILLAGER_SPAWN_EGG = registerSpawnEgg("mayor_villager_spawn_egg", EntityType.VILLAGER, "Mayor Villager");
    public static final DeferredItem<SpawnEggItem> TESTIFICATE_MAN_SPAWN_EGG = registerSpawnEgg("testificate_man_spawn_egg", EntityType.VILLAGER, "Testificate Man");
    public static final DeferredItem<SpawnEggItem> VILLAGER_5_SPAWN_EGG = registerSpawnEgg("villager_5_spawn_egg", EntityType.VILLAGER, "Villager #5");
    public static final DeferredItem<SpawnEggItem> VILLAGER_9_SPAWN_EGG = registerSpawnEgg("villager_9_spawn_egg", EntityType.VILLAGER, "Villager #9");
    public static final DeferredItem<SpawnEggItem> UNTOUCHABLE_VILLAGER_SPAWN_EGG = registerSpawnEgg("untouchable_villager_spawn_egg", EntityType.VILLAGER, "Villager Unreachable");
    public static final DeferredItem<SpawnEggItem> WOOLY_SPAWN_EGG = registerSpawnEgg("wooly_spawn_egg", EntityType.SHEEP, "Wooly The Sheep");

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = TABS.register("items", () ->
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.villager-news-addon-port.items"))
            .icon(() -> new ItemStack(HANDBOOK.get()))
            .displayItems((parameters, output) -> {
                output.accept(HANDBOOK.get());
                output.accept(MAYOR_HAT.get());
                output.accept(TESTIFICATE_MAN_HELMET.get());
                output.accept(MICROPHONE.get());
                output.accept(MOUSTACHE.get());
                output.accept(VILLAGER_NOSE.get());
                output.accept(MAYOR_VILLAGER_SPAWN_EGG.get());
                output.accept(TESTIFICATE_MAN_SPAWN_EGG.get());
                output.accept(VILLAGER_5_SPAWN_EGG.get());
                output.accept(VILLAGER_9_SPAWN_EGG.get());
                output.accept(UNTOUCHABLE_VILLAGER_SPAWN_EGG.get());
                output.accept(WOOLY_SPAWN_EGG.get());
            })
            .build()
    );

    private static final Map<Item, Integer> COSMETICS = new LinkedHashMap<>();

    private VillagerNewsItems() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        TABS.register(modBus);
    }

    public static int cosmetic(Item item) {
        ensureCosmetics();
        return COSMETICS.getOrDefault(item, 0);
    }

    public static Item cosmeticItem(int cosmetic) {
        ensureCosmetics();
        return COSMETICS.entrySet().stream().filter(entry -> entry.getValue() == cosmetic)
            .map(Map.Entry::getKey).findFirst().orElse(null);
    }

    private static void ensureCosmetics() {
        if (!COSMETICS.isEmpty()) return;
        COSMETICS.put(MAYOR_HAT.get(), 1);
        COSMETICS.put(TESTIFICATE_MAN_HELMET.get(), 2);
        COSMETICS.put(MICROPHONE.get(), 3);
        COSMETICS.put(MOUSTACHE.get(), 4);
    }

    private static DeferredItem<SpawnEggItem> registerSpawnEgg(String path, EntityType<? extends Mob> type, String entityName) {
        return ITEMS.register(path, () -> new NamedSpawnEggItem(type, entityName, new Item.Properties().stacksTo(64)));
    }

    private static final class NamedSpawnEggItem extends SpawnEggItem {
        private final String entityName;

        private NamedSpawnEggItem(EntityType<? extends Mob> type, String entityName, Item.Properties properties) {
            // Colors only affect the legacy tint path; item models provide the visible textures for this mod.
            super(type, 0x8B6B4A, 0xD8C49A, properties);
            this.entityName = entityName;
        }

        @Override
        public ItemStack getDefaultInstance() {
            ItemStack stack = super.getDefaultInstance();
            CompoundTag tag = new CompoundTag();
            tag.put("CustomName", ComponentSerialization.CODEC.encodeStart(NbtOps.INSTANCE, Component.literal(entityName)).getOrThrow());
            tag.putBoolean("PersistenceRequired", true);
            stack.set(DataComponents.ENTITY_DATA, CustomData.of(tag));
            return stack;
        }
    }
}
