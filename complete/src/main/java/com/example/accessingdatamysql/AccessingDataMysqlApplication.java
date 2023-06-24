package com.example.accessingdatamysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class AccessingDataMysqlApplication {

	public static void main(String[] args) throws IOException {
		// Custom properties file containing props for this sample app, namely Endpoint and Token.
		Properties properties = new Properties();
		InputStream in = new FileInputStream("complete/dynatrace-otel.properties");
		properties.load(in);

		// Could be a local collector or a remote OTLP endpoint.
		String endpointBase = properties.getProperty("otel.exporter.otlp.endpoint");
		// Dynatrace token to ingest
		String token = properties.getProperty("dynatrace.api-token");
		String authHeader = String.format("Api-Token %s", token);

		String tracesEndpoint = String.format("%s/v1/traces", endpointBase);
		String metricsEndpoint = String.format("%s/v1/metrics", endpointBase);
		String logsEndpoint = String.format("%s/v1/logs", endpointBase);

		// At this point, we have an authorization header and endpoints for all three signals.
		// Let's start setting up OTel!


		SpringApplication.run(AccessingDataMysqlApplication.class, args);
	}
}
