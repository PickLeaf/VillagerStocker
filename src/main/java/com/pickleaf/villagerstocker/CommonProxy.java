package com.pickleaf.villagerstocker;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CommonProxy {

    private static Block blockStocker;

    public void preInit(FMLPreInitializationEvent event){
        blockStocker = new BlockStocker(Material.rock).setCreativeTab(CreativeTabs.tabRedstone);
        GameRegistry.registerBlock(blockStocker, ItemBlockStocker.class, blockStocker.getUnlocalizedName());
    }

    public void init(FMLInitializationEvent event){
        GameRegistry.addRecipe(new ItemStack(blockStocker, 1, 1), "CCC","CCC","CEC", 'C',Blocks.cobblestone, 'E',Blocks.emerald_block);
    }

}
