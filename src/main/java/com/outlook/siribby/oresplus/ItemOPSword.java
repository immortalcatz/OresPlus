package com.outlook.siribby.oresplus;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;

public class ItemOPSword extends ItemSword {
    private int color;
    @SideOnly(Side.CLIENT)
    private IIcon overlayIcon;

    public ItemOPSword(Item.ToolMaterial toolMaterial, int rgb) {
        super(toolMaterial);
        color = rgb;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack itemStack, int par2) {
        if (par2 > 0) {
            return super.getColorFromItemStack(itemStack, par2);
        }
        return color;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamageForRenderPass(int metadata, int pass) {
        return pass == 1 ? overlayIcon : super.getIconFromDamageForRenderPass(metadata, pass);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
        overlayIcon = iconRegister.registerIcon(getIconString() + "_base");
    }
}
