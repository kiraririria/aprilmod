package kiraririria.aprilmod.core;

import kiraririria.aprilmod.AprilMod;
import kiraririria.aprilmod.core.transformers.GuiMainMenuTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.Iterator;

public class AprilCoreClassTransformer extends CoreClassTransformer
{
    private GuiMainMenuTransformer guiMainMenuTransformer = new GuiMainMenuTransformer();

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