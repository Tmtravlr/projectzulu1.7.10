package com.stek101.projectzulu.common.potion.brewingstands;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.stek101.projectzulu.common.ProjectZulu_Core;
import com.stek101.projectzulu.common.core.DefaultProps;
import com.stek101.projectzulu.common.core.GuiID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockBrewingStandBase extends BlockContainer {

    public final int renderID;
    public IIcon potionIcon;

    public BlockBrewingStandBase(int renderID) {
        super(Material.iron);
        this.renderID = renderID;
        setCreativeTab(ProjectZulu_Core.projectZuluCreativeTab);
        setHardness(0.5F);
        setBlockBoundsForItemRender();
    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    @Override
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB,
            List par6List, Entity par7Entity) {
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.60f, 0.75f);
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.60f, 0.75f);
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return renderID;
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int var2);

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what,
            float these, float are) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return super.onBlockActivated(world, x, y, z, player, idk, what, these, are);
        }

        player.openGui(ProjectZulu_Core.modInstance, GuiID.BrewingStand.getID(), world, x, y, z);
        return true;
    }
    
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
    {
        double var6 = (double)((float)p_149734_2_ + 0.4F + p_149734_5_.nextFloat() * 0.2F);
        double var8 = (double)((float)p_149734_3_ + 0.7F + p_149734_5_.nextFloat() * 0.3F);
        double var10 = (double)((float)p_149734_4_ + 0.4F + p_149734_5_.nextFloat() * 0.2F);
        p_149734_1_.spawnParticle("smoke", var6, var8, var10, 0.0D, 0.0D, 0.0D);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        potionIcon = par1IconRegister.registerIcon(DefaultProps.blockKey + ":" + "potion");
        super.registerBlockIcons(par1IconRegister);
    }
    
    
}
