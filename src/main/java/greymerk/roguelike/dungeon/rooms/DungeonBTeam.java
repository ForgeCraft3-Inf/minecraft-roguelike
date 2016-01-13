package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Record;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.worldgen.BlockCheckers;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.BrewingStand;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Crops;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.Slab;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Trapdoor;
import greymerk.roguelike.worldgen.blocks.Wood;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class DungeonBTeam extends DungeonBase {

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = new MetaStair(StairType.SPRUCE);
		MetaBlock log = Log.getLog(Wood.OAK, Cardinal.UP);
		MetaBlock stonebrick = BlockType.get(BlockType.STONE_BRICK);
		MetaBlock cyan = ColorBlock.get(ColorBlock.CLAY, DyeColor.CYAN);
		MetaBlock slab = Slab.get(Slab.STONE, false, true, true);
		MetaBlock cobble = BlockType.get(BlockType.COBBLESTONE);
		MetaBlock lamp = BlockType.get(BlockType.REDSTONE_LAMP);
				
		Cardinal dir = entrances[0];
		
		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(dir, 5);
		end.add(Cardinal.reverse(dir), 4);
		start.add(Cardinal.left(dir), 6);
		end.add(Cardinal.right(dir), 6);
		end.add(Cardinal.UP, 5);
		start.add(Cardinal.DOWN);
		stonebrick.fillRectHollow(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(dir, 4);
		end.add(Cardinal.reverse(dir), 3);
		start.add(Cardinal.left(dir), 5);
		end.add(Cardinal.right(dir), 5);
		cobble.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(dir, 3);
		end.add(Cardinal.reverse(dir), 2);
		start.add(Cardinal.left(dir), 4);
		end.add(Cardinal.right(dir), 4);
		cyan.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(dir, 2);
		end.add(Cardinal.reverse(dir), 1);
		start.add(Cardinal.left(dir), 3);
		end.add(Cardinal.right(dir), 3);
		slab.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 4);
		logWall(editor, rand, dir, cursor);
		cursor.add(dir, 9);
		logWall(editor, rand, Cardinal.reverse(dir), cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.left(dir), 6);
		tvWall(editor, rand, Cardinal.left(dir), cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.right(dir), 6);
		bWall(editor, rand, Cardinal.right(dir), cursor);
		
		table(editor, rand, dir, origin);
		
		start = new Coord(origin);
		start.add(Cardinal.reverse(dir), 4);
		end = new Coord(start);
		start.add(Cardinal.left(dir));
		end.add(Cardinal.right(dir));
		end.add(Cardinal.UP, 2);
		cobble.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 4);
		air.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		air.setBlock(editor, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.left(dir), 3);
		cursor.add(Cardinal.UP, 5);
		log.setBlock(editor, cursor);
		cursor.add(dir, 3);
		log.setBlock(editor, cursor);
		cursor.add(Cardinal.right(dir), 6);
		log.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir), 3);
		log.setBlock(editor, cursor);
		
		start = new Coord(origin);
		start.add(Cardinal.reverse(dir));
		start.add(Cardinal.UP, 5);
		end = new Coord(start);
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.right(dir), 2);
		stair.setOrientation(dir, true).fillRectSolid(editor, rand, start, end, true, true);
		start.add(dir, 3);
		end.add(dir, 3);
		stair.setOrientation(Cardinal.reverse(dir), true).fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal d : Cardinal.orthogonal(dir)){
			start = new Coord(origin);
			start.add(Cardinal.UP, 5);
			start.add(d, 3);
			end = new Coord(start);
			end.add(dir);
			stair.setOrientation(Cardinal.reverse(d), true).fillRectSolid(editor, rand, start, end, true, true);
		}
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 5);
		end = new Coord(start);
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.right(dir), 2);
		end.add(dir);
		lamp.fillRectSolid(editor, rand, start, end, true, true);

		cursor = new Coord(origin);
		cursor.add(dir, 4);
		cursor.add(Cardinal.right(dir), 5);
		BlockType.get(BlockType.SHELF).setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		BrewingStand.get().setBlock(editor, cursor);
		
		cursor = new Coord(origin);
		cursor.add(dir, 4);
		cursor.add(Cardinal.left(dir), 4);
		BlockType.get(BlockType.JUKEBOX).setBlock(editor, cursor);
		cursor.add(Cardinal.left(dir));
		ITreasureChest stal = Treasure.generate(editor, rand, cursor, Treasure.EMPTY, settings.getDifficulty(cursor));
		stal.setSlot(stal.getSize() / 2, Record.getRecord(Record.STAL));
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 3);
		cursor.add(Cardinal.left(dir), 4);
		ITreasureChest bdub = Treasure.generate(editor, rand, cursor, Treasure.EMPTY, settings.getDifficulty(cursor));
		bdub.setSlot((bdub.getSize() / 2) - 2, ItemNovelty.getItem(ItemNovelty.BDOUBLEO));
		ItemStack shirt = new ItemStack(Items.leather_chestplate);
		Loot.setItemName(shirt, "Pink Sweater", null);
		Loot.setItemLore(shirt, "\"It's chinese red!\"");
		ItemArmour.dyeArmor(shirt, 250, 96, 128);
		bdub.setSlot((bdub.getSize() / 2) + 2, shirt);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 3);
		cursor.add(Cardinal.right(dir), 4);
		ITreasureChest genny = Treasure.generate(editor, rand, cursor, Treasure.EMPTY, settings.getDifficulty(cursor));
		genny.setSlot(genny.getSize() / 2, ItemNovelty.getItem(ItemNovelty.GENERIKB));
		
		return true;
	}
	
	private void table(IWorldEditor editor, Random rand, Cardinal dir, Coord origin){

		IStair stair = new MetaStair(StairType.SPRUCE);
		IStair chair = new MetaStair(StairType.NETHERBRICK);
		MetaBlock slab = Slab.get(Slab.SPRUCE, true, false, false);
		
		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		start.add(Cardinal.left(dir));
		end = new Coord(origin);
		end.add(Cardinal.right(dir));
		end.add(dir);
		slab.fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal d : Cardinal.orthogonal(dir)){
			start = new Coord(origin);
			start.add(d, 2);
			end = new Coord(start);
			end.add(dir);
			stair.setOrientation(d, true).fillRectSolid(editor, rand, start, end, true, true);
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir), 2);
		for(Cardinal d : Cardinal.orthogonal(dir)){
			Coord c = new Coord(cursor);
			c.add(d);
			chair.setOrientation(dir, false).setBlock(editor, c);
		}
		
		cursor.add(dir, 5);
		for(Cardinal d : Cardinal.orthogonal(dir)){
			Coord c = new Coord(cursor);
			c.add(d);
			chair.setOrientation(Cardinal.reverse(dir), false).setBlock(editor, c);
		}
	}
	
	private void lamp(IWorldEditor editor, Random rand, Cardinal dir, Coord origin){
		MetaBlock fence = BlockType.get(BlockType.FENCE);
		MetaBlock plank = Wood.getPlank(Wood.SPRUCE);
		
		Coord cursor;
		
		cursor = new Coord(origin);
		plank.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		fence.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		BlockType.get(BlockType.GLOWSTONE).setBlock(editor, cursor);
		for(Cardinal d : Cardinal.directions){
			if(d == Cardinal.reverse(dir)) continue;
			
			Coord c = new Coord(cursor);
			c.add(d);
			Trapdoor.get(Trapdoor.OAK, Cardinal.reverse(d), false, true).setBlock(editor, c);
		}
		cursor.add(Cardinal.UP);
		fence.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		plank.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		plank.setBlock(editor, cursor);
	}
	
	private void logWall(IWorldEditor editor, Random rand, Cardinal dir, Coord origin){

		Coord start;
		Coord end;
		Coord cursor;
		
		IStair stair = new MetaStair(StairType.SPRUCE);
		MetaBlock plank = Wood.getPlank(Wood.SPRUCE);
		BlockCheckers checkers = new BlockCheckers(
				Log.getLog(Wood.SPRUCE, Cardinal.UP),
				Log.getLog(Wood.SPRUCE, Cardinal.left(dir))
				);

		start = new Coord(origin);
		start.add(Cardinal.UP);
		end = new Coord(start);
		start.add(Cardinal.left(dir), 4);
		end.add(Cardinal.right(dir), 4);
		end.add(Cardinal.UP, 2);
		checkers.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(start);
		start.add(Cardinal.left(dir), 5);
		end.add(Cardinal.right(dir), 5);
		plank.fillRectSolid(editor, rand, start, end, true, true);
		start.add(dir);
		end.add(dir);
		start.add(Cardinal.UP, 4);
		end.add(Cardinal.UP, 4);
		stair.setOrientation(dir, true).fillRectSolid(editor, rand, start, end, true, true);
		
		for(Cardinal d : Cardinal.orthogonal(dir)){
			start = new Coord(origin);
			start.add(d, 5);
			start.add(Cardinal.UP);
			end = new Coord(start);
			end.add(Cardinal.UP, 2);
			Log.getLog(Wood.SPRUCE, Cardinal.UP).fillRectSolid(editor, rand, start, end, true, true);
			
			cursor = new Coord(origin);
			cursor.add(dir);
			cursor.add(d, 3);
			lamp(editor, rand, dir, cursor);
		}
		

	}
	
	private void bWall(IWorldEditor editor, Random rand, Cardinal dir, Coord origin){
		BlockJumble bricks = new BlockJumble();
		bricks.addBlock(BlockType.get(BlockType.STONE_BRICK));
		bricks.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED));
		bricks.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY));
		MetaBlock plank = Wood.getPlank(Wood.SPRUCE);
		MetaBlock b = RogueConfig.getBoolean(RogueConfig.GENEROUS)
			? BlockType.get(BlockType.EMERALD_BLOCK)
			: ColorBlock.get(ColorBlock.GLASS, DyeColor.LIME);

		Coord start;
		Coord end;
		Coord cursor;
		
		start = new Coord(origin);
		end = new Coord(start);
		start.add(Cardinal.right(dir), 3);
		end.add(Cardinal.left(dir), 4);
		plank.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.UP);
		end = new Coord(start);
		start.add(Cardinal.right(dir), 3);
		end.add(Cardinal.left(dir), 4);
		end.add(Cardinal.UP, 3);
		bricks.fillRectSolid(editor, rand, start, end, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.reverse(dir));
		for(int i = 0; i < 5; ++i){
			
			if(i % 2 == 0){
				start = new Coord(cursor);
				end = new Coord(start);
				end.add(Cardinal.left(dir), 2);
				b.fillRectSolid(editor, rand, start, end, true, true);
			} else {
				Coord c = new Coord(cursor);
				c.add(Cardinal.right(dir));
				b.setBlock(editor, c);
				c.add(Cardinal.left(dir), 3);
				b.setBlock(editor, c);
			}
			
			cursor.add(Cardinal.UP);
		}
		
	}
	
	private void tvWall(IWorldEditor editor, Random rand, Cardinal dir, Coord origin){

		Coord start;
		Coord end;
		Coord cursor;
		
		MetaBlock plank = Wood.getPlank(Wood.SPRUCE);
		MetaBlock shelf = BlockType.get(BlockType.SHELF);
		MetaBlock jungle = Log.getLog(Wood.JUNGLE, dir);
		MetaBlock note = BlockType.get(BlockType.NOTEBLOCK);
		MetaBlock black = ColorBlock.get(ColorBlock.WOOL, DyeColor.BLACK);
		MetaBlock bean = Crops.getCocao(dir);
		MetaBlock slab = Slab.get(Slab.SPRUCE, true, false, false);
		
		start = new Coord(origin);
		start.add(Cardinal.reverse(dir));
		start.add(Cardinal.UP, 4);
		end = new Coord(start);
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.right(dir), 3);
		slab.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(Cardinal.left(dir), 3);
		end.add(Cardinal.right(dir), 4);
		plank.fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.right(dir), 2);
		end.add(Cardinal.left(dir), 2);
		note.fillRectSolid(editor, rand, start, end, true, true);
		start.add(Cardinal.UP);
		end.add(Cardinal.UP, 3);
		black.fillRectSolid(editor, rand, start, end, true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.left(dir), 2);
		start.add(Cardinal.UP);
		end = new Coord(start);
		end.add(Cardinal.left(dir));
		end.add(Cardinal.UP, 2);
		shelf.fillRectSolid(editor, rand, start, end, true, true);
		cursor = new Coord(start);
		cursor.add(Cardinal.UP);
		jungle.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		bean.setBlock(editor, cursor);
		
		start = new Coord(origin);
		start.add(Cardinal.right(dir), 3);
		start.add(Cardinal.UP);
		end = new Coord(start);
		end.add(Cardinal.right(dir));
		end.add(Cardinal.UP, 2);
		shelf.fillRectSolid(editor, rand, start, end, true, true);
		cursor = new Coord(start);
		cursor.add(Cardinal.UP);
		jungle.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		bean.setBlock(editor, cursor);		
		
	}
	
	public int getSize(){
		return 8;
	}
	
	public boolean validLocation(IWorldEditor editor, Cardinal dir, int x, int y, int z){
		
		List<Coord> box = editor.getRectHollow(new Coord(x - 7, y - 2, z - 7), new Coord(x + 7, y + 5, z + 7));
		
		for(Coord pos : box){
			MetaBlock b = editor.getBlock(pos);
			if(!b.getBlock().getMaterial().isSolid()) return false;
		}
		
		return true;
	}
	
	
}
