/********************************************************************************
# * Licensed Materials - Property of IBM
# * (C) Copyright IBM Corporation 2020. All Rights Reserved
# *
# * This program and the accompanying materials are made available under the
# * terms of the Eclipse Public License 2.0 which is available at
# * http://www.eclipse.org/legal/epl-2.0.
# *
# * US Government Users Restricted Rights - Use, duplication or
# * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
# *
# ********************************************************************************/

package com.i2group.eri;

import com.i2group.eri.rest.externalsource.SocrataClient;
import com.i2group.eri.rest.externalsource.transport.SocrataResponse;
import com.i2group.eri.rest.transport.request.RequestCondition;
import com.i2group.eri.rest.transport.request.seed.SeedEntity;
import com.i2group.eri.rest.transport.request.seed.Seeds;
import com.i2group.eri.rest.transport.response.ConnectorResponse;
import com.i2group.eri.rest.transport.response.Entity;
import com.i2group.eri.rest.transport.response.ItemFactory;
import com.i2group.eri.rest.transport.response.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ExternalConnectorDataService {
    private final SocrataClient socrataClient;
    private static final String LIMIT_FIELD = "limitValue";
    private static final int LIMIT_VALUE = 50;
    private static final String BASE_QUERY = "?$limit={limitValue}";

    private static final String WHERE = "&$where=";
    private static final String LIKE = " like ";
    private static final String IS = "=";
    private static final String AND = " AND ";

    private static final String FIELD_INCIDENT_TYPE = "incident_type";
    private static final String FIELD_BOROUGH = "borough";
    private static final String FIELD_ADDRESS = "location";

    /**
     * Constructor used to initialise the Socrata client used to retrieve
     * external data.
     *
     * @param baseUrl  The URL of the Emergency Response Incident dataset.
     * @param apiToken The API token required to access the dataset.
     */
    @Autowired
    public ExternalConnectorDataService(
        @Value("${socrata.url}") String baseUrl, @Value("${socrata.api.token}") String apiToken) {
        socrataClient = new SocrataClient(baseUrl, apiToken);
    }

    /**
     * Query the external dataset and retrieve all data.
     *
     * @return A response containing the entities and links.
     */
    public ConnectorResponse all() {
        final Map<String, Object> params = new HashMap<>();
        params.put(LIMIT_FIELD, LIMIT_VALUE);

        final SocrataResponse response = socrataClient.get(BASE_QUERY, SocrataResponse.class,
            params);
        return marshalItemsFromResponse(response);
    }

    /**
     * Retrieve data matching certain specified conditions.
     *
     * @param conditions The conditions provided by the user via the interface.
     * @return A response containing the entities and links.
     */
    public ConnectorResponse search(List<RequestCondition> conditions) {
        final Map<String, Object> params = new HashMap<>();
        params.put(LIMIT_FIELD, LIMIT_VALUE);
        String url = BASE_QUERY;

        int count = 0;
        for (RequestCondition condition : conditions) {
            if (condition.value != null) {
                url += count == 0 ? WHERE : AND;
                params.put(condition.id, condition.value);
                url += condition.id + LIKE + "'{" + condition.id + "}%'";
                count++;
            }
        }

        final SocrataResponse response = socrataClient.get(url, SocrataResponse.class, params);
        final AtomicInteger index = new AtomicInteger();

        final ConnectorResponse connectorResponse = new ConnectorResponse();
        connectorResponse.entities = response.stream()
            .map(entry -> ItemFactory.createIncident(entry,
                entry.getIncidentKey(index.getAndIncrement())))
            .collect(Collectors.toList());
        return connectorResponse;
    }

    /**
     * Performs a Find-Like-This operation, finding incidents with similar
     * properties to a selected incident.
     *
     * @param seeds The selected entities provided by the user via the
     *              interface.
     * @return A response containing the entities and links.
     */
    public ConnectorResponse findLikeThisIncident(Seeds seeds) {
        SeedEntity seed = seeds.entities.get(0);

        final Map<String, Object> params = new HashMap<>();
        params.put(LIMIT_FIELD, LIMIT_VALUE);
        params.put("incidentType", seed.properties.get("PT1"));
        String url = BASE_QUERY + WHERE + FIELD_INCIDENT_TYPE + LIKE + "'{incidentType}%'";

        final SocrataResponse response = socrataClient.get(url, SocrataResponse.class, params);
        final AtomicInteger index = new AtomicInteger();

        final ConnectorResponse connectorResponse = new ConnectorResponse();
        connectorResponse.entities = response.stream()
            .map(entry -> ItemFactory.createIncident(entry,
                entry.getIncidentKey(index.getAndIncrement())))
            .collect(Collectors.toList());
        return connectorResponse;
    }

    /**
     * Performs an Expand operation on selected entities, finding entities which stem from the same
     * source records.
     *
     * @param seeds The selected entities provided by the user via the interface.
     * @return A response containing the entities and links.
     */
    public ConnectorResponse expand(Seeds seeds) {
        final SeedEntity seed = seeds.entities.get(0);

        final Map<String, Object> params = new HashMap<>();
        params.put(LIMIT_FIELD, LIMIT_VALUE);
        String url = BASE_QUERY;

        if (seed.typeId.equals("ET1")) {
            final String incidentType = seed.properties.get("PT1")
                .toString();
            final String incidentSubtype = seed.properties.get("PT2")
                .toString();
            params.put("field", FIELD_INCIDENT_TYPE);
            params.put("value", String.format("%s-%s", incidentType, incidentSubtype));
            url += WHERE + "{field}" + IS + "'{value}'";
        } else if (seed.typeId.equals("ET2")) {
            final String borough = seed.properties.get("PT7")
                .toString();
            final String address = seed.properties.get("PT8")
                .toString();
            params.put("field_borough", FIELD_BOROUGH);
            params.put("field_address", FIELD_ADDRESS);
            params.put("value_borough", borough);
            params.put("value_address", address);
            url += WHERE + "{field_borough}" + IS + "'{value_borough}'" + AND + "{field_address}" + IS + "'{value_address}'";
        }

        final SocrataResponse socrataResponse = socrataClient.get(url, SocrataResponse.class,
            params);
        final ConnectorResponse response = marshalItemsFromResponse(socrataResponse);
        response.links = linkToSeedIds(response, seed);

        return response;
    }

    /**
     * Marshal the response items into a list of entities and links. Ensures no duplicate
     * incidents or locations are included.
     *
     * @param response The resulting source records returned from the request.
     * @return The response containing entities and links.
     */
    private ConnectorResponse marshalItemsFromResponse(SocrataResponse response) {
        final List<Entity> entities = new ArrayList<>();
        final List<Link> links = new ArrayList<>();

        final Map<String, Entity> incidents = new HashMap<>();
        final Map<String, Entity> locations = new HashMap<>();

        final AtomicInteger count = new AtomicInteger();

        response.forEach(entry -> {
            count.getAndIncrement();

            Entity incident;
            final String incidentKey = entry.getIncidentKey();
            if (incidents.containsKey(incidentKey)) {
                incident = incidents.get(incidentKey);
            } else {
                incident = ItemFactory.createIncident(entry, incidentKey);
                incidents.put(incidentKey, incident);
                entities.add(incident);
            }

            Entity location;
            final String locationKey = entry.getLocationKey();
            if (locations.containsKey(locationKey)) {
                location = locations.get(locationKey);
            } else {
                location = ItemFactory.createLocation(entry, locationKey);
                locations.put(locationKey, location);
                entities.add(location);
            }

            final Link locationLink = ItemFactory.createLocationLink(incident, location,
                count.get());
            links.add(locationLink);
        });

        final ConnectorResponse connectorResponse = new ConnectorResponse();
        connectorResponse.entities = entities;
        connectorResponse.links = links;
        return connectorResponse;
    }

    /**
     * Match link ends to seed identifiers.
     *
     * @param response The response containing the marshaled entities and links.
     * @param seed     The selected entity provided by the user via the interface.
     * @return The list of links matched to the corresponding seed identifiers.
     */
    private List<Link> linkToSeedIds(ConnectorResponse response, SeedEntity seed) {
        for (Link link : response.links) {
            String sourceId = seed.sourceIds.get(0).key.get(2);
            if (link.fromEndId.equals(sourceId)) {
                link.fromEndId = seed.seedId;
            } else if (link.toEndId.equals(sourceId)) {
                link.toEndId = seed.seedId;
            }
        }

        return response.links;
    }
}
