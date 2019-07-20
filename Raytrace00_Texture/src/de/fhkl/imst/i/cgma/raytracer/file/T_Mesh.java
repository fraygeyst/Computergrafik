package de.fhkl.imst.i.cgma.raytracer.file;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class T_Mesh extends RT_Object {
	// read information
	public float[][] materials;
	public int[] materialsN;

	public float[][] vertices;
	public int[] verticesMat;

	public int[][] triangles;

	public char fgp='f';  // flat, gouraud, phong

	// calculated information
	public float[][] vertexNormals;
	public float[][] vertexColors; 

	public float[][] triangleNormals;
	public float[][] triangleColors;
	public float[] triangleAreas;

	// Neu für Textur
	public float []u;
	public float []v;
	public int tex_index;

	@Override
	public String getHeader() {
		return "TRIANGLE_MESH";
	}


	@Override
	public void readContent(LineNumberReader br) throws IOException {
		// dateiinformationen lesen
		Pattern pInfo = Pattern.compile(fInfoRegex);
		Pattern pMaterial = Pattern.compile(materialRegex);
		Pattern pVertex = Pattern.compile(vertexRegex);
		Pattern pTriangle = Pattern.compile(triangleRegex);

		//Fuer Texture
		Pattern pUV = Pattern.compile(uvRegex);
		Pattern pTextureID = Pattern.compile(textureRegex);

		Matcher matcher = pInfo.matcher(readLine(br));
		if(!matcher.matches())
			throw new IOException("Ungültiges Dateiformat!");

		int nExpVerts, nExpTriangles, nExpMaterials, nExpUV, nExpTextureID;
		nExpVerts = Integer.parseInt(matcher.group(1));
		nExpTriangles = Integer.parseInt(matcher.group(2));
		nExpMaterials = Integer.parseInt(matcher.group(3));

		//Fuer Texture
		nExpUV = Integer.parseInt(matcher.group(5));
		nExpTextureID = Integer.parseInt(matcher.group(6));

		fgp=matcher.group(4).charAt(0);
		materials = new float[nExpMaterials][9];	// ar ag ab dr dg db sr sg sb
		materialsN = new int[nExpMaterials];		// n
		vertices = new float[nExpVerts][3];		// x y z
		verticesMat = new int[nExpVerts];			// Materialindex
		triangles = new int[nExpTriangles][3];		// i1 i2 i3

		//fuer Texture
		u = new float[nExpUV];
		v = new float[nExpUV];

		// Materialien lesen
		for(int i = 0; i < nExpMaterials; ++i) {
			matcher = pMaterial.matcher(readLine(br).trim());
			if(!matcher.matches()) {
				throw new IOException("Ungültiges Dateiformat! " + br.getLineNumber());
			}

			for(int j = 0; j < 9; ++j)
				materials[i][j] = Float.parseFloat(matcher.group(j+1));
			materialsN[i] = Integer.parseInt(matcher.group(10));
		}

		// Vertices lesen
		for(int i = 0; i < nExpVerts; i++) {
			matcher = pVertex.matcher(readLine(br).trim());
			if(!matcher.matches())
				throw new IOException("Ungültiges Dateiformat! " + br.getLineNumber());

			for(int j = 0; j < 3; ++j)
				vertices[i][j] = Float.parseFloat(matcher.group(1+j));
			verticesMat[i] = Integer.parseInt(matcher.group(4));
		}

		// Dreiecke lesen
		for(int i = 0; i < nExpTriangles; i++) {
			matcher = pTriangle.matcher(readLine(br).trim());
			if(!matcher.matches())
				throw new IOException("Ungültiges Dateiformat! " + br.getLineNumber());

			for(int j = 0; j < 3; ++j)
				triangles[i][j] = Integer.parseInt(matcher.group(j+1));
		}

		// UV lesen
		for(int i = 0; i < nExpUV; i++) {
			matcher = pUV.matcher(readLine(br).trim());
			if(!matcher.matches())
				throw new IOException("Ungültiges Dateiformat! " + br.getLineNumber());

			u[i] = Float.parseFloat(matcher.group(1));
			v[i] = Float.parseFloat(matcher.group(2));
		}

		//textureId einlesen
		for(int i = 0; i < nExpTextureID; i++) {
			matcher = pTextureID.matcher(readLine(br).trim());
			if(!matcher.matches())
				throw new IOException("Ungültiges Dateiformat! " + br.getLineNumber());
			tex_index= Integer.parseInt(matcher.group(1));
		}
		

		// BBox berechnen
		calcBoundingBox();
	}

	@Override
	public void calcBoundingBox() {
	/*	min[0] = vertices[0][0];
		min[1] = vertices[0][1];
		min[2] = vertices[0][2];
		max[0] = vertices[0][0];
		max[1] = vertices[0][1];
		max[2] = vertices[0][2];
		for (int i = 1; i < vertices.length; ++i) {
			for (int j = 0; j < 3; j++) {
				if (min[j] > vertices[i][j])
					min[j] = vertices[i][j];
				if (max[j] < vertices[i][j])
					max[j] = vertices[i][j];
			}
		}
	 */
	}



	private static final String fInfoRegex =
			"([0-9]*) ([0-9]*) ([0-9]*) ([fgptFGPT]) ([0-9]*) ([0-9]*)";
	private static final String materialRegex =
			"(\\-?[0-9]+\\.[0-9]+) +(\\-?[0-9]+\\.[0-9]+) +(\\-?[0-9]+\\.[0-9]+) +(\\-?[0-9]+\\.[0-9]+) +(\\-?[0-9]+\\.[0-9]+) +(\\-?[0-9]+\\.[0-9]+) +(\\-?[0-9]+\\.[0-9]+) +(\\-?[0-9]+\\.[0-9]+) +(\\-?[0-9]+\\.[0-9]+) +([0-9]+)";
	private static final String vertexRegex =
			"(\\-?[0-9]+\\.[0-9]+) +(\\-?[0-9]+\\.[0-9]+) +(\\-?[0-9]+\\.[0-9]+) ([0-9]+)";
	private static final String triangleRegex =
			"([0-9]+) +([0-9]+) +([0-9]+)";

	//Fuer Texture
	private static final String uvRegex =
			"(\\-?[0-9]+\\.[0-9]+) +(\\-?[0-9]+\\.[0-9]+)";
	private static final String textureRegex =
			"([0-9]*)";
}
