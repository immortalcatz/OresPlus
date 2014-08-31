package com.outlook.siribby.oresplus;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.HashMap;

@Mod(modid = OresPlus.MOD_ID, version = OresPlus.VERSION, useMetadata = true)
public class OresPlus {
    public static final String MOD_ID = "oresplus";
    public static final String VERSION = "@VERSION@";
    @Mod.Instance(MOD_ID)
    public static OresPlus instance;
    @SidedProxy(clientSide = "com.outlook.siribby.oresplus.ProxyClient", serverSide = "com.outlook.siribby.oresplus.ProxyCommon")
    public static ProxyCommon proxy;

    public static final BiomeDecoratorOP generator = new BiomeDecoratorOP();
    public static final int renderId = RenderingRegistry.getNextAvailableRenderId();
    public static Configuration config;

    public static final CreativeTabs tabOresPlus = new CreativeTabs(MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(GameRegistry.findBlock(MOD_ID, "red_ore"));
        }
    };
    public static Block tnt_ore;
    public static Object[] names = {"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", new String[]{"Light", "Gray"}, "Gray", "Pink", "Lime", "Yellow", new String[]{"Light", "Blue"}, "Magenta", "Orange"};
    public static int[] colors = {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844};

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        if (config.get("general", "TNTOreEnabled", true).getBoolean(true)) {
            tnt_ore = new BlockTNTOre().setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setBlockName("oreTnt").setBlockTextureName(MOD_ID + ":tnt_ore").setCreativeTab(tabOresPlus);
            GameRegistry.registerBlock(tnt_ore, "tnt_ore");
            generator.addOreGeneration(tnt_ore, 8, 20, 64);
        }

        for (int i = 0; i < names.length; i++) {
            if ((names[i] instanceof String[])) {
                String[] names1 = (String[]) names[i];
                String[] names2 = new String[names1.length - 1];
                System.arraycopy(names1, 1, names2, 0, names2.length);
                String name = "";
                for (String s : names1) {
                    name += s;
                }
                if (config.get("general", name + "StuffEnabled", true).getBoolean(true)) {
                    registerOre(names1[0], colors[i], names2);
                }
            } else {
                String name = (String) names[i];
                if (config.get("general", name + "StuffEnabled", true).getBoolean(true)) {
                    registerOre(name, colors[i]);
                }
            }
        }

        GameRegistry.registerWorldGenerator(generator, 4);

        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        config.save();
    }

    private void registerOre(String oreName, int color, String... extraNames) {
        String name = oreName;
        String lowerName = oreName.toLowerCase();
        String registerName = lowerName;
        if (!Arrays.asList(extraNames).isEmpty()) {
            for (String extraName : extraNames) {
                if (!StringUtils.isNullOrEmpty(extraName)) {
                    name = name + extraName;
                    lowerName = lowerName + extraName;
                    registerName = registerName + "_" + extraName.toLowerCase();
                }
            }
        }
        boolean isLightGray = lowerName.equals("lightGray");
        String smeltName = isLightGray ? "diamond" : "ingot";

        Item smeltable = new ItemOP(color).setUnlocalizedName(smeltName + name).setCreativeTab(tabOresPlus).setTextureName("oresplus:" + smeltName);
        Item nugget = new ItemOP(color).setUnlocalizedName(lowerName + "Nugget").setCreativeTab(tabOresPlus).setTextureName("oresplus:nugget");

        Item.ToolMaterial toolMaterial = isLightGray ? Item.ToolMaterial.EMERALD : Item.ToolMaterial.IRON;
        Item sword = new ItemOPSword(toolMaterial, color).setUnlocalizedName("sword" + name).setTextureName("oresplus:sword").setCreativeTab(tabOresPlus);
        Item shovel = new ItemOPSpade(toolMaterial, color).setUnlocalizedName("shovel" + name).setTextureName("oresplus:shovel").setCreativeTab(tabOresPlus);
        Item pickaxe = new ItemOPPickaxe(toolMaterial, color).setUnlocalizedName("pickaxe" + name).setTextureName("oresplus:pickaxe").setCreativeTab(tabOresPlus);
        Item axe = new ItemOPAxe(toolMaterial, color).setUnlocalizedName("hatchet" + name).setTextureName("oresplus:axe").setCreativeTab(tabOresPlus);
        Item hoe = new ItemOPHoe(toolMaterial, color).setUnlocalizedName("hoe" + name).setTextureName("oresplus:hoe").setCreativeTab(tabOresPlus);

        ItemArmor.ArmorMaterial armorMaterial = isLightGray ? EnumHelper.addArmorMaterial(registerName.toUpperCase(), 33, new int[]{3, 8, 6, 3}, 10) : EnumHelper.addArmorMaterial(registerName.toUpperCase(), 15, new int[]{2, 6, 5, 2}, 9);
        int armorRenderIndex = isLightGray ? proxy.getArmorRenderIndex("diamond") : proxy.getArmorRenderIndex("iron");
        Item helmet = new ItemOPArmor(armorMaterial, armorRenderIndex, 0, color).setUnlocalizedName("helmet" + name).setTextureName("oresplus:helmet").setCreativeTab(tabOresPlus);
        Item chestplate = new ItemOPArmor(armorMaterial, armorRenderIndex, 1, color).setUnlocalizedName("chestplate" + name).setTextureName("oresplus:chestplate").setCreativeTab(tabOresPlus);
        Item leggings = new ItemOPArmor(armorMaterial, armorRenderIndex, 2, color).setUnlocalizedName("leggings" + name).setTextureName("oresplus:leggings").setCreativeTab(tabOresPlus);
        Item boots = new ItemOPArmor(armorMaterial, armorRenderIndex, 3, color).setUnlocalizedName("boots" + name).setTextureName("oresplus:boots").setCreativeTab(tabOresPlus);

        Block ore = new BlockOPOre(color).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setBlockName("ore" + name).setBlockTextureName("oresplus:ore").setCreativeTab(tabOresPlus);
        Block compressed = new BlockOPCompressed(color).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypePiston).setBlockName("block" + name).setBlockTextureName("oresplus:block").setCreativeTab(tabOresPlus);

        int harvestLevel = isLightGray ? 2 : 1;
        ore.setHarvestLevel("pickaxe", harvestLevel);
        compressed.setHarvestLevel("pickaxe", harvestLevel);

        GameRegistry.registerItem(smeltable, registerName + "_" + smeltName);
        GameRegistry.registerItem(nugget, registerName + "_nugget");

        GameRegistry.registerItem(sword, registerName + "_sword");
        GameRegistry.registerItem(shovel, registerName + "_shovel");
        GameRegistry.registerItem(pickaxe, registerName + "_pickaxe");
        GameRegistry.registerItem(axe, registerName + "_axe");
        GameRegistry.registerItem(hoe, registerName + "_hoe");

        GameRegistry.registerItem(helmet, registerName + "_helmet");
        GameRegistry.registerItem(chestplate, registerName + "_chestplate");
        GameRegistry.registerItem(leggings, registerName + "_leggings");
        GameRegistry.registerItem(boots, registerName + "_boots");

        GameRegistry.registerBlock(ore, registerName + "_ore");
        OreDictionary.registerOre(name, ore);
        GameRegistry.registerBlock(compressed, registerName + "_block");

        float smeltingXp = isLightGray ? 1.0F : 0.7F;
        GameRegistry.addSmelting(new ItemStack(ore), new ItemStack(smeltable), smeltingXp);
        GameRegistry.addShapelessRecipe(new ItemStack(smeltable, 9), compressed);
        GameRegistry.addRecipe(new ItemStack(compressed), "###", "###", "###", '#', smeltable);

        GameRegistry.addShapelessRecipe(new ItemStack(nugget, 9), smeltable);
        GameRegistry.addRecipe(new ItemStack(smeltable), "###", "###", "###", '#', nugget);

        String[][] recipePatterns = {{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", "#X", "# "}, {"XX", " #", " #"}, {"XX", "# ", "# "}, {"X", "X", "#"}};
        Object[] recipeItems = {pickaxe, shovel, axe, axe, hoe, hoe, sword};
        for (int j = 0; j < recipePatterns.length; j++) {
            Item item = (Item) recipeItems[j];
            GameRegistry.addRecipe(new ItemStack(item), recipePatterns[j], '#', Items.stick, 'X', smeltable);
        }
        int numberOfBlocks = isLightGray ? 7 : 8;
        int timesPerChunk = isLightGray ? 1 : 20;
        int maxHeight = isLightGray ? 16 : 64;
        generator.addOreGeneration(ore, numberOfBlocks, timesPerChunk, maxHeight);
    }
}
