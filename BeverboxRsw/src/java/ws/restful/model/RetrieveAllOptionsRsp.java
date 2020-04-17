package ws.restful.model;

import entity.OptionEntity;
import java.util.List;

public class RetrieveAllOptionsRsp {

    private List<OptionWrapper> options;
    
    public RetrieveAllOptionsRsp() {
    }

    public RetrieveAllOptionsRsp(List<OptionWrapper> options) {
        this.options = options;
    }

    public List<OptionWrapper> getOptions() {
        return options;
    }

    public void setOptions(List<OptionWrapper> options) {
        this.options = options;
    }
    
    
}
