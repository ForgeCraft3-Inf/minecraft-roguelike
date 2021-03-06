package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public enum Door {

	IRON, OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARKOAK;
	
	public static void generate(IWorldEditor editor, Coord pos, Cardinal dir, Door type){
		generate(editor, pos, dir, type, false);
	}
	
	public static void generate(IWorldEditor editor, Coord pos, Cardinal dir, Door type, boolean open){
		
		Coord cursor = new Coord(pos);
		MetaBlock doorBase = getMeta(type, false, dir, open, false);
		doorBase.set(editor, cursor);
		cursor.add(Cardinal.UP);
		MetaBlock doorTop = getMeta(type, true, dir, open, false);
		doorTop.set(editor, cursor);
	}
	
	private static MetaBlock getMeta(Door type, boolean top, Cardinal dir, boolean open, boolean hingeLeft){
		
		IBlockState door;
		switch(type){
		case IRON: door = new MetaBlock(Blocks.IRON_DOOR.getDefaultState()); break;
		case BIRCH: door = new MetaBlock(Blocks.BIRCH_DOOR.getDefaultState()); break;
		case SPRUCE: door = new MetaBlock(Blocks.SPRUCE_DOOR.getDefaultState()); break;
		case JUNGLE: door = new MetaBlock(Blocks.JUNGLE_DOOR.getDefaultState()); break;
		case ACACIA: door = new MetaBlock(Blocks.ACACIA_DOOR.getDefaultState()); break;
		case DARKOAK: door = new MetaBlock(Blocks.DARK_OAK_DOOR.getDefaultState()); break;
		default: door = new MetaBlock(Blocks.OAK_DOOR.getDefaultState()); break;
		}
		
		door = door.withProperty(BlockDoor.HALF, top ? BlockDoor.EnumDoorHalf.UPPER : BlockDoor.EnumDoorHalf.LOWER);
		
		door = door.withProperty(BlockDoor.FACING, Cardinal.facing(dir));
		
		door = door.withProperty(BlockDoor.OPEN, open);
		
		door = door.withProperty(BlockDoor.HINGE, hingeLeft ? BlockDoor.EnumHingePosition.LEFT : BlockDoor.EnumHingePosition.RIGHT);
		
		return new MetaBlock(door);
	}

}
