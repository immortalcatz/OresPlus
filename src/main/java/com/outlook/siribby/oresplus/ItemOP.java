package com.outlook.siribby.oresplus;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemOP extends Item {
    private int color;

    public ItemOP(int rgb) {
        color = rgb;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        return color;
    }
}
