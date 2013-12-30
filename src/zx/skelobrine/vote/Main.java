package zx.skelobrine.vote;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Main extends JavaPlugin {
    public static final int NONE = 0;
    public static final int DAY = 1;
    static final int NIGHT = 2;
    static final int RAIN = 3;
    static final int KICK = 4;
    static final int BAN = 5;
    public static boolean Timer = false;
    public static String votee = "";
    public static boolean voteOn = false;
    public static int voteTop = NONE;
    public static int players = 0;
    public static double thresh = 0D;
    public static int fthresh = 0;
    public static boolean tR = false;
    public static int voteY = 0;
    public static int voteN = 0;
    public static String configS[] = new String[100];
    public static int configI = 300;
    public static boolean configB = false;
    public static String holder = "";
    @SuppressWarnings("CanBeFinal")
    public static List<String> voted = new ArrayList<String>();
    int thesholds[] = new int[]{this.getConfig().getInt("ban.banThreshold"), this.getConfig().getInt("kick.kickThreshold")};

    private static String rem(String str) {
        return str.substring(0, str.length() - 2);
    }

    public static void broad(String msg) {
        Tester.broad(msg, 0);
    }

    @Override
    public void onEnable() {
        getLogger().info("Tutorial Plugin has been enabled!");
        this.saveDefaultConfig();
        Tester.clear();
        configB = this.getConfig().getBoolean("debug");
        configI = this.getConfig().getInt("timer");
        configS[1] = this.getConfig().getString("chance");
        configS[2] = this.getConfig().getString("success.day");
        configS[3] = this.getConfig().getString("success.night");
        configS[4] = this.getConfig().getString("success.rain.start");
        configS[5] = this.getConfig().getString("success.rain.stop");

        configS[6] = this.getConfig().getString("kick.kick1");
        configS[7] = this.getConfig().getString("kick.kick2");

        getLogger().info("//----------------------------------------------");
        getLogger().info("//Debug: " + configB);
        getLogger().info("//Timer: " + configI);
        getLogger().info("//Chance: " + configS[1]);
        getLogger().info("//Success day: " + configS[2]);
        getLogger().info("//Success night: " + configS[3]);
        getLogger().info("//Success rain start: " + configS[4]);
        getLogger().info("//Success rain stop: " + configS[5]);
        getLogger().info("//----------------------------------------------");
    }

    @Override
    public void onDisable() {
        getLogger().info("Tutorial Plugin has been disabled!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player;
        player = (Player) sender;
        if (!(args.length < 1)) {

            if (cmd.getName().equalsIgnoreCase("vote")) {
                //noinspection ConstantConditions
                if (player instanceof Player) {
                    if (!voteOn) {
                        players = Bukkit.getOnlinePlayers().length;
                        //player.sendMessage("Sorry there aren't enough players online right now");
                        thresh = players / 2;
                        if (!pI(thresh)) {
                            System.out.println(thresh);
                            if (!(pIsB(rem(String.valueOf(Math.ceil(thresh)))))) {
                                getLogger().severe("Something has gone wrong. Please contact ZXSkelobrine at ZXSkelobrine@gmail.com");
                            } else {
                                fthresh = Integer.parseInt(rem(String.valueOf(Math.ceil(thresh))));
                            }
                        }
//                if (player.hasPermission("some.permission")){
                        if (args != null) {

                            if (args[0].equalsIgnoreCase("start")) {
                                if (!Timer && tR) {
                                    player.sendMessage("Sorry there is already a vote running for: " + r(voteTop));
                                } else {
                                    if (args.length > 1) {
                                        if (args[1].equalsIgnoreCase("day")) {
                                            if (player.hasPermission("voter.start.day")) {
                                                if (Bukkit.getOnlinePlayers().length == 1) {
                                                    Tester.command(1);
                                                } else {
                                                    voteTop = DAY;
                                                    voteOn = true;
                                                    votee = player.getDisplayName();
                                                    Tester.broad("Vote Topic: " + r(voteTop) + "   Vote Starter: " + votee, 1);
                                                    Tester.timerT();
                                                }
                                            }
                                        }
                                        if (args[1].equalsIgnoreCase("night")) {
                                            if (player.hasPermission("voter.start.night")) {
                                                if (Bukkit.getOnlinePlayers().length == 1) {
                                                    Tester.command(2);
                                                } else {
                                                    voteTop = NIGHT;
                                                    voteOn = true;
                                                    votee = player.getDisplayName();
                                                    Tester.broad("Vote Topic: " + r(voteTop) + "   Vote Starter: " + votee, 1);
                                                    Tester.timerT();
                                                }
                                            }
                                        }

                                        if (args[1].equalsIgnoreCase("rain")) {
                                            if (player.hasPermission("voter.start.rain")) {
                                                if (Bukkit.getOnlinePlayers().length == 1) {
                                                    Tester.command(3);
                                                } else {

                                                    voteTop = RAIN;
                                                    voteOn = true;
                                                    votee = player.getDisplayName();
                                                    Tester.broad("Vote Topic: " + r(voteTop) + "   Vote Starter: " + votee, 1);
                                                    Tester.timerT();
                                                }
                                            }
                                        }
                                        if (args[1].equalsIgnoreCase("ban")) {
                                            if (!(args.length < 2)) {
                                                if (player.hasPermission("voter.start.ban")) {
                                                    if (Bukkit.getOnlinePlayers().length < 3) {
                                                        broad("Insufficient online player to allow a ban.");
                                                    } else {

                                                    }
                                                }
                                            } else {

                                            }
                                        }
                                        if (args[1].equalsIgnoreCase("kick")) {
                                            if (!(args.length < 2)) {
                                                if (player.hasPermission("voter.start.kick")) {
                                                    if (Bukkit.getOnlinePlayers().length < thesholds[1]) {
                                                        broad("Insufficient online player to allow a kick.");
                                                    } else {
                                                        boolean online = false;
                                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                                            online = p.getDisplayName().equalsIgnoreCase(args[2]);
                                                        }
                                                        if (!online) {
                                                            Tester.smo(player, "They are not online");
                                                        } else {
                                                            voteTop = KICK;
                                                            voteOn = true;
                                                            votee = player.getDisplayName();
                                                            Tester.broad("Vote Topic: " + r(voteTop) + "   Vote Starter: " + votee, 1);
                                                            holder = args[2];
                                                            Tester.timerT();
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }

                                }
                                return true;
                            } else {

                                return false;
                            }

//                }
                        }
                    }
                }

            }


            assert args != null;
            if (args[0].equalsIgnoreCase("y") || args[0].equalsIgnoreCase("yes")) {
                if (player.hasPermission("voter.vote.y")) {
                    boolean gh = false;
                    int z = 0;
                    while (z < voted.toArray().length) {
                        gh = player.getDisplayName().equals(voted.toArray()[z]);
                        z++;
                    }
                    if (z == voted.toArray().length) {
                        if (!gh) {
                            Main.voteY++;
                            Main.voted.add(player.getDisplayName());
                            Tester.smo(player, "Thank you for casting your vote");
                        } else {
                            Tester.smo(player, "Sorry you have already voted");
                        }
                    }
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("n") || args[0].equalsIgnoreCase("no")) {
                if (player.hasPermission("voter.vote.n")) {
                    boolean gh = false;
                    int z = 0;
                    while (z < voted.toArray().length) {
                        gh = player.getDisplayName().equals(voted.toArray()[z]);
                        z++;
                    }
                    if (z == voted.toArray().length) {
                        if (!gh) {
                            Main.voteN++;
                            Main.voted.add(player.getDisplayName());
                            Tester.smo(player, "Thank you for casting your vote");
                        } else {
                            Tester.smo(player, "Sorry you have already voted");
                        }
                    }
                }
                return true;
            }
            if (args[0].equals("cancel")) {
                if (player.hasPermission("voter.admin.cancel")) {
                    broad("Vote admin " + player.getDisplayName() + " has stopped the vote!");
                    Tester.timer.cancel();
                    Tester.clear();
                } else {
                    Tester.smo(player, "Sorry - you don't seem to have the correct permissions.");
                }
                return true;
            }
        } else {
            player.sendMessage("Sorry there is already a vote running for: " + r(voteTop));
        }
        return false;
    }

// --Commented out by Inspection START (28/12/13 17:45):
//    public int pIn(Double d) {
//        try {
//            //noinspection UnnecessaryLocalVariable
//            int s = Integer.parseInt(d.toString());
//            return s;
//        } catch (Exception e) {
//            return 0;
//        }
//
//    }
// --Commented out by Inspection STOP (28/12/13 17:45)

// --Commented out by Inspection START (28/12/13 17:46):
//    public int pIs(String s) {
//        return Integer.parseInt(s);
//    }
// --Commented out by Inspection STOP (28/12/13 17:46)

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean pI(Double d) {
        try {
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(d.toString());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean pIsB(String s) {
        try {
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String r(int i) {
        String ret = "";
        switch (i) {
            case 0:
                ret = "None";
                break;
            case 1:
                ret = "Day";
                break;
            case 2:
                ret = "Night";
                break;
            case 3:
                ret = "Rain";
                break;
        }
        return ret;
    }
}
