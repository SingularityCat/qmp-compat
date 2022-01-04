package net.midnightspark.minecraft.qmpcompat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("qmpcompat")
public class QMPCompat {
    private static final Logger LOGGER = LogManager.getLogger();

    public QMPCompat() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    	final Item MILK_BOTTLE = ForgeRegistries.ITEMS.getValue(new ResourceLocation("enhancedfarming:milk_bottle"));
    	final Item TOOLS[] = {
	        ForgeRegistries.ITEMS.getValue(new ResourceLocation("enhancedfarming:cutting_board")),
	        ForgeRegistries.ITEMS.getValue(new ResourceLocation("enhancedfarming:mortar_and_pestle")),
	        ForgeRegistries.ITEMS.getValue(new ResourceLocation("enhancedfarming:pot"))
    	};

    	
    	if (MILK_BOTTLE != null) {
            InterModComms.sendTo(
                "cookingforblockheads",
                "RegisterMilkItem",
                () -> {
                    LOGGER.info("Registering '{}' as a milk item.", MILK_BOTTLE.getRegistryName().toString());
                    return new ItemStack(MILK_BOTTLE);
                }
            );
    	}

    	for (Item tool : TOOLS) {
    	    if (tool == null) {
    	        continue;
    	    }
    	    
    	    InterModComms.sendTo(
                "cookingforblockheads",
                "RegisterTool",
                () -> {
                    LOGGER.info("Registering '{}' as a tool.", tool.getRegistryName().toString());
                    return new ItemStack(tool);
                }
            );
    	}
    }
}
