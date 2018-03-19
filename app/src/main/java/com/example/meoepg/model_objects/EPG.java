package com.example.meoepg.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by danielbeleza on 16/03/2018.
 */

// Now and next list shows
public class EPG {

    @SerializedName("odata.metadata")
    @Expose
    private String metadata;

    @SerializedName("value")
    @Expose
    private List<Show> showList;

    public EPG(String metadata, List<Show> showList) {
        this.metadata = metadata;
        this.showList = showList;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public List<Show> getShowList() {
        return showList;
    }

    public void setShowList(List<Show> showList) {
        this.showList = showList;
    }
}
