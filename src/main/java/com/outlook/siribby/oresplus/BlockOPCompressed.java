package com.outlook.siribby.oresplus;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraft.world.IBlockAccess;

public class BlockOPCompressed extends BlockCompressed {
    private int color;

    public BlockOPCompressed(int rgb) {
        super(MapColor.ironColor);
        color = rgb;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getBlockColor() {
        return color;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(int par1) {
        return color;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return color;
    }
}
