package com.outlook.siribby.oresplus;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockOre;
import net.minecraft.world.IBlockAccess;

public class BlockOPOre extends BlockOre {
    public static int renderPass;
    private int color;

    public BlockOPOre(int rgb) {
        color = rgb;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return OresPlus.renderId;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        renderPass = pass;
        return true;
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
