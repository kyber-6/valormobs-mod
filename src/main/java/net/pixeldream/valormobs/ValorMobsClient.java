package net.pixeldream.valormobs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.pixeldream.valormobs.entity.client.renderer.*;
import net.pixeldream.valormobs.registry.EntityRegistry;

public class ValorMobsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegistry.SKULLHAVEN_SPIRIT_ENTITY, AbstractSpiritRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CRAB_ENTITY, CrabRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.EXECUTIONER_ENTITY, ExecutionerRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MUMMY_ENTITY, MummyRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.AZTEC_WARRIOR_ENTITY, AztecWarriorRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.EAGLE_WARRIOR_ENTITY, EagleWarriorRenderer::new);
    }
}
