package org.despacito696969.mi_batch_crafting_addon.mixin;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.multiblocks.AbstractCraftingMultiblockBlockEntity;
import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import aztech.modern_industrialization.machines.multiblocks.ShapeTemplate;
import org.despacito696969.mi_batch_crafting_addon.BatchComponentRegistry;
import org.despacito696969.mi_batch_crafting_addon.BatchSelection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractCraftingMultiblockBlockEntity.class)
public abstract class AbstractCraftingMultiblockBlockEntityMixin extends MachineBlockEntity {
    public AbstractCraftingMultiblockBlockEntityMixin(
            BEP bep,
            MachineGuiParameters guiParams,
            OrientationComponent.Params orientationParams
    ) {
        super(bep, guiParams, orientationParams);
    }

    @Shadow(remap = false) public abstract CrafterComponent getCrafterComponent();

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void addBatchComponentMixin(
            BEP bep, String name, OrientationComponent.Params orientationParams, ShapeTemplate[] shapeTemplates,
            CallbackInfo ci
    ) {
        // I tried to add these into the block entity constructors when blocks are registered, but I failed :(
        if (BatchComponentRegistry.allowed(bep)) {
            registerGuiComponent(new BatchSelection.Server((BatchSelection.BatchCrafterComponent) getCrafterComponent()));
            ((BatchSelection.BatchCrafterComponent) getCrafterComponent()).MIBatchCraftingAddon$setBatchingState(true);
        }
    }
}
