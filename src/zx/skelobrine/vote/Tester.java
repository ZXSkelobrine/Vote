package zx.skelobrine.vote;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("WeakerAccess")
public class Tester {
    static int interval;
    static Timer timer;
    static boolean xb = false;
    Main m = new Main();

    public static void timerT() {
        Main.tR = true;
        int delay = 1000;

        int period = 1000;
        timer = new Timer();
        interval = Main.configI;
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                //System.out.println(setInterval());
                System.out.println();
                setInterval();

            }
        }, delay, period);

    }

    @SuppressWarnings("UnusedReturnValue")
    private static int setInterval() {
        Main m = new Main();
        if (interval == 1) {
            timer.cancel();
            xb = (Main.voteY == Main.voteN);

            broad("Vote has ended", 0);
            if (Main.voteY >= Main.fthresh && Main.voteN <= Main.fthresh && !xb) {
                command();
            } else {
                broad("Vote has failed", 0);
            }

            if (xb) {
                broad(Main.configS[1], 0);
                if (chance(100)) {
                    broad("Vote will take effect!", 0);
                    command();
                } else {
                    broad("Vote will not take effect", 0);
                }
            }
            clear();
        }
        return --interval;
    }

    public static void command() {
        switch (Main.voteTop) {
            case 1:
                broad(Main.configS[2], 0);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "time set 0");
                break;
            case 2:
                broad(Main.configS[3], 0);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "time set 18000");
                break;
            case 3:
                if (Bukkit.getWorlds().get(0).hasStorm()) {
                    broad(Main.configS[5], 0);
                } else {
                    broad(Main.configS[4], 0);
                }
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "toggledownfall");
                break;
        }
    }

    public static void command(int com) {
        switch (com) {
            case 1:
                broad(Main.configS[2], 0);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "time set 0");
                break;
            case 2:
                broad(Main.configS[3], 0);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "time set 18000");
                break;
            case 3:
                if (Bukkit.getWorlds().get(0).hasStorm()) {
                    broad(Main.configS[5], 0);
                } else {
                    broad(Main.configS[4], 0);
                }
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "toggledownfall");
                break;
        }
    }

    public static void clear() {
        Main.Timer = false;
        Main.votee = "";
        Main.voteOn = false;
        Main.voteTop = Main.NONE;
        Main.players = 0;
        Main.thresh = 0D;
        Main.fthresh = 0;
        Main.voteY = 0;
        Main.voteN = 0;
        Main.voted.clear();
        Main.voted.add(0, "");
        Main.voted.add(1, "");
        Main.tR = false;
    }

    public static void broad(String m, int f) {
        if (f == 1){
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[Voter]" + ChatColor.RESET + "" + ChatColor.GREEN + m);
            }
        }
        boolean config = false;
        if (Main.configB) {
            config = true;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Voter]" + ChatColor.RESET + "" + ChatColor.RED + m);
            if (config && p.getDisplayName().equals("ZXSkelobrine")) {
                p.sendMessage("Details: ");
                p.sendMessage("Timer: " + Main.Timer);
                p.sendMessage("votee: " + Main.voteOn);
                p.sendMessage("voteOn: " + Main.voteOn);
                p.sendMessage("voteTop: " + Main.voteTop);
                p.sendMessage("players: " + Main.players);
                p.sendMessage("thresh: " + Main.thresh);
                p.sendMessage("fthresh: " + Main.fthresh);
                p.sendMessage("tR: " + Main.tR);
                p.sendMessage("voteY: " + Main.voteY);
                p.sendMessage("voteN: " + Main.voteN);
            }
        }
    }

    /**
     * @param v Maximum value setting.
     * @return boolean
     */
    public static boolean chance(int v) {
        Random r = new Random();
        return r.nextInt(v) > 50;
    }

    public static void smo(Player p, String msg) {
        p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Voter]" + ChatColor.RESET + "" + ChatColor.RED + msg);
    }
}