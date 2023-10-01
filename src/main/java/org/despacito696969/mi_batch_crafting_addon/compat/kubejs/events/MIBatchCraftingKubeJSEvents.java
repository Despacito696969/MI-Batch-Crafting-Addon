package org.despacito696969.mi_batch_crafting_addon.compat.kubejs.events;// package org.despacito696969.mi_batch_crafting_addon.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface MIBatchCraftingKubeJSEvents {
    EventGroup EVENT_GROUP = EventGroup.of("MIBatchCrafting");

    EventHandler MODIFY_MACHINES = EVENT_GROUP.startup("modifyMachines", () -> ModifyMachinesKubeJSEvents.class);
}
