package com.yikuni.mc.remiriyaraffle.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerRaffleEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final ItemStack itemStack;

    public PlayerRaffleEvent(@NotNull Player who, @NotNull ItemStack itemStack) {
        super(who);
        this.itemStack = itemStack;
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
}
