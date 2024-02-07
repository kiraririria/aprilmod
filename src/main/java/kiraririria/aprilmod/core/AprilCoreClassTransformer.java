package kiraririria.aprilmod.core;

import kiraririria.aprilmod.AprilMod;
import kiraririria.aprilmod.core.transformers.GuiMainMenuTransformer;

public class AprilCoreClassTransformer extends CoreClassTransformer
{
    private final GuiMainMenuTransformer guiMainMenuTransformer = new GuiMainMenuTransformer();

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if (checkName(name, "blr", "net.minecraft.client.gui.GuiMainMenu"))
        {
            AprilMod.log("<]---MainMenu---[>");
            return this.guiMainMenuTransformer.transform(name, basicClass);
        }
        return basicClass;
    }
}