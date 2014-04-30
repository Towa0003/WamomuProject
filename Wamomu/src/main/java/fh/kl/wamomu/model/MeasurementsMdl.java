package fh.kl.wamomu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by Max on 09.04.2014.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeasurementsMdl {



    @JsonProperty("measurement")
    List<MeasurementMdl> measurement;

    public List<MeasurementMdl> getMeasurement() {
        return measurement;
    }

    public void setMeasurement(List<MeasurementMdl> measurement) {
        this.measurement = measurement;
    }
}
