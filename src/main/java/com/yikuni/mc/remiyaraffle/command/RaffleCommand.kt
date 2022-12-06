package com.yikuni.mc.remiyaraffle.command

import com.yikuni.mc.reflect.annotation.YikuniCommand
import com.yikuni.mc.reflect.context.menu.MenuFacade
import com.yikuni.mc.remiyaraffle.raffle.RaffleItem
import com.yikuni.mc.remiyaraffle.raffle.RaffleManager
import com.yikuni.mc.rumiyalib.utils.sender
import org.bukkit.Material
import org.bukkit.block.TileState
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

@YikuniCommand(value = "raffle", description = "抽奖有关指令")
class RaffleCommand: CommandExecutor, TabCompleter {
    private val usage = "TODO"
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        val isFormatValid = if (args.isNotEmpty()){
            when(args[0]){
                "create" ->{
                    if (args.size == 2){
                        sender.sender().primary(RaffleManager.createRaffleChest(args[1]))
                        true
                    }else false
                }
                "remove" ->{
                    if (args.size == 2){
                        sender.sender().primary(RaffleManager.removeRaffleChest(args[1]))
                        true
                    }else false
                }
                "addItem" ->{
                    if (args.size == 4){
                        if (args[2] == "hand"){
                            val itemStack = sender.inventory.itemInMainHand
                            if (itemStack == null){
                                sender.sender().warn("请将要加入的奖品放在主手")
                                return true
                            }
                            sender.sender().primary(RaffleManager.addRaffleItem(args[1], itemStack, args[3].toInt()))
                            true
                        }else if (args[2] == "inventory"){
                            sender.sender().primary(RaffleManager.addRaffleItem(args[1], sender.inventory, args[3].toInt()))
                            true
                        }else false
                    }else false
                }
                "removeItem" ->{
                    if (args.size == 3){
                        sender.sender().primary(RaffleManager.removeRaffleItem(args[1], args[2].toInt()))
                        true
                    }else false
                }
                "list" ->{
                    sender.sender().info(RaffleManager.getFormattedRaffleNameList())
                    true
                }
                "show" ->{
                    if (args.size == 2){
                        MenuFacade.open(sender, "Admin抽奖箱", args[1])
                        true
                    }else false
                }
                "place" ->{
                    if (args.size > 1){
                        val chest = sender.location.block
                        if (args.size == 3){
                            chest.type = when(args[2]){
                                "1" -> Material.ENDER_CHEST
                                "2" -> Material.SHULKER_BOX
                                else -> Material.CHEST
                            }
                        }else{
                            chest.type = Material.CHEST
                        }
                        val state = chest.state as TileState
                        state.persistentDataContainer[RaffleManager.key, PersistentDataType.STRING] = args[1]
                        state.update()
                        sender.sender().success("宝箱已生成")
                        true
                    }else false
                }
                "key" ->{
                    if (args.size == 2){
                        val itemStack = sender.inventory.itemInMainHand
                        if (itemStack == null){
                            sender.sender().warn("请确保手上有物品再设置钥匙")
                            return true
                        }
                        val meta = itemStack.itemMeta!!
                        meta.persistentDataContainer[RaffleManager.key, PersistentDataType.STRING] = args[1]
                        meta.addEnchant(Enchantment.LUCK, 1, true)
                        itemStack.itemMeta = meta
                        sender.inventory.addItem(itemStack)
                        sender.sender().success("钥匙设置成功")
                    }
                    true
                }

                else ->false
            }
        }else false

        if (!isFormatValid){
           sender.sender().warn(usage)
        }
        return true
    }



    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        return when(args.size){
            1 ->{
                listOf("create", "remove", "addItem", "removeItem", "list", "help", "show", "place", "key")
            }
            2 ->{
                when(args[0]){
                    "create" -> listOf("宝箱名")
                    "remove", "removeItem", "addItem", "show", "place", "key" -> RaffleManager.getAllChestNames()
                    else -> null
                }
            }
            3 ->{
                when(args[0]){
                    "removeItem" -> listOf("<移除物品的序号>")
                    "addItem" -> listOf("hand", "inventory")
                    "place" -> listOf("CHEST", "ENDER_CHEST", "SHULKER_BOX")
                    else -> null
                }
            }
            else -> null
        }?.filter { it.startsWith(args[args.size - 1]) }?.toMutableList()
    }
}