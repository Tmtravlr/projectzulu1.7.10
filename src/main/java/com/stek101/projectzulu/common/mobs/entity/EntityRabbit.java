package com.stek101.projectzulu.common.mobs.entity;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.stek101.projectzulu.common.api.CustomEntityList;
import com.stek101.projectzulu.common.core.DefaultProps;
import com.stek101.projectzulu.common.mobs.EntityAFightorFlight;
import com.stek101.projectzulu.common.mobs.entityai.EntityAIFollowParent;
import com.stek101.projectzulu.common.mobs.entityai.EntityAIHurtByTarget;
import com.stek101.projectzulu.common.mobs.entityai.EntityAIMate;
import com.stek101.projectzulu.common.mobs.entityai.EntityAINearestAttackableTarget;
import com.stek101.projectzulu.common.mobs.entityai.EntityAIPanic;
import com.stek101.projectzulu.common.mobs.entityai.EntityAITempt;
import com.stek101.projectzulu.common.mobs.entityai.EntityAIWander;

public class EntityRabbit extends EntityGenericAnimal implements IAnimals {
	private EntityAFightorFlight EAFF;
	private CustomEntityList entityEntry;
	private float aggroLevel;
	private double aggroRange;

    public EntityRabbit(World par1World) {
        super(par1World);
        setSize(0.5f, 0.5f);
        
        Random rand1 = new Random();
        this.textureID = rand1.nextInt(3);
        
      this.entityEntry = CustomEntityList.getByName(EntityList.getEntityString(this));
        
        /* Check if aggroLevel and aggroRange have valid values to activate AFoF */
   	  if (entityEntry != null && entityEntry.modData.get().entityProperties != null) {
           this.aggroLevel = entityEntry.modData.get().entityProperties.aggroLevel;
           this.aggroRange = entityEntry.modData.get().entityProperties.aggroRange;                    
         }
   	  
   	  if (Math.round(this.aggroRange) != 0) {
   		  EAFF = new EntityAFightorFlight().setEntity(this, worldObj, this.aggroLevel, this.aggroRange);
   	  }
   	  
        getNavigator().setAvoidsWater(true);

        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIPanic(this, 1.25f, true));
        // tasks.addTask(3, new EntityAIAttackOnCollide(this, moveSpeed, false));
        // tasks.addTask(4, new EntityAIFollowOwner(this, moveSpeed, 10.0F, 2.0F));

        tasks.addTask(5, new EntityAIMate(this, 1.0f, true));
        tasks.addTask(6, new EntityAITempt(this, 1.2f, Items.carrot, false, true));
        tasks.addTask(6, new EntityAITempt(this, 1.2f, Items.apple, false, true));

        tasks.addTask(7, new EntityAIFollowParent(this, 1.1f));
        tasks.addTask(9, new EntityAIWander(this, 1.0f, 40, true));

        targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, false));
        targetTasks.addTask(4,
                new EntityAINearestAttackableTarget(this, EnumSet.of(EntityStates.attacking, EntityStates.looking),
                        EntityPlayer.class, 16.0F, 0, true));
        // targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityLiving.class, 16.0F, 0, false, true,
        // IMob.mobSelector));
    }
    
    @Override
    protected boolean isValidLocation(World world, int xCoord, int yCoord, int zCoord) {
        return worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord);
    }
    
    @Override
    public void onLivingUpdate() {
    	super.onLivingUpdate();
    	if (Math.round(this.aggroRange) != 0) {
    		EAFF.updateEntityAFF(worldObj);
    	}
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    @Override
    public int getTotalArmorValue() {
        return 0;
    }
    
    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound() {
        return DefaultProps.mobKey + ":" + DefaultProps.entitySounds + "rabbitlivingsound";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound() {
        return DefaultProps.mobKey + ":" + DefaultProps.entitySounds + "rabbithurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound() {
        return DefaultProps.mobKey + ":" + DefaultProps.entitySounds + "rabbitdead";
    }
    
    @Override
    public int getTalkInterval() {
        return 160;
    }

    @Override
    public boolean isValidBreedingItem(ItemStack itemStack) {
        if (itemStack != null && (itemStack.getItem() == Items.apple || itemStack.getItem() == Items.carrot)) {
        	this.setAngerLevel(0);
        	this.setFleeTick(0);
        	return true;
        } else {
            return super.isValidBreedingItem(itemStack);
        }
    }

    @Override
    protected boolean shouldPanic() {
        return true;
    }

    @Override
    public boolean shouldNotifySimilar(EntityPlayer attackingPlayer) {
        return true;
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
    protected void func_145780_a(int xCoord, int yCoord, int zCoord, Block stepBlock) { /* Does Not Play a Step Sound */
    }
}