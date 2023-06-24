package com.example.accessingdatamysql;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.sdk.resources.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.opentelemetry.semconv.resource.attributes.ResourceAttributes.OS_NAME;
import static io.opentelemetry.semconv.resource.attributes.ResourceAttributes.SERVICE_NAME;

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

		// The OpenTelemetry resource contains general info for all signals (e.g., OS, Java version, service name etc.)
		Resource resource = Resource.getDefault().merge(
				Resource.create(
						Attributes.of(
								SERVICE_NAME, AccessingDataMysqlApplication.class.getSimpleName(),
								OS_NAME, "Windows 11",
								AttributeKey.stringKey("java.version"), System.getProperty("java.version")
						)));


		SpringApplication.run(AccessingDataMysqlApplication.class, args);
	}
}
