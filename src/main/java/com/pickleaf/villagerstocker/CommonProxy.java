package com.pickleaf.villagerstocker;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;

public class CommonProxy {

    private static Block blockStocker;

    public void preInit(FMLPreInitializationEvent event){
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
        double stockerWorkRadius = cfg.getFloat("villagerStockerWorkRadius", Configuration.CATEGORY_GENERAL, 2, 0, 128, "村民补货器的工作范围，是以补货器为中心的正方体");
        float stockerConsumeChance = cfg.getFloat("villagerStockerConsumeEmerableChance", Configuration.CATEGORY_GENERAL, (float)0.1, 0, 1, "村民补货器每次补货后消耗掉绿宝石的概率");
        if(cfg.hasChanged()) cfg.save();
        Config.setStockerWorkRadius(stockerWorkRadius);
        Config.setStockerConsumeChance(stockerConsumeChance);

        blockStocker = new BlockStocker().setCreativeTab(CreativeTabs.tabRedstone);
        TileEntity.addMapping(TileEntityStocker.class, "VillagerStocker:Stocker");
        GameRegistry.registerBlock(blockStocker, ItemBlockStocker.class, blockStocker.getUnlocalizedName());
    }

    public void init(FMLInitializationEvent event){
        GameRegistry.addRecipe(new ItemStack(blockStocker, 1, 1), "CEC","CCC","CCC", 'C',Blocks.cobblestone, 'E',Blocks.emerald_block);
    }
}
