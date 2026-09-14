package com.vnap.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;

import java.util.UUID;

final class ClientEntityLookup {
    private ClientEntityLookup() {
    }

    static Entity byUuid(ClientLevel level, UUID id) {
        if (level == null || id == null) return null;
        for (Entity entity : level.entitiesForRendering()) {
            if (id.equals(entity.getUUID())) return entity;
        }
        return null;
    }
}
