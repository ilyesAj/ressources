package communicationProtocol;


import kong.unirest.Unirest;
import kong.unirest.HttpResponse;;


public class RestClient {
	private String url;
	
	
	public RestClient (String u,String p) {
		url= u+":"+p;
	}
	
	public String postCreatePerson(String id, String fName, String lName, String eM, String Stat)
	{		
		HttpResponse<String> response = Unirest.post("http://"+url+"/createPerson")
				  .header("Content-Type", "application/json")
				  .body("{ \"personId\" :"
					  		+ " \""+id+"\" , \"firstName\" : \""+fName+"\" , \"lastName\" : "
					  		+ "\""+lName+"\" , \"email\" : \""+eM+"\" , \"status\" "
					  		+ ": \""+Stat+"\" }\n")
				  .asString();
		return response.getBody();
	}
	public String postCreateRoom(String rId, String rName, String rcap, String bName)
	{
		HttpResponse<String> response = Unirest.post("http://"+url+"/createRoom")
				  .header("Content-Type", "application/json")
				  .body("{ \"roomId\" : \""+rId+"\" , "
				  		+ "\"roomName\" : \""+rName+"\" ,"
				  		+ " \"capacity\" : \""+rcap+"\" ,"
				  		+ " \"buildingName\" : \""+bName+"\" }\n")
				  .asString();
		return response.getBody();
	}
		
		
	public String postCreateTimeSlot(String tId, String day, String start, String end)
	{
		HttpResponse<String> response = Unirest.post("http://"+url+"/createTimeSlot")
				  .header("Content-Type", "application/json")
				  .body("{  \"timeSlotId\" : \""+tId+"\" ,"
				  		+ " \"day\" : \""+day+"\" ,"
				  		+ " \"start\" : \""+start+"\" , "
				  		+ "\"end\" : \""+end+"\" }\n")
				  .asString();
		return response.getBody();
	}
	
	public String postCreateReservation(String pId, String fName, String lName, String eM, String Stat, String rId,
			String rName, String rCap, String bName,String tId, String day, String start, String end)
	{
		HttpResponse<String> response = Unirest.post("http://"+url+"/createReservation")
				  .header("Content-Type", "application/json")
				  .body("{ \"personId\" : \""+pId+"\" ,"
				  		+ " \"firstName\" : \""+fName+"\" , "
				  		+ "\"lastName\" : \""+lName+"\" , "
				  		+ "\"email\" : \""+eM+"\" ,"
				  		+ " \"status\" : \""+Stat+"\" ,"
				  		+ "\"roomId\" : \""+rId+"\" , "
				  		+ "\"roomName\" : \""+rName+"\" ,"
				  		+ " \"capacity\" : \""+rCap+"\" ,"
				  		+ " \"buildingName\" : \""+bName+"\" ,"
				  		+ " \"timeSlotId\" : \""+tId+"\" ,"
				  		+ " \"day\" : \""+day+"\" , "
				  		+ "\"start\" : \""+start+"\" ,"
				  		+ " \"end\" : \""+end+"\" }\n")
				  .asString();
		return response.getBody();
	}
	
	public String postDeletePerson(String pId, String fName, String lName, String eM, String Stat)
	{
		HttpResponse<String> response = Unirest.post("http://"+url+"/deletePerson")
				  .header("Content-Type", "application/json")
				  .body(" {  \"personId\" : \""+pId+"\" , "
				  		+ "\"firstName\" : \""+fName+"\" ,"
				  		+ " \"lastName\" : \""+lName+"\" ,"
				  		+ " \"email\" : \""+eM+"\" ,"
				  		+ " \"status\" : \""+Stat+"\"  }\t\n")
				  .asString();
		return response.getBody();
	}
	public String postDeleteRoom( String rId, String rName, String rCap, String bName)
	{
		HttpResponse<String> response = Unirest.post("http://"+url+"/deleteRoom")
				  .header("Content-Type", "application/json")
				  .body("{   \"roomId\" : \""+rId+"\" ,"
				  		+ " \"roomName\" : \""+rName+"\" ,"
				  		+ " \"capacity\" : \""+rCap+"\" ,"
				  		+ " \"buildingName\" : \""+bName+"\" }\n")
				  .asString();
		return response.getBody();
	}
	
