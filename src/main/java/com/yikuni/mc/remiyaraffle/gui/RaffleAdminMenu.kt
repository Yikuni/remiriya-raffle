package com.yikuni.mc.remiyaraffle.gui

import com.yikuni.mc.reflect.annotation.YikuniMenu
import com.yikuni.mc.reflect.common.Menu
import com.yikuni.mc.remiyaraffle.raffle.RaffleManager
import com.yikuni.mc.rumiyalib.utils.giveItem
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
        val itemStack = event.currentItem?.clone()?:return
        val itemMeta = itemStack.itemMeta!!
        itemMeta.lore!!.removeLast()
        itemStack.itemMeta = itemMeta
        (event.whoClicked as Player).giveItem(itemStack)
    }

    override fun open(player: Player, inventory: Inventory, vararg args: Any?) {
        val chestName = args[0] as String
        val raffleChest = RaffleManager.getRaffleChest(chestName) ?: return
        raffleChest.menu(inventory)
    }

}