package tkm.govt.nz.tkm.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubRegion {
    @JsonProperty("name")
    private String name;

    @JsonProperty("file")
    private String file;

    @JsonProperty("info_file")
    private String infoFile;

    @JsonProperty("info_marker_coordinate")
    private String infoMarkerCoordinate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getInfoFile() {
        return infoFile;
    }

    public void setInfoFile(String infoFile) {
        this.infoFile = infoFile;
    }

    public String getInfoMarkerCoordinate() {
        return infoMarkerCoordinate;
    }

    public void setInfoMarkerCoordinate(String infoMarkerCoordinate) {
        this.infoMarkerCoordinate = infoMarkerCoordinate;
    }
}
