
import static spark.Spark.get;
import static spark.Spark.post;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.BasicConfigurator;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import isty.iatic5.arlo.res.facade.ResourcesFacade;
import isty.iatic5.arlo.res.facade.ResourcesInterface;
import isty.iatic5.arlo.res.model.ExistingReferenceException;
import isty.iatic5.arlo.res.model.InvalidInputException;
import isty.iatic5.arlo.res.model.NullElementException;
import isty.iatic5.arlo.res.model.Person;
import isty.iatic5.arlo.res.model.Reservation;
import isty.iatic5.arlo.res.model.Room;
import isty.iatic5.arlo.res.model.Status;
import isty.iatic5.arlo.res.model.TimeSlot;
import spark.Request;
import spark.Response;
import spark.Route;

public class Main {
	public static void main(String[] args) {
		ResourcesInterface resourcesManager = new ResourcesFacade();

		JsonParser jsonParser = new JsonParser();

		BasicConfigurator.configure();

		post("/createPerson", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
				JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

				System.out.println(request);
				// System.out.println(jsonRequest.get("command").toString().equals("\"asba\""));
				// if (jsonRequest.get("command").toString().equals("\"createPerson\"")) {
				System.out.println("Creation d'une personne");
				String personId = jsonRequest.get("personId").toString().replaceAll("\"", "");
				String firstName = jsonRequest.get("firstName").toString().replaceAll("\"", "");
				String lastName = jsonRequest.get("lastName").toString().replaceAll("\"", "");
				String email = jsonRequest.get("email").toString().replaceAll("\"", "");
				Status status = jsonRequest.get("status").toString().replaceAll("\"", "") == "\"Student\""
						? Status.Student
						: Status.Teacher; // Student ou Teacher
				try {
					resourcesManager.createPerson(personId, firstName, lastName, email, status);
					result = "{ \"result\" : \"ok\" }";
					
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return result;
			}
		});