	public String postDeleteTimeSlot(String tId, String day, String start, String end)
	{
		HttpResponse<String> response = Unirest.post("http://"+url+"/deleteTimeSlot")
				  .header("Content-Type", "application/json")
				  .body("{  \"timeSlotId\" : \""+tId+"\" , "
				  		+ "\"day\" : \""+day+"\" ,"
				  		+ "\"start\" : \""+start+"\" ,"
				  		+ "\"end\" : \""+end+"\" }")
				  .asString();
		return response.getBody();
	}
	
	public String postDeleteReservation(String pId, String fName, String lName, String eM, String Stat, String rId,
			String rName, String rCap, String bName,String tId, String day, String start, String end)
	{
		HttpResponse<String> response = Unirest.post("http://"+url+"/deleteReservation")
				  .header("Content-Type", "application/json")
				  .body("{ \"personId\" : \""+pId+"\" ,"
					  		+ " \"firstName\" : \""+fName+"\" , "
					  		+ "\"lastName\" : \""+lName+"\" , "
					  		+ "\"email\" : \""+eM+"\" ,"
					  		+ " \"status\" : \""+Stat+"\" ,"
					  		+ "\"roomId\" : \""+rId+"\" , "
					  		+ "\"roomName\" : \""+rName+"\" ,"
					  		+ " \"capacity\" : \""+rCap+"\" ,"
					  		+ " \"buildingName\" : \""+bName+"\" ,"
					  		+ " \"timeSlotId\" : \""+tId+"\" ,"
					  		+ " \"day\" : \""+day+"\" , "
					  		+ "\"start\" : \""+start+"\" ,"
					  		+ " \"end\" : \""+end+"\" }\n")
				  .asString();
		return response.getBody();
	}
	
	public String postEditReservation(String pId, String fName, String lName, String eM, String Stat, String rId,
			String rName, String rCap, String bName,String tId, String day, String start, String end, String ntId, String nday, String nstart, String nend)
	{
		HttpResponse<String> response = Unirest.post("http://"+url+"/editReservation")
				  .header("Content-Type", "application/json")
				  .body("{ \"personId\" : \""+pId+"\" ,"
				  		+ " \"firstName\" : \""+fName+"\" , "
				  		+ "\"lastName\" : \""+lName+"\" , "
				  		+ "\"email\" : \""+eM+"\" ,"
				  		+ " \"status\" : \""+Stat+"\" ,"
				  		+ "\"roomId\" : \""+rId+"\" , "
				  		+ "\"roomName\" : \""+rName+"\" ,"
				  		+ " \"capacity\" : \""+rCap+"\" ,"
				  		+ " \"buildingName\" : \""+bName+"\" ,"
				  		+ " \"timeSlotId\" : \""+tId+"\" ,"
				  		+ " \"day\" : \""+day+"\" , "
				  		+ "\"start\" : \""+start+"\" ,"
				  		+ " \"end\" : \""+end+"\" ,"+
				  		"\"newTimeSlotId\" : \""+ntId+"\" ,"
				  		+ " \"newDay\" : \""+nday+"\" ,"
				  		+ " \"newStart\" : \""+nstart+"\" ,"
				  		+ " \"newEnd\" : \""+nend+"\"}")
				  .asString();
		return response.getBody();
	}
	
	public String getData(String param)
	{
		HttpResponse<String> response = Unirest.get("http://"+url+"/"+param)
				  .header("Content-Type", "application/json")
				  .asString();
		return response.getBody();
	}

}
