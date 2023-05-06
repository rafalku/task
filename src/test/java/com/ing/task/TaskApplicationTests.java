package com.ing.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskApplicationTests {

    private static final String[] ENDPOINTS = {"/atms/calculateOrder", "/transactions/report"};

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @ParameterizedTest
    @MethodSource
    void endpoint(String endpoint, File requestFile, File expectedResponseFile) throws IOException, URISyntaxException {
        URI url = new URI("http", null, "localhost", port, endpoint, null, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(Files.readString(requestFile.toPath()), headers);
        String response = restTemplate.postForObject(url, request, String.class);

        String expectedResponse = Files.readString(expectedResponseFile.toPath());
        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(expectedResponse), mapper.readTree(response));
    }

    private static List<Arguments> endpoint() {
        List<Arguments> arguments = new LinkedList<>();
        for (String endpoint : ENDPOINTS) {
            String subdirectory = endpoint.split("/")[1];
            File directory = new File("src/test/resources/" + subdirectory);
            if (!directory.exists()) {
                continue;
            }
            File[] requestFiles = directory.listFiles((FileFilter) new WildcardFileFilter("*_request.json"));
            File[] responseFiles = directory.listFiles((FileFilter) new WildcardFileFilter("*_response.json"));
            if (requestFiles == null || responseFiles == null || requestFiles.length != responseFiles.length
                    || requestFiles.length == 0) {
                continue;
            }
            Arrays.sort(requestFiles, NameFileComparator.NAME_COMPARATOR);
            Arrays.sort(responseFiles, NameFileComparator.NAME_COMPARATOR);

            for (int i = 0; i < requestFiles.length; i++) {
                arguments.add(Arguments.of(endpoint, requestFiles[i], responseFiles[i]));
            }
        }
        return arguments;
    }

}
