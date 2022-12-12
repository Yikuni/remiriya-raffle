package com.yikuni.mc.remiriyaraffle.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerRaffleEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final ItemStack itemStack;
    private final String chestName;
    private final int weight;
    public PlayerRaffleEvent(@NotNull Player who, @NotNull ItemStack itemStack, @NotNull String chestName, int weight) {
        super(who);
        this.itemStack = itemStack;
        this.chestName = chestName;
        this.weight = weight;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static @NotNull HandlerList getHandlerList(){
        return handlers;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getChestName() {
        return chestName;
    }

    public int getWeight() {
        return weight;
    }
}
