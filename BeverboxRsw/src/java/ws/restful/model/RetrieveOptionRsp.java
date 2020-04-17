package ws.restful.model;

import entity.OptionEntity;

public class RetrieveOptionRsp {

    private OptionWrapper option;

    public RetrieveOptionRsp() {
    }

    public RetrieveOptionRsp(OptionWrapper option) {
        this.option = option;
    }
    
    public OptionWrapper getOption() {
        return option;
    }

    public void setOption(OptionWrapper option) {
        this.option = option;
    }
    
}
