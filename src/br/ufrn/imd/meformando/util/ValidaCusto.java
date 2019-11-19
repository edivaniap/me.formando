package br.ufrn.imd.meformando.util;


public class ValidaCusto {
	
	public static boolean isCustoValido(Double custo) {
		boolean isCustoValid = true;
		String str = Double.toString(custo);
		if(!str.matches("[0-9]*")) {
			isCustoValid = false;
		}
		
		return isCustoValid;
	}
}
