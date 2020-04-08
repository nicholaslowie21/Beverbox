package ws.restful.model;

import entity.OptionEntity;
import java.util.List;

public class RetrieveAllOptionsRsp {

    private List<OptionEntity> options;
    
    public RetrieveAllOptionsRsp() {
    }

    public RetrieveAllOptionsRsp(List<OptionEntity> options) {
        this.options = options;
    }

    public List<OptionEntity> getOptions() {
        return options;
    }

    public void setOptions(List<OptionEntity> options) {
        this.options = options;
    }
    
    
}
