package net.cirsius.cart4legacy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Cart4Legacy extends JavaPlugin {
    @Override
    public void onEnable() {
        saveResource("cart.yml", false);
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "cart.yml"));

        if (config.getBoolean("fixes.crossbow-carting", true)) {
            getServer().getPluginManager().registerEvents(new CrossbowFix(this), this);
        }
        if (config.getBoolean("fixes.tnt-minecart-drops", true)) {
            getServer().getPluginManager().registerEvents(new CartDropFix(), this);
        }
    }
}
