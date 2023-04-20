package com.hcl.engage.etlsample;

import io.vertx.core.json.JsonObject;

/**
 * Credentials object containing necessary information to authenticate with the
 * HCL Domino Rest API.
 */
public class Credentials {

  /**
   * The account username.
   */
  private final String username;
  /**
   * The account password.
   */
  private final String password;
  /**
   * Authorization for requests.
   */
  private String Authorization;

  /**
   * The constructor.
   * 
   * @param username account username
   * @param password account password
   */
  public Credentials(final String username, final String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * Get the Authorization.
   * 
   * @return the Authorization string.
   */
  public String getAuthorization() {
    return this.Authorization;
  }

  /**
   * Sets the Authorization string given the JWT string.
   * 
   * @param jwt The JWT token from HCL Domino Rest API.
   */
  public void setAuthorization(String jwt) {
    this.Authorization = "Bearer " + jwt;
  }

  /**
   * Parses the username and password as JsonObject.
   * 
   * @return JsonObject containing the username and password.
   */
  public JsonObject toJson() {
    return new JsonObject()
        .put("username", this.username)
        .put("password", this.password);
  }
  
}
