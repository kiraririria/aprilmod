package kiraririria.aprilmod.core.transformers;

import kiraririria.aprilmod.AprilMod;
import kiraririria.aprilmod.core.ClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class GuiMainMenuTransformer extends ClassTransformer
{
    @Override
    public void process(String name, ClassNode node)
    {
        for (MethodNode method : node.methods)
        {
            if (method.name.equals("<init>") && method.desc.equals("()V"))
            {
                this.processInit(method);
            }
        }
    }
    public void processInit(MethodNode method)
    {
        InsnList list = new InsnList();
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "kiraririria/aprilmod/api/AprilAPI", "finishMap", "()V", false));
        method.instructions.insert(list);
        AprilMod.log("[>---GuiMainMenu <init> was changed successfully---<]");
    }
}
