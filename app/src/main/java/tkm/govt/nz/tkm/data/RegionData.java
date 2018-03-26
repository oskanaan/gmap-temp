package tkm.govt.nz.tkm.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RegionData {
    @JsonProperty("name")
    private String name;

    @JsonProperty("file")
    private String file;

    @JsonProperty("sub_regions")
    private List<SubRegion> subRegions;

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

    public List<SubRegion> getSubRegions() {
        return subRegions;
    }

    public void setSubRegions(List<SubRegion> subRegions) {
        this.subRegions = subRegions;
    }

}
