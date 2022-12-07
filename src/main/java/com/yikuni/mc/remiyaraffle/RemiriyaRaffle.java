package com.yikuni.mc.remiyaraffle;

import com.yikuni.db.exception.YikuniDBException;
import com.yikuni.db.main.Database;
import com.yikuni.mc.reflect.PluginLoader;
import com.yikuni.mc.remiyaraffle.raffle.RaffleManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class RemiriyaRaffle extends JavaPlugin {
    private static RemiriyaRaffle INSTANCE;
    public static Database database;

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginLoader.run(RemiriyaRaffle.class);
        INSTANCE = this;
        initDatabase();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        database.save();
    }

    public static RemiriyaRaffle getInstance(){return INSTANCE;}

    private void initDatabase(){
        try {
            database = new Database(new File(this.getDataFolder(), "database").getAbsolutePath(), 1000 * 60 * 60L);
            RaffleManager.INSTANCE.initTable();
        } catch (YikuniDBException e) {
            e.printStackTrace();
        }
    }
}
