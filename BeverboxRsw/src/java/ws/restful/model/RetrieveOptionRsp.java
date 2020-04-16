package ws.restful.model;

import entity.OptionEntity;

public class RetrieveOptionRsp {

    private OptionEntity option;

    public RetrieveOptionRsp() {
    }

    public RetrieveOptionRsp(OptionEntity option) {
        this.option = option;
    }
    
    public OptionEntity getOption() {
        return option;
    }

    public void setOption(OptionEntity option) {
        this.option = option;
    }
    
}
