package GUI;
import java.util.Random;

public class Number {
    Number randomnumber;
	int min,max;
	
	public int generatex(){
		min = 0;
		max = 580;
		Random random = new Random();
		int num;
		num = random.nextInt(max)+ min;
		return num;
	}
	public int generatey(){
		min = 0;
		max = 260;
		Random random = new Random();
		int num;
		num = random.nextInt(max)+ min;
		return num;
	}
}
