package io.github.justfoxx.tot;

import io.github.justfoxx.tot.items.TotItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final Item TOT_ITEM = new TotItem();
    public static void init() {
        if(Configs.totItemConfig.data.enabled) Registry.register(Registry.ITEM, PreMain.g.id("tot_item"), TOT_ITEM);
    }
}
