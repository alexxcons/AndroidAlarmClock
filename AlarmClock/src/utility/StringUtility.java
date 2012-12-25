package utility;

public class StringUtility
{
	static public String NumberTo2DigitString(int number)
	{
		String TwoDigitString = new String();
    	if(number < 10)//to get always 2 digits
    	{
    		TwoDigitString += "0";
    	}
    	TwoDigitString += String.valueOf(number);
    	
		return TwoDigitString;
	}

}
