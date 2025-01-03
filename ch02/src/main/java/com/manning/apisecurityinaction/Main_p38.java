package com.manning.apisecurityinaction;

import java.nio.file.*;

import com.manning.apisecurityinaction.controller.*;
import org.dalesbred.Database;
import org.dalesbred.result.EmptyResultException;
import org.h2.jdbcx.JdbcConnectionPool;
import org.json.*;
import spark.*;

import static spark.Spark.*;

public class Main_p38 {

    public static void main(String... args) throws Exception {
    	
        var datasource = JdbcConnectionPool.create(
            "jdbc:h2:mem:natter", "natter", "password");
        var database = Database.forDataSource(datasource);
        createTables(database);
        
        var spaceController = new SpaceController(database);

        
        post("/spaces", spaceController::createSpace);
        
        after((req,res)->{
        	res.type("application/json");
        });

        

        internalServerError(new JSONObject()
            .put("error", "internal server error").toString());
        notFound(new JSONObject()
            .put("error", "not found").toString());

    }

 

    private static void createTables(Database database) throws Exception {
        var path = Paths.get(
                Main_p38.class.getResource("/schema.sql").toURI());
        database.update(Files.readString(path));
    }
}