//CalvinRiordan
//R00137587
//Revision
import java.util.Scanner;
public class Revision1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int roomNum, roomCount, totalPaintCost, totalLabourCost, totalCost,  paintPrice, wallspace, roomGallons, roomHoursLabour, roomPaintCost, roomLabourCost, roomCost;
		paintPrice = 0;
		roomNum = 0;
		wallspace = 0;

		Scanner keyboard = new Scanner(System.in);

		////////////////////////////////////////////////////////////////////////////////////////////////

		System.out.println("****************************************");
		System.out.println("*Welcome to CITPaint Job Cost Estimator*");
		System.out.println("****************************************");

		while(roomNum <= 0){
			System.out.println("Enter the number of rooms: " );
			roomNum = keyboard.nextInt();

			if(roomNum <= 0)
				System.out.println("error! Please enter a number greater than 0");
		}
		while(paintPrice<=10){
			System.out.println("Enter the paint price per gallon(€): ");
			paintPrice = keyboard.nextInt();

			if(paintPrice<=10)
				System.out.println("error! Please enter an integer greater than 10!");
		}

		////////////////////////////////////////////////////////////////////////////////////////////////

		roomCount = 1;
		totalPaintCost = 0;
		totalLabourCost = 0;
		totalCost = 0;

		while(roomCount <= roomNum)
		{
			keyboard.nextLine();
			System.out.println("");
			System.out.println("Room "+ roomCount +" - Quotation");
			System.out.println("--------------------------------");

			while(wallspace <= 0){
				System.out.println("Enter the wallspace(sq. Ft.): ");
				wallspace = keyboard.nextInt();

				if(wallspace <= 0)
					System.out.println("error! Please enter a number greater than 0");
			}

			roomGallons = wallspace/100;
			roomHoursLabour = roomGallons * 5;
			roomPaintCost = roomGallons * paintPrice;
			roomLabourCost = roomHoursLabour * 20;
			roomCost = roomPaintCost + roomLabourCost;


			System.out.println("This room requires:");
			System.out.println("Paint(Gallons): " + roomGallons + "      "+" Paint Price: " + roomPaintCost);
			System.out.println("Labour(Hours): " + roomHoursLabour + "       "+" Labour Price: " + roomLabourCost);
			System.out.println("                         Room Cost: " + roomCost);

			totalPaintCost = totalPaintCost + roomPaintCost;
			totalLabourCost = totalLabourCost + roomLabourCost;

			roomCount++;

			////////////////////////////////////////////////////////////////////////////////////////////////
		}

		totalCost = totalPaintCost + totalLabourCost;

		System.out.println("Overall Quotation");
		System.out.println("-----------------");
		System.out.println("Paint Cost = " + totalPaintCost);
		System.out.println("Labour Cost = " + totalLabourCost);
		System.out.println("Total Cost = " + totalCost);

	}

}
