# Etlsample

An example Java application that reads from a CSV file and uploads it into the configured HCL Domino Rest API server.

This application was generated using <http://start.vertx.io>.

## Get started

Create a `config.json` file in the project root directory with the following format:

| **Configuration key** | Type    | Description                                                 | Example                    |
|:---------------------:|---------|-------------------------------------------------------------|----------------------------|
|      **username**     | String  | Your HCL Domino Rest API account username.                  | myusername                 |
|      **password**     | String  | Your HCL Domino Rest API account password.                  | mypassword                 |
|       **server**      | String  | Your HCL Domino Rest API server name.                       | whitepalace.projectkeep.io |
|        **port**       | Integer | Port that the `server` is running.                          | 80                         |
|      **isHttps**      | Boolean | If `server` should be called with HTTPS protocol.           | `true`                     |
|     **targetFile**    | String  | The target CSV file to use for the bulk data upload.        | training_list.csv          |
|       **scope**       | String  | Specifies which scope in HCL Domino Rest API to upload to.  | approval                   |

### Example configuration

```json
{
  "username": "myusername",
  "password": "mypassword",
  "server": "whitepalace.projectkeep.io",
  "port": 80,
  "isHttps": true,
  "targetFile": "training_list.csv",
  "scope": "approval"
}
```

To package your application:

```shell
./mvnw package
```

To run your application:

```shell
./mvnw exec:java
```
