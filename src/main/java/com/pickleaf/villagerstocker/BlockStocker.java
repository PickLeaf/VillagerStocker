package com.pickleaf.villagerstocker;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public final class BlockStocker extends Block {
    private static IIcon[] icons = new IIcon[3];

    public BlockStocker() {
        super(Material.rock);
        this.setHardness(2.5F);
        this.setResistance(5.0F);
        this.setBlockName("Stocker");
        this.setBlockTextureName("villagerstocker:stocker");
        this.setTickRandomly(false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        BlockStocker.icons[0] = iconRegister.registerIcon(this.getTextureName() + "_0");
        BlockStocker.icons[1] = iconRegister.registerIcon(this.getTextureName() + "_1");
        BlockStocker.icons[2] = iconRegister.registerIcon(this.getTextureName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if(side!=1)return icons[2];
        if (meta > 2) meta = 0;
        return icons[meta];
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));
    }

    @Override
    public int damageDropped(int oldMeta) {
        return oldMeta;
    }

    @Override
    public boolean hasComparatorInputOverride() { return true; }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int p_149736_5_) {
        return world.getBlockMetadata(x,y,z)==1 ? 15 : 0;
    }

    @Override
    public boolean hasTileEntity(int metadata) { return true; }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityStocker(metadata);
    }

}
