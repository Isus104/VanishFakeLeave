package isus.VanishFakeLeave;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.ISettings;
import com.earth2me.essentials.User;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;


public class VanishFakeLeave<player> extends JavaPlugin {


    @Override
    public void onEnable() {
        super.onEnable();
        getLogger().info("VanishFakeLeave enabled!");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        HandlerList.unregisterAll(this);
        getLogger().info("VanishFakeLeave disabled!");
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        assert ess != null;
        ISettings setting = ess.getSettings();
        Player p = (Player) sender;
        User name = ess.getUser(p);


        if (cmd.getName().equalsIgnoreCase("fakequit")) {
            if (args.length == 0 && name.isVanished() == false) {
                String customquitnmsg = setting.getCustomQuitMessage().replace("{USERNAME}", name.getName());
                name.setVanished(true);
                if (customquitnmsg.contains("none")) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + name.getName() + ChatColor.YELLOW + " left the game");
                } else if (customquitnmsg.contains("none") == false) {
                    Bukkit.broadcastMessage(customquitnmsg);
                }
            } else if (args.length == 0 && name.isVanished() == true) {
                p.sendMessage(ChatColor.RED + "(VanishFakeLeave) - You are already vanished!");
            } else {
                p.sendMessage(ChatColor.RED + ("(VanishFakeLeave) - Invalid argument: '") + args[0] + "'");
            }
        }

        if (cmd.getName().equalsIgnoreCase("fakejoin")) {
            if(args.length == 0 && name.isVanished()){
                String customjoinmsg = setting.getCustomJoinMessage().replace("{USERNAME}", name.getName());
                name.setVanished(false);
                if(customjoinmsg.contains("none")){
                    Bukkit.broadcastMessage(ChatColor.YELLOW + name.getName() + ChatColor.YELLOW + " joined the game");
                } else if (customjoinmsg.contains("none") == false) {
                    Bukkit.broadcastMessage(customjoinmsg);
                }
            } else if (args.length == 0 && name.isVanished() == false){
                p.sendMessage(ChatColor.RED +"(VanishFakeLeave) - You are already visible!");
            } else {
                p.sendMessage(ChatColor.RED + ("(VanishFakeLeave) - Invalid argument: '") + args[0] + "'");
            }
        }
        return true;
    }
}