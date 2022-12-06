package com.yikuni.mc.remiyaraffle.gui

import com.yikuni.mc.reflect.annotation.YikuniMenu
import com.yikuni.mc.reflect.common.Menu
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

@YikuniMenu(value = "Admin抽奖箱", size = 54)
class RaffleAdminMenu(): Menu() {
    constructor(name: String, size: Int): this(){
        this.name = name
        this.size = size
    }
    override fun click(event: InventoryClickEvent) {
        TODO("Not yet implemented")
    }

    override fun open(player: Player, inventory: Inventory, vararg args: Any?) {
        TODO("Not yet implemented")
    }

}