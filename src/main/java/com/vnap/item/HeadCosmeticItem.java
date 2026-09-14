package com.vnap.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Simple wearable cosmetic for the vanilla head equipment slot on Minecraft 1.21.1. */
public final class HeadCosmeticItem extends Item {
    public HeadCosmeticItem(Properties properties) {
        super(properties);
    }

    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }
}
