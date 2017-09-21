package com.bs;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bson.types.ObjectId;
import com.connect.connect;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
@Path("/bookstore") 
public class Bookstore {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello(){
	return "Hello Jersey";	
	}
	
	@GET
	@Path("/findname/{name}") @Produces(MediaType.TEXT_XML)
	public String sayFindNameBook(@PathParam("name")String name){
		DB db = new connect().mongo();
		DBCollection collection = db.getCollection("book");
		
		BasicDBObject q = new BasicDBObject();
		q.put("name",  java.util.regex.Pattern.compile(name));
		
		DBCursor cursor = collection.find(q);
		
		List<DBObject> myList = cursor.toArray(); 
		

		String xml = "<?xml version=\"1.0\"?>";
		xml += "<return>";
		for(DBObject object : myList)
		{
			xml += "<book>";
				xml += "<id>"+object.get("_id").toString()+"</id>";
				xml += "<name>"+object.get("name").toString()+"</name>";
				xml += "<version>"+object.get("version").toString()+"</version>";
				xml += "<type>"+object.get("type").toString()+"</type>";
				xml += "<price>"+object.get("price").toString()+"</price>";
				xml += "<days>"+object.get("days").toString()+"</days>";
				xml += "<charge>"+object.get("charge").toString()+"</charge>";
				xml += "<total>"+object.get("total").toString()+"</total>";
			xml += "</book>";
		}
		xml += "</return>";
		
		System.out.println(xml);
		
		return xml;
		
	}
	@GET
	@Path("/findname") 
	@Produces(MediaType.TEXT_XML)  
	public String sayFindAllBook(){
		DB db = new connect().mongo();
		DBCollection collection = db.getCollection("book");

		DBCursor cursor = collection.find();
		
		List<DBObject> myList = cursor.toArray(); 
		
		String xml = "<?xml version=\"1.0\"?>";
		xml += "<return>";
		for(DBObject object : myList)
		{
			xml += "<book>";
				xml += "<id>"+object.get("_id").toString()+"</id>";
				xml += "<name>"+object.get("name").toString()+"</name>";
				xml += "<version>"+object.get("version").toString()+"</version>";
				xml += "<type>"+object.get("type").toString()+"</type>";
				xml += "<price>"+object.get("price").toString()+"</price>";
				xml += "<days>"+object.get("days").toString()+"</days>";
				xml += "<charge>"+object.get("charge").toString()+"</charge>";
				xml += "<total>"+object.get("total").toString()+"</total>";
			xml += "</book>";
		}
		xml += "</return>";
		
		System.out.println(xml);
		
		return xml;
		
	}
	
	@POST
	@Path("/create") 
	@Produces(MediaType.TEXT_PLAIN)  
	public String createFuck(@FormParam("_id") String _id, @FormParam("name") String name, @FormParam("type") String type, @FormParam("version") String version,@FormParam("price") String price,@FormParam("charge") String charge,@FormParam("days") String days,@FormParam("total") String total) {
		DB db = new connect().mongo();
		DBCollection collection = db.getCollection("book");
		
		System.out.println(_id);
		System.out.println(name);
		System.out.println(version);
		System.out.println(price);
		System.out.println(charge);
		System.out.println(days);
		System.out.println(total);
		

		BasicDBObject document = new BasicDBObject();
		document.put("_id", _id);
		document.put("name", name);
		document.put("version", version);
		document.put("type", type);
		document.put("price", price);
		document.put("charge",charge);
		document.put("days", days);
		document.put("total", total);

		collection.insert(document);
		
		return "true";  
	}
	@POST
	@Path("/update") 
	@Produces(MediaType.TEXT_PLAIN)  
	public String updateFucking(@FormParam("id") String id, @FormParam("name") String name, @FormParam("type") String type, @FormParam("version") String version,@FormParam("price") String price,@FormParam("charge") String charge,@FormParam("days") String days,@FormParam("total") String total) { 
		
		DB db = new connect().mongo();
		DBCollection collection = db.getCollection("book");
		
		BasicDBObject document = new BasicDBObject();
		document.put("name", name);
		document.put("version", version);
		document.put("type", type);
		document.put("price", price);
		document.put("charge",charge);
		document.put("days", days);
		document.put("total", total);
		
		
		BasicDBObject setQuery = new BasicDBObject();
        setQuery.put("$set", document);
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", new ObjectId(id));

		collection.update(searchQuery, setQuery);
		
		return "true";  
	} 
	@GET
	@Path("/delete/{id}") 
	@Produces(MediaType.TEXT_PLAIN)  
	public String delete(@PathParam("id") String id) {   
		System.out.println(id);
		DB db = new connect().mongo();
		DBCollection collection = db.getCollection("book");
		
		DBObject document = collection.findOne(new ObjectId(id));
		collection.remove(document);
		
		return "true";  
	}
	
	
	@GET
	@Path("/getUpdate/{id}") 
	@Produces(MediaType.TEXT_XML)  
	public String getUpdate(@PathParam("id") String id) {   
		
		DB db = new connect().mongo();
		DBCollection table = db.getCollection("book");
		
		DBObject object = table.findOne(new ObjectId(id));
		
		// create xml
		String xml = "<?xml version=\"1.0\"?>";
		xml += "<return>";
		xml += "<book>";
		xml += "<id>"+object.get("_id").toString()+"</id>";
		xml += "<name>"+object.get("name").toString()+"</name>";
		xml += "<version>"+object.get("version").toString()+"</version>";
		xml += "<type>"+object.get("type").toString()+"</type>";
		xml += "<price>"+object.get("price").toString()+"</price>";
		xml += "<days>"+object.get("days").toString()+"</days>";
		xml += "<charge>"+object.get("charge").toString()+"</charge>";
		xml += "<total>"+object.get("total").toString()+"</total>";
		xml += "</book>";
		xml += "</return>";
		
		return xml;  
	} 
}
