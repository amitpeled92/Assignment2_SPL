package bgu.spl.mics.application;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.concurrent.atomic.AtomicInteger;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main
{
	public static void main(String[] args) {
		//Need to convert the JSON input into Leia's Attack array

		Thread[] threads = new Thread[5];
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
			threads[0] = new Thread(leiaMicroservice);
			threads[1] = new Thread(hanSoloMicroservice);
			threads[2] = new Thread(c3POMicroservice);
			threads[3] = new Thread(r2D2Microservice);
			threads[4] = new Thread(LandoMicroservice);
			for (int i=1;i<threads.length;i++) {
				threads[i].start();
			}
			threads[0].start();
			for (Thread t : threads) {
				t.join();
			}
		} catch (Exception e) {
			System.out.println("end exception");

		}
		//Create JSON output from Dairy instance (Dairy is a singleton)
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(args[1]);
			Diary diary = Diary.getInstance();
			gson.toJson(diary, writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.out.println("end exception");

		}
	}

}

