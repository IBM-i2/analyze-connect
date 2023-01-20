/*
 * MIT License
 *
 * © N.Harris Computer Corporation (2023)
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

package com.i2group.async.rest.transport;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

/**
 * A POJO (Plain Old Java Object) for objects from the people.json data source.
 */
@ToString
public class ResponseData {
  // Person
  @JsonProperty("people")
  public List<Person> person;

  public static class Person {
    @JsonProperty("id")
    public String id;
  
    @JsonProperty("forename")
    public String forename;
  
    @JsonProperty("surname")
    public String surname;
  
    @JsonProperty("dob")
    public String dob;
  
    @JsonProperty("ssn")
    public String ssn;
  
    @JsonProperty("issuedDateAndTime")
    public String issuedDateAndTime;
  
    @JsonProperty("friends")
    public List<String> friends;
  
    @JsonProperty("image")
    public String image;
  }
}
