package kiraririria.aprilmod.core.transformers;

import kiraririria.aprilmod.AprilMod;
import kiraririria.aprilmod.core.ClassTransformer;
import kiraririria.aprilmod.core.CoreClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class GuiMainMenuTransformer extends ClassTransformer
{
    @Override
    public void process(String name, ClassNode node)
    {
        for (MethodNode method : node.methods)
        {
            if (method.name.equals("drawScreen") && method.desc.equals("(IIF)V"))
            {
                this.processDrawScreen(method);
            }
            if (method.name.equals("<init>") && method.desc.equals("()V"))
            {
                this.processInit(method);
            }
        }
    }

    public void processDrawScreen(MethodNode method)
    {
        final String desc = CoreClassTransformer.get("blr;IIF", "Lnet/minecraft/client/gui/GuiMainMenu;IIF");
        InsnList list = new InsnList();
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new VarInsnNode(Opcodes.ILOAD, 1));
        list.add(new VarInsnNode(Opcodes.ILOAD, 2));
        list.add(new VarInsnNode(Opcodes.FLOAD, 3));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "kiraririria/aprilmod/api/AprilAPI", "renderHerobrine", "(" + desc + ")V", false));
        method.instructions.insert(list);
        AprilMod.log("[>---GuiMainMenu drawScreen was changed successfully---<]");
    }
    public void processInit(MethodNode method)
    {
        InsnList list = new InsnList();
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "kiraririria/aprilmod/api/AprilAPI", "finishMap", "()V", false));
        method.instructions.insert(list);
        AprilMod.log("[>---GuiMainMenu <init> was changed successfully---<]");
    }
}
