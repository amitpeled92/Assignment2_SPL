package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		//Need to convert the JSON input into Leia's Attack array
		try {
			Input input;
			Gson gson = new Gson();
			try (Reader reader = new FileReader(args[0])) {
				input =gson.fromJson(reader, Input.class);
			}
			System.out.println(input);//need to check i cant run it;
			Attack[] attacks= input.getAttacks();
			LeiaMicroservice leiaMicroservice = new LeiaMicroservice(attacks);//need to check efficency of this way;
			long r2= input.getR2D2();
			R2D2Microservice r2D2Microservice= new R2D2Microservice(r2);
			HanSoloMicroservice hanSoloMicroservice= new HanSoloMicroservice();
			C3POMicroservice c3POMicroservice= new C3POMicroservice();
			long lando= input.getLando();
			LandoMicroservice LandoMicroservice= new LandoMicroservice(lando);
			Ewoks ewoks= Ewoks.getInstance();
			ewoks.init(input.getEwoks());
		}
		catch (Exception e)
		{

		}

		//Create JSON output from Dairy instance (Dairy is a singleton)
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(args[1]);
			Diary diary= Diary.getInstance();
			gson.toJson(diary,writer);
			writer.flush();
			writer.close();
		}
		catch (Exception e)
		{

		}
	}
}
