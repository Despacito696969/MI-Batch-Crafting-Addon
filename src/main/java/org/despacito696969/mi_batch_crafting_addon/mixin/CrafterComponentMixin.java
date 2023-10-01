package org.despacito696969.mi_batch_crafting_addon.mixin;

import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import net.minecraft.nbt.CompoundTag;
import org.despacito696969.mi_batch_crafting_addon.BatchSelection;
import org.despacito696969.mi_batch_crafting_addon.MIBatchCraftingAddon;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrafterComponent.class)
public class CrafterComponentMixin implements BatchSelection.BatchCrafterComponent {
    @Unique
    public int MIBatchCraftingAddon$desiredBatchSize = 1;
    @Unique
    public int MIBatchCraftingAddon$currentBatchSize = 1;

    @Unique
    public boolean MIBatchCraftingAddon$isBatchingEnabled = false;

    @Unique
    private static final String CURRENT_BATCH_SIZE_NBT = "currentBatchSize";

    @Unique
    private static final String DESIRED_BATCH_SIZE_NBT = "desiredBatchSize";

    @Override
    public int MIBatchCraftingAddon$getDesiredRecipeBatching() {
        return MIBatchCraftingAddon$desiredBatchSize;
    }

    @Override
    public void MIBatchCraftingAddon$setDesiredRecipeBatching(int batchSize) {
        MIBatchCraftingAddon$desiredBatchSize = batchSize;
    }

    @Override
    public void MIBatchCraftingAddon$setBatchingState(boolean batchingState) {
        MIBatchCraftingAddon$isBatchingEnabled = batchingState;
    }

    @Shadow(remap = false) private int efficiencyTicks;

    @Inject(method = "updateActiveRecipe", at = @At(value = "HEAD", remap = false), cancellable = true, remap = false)
    private void updateActiveRecipeMixin(CallbackInfoReturnable<Boolean> ci) {
        // Sanity checks, these ifs should never succeed
        if (MIBatchCraftingAddon$desiredBatchSize < 1) {
            MIBatchCraftingAddon$desiredBatchSize = 1;
        }
        if (MIBatchCraftingAddon$desiredBatchSize > MIBatchCraftingAddon.BATCH_MAX) {
            MIBatchCraftingAddon$desiredBatchSize = MIBatchCraftingAddon.BATCH_MAX;
        }
        if (MIBatchCraftingAddon$isBatchingEnabled) {
            MIBatchCraftingAddon$desiredBatchSize = 1;
        }
        if (efficiencyTicks == 0) {
            MIBatchCraftingAddon$currentBatchSize = MIBatchCraftingAddon$desiredBatchSize;
        }

        if (MIBatchCraftingAddon$currentBatchSize != MIBatchCraftingAddon$desiredBatchSize) {
            ci.setReturnValue(false);
        }
    }

    @Redirect(method = "updateActiveRecipe", at = @At(value = "INVOKE", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe;getTotalEu()J"), remap = false)
    private long getTotalEuRedirect(MachineRecipe instance) {
        return instance.getTotalEu() * MIBatchCraftingAddon$currentBatchSize;
    }

    @ModifyArg(method = "updateActiveRecipe", at = @At(value = "INVOKE", remap = false, target = "Laztech/modern_industrialization/machines/components/CrafterComponent;getRecipeMaxEu(JJI)J"), index = 0, remap = false)
    private long recipeEuModifier(long eu) {
        return eu * MIBatchCraftingAddon$currentBatchSize;
    }

    @Inject(method = "writeNbt", at = @At("TAIL"), remap = false)
    public void writeNbtMixin(CompoundTag tag, CallbackInfo ci) {
        tag.putInt(CURRENT_BATCH_SIZE_NBT, this.MIBatchCraftingAddon$currentBatchSize);
        if (this.MIBatchCraftingAddon$isBatchingEnabled) {
            // We don't need to save desiredBatchSize == 1
            tag.putInt(DESIRED_BATCH_SIZE_NBT, this.MIBatchCraftingAddon$desiredBatchSize);
        }
    }

    @Inject(method = "readNbt", at = @At("TAIL"), remap = false)
    public void readNbtMixin(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(CURRENT_BATCH_SIZE_NBT)) {
            MIBatchCraftingAddon$currentBatchSize = tag.getInt(CURRENT_BATCH_SIZE_NBT);
        }
        if (tag.contains(DESIRED_BATCH_SIZE_NBT)) {
            MIBatchCraftingAddon$desiredBatchSize = tag.getInt(DESIRED_BATCH_SIZE_NBT);
        }
    }


    @Redirect(method = "takeItemInputs", at = @At(value = "FIELD", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe$ItemInput;amount:I", opcode = Opcodes.GETFIELD), remap = false)
    private int itemInputAmountModifier(MachineRecipe.ItemInput input) {
        return input.amount * MIBatchCraftingAddon$currentBatchSize;
    }

    @Redirect(method = "putItemOutputs", at = @At(value = "FIELD", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe$ItemOutput;amount:I", opcode = Opcodes.GETFIELD), remap = false)
    private int itemOutputAmountModifier(MachineRecipe.ItemOutput output) {
        return output.amount * MIBatchCraftingAddon$currentBatchSize;
    }

    @Redirect(method = "takeFluidInputs", at = @At(value = "FIELD", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe$FluidInput;amount:J", opcode = Opcodes.GETFIELD), remap = false)
    private long fluidInputAmountModifier(MachineRecipe.FluidInput input) {
        return input.amount * MIBatchCraftingAddon$currentBatchSize;
    }

    @Redirect(method = "putFluidOutputs", at = @At(value = "FIELD", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe$FluidOutput;amount:J", opcode = Opcodes.GETFIELD), remap = false)
    private long fluidOutputAmountModifier(MachineRecipe.FluidOutput output) {
        return output.amount * MIBatchCraftingAddon$currentBatchSize;
    }

    @Redirect(method = "getRecipeMaxEfficiencyTicks", at = @At(value = "INVOKE", remap = false, target = "Laztech/modern_industrialization/machines/recipe/MachineRecipe;getTotalEu()J"), remap = false)
    private long getTotalEuMixin(MachineRecipe recipe) {
        return recipe.getTotalEu() * MIBatchCraftingAddon$currentBatchSize;
    }
}
