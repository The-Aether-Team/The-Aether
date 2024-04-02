# Suits up the player with equipment that won't break for battle.
give @p aether:zanite_pickaxe{Unbreakable:1}
give @p aether:zanite_sword{Unbreakable:1}

# If the player doesn't already have armor, equip them with gravitite.
execute unless data entity @p Inventory[{Slot:100b}] run item replace entity @p armor.feet with aether:gravitite_boots{Unbreakable:1}
execute unless data entity @p Inventory[{Slot:101b}] run item replace entity @p armor.legs with aether:gravitite_leggings{Unbreakable:1}
execute unless data entity @p Inventory[{Slot:102b}] run item replace entity @p armor.chest with aether:gravitite_chestplate{Unbreakable:1}
execute unless data entity @p Inventory[{Slot:103b}] run item replace entity @p armor.head with aether:gravitite_helmet{Unbreakable:1}
execute unless data entity @p ForgeCaps."curios:inventory".Curios[{Identifier:"aether_gloves"}].StacksHandler.Stacks.Items[0] run curios replace aether_gloves 0 @p with aether:gravitite_gloves{Unbreakable:1}

give @p aether:skyroot_water_bucket
give @p aether:enchanted_berry 64
give @p aether:healing_stone 64
