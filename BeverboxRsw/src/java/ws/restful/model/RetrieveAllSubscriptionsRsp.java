package ws.restful.model;

import entity.Subscription;
import java.util.List;

public class RetrieveAllSubscriptionsRsp {
    private List<Subscription> subscriptions;

    public RetrieveAllSubscriptionsRsp() {
    }

    public RetrieveAllSubscriptionsRsp(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
    
    
}
