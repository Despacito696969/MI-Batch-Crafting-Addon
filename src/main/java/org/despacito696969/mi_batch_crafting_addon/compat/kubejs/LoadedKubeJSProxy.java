package org.despacito696969.mi_batch_crafting_addon.compat.kubejs;

import org.despacito696969.mi_batch_crafting_addon.compat.kubejs.events.MIBatchCraftingKubeJSEvents;
import org.despacito696969.mi_batch_crafting_addon.compat.kubejs.events.ModifyMachinesKubeJSEvents;

public class LoadedKubeJSProxy extends KubeJSProxy {
    @Override
    public void fireBatchCraftingModifyMachines() {
        MIBatchCraftingKubeJSEvents.MODIFY_MACHINES.post(new ModifyMachinesKubeJSEvents());
    }
}
