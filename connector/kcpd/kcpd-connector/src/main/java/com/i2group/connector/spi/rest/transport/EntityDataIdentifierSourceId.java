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
import com.i2group.connector.spi.rest.transport.SourceIdentifier;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.*;

/**
 * A source identifier being used to identify an entity or a link.
 */
public class EntityDataIdentifierSourceId implements EntityDataIdentifier {

  public SourceIdentifier sourceId;

  public EntityDataIdentifierSourceId() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public EntityDataIdentifierSourceId(SourceIdentifier sourceId) {
    this.sourceId = sourceId;
  }

  public EntityDataIdentifierSourceId sourceId(SourceIdentifier sourceId) {
    this.sourceId = sourceId;
    return this;
  }

  /**
   * Get sourceId
   * @return sourceId
  */
  @NotNull @Valid 
  @JsonProperty("sourceId")
  public SourceIdentifier getSourceId() {
    return sourceId;
  }

  public void setSourceId(SourceIdentifier sourceId) {
    this.sourceId = sourceId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntityDataIdentifierSourceId entityDataIdentifierSourceId = (EntityDataIdentifierSourceId) o;
    return Objects.equals(this.sourceId, entityDataIdentifierSourceId.sourceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntityDataIdentifierSourceId {\n");
    sb.append("    sourceId: ").append(toIndentedString(sourceId)).append("\n");
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

