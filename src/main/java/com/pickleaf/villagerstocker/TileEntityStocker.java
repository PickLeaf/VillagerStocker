package com.pickleaf.villagerstocker;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class TileEntityStocker extends TileEntity implements IInventory {
    private ItemStack inventory = null;

    public TileEntityStocker() {
        super();
    }

    public TileEntityStocker(int metadata) {
        super();
        if(metadata==1){
            this.inventory = new ItemStack(Items.emerald);
        }
    }

    @Override
    public void updateEntity() {
        World world = this.worldObj;
        if (world.isRemote) return;

        if(world.getWorldTime()%20!=0) return;
        if(inventory==null) return;

        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;
        if (world.isBlockIndirectlyGettingPowered(x, y, z)) return;

        double radius = Config.getStockerWorkRadius();
        List<EntityVillager> villages = world.getEntitiesWithinAABB(
                EntityVillager.class, AxisAlignedBB.getBoundingBox(
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
            NBTTagList recipes = nbt.getCompoundTag("Offers").getTagList("Recipes", 10);

            for (int i = 0; i < 10; i++) {
                NBTTagCompound trades = recipes.getCompoundTagAt(i);
                if(!trades.hasKey("maxUses")) continue;
                if(trades.getInteger("uses") < trades.getInteger("maxUses")) continue;

                trades.setInteger("uses", 0);

                if ((new Random()).nextFloat() < Config.getStockerConsumeChance()) {
                    this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
                    this.inventory = null;
                }
            }

            villager.readEntityFromNBT(nbt);
        }
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagCompound item = compound.getCompoundTag("Item");
        this.inventory = ItemStack.loadItemStackFromNBT(item);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagCompound item = new NBTTagCompound();
        if(this.inventory!=null) this.inventory.writeToNBT(item);
        compound.setTag("Item", item);
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory;
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        if (this.inventory != null) {
            ItemStack itemstack;
            itemstack = this.inventory;
            this.inventory = null;
            this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return this.inventory;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return Items.emerald==itemStack.getItem();
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        itemStack.stackSize = this.getInventoryStackLimit();
        this.inventory = itemStack;
        this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory() { }

    @Override
    public void closeInventory() { }
}
