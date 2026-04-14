package com.example.taskmanager;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@SpringBootApplication
public class TaskmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}

	@Bean
	public MongoClient mongoClient() {
		String uri = System.getenv("SPRING_DATA_MONGODB_URI");
		if (uri == null) uri = System.getenv("MONGO_URI");
		if (uri == null) uri = "mongodb+srv://nikhilsingh_db_user:2Sc8krgTHq9db0lI@testdbonline.8l4hjf7.mongodb.net/taskmanagerdb?retryWrites=true&w=majority&appName=TestDBOnline";
		return MongoClients.create(uri);
	}

	@Bean
	public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
		return new SimpleMongoClientDatabaseFactory(mongoClient, "taskmanagerdb");
	}
}