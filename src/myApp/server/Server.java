package myApp.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;

import myApp.domain.LoginInfo;
import myApp.domain.Student;
import myApp.domain.Timetable;

public class Server {
	
	private DataOutputStream dataToClient;
	private DataInputStream dataFromClient;
	
	private ObjectInputStream objectFromClient;
	private ObjectOutputStream objectToClient;

	private ServerSocket serverSocket;
	
	private Socket socket;
	
	//student parameters
	private String sID;
	private String sPass;
	private String sFname;
	private String sLname;
	private String sCourse;
	private String sTel;
	private String sEmail;
	private String sAdd;
	private String sPostcode;
	
	//hold the username, when user login into the system
	private String username;
	//hold the password, when user login into the system
	private String pass;
	
	//timetable parameters
	private String moduleName;
	private String courseName;
	private String roomNo;
	private String day;
	private String time;
	private String lec;
	
	private String queryString;
	private String moduleNo;
	
	private Connection connection;
	
	private ArrayList<Timetable> timetableArray;
	private ArrayList<Student> studentArray;
	
	public static void main(String[] args) throws IOException {
		//start the server
		new Server();
	}
	
	public Server()  {
		try {
			//create a server socket
			serverSocket = new ServerSocket(8000);
			System.out.println("Server started at " + new Date());
			
			while(true) {
				//listen for a new connection request
				socket = serverSocket.accept();
				
				dataFromClient = new DataInputStream(socket.getInputStream());
				
				String connection =  dataFromClient.readUTF();
				
				if (connection.equals("registration")) {
					//method to create a new student account and save student information into database
					createStudentAccount();
					
				} else if(connection.equals("login")) {
					//method to log in into account
					logInAccount();
					
				} else if(connection.equals("viewInfo")) {
					//method to get student information from the database
					queryString = "select * from student where studentID = ?";
					
					Student student = getStudentInfoDB();
					
					objectToClient = new ObjectOutputStream(socket.getOutputStream());
					
					objectToClient.writeObject(student);
					System.out.println("Student information is sent.");
					
					dataFromClient.close();
					objectToClient.flush();
					objectToClient.close();
					socket.close();
					System.out.println("Streams and socket are closed. \n");
				} else if(connection.equals("saveInfo")) {
					//method to save or update student information
					saveStudentInfo();
				} else if(connection.equals("viewTimetable")) {
					//method to get timetable information and send it to client
					viewTimetable();
				} else if(connection.equals("viewAllStudentInfo")) {
					queryString = "select * from student";
					
					studentArray = getAllStudentDataDB();
					
					objectToClient = new ObjectOutputStream(socket.getOutputStream());
					objectToClient.writeObject(studentArray);
					
					System.out.println("All students information is sent.");
					
					dataFromClient.close();
					objectToClient.flush();
					objectToClient.close();
					socket.close();
					System.out.println("Streams and socket are closed. \n");
				} else if(connection.equals("deleteStudent")) {
					queryString = "delete from student where studentID = ?";
					
					dataFromClient = new DataInputStream(socket.getInputStream());
					
					sID = dataFromClient.readUTF();
					
					String status = deleteStudentDB();
					
					dataToClient = new DataOutputStream(socket.getOutputStream());
					
					dataToClient.writeUTF(status);
					
					dataFromClient.close();
					objectToClient.flush();
					objectToClient.close();
					socket.close();
					System.out.println("Streams and socket are closed. \n");
				} else if(connection.equals("addModule")) {
					//method to add and save new module information
					createModule();
					
				} else if(connection.equals("deleteModule")) {
					queryString = "delete from timetable where moduleNo = ?";
					
					dataFromClient = new DataInputStream(socket.getInputStream());

					moduleNo = dataFromClient.readUTF();

					String status = deleteModuleDB();

					dataToClient = new DataOutputStream(socket.getOutputStream());

					dataToClient.writeUTF(status);
					
					dataFromClient.close();
					objectToClient.flush();
					objectToClient.close();
					socket.close();
					System.out.println("Streams and socket are closed. \n");
					
				} else if(connection.equals("updateModule")) {
					//method to edit module information
					updateModule();
				}
			}
		}
		catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}	
	}
	
	private void createStudentAccount() throws IOException, ClassNotFoundException {
		queryString = "insert into Student(studentID, studentPass, studentFname, "
				+ "studentLname, studentTel, studentEmail, studentAdd, studentPostcode)" +
				"values (?, ?, ?, ?, ?, ?, ?, ?)";
		//create an input stream from the socket
		objectFromClient = new ObjectInputStream(socket.getInputStream());
		//read from input
		Student student = (Student) objectFromClient.readObject();
		//get first name, last name and telephone from the student object
		String fName = student.getFname().toLowerCase();
		String lName = student.getLname().toLowerCase();
		String tel = student.getTel();
		//create a random student id consisted of student's full name and telephone
		String studentID = fName + lName.substring(0, 3) + tel.substring(tel.length() - 3);
		//create an output stream from the socket
		dataToClient = new DataOutputStream(socket.getOutputStream());
		//get student's information to prepare to write to the database
		sID = studentID;
		sPass = student.getPass();
		sFname = student.getFname();
		sLname = student.getLname();
		//sCourse = "none";
		sTel = student.getTel();
		sEmail = student.getEmail();
		sAdd = student.getAddress();
		sPostcode = student.getPostcode();
		
		boolean status = saveInputToDatabaseDB();
		
		if(status) {
			//send student id back to the client
			dataToClient.writeUTF(studentID);
		} else {
			dataToClient.writeUTF("false");
		}
		
		dataFromClient.close();
		objectFromClient.close();
		dataToClient.flush();
		dataToClient.close();
		socket.close();
		System.out.println("Streams and socket are closed. \n");
	}
	
	private void logInAccount() throws IOException, ClassNotFoundException {
		queryString = "select studentID, studentPass from student where studentID = ? and studentPass = ?;";
		//create an input stream from the socket
		objectFromClient = new ObjectInputStream(socket.getInputStream());
		
		//create an output stream from the socket
		dataToClient = new DataOutputStream(socket.getOutputStream());
		
		//read from input
		LoginInfo li = (LoginInfo) objectFromClient.readObject();
		
		username = li.getUsername();
		pass = li.getPass();
		
		String result = checkLoginDetailsDB();
		
		dataToClient.writeUTF(result);
		
		if(result.equals("found") || result.equals("adminUser")) {
			System.out.println("User " + username + " is connected.");
		} else {
			System.out.println("Login failed.");
		}
		
		dataFromClient.close();
		objectFromClient.close();
		dataToClient.flush();
		dataToClient.close();
		socket.close();
		
		System.out.println("Streams and socket are closed. \n");
	}
	
	private void saveStudentInfo() throws IOException, ClassNotFoundException {
		queryString = "update student set studentFname = ?, studentLname = ?, studentCourse = ?, "
				+ "studentTel = ?, studentEmail = ?, studentAdd = ?, studentPostcode = ?, studentPass = ? where "
				+ "studentID = ?";
		
		//create an input stream from the socket
		objectFromClient = new ObjectInputStream(socket.getInputStream());
		
		//create an output stream from the socket
		dataToClient = new DataOutputStream(socket.getOutputStream());
		
		Student student = (Student) objectFromClient.readObject();
		
		sFname = student.getFname();
		sLname = student.getLname();
		sCourse = student.getCourse();
		sTel = student.getTel();
		sEmail = student.getEmail();
		sAdd = student.getAddress();
		sPostcode = student.getPostcode();
		
		if(username.equals("admin") && pass.equals("admin")) {
			sID = student.getId();
			sPass = student.getPass();
		}
		
		String result = updateStudentInfoDB();
		dataToClient.writeUTF(result);
		
		dataFromClient.close();
		objectFromClient.close();
		dataToClient.flush();
		dataToClient.close();
		socket.close();
		
		System.out.println("Streams and socket are closed. \n");
		
	}
	
	private void viewTimetable() throws IOException {
		dataFromClient = new DataInputStream(socket.getInputStream());
		String courseName = dataFromClient.readUTF();
		
		moduleNo = null;
		
		if(courseName.equals("Computer Science")) {
			moduleNo = "10%";
		} else if(courseName.equals("Creative Computing")) {
			moduleNo = "20%";
		} else if(courseName.equals("Cyber Security")) {
			moduleNo = "30%";
		} else if(courseName.equals("all")) {
			moduleNo = "%";
		}
		
		queryString = "select * from timetable where moduleNo like ?";
		
		timetableArray = getTimetableDataDB();
		
		objectToClient = new ObjectOutputStream(socket.getOutputStream());
		objectToClient.writeObject(timetableArray);
		
		System.out.println("Timetable information is sent.");
		
		objectToClient.flush();
		objectToClient.close();
		dataFromClient.close();
		objectFromClient.close();
		dataToClient.flush();
		dataToClient.close();
		socket.close();
		
		System.out.println("Streams and socket are closed. \n");
	}
	
	private void createModule() throws IOException, ClassNotFoundException {
		objectFromClient = new ObjectInputStream(socket.getInputStream());
		Timetable timetable = (Timetable) objectFromClient.readObject();

		moduleName = timetable.getModuleName();
		courseName = timetable.getCourseName();
		roomNo = timetable.getRoomNo();
		day = timetable.getDay();
		time = timetable.getTime();
		lec = timetable.getLec();
		
		String status = addModuleDB();
		
		dataToClient = new DataOutputStream(socket.getOutputStream());
		
		if(status.equals(moduleNo)) {
			dataToClient.writeUTF(moduleNo);
			System.out.println("New module is added.");
		} else {
			dataToClient.writeUTF("false");
			System.out.println("Operation add module is failed.");
		}
		
		dataFromClient.close();
		objectFromClient.close();
		dataToClient.flush();
		dataToClient.close();
		socket.close();
		
		System.out.println("Streams and socket are closed. \n");
	}
	
	private void updateModule() throws IOException, ClassNotFoundException {
		queryString = "update timetable set moduleName = ?, roomNo = ?, day = ?, cTime = ?, lecturer = ?"
				+ "where moduleNo = ?";
		
		objectFromClient = new ObjectInputStream(socket.getInputStream());
		Timetable timetable = (Timetable) objectFromClient.readObject();
		
		moduleNo = timetable.getModuleNo();
		moduleName = timetable.getModuleName();
		roomNo = timetable.getRoomNo();
		day = timetable.getDay();
		time = timetable.getTime();
		lec = timetable.getLec();
		
		String result = updateModuleInfoDB();
		
		dataToClient = new DataOutputStream(socket.getOutputStream());
		dataToClient.writeUTF(result);
		
		dataFromClient.close();
		objectFromClient.close();
		dataToClient.flush();
		dataToClient.close();
		socket.close();
		
		System.out.println("Streams and socket are closed. \n");
	}
	
	private Boolean saveInputToDatabaseDB() {
		//connects to database and create a prepared statement
		PreparedStatement s = initializeDB();
		
		try {
			s.setString(1, sID);
			s.setString(2, sPass);
			s.setString(3, sFname);
			s.setString(4, sLname);
			s.setString(5, sTel);
			s.setString(6, sEmail);
			s.setString(7, sAdd);
			s.setString(8, sPostcode);
			
			//execute an sql update statement
			s.executeUpdate();
			System.out.println("Student information saved.");
			
		} catch(SQLIntegrityConstraintViolationException e) {
			return false;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				System.out.println("Connection with database closed.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	private String checkLoginDetailsDB() {
		PreparedStatement s = initializeDB();
		
		try {
			s.setString(1, username);
			s.setString(2, pass);
			ResultSet rset = s.executeQuery();
			
			if(rset.next()) {
				username = rset.getString(1);
				pass = rset.getString(2);
				
				if(rset.getString(1).equals("admin") && rset.getString(2).equals("admin")) {
					connection.close();
					System.out.println("Connection with database closed.");
					return "adminUser";
				}
				
				connection.close();
				System.out.println("Connection with database closed.");
				return "found";
				
			} else {
				connection.close();
				System.out.println("Connection with database closed.");
				return "not found";
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return "not found";	
	}
	
	private Student getStudentInfoDB() {
		PreparedStatement s = initializeDB();
		
		Student student = null;
		
		try {
			s.setString(1, username);
			ResultSet rset = s.executeQuery();
			
			if(rset.next()) {
				String fName = rset.getString(3);
				String lName = rset.getString(4);
				String course = rset.getString(5);
				String tel = rset.getString(6);
				String email = rset.getString(7);
				String add = rset.getString(8);
				String postcode = rset.getString(9);
				String id = username;
				
				String password = pass;
				
				student = new Student(fName, lName, tel, email, add, postcode, id, password, course);
				
				connection.close();
				System.out.println("Connection with database closed.");
				
			} else {
				connection.close();
				System.out.println("Connection with database closed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}
	
	private String updateStudentInfoDB() {
		String result = "false";
		
		PreparedStatement s = initializeDB();
		
		try {
			s.setString(1, sFname);
			s.setString(2, sLname);
			s.setString(3, sCourse);
			s.setString(4, sTel);
			s.setString(5, sEmail);
			s.setString(6, sAdd);
			s.setString(7, sPostcode);
			if(username.equals("admin") && pass.equals("admin")) {
				s.setString(8, sPass);
				s.setString(9, sID);
			} else {
				s.setString(8, pass);
				s.setString(9, username);
			}
			
			s.executeUpdate();
			result = "Updated successfully.";
			System.out.println("Student Information Updated.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				System.out.println("Connection with database closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	

	private ArrayList<Timetable> getTimetableDataDB() {
		ArrayList<Timetable> data = new ArrayList<>();
		PreparedStatement s = initializeDB();
		
		try {
			s.setString(1, moduleNo);
			
			ResultSet rs = s.executeQuery();
			
			while(rs.next()) {
				data.add(new Timetable(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				System.out.println("Connection with database closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	private ArrayList<Student> getAllStudentDataDB() {
		ArrayList<Student> data = new ArrayList<>();
		PreparedStatement s = initializeDB();
		
		try {
			ResultSet rs = s.executeQuery();
			
			while(rs.next()) {
				//ignore the administrator record in student table
				if(rs.getString(1).equals("admin") && rs.getString(2).equals("admin")) {
					continue;
				} else {
					//add all students object to array list
					data.add(new Student(rs.getString(3), rs.getString(4), rs.getString(6), rs.getString(7), rs.getString(8),
							rs.getString(9), rs.getString(1), rs.getString(2), rs.getString(5)));
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				System.out.println("Connection with database closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	//deletes student record
	private String deleteStudentDB() {
		String status = "Error";
		PreparedStatement s = initializeDB();
		
		try {
			s.setString(1, sID);
			
			s.executeUpdate();
			System.out.println("Student record deleted.");
			status = "Student record deleted.";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				System.out.println("Connection with database closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}
	
	//add a new module to database
	private String addModuleDB() {
		//query string to find the max value in moduleNo column
		queryString  =  "select max(moduleNo) from timetable where courseName = ?";
		
		PreparedStatement s = initializeDB();
		
		try {
			s.setString(1, courseName);

			ResultSet rs = s.executeQuery();
			//search the maximum value of module number
			while(rs.next()) { moduleNo = rs.getString(1);}
			//converts module no to int data type
			int newModNo = Integer.parseInt(moduleNo);
			newModNo++;
			//converts module number to String data type
			moduleNo = Integer.toString(newModNo);
			//query string to add module information to database
			queryString = "insert into timetable (moduleNo, courseName, moduleName, roomNo, day, cTime, lecturer)"
					+ "values(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(queryString);
			ps.setString(1, moduleNo);
			ps.setString(2, courseName);
			ps.setString(3, moduleName);
			ps.setString(4, roomNo);
			ps.setString(5, day);
			ps.setString(6, time);
			ps.setString(7, lec);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				System.out.println("Connection with database closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return moduleNo;
	}
	
	//deletes module record from database
	private String deleteModuleDB() {
		String status = "Error";
		
		PreparedStatement s = initializeDB();
		
		try {
			s.setString(1, moduleNo);
			s.executeUpdate();
			System.out.println("Module record deleted.");
			status = "Module record deleted.";
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				System.out.println("Connection with database closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}
	
	//updates timetable information in the database
	private String updateModuleInfoDB() {
		String status = "true";

		PreparedStatement s = initializeDB();
		
		try {
			s.setString(1, moduleName);
			s.setString(2, roomNo);
			s.setString(3, day);
			s.setString(4, time);
			s.setString(5, lec);
			s.setString(6, moduleNo);
			
			s.executeUpdate();
			
			System.out.println("Module information updated.");
		} catch(SQLException e) {
			//status = "false";
			e.printStackTrace();
			//return status;
		} finally {
			try {
				connection.close();
				System.out.println("Connection with database closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}
	
	private PreparedStatement initializeDB() {
		try {
			//load the jdbc driver
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");

			//connect to a database
			connection = DriverManager.getConnection("jdbc:mysql://localhost/studentinfo", "roman", "tiger");
			System.out.println("Database connected");
			//create a prepared statement
			PreparedStatement preparedStatement = connection.prepareStatement(queryString);
			return preparedStatement;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}	
}