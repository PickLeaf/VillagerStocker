package com.pickleaf.villagerstocker;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockStocker extends ItemBlockWithMetadata {

    public ItemBlockStocker(Block block) {
        super(block, block);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack){
        return this.getUnlocalizedName() + "_" + itemStack.getItemDamage();
    }
}
