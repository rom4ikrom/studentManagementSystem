package myApp.domain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Client {
	
	private String host = "localhost";
	private int portNo = 8000;
	private DataInputStream dataFromServer;
	private DataOutputStream dataToServer;
	private ObjectOutputStream objectToServer;
	private ObjectInputStream objectFromServer;
	private Socket socket;
	
	private ObservableList<Timetable> timetableData;
	private ArrayList<Timetable> timetableArray;
	
	private ObservableList<Student> studentData;
	private ArrayList<Student> studentArray;
	
	//method to send information to server as an object with specified connection type
	//and receive from the server string as a result of operation
	public String sendInfoToServer(String connectionType, Object object) {
		
		String result = "";
		
		try {
			//create client socket
			socket = new Socket(host, portNo);
			
			//create output data stream from client to server
			dataToServer = new DataOutputStream(socket.getOutputStream());
			
			//send connection type to the server
			dataToServer.writeUTF(connectionType);
			
			//create input data stream from server to client
			dataFromServer = new DataInputStream(socket.getInputStream());
			
			//create output stream from client to server
			objectToServer = new ObjectOutputStream(socket.getOutputStream());
			
			
			//send student object to server
			objectToServer.writeObject(object);
			
			result = dataFromServer.readUTF();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				objectToServer.flush();
				objectToServer.close();
				dataToServer.close();
				dataFromServer.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//method to send request to server to get the specific information depending on connection type
	public Student getInfoFromServer(String connectionType) {
		Student student = new Student();
		
		try {
			//create client socket
			socket = new Socket(host, portNo);
			
			//create output data stream from client to server
			dataToServer = new DataOutputStream(socket.getOutputStream());
			
			//send connection type to the server
			dataToServer.writeUTF(connectionType);
			
			objectFromServer = new ObjectInputStream(socket.getInputStream());
			
			student = (Student) objectFromServer.readObject();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dataToServer.close();
				objectFromServer.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return student;
	}
	
	//method to receive the timetable information from the server as an array list and convert it to observable list
	//to show information in the table view
	@SuppressWarnings("unchecked")
	public ObservableList<Timetable> getTimetableList(String connectionType, String courseName) {
		
		timetableArray = new ArrayList<>();
		
		try {
			
			socket = new Socket(host, portNo);
			
			dataToServer = new DataOutputStream(socket.getOutputStream());
			dataToServer.writeUTF(connectionType);
			
			dataToServer.writeUTF(courseName);
			
			objectFromServer = new ObjectInputStream(socket.getInputStream());
			
			timetableArray = (ArrayList<Timetable>) objectFromServer.readObject();
			
			timetableData = FXCollections.observableArrayList(timetableArray);
		
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dataToServer.flush();
				dataToServer.close();
				objectFromServer.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return timetableData;
	}
	
	//method to receive the student information from the server as an array list and convert it to observable list
	//to show information in the table view
	@SuppressWarnings("unchecked")
	public ObservableList<Student> getStudentList(String connectionType) {
		studentArray = new ArrayList<>();
		
		try {
			
			socket = new Socket(host, portNo);
			
			dataToServer = new DataOutputStream(socket.getOutputStream());
			dataToServer.writeUTF(connectionType);
			
			objectFromServer = new ObjectInputStream(socket.getInputStream());
			studentArray = (ArrayList<Student>)objectFromServer.readObject();
			
			studentData = FXCollections.observableArrayList(studentArray);
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dataToServer.flush();
				dataToServer.close();
				objectFromServer.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return studentData;
	}
	
	//method to send request to server to delete record in student or timetable table depending on the connection type
	//and receive the result from the server as a string
	public String removeRecord(String connectionType, String id) {
		String status = "";
		
		try {
			socket = new Socket(host, portNo);
			
			dataToServer = new DataOutputStream(socket.getOutputStream());
			
			dataToServer.writeUTF(connectionType);
			
			dataToServer.writeUTF(id);
			
			dataFromServer = new DataInputStream(socket.getInputStream());
			
			status = dataFromServer.readUTF();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dataToServer.flush();
				dataToServer.close();
				dataFromServer.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return status;
	}
}


