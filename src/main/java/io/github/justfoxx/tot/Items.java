package io.github.justfoxx.tot;

import io.github.justfoxx.tot.items.TotItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final Item TOT_ITEM = new TotItem();
    public static void init() {
        Registry.register(Registry.ITEM, Global.id("tot_item"), TOT_ITEM);
    }
}
