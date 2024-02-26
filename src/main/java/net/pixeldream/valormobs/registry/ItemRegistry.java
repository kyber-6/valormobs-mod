package net.pixeldream.valormobs.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.pixeldream.valormobs.ValorMobs;

public class ItemRegistry {
    public static final Item SKULLHAVEN_SPIRIT_SPAWN_EGG = registerItem("skullhaven_spirit_spawn_egg", new SpawnEggItem(EntityRegistry.SKULLHAVEN_SPIRIT_ENTITY, 0xE3E3E3, 0xB8B8B8, new FabricItemSettings().maxCount(64)));
    public static final Item EXECUTIONER_SPAWN_EGG = registerItem("executioner_spawn_egg", new SpawnEggItem(EntityRegistry.EXECUTIONER_ENTITY, 0xD5D0C1, 0x484237, new FabricItemSettings().maxCount(64)));
    public static final Item MUMMY_SPAWN_EGG = registerItem("mummy_spawn_egg", new SpawnEggItem(EntityRegistry.MUMMY_ENTITY, 0x655B4E, 0x9B4230, new FabricItemSettings().maxCount(64)));
    public static final Item AZTEC_WARRIOR_SPAWN_EGG = registerItem("aztec_warrior_spawn_egg", new SpawnEggItem(EntityRegistry.AZTEC_WARRIOR_ENTITY, 0xD5E2D1, 0x5A956A, new FabricItemSettings().maxCount(64)));
    public static final Item EAGLE_WARRIOR_SPAWN_EGG = registerItem("eagle_warrior_spawn_egg", new SpawnEggItem(EntityRegistry.EAGLE_WARRIOR_ENTITY, 0xDBDAD7, 0xF6C456, new FabricItemSettings().maxCount(64)));
    public static final Item JAGUAR_WARRIOR_SPAWN_EGG = registerItem("jaguar_warrior_spawn_egg", new SpawnEggItem(EntityRegistry.JAGUAR_WARRIOR_ENTITY, 0xD8AE56, 0x5D411D, new FabricItemSettings().maxCount(64)));
    public static final Item SET_ANIMAL_SPAWN_EGG = registerItem("seth_spawn_egg", new SpawnEggItem(EntityRegistry.SET_ANIMAL_ENTITY, 0x554A49, 0xF4B940, new FabricItemSettings().maxCount(64)));
    public static final Item SEA_SERPENT_SPAWN_EGG = registerItem("sea_serpent_spawn_egg", new SpawnEggItem(EntityRegistry.SEA_SERPENT_ENTITY, 0x607D96, 0xE6F9DF, new FabricItemSettings().maxCount(64)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(ValorMobs.MOD_ID, name), item);
    }

    public static void initialize() {
        ValorMobs.LOGGER.info("Registering items for " + ValorMobs.MOD_NAME);
    }
}
