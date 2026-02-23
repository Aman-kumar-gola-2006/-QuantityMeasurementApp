package com.apps.QuantityMeasurementApp;


public class QuantityMeasurementApp {

	//Inner class to represent Feet measurement  
	public static class Feet{
		private final double value;
		
		public Feet(double value) {
          this.value=value;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			//Reference check
			if(this==obj)
			{
				return true;
			}
			
			 if (obj == null)
	                return false;

	            // Type check
	            if (getClass() != obj.getClass())
	                return false;

	            // Cast safely
	            Feet other = (Feet) obj;

	            // Compare double safely
	            return Double.compare(this.value, other.value) == 0;
	        
		}
		
	}
	
}