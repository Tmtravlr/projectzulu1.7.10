package com.stek101.projectzulu.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;

import com.stek101.projectzulu.common.core.CreativePZGeneralTab;
import com.stek101.projectzulu.common.core.CreativePZPotionTab;
import com.stek101.projectzulu.common.core.CustomEntityManager;
import com.stek101.projectzulu.common.core.DefaultProps;
import com.stek101.projectzulu.common.core.EventHookContainerClass;
import com.stek101.projectzulu.common.core.ItemBlockManager;
//import com.stek101.projectzulu.common.core.PacketPipeline;
import com.stek101.projectzulu.common.core.ProjectZuluLog;
import com.stek101.projectzulu.common.core.ZuluGuiHandler;
import com.stek101.projectzulu.common.core.network.PZPacketHandler;
import com.stek101.projectzulu.common.core.terrain.FeatureGenerator;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/*Useful OpenSource reference to Look at: ExtrabiomesXL, Gaurdsman*/
@Mod(modid = DefaultProps.CoreModId, name = "Project Zulu Core", dependencies = DefaultProps.DesiredBefore, version = DefaultProps.VERSION_STRING, useMetadata = true)
public class ProjectZulu_Core {

    @Instance(DefaultProps.CoreModId)
    public static ProjectZulu_Core modInstance;

    private static int defaulteggID = 300;

    public static int getNextDefaultEggID() {
        return defaulteggID++;
    }

    public static final CreativeTabs projectZuluCreativeTab = new CreativePZGeneralTab(
            CreativeTabs.creativeTabArray.length, "projectZuluTab");
    public static final CreativeTabs projectZuluPotionTab = new CreativePZPotionTab(
            CreativeTabs.creativeTabArray.length, "projectZuluPotionTab");

    public static boolean enableTestBlock = false;
    public static boolean enableTemperature = false;

    public static int testBlockID = 2540;
    public static Block testBlock;

    /* Material Declarations */
    public static final ArmorMaterial desertClothMaterial = EnumHelper.addArmorMaterial("Desert Cloth Material", 2,
            new int[] { 1, 2, 1, 1 }, 15);
    public static final ArmorMaterial scaleMaterial = EnumHelper.addArmorMaterial("Scale Material", 5, new int[] { 1,
            3, 2, 1 }, 15);
    public static final ArmorMaterial furMaterial = EnumHelper.addArmorMaterial("Fur Material", 3, new int[] { 1, 3, 2,
            1 }, 13);
    public static final ArmorMaterial goldScaleMaterial = EnumHelper.addArmorMaterial("Gold Scale Material", 7,
            new int[] { 2, 5, 3, 1 }, 25);
    public static final ArmorMaterial ironScaleMaterial = EnumHelper.addArmorMaterial("Iron Scale Material", 15,
            new int[] { 2, 6, 5, 2 }, 9);
    public static final ArmorMaterial diamondScaleMaterial = EnumHelper.addArmorMaterial("Diamond Scale Material", 33,
            new int[] { 3, 8, 6, 3 }, 10);
    
    public static final ArmorMaterial steelMaterial = EnumHelper.addArmorMaterial("Steel Material", 20, 
    		new int[] { 3, 8, 6, 3 }, 10);

    public static File modConfigDirectoryFile;

    public static final FeatureGenerator featureGenerator = new FeatureGenerator();

 //   private static PacketPipeline packetPipeline;
    
    public static final PZPacketHandler packetHandler = new PZPacketHandler();

//    public static PacketPipeline getPipeline() {
//        return packetPipeline;
//    }
    
	public static String Updates = "";
	public static boolean modOutDated = false;
	public static boolean checkForUpdates = true;
	//UpdateEventHandler UEH = new UpdateEventHandler();

    @SidedProxy(clientSide = "com.stek101.projectzulu.common.ClientProxyProjectZulu", serverSide = "com.stek101.projectzulu.common.CommonProxyProjectZulu")
    public static CommonProxyProjectZulu proxy;

    private class ModuleInfo {
        public Module module;
        public boolean isEnabled;

        public ModuleInfo(Module module) {
            this.module = module;
            isEnabled = true;
        }

        public String getIdentifier() {
            return module.getIdentifier();
        }
    }

    private List<ModuleInfo> modules = new ArrayList<ProjectZulu_Core.ModuleInfo>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //if (checkForUpdates)
		//	UpdateMonitor.checkForUpdates();
        //FMLCommonHandler.instance().bus().register(new UpdateEventHandler());

        modConfigDirectoryFile = event.getModConfigurationDirectory();

        ProjectZuluLog.configureLogging(modConfigDirectoryFile);
        attemptLoadModule("com.stek101.projectzulu.common.ProjectZulu_Blocks");
        attemptLoadModule("com.stek101.projectzulu.common.ProjectZulu_Mobs");
        attemptLoadModule("com.stek101.projectzulu.common.ProjectZulu_Dungeon");
        attemptLoadModule("com.stek101.projectzulu.common.ProjectZulu_World");

