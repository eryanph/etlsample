package com.hcl.engage.etlsample;

import io.vertx.core.json.JsonObject;

/**
 * Class that holds values read from the configuration file.
 */
public class Configuration {

  /**
   * The server name (e.g localhost, mycastle, heythere.com).
   */
  private final String server;
  /**
   * The server port (e.g 80, 8880, 443).
   */
  private final int port;
  /**
   * Specifies if the server should be called using HTTPS or HTTP.
   */
  private final boolean isHttps;
  /**
   * The target CSV file.
   */
  private final String targetFile;
  /**
   * Credential object for authenticating with the HCL Domino Rest API.
   */
  private final Credentials credentials;
  /**
   * The target scope to upload to.
   */
  private final String scope;

  /**
   * The constructor.
   * 
   * @param config JsonObject of values from the configuration file.
   */
  public Configuration(JsonObject config) {
    this.server = config.getString("server");
    this.port = config.getInteger("port");
    this.isHttps = config.getBoolean("isHttps", false);
    this.targetFile = config.getString("targetFile");
    this.credentials = new Credentials(config.getString("username"), config.getString("password"));
    this.scope = config.getString("scope");
  }

  /**
   * Get the server name.
   * 
   * @return The server name.
   */
  public String getServer() {
    return this.server;
  }

  /**
   * Get the server port.
   * 
   * @return The server port.
   */
  public int getPort() {
    return this.port;
  }

  /**
   * Get if HTTPS protocol should be used, otherwise HTTP.
   * 
   * @return True or false if HTTPS should be used.
   */
  public boolean isHttps() {
    return this.isHttps;
  }

  /**
   * Get the target CSV file.
   * 
   * @return The target CSV file.
   */
  public String getTargetFile() {
    return this.targetFile;
  }

  /**
   * Get the Credentials object.
   * 
   * @return The Credentials object.
   */
  public Credentials getCredentials() {
    return this.credentials;
  }

  /**
   * Get the target scope.
   * 
   * @return The target scope.
   */
  public String getScope() {
    return this.scope;
  }
  
}
