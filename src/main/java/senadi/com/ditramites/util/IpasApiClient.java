/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.util;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.http.HttpEntity;
import senadi.com.ditramites.model.postgres.polaris.IpasEnvironment;

/**
 *
 * @author michael
 */
public class IpasApiClient {

    private IpasEnvironment environment;

    public IpasApiClient(String tipo) {
        loadData(tipo);
    }

    private void loadData(String tipo) {
        Controlador c = new Controlador();
        environment = c.getIpasEnvironmentActive(true, tipo);
    }

    // 1️Obtener el primer access_token desde Cognito
    public String getCognitoAccessToken() {
        try {
            System.out.println("-----> a");
            CloseableHttpClient client = HttpClients.createDefault();
//            System.out.println("cognito-url: "+environment.getCognitoUrl());
//            System.out.println("client-id: "+environment.getAppClientId());
//            System.out.println("client-secret: "+environment.getAppClientSecret());
            HttpPost post = new HttpPost(environment.getCognitoUrl());
            System.out.println("-----> b");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
//            System.out.println("llego 1");
            String body = "grant_type=client_credentials&client_id=" + environment.getAppClientId() + "&client_secret=" + environment.getAppClientSecret();
            post.setEntity(new StringEntity(body));
            System.out.println("-----> c");
//            System.out.println("llego 2");
            CloseableHttpResponse response = client.execute(post);
            String json = EntityUtils.toString(response.getEntity());
            System.out.println("-----> d");
//            System.out.println("llego 3: "+json);
//            System.out.println("HTTP Status: " + response.getStatusLine());
            return Operaciones.extractToken(json);

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(IpasApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return "0";
        } catch (IOException ex) {
            Logger.getLogger(IpasApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return "1";
        }
    }

    // 2️Obtener el segundo access_token desde IPAS
    public String getXAccessToken(String cognitoAccessToken) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Construir el body en formato x-www-form-urlencoded
            Map<String, String> formParams = Map.of(
                    "grant_type", "token-exchange",
                    "subject_token", cognitoAccessToken,
                    "audience", "ipfile"
            );

            String formBody = formParams.entrySet().stream()
                    .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "="
                    + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                    .collect(Collectors.joining("&"));

            System.out.println("token-url: " + environment.getTokenUrl());
            // Crear la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(environment.getTokenUrl() + "/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(formBody))
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("llego aquí 1");
            // Verificar si la respuesta es exitosa
            if (response.statusCode() == 200) {
                System.out.println("llego aquí 2");
                if (response.body().contains("503 Service Temporarily Unavailable")) {
                    System.out.println("llego aquí 3 ");
                    return "503 Service Temporarily Unavailable";
                } else {
                    System.out.println("llego aquí 4");
                    System.out.println("================> " + (response.body().length() > 25 ? response.body().substring(0, 25) : response.body()));
                    return Operaciones.extractToken(response.body());
                }

            } else {
                throw new RuntimeException("Error: Código de estado " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException ex) {
            Logger.getLogger(IpasApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return "1";
        } catch (InterruptedException ex) {
            Logger.getLogger(IpasApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return "2";
        }
    }

    // 3 Generar la URL para subir el archivo
    public String[] generateUploadUrlPatent(String xAccessToken, String documentCodeCategory) {
        try {
            String documentNumber = documentCodeCategory.equals("patent") ? environment.getPatentDocumentNumber() : environment.getDesignDocumentNumber();
            String currentDate = Operaciones.getCurrentTimeStamp().replace(" ", "T") + "Z";
            // JSON del Body (versión compatible con Java 8 y 11)
            String jsonBody = "["
                    + "{"
                    + "\"id\":null,"
                    + "\"languageCode\":\"" + environment.getLanguagueCode() + "\","
                    + "\"sourceSystemIdentifier\":\"" + environment.getSourceSystemIdentifier() + "\","
                    + "\"documentName\":\"" + environment.getDocumentName() + "\","
                    + "\"fileName\":null,"
                    + "\"documentFormatCategory\":\"" + environment.getDocumentFormatCategory() + "\","
                    + "\"documentKindCategory\":\"" + environment.getDocumentKindCategory() + "\","
                    + "\"documentCodeCategory\":\"" + documentCodeCategory + "\","
                    + "\"documentDate\":\"" + currentDate + "\","//2020-01-06T15:35:24Z
                    + "\"documentLocationURI\":null,"
                    + "\"documentSizeQuantity\":\"1573511\","
                    + "\"documentPageQuantity\":null,"
                    + "\"documentNumber\":\"" + documentNumber + "\","
                    + "\"documentLanguageSource\":\"" + environment.getDocumentLanguageSource() + "\","
                    + "\"documentOrigin\":\"" + environment.getDocumentOrigin() + "\","
                    + "\"documentGroupCategory\":null,"
                    + "\"nplIndicator\":\"false\","
                    + "\"commentText\":null"
                    + "}"
                    + "]";

            // Crear cliente HTTP
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1) // Asegurar compatibilidad
                    .build();

            // Crear la solicitud con headers
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(environment.getBaseUrl() + "/documents")) // URL correcta
                    .header("Content-Type", "application/json")
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate, br") // Simula Postman
                    .header("User-Agent", "PostmanRuntime/7.43.0") // Simula Postman
                    .header("X-ACCESS-TOKEN", xAccessToken)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Imprimir la solicitud para depuración
//        System.out.println("URL usada: " + BASE_URL + "/documents");
//        System.out.println("JSON enviado:\n" + jsonBody);
// Enviar la solicitud y capturar la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

// Imprimir la respuesta completa
//        System.out.println("Código de respuesta: " + response.statusCode());
//        System.out.println("Cuerpo de respuesta: \n" + response.body());
// Verificar si la respuesta es 500
            if (response.statusCode() == 500) {
                System.out.println("⚠️ Error 500: El servidor falló. Puede haber un problema con la solicitud.");
                System.err.println("Error: Código de estado " + response.statusCode() + " - \n" + response.body());
                return new String[]{"3", response.body()};
            } else {
                String[] resp = {Operaciones.extractUploadUrl(response.body()), Operaciones.extractId(response.body())};
                return resp;
            }
        } catch (IOException ex) {
            Logger.getLogger(IpasApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return new String[]{"1", "1"};
        } catch (InterruptedException ex) {
            Logger.getLogger(IpasApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return new String[]{"2", "2"};
        }
    }

    // 4️Subir el archivo ZIP
    public boolean uploadFile(String presignedUrl, String ruta) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPut put = new HttpPut(presignedUrl);

            File file = new File(ruta);  // Ruta del archivo a subir
            FileBody fileBody = new FileBody(file);
            HttpEntity entity = MultipartEntityBuilder.create().addPart("file", fileBody).build();

            put.setEntity(entity);
            client.execute(put);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(IpasApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }   

    // 5 Crear la aplicación
    public String createApplication(String xAccessToken, String id, String documentCodeCategory) {
        try {
            String documentNumber = documentCodeCategory.equals("patent") ? environment.getPatentDocumentNumber() : environment.getDesignDocumentNumber();
            String currentDate = Operaciones.getCurrentTimeStamp().replace(" ", "T") + "Z";
            // JSON del Body (versión compatible con Java 8 y 11)
            String jsonBody = "["
                    + "{"
                    + "\"id\":\"" + id + "\","
                    + "\"languageCode\":\"" + environment.getLanguagueCode() + "\","
                    + "\"sourceSystemIdentifier\":\"" + environment.getSourceSystemIdentifier() + "\","
                    + "\"documentName\":\"" + environment.getDocumentName() + "\","
                    + "\"fileName\":null,"
                    + "\"documentFormatCategory\":\"" + environment.getDocumentFormatCategory() + "\","
                    + "\"documentKindCategory\":\"" + environment.getDocumentKindCategory() + "\","
                    + "\"documentCodeCategory\":\"" + documentCodeCategory + "\","
                    + "\"documentDate\":\"" + currentDate + "\"," //2020-01-06T15:35:24Z
                    + "\"documentLocationURI\":null,"
                    + "\"documentSizeQuantity\":\"1573511\","
                    + "\"documentPageQuantity\":null,"
                    + "\"documentNumber\":\"" + documentNumber + "\","
                    + "\"documentLanguageSource\":\"" + environment.getDocumentLanguageSource() + "\","
                    + "\"documentOrigin\":\"" + environment.getDocumentOrigin() + "\","
                    + "\"documentGroupCategory\":null,"
                    + "\"nplIndicator\":\"false\","
                    + "\"commentText\":null"
                    + "}"
                    + "]";

            // Crear cliente HTTP
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1) // Asegurar compatibilidad
                    .build();

            // Crear la solicitud con headers
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(environment.getBaseUrl() + "/applications")) // URL correcta
                    .header("Content-Type", "application/json")
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate, br") // Simula Postman
                    .header("User-Agent", "PostmanRuntime/7.43.0") // Simula Postman
                    .header("X-ACCESS-TOKEN", xAccessToken)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Imprimir la solicitud para depuración
//        System.out.println("URL usada: " + BASE_URL + "/documents");
//        System.out.println("JSON enviado:\n" + jsonBody);
// Enviar la solicitud y capturar la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

// Imprimir la respuesta completa
            System.out.println("Código de respuesta: " + response.statusCode());
//        System.out.println("Cuerpo de respuesta: \n" + response.body());

// Verificar si la respuesta es 500
            if (response.statusCode() == 500) {
                System.out.println("⚠️ Error 500: El servidor falló. Código de estado " + response.statusCode() + " - \n" + response.body());
                return "1";
            } else {
                return response.body();
            }
        } catch (IOException ex) {
            Logger.getLogger(IpasApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return "1";
        } catch (InterruptedException ex) {
            Logger.getLogger(IpasApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return "2";
        }
    }

    public String getApplicationBiblioGraphicData(String tramiteIpas, String xAccessToken) {
        try {
            // Construir la URL completa
            String url = environment.getBaseUrl() + "/applications/" + tramiteIpas;

            // Crear el cliente HTTP
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1) // Asegurar compatibilidad
                    .build();

            // Construir la solicitud GET con headers
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "PostmanRuntime/7.43.0") // Simula Postman
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("X-ACCESS-TOKEN", xAccessToken)
                    .GET()
                    .build();

            // Enviar la solicitud y capturar la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar si la respuesta es exitosa (200 OK)
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.err.println("AppBibGraDa Error: Código de estado " + response.statusCode() + " - " + response.body());
                return "No se encontró " + tramiteIpas;
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(IpasApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en la petición";
        }
    }

    public String closeExistingAndOpenSpeciefiedDate(String xAccessToken) {
        try {
            // Construir la URL completa
            String url = environment.getBaseUrl() + "/dailylogs/closeExistingAndOpenSpecifiedDate";

            // Crear el cliente HTTP
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1) // Asegurar compatibilidad
                    .build();

            String fechaActual = Operaciones.formatDate(new Date());
            // Cuerpo del JSON
            String jsonBody = "{"
                    + "\"docOri\":\"EC\","
                    + "\"docLog\":\"E\","
                    + "\"dailyLogDate\":\""+fechaActual+"T10:00:00\""
                    + "}";

            // Construir la solicitud GET con headers
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "PostmanRuntime/7.43.0") // Simula Postman
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("X-ACCESS-TOKEN", xAccessToken)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Enviar la solicitud y capturar la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar si la respuesta es exitosa (200 OK)
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.err.println("closeExistingAndOpenSpecifiedDate Error: Código de estado " + response.statusCode() + " - " + response.body());
                return "Error al ejecutar la operación (código " + response.statusCode() + ")";
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(IpasApiClient.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en la petición";
        }
    }

    /**
     * @return the environment
     */
    public IpasEnvironment getEnvironment() {
        return environment;
    }

    /**
     * @param environment the environment to set
     */
    public void setEnvironment(IpasEnvironment environment) {
        this.environment = environment;
    }
}
