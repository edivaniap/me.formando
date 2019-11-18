package br.ufrn.imd.meformando.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidaData {
	public boolean isDataValida(String date) {
	      try {
	         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	         LocalDate d = LocalDate.parse(date, formatter);    
	         return true;
	      } catch (DateTimeParseException e) {
	        return false;
	      }   
	   }

}
