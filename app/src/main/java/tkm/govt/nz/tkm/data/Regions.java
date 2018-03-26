package tkm.govt.nz.tkm.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by kanaano on 25/03/18.
 */

public class Regions {
    @JsonProperty("regions")
    private List<Region> regions;

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
}
