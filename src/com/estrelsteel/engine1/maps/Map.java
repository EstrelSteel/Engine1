package com.estrelsteel.engine1.maps;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.estrelsteel.engine1.world.World;

public abstract class Map {
	
	public abstract World loadTiles1(World world);
	
	public abstract World loadEntities(World world);
	
	public abstract World loadChunks(World world);
	
	public World load() {
		World world = new World(650, 650);
		loadTiles1(world);
		loadEntities(world);
		loadChunks(world);
		return world;
	}
	
	public static void generateFile(String name, World world) throws IOException {
		FileWriter fw = new FileWriter("src/com/estrelsteel/engine1/maps/" + name + ".java");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("package com.estrelsteel.engine1.maps;\n\n");
		bw.write("import com.estrelsteel.engine1.tile.Tile;\n");
		bw.write("import com.estrelsteel.engine1.tile.TileType;\n");
		bw.write("import com.estrelsteel.engine1.world.Location;\n");
		bw.write("import com.estrelsteel.engine1.world.World;\n\n");
		bw.write("public class " + name + " extends Map {\n");
		bw.write("\tpublic World loadTiles1(World world) {\n");
		ArrayList<String> worldJava = world.convertToJava(new ArrayList<String>());
		int byteCount = 0;
		int method = 1;
		for(String line : worldJava) {
			byteCount = byteCount + line.getBytes().length;
			bw.write("\t\t" + line + "\n");
			if(byteCount >= 64000) {
				byteCount = 0;
				method = method + 1;
				bw.write("\t\tworld = loadTiles" + method + "(world);\n");
				bw.write("\t\treturn world;\n\t}\n");
				bw.write("\tpublic World loadTiles" + method + "(World world) {\n");
				
			}
		}
		bw.write("\t\treturn world;\n\t}\n");
		bw.write("\tpublic World loadEntities(World world) {\n");
		bw.write("\t\treturn world;\n");
		bw.write("\t}\n");
		bw.write("\tpublic World loadChunks(World world) {\n");
		bw.write("\t\treturn world;\n");
		bw.write("\t}\n");
		bw.write("}");
		bw.flush();
		bw.close();
	}
}
