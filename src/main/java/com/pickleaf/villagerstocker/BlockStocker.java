package com.pickleaf.villagerstocker;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public final class BlockStocker extends Block {

    private double radius;
    private float chance;
    private Block chargeBlock;
    private static IIcon[] icons = new IIcon[3];

    public BlockStocker(double radius, float chance, String block) {
        super(Material.rock);
        this.radius = radius;
        this.chance = chance;
        this.chargeBlock = Block.getBlockFromName(block);
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
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        if (world.isRemote) return;
        switch (world.getBlockMetadata(x, y, z)) {
            case 0: {
                if (!Block.isEqualTo(world.getBlock(x, y + 1, z), this.chargeBlock)) break;
                world.setBlockMetadataWithNotify(x, y, z, 1, 0);
                world.setBlock(x, y + 1, z, Blocks.air);
                break;
            }
            case 1: {
                if (!world.isBlockIndirectlyGettingPowered(x, y, z)) return;
                List<EntityVillager> villages = world.getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getBoundingBox(
                        x - radius
                        , y - radius
                        , z - radius
                        , x + radius +1
                        , y + radius +1
                        , z + radius +1
                ));

                for (EntityVillager villager : villages) {
                    NBTTagCompound nbt = new NBTTagCompound();
                    villager.writeEntityToNBT(nbt);

                    for (int i = 0; i < 10; i++) {
                        NBTTagCompound trades = nbt.getCompoundTag("Offers").getTagList("Recipes", 10).getCompoundTagAt(i);
                        if(!trades.hasKey("maxUses")) continue;
                        if(trades.getInteger("uses") < trades.getInteger("maxUses")) continue;

                        trades.setInteger("uses", 0);

                        Random rand = new Random();
                        if (rand.nextFloat() < chance)
                            world.setBlock(x,y,z,this,0,2);
                    }

                    villager.readEntityFromNBT(nbt);
                }
            }
        }
    }

}
