package com.outlook.siribby.oresplus;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.util.StringUtils;

public class ProxyClient extends ProxyCommon {
    @Override
    public void registerRenderers() {
        RenderingRegistry.registerBlockHandler(new RenderOPOre());
    }

    @Override
    public int getArmorRenderIndex(String armor) {
        for (int i = 0; i < RenderBiped.bipedArmorFilenamePrefix.length; i++) {
            if ((!StringUtils.isNullOrEmpty(RenderBiped.bipedArmorFilenamePrefix[i])) && (armor.equalsIgnoreCase(RenderBiped.bipedArmorFilenamePrefix[i]))) {
                return i;
            }
        }
        return RenderingRegistry.addNewArmourRendererPrefix(armor);
    }
}
