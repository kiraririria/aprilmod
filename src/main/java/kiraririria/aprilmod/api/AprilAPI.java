package kiraririria.aprilmod.api;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AprilAPI
{
    public static String MAP_NAME = "Апрель";
    public static String APP_DATA_PATH = System.getenv("APPDATA");
    public static String FILE_DIRECTORY = AprilAPI.APP_DATA_PATH + "\\.firstapril";
    public static String FILE_NAME = "finished";
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
    }

    /**
     * [API Scripting]
     * //Example:
     * function finishMap(player)
     * {
     * var AprilAPI = Java.type("kiraririria.aprilmod.api.AprilAPI");
     * AprilAPI.finish(player.getMCEntity(),"Finish")
     * }
     */
    public static void finish(EntityPlayerMP player, String message)
    {
        player.connection.disconnect(new TextComponentString(message));
        createFinishedFile();
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
        isaveformat.flushCache();
        List<WorldSummary> filtered = list.stream().filter(worldSummary -> worldSummary.getDisplayName().contains(MAP_NAME)).collect(Collectors.toList());
        for (WorldSummary worldSummary : filtered)
        {
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
    }

    public static void deleteFinishedFile()
    {
        File file = new File(FILE_DIRECTORY, FILE_NAME);
        if (file.exists())
        {
            file.delete();
        }
    }
}
