package kiraririria.aprilmod.core;

import net.minecraftforge.fml.common.DummyModContainer;

public class AprilCoreModInfo extends DummyModContainer
{

    @Override
    public String getName()
    {
        return "AprilCoreMod";
    }

    @Override
    public String getModId()
    {
        return "april_core";
    }

    @Override
    public Object getMod()
    {
        return null;
    }

    @Override
    public String getVersion()
    {
        return "%VERSION%";
    }

}