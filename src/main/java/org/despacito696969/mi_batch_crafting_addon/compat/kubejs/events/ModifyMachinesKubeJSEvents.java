package org.despacito696969.mi_batch_crafting_addon.compat.kubejs.events;// package org.despacito696969.mi_batch_crafting_addon.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventJS;
import org.despacito696969.mi_batch_crafting_addon.BatchComponentRegistry;
import org.despacito696969.mi_batch_crafting_addon.BatchSelection;

import java.util.Collection;
import java.util.List;

public class ModifyMachinesKubeJSEvents extends EventJS {
    public List<String> getList() {
        return BatchComponentRegistry.types.stream().toList();
    }

    public boolean getIsWhitelisted() {
        return BatchComponentRegistry.isWhitelisted;
    }

    public void setIsWhitelisted(boolean isWhitelisted) {
        BatchComponentRegistry.isWhitelisted = isWhitelisted;
    }

    public boolean removeFromList(String elem) {
        if (elem == null) {
            return false;
        }
        return BatchComponentRegistry.types.remove(elem);
    }

    public boolean addToList(String elem) {
        if (elem == null) {
            return false;
        }
        return BatchComponentRegistry.types.add(elem);
    }

    public boolean doesListContain(String elem) {
        if (elem == null) {
            return false;
        }
        return BatchComponentRegistry.types.contains(elem);
    }
}
