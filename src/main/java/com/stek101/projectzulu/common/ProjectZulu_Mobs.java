package com.stek101.projectzulu.common;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;

import com.stek101.projectzulu.common.core.CustomEntityManager;
import com.stek101.projectzulu.common.core.DefaultProps;
import com.stek101.projectzulu.common.mobs.ChangeVanillaDrops;
import com.stek101.projectzulu.common.mobs.entitydefaults.AlligatorDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.ArmadilloDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.BearBlackDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.BearBrownDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.BearPolarDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.BeaverDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.BlueFinchDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.BoarDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.CentipedeDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.CrowDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.DeerDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.DuckDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.DuckEggDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.EagleDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.ElephantDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.FishADeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.FishBDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.FollowerDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.FoxDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.FrogDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.GiantRatDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.GiraffeDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.GoatDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.GorillaDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.GreenFinchDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.HauntedArmorDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.HornbillDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.HorseBlackDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.HorseRandomDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.LizardDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.LizardSpitDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.MammothDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.MimicDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.MinotaurDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.MummyDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.OstrichDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.OstrichEggDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.PelicanDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.PenguinDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.PharaohDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.RabbitDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.RedFinchDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.RhinoDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.RipperFinDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.SandwormDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.SkeletonnDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.TreeEntDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.VultureDeclaration;
import com.stek101.projectzulu.common.mobs.entitydefaults.YellowFinchDeclaration;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ProjectZulu_Mobs extends BaseModule {

    @Override
    public String getIdentifier() {
        return DefaultProps.MobsModId;
    }

    @Override
    public void registration(CustomEntityManager manager) {
        manager.addEntity(new ArmadilloDeclaration(), new SandwormDeclaration(), new LizardDeclaration(),
                new LizardSpitDeclaration(), new PharaohDeclaration(), new MummyDeclaration(),
                new VultureDeclaration(), new TreeEntDeclaration(), new MammothDeclaration(), new FoxDeclaration(),
                new BoarDeclaration(), new MimicDeclaration(), new AlligatorDeclaration(), new FrogDeclaration(),
                new PenguinDeclaration(), new BeaverDeclaration(), new BearBlackDeclaration(),
                new BearBrownDeclaration(), new BearPolarDeclaration(), new OstrichDeclaration(),
                new RhinoDeclaration(), new RabbitDeclaration(), new RedFinchDeclaration(),
                new GreenFinchDeclaration(), new BlueFinchDeclaration(), new GorillaDeclaration(),
                new GiraffeDeclaration(), new ElephantDeclaration(), new HorseBlackDeclaration(), 
                //new HorseBrownDeclaration(), new HorseDarkBlackDeclaration(), new HorseBeigeDeclaration(),
                //new HorseDarkBrownDeclaration(), new HorseGreyDeclaration(), new HorseWhiteDeclaration(),
                new EagleDeclaration(), new HornbillDeclaration(), new PelicanDeclaration(), new MinotaurDeclaration(),
                new HauntedArmorDeclaration(), new CentipedeDeclaration(), new FollowerDeclaration(),
                new HorseRandomDeclaration(), new YellowFinchDeclaration(), new GoatDeclaration(), new DuckDeclaration(),
                new DuckEggDeclaration(), new DeerDeclaration(), new SkeletonnDeclaration(), new FishADeclaration(),
                new RipperFinDeclaration(), new OstrichEggDeclaration(), new GiantRatDeclaration(), new FishBDeclaration(),
                new CrowDeclaration());
    }
    
    @Override
    public void init(FMLInitializationEvent event, File configDirectory) {
    	MinecraftForge.EVENT_BUS.register(new ChangeVanillaDrops());    	
    }
}
