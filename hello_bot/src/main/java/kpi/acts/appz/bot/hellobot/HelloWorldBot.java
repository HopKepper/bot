package kpi.acts.appz.bot.hellobot;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import kpi.acts.appz.bot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//sender
public final class HelloWorldBot extends Bot {
    public static void main(String[] args) throws IOException, TimeoutException {
            ApiContextInitializer.init();
            Bot bot = new HelloWorldBot("1848955576:AAETbFVAqXzEOOdGJJ5eEtoc4n-6Sw3fArM","KingBot");
            Bot.runBot(bot);
    }

    private HelloWorldBot(String token, String botName) {
        super(token, botName);
    }

    @Override
    protected void processTheException(Exception e) {
        e.printStackTrace();
        System.out.println(e.toString());
    }

    @Override
    public void onUpdateReceived(Update update) {
        String textMessage = update.getMessage().getText();
    String sender = update.getMessage().getFrom().getUserName();
    System.out.println(update.getMessage().getText());

        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection= factory.newConnection()){
            Channel channel = connection.createChannel();
            if (textMessage.equals("Починаю лекцію")){
                sendTextMessage(update.getMessage(), "Викладач  " + sender + " почав лекцію "   );
                channel.queueDeclare("lecture",false,false,false, null);
                Bot.runBot(new Student_Bot("1880111321:AAEt7QTlDnNQ6N5hzdadD3FAjIpeloL-NBk","BootyBot"));
                channel.basicPublish(textMessage, textMessage + sender , false, null, textMessage.getBytes());
                System.out.println(" message has been sent: " + textMessage +" " + sender);}
            else{
                sendTextMessage(update.getMessage(), " Лекція ще не почалась  "  );
                channel.queueDeclare("no lecture",false,false,false, null);
                String message= "lecture has not  started" ;
                channel.basicPublish("","by", false, null,message.getBytes());
                System.out.println("!!! message has not been sent " + textMessage + sender);
            }}
        catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
