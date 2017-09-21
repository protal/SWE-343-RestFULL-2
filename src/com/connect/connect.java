package com.connect;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class connect {
	public DB mongo(){
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("BookStore");
		return db;
	}
}
