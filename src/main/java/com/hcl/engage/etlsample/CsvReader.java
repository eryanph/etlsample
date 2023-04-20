package com.hcl.engage.etlsample;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * For CSV utility.
 */
public class CsvReader {

  /**
   * Reads data inside the given `targetFile`. The first line is treated as the JSON keys and
   * the succeeding lines are the value.
   * 
   * @param targetFile the target CSV file
   * @return JsonArray of read data.
   * @throws Exception
   */
  public static JsonArray getCsvData(final String targetFile) throws Exception {
    JsonArray data = new JsonArray();
    List<String> jsonKeys = new ArrayList<String>();
    AtomicBoolean jsonKeyFlag = new AtomicBoolean(true);

    Path targetFilePath = Path.of(targetFile);
    Files.lines(targetFilePath)
      .forEach(line -> {
        if (jsonKeyFlag.get()) {
          jsonKeyFlag.set(false);
          for (String key : line.split(",")) {
            jsonKeys.add(key);
          }
        } else {
          JsonObject entry = new JsonObject();

          String[] col = line.split(",");
          for (int i = 0; i < col.length; i++) {
            entry.put(jsonKeys.get(i), col[i]);
          }

          // Add Pending status to request
          entry.put(Constants.STATUS_KEY, Constants.STATUS_PENDING);

          data.add(entry);
        }
      });
    
    return data;
  }
  
}
