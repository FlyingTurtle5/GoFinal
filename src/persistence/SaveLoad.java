package persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveLoad {

	public static void safe(PersistenceObject po) {
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Go.ser"));
			oos.writeObject(po);
			oos.close();
			System.out.println("Das Board wurde gespeichert");
			System.out.println(po.getBoard());
			System.out.println("Der Status wurde gespeichert "+ po.getStatus());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static PersistenceObject load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Go.ser"));
			PersistenceObject po = (PersistenceObject) ois.readObject();
			ois.close();
			return po;		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return null;
	}
	
}
