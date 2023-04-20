package com.hcl.engage.etlsample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

/**
 * The main process of this application.
 */
public class Main extends AbstractVerticle {

  /**
   * Holds configuration values from `config.json`.
   */
  private static Configuration config = null;

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Configuration config = readConfiguration(startPromise);

    if (config != null) {
      login(config.getServer(), config.getPort(), config.isHttps(), config.getCredentials())
          .onSuccess(v -> this.processCsv(startPromise))
          .onFailure(startPromise::fail);
    }
  }

  /**
   * Reads `config.json` from root directory and assign it to `config`.
   * 
   * @param startPromise vertx start-up promise
   * @return Configuration object containing necessary information for the main process.
   */
  private Configuration readConfiguration(Promise<Void> startPromise) {
    try {
      Path configPath = Path.of(Constants.CONFIGURATION_FILE);
      String configString = Files.readString(configPath);
      config = new Configuration(new JsonObject(configString));
    } catch (Exception e) {
      startPromise.fail("Error reading configuration file 'config.json'.");
    }

    return config;
  }

  /**
   * Attempt to authenticate with the HCL Domino Rest API.
   * 
   * @param server server name
   * @param port server port
   * @param isHttps if WebClient call should be HTTPS or HTTP
   * @param credentials Credential object containing the username and password to authenticate with
   * @return A Promise object that can fail or succeed.
   */
  Future<Void> login(String server, int port, boolean isHttps, Credentials credentials) {
    Promise<Void> promise = Promise.promise();

    WebClient client = WebClient.create(vertx);
    client
        .post(port, server, Constants.LOGIN_ENDPOINT)
        .ssl(isHttps)
        .sendJsonObject(credentials.toJson())
        .onSuccess(res -> {
          if (res.statusCode() != 200) {
            promise.fail("Sucker! You failed.");
          } else {
            credentials.setAuthorization(res.bodyAsJsonObject().getString("bearer"));
            System.out.println("Login success!");
            promise.complete();
          }
        })
        .onFailure(promise::fail);

    return promise.future();
  }

  /**
   * Reads targetFile from configuration data and uploads all data it to the specified
   * scope in the configuration.
   * 
   * @param startPromise vertx start-up promise
   */
  private void processCsv(Promise<Void> startPromise) {
    try {
      JsonArray data = CsvReader.getCsvData(config.getTargetFile());

      upload(config.getServer(), config.getPort(), config.isHttps(), config.getScope(),
          config.getCredentials(), data)
        .onSuccess(v -> {
          System.out.println("CSV successfully uploaded!");
          startPromise.complete();
          vertx.close();
        })
        .onFailure(startPromise::fail);
    } catch (IOException e) {
      startPromise.fail("Error reading CSV file '" + config.getTargetFile() + "'.");
    } catch (Exception e) {
      startPromise.fail(e.getMessage());
    }
  }

  /**
   * Uploads the given data using `/bulk/create` endpoint of the HCL Domino Rest API.
   * 
   * @param server server name
   * @param port server port
   * @param isHttps if WebClient call should be HTTPS or HTTP
   * @param scope target scope to upload data to
   * @param credentials Credential object containing the JWT to authenticate request
   * @param data the data to upload
   * @return A Promise object that can fail or succeed.
   */
  private Future<Void> upload(final String server, final int port, final boolean isHttps,
      final String scope, final Credentials credentials, final JsonArray data) {
    Promise<Void> promise = Promise.promise();

    WebClient client = WebClient.create(vertx);
    client
        .post(port, server, Constants.BULK_CREATE_ENDPOINT)
        .addQueryParam("dataSource", scope)
        .putHeader("Authorization", credentials.getAuthorization())
        .ssl(isHttps)
        .sendJsonObject(new JsonObject().put("documents", data))
        .onSuccess(res -> {
          if (res.statusCode() != 200) {
            promise.fail("Oh no! Something went wrong...");
          } else {
            promise.complete();
          }
        })
        .onFailure(promise::fail);

    return promise.future();
  }

}
