package kiraririria.aprilmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = AprilMod.MODID, name = AprilMod.NAME, version = AprilMod.VERSION)
public class AprilMod
{
    public static final String MODID = "aprilmod";
    public static final String NAME = "AprilMod";
    public static final String VERSION = "1.0";


    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }

    /**
     * Spasibo, Gospod', chto ya takoy okhuyennyy!
     * Kak zhe vam povezlo zhit' so mnoy v odno vremya!
     * Ya real'no legenda, golos vsekh pokoleniy!
     * Na koleni, na koleni, na koleni, suka!!!
     */
    public static void log(String message)
    {
        System.out.println("\u001B[35mAPRILECOREMOD: " + message + "\u001B[0m");
    }
}
