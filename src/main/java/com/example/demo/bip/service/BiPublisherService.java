package com.example.demo.bip.service;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import com.example.demo.bip.enums.BipReportFormat;
import com.example.demo.bip.exception.NoFileContentFoundException;
import com.example.demo.bip.model.BipRequestBody;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import javax.mail.util.ByteArrayDataSource;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
@Log4j
public class BiPublisherService {

    private final RestTemplate restTemplate;

    @Value( "${report.service.username}" )
    private String username;

    @Value( "${report.service.password}" )
    private String password;

    @Value( "${report.service.url}" )
    private String bipServer;

    public BiPublisherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public byte[] getBipReportAttachment(String schemeName, String reportName
        , Map<String, String> namedValueParameters
        , BipReportFormat reportFormat) throws IOException, MessagingException {

        String url = bipServer + "%2f" + schemeName + "%2f" + reportName + "/run";
        return getBipReportAttachmentByUrl(url, namedValueParameters, reportFormat);
    }
    public byte[] getBipReportAttachment(String reportName
        , Map<String, String> namedValueParameters
        , BipReportFormat reportFormat) throws IOException, MessagingException {

        String url = bipServer + "%2f" + reportName + "/run";
        return getBipReportAttachmentByUrl(url, namedValueParameters, reportFormat);
    }

    public byte[] getBipReportAttachmentByUrl(String url
        , Map<String, String> namedValueParameters
        , BipReportFormat reportFormat) throws IOException, MessagingException {

        HttpHeaders header = createMultipartFormDataHeader();
        MultiValueMap<String, String> fileMap = createFileMap();
        BipRequestBody msgBody = createMsgBody(reportFormat, namedValueParameters);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = createHttpRequestEntity(header, msgBody, fileMap);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);
        MimeMultipart multipart = new MimeMultipart( new ByteArrayDataSource(response.getBody(), "multipart/form-data"));
        return getFileByteArray(multipart);
    }

    private byte[] getFileByteArray(MimeMultipart multipart) throws MessagingException, IOException {
        int count = multipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (bodyPart.isMimeType(MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
                return IOUtils.toByteArray(bodyPart.getInputStream());
            }
        }
        throw new NoFileContentFoundException("No file content could be found in the response from BI Publisher");
    }

    private HttpHeaders createMultipartFormDataHeader() {
        HttpHeaders headers = createAuthHeader();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.MULTIPART_FORM_DATA));
        return headers;
    }

    private HttpHeaders createAuthHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setBasicAuth(username, password, StandardCharsets.US_ASCII);
        return header;
    }

    private MultiValueMap<String, String> createFileMap(){
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
            .builder("form-data")
            .name("ReportRequest")
            .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

        return fileMap;
    }

    private BipRequestBody createMsgBody(BipReportFormat reportFormat, Map<String, String> namedValueParameters){
        BipRequestBody msgBody = new BipRequestBody();

        msgBody.setAttributeFormat(reportFormat.getFormat());
        for (Map.Entry<String, String> parameter : namedValueParameters.entrySet()) {
            msgBody.addParameter(parameter.getKey(), parameter.getValue());
        }
        return msgBody;
    }
    private HttpEntity<MultiValueMap<String, Object>> createHttpRequestEntity(
        HttpHeaders header, BipRequestBody msgBody,
        MultiValueMap<String, String> fileMap){
        HttpEntity<BipRequestBody> reportRequestEntity = new HttpEntity<>(msgBody, fileMap);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("ReportRequest", reportRequestEntity);

         return new HttpEntity<>(body, header);
    }
}
