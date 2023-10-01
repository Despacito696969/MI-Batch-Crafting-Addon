// package org.despacito696969.mi_batch_crafting_addon.mixin;
/*
import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.AbstractCraftingMachineBlockEntity;
import aztech.modern_industrialization.machines.init.MachineRegistrationHelper;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.despacito696969.mi_batch_crafting_addon.BatchComponentRegistry;
import org.despacito696969.mi_batch_crafting_addon.BatchSelection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Consumer;
import java.util.function.Function;

@Mixin(MachineRegistrationHelper.class)
public abstract class MachineRegistrationHelperMixin {
    @ModifyArgs(method = "registerMachine", remap = false, at = @At(target = "Ljava/util/function/Function;apply(Ljava/lang/Object;)Ljava/lang/Object;", remap = false))
    private static void apply(Args args) {

    }
    private static void registerMachineMixin(
            String englishName, String id, Function<BEP, MachineBlockEntity> factory,
            Consumer<BlockEntityType<?>>[] extraRegistrators, CallbackInfoReturnable<BlockEntityType<?>> cir
    ) {
        System.out.println("Got a machine " + id);
        if (BatchComponentRegistry.types.contains(id)) {
            System.out.println("Found a machine! " + id);
            var factory_old = factory;
            factory = bep -> {
                var result = factory_old.apply(bep);
                if (result instanceof AbstractCraftingMachineBlockEntity craftingMachine) {
                    craftingMachine.guiComponents.add(new BatchSelection.Server((BatchSelection.BatchCrafterComponent)craftingMachine.getCrafterComponent()));
                }
                return result;
            };
        }
    }
}
*/