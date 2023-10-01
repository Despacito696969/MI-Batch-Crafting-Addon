package org.despacito696969.mi_batch_crafting_addon.mixin;

import aztech.modern_industrialization.ModernIndustrialization;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.ambient.Bat;
import org.despacito696969.mi_batch_crafting_addon.BatchSelection;
import org.despacito696969.mi_batch_crafting_addon.compat.kubejs.KubeJSProxy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModernIndustrialization.class)
public class ModernIndustrializationMixin {
    @Inject(method = "setupPackets", at = @At("TAIL"), remap = false)
    private static void setupPacketsMixin(CallbackInfo ci) {
        ServerPlayNetworking.registerGlobalReceiver(BatchSelection.PacketsC2S.CHANGE_BATCH_SIZE, BatchSelection.PacketsC2S.ON_CHANGE_BATCH_SIZE);
    }

    @Inject(method = "initialize", at = @At("HEAD"), remap = false)
    private static void initializeMixin(CallbackInfo ci) {
        // Here we add KubeJS
        KubeJSProxy.instance.fireBatchCraftingModifyMachines();
    }
}
