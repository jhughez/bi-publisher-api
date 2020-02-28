package com.example.demo.bip.service;


import com.example.demo.bip.enums.BipReportFormat;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

@RunWith(SpringRunner.class)
@RestClientTest(BiPublisherService.class)
public class BiPublisherServiceTest {

  @Autowired
  private BiPublisherService biPublisherService;

  @Autowired
  private MockRestServiceServer server;

  @Value( "${report.service.url}" )
  private String bipServer;

  private String urlScheme;

  private String urlNoScheme;

  @Before
  public void init() throws IOException {
    urlScheme = bipServer + "%252fkt%252ftestReport/run";
    urlNoScheme = bipServer + "%252ftestReport/run";
  }

  @Test
  public void getBipReportAttachmentExcelSchemeTest() throws IOException, MessagingException {
    byte[] responseContent = getFileContent("src/test/resources/excel.response");
    byte[] expectedContent = getFileContent("src/test/resources/excel.file");

    server.expect(MockRestRequestMatchers.requestTo(urlScheme))
        .andRespond(MockRestResponseCreators.withSuccess(responseContent, MediaType.APPLICATION_JSON));

    Map<String, String> parameters = new HashMap<>();
    parameters.put("inspId", "410000");
    byte[] response = biPublisherService.getBipReportAttachment("kt", "testReport", parameters, BipReportFormat.HTML);
    Assert.assertNotNull(response);
    Assert.assertArrayEquals(expectedContent, response);
  }


  @Test
  public void getBipReportAttachmentPdfSchemeTest() throws IOException, MessagingException {
    byte[] responseContent = getFileContent("src/test/resources/pdf.response");
    byte[] expectedContent = getFileContent("src/test/resources/pdf.file");

    server.expect(MockRestRequestMatchers.requestTo(urlScheme))
        .andRespond(MockRestResponseCreators.withSuccess(responseContent, MediaType.APPLICATION_JSON));

    Map<String, String> parameters = new HashMap<>();
    parameters.put("inspId", "410000");
    byte[] response = biPublisherService.getBipReportAttachment("kt", "testReport", parameters, BipReportFormat.HTML);
    Assert.assertNotNull(response);
    Assert.assertArrayEquals(expectedContent, response);
  }

  @Test
  public void getBipReportAttachmentHtmlSchemeTest() throws IOException, MessagingException {
    byte[] responseContent = getFileContent("src/test/resources/html.response");
    byte[] expectedContent = getFileContent("src/test/resources/html.file");

    server.expect(MockRestRequestMatchers.requestTo(urlScheme))
        .andRespond(MockRestResponseCreators.withSuccess(responseContent, MediaType.APPLICATION_JSON));

    Map<String, String> parameters = new HashMap<>();
    parameters.put("inspId", "410000");
    byte[] response = biPublisherService.getBipReportAttachment("kt", "testReport", parameters, BipReportFormat.HTML);
    Assert.assertNotNull(response);
    Assert.assertArrayEquals(expectedContent, response);
  }

  @Test
  public void getBipReportAttachmentExcelNoSchemeTest() throws IOException, MessagingException {
    byte[] responseContent = getFileContent("src/test/resources/excel.response");
    byte[] expectedContent = getFileContent("src/test/resources/excel.file");

    server.expect(MockRestRequestMatchers.requestTo(urlNoScheme))
        .andRespond(MockRestResponseCreators.withSuccess(responseContent, MediaType.APPLICATION_JSON));

    Map<String, String> parameters = new HashMap<>();
    parameters.put("inspId", "410000");
    byte[] response = biPublisherService.getBipReportAttachment( "testReport", parameters, BipReportFormat.HTML);
    Assert.assertNotNull(response);
    Assert.assertArrayEquals(expectedContent, response);
  }


  @Test
  public void getBipReportAttachmentPdfNoSchemeTest() throws IOException, MessagingException {
    byte[] responseContent = getFileContent("src/test/resources/pdf.response");
    byte[] expectedContent = getFileContent("src/test/resources/pdf.file");

    server.expect(MockRestRequestMatchers.requestTo(urlNoScheme))
        .andRespond(MockRestResponseCreators.withSuccess(responseContent, MediaType.APPLICATION_JSON));

    Map<String, String> parameters = new HashMap<>();
    parameters.put("inspId", "410000");
    byte[] response = biPublisherService.getBipReportAttachment( "testReport", parameters, BipReportFormat.HTML);
    Assert.assertNotNull(response);
    Assert.assertArrayEquals(expectedContent, response);
  }

  @Test
  public void getBipReportAttachmentHtmlNoSchemeTest() throws IOException, MessagingException {
    byte[] responseContent = getFileContent("src/test/resources/html.response");
    byte[] expectedContent = getFileContent("src/test/resources/html.file");

    server.expect(MockRestRequestMatchers.requestTo(urlNoScheme))
        .andRespond(MockRestResponseCreators.withSuccess(responseContent, MediaType.APPLICATION_JSON));

    Map<String, String> parameters = new HashMap<>();
    parameters.put("inspId", "410000");
    byte[] response = biPublisherService.getBipReportAttachment( "testReport", parameters, BipReportFormat.HTML);
    Assert.assertNotNull(response);
    Assert.assertArrayEquals(expectedContent, response);
  }

  private byte[] getFileContent(String relativePath) throws IOException {
    return Files.readAllBytes(new File(relativePath).toPath());
  }

}
