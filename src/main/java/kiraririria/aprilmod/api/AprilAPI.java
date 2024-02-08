package kiraririria.aprilmod.api;

import kiraririria.aprilmod.AprilMod;
import kiraririria.aprilmod.core.CoreClassTransformer;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AprilAPI
{
    public static String MAP_NAME = "Апрель";
    public static String APP_DATA_PATH = System.getenv("APPDATA");
    public static String FILE_DIRECTORY = AprilAPI.APP_DATA_PATH + "\\.firstapril";
    public static String FILE_NAME = "finished";
    public static String FILE_NAME2 = "authors";
    public static int BLUE_SCREEN_WAITING = 4000;
    public static int BLUE_SCREEN_PRE_WAITING = 4000;
    public static String BLUE_SCREEN_LOGO = "Blue Screen of Death";
    public static String BLUE_SCREEN_HTML = "<html>Your PC ran into a problem and needs to restart.<br>We're just collecting some error info, and then we'll restart for you.<br>А если по-русски, то ты первое апреля поздравляю<br>подпишись на бусти пж..<html>";

    /**
     * [Called by ASM]
     * Change MainMenu
     * Client Side only implicitly
     */
    public static void finishMap()
    {
        if (isFinished())
        {
            new Thread(() ->
            {
                try
                {
                    Thread.sleep(2000);
                    if (deleteMapWorld())
                    {
                        deleteFinishedFile();
                        new Thread(() ->
                        {
                            try
                            {
                                Thread.sleep(BLUE_SCREEN_PRE_WAITING);
                                showBlueScreen();
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * [API Scripting]
     * //Example:
     * function interact(e)
     * {
     * var AprilAPI = Java.type("kiraririria.aprilmod.api.AprilAPI");
     * AprilAPI.finish(e.player.getMCEntity(),"Finish")
     * }
     */
    public static void finish(EntityPlayerMP player, String message)
    {
        player.connection.disconnect(new TextComponentString(message));
        createFinishedFile();
    }

    public static void addAuthorButton(GuiMainMenu self, int p, int p1) throws NoSuchFieldException, IllegalAccessException
    {
        if (!showAuthors())
        {
            return;
        }
        Field field = GuiScreen.class.getDeclaredField(CoreClassTransformer.get("field_146292_n", "buttonList"));
        field.setAccessible(true);
        List<GuiButton> list = (List<GuiButton>) field.get(self);
        list.add(new GuiButton(93, self.width / 2 + 2, self.height / 3 + 5, 135, 20, "Канал Автора Апрельской Карты!"));
        list.add(new GuiButton(94, self.width / 2 - 135 - 2, self.height / 3 + 5, 135, 20, "Группа Автора Апрельской Карты!"));
        field.setAccessible(false);
    }

    public static void handleAuthorButton(GuiButton button) throws URISyntaxException
    {
        if (!showAuthors())
        {
            return;
        }
        if (button.id == 93)
        {
            openWebLink(URI.create("https://www.youtube.com/@Creman"));
        }
        if (button.id == 94)
        {
            openWebLink(URI.create("https://vk.com/cremanmaps"));
        }
    }

    public static void openWebLink(URI url)
    {
        try
        {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke((Object) null);
            oclass.getMethod("browse", URI.class).invoke(object, url);
        }
        catch (Throwable throwable1)
        {
            Throwable throwable = throwable1.getCause();
            AprilMod.log("Couldn't open link: " + (Object) (throwable == null ? "<UNKNOWN>" : throwable.getMessage()));
        }
    }

    public static boolean deleteMapWorld()
    {
        Minecraft mc = Minecraft.getMinecraft();
        ISaveFormat isaveformat = mc.getSaveLoader();
        List<WorldSummary> list;
        boolean result = false;
        try
        {
            list = isaveformat.getSaveList();
        }
        catch (AnvilConverterException anvilconverterexception)
        {
            return result;
        }
        Collections.sort(list);
        List<WorldSummary> filtered = list.stream().filter(worldSummary -> worldSummary.getDisplayName().contains(MAP_NAME)).collect(Collectors.toList());
        for (WorldSummary worldSummary : filtered)
        {
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory(worldSummary.getFileName());
            result = true;
        }
        return result;
    }

    public static void showBlueScreen()
    {
        JFrame frame = new JFrame(BLUE_SCREEN_LOGO);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().toFront();
                e.getWindow().requestFocus();
            }
        });

        frame.setUndecorated(true);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLUE);

        JLabel smileLabel = new JLabel(":(");
        smileLabel.setForeground(Color.WHITE);
        smileLabel.setFont(new Font("Arial", Font.BOLD, 172));
        smileLabel.setBorder(new EmptyBorder(120, 150, 0, 0));
        frame.add(smileLabel, BorderLayout.NORTH);

        JLabel infoLabel = new JLabel(BLUE_SCREEN_HTML);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setVerticalAlignment(SwingConstants.CENTER);
        frame.add(infoLabel, BorderLayout.CENTER);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.toFront();
        frame.requestFocus();

        try
        {
            Robot robot = new Robot();
            robot.delay(BLUE_SCREEN_WAITING);
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }

        frame.dispose();
    }

    public static boolean isFinished()
    {
        return (new File(FILE_DIRECTORY, FILE_NAME)).exists();
    }

    public static boolean showAuthors()
    {
        return (new File(FILE_DIRECTORY, FILE_NAME2)).exists();
    }

    public static void createFinishedFile()
    {
        File directory = new File(FILE_DIRECTORY);
        if (!directory.exists() && !directory.mkdirs())
        {
            return;
        }

        File file = new File(directory, FILE_NAME);
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        File file2 = new File(directory, FILE_NAME2);
        try
        {
            file2.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void deleteFinishedFile()
    {
        File file = new File(FILE_DIRECTORY, FILE_NAME);
        if (file.exists())
        {
            file.delete();
        }
    }

    public static void deleteAuthorFile()
    {
        File file = new File(FILE_DIRECTORY, FILE_NAME2);
        if (file.exists())
        {
            file.delete();
        }
    }

}
