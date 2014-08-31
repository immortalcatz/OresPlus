package com.outlook.siribby.oresplus;

import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockTNT;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTNTOre extends BlockOre {
    @Override
    public Item getItemDropped(int par1, Random random, int par3) {
        return Item.getItemFromBlock(Blocks.tnt);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int metadata) {
        if ((!player.isSneaking()) && (!EnchantmentHelper.getSilkTouchModifier(player))) {
            ((BlockTNT) Blocks.tnt).func_150114_a(world, x, y, z, 1, player);
            world.setBlockToAir(x, y, z);
            return;
        }
        super.harvestBlock(world, player, x, y, z, metadata);
    }
}
