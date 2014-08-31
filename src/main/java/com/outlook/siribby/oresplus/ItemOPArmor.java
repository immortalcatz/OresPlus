package com.outlook.siribby.oresplus;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;

public class ItemOPArmor extends ItemArmor {
    private int color;

    public ItemOPArmor(ItemArmor.ArmorMaterial material, int renderIndex, int armorType, int rgb) {
        super(material, renderIndex, armorType);
        color = rgb;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack itemStack, int par2) {
        return getColor(itemStack);
    }

    @Override
    public boolean hasColor(ItemStack itemStack) {
        return true;
    }

    @Override
    public int getColor(ItemStack itemStack) {
        return color;
    }

    @Override
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type) {
        String texture = "layer_" + (slot == 2 ? 2 : 1);
        if (!StringUtils.isNullOrEmpty(type)) {
            texture = "layer_empty";
        }
        return OresPlus.MOD_ID + ":/textures/models/armor/" + texture + ".png";
    }
}
