package kiraririria.aprilmod.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public abstract class CoreClassTransformer implements IClassTransformer
{
    public static boolean obfuscated = false;

    public static boolean checkName(String name, String notch, String mcp)
    {
        if (name.equals(mcp) || name.equals(notch))
        {
            obfuscated = name.equals(notch);

            return true;
        }

        return false;
    }

    public static String get(String notch, String mcp)
    {
        return obfuscated ? notch : mcp;
    }
}