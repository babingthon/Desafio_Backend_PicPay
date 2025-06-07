package flag.dev.picpay.service;

import flag.dev.picpay.client.NotificationClient;
import flag.dev.picpay.entity.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private NotificationClient notificationClient;

    public NotificationService(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    public void sendNotification(Transfer transfer) {
        try {
          logger.info("Sending notification to picpay...");  
            
          var resp = notificationClient.sendNotification(transfer);
          
          if (resp.getStatusCode().isError()) {
              logger.error("Error sending notification to picpay, status code: " + resp.getStatusCode());
          }
        } catch (Exception e){
            logger.error("Eror while sending notification", e);
        }
    }
}
