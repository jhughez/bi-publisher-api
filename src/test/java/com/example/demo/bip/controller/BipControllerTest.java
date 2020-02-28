package com.example.demo.bip.controller;

import com.example.demo.bip.enums.BipReportFormat;
import com.example.demo.bip.service.BiPublisherService;
import java.io.IOException;
import java.util.Map;
import javax.mail.MessagingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class BipControllerTest {

  private MockMvc mockMvc;

  @Mock
  private BiPublisherService biPublisherService;

  @InjectMocks
  BipController bipController;

  @Before
  public void init() throws IOException, MessagingException {
    MockitoAnnotations.initMocks(this);
    bipController = new BipController(biPublisherService);
    mockMvc = MockMvcBuilders
        .standaloneSetup(bipController)
        .build();

    Mockito.when(biPublisherService.getBipReportAttachment(ArgumentMatchers.any(String.class), ArgumentMatchers
        .any(String.class), ArgumentMatchers.any(Map.class), ArgumentMatchers.any(BipReportFormat.class)))
        .thenReturn(new byte[0]);
  }

  @Test
  public void getHtmlReportTest() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/htmlReport/testReport")
        .contentType(MediaType.TEXT_HTML_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
  }

  @Test
  public void getHtmlReportSchemeTest() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/htmlReport/kt/testReport")
        .contentType(MediaType.TEXT_HTML_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
  }

  @Test
  public void getPdfReportTest() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pdfReport/testReport")
        .contentType(MediaType.APPLICATION_PDF_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
  }

  @Test
  public void getPdfReportSchemeTest() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pdfReport/kt/testReport")
        .contentType(MediaType.APPLICATION_PDF_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
  }

  @Test
  public void getExcelReportTest() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/excelReport/testReport")
        .contentType(MediaType.APPLICATION_PDF_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
  }

  @Test
  public void getExcelReportSchemeTest() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/excelReport/kt/testReport")
        .contentType(MediaType.APPLICATION_PDF_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
  }
}
