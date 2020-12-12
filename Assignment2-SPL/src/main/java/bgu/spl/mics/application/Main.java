package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.atomic.AtomicInteger;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main
{

	public static void main(String[] args)
	{
		boolean isThreadsDone = false;
		//Converting the JSON input into Leia's Attack array
		try {
			Input input;
			Gson gson = new Gson();
			try (Reader reader = new FileReader(args[0])) {
				input = gson.fromJson(reader, Input.class);
			}
			Attack[] attacks = input.getAttacks();
			LeiaMicroservice leiaMicroservice = new LeiaMicroservice(attacks);
			long r2 = input.getR2D2();
			R2D2Microservice r2D2Microservice = new R2D2Microservice(r2);
			HanSoloMicroservice hanSoloMicroservice = new HanSoloMicroservice();
			C3POMicroservice c3POMicroservice = new C3POMicroservice();
			long lando = input.getLando();
			LandoMicroservice LandoMicroservice = new LandoMicroservice(lando);
			Ewoks ewoks = Ewoks.getInstance();
			ewoks.init(input.getEwoks());
			Diary diary = Diary.getInstance();
			diary.setTotalAttacks(0);
			Thread[] threads = new Thread[5];
			threads[0] = new Thread(leiaMicroservice);
			threads[1] = new Thread(hanSoloMicroservice);
			threads[2] = new Thread(c3POMicroservice);
			threads[3] = new Thread(r2D2Microservice);
			threads[4] = new Thread(LandoMicroservice);
			for (Thread t : threads) {
				t.start();
			}
			for (Thread t : threads) {
				t.join();
			}
			isThreadsDone = true;
		}
		catch (Exception e) {

		}


//		try {
//			System.out.println("end run");
//			Thread.currentThread().wait(10000);
//		}
//		catch (Exception e){}


		//Create JSON output from Dairy instance (Dairy is a singleton)
		while(isThreadsDone) {
			Diary diary = Diary.getInstance();
			try (FileWriter file = new FileWriter(args[1])) {
				Gson gson = new Gson();
				gson.toJson(diary, file);
				file.flush();
				//file.close();
				System.out.println(diary.toString()); //diary debugger
			} catch (IOException e) {
				System.out.println("error in output file");
			}
			isThreadsDone = false;
		}

		/*
		//Create JSON output from Dairy instance (Dairy is a singleton)
		try { //TODO: Need to make sure output is working
			Thread.currentThread().wait();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(args[1]);
			Diary diary = Diary.getInstance();
			System.out.println(1);
			gson.toJson(diary, writer);
			System.out.println(2);

			writer.flush();
			System.out.println(3);

			writer.close();
			System.out.println(4);

		}
		catch (Exception e) {
			System.out.println("end exception");

		}
		 */
	}

}

