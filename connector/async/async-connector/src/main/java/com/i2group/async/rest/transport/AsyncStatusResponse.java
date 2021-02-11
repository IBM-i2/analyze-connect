/********************************************************************************
# * Licensed Materials - Property of IBM
# * (C) Copyright IBM Corporation 2021. All Rights Reserved
# *
# * This program and the accompanying materials are made available under the
# * terms of the Eclipse Public License 2.0 which is available at
# * http://www.eclipse.org/legal/epl-2.0.
# *
# * US Government Users Restricted Rights - Use, duplication or
# * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
# *
# ********************************************************************************/

package com.i2group.async.rest.transport;

import java.util.List;

public class AsyncStatusResponse {

  public State state;
  public String errorMessage;
  public List<AsyncQuerySubstatus> substatuses;

  public enum State {
    STARTED,
    SUCCEEDED,
    FAILED;
  }
}
