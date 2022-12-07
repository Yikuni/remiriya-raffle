package com.yikuni.mc.remiriyaraffle.raffle

import com.yikuni.mc.rumiyalib.inventory.InventoryItem
import com.yikuni.mc.rumiyalib.utils.getItemText
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.inventory.ItemStack

class RaffleItem() {
    lateinit var inventoryItem: InventoryItem
    var weight: Int = 1
    private lateinit var itemStack: ItemStack
    constructor(itemStack: ItemStack, weight: Int): this(){
        inventoryItem = InventoryItem.fromItemStack(itemStack)
        this.weight = weight
        this.itemStack = itemStack
    }
    private fun getRare():String{
        return when(weight){
            0 -> "${org.bukkit.ChatColor.RED}不可获得"
            in 1..3 -> "${org.bukkit.ChatColor.GOLD}传说"
            in 4..12 -> "${org.bukkit.ChatColor.DARK_PURPLE}稀有"
            else -> "${org.bukkit.ChatColor.WHITE}普通"
        }
    }

    fun textComp(): List<TextComponent>{
        val result = mutableListOf(TextComponent(*ComponentBuilder().append(
            when(weight) {
                0 -> "不可获得"
                in 1..3 -> "传说"
                in 4..12 -> "稀有"
                else -> "普通"
            }).color(
            when(weight) {
                0 -> ChatColor.RED
                in 1..3 -> ChatColor.GOLD
                in 4..12 -> ChatColor.DARK_PURPLE
                else -> ChatColor.WHITE
            }).create()))
        result.add(getItemText(provideGuiItem()))
        return result
    }

    fun provideGuiItem(): ItemStack{
        val item = itemStack.clone()
        val itemMeta = item.itemMeta!!
        if (itemMeta.lore == null) itemMeta.lore = mutableListOf(getRare())
        else itemMeta.lore!!.add(getRare())

        item.itemMeta = itemMeta
        return item
    }

    fun provideItemStack(): ItemStack{
        return itemStack
    }
}