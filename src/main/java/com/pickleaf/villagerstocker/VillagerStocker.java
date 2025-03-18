package com.pickleaf.villagerstocker;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;

@Mod(
        modid = VillagerStocker.MODID,
        version = VillagerStocker.VERSION,
        acceptedMinecraftVersions = "1.7.10"
)
public class VillagerStocker {
    public static final String MODID = "villagerstocker";
    public static final String VERSION = "1.3";

    @Mod.Instance(VillagerStocker.MODID)
    public static VillagerStocker instance;

    @SidedProxy(clientSide = "com.pickleaf.villagerstocker.ClientProxy",
            serverSide = "com.pickleaf.villagerstocker.CommonProxy")
    public static CommonProxy proxy;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
