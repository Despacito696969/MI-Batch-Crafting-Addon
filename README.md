# MI-Batch-Crafting-Addon
Adds a component that allows you to do up to 64 recipes in parallel. Component can be applied to only some machines using KubeJS.

Right now only for 1.20.1

## KubeJS
To use KubeJS integration use startup event `MIBatchCrafting.modifyMachines(e => {...})`.

Internally there is a set of all the machines and a flag that says if the list is a blacklist or a whitelist (by default it's blacklist).

```java
List<String> getList()

boolean getIsWhitelisted()

void setIsWhitelisted(boolean isWhitelisted)

boolean removeFromList(String elem)

boolean addToList(String elem)

boolean doesListContain(String elem)
```

You need to add to the list the block entity id. If you want for example to add electric compressor to the list, you need to add `modern_industrialization:electric_compressor`
