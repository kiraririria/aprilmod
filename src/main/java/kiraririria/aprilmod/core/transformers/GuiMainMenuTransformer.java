package kiraririria.aprilmod.core.transformers;

import kiraririria.aprilmod.AprilMod;
import kiraririria.aprilmod.core.ClassTransformer;
import kiraririria.aprilmod.core.CoreClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.Iterator;

public class GuiMainMenuTransformer extends ClassTransformer
{
    @Override
    public void process(String name, ClassNode node)
    {
        Iterator<MethodNode> iterator = node.methods.iterator();
        while (iterator.hasNext())
        {
            MethodNode method = iterator.next();
            String methodName = this.checkName(method, "<init>", "()V", "<init>", "()V");
            if (methodName != null)
            {
                this.processInit(method);
            }
            methodName = this.checkName(method, "b", "(II)V", "addSingleplayerMultiplayerButtons", "(II)V");
            if (methodName != null)
            {
                this.processAddSMB(method);
            }
            methodName = this.checkName(method, "a", "(Lbja;)V", "actionPerformed", "(Lnet/minecraft/client/gui/GuiButton;)V");
            if (methodName != null)
            {
                this.processAP(method);
            }
        }

    }

    public void processAP(MethodNode method)
    {
        Iterator<AbstractInsnNode> it = method.instructions.iterator();
        AbstractInsnNode target = null;
        while (it.hasNext())
        {
            AbstractInsnNode node = it.next();
            if (node.getOpcode() == Opcodes.RETURN)
            {
                target = node;
                break;
            }
        }

        final String desc = CoreClassTransformer.get("Lbja;", "Lnet/minecraft/client/gui/GuiButton;");
        InsnList list = new InsnList();
        list.add(new VarInsnNode(Opcodes.ALOAD, 1));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "kiraririria/aprilmod/api/AprilAPI", "handleAuthorButton", "(" + desc + ")V", false));
        method.instructions.insertBefore(target, list);
        AprilMod.log("[>---GuiMainMenu AP was changed successfully---<]");
    }

    public void processAddSMB(MethodNode method)
    {
        Iterator<AbstractInsnNode> it = method.instructions.iterator();
        AbstractInsnNode target = null;
        while (it.hasNext())
        {
            AbstractInsnNode node = it.next();
            if (node.getOpcode() == Opcodes.RETURN)
            {
                target = node;
                break;
            }
        }

        final String desc = CoreClassTransformer.get("Lblr;II", "Lnet/minecraft/client/gui/GuiMainMenu;II");
        InsnList list = new InsnList();
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new VarInsnNode(Opcodes.ILOAD, 1));
        list.add(new VarInsnNode(Opcodes.ILOAD, 2));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "kiraririria/aprilmod/api/AprilAPI", "addAuthorButton", "(" + desc + ")V", false));
        method.instructions.insertBefore(target, list);
        AprilMod.log("[>---GuiMainMenu AddSMB was changed successfully---<]");
    }

    public void processInit(MethodNode method)
    {
        InsnList list = new InsnList();
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "kiraririria/aprilmod/api/AprilAPI", "finishMap", "()V", false));
        method.instructions.insert(list);
        AprilMod.log("[>---GuiMainMenu <init> was changed successfully---<]");
    }
}
