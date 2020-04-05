package controller;

import java.util.Map;



import model.LindenmayerModel;


public class LindenmayerController {
	
	private LindenmayerModel model;
	private String[] replacements;
	private String currentMap;
	private String newMap;
	
	public LindenmayerController(LindenmayerModel model) {
		this.model = model;
	}
	
	
		
	public void makeReplacements(int iterations, String axiom, Map<String, String> map) {
		
		replacements = new String[iterations + 1];
		replacements[0] = axiom;
		currentMap = axiom;
		
		for (int i = 0; i < iterations; i++) {
			newMap = "";
			for(int j = 0; j < currentMap.length(); j++) {
				if(currentMap.charAt(j) == 'F' || currentMap.charAt(j) == 'G') {
					newMap += map.get(currentMap.charAt(j) + "");
				} else {
					newMap += currentMap.charAt(j);
				}
			}
			
			currentMap = newMap;
			replacements[i + 1] = currentMap;
		}
		
	}
	
	public String getReplacements() {
		return currentMap;
	}
}

    


