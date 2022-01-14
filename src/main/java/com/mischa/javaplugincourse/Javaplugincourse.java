package com.mischa.javaplugincourse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Javaplugincourse extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println(ChatColor.YELLOW + "[Plugin]: Java course plugin is enabled!");

        //Loading the config file
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Bukkit.getPluginManager().registerEvent(this, this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(ChatColor.LIGHT_PURPLE + "[Plugin]: Java course plugin is disabled!");
    }

    //Custom commands
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equals("hello")) {
            if (!(sender instanceof Player)) { //If the sender is not a player
                System.out.println(ChatColor.RED + "You cannot use this command through console!");
                return true;
            } else {
                Player player = (Player) sender; //Cast sender to player
                player.sendMessage(ChatColor.GRAY + "Hello, " + ChatColor.GREEN + player.getName() + ChatColor.GRAY + " your health has been restored!");
                player.setHealth(20.0);
                return false;
            }
        }
        //Access data from config file using a command
       if (cmd.getName().equals("config")) {
           Player player = (Player) sender;
           String word = this.getConfig().getString("Word");
           int number = this.getConfig().getInt("Number");

           player.sendMessage(ChatColor.GRAY + "The word is " + ChatColor.GREEN + word + ChatColor.GRAY + " and the number is " + ChatColor.GREEN + number);
        }
       return false;
    }

    //Events

    //Cancel player movement
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        //Check if player has move permission
        if (!player.hasPermission("javaplugincourse.allowmove")) {
            //Cancel the player's movement if they don't have the permission
            e.setCancelled(true);
        }
    }

    //Say "Egg throw!" when player throws and egg
    @EventHandler
    public void onThrow(PlayerEggThrowEvent e ) {
        Player player = e.getPlayer();
        player.sendMessage(ChatColor.RED + "Egg throw!");
    }

    //Edits the config file
    public void editConfig() {
        //Changes the word to "Hello!"
        this.getConfig().set("Word", "Hello!");

        //Creates a list of config variables
        this.getConfig().createSection("word");
    }
}