package com.manning.apisecurityinaction;

import static spark.Spark.*;

import org.json.JSONObject;

import spark.Request;
import spark.Response;

public class SparkTests {
	public static void main(String[] args) {
		new SparkTests();
	}

	public SparkTests() {
		
		get("/hello", (req,res)->"Hello World");
		
		Controller controller = new Controller();
		post("/give",controller::createJson);
//		post("/",(request, response) -> controller.createJson(request, response));
		post("/test",(req,res)->{
			return "Test";
		});
		
	}
	

	private static class Controller{
		public JSONObject createJson(Request req,Response res) {
			JSONObject json = new JSONObject(req.body()	);
			String name = json.getString("name");
			String owner = json.getString("owner");
			JSONObject result = new JSONObject();
			result.put("name", name);
			result.put("owner", owner);
			res.status(201);
			res.header("Location", "/give");
			return result;
		}
	}
}