		post("/createRoom", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
				JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

				System.out.println(request);

				// if (jsonRequest.get("command").toString().equals("\"createRoom\"")) {
				System.out.println("Creation d'une room");
				String roomId = jsonRequest.get("roomId").toString().replaceAll("\"", "");
				String roomName = jsonRequest.get("roomName").toString().replaceAll("\"", "");
				int capacity = Integer.valueOf(jsonRequest.get("capacity").toString().replaceAll("\"", ""));
				String buildingName = jsonRequest.get("buildingName").toString().replaceAll("\"", "");
				try {
					resourcesManager.createRoom(roomId, roomName, capacity, buildingName);
					result = "{ \"result\" : \"ok\" }";
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// }

				return result;
			}
		});

		post("/createTimeSlot", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
				JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

				System.out.println(request);

				// if (jsonRequest.get("command").toString().equals("\"createTimeSlot\"")) {
				System.out.println("Creation d'un timeslot");
				String timeSlotId = jsonRequest.get("timeSlotId").toString().replaceAll("\"", "");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				String date = jsonRequest.get("day").toString().replaceAll("\"", "");
				LocalDate day = LocalDate.parse(date, formatter);

				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
				LocalTime start = LocalTime.parse(jsonRequest.get("start").toString().replaceAll("\"", ""),
						timeFormatter);
				LocalTime end = LocalTime.parse(jsonRequest.get("end").toString().replaceAll("\"", ""), timeFormatter);

				try {
					resourcesManager.createTimeSlot(timeSlotId, day, start, end);
					result = "{ \"result\" : \"ok\" }";
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// }

				return result;
			}
		});

		post("/createReservation", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
				JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

				System.out.println(request);

				// if (jsonRequest.get("command").toString().equals("\"createReservation\"")) {
				System.out.println("Creation d'une reservation");
				String personId = jsonRequest.get("personId").toString().replaceAll("\"", "");
				String firstName = jsonRequest.get("firstName").toString().replaceAll("\"", "");
				String lastName = jsonRequest.get("lastName").toString().replaceAll("\"", "");
				String email = jsonRequest.get("email").toString().replaceAll("\"", "");
				Status status = jsonRequest.get("status").toString().replaceAll("\"", "") == "\"Student\""
						? Status.Student
						: Status.Teacher; // Student ou Teacher
				String roomId = jsonRequest.get("roomId").toString().replaceAll("\"", "");
				String roomName = jsonRequest.get("roomName").toString().replaceAll("\"", "");
				int capacity = Integer.valueOf(jsonRequest.get("capacity").toString().replaceAll("\"", ""));
				String buildingName = jsonRequest.get("buildingName").toString().replaceAll("\"", "");
				String timeSlotId = jsonRequest.get("timeSlotId").toString().replaceAll("\"", "");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				String date = jsonRequest.get("day").toString().replaceAll("\"", "");
				LocalDate day = LocalDate.parse(date, formatter);

				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
				LocalTime start = LocalTime.parse(jsonRequest.get("start").toString().replaceAll("\"", ""),
						timeFormatter);
				LocalTime end = LocalTime.parse(jsonRequest.get("end").toString().replaceAll("\"", ""), timeFormatter);
				try {
					resourcesManager.createReservation(new Person(personId, firstName, lastName, email, status),
							new Room(roomId, roomName, capacity, buildingName),
							new TimeSlot(timeSlotId, start, end, day));
					result = "{ \"result\" : \"ok\" }";
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// }
				return result;
			}
		});

		post("/deletePerson", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
				JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

				System.out.println(request);
				// if (jsonRequest.get("command").toString().equals("\"deletePerson\"")) {

				System.out.println("Supression d'une personne");
				String personId = jsonRequest.get("personId").toString().replaceAll("\"", "");
				String firstName = jsonRequest.get("firstName").toString().replaceAll("\"", "");
				String lastName = jsonRequest.get("lastName").toString().replaceAll("\"", "");
				String email = jsonRequest.get("email").toString().replaceAll("\"", "");
				Status status = jsonRequest.get("status").toString().replaceAll("\"", "") == "\"Student\""
						? Status.Student
						: Status.Teacher; // Student ou Teacher

				Person person = new Person(personId, firstName, lastName, email, status);

				try {
					resourcesManager.deletePerson(person);
					result = "{ \"result\" : \"ok\" }";
				} catch (NullElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExistingReferenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// }
				return result;
			}
		});

		post("/deleteRoom", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
				JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

				System.out.println(request);
				// if (jsonRequest.get("command").toString().equals("\"deleteRoom\"")) {
				System.out.println("Supression d'une room");
				String roomId = jsonRequest.get("roomId").toString().replaceAll("\"", "");
				String roomName = jsonRequest.get("roomName").toString().replaceAll("\"", "");
				int capacity = Integer.valueOf(jsonRequest.get("capacity").toString().replaceAll("\"", ""));
				String buildingName = jsonRequest.get("buildingName").toString().replaceAll("\"", "");

				try {
					resourcesManager.deleteRoom(new Room(roomId, roomName, capacity, buildingName));
					result = "{ \"result\" : \"ok\" }";
				} catch (NullElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExistingReferenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// }
				return result;
			}
		});

		post("/deleteTimeSlot", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
				JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

				System.out.println(request);
				// if (jsonRequest.get("command").toString().equals("\"deleteTimeSlot\"")) {
				System.out.println("Creation d'un timeslot");
				String timeSlotId = jsonRequest.get("timeSlotId").toString().replaceAll("\"", "");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				String date = jsonRequest.get("day").toString().replaceAll("\"", "");
				LocalDate day = LocalDate.parse(date, formatter);

				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
				LocalTime start = LocalTime.parse(jsonRequest.get("start").toString().replaceAll("\"", ""),
						timeFormatter);
				LocalTime end = LocalTime.parse(jsonRequest.get("end").toString().replaceAll("\"", ""), timeFormatter);
				try {
					resourcesManager.deleteTimeSlot(new TimeSlot(timeSlotId, start, end, day));
					result = "{ \"result\" : \"ok\" }";
				} catch (NullElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExistingReferenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// }
				return result;
			}
		});

		post("/deleteReservation", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
				JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

				System.out.println(request);
				// if (jsonRequest.get("command").toString().equals("\"deleteReservation\"")) {
				System.out.println("Creation d'une reservation");
				String personId = jsonRequest.get("personId").toString().replaceAll("\"", "");
				String firstName = jsonRequest.get("firstName").toString().replaceAll("\"", "");
				String lastName = jsonRequest.get("lastName").toString().replaceAll("\"", "");
				String email = jsonRequest.get("email").toString().replaceAll("\"", "");
				Status status = jsonRequest.get("status").toString().replaceAll("\"", "") == "\"Student\""
						? Status.Student
						: Status.Teacher; // Student ou Teacher
				String roomId = jsonRequest.get("roomId").toString().replaceAll("\"", "");
				String roomName = jsonRequest.get("roomName").toString().replaceAll("\"", "");
				int capacity = Integer.valueOf(jsonRequest.get("capacity").toString().replaceAll("\"", ""));
				String buildingName = jsonRequest.get("buildingName").toString().replaceAll("\"", "");
				String timeSlotId = jsonRequest.get("timeSlotId").toString().replaceAll("\"", "");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				String date = jsonRequest.get("day").toString().replaceAll("\"", "");
				LocalDate day = LocalDate.parse(date, formatter);

				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
				LocalTime start = LocalTime.parse(jsonRequest.get("start").toString().replaceAll("\"", ""),
						timeFormatter);
				LocalTime end = LocalTime.parse(jsonRequest.get("end").toString().replaceAll("\"", ""), timeFormatter);
				try {
					resourcesManager.deleteReservation(new Reservation(new TimeSlot(timeSlotId, start, end, day),
							new Person(personId, firstName, lastName, email, status),
							new Room(roomId, roomName, capacity, buildingName)));
					result = "{ \"result\" : \"ok\" }";
				} catch (NullElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Instanciation de l'IHM
				// MainGUI mainGUI = new MainGUI();
				// Passe la référence de l'objet exposant les ressources à l'IHM
				// mainGUI.setResourcesManager(resourcesManager);
				// mainGUI.show();
				// }

				return result;
			}
		});

		post("/editReservation", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
				JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

				System.out.println(request);
				// if (jsonRequest.get("command").toString().equals("\"editReservation\"")) {
				System.out.println("Creation d'une reservation");
				String personId = jsonRequest.get("personId").toString().replaceAll("\"", "");
				String firstName = jsonRequest.get("firstName").toString().replaceAll("\"", "");
				String lastName = jsonRequest.get("lastName").toString().replaceAll("\"", "");
				String email = jsonRequest.get("email").toString().replaceAll("\"", "");
				Status status = jsonRequest.get("status").toString().replaceAll("\"", "") == "\"Student\""
						? Status.Student
						: Status.Teacher; // Student ou Teacher
				String roomId = jsonRequest.get("roomId").toString().replaceAll("\"", "");
				String roomName = jsonRequest.get("roomName").toString().replaceAll("\"", "");
				int capacity = Integer.valueOf(jsonRequest.get("capacity").toString().replaceAll("\"", ""));
				String buildingName = jsonRequest.get("buildingName").toString().replaceAll("\"", "");
				String timeSlotId = jsonRequest.get("timeSlotId").toString().replaceAll("\"", "");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				String date = jsonRequest.get("day").toString().replaceAll("\"", "");
				LocalDate day = LocalDate.parse(date, formatter);

				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
				LocalTime start = LocalTime.parse(jsonRequest.get("start").toString().replaceAll("\"", ""),
						timeFormatter);
				LocalTime end = LocalTime.parse(jsonRequest.get("end").toString().replaceAll("\"", ""), timeFormatter);

				String newDate = jsonRequest.get("newDay").toString().replaceAll("\"", "");
				LocalDate newDay = LocalDate.parse(newDate, formatter);

				LocalTime newStart = LocalTime.parse(jsonRequest.get("newStart").toString().replaceAll("\"", ""),
						timeFormatter);
				LocalTime newEnd = LocalTime.parse(jsonRequest.get("newEnd").toString().replaceAll("\"", ""),
						timeFormatter);

				String newTimeSlotId = jsonRequest.get("newTimeSlotId").toString().replaceAll("\"", "");

				Reservation reservation = new Reservation(new TimeSlot(timeSlotId, start, end, day),
						new Person(personId, firstName, lastName, email, status),
						new Room(roomId, roomName, capacity, buildingName));

				try {
					resourcesManager.deleteReservation(reservation);
					resourcesManager.createReservation(reservation.getPerson(), reservation.getRoom(), new TimeSlot(newTimeSlotId, newStart, newEnd, newDay));
					//resourcesManager.editReservation(reservation,
					//		new TimeSlot(newTimeSlotId, newStart, newEnd, newDay));
					result = "{ \"result\" : \"ok\" }";
				} catch (NullElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// }
				return result;
			}
		});

		get("/getPersonData", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
			//	JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

			//	System.out.println(request);
				// if (jsonRequest.get("command").toString().equals("\"getPersonData\"")) {
				Gson gson = new Gson();
				result = "[";
				for (Person personne : resourcesManager.getPersonData()) {
					String jsonPerson = String.format(
							"{\"personId\" : \"%s\" , \"firstName\" : \"%s\" , \"lastName\" : \"%s\" , \"email\" : \"%s\" , \"status\" : \"%s\"}",
							personne.getId(), personne.getFirstName(), personne.getLastName(), personne.getEmail(),
							personne.getStatus() == Status.Student ? "Student" : "Teacher");
					result += jsonPerson + ",**";
				}
				result += "]";
				result = result.replace(",**]", "]");
				result = result.replace("**", "");
				System.out.println(result);

				// }

				return result;
			}
		});

		get("/getTimeSlotData", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
			//	JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

			//	System.out.println(request);
				// if (jsonRequest.get("command").toString().equals("\"getTimeSlotData\"")) {
				Gson gson = new Gson();
				result = "[";
				for (TimeSlot timeslot : resourcesManager.getTimeSlotData()) {
					String jsonPerson = String.format(
							"{ \"timeSlotId\" : \"%s\" , \"day\" : \"%s\" , \"start\" : \"%s\" , \"end\" : \"%s\" }",
							timeslot.getId(), timeslot.getDay().toString(), timeslot.getStart().toString(),
							timeslot.getEnd().toString());
					result += jsonPerson + ",**";
				}
				result += "]";
				result = result.replace(",**]", "]");
				result = result.replace("**", "");
				System.out.println(result);

				// }
				return result;
			}
		});

		get("/getRoomData", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
				//JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

			//	System.out.println(request);
				// if (jsonRequest.get("command").toString().equals("\"getRoomData\"")) {
				Gson gson = new Gson();
				result = "[";
				for (Room room : resourcesManager.getRoomData()) {
					String jsonPerson = String.format(
							"{\"roomId\" : \"%s\" , \"roomName\" : \"%s\" , \"capacity\" : \"%s\" , \"buildingName\" : \"%s\"}",
							room.getId(), room.getName(), room.getCapacity().toString(), room.getBuilding());
					result += jsonPerson + ",**";
				}
				result += "]";
				result = result.replace(",**]", "]");
				result = result.replace("**", "");
				System.out.println(result);

				// }

				return result;
			}
		});

		get("/getReservationData", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// return request.body();
				String result = "{ \"result\" : \"error\" }";
			//	JsonObject jsonRequest = jsonParser.parse(request.body()).getAsJsonObject();

			//	System.out.println(request);
				// if (jsonRequest.get("command").toString().equals("\"getReservationData\"")) {
				Gson gson = new Gson();
				result = "[";
				for (Reservation reservation : resourcesManager.getReservationData()) {
					Room room = reservation.getRoom();
					Person personne = reservation.getPerson();
					TimeSlot timeslot = reservation.getTimeSlot();
					System.out.println(room);
					System.out.println(personne);
					System.out.println(timeslot);
					String jsonPerson = String.format(
							"{\"roomId\" : \"%s\" , \"roomName\" : \"%s\" , \"capacity\" : \"%s\" , \"buildingName\" : \"%s\" , \"timeSlotId\" : \"%s\" , \"day\" : \"%s\" , \"start\" : \"%s\" , \"end\" : \"%s\" , \"personId\" : \"%s\" , \"firstName\" : \"%s\" , \"lastName\" : \"%s\" , \"email\" : \"%s\" , \"status\" : \"%s\" }",
							room.getId(), room.getName(), room.getCapacity().toString(), room.getBuilding(),
							timeslot.getId(), timeslot.getDay().toString(), timeslot.getStart().toString(),
							timeslot.getEnd().toString(), personne.getId(), personne.getFirstName(),
							personne.getLastName(), personne.getEmail(),
							personne.getStatus() == Status.Student ? "Student" : "Teacher");
					result += jsonPerson + ",**";
				}
				result += "]";
				result = result.replace(",**]", "]");
				result = result.replace("**", "");
				System.out.println(result);

				// }

				return result;

				// return "User: username=test, email=test@test.net";

			}
		});
	}
}