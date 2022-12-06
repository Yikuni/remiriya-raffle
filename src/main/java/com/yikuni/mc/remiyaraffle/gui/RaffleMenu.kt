package com.yikuni.mc.remiyaraffle.gui

import com.yikuni.mc.reflect.annotation.YikuniMenu
import com.yikuni.mc.reflect.common.Menu
import com.yikuni.mc.remiyaraffle.raffle.RaffleManager
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

@YikuniMenu(value = "抽奖箱", size = 54)
class RaffleMenu(): Menu() {
    constructor(name: String, size: Int): this(){
        this.name = name
        this.size = size
    }
    override fun click(event: InventoryClickEvent) {
        // do nothing
    }

    override fun open(player: Player, inventory: Inventory, vararg args: Any?) {
        val chestName = args[0] as String
        val raffleChest = RaffleManager.getRaffleChest(chestName) ?: return
        raffleChest.menu(inventory)
    }

}