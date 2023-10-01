package org.despacito696969.mi_batch_crafting_addon.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import org.despacito696969.mi_batch_crafting_addon.compat.kubejs.events.MIBatchCraftingKubeJSEvents;

public class MIBatchCraftingAddonKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerEvents() {
        MIBatchCraftingKubeJSEvents.EVENT_GROUP.register();
    }

    @Override
    public void initStartup() {
        KubeJSProxy.instance = new LoadedKubeJSProxy();
    }
}
