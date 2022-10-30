package io.github.justfoxx.tot;

import io.github.justfoxx.tot.items.TotItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class Items {
    public static Item TOT_ITEM = new TotItem();
    public static void init() {
        Registry.register(Registry.ITEM, Global.id("tot_item"), TOT_ITEM);
    }
}
