package com.example.demo.bip.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BipRequestBody implements Serializable {
  private boolean byPassCache =  true;
  private String attributeFormat = "html";
  private ReportParameterList parameterNameValues = new ReportParameterList();

  public void addParameter(String name, String value) {
    parameterNameValues.getListOfParamNameValues().add(new ReportParameter(name, value));
  }

  @Data
  private static class ReportParameterList implements Serializable {
    private List<ReportParameter> listOfParamNameValues = new LinkedList<>();
  }

  @AllArgsConstructor
  @Data
  private static class ReportParameter implements Serializable {
    private String name;
    private String values;
  }
}
