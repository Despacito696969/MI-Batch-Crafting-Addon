package org.despacito696969.mi_batch_crafting_addon;

import aztech.modern_industrialization.machines.BEP;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.Set;

public class BatchComponentRegistry {
    public static boolean hasBeenEnabled = false;
    public static boolean isWhitelisted = false;
    public static Set<String> types = new HashSet<>();

    public static boolean allowed(BEP bep) {
        return types.contains(BlockEntityType.getKey(bep.type()).toString()) == isWhitelisted;
    }
}
