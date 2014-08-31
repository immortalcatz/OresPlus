package com.outlook.siribby.oresplus;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BiomeDecoratorOP implements IWorldGenerator {
    private static List<Minable> minables = new ArrayList<Minable>();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.isSurfaceWorld()) {
            for (Minable minable : minables) {
                WorldGenerator oreGen = new WorldGenMinable(minable.ore, minable.numberOfBlocks);
                if (TerrainGen.generateOre(world, random, oreGen, chunkX, chunkZ, OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
                    for (int k = 0; k < minable.timesPerChunk; k++) {
                        int firstBlockXCoord = chunkX * 16 + random.nextInt(16);
                        int firstBlockYCoord = random.nextInt(minable.maxHeight);
                        int firstBlockZCoord = chunkZ * 16 + random.nextInt(16);
                        oreGen.generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
                    }
                }
            }
        }
    }

    public void addOreGeneration(Block ore, int numberOfBlocks, int timesPerChunk, int maxHeight) {
        minables.add(new Minable(ore, numberOfBlocks, timesPerChunk, maxHeight));
    }

    private class Minable {
        public Block ore;
        public int numberOfBlocks;
        public int timesPerChunk;
        public int maxHeight;

        public Minable(Block ore, int numberOfBlocks, int timesPerChunk, int maxHeight) {
            this.ore = ore;
            this.numberOfBlocks = numberOfBlocks;
            this.timesPerChunk = timesPerChunk;
            this.maxHeight = maxHeight;
        }
    }
}
