package ws.restful.model;

import java.util.List;

public class RetrieveAllSubscriptionsRsp {
    private List<SubscriptionWrapper> subscriptions;

    public RetrieveAllSubscriptionsRsp() {
    }

    public RetrieveAllSubscriptionsRsp(List<SubscriptionWrapper> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<SubscriptionWrapper> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionWrapper> subscriptions) {
        this.subscriptions = subscriptions;
    }
    
    
}