        Configuration moduleConfig = new Configuration(new File(event.getModConfigurationDirectory(),
                DefaultProps.configDirectory + DefaultProps.moduleConfigFile));
        moduleConfig.load();
        for (ModuleInfo moduleInfo : modules) {
            moduleInfo.isEnabled = moduleConfig.get("isEnabled", moduleInfo.getIdentifier(), true,
                    "Set to false to disable module.").getBoolean(true);
            if (moduleInfo.isEnabled) {
                ProjectZuluLog.info("Module [%s] settings read and will be loaded.", moduleInfo.getIdentifier());
            } else {
                ProjectZuluLog.info("Module [%s] settings read and will be disabled.", moduleInfo.getIdentifier());
            }
        }
        moduleConfig.save();
        Configuration zuluConfig = new Configuration(new File(event.getModConfigurationDirectory(),
                DefaultProps.configDirectory + DefaultProps.defaultConfigFile));
        Properties.loadFromConfig(modConfigDirectoryFile);
        zuluConfig.load();
        enableTemperature = zuluConfig.get("General Controls", "enableTemperature", enableTemperature).getBoolean(
                enableTemperature);
        zuluConfig.save();

        proxy.bossHealthTicker();
//        packetPipeline = new PacketPipeline("ProjectZulu");
        this.packetHandler.init();
        
        for (ModuleInfo moduleInfo : modules) {
            if (moduleInfo.isEnabled) {
                moduleInfo.module.registration(CustomEntityManager.INSTANCE);
                moduleInfo.module.registration(ItemBlockManager.INSTANCE);
                moduleInfo.module.registration(featureGenerator);
                moduleInfo.module.preInit(event, modConfigDirectoryFile);
            }
        }

        ProjectZuluLog.info("Load Entity Moxdels and Render");
        ProjectZulu_Core.proxy.registerModelsAndRender();

        ProjectZuluLog.info("Load Entity Properties");
        CustomEntityManager.INSTANCE.loadCreaturesFromConfig(modConfigDirectoryFile);

        ProjectZuluLog.info("Starting ItemBlock Setup");
        ItemBlockManager.INSTANCE.createBlocks(modConfigDirectoryFile);

        ProjectZuluLog.info("Starting ItemBlock Registration");
        ItemBlockManager.INSTANCE.registerBlocks();
        ProjectZuluLog.info("Completed ItemBlock Registration");
        
        ProjectZuluLog.info("Registering Entites");
        CustomEntityManager.INSTANCE.registerEntities(modConfigDirectoryFile); 
    }

    private void attemptLoadModule(String classResourceName) {
        try {
            Class moduleClass = Class.forName(classResourceName);
            if (Module.class.isAssignableFrom(moduleClass)) {
                Module module = (Module) (moduleClass.newInstance());
                ProjectZuluLog.info("Detected %s module.", module.getIdentifier());
                modules.add(new ModuleInfo(module));
            }
        } catch (ClassNotFoundException e) {
            ProjectZuluLog.info("Module [%s] missing; will be disabled.", classResourceName);
        } catch (InstantiationException e) {
            ProjectZuluLog.severe("Failed to instantiate %s, report to modder.", classResourceName);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            ProjectZuluLog.severe("Failed to instantiate %s, report to modder.", classResourceName);
            e.printStackTrace();
        }
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ProjectZulu_Core.modInstance, new ZuluGuiHandler());
//        packetPipeline.initialise(event);
        for (ModuleInfo moduleInfo : modules) {
            if (moduleInfo.isEnabled) {
                moduleInfo.module.init(event, modConfigDirectoryFile);
            }
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        BiomeDictionary.registerAllBiomes();

        ProjectZuluLog.info("Registering Events");
        MinecraftForge.EVENT_BUS.register(new EventHookContainerClass());

        ProjectZuluLog.info("Load Entity Biomes");
        CustomEntityManager.INSTANCE.loadBiomesFromConfig(modConfigDirectoryFile);
        ProjectZuluLog.info("Register Entity Spawns");
        CustomEntityManager.INSTANCE.addSpawns();

        ProjectZuluLog.info("Initializing TerrainFeatures");
        featureGenerator.initialize(modConfigDirectoryFile);
        GameRegistry.registerWorldGenerator(featureGenerator, 1);
        for (ModuleInfo moduleInfo : modules) {
            if (moduleInfo.isEnabled) {
                moduleInfo.module.postInit(event, modConfigDirectoryFile);
            }
        }
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        for (ModuleInfo moduleInfo : modules) {
            if (moduleInfo.isEnabled) {
                moduleInfo.module.serverStarting(event, modConfigDirectoryFile);
            }
        }
    }

    @EventHandler
    public void serverStart(FMLServerStartedEvent event) {
        for (ModuleInfo moduleInfo : modules) {
            if (moduleInfo.isEnabled) {
                moduleInfo.module.serverStart(event, modConfigDirectoryFile);
            }
        }
    }   

}
