package com.yikuni.mc.remiriyaraffle.raffle

import com.yikuni.db.main.JsonSerializeStrategy
import com.yikuni.db.main.Table
import com.yikuni.mc.remiriyaraffle.RemiriyaRaffle
import org.bukkit.ChatColor
import org.bukkit.NamespacedKey
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object RaffleManager {
    val key = NamespacedKey("remiriyaraffle", "raffle_chest_name")
    lateinit var raffleTable: Table<RaffleChest>

    fun initTable(){
        val database = RemiriyaRaffle.database
        val tableName = "raffleTable"
        raffleTable = if (database.existTable(tableName)) database.getTable(tableName, RaffleChest::class.java)
                        else database.createTable(tableName, RaffleChest::class.java, JsonSerializeStrategy())
    }

    fun getAllChestNames(): List<String>{
        return raffleTable.data.map { it.name }
    }

    fun createRaffleChest(name: String): RaffleChest?{
        if (getRaffleChest(name) != null){
            return null
        }
        val raffleChest = RaffleChest(name)
        raffleTable.add(raffleChest)
        return raffleChest
    }

    fun removeRaffleChest(name: String): String{
        return if (getRaffleChest(name) == null) "该抽奖箱不存在, 移除失败" else {
            raffleTable.deleteSelective(RaffleChest(name))
            "移除成功"
        }
    }

    fun addRaffleItem(chestName: String, itemStack: ItemStack, weight: Int): String{
        val raffleChest = getRaffleChest(chestName)?:return "未找到该宝箱"
        raffleChest.addItem(itemStack, weight)
        return "添加成功"
    }

    fun addRaffleItem(chestName: String, inventory: Inventory, weight: Int): String{
        val raffleChest = getRaffleChest(chestName)?:return "未找到该宝箱"
        inventory.forEach {
            if (it != null) raffleChest.addItem(it, weight)
        }
        return "添加成功"
    }

    fun removeRaffleItem(chestName: String, index: Int): String{
        val raffleChest = getRaffleChest(chestName)?:return "未找到该宝箱"
        raffleChest.removeItem(index)
        return "移除成功"
    }

    fun getFormattedRaffleNameList(): String{
        val nameList = StringBuilder()
        nameList.append("${ChatColor.DARK_GREEN}======抽奖箱列表=====")
        raffleTable.data.forEach {
            nameList.append("\n${ChatColor.WHITE}${it.name}")
        }
        nameList.append("\n${ChatColor.DARK_GREEN}======抽奖箱列表=====")

        return nameList.toString()
    }

    fun getRaffleChest(name: String): RaffleChest?{
        val select = raffleTable.select(RaffleChest(name))
        return if (select.isEmpty()) null else select[0]
    }
}