/*
* MIT License
*
* © N.Harris Computer Corporation (2024)
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

package com.i2group.connector.spi.rest.transport;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.i2group.connector.spi.rest.transport.ClientConfigConfig;
import com.i2group.connector.spi.rest.transport.ClientConfigType;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.*;

/**
 * An indication of how a client should interact with the service.
 */
public class ClientConfig {

  public ClientConfigConfig config;

  public String id;

  public ClientConfigType type;

  public ClientConfig() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ClientConfig(ClientConfigConfig config, String id, ClientConfigType type) {
    this.config = config;
    this.id = id;
    this.type = type;
  }

  public ClientConfig config(ClientConfigConfig config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
  */
  @NotNull @Valid 
  @JsonProperty("config")
  public ClientConfigConfig getConfig() {
    return config;
  }

  public void setConfig(ClientConfigConfig config) {
    this.config = config;
  }

  public ClientConfig id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The unique identifier of the client configuration.
   * @return id
  */
  @NotNull 
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ClientConfig type(ClientConfigType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  */
  @NotNull @Valid 
  @JsonProperty("type")
  public ClientConfigType getType() {
    return type;
  }

  public void setType(ClientConfigType type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientConfig clientConfig = (ClientConfig) o;
    return Objects.equals(this.config, clientConfig.config) &&
        Objects.equals(this.id, clientConfig.id) &&
        Objects.equals(this.type, clientConfig.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(config, id, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClientConfig {\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

