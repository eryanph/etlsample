package com.hcl.engage.etlsample;

/**
 * Utility for constant values.
 */
public class Constants {

  /**
   * The configuration file filename.
   */
  public static final String CONFIGURATION_FILE = "config.json";

  /**
   * Status key to add to upload data.
   */
  public static final String STATUS_KEY = "Status";
  /**
   * Status value as pending.
   */
  public static final String STATUS_PENDING = "Pending";
  
  /**
   * The HCL Domino Rest API login endpoint.
   */
  public static final String LOGIN_ENDPOINT = "/api/v1/auth";
  /**
   * The HCL Domino Rest API bulk create endpoint.
   */
  public static final String BULK_CREATE_ENDPOINT = "/api/v1/bulk/create";
  
}
