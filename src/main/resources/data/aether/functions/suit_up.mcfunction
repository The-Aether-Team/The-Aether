# Suits up the player with equipment that won't break for battle.
give @p aether:zanite_pickaxe{Enchantments:[{id:"minecraft:unbreaking",lvl:10}]}
give @p aether:zanite_sword{Enchantments:[{id:"minecraft:unbreaking", lvl:10}]}

# If the player doesn't already have armor, equip them with gravitite.
execute unless data entity @p Inventory[{Slot:100b}] run item replace entity @p armor.feet with aether:gravitite_boots{Enchantments:[{id:"minecraft:unbreaking",lvl:10}]}
execute unless data entity @p Inventory[{Slot:101b}] run item replace entity @p armor.legs with aether:gravitite_leggings{Enchantments:[{id:"minecraft:unbreaking",lvl:10}]}
execute unless data entity @p Inventory[{Slot:102b}] run item replace entity @p armor.chest with aether:gravitite_chestplate{Enchantments:[{id:"minecraft:unbreaking",lvl:10}]}
execute unless data entity @p Inventory[{Slot:103b}] run item replace entity @p armor.head with aether:gravitite_helmet{Enchantments:[{id:"minecraft:unbreaking",lvl:10}]}
execute unless data entity @p ForgeCaps."curios:inventory".Curios[{Identifier:"aether_gloves"}].StacksHandler.Stacks.Items[0] run curios replace aether_gloves 0 @p with aether:gravitite_gloves{Enchantments:[{id:"minecraft:unbreaking",lvl:10}]}

give @p aether:skyroot_water_bucket
give @p aether:enchanted_berry 64
give @p aether:healing_stone 64
